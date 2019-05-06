package com.example.demo.WebSocket;


import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.domain.user.Waiter;
import com.example.demo.model.waiter.WaiterDeliveryModel;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.info.WaiterDeliveryRecordService;
import com.example.demo.service.user.WaiterService;
import com.sun.deploy.security.ValidationState;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
//@ServerEndpoint("/websocket/{user}")
@ServerEndpoint(value = "/websocket/{openid}")
@Component
@ComponentScan("com.example.demo.service.*")
public class TestWebSocket {

    /**
     * 工作的状态
     * */
    public static final int STATUS_WORKING = 10;
    public static final int STATUS_STOP = 11;

    /**
     * 发送信息的内容标识符
     * TYPE_WORKING : 接受任务
     * TYPE_STATUS : 改变工作状态
     * */
    public static final int TYPE_GETMEESSION = 101;
    public static final int TYPE_STATUS = 102;

    /**
     * 处理的状态码
     * */
    public static final int SUCCEED = 201;
    public static final int FAILED = 202;
    public static final int ERROR = 203;

    /**
     * 内在属性
     * */
    private Waiter waiter;
    ;
    private int status = STATUS_WORKING;

    private Session session;


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static volatile int index_waiter = -1;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArrayList<TestWebSocket> webSocketSet = new CopyOnWriteArrayList<TestWebSocket>();

    private static BlockingQueue<WaiterDeliveryRecord> messageQueue = new LinkedBlockingDeque();


    public static WaiterService waiterService;
    public static WaiterDeliveryRecordService waiterDeliveryRecordService;
    public static FoodService foodService;

    @Autowired
    public void setWaiterService(WaiterDeliveryRecordService waiterService){
        TestWebSocket.waiterDeliveryRecordService = waiterService;
    }
    @Autowired
    public void setFoodService(FoodService foodService){
        TestWebSocket.foodService = foodService;
    }
    @Autowired
    public void setWaiterService(WaiterService waiterService){
        TestWebSocket.waiterService = waiterService;
    }

    /**
     * 监听线程
     * */
    static class WaiterServiceThread extends Thread{
        @Override
        public void run(){
            log.info("监听线程已启动");


            while (true){
                if(messageQueue.size() != 0 ){
                    int index = getNextWaiter();

                    log.info("waiter index : "+index);

                    while(true){
                        TestWebSocket socket = webSocketSet.get(index);

                        if(socket.status == STATUS_WORKING){
                            WaiterDeliveryRecord deliveryRecord = getFirstWaiterDeliveryRecord();
                            deliveryRecord.setWaiterID(socket.waiter.getId());
                            waiterDeliveryRecordService.save(deliveryRecord);

                            WaiterDeliveryModel model = new WaiterDeliveryModel();
                            model.setOrderID(deliveryRecord.getId());
                            model.setType(deliveryRecord.getType());

                            if(deliveryRecord.getType() == WaiterDeliveryRecord.TYPE_DELIVERY) {
                                String picUrl = foodService.getFoodPicUrlByFoodID(deliveryRecord.getFoodID());

                                model.setFoodPicUrl(picUrl);
                            }

                            model.setCreateTime("2019-5-4:12.30");
                            model.setTableNum(deliveryRecord.getTableNum());

                            JSONObject jsonObject = JSONObject.fromObject(model);
                            String jsonMessage = jsonObject.toString();

                            try {
                                socket.sendMessage(jsonMessage);

                                removeFirstWaiterDeliveryRecord();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            break;
                        }

                        index = getNextWaiter();
                    }
                }
            }
        }
    }

    /**
     * 当Socket类被实例化时，建立监听线程
     * */
    static{
        WaiterServiceThread serviceThread = new WaiterServiceThread();
        serviceThread.start();
    }


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("openid") String openid) throws IOException {
        this.session = session;

        Waiter waiter = waiterService.getWaiterByOpenID(openid);

        if(waiter == null){
            return;
        }

        this.waiter = waiter;

        webSocketSet.add(this);     //加入set中

        addOnlineCount();
        //在线数加1
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            JSONObject object = new JSONObject();
            object.put("status", "SUCCEED");
            sendMessage(object.toString());
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从array中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("来自客户端的消息:" + message);

        JSONObject object = JSONObject.fromObject(message);

        Integer messageFlag = object.getInt("type");

        switch (messageFlag){
            case TYPE_GETMEESSION ://任务接收
                Long recordID = object.getLong("id");

                WaiterDeliveryRecord waiterDeliveryRecord = acceptedMission(recordID);

                if(waiterDeliveryRecord != null && waiterDeliveryRecord.getIsComplete() == 1){//保存成功
                    String result = getJsonResult(TYPE_GETMEESSION,SUCCEED, String.valueOf(waiterDeliveryRecord.getId()));

                    sendMessage(result);
                } else {
                    String result = getJsonResult(TYPE_GETMEESSION,FAILED, "");

                    sendMessage(result);
                }

                break;
            case TYPE_STATUS ://改变状态
                Integer newStatus = object.getInt("workingStatus");

                changStatus(newStatus);

                String result = "";

                if(status == STATUS_WORKING){
                    result = getJsonResult(TYPE_STATUS,SUCCEED,"");
                } else {
                    result = getJsonResult(TYPE_STATUS,FAILED,"");
                }

                sendMessage(result);

                break;

            default:
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public String getJsonResult(int resultType, int result, String data){
        JSONObject object = new JSONObject();

        object.put("type",resultType);

        switch (result) {
            case SUCCEED://处理成功
                object.put("status", "SUCCEED");

                if(resultType == TYPE_GETMEESSION){
                    object.put("id", Long.parseLong(data));
                }

                return object.toString();
            case FAILED://处理失败
                object.put("status", "FAILED");

                return object.toString();
            case ERROR://错误
                object.put("status", "ERROR");

                return object.toString();

            default://错误
                object.put("status", "ERROR");

                return object.toString();
        }

    }

    /**
     * 外部程序将订单记录传至阻塞队列中等待处理
     * */
    public synchronized static boolean putMessageToWaiterMessageBlockingQueue(WaiterDeliveryRecord record){
        return messageQueue.add(record);
    }

    /**
     * 获取下一个服务员的下标
     * */
    public synchronized static int getNextWaiter(){
        index_waiter++;

        if(index_waiter >= getMessageQueueSize() ){
            return 0;
        }

        return index_waiter;
    }
    /**
     * 供给内部获取阻塞队列中的
     * */
    public static synchronized WaiterDeliveryRecord getFirstWaiterDeliveryRecord(){
        return messageQueue.peek();
    }

    public static synchronized WaiterDeliveryRecord removeFirstWaiterDeliveryRecord(){
        return messageQueue.poll();
    }

    public static synchronized int getMessageQueueSize(){return messageQueue.size();}

    private void changStatus(int newStatus){
        this.status = newStatus;
    }

    /**
     * 任务完成，根据id获取服务记录，将isComplete改写为1，说明已经完成任务
     * */
    private WaiterDeliveryRecord acceptedMission(Long recordID){
        WaiterDeliveryRecord waiterDeliveryRecord = waiterDeliveryRecordService.findOneByID(recordID);

        if(waiterDeliveryRecord ==  null){
            return null;
        }

        waiterDeliveryRecord.setIsComplete(1);

        return waiterDeliveryRecordService.save(waiterDeliveryRecord);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        log.info(message);
        for (TestWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        TestWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        TestWebSocket.onlineCount--;
    }


}
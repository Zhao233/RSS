package com.example.demo.asynchronousHandler.Waiter;


import com.example.demo.domain.info.OrderRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.domain.user.Waiter;
import com.example.demo.model.waiter.WaiterDeliveryModel;
import com.example.demo.repository.user.WaiterDao;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.info.OrderRecordService;
import com.example.demo.service.info.WaiterDeliveryRecordService;
import com.example.demo.service.user.WaiterService;
import com.example.demo.util.TimeUtil;
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
public class WaiterJobHandler {

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
    public static final int TYPE_COMPLETEMISSION = 101;
    public static final int TYPE_STATUS = 102;
    public static final int TYPE_GETMISSION = 103;

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
    private int status = STATUS_WORKING;
    private Session session;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static volatile int index_waiter = -1;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArrayList<WaiterJobHandler> webSocketSet = new CopyOnWriteArrayList<WaiterJobHandler>();

    private static BlockingQueue<WaiterDeliveryRecord> messageQueue = new LinkedBlockingDeque();

    public static WaiterService waiterService;
    public static WaiterDeliveryRecordService waiterDeliveryRecordService;
    public static FoodService foodService;

    public static WaiterDao waiterDao;

    @Autowired
    public void setWaiterService(WaiterDeliveryRecordService waiterService){
        WaiterJobHandler.waiterDeliveryRecordService = waiterService;
    }
    @Autowired
    public void setFoodService(FoodService foodService){
        WaiterJobHandler.foodService = foodService;
    }
    @Autowired
    public void setWaiterService(WaiterService waiterService){
        WaiterJobHandler.waiterService = waiterService;
    }
    @Autowired
    public void setWaiterDao(WaiterDao waiterDao){
        WaiterJobHandler.waiterDao = waiterDao;
    }

    /**
     * 监听线程
     * */
    static class WaiterServiceThread extends Thread{
        public boolean isStart = false;

        @Override
        public void run(){
            log.info("监听线程已启动");

            setRunningStatus(true);

            while (true){
                if(getMessageQueueSize() != 0 ){
                    while(true){

                        try {
                            this.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        int index = getNextWaiter();

                        log.info("waiter index : "+index);

                        if(getSocketQueueSize() == 0){
                            continue;
                        }

                        WaiterJobHandler socket = webSocketSet.get(index);

                        if(socket.status != STATUS_WORKING){
                            continue;
                        }

                        WaiterDeliveryRecord deliveryRecord = getFirstWaiterDeliveryRecord();
                        deliveryRecord.setWaiterID(socket.waiter.getId());
                        waiterDeliveryRecordService.save(deliveryRecord);

                        WaiterDeliveryModel model = new WaiterDeliveryModel();
                        model.setOrderID(deliveryRecord.getId());

                        switch (deliveryRecord.getType()){
                            case WaiterDeliveryRecord.TYPE_DELIVERY :
                                model.setType("派送");

                                break;
                            case WaiterDeliveryRecord.TYPE_SERVICE :
                                model.setType("服务");
                                break;
                            default:
                                model.setType("无");
                        }

                        if(deliveryRecord.getType() == WaiterDeliveryRecord.TYPE_DELIVERY) {
                            String picUrl = foodService.getFoodPicUrlByFoodID(deliveryRecord.getFoodID());

                            model.setFoodPicUrl(picUrl);
                        }

                        model.setCreateTime(TimeUtil.getFormattedTime(deliveryRecord.getCreateTime()));
                        model.setTableNum(deliveryRecord.getTableNum());

                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("status","SUCCEED");
                        jsonObject.put("type",TYPE_GETMISSION);
                        jsonObject.put("data", model);

                        String jsonMessage = jsonObject.toString();

                        try {
                            socket.sendMessage(jsonMessage);

                            removeFirstWaiterDeliveryRecord();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                }
            }
        }

        public synchronized void setRunningStatus(boolean status){
            isStart = status;
        }

        public synchronized boolean getRunningStatus(){
            return isStart;
        }
    }

    static {
        WaiterServiceThread thread = new WaiterServiceThread();
        thread.start();
    }

    public static WaiterServiceThread waiterServiceThread = new WaiterServiceThread();

    /**=======================================Socket状态发生改变时的调用函数===============================================================*/

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session, @PathParam("openid") String openid) throws IOException {
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

//        if(getSocketQueueSize() == 1) {//当第一个服务员连接时，启用线程
//            startThread();
//        }

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
    public void onClose() throws InterruptedException {
        webSocketSet.remove(this);  //从array中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());

//        if( getSocketQueueSize() == 0){//当没有人在线时，线程停止工作
//            pauseThread();
//
//            log.info("线程中断");
//        }
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
            case TYPE_COMPLETEMISSION ://任务接收
                Long recordID = object.getLong("id");

                WaiterDeliveryRecord waiterDeliveryRecord = acceptedMission(recordID);

                if(waiterDeliveryRecord != null && waiterDeliveryRecord.getIsComplete() == 1){//保存成功
                    String result = getJsonResult(TYPE_COMPLETEMISSION,SUCCEED, String.valueOf(waiterDeliveryRecord.getId()));

                    sendMessage(result);
                } else {
                    String result = getJsonResult(TYPE_COMPLETEMISSION,FAILED, "");

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

                if(resultType == TYPE_COMPLETEMISSION){
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

    /**========================================对messageQueue的所有线程安全操作=========================================================*/

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

        if(index_waiter >= getSocketQueueSize() ){
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
    public static synchronized int getSocketQueueSize(){return webSocketSet.size();}

    public static synchronized void startThread(){
        if( WaiterJobHandler.waiterServiceThread.getRunningStatus() ){
            WaiterJobHandler.waiterServiceThread.notify();
        } else {
            WaiterJobHandler.waiterServiceThread.start();
        }
    }
    public static synchronized void pauseThread() throws InterruptedException {
        if( WaiterJobHandler.waiterServiceThread.getRunningStatus() ){
            WaiterJobHandler.waiterServiceThread.interrupt();
        }
    }

    /**=======================================每个厨师私有的方法====================================================================*/

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
        waiterDeliveryRecord.setUpdateTime(TimeUtil.getTimeNow());

        return waiterDeliveryRecordService.save(waiterDeliveryRecord);
    }

    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        log.info(message);
        for (WaiterJobHandler item : webSocketSet) {
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
        WaiterJobHandler.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WaiterJobHandler.onlineCount--;
    }


}
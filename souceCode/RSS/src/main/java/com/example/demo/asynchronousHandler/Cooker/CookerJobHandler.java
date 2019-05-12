package com.example.demo.asynchronousHandler.Cooker;

import com.example.demo.asynchronousHandler.Waiter.WaiterJobHandler;
import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.domain.user.Cooker;
import com.example.demo.model.cooker.CookerDeliveryModel;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.info.CookerDeliveryRecordService;
import com.example.demo.service.user.CookerService;
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
@ServerEndpoint(value = "/websocket/cooker/{openid}")
@Component
@ComponentScan("com.example.demo.service.*")
public class CookerJobHandler {

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
    private Cooker cooker;
    private int status = STATUS_WORKING;
    private Session session;


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static volatile int index_waiter = -1;

    /**
     * 已连接的对象合集
     * */
    private static CopyOnWriteArrayList<CookerJobHandler> webSocketSet = new CopyOnWriteArrayList<>();

    /**
     * 消息队列
     * */
    private static BlockingQueue<CookerDeliveryRecord> messageQueue = new LinkedBlockingDeque();

    /**
     * 服务接口
     * */
    public static CookerService cookerService;
    public static CookerDeliveryRecordService cookerDeliveryRecordService;
    public static FoodService foodService;

    @Autowired
    public void setWaiterService(CookerService waiterService){
        CookerJobHandler.cookerService = waiterService;
    }
    @Autowired
    public void setWaiterService(CookerDeliveryRecordService cookerDeliveryRecordService){
        CookerJobHandler.cookerDeliveryRecordService = cookerDeliveryRecordService;
    }
    @Autowired
    public void setFoodService(FoodService foodService){
        CookerJobHandler.foodService = foodService;
    }


    /**
     * 监听线程
     * */
    static class CookerServiceThread extends Thread{
        @Override
        public void run(){
            log.info("监听线程已启动");


            while (true){
                if(messageQueue.size() != 0 ){//持续读取消息队列

                    while(true){//持续遍历已连接厨师列表

                        int index = getNextCooker();//获取下一个厨师的index

                        log.info("waiter index : "+index);

                        if(getSocketQueueSize() == 0){//当没有厨师连接服务器时，查找下一个厨师
                            continue;
                        }

                        CookerJobHandler socket = webSocketSet.get(index);

                        if(socket.status != STATUS_WORKING){//如果当前已连接厨师的工作状态处于暂停
                            continue;
                        }

                        CookerDeliveryRecord cookerDeliveryRecord = CookerJobHandler.getFirstCookerDeliveryRecord();
                        Food food = foodService.getOne(cookerDeliveryRecord.getFoodId());

                        if(socket.cooker.getRole() != food.getRole()){ //如果菜品的制作类型不属于该厨师，就遍历下一个
                            continue;
                        }

                        cookerDeliveryRecord.setCookerID(socket.cooker.getId());
                        cookerDeliveryRecordService.save(cookerDeliveryRecord);


                        CookerDeliveryModel model = new CookerDeliveryModel();
                        model.setCreateTime(TimeUtil.getFormattedTime( cookerDeliveryRecord.getCreateTime() ));
                        model.setOrderID(cookerDeliveryRecord.getOrderRecordId());
                        model.setTableNum(cookerDeliveryRecord.getTableNum());
                        model.setFoodPicUrl(food.getPicUrl());

                        JSONObject jsonObject = JSONObject.fromObject(model);
                        String jsonMessage = jsonObject.toString();

                        try {
                            socket.sendMessage(jsonMessage);

                            removeFirstCookerDeliveryRecord();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }

    /**
     * 当Socket类被实例化时，建立监听线程
     * */


    public static CookerServiceThread cookerServiceThread = new CookerServiceThread();

    /**=======================================Socket状态发生改变时的调用函数===============================================================*/

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("openid") String openid) throws IOException {
        this.session = session;

        Cooker cooker = cookerService.getCookerByOpenID(openid);

        if(cooker == null){
            return;
        }

        this.cooker = cooker;

        webSocketSet.add(this);     //加入set中

        addOnlineCount();
        //在线数加1
        log.info("有新连接加入！当前在线人数为" + getOnlineCount());

        if(getSocketQueueSize() == 1){
            cookerServiceThread.start();
        }

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

        if( getSocketQueueSize() == 0){//当没有人在线时，线程停止工作
            CookerJobHandler.cookerServiceThread.wait();
        }
    }

    /**
     * 收到客户端消息后调用的方法
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

                CookerDeliveryRecord cookerDeliveryRecord = acceptMission(recordID);

                if(cookerDeliveryRecord != null && cookerDeliveryRecord.getIsComplete() == 1){//保存成功
                    String result = getJsonResult(TYPE_COMPLETEMISSION,SUCCEED, String.valueOf(cookerDeliveryRecord.getId()));

                    sendMessage(result);

                    //厨师完成任务后，通知服务员取餐
                    sendDeliveryJobToWaiter(cookerDeliveryRecord);
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

    /**
     * 向已连接对象发送消息
     * */
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
    public synchronized static boolean putMessageToWaiterMessageBlockingQueue(CookerDeliveryRecord record){
        return messageQueue.add(record);
    }

    /**
     * 获取下一个服务员的下标
     * */
    public synchronized static int getNextCooker(){
        index_waiter++;

        if(index_waiter >= getSocketQueueSize() ){
            return 0;
        }

        return index_waiter;
    }

    public static synchronized int getSocketQueueSize(){return webSocketSet.size();}

    /**
     * 获取消息队列中的头消息
     * */
    public static synchronized CookerDeliveryRecord getFirstCookerDeliveryRecord(){
        return messageQueue.peek();
    }

    /**
     * 移除消息队列中的头消息
     * */
    public static synchronized CookerDeliveryRecord removeFirstCookerDeliveryRecord(){
        return messageQueue.poll();
    }

    /**
     * 获取消息队列的大小
     * */
    public static synchronized int getMessageQueueSize(){return messageQueue.size();}



    /**=======================================每个厨师私有的方法====================================================================*/

    private void changStatus(int newStatus){
        this.status = newStatus;
    }

    /**
     * 任务完成，根据id获取服务记录，将isComplete改写为1，说明已经完成任务
     * */
    private CookerDeliveryRecord acceptMission(Long id){
        CookerDeliveryRecord cookerDeliveryRecord = cookerDeliveryRecordService.findOneByID(id);

        if(cookerDeliveryRecord ==  null){
            return null;
        }

        cookerDeliveryRecord.setIsComplete(1);
        cookerDeliveryRecord.setUpdateTime(TimeUtil.getTimeNow());

        return cookerDeliveryRecordService.save(cookerDeliveryRecord);
    }

    public void sendDeliveryJobToWaiter(CookerDeliveryRecord cookerDeliveryRecord){
        WaiterDeliveryRecord record = new WaiterDeliveryRecord();
        record.setOrderRecordID(cookerDeliveryRecord.getOrderRecordId());
        record.setFoodID(cookerDeliveryRecord.getFoodId());
        record.setTableNum(cookerDeliveryRecord.getTableNum());
        record.setCreateTime(cookerDeliveryRecord.getCreateTime());
        record.setType(WaiterDeliveryRecord.TYPE_DELIVERY);

        //将送餐任务传送给服务员
        WaiterJobHandler.putMessageToWaiterMessageBlockingQueue(record);
    }



    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        CookerJobHandler.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        CookerJobHandler.onlineCount--;
    }



}

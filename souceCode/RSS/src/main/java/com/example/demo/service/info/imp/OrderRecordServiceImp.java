package com.example.demo.service.info.imp;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.model.customer.OrderRecordModel;
import com.example.demo.repository.info.OrderRecordDao;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.info.DiscountService;
import com.example.demo.service.info.OrderRecordService;
import com.example.demo.service.user.CustomerService;
import com.example.demo.util.   StringTranslator;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service("orderRecordService")
public class OrderRecordServiceImp implements OrderRecordService {
    @Autowired
    private OrderRecordDao orderRecordDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private DiscountService discountService;



    /**================================For Admin========================================================*/

    @Override
    public Long addOne(List<Long> foodIDList, List<Integer> foodNumList, List<Long> styleIDList, Long discountID, String openid, long expirationTime, Double account) {
        Long userID = customerService.getIDByOpenID(openid);
        String foodIDList_String = StringTranslator.getStringFromList(foodIDList);
        String foodNum_String = StringTranslator.getStringFromList(foodNumList);
        String styleIDList_String = StringTranslator.getStringFromList(styleIDList);

        Double settlementAmount = account;

        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setDiscountID(discountID);

        if(discountID != 90000) {//使用优惠卷
            settlementAmount = discountService.getDiscountNum(discountID) * account;
            orderRecord.setDiscountID(discountID);
        }

        orderRecord.setUserID(userID);
        orderRecord.setFoodsID(foodIDList_String);
        orderRecord.setFoodsNum(foodNum_String);
        orderRecord.setStylesID(styleIDList_String);

        orderRecord.setSettlementAmount(settlementAmount);
        orderRecord.setOriginAmount(account);
        orderRecord.setIsPaid(0);//未支付
        orderRecord.setDiscountID(90000);

        orderRecord.setExpirationTime(TimeUtil.MilTimeToTimeStamp(expirationTime));
        orderRecord.setCreateTime(TimeUtil.getTimeNow());

        return orderRecordDao.save(orderRecord).getId();
    }

    @Override
    public List<OrderRecordModel> getAllOrderRecordModel(String openID) {
        Long userID = customerService.getIDByOpenID(openID);

        List<OrderRecord> orderRecordList = orderRecordDao.getAllByUserID(userID);
        List<OrderRecordModel> orderRecordModelList = new LinkedList<>();

        for(OrderRecord temp_orderRecord : orderRecordList){
            OrderRecordModel temp_orderRecordModel = new OrderRecordModel();

            /**
             * 获取订单中菜品的图片
             * */
            String foodsID = temp_orderRecord.getFoodsID();
            String[] temp_foodsID = foodsID.split("_");
            List<Long> foodsIDList = new LinkedList<>();
            for(String temp_string_foodID : temp_foodsID){
                foodsIDList.add(Long.valueOf(temp_string_foodID));
            }
            List<String> picUrlList =  foodService.getFoodsPicUrlByFoodIDs(foodsIDList);
            temp_orderRecordModel.setPicURL_foods(picUrlList);


            temp_orderRecordModel.setAccount_final(temp_orderRecord.getSettlementAmount());//实际支付
            temp_orderRecordModel.setCreateDate(TimeUtil.getFormattedTime( temp_orderRecord.getCreateTime() ));
            temp_orderRecordModel.setId(temp_orderRecord.getId());

            /**
             * 设置订单状态
             * */
            temp_orderRecordModel.setStatus(111);//默认status

            boolean goon = true;

            if( temp_orderRecord.getExpirationTime().before( TimeUtil.getTimeNow() ) &&
                    temp_orderRecord.getIsPaid() ==  0 && goon){//超时
                temp_orderRecordModel.setStatus(OrderRecord.TIMEOUT);

                goon = false;
            }

            if( temp_orderRecord.getIsPaid() == 0 && goon){//未支付
                temp_orderRecordModel.setStatus(OrderRecord.NOT_PAID);

                goon = false;
            }

            if( temp_orderRecord.getIsPaid() == 1 && goon){//已支付
                temp_orderRecordModel.setStatus(OrderRecord.DONE);

                goon = false;
            }


            orderRecordModelList.add(temp_orderRecordModel);
        }

        return orderRecordModelList;
    }

    /**
     * 获取消费额
     *
     * type：
     * 1 月消费额
     * 2 星期消费额
     * 3 今日消费额
     */
    @Override
    public Double getAccount(int type) {
        Double account = new Double(0);

        Calendar calendar = Calendar.getInstance();

        Timestamp time_start = null;
        Timestamp time_end = new Timestamp(calendar.getTime().getTime());

        switch (type){
            case OrderRecord.TYPE_ACCOUNT_MONTH :

                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithMonth();

                account = orderRecordDao.getAccount(time_start, time_end);

                return account;
            case OrderRecord.TYPE_ACCOUNT_WEEK:
                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithWeek();

                account = orderRecordDao.getAccount(time_start, time_end);

                return account;
            case OrderRecord.TYPE_ACCOUNT_DAY:

                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithDay();

                account = orderRecordDao.getAccount(time_start, time_end);

                return account;
        }

        return account;
    }

    /**
     * type:
     * 1 总点餐次数
     * 2 本星期点餐次数
     * 3 今日点餐次数
     * */
    @Override
    public int orderTime(int type) {
        Integer times = new Integer(0);

        Calendar calendar = Calendar.getInstance();

        Timestamp time_start = null;
        Timestamp time_end = new Timestamp(calendar.getTime().getTime());

        switch (type){
            case 1 :
                time_start = getTimeWithMonth();

                break;

            case 2:
                time_start = getTimeWithWeek();

                break;

            case OrderRecord.TYPE_ORDERTIME_DAY:
                time_start = getTimeWithDay();

                break;
        }

        times = orderRecordDao.getOrderTimes(time_start, time_end);

        return times;
    }

    @Override
    public HashMap<Long, Integer> getAllOrderByTime(int type) {
        HashMap<Long, Integer> res = new HashMap<>();
        List<OrderRecord> orderRecordList = new LinkedList<>();

        Calendar calendar = Calendar.getInstance();

        Timestamp time_start = null;
        Timestamp time_end = new Timestamp(calendar.getTime().getTime());

        switch (type) {
            case 1:
                time_start = getTimeWithMonth();

                break;
            case 2:

                time_start = getTimeWithWeek();
                break;
            case OrderRecord.TYPE_POPULARFOODS_DAY:

                time_start = getTimeWithDay();

                orderRecordList = orderRecordDao.getAllOrderByTime(time_start, time_end);

                break;
        }

        for( OrderRecord temp_orderRecord : orderRecordList ){
            String foods = temp_orderRecord.getFoodsID();
            List<Long> foodIDList = StringTranslator.getListFromString(foods,0);

            for( Long temp_id : foodIDList ){
                Integer res_num = res.get(temp_id);

                if(res_num == null){
                    res.put(temp_id,1);
                } else {
                    res.put(temp_id, res_num + 1);
                }
            }
        }

        return res;
    }

    public Timestamp getTimeWithMonth(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
    public Timestamp getTimeWithWeek(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
    public Timestamp getTimeWithDay(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
}

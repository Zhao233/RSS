package com.example.demo.service.info.imp;

import com.example.demo.domain.foodInfo.Food;
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

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

@Service("nonPaymentRecordService")
public class OrderRecordServiceImp implements OrderRecordService {
    @Autowired
    private OrderRecordDao orderRecordDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private DiscountService discountService;

    @Override
    public Long addOne(List<Long> foodIDList, List<Integer> foodNumList, List<Long> styleIDList, Long discountID, String openid, long expirationTime, Double account) {
        Long userID = customerService.getIDByOpenID(openid);
        String foodIDList_String = StringTranslator.getStringFromList(foodIDList);
        String foodNum_String = StringTranslator.getStringFromList(foodNumList);
        String styleIDList_String = StringTranslator.getStringFromList(styleIDList);

        Double settlementAmount = account;

        if(discountID != 90000) {//使用优惠卷
            settlementAmount = discountService.getDiscountNum(discountID) * account;
        }

        OrderRecord orderRecord = new OrderRecord();

        orderRecord.setUserID(userID);
        orderRecord.setFoodsID(foodIDList_String);
        orderRecord.setFoodsNum(foodNum_String);
        orderRecord.setStylesID(styleIDList_String);

        orderRecord.setSettlementAmount(settlementAmount);
        orderRecord.setOriginAmount(account);
        orderRecord.setIsPaid(0);//未支付

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
}

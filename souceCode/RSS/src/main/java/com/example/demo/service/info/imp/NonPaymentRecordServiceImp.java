package com.example.demo.service.info.imp;


import com.example.demo.domain.info.NonPaymentRecord;
import com.example.demo.domain.user.Customer;
import com.example.demo.repository.info.NonPaymentRecordDao;
import com.example.demo.repository.user.CustomerDao;
import com.example.demo.service.info.DiscountService;
import com.example.demo.service.info.NonPaymentRecordService;
import com.example.demo.service.user.CustomerService;
import com.example.demo.util.StringTranslator;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("nonPaymentRecordService")
public class NonPaymentRecordServiceImp implements NonPaymentRecordService {
    @Autowired
    private NonPaymentRecordDao nonPaymentRecordDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private DiscountService discountService;

    @Override
    public void addOne(List<Long> foodIDList, List<Integer> foodNumList, List<Long> styleIDList, Long discountID, String openid, long expirationTime, Double account) {
        Long userID = customerService.getIDByOpenID(openid);
        String foodIDList_String = StringTranslator.getStringFromList(foodIDList);
        String foodNum_String = StringTranslator.getStringFromList(foodNumList);
        String styleIDList_String = StringTranslator.getStringFromList(styleIDList);

        Double settlementAmount = account;

        if(discountID != 90000) {//使用优惠卷
            settlementAmount = discountService.getDiscountNum(discountID) * account;
        }

        NonPaymentRecord nonPaymentRecord = new NonPaymentRecord();

        nonPaymentRecord.setUserID(userID);
        nonPaymentRecord.setFoodsID(foodIDList_String);
        nonPaymentRecord.setFoodsNum(foodNum_String);
        nonPaymentRecord.setStylesID(styleIDList_String);

        nonPaymentRecord.setSettlementAmount(settlementAmount);
        nonPaymentRecord.setOriginAmount(account);


        nonPaymentRecord.setExpirationTime(TimeUtil.MilTimeToTimeStamp(expirationTime));
        nonPaymentRecord.setCreateTime(TimeUtil.getTimeNow());

        nonPaymentRecordDao.save(nonPaymentRecord);
    }
}

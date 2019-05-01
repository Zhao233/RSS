package com.example.demo.service.info;

import com.example.demo.model.customer.OrderRecordModel;

import java.util.List;

public interface OrderRecordService {
    Long addOne(List<Long> foodIDList, List<Integer> foodNumList, List<Long> styleIDList, Long discountID, String openid, long expirationTime, Double account);

    List<OrderRecordModel> getAllOrderRecordModel(String openID);
}
package com.example.demo.service.info;

import java.util.List;

public interface NonPaymentRecordService {
    void addOne(List<Long> foodIDList, List<Integer> foodNumList, List<Long> styleIDList, Long discountID, String openid, long expirationTime, Double account);
}

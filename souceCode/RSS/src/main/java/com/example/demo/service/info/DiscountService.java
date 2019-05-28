package com.example.demo.service.info;

import com.example.demo.domain.foodInfo.DiscountRecord;

import java.util.List;

public interface DiscountService {
    List<DiscountRecord> getDiscount(double account);

    Double getDiscountNum(Long discountID);
}

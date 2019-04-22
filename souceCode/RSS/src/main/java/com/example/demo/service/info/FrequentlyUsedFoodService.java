package com.example.demo.service.info;

import com.example.demo.domain.info.FrequentlyUsedFood;
import com.example.demo.model.customer.FoodForCustomerFrequentlyModel;

import java.util.List;

public interface FrequentlyUsedFoodService {
    FoodForCustomerFrequentlyModel getFrequentlyUsedFoodByUserID(Long userID);
}

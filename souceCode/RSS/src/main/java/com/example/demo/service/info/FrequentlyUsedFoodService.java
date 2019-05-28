package com.example.demo.service.info;

import com.example.demo.domain.info.FrequentlyUsedFood;
import com.example.demo.model.customer.FoodForCustomerFrequentlyModel;

import java.util.List;

public interface FrequentlyUsedFoodService {
    FoodForCustomerFrequentlyModel getFrequentlyUsedFoodByUserID(String openID);

    boolean addFoodToFrequentlyUsedFoodList(String openID, Long foodID, Long styID);

    void saveFrequentlyUsedFoodList(String openID, String foodIDs, String styleIDs, String nums);

    void saveEmptyFrequentlyUsedFoodList(Long userID);
}

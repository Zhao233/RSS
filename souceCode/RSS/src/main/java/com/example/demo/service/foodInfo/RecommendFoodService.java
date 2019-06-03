package com.example.demo.service.foodInfo;

import com.example.demo.model.customer.FoodForCustomerRecommendModel;

import java.util.List;

public interface RecommendFoodService {
    List<FoodForCustomerRecommendModel> getCustomerRecommendModel();
}

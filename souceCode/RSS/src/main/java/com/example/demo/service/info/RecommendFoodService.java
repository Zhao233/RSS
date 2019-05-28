package com.example.demo.service.info;

import com.example.demo.model.customer.FoodForCustomerRecommendModel;

import java.util.List;

public interface RecommendFoodService {
    List<FoodForCustomerRecommendModel> getCustomerRecommendModel();
}

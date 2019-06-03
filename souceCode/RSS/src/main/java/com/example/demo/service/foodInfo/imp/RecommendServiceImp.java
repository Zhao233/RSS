package com.example.demo.service.foodInfo.imp;

import com.example.demo.model.customer.FoodForCustomerRecommendModel;
import com.example.demo.repository.foodInfo.RecommendFoodDao;
import com.example.demo.service.foodInfo.RecommendFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("recommendService")
public class RecommendServiceImp implements RecommendFoodService {
    @Autowired
    private RecommendFoodDao recommendFoodDao;


    @Override
    public List<FoodForCustomerRecommendModel> getCustomerRecommendModel() {
        return recommendFoodDao.getCustomerRecommendModel();
    }
}

package com.example.demo.service.foodInfo;

import com.example.demo.domain.foodInfo.Food;

import java.util.List;

public interface FoodService {
    List<Food> getFoodsByMenuId(long menuId);
}

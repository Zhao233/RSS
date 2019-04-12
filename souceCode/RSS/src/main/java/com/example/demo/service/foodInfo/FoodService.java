package com.example.demo.service.foodInfo;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.model.Food_All;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodService {
    Page<Food> getFoodsByMenuId(long menuId, String search, Pageable pageable);

    Page<Food> getFoodList(String search, Pageable pageable);

    Food getOne(long id);

    void addFood(Food food);

    void updateFood(Food food);

    void deleteFood(long id);
}

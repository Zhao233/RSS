package com.example.demo.service.foodInfo;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.model.admin.FoodForRecommendModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {
    /**=================================For Admin================================================*/

    Page<Food> getFoodsByMenuId(long menuId, String search, Pageable pageable);

    Page<Food> getFoodList(String search, Pageable pageable);

    Food getOne(long id);

    void addFood(Food food);

    void updateFood(Food food);

    void deleteFood(long id);

    Page<FoodForRecommendModel> getFoodListForRecommend(String search, Pageable pageable);

    /**
     * 获取最受欢迎的十个菜品
     */
    Page<Food> getMostTenPopularFood();

    /**=================================For Customer================================================*/
    List<Food> getFoodsByMenuId(long menuId);

    List<String> getFoodsPicUrlByFoodIDs(List<Long> foodIDs);

    String getFoodPicUrlByFoodID(Long foodID);
}

package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.RecommendFood;
import com.example.demo.model.admin.RecommendForActivityModel;
import com.example.demo.model.customer.FoodForCustomerRecommendModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendFoodDao extends JpaRepository<RecommendFood,Long> {

    @Query(value = "SELECT new com.example.demo.model.admin.RecommendForActivityModel(" +
            " recommendDood.id, food.picUrl, food.name, menu.name, " +
            " recommendDood.type, recommendDood.enable, " +
            " recommendDood.createTime, recommendDood.updateTime" +
            ") FROM RecommendFood recommendDood" +
            " INNER JOIN Food food" +
            " ON recommendDood.foodID = food.id " +
            " INNER JOIN Menu menu " +
            " ON food.menuID = menu.id")
    Page<RecommendForActivityModel> getAll(String search, Pageable pageable);

    /**=================================For Customer==========================================*/
    @Query(value = "SELECT new com.example.demo.model.customer.FoodForCustomerRecommendModel( " +
            "food.id, food.name, food.picUrl, food.price, recomendFood.type" +
            ") FROM RecommendFood recomendFood " +
            "INNER JOIN Food food " +
            "ON recomendFood.foodID = food.id " +
            "where food.enable = 1")
    List<FoodForCustomerRecommendModel> getCustomerRecommendModel();



}

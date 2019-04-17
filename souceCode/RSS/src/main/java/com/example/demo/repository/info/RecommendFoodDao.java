package com.example.demo.repository.info;

import com.example.demo.domain.info.DiscountRecord;
import com.example.demo.domain.info.RecommendFood;
import com.example.demo.domain.foodInfo.*;
import com.example.demo.model.RecommendForActivityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendFoodDao extends JpaRepository<RecommendFood,Long> {

    @Query(value = "SELECT new com.example.demo.model.RecommendForActivityModel(" +
            " recommendDood.id, food.picUrl, food.name, menu.name, " +
            " recommendDood.type, recommendDood.enable, " +
            " recommendDood.createTime, recommendDood.updateTime" +
            ") FROM RecommendFood recommendDood" +
            " INNER JOIN Food food" +
            " ON recommendDood.foodID = food.id " +
            " INNER JOIN Menu menu " +
            " ON food.menuID = menu.id")
    Page<RecommendForActivityModel> getAll(String search, Pageable pageable);
}

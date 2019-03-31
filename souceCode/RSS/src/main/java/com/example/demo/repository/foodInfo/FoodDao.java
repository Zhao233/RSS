package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDao extends JpaRepository<Food,Long> {

    @Query(value = "SELECT food from Food food where food.menuId = ?1")
    List<Food> getFoodsByMenuId(long id);
}

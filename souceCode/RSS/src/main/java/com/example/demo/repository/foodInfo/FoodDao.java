package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDao extends JpaRepository<Food,Long> {

    @Query(value = "SELECT food FROM Food food where name like %?1%")
    Page<Food> getFoodList(String search, Pageable pageable);

    /**
     * 根据食物母菜单获取食物列表
     * */
    @Query(value = "SELECT food FROM Food food WHERE food.menuId = ?1 and food.name like %?1%")
    Page<Food> getFoodsByMenuId(long id, String search, Pageable pageable);

    /**
     *根据食物id获取该食物下所有的口味id字符串
     * */
    @Query(value = "SELECT food.stylesId FROM Food food WHERE food.id = ?1")
    String getStylesByFoodId(long foodId);

    @Query(value = "update Food food set enable = 1 where food.id = ?1")
    String deleteFoodById(long id);
}

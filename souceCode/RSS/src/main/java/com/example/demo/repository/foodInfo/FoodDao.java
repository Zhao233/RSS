package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.model.FoodForRecommendModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodDao extends JpaRepository<Food,Long> {

    @Query(value = "SELECT food FROM Food food where name like %?1%")
    Page<Food> getFoodList(String search, Pageable pageable);

    /**
     * 根据食物母菜单获取食物列表
     * */
    @Query(value = "SELECT food FROM Food food WHERE food.menuID = ?1 and food.name like %?1%")
    Page<Food> getFoodsByMenuId(long id, String search, Pageable pageable);

    /**
     *根据食物id获取该食物下所有的口味id字符串
     * */
    @Query(value = "SELECT food.stylesID FROM Food food WHERE food.id = ?1")
    String getStylesByFoodId(long foodId);

    //只搜索已经启用的food
    @Query(value = "SELECT new com.example.demo.model.FoodForRecommendModel(" +
            "food.id, food.picUrl, food.name, menu.name) " +
            " FROM Food food " +
            " INNER JOIN Menu menu " +
            " ON food.menuID = menu.id" +
            " WHERE ( food.name like %?1% OR menu.name like %?1% ) And food.enable = 1")
    Page<FoodForRecommendModel> getFoodListForRecommend(String search, Pageable pageable);

}

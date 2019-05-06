package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.model.admin.FoodForRecommendModel;
import com.example.demo.model.customer.FoodForCustomerRecommendModel;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDao extends JpaRepository<Food,Long> {

    @Query(value = "SELECT food FROM Food food where name like %?1%")
    Page<Food> getFoodList(String search, Pageable pageable);


    /**
     * 根据食物母菜单获取食物列表
     */
    @Query(value = "SELECT food FROM Food food WHERE food.menuID = ?1 and food.name like %?1%")
    Page<Food> getFoodsByMenuId(long id, String search, Pageable pageable);

    /**
     * 根据食物母菜单获取食物列表,
     * 只选择启用的
     */
    @Query(value = "SELECT food FROM Food food WHERE food.menuID = ?1 and food.enable = 1")
    List<Food> getFoodsByMenuId(long menuID);

    /**
     * 根据食物id获取该食物下所有的口味id字符串
     */
    @Query(value = "SELECT food.stylesID FROM Food food WHERE food.id = ?1")
    String getStylesByFoodId(long foodId);

    //只搜索已经启用的food
    @Query(value = "SELECT new com.example.demo.model.admin.FoodForRecommendModel(" +
            "food.id, food.picUrl, food.name, menu.name) " +
            " FROM Food food " +
            " INNER JOIN Menu menu " +
            " ON food.menuID = menu.id" +
            " WHERE ( food.name like %?1% OR menu.name like %?1% ) And food.enable = 1")
    Page<FoodForRecommendModel> getFoodListForRecommend(String search, Pageable pageable);

    /**=================================For Customer================================================*/

    @Query(value = "SELECT food.picUrl FROM Food food WHERE food.id in ( :foodIDs )")
    List<String> findFoodByIdIn(@Param("foodIDs") List<Long> foodIDs);

    @Query(value = "SELECT food.picUrl FROM Food food WHERE food.id = ?1")
    String getFoodPicUrlById(Long id);
}

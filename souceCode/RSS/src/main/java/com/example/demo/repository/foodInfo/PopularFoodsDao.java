package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.PopularFoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularFoodsDao extends JpaRepository<PopularFoods,Long> {

    @Query(value = "SELECT * FROM popular_foods ORDER BY popular_foods.id DESC LIMIT 1", nativeQuery = true)
    PopularFoods getLatestPopularFoods();
}

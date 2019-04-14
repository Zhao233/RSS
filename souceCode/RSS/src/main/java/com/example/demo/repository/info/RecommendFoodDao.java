package com.example.demo.repository.info;

import com.example.demo.domain.info.DiscountRecord;
import com.example.demo.domain.info.RecommendFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendFoodDao extends JpaRepository<RecommendFood,Long> {
    @Query(value = "SELECT recommendDood FROM RecommendFood recommendDood")
    Page<RecommendFood> getAll(String search, Pageable pageable);
}

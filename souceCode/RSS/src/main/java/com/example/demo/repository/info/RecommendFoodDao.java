package com.example.demo.repository.info;

import com.example.demo.domain.info.RecommendFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendFoodDao extends JpaRepository<RecommendFood,Long> {
}

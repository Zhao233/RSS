package com.example.demo.repository.info;

import com.example.demo.domain.info.FrequentlyUsedFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrequentlyUsedFoodDao extends JpaRepository<FrequentlyUsedFood,Long> {

    @Query(value ="SELECT frequentlyUsedFood FROM FrequentlyUsedFood frequentlyUsedFood WHERE frequentlyUsedFood.userId = ?1")
    FrequentlyUsedFood getFrequentlyUsedFoodById(Long userID);
}


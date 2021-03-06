package com.example.demo.repository.foodInfo;

import com.example.demo.domain.foodInfo.DiscountRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public interface DiscountRecordDao extends JpaRepository<DiscountRecord,Long> {
    @Query(value = "SELECT discount FROM DiscountRecord discount")
    Page<DiscountRecord> getAll(String search, Pageable pageable);

    /**=================================For Customer=========================================*/
    @Query(value = "SELECT discount FROM DiscountRecord discount WHERE discount.enable = 1")
    LinkedList<DiscountRecord> getAll();
}


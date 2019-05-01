package com.example.demo.repository.info;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.model.customer.OrderRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRecordDao extends JpaRepository<OrderRecord,Long> {

    /**=========================For Customer==================================*/

    @Query(value = "SELECT orderRecord FROM OrderRecord orderRecord WHERE orderRecord.userID = ?1")
    List<OrderRecord> getAllByUserID(Long userID);
}
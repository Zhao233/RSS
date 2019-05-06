package com.example.demo.repository.info;

import com.example.demo.domain.info.WaiterDeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WaiterDeliveryRecordDao extends JpaRepository<WaiterDeliveryRecord,Long> {

    @Query(value = "SELECT waiterDeliveryRecord FROM WaiterDeliveryRecord waiterDeliveryRecord WHERE waiterDeliveryRecord.id = ?1 AND waiterDeliveryRecord.isComplete = 0")
    WaiterDeliveryRecord getOne(Long id);
}


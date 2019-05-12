package com.example.demo.repository.info;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CookerDeliveryRecordDao extends JpaRepository<CookerDeliveryRecord,Long> {

    /**========================================= For Cooker ==========================================================================*/

    @Query(value = "SELECT cookerDeliveryRecord FROM CookerDeliveryRecord cookerDeliveryRecord WHERE cookerDeliveryRecord.id = ?1 AND cookerDeliveryRecord.isComplete = 0")
    CookerDeliveryRecord getOne(Long id);
}


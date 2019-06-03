package com.example.demo.repository.info;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CookerDeliveryRecordDao extends JpaRepository<CookerDeliveryRecord,Long> {
    /**========================================= For Admin ==========================================================================*/

    @Query(value = "SELECT SUM(deliveryRecord) FROM CookerDeliveryRecord deliveryRecord " +
            "WHERE deliveryRecord.isComplete = 1 " +
            "AND deliveryRecord.cookerID = ?3" +
            "AND " +
            "(deliveryRecord.createTime > ?1 AND deliveryRecord.createTime < ?2  )")
    int getDeliveryTime(Timestamp startTime, Timestamp endTime, Long cookerID);

    @Query(value = "SELECT SUM(deliveryRecord) FROM CookerDeliveryRecord deliveryRecord " +
            "WHERE deliveryRecord.isComplete = 1 " +
            "AND deliveryRecord.cookerID = ?1" )
    int getAllDeliveryTimes(Long cookerID);

    @Query(value = "SELECT deliveryRecord FROM CookerDeliveryRecord  deliveryRecord " +
            "WHERE deliveryRecord.isComplete =1 " +
            "AND deliveryRecord.cookerID = ?3"+
            "AND" +
            "(deliveryRecord.createTime > ?1 AND deliveryRecord.createTime < ?2  )")
    List<CookerDeliveryRecord> getAllCookerDeliveryRecord (Timestamp startTime, Timestamp endTime, Long cookerID);

    /**========================================= For Cooker ==========================================================================*/

    @Query(value = "SELECT cookerDeliveryRecord FROM CookerDeliveryRecord cookerDeliveryRecord WHERE cookerDeliveryRecord.id = ?1 AND cookerDeliveryRecord.isComplete = 0")
    CookerDeliveryRecord getOne(Long id);


}


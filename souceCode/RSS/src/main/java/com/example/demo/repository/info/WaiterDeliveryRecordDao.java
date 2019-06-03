package com.example.demo.repository.info;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WaiterDeliveryRecordDao extends JpaRepository<WaiterDeliveryRecord,Long> {

    /**=============================================== For Admin ================================================================*/

    @Query(value = "SELECT SUM(deliveryRecord) FROM WaiterDeliveryRecord deliveryRecord " +
            "WHERE deliveryRecord.isComplete = 1 " +
            "AND deliveryRecord.waiterID = ?3" +
            "AND" +
            "(deliveryRecord.createTime > ?1 AND deliveryRecord.createTime < ?2  )")
    int getDeliveryTime(Timestamp startTime, Timestamp endTime, Long waiterID);

    @Query(value = "SELECT SUM(deliveryRecord) FROM WaiterDeliveryRecord deliveryRecord " +
            "WHERE deliveryRecord.isComplete = 1 " +
            "AND deliveryRecord.waiterID = ?1 ")
    int getAllDeliveryTimes(Long waiterID);

    @Query(value = "SELECT deliveryRecord FROM WaiterDeliveryRecord  deliveryRecord " +
            "WHERE deliveryRecord.isComplete =1 " +
            "AND deliveryRecord.waiterID = ?3"+
            "AND" +
            "(deliveryRecord.createTime > ?1 AND deliveryRecord.createTime < ?2  )")
    List<CookerDeliveryRecord> getAllCookerDeliveryRecord (Timestamp startTime, Timestamp endTime, Long waiterID);

    /**=============================================== For Waiter ================================================================*/

    @Query(value = "SELECT waiterDeliveryRecord FROM WaiterDeliveryRecord waiterDeliveryRecord WHERE waiterDeliveryRecord.id = ?1 AND waiterDeliveryRecord.isComplete = 0")
    WaiterDeliveryRecord getOne(Long id);
}


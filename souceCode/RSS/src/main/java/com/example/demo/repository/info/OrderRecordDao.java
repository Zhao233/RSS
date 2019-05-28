package com.example.demo.repository.info;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.model.customer.OrderRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRecordDao extends JpaRepository<OrderRecord,Long> {
    /**=========================For Admin==================================*/

    @Query(value = "SELECT SUM(orderRecord.settlementAmount) FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1 " +
            "AND" +
            "(orderRecord.createTime > ?1 AND orderRecord.createTime < ?2  )")
    Double getAccount(Timestamp startTime, Timestamp endTime);

    @Query(value = "SELECT SUM(orderRecord.settlementAmount) FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1 ")
    Double getAllAccount();

    @Query(value = "SELECT SUM(orderRecord) " +
                   "FROM OrderRecord orderRecord " +
                   "WHERE orderRecord.isPaid = 1" +
                   "AND" +
            "(orderRecord.createTime > ?1 AND orderRecord.createTime < ?2  )")
    int getOrderTimes(Timestamp startTime, Timestamp endTime);

    @Query(value = "SELECT orderRecord " +
            "FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1" +
            "AND" +
            "(orderRecord.createTime > ?1 AND orderRecord.createTime < ?2  )")
    List<OrderRecord> getAllOrderByTime(Timestamp startTime, Timestamp endTime);


    /**===========================================================*/


    /**=========================For Customer==================================*/

    @Query(value = "SELECT orderRecord FROM OrderRecord orderRecord WHERE orderRecord.userID = ?1")
    List<OrderRecord> getAllByUserID(Long userID);
}
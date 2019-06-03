package com.example.demo.repository.info;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.domain.user.Cooker;
import com.example.demo.model.customer.OrderRecordModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRecordDao extends JpaRepository<OrderRecord,Long> {
    /**=========================For Admin==================================*/

    /**
     * 获取某一用户的所有订单记录
     * */
    @Query(value = "SELECT orderRecord FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1 " +
            "AND orderRecord.userID = ?1")
    Page<OrderRecord> getAllByUserID(Long userID, Pageable pageable);

    /**
     * 获取一段时间内的订单总金额
     * */
    @Query(value = "SELECT SUM(orderRecord.settlementAmount) FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1 " +
            "AND" +
            "(orderRecord.createTime > ?1 AND orderRecord.createTime < ?2  )")
    Double getAccount(Timestamp startTime, Timestamp endTime);

    @Query(value = "SELECT SUM(orderRecord.settlementAmount) FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1 " +
            "AND orderRecord.userID = ?3 " +
            "AND" +
            "(orderRecord.createTime > ?1 AND orderRecord.createTime < ?2  )")
    Double getAccount(Timestamp startTime, Timestamp endTime, Long customerID);

    /**
     * 获取订单总金额
     * */
    @Query(value = "SELECT SUM(orderRecord.settlementAmount) FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1 ")
    Double getAllAccount();
    @Query(value = "SELECT SUM(orderRecord.settlementAmount) FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1 " +
            "AND orderRecord.userID = ?1")
    Double getAllAccount(Long id);

    /**
     * 获取一定时间内的订单次数
     * */
    @Query(value = "SELECT SUM(orderRecord) " +
            "FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1" +
            "AND" +
            "(orderRecord.createTime > ?1 AND orderRecord.createTime < ?2  )")
    int getOrderTimes(Timestamp startTime, Timestamp endTime);

    /**
     * 获取一定时间内的所有订单
     * */
    @Query(value = "SELECT orderRecord " +
            "FROM OrderRecord orderRecord " +
            "WHERE orderRecord.isPaid = 1" +
            "AND" +
            "(orderRecord.createTime > ?1 AND orderRecord.createTime < ?2  )")
    List<OrderRecord> getAllOrderByTime(Timestamp startTime, Timestamp endTime);

    /**=========================For Customer==================================*/

    @Query(value = "SELECT orderRecord FROM OrderRecord orderRecord WHERE orderRecord.userID = ?1")
    List<OrderRecord> getAllByUserID(Long userID);
}
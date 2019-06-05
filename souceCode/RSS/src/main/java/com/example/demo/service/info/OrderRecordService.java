package com.example.demo.service.info;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.domain.user.Cooker;
import com.example.demo.model.customer.OrderRecordModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public interface OrderRecordService {
    /**================================For Admin========================================================*/
    /**
     * 获取消费额
     *
     * type：
     * 1 月消费额
     * 2 星期消费额
     * 3 今日消费额
     */
    Double getAccount(int type);
    Double getAccount(int type, Long id);


    /**
     * type:
     * 1 总点餐次数
     * 2 本星期点餐次数
     * 3 今日点餐次数
     * */
    List<Integer> getOrderTime(Timestamp startTime);

    /**
     * 获取所有已支付的订单
     * */
    HashMap<Long, Integer> getAllOrderByTime(int type);

    /**
     * 获取某一客户所有已支付的订单
     * */
    Page<OrderRecord> getAllByUserID(int offset, int limit, Long userID);

    Long addOne(List<Long> foodIDList, List<Integer> foodNumList, List<Long> styleIDList, Long discountID, String openid, long expirationTime, Double account, Integer tableNum);

    List<OrderRecordModel> getAllOrderRecordModel(String openID);

    OrderRecord getOrderRecordByID(Long orderRecord);

}
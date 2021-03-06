package com.example.demo.domain.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

@Entity
@Component
@Table(name = "waiter_delivery_record")
@Data
public class WaiterDeliveryRecord {
    public static final int TYPE_DELIVERY = 1;
    public static final int TYPE_SERVICE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * 服务类型
     * 1: 服务(呼叫服务员)
     * 2: 送餐
     * */
    @Column(name = "type")
    private Integer type;

    /**
     * 用户的openid
     *
     * 当配送类型为送餐时，设置此字段
     * */
    @Column(name = "open_id")
    private String openID;

    /**
     * 桌号
     * */
    @Column(name = "table_num")
    private Integer tableNum;

    /**
     * 服务员ID
     * */
    @Column(name = "waiter_id")
    private Long waiterID;

    /**
     * 订单ID
     * */
    @Column(name = "order_record_id")
    private long orderRecordID;

    /**
     * 菜品ID
     * */
    @Column(name = "food_id")
    private long foodID;

    /**
     * 是否已完成
     * */
    @Column(name = "is_complete")
    private int isComplete = 0;

    /**
     * 创建时间
     * */
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 完成时间
     * */
    @Column(name = "update_time")
    private Timestamp updateTime;

    public static final int TYPE_SERVICE_MONTH = 1;
    public static final int TYPE_SERVICE_WEEK  = 2;
    public static final int TYPE_SERVICE_DAY   = 3;
    public static final int TYPE_SERVICE_ALL   = 4;
}

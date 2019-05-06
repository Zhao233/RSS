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
     * 桌号
     * */
    @Column(name = "table_num")
    private Integer tableNum;

    @Column(name = "waiter_id")
    private Long waiterID;

    @Column(name = "order_record_id")
    private long orderRecordID;

    @Column(name = "food_id")
    private long foodID;

    @Column(name = "is_complete")
    private int isComplete = 0;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}

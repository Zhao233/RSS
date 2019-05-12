package com.example.demo.domain.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

@Entity
@Component
@Table(name = "cooker_delivery_record")
@Data
public class CookerDeliveryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 订单ID
     * */
    @Column(name = "order_record_id")
    private Long orderRecordId;

    /**
     * 厨师ID
     * */
    @Column(name = "cooker_id")
    private Long cookerID;

    /**
     * 桌号
     * */
    @Column(name = "table_num")
    private Integer tableNum;

    /**
     * 菜品ID
     * */
    @Column(name = "food_id")
    private Long foodId;

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
}

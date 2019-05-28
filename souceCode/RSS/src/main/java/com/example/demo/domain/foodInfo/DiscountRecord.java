package com.example.demo.domain.foodInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

@Entity
@Component
@Table(name = "discount_record")
@Data
public class DiscountRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "discount")
    private Double discount;

    /**
     * 生效金额，当结算金额大于生效金额时，满减起效，当type为折扣时，此字段不生效
     * */
    @Column(name = "money_limit")
    private Double moneyLimit;

    /**
     * 0: 折扣
     * 1: 满减
     * */
    @Column(name = "type")
    private Integer type = 0;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "end_time")
    private Timestamp endTime;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    /**
     * 1: 生效
     * 0: 不生效
     * 默认生效
     * */
    @Column(name = "enable")
    private Integer enable;

    /**
     * 折扣
     * */
    public static int DISCOUNT = 0;

    /**
     * 满减
     * */
    public static int DISCOUNT_ReachedLimitedMoney = 1;

}

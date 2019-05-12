package com.example.demo.domain.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

@Entity
@Component
@Table(name = "order_record")
@Data
public class OrderRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userID;

    @Column(name = "foods_id")
    private String foodsID;

    @Column(name = "foods_num")
    private String foodsNum;

    @Column(name = "styles_id")
    private String stylesID;

    @Column(name = "settlement_amount")
    private double settlementAmount;

    @Column(name = "origin_amount")
    private double originAmount;

    @Column(name = "discount_id")
    private long discountID;

    /**
     * 0,未支付
     * 1，已支付
     * */
    @Column(name = "is_paid")
    private Integer isPaid;

    @Column(name = "expiration_time")
    private Timestamp expirationTime;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;


    /**
     * status
     * */
    public final static int DONE = 1;
    public final static int TIMEOUT = 2;
    public final static int NOT_PAID = 0;

    /**
     * 获取支付信息时的类型
     * */
    public static final int TYPE_ACCOUNT_MONTH = 1;
    public static final int TYPE_ACCOUNT_WEEK = 2;
    public static final int TYPE_ACCOUNT_DAY = 3;

    /***
     * 获取订单次数的的类型
     */
    public static final int TYPE_ORDERTIME_DAY = 3;

    /***
     * 获取最受欢迎的菜品的类型
     */
    public static final int TYPE_POPULARFOODS_DAY = 3;
}

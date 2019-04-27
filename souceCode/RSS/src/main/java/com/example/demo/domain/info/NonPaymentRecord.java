package com.example.demo.domain.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

@Entity
@Component
@Table(name = "non_payment_record")
@Data
public class NonPaymentRecord {
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

    @Column(name = "preference_id")
    private long preferenceID;

    @Column(name = "discount_id")
    private long discountID;

    @Column(name = "expiration_time")
    private Timestamp expirationTime;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}

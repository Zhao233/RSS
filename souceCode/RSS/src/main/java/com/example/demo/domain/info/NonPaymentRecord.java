package com.example.demo.domain.info;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Component
@Table(name = "non_payment_record")
@Data
public class NonPaymentRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "foods_id")
    private String foodsId;

    @Column(name = "settlement_amount")
    private double settlementAmount;

    @Column(name = "origin_amount")
    private double originAmount;

    @Column(name = "preference_id")
    private long preferenceId;

    @Column(name = "discount_id")
    private long discountId;

    @Column(name = "expiration_time")
    private Timestamp expirationTime;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}

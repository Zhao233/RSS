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

    @Column(name = "create_time")
    private Timestamp createTime;
}

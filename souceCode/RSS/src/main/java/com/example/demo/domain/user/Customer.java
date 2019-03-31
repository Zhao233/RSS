package com.example.demo.domain.user;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Component
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_id")
    private String userid;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "log_in_times")
    private int logInTimes;

    @Column(name = "level")
    private int level;

    @Column(name = "credit")
    private int credit;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}

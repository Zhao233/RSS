package com.example.demo.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

@Entity
@Component
@Table(name = "customer")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "open_id")
    private String openID;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "log_in_times")
    private Integer logInTimes = 0;

    @Column(name = "level")
    private Integer level = 0;

    @Column(name = "credit")
    private Integer credit = 0;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}

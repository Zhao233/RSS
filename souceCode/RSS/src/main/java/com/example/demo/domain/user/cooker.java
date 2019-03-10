package com.example.demo.domain.user;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Component
@Table(name = "cooker")
@Data
public class cooker {
    /**
     * 数据库id
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    /**
     * 微信id
     * */
    @Column(name = "user_id")
    private String userid;
    /**
     * 微信名
     * */
    @Column(name = "user_name")
    private String userName;

    @Column(name = "log_in_times")
    private int logInTimes;

    @Column(name = "enable")
    private boolean enable;
    /**
     * 厨师的身份
     * 0：熟食
     * 1：凉品
     * 2：糕点
     * */
    @Column(name = "role")
    private int role;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}

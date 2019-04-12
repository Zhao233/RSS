package com.example.demo.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })

@Entity
@Component
@Table(name = "cooker")
@Data
public class Cooker {
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
    private String userID;

    /**
     * 微信名
     * */
    @Column(name = "name")
    private String name;


    /**
     * 登录凭证
     * */
    @Column(name = "login_id")
    private String loginID;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "log_in_times")
    private Integer loginTimes = 0;

    @Column(name = "enable")
    private Integer enable = 1;

    /**
     * 厨师的身份
     * 0：熟食
     * 1：凉品
     * 2：糕点
     * */
    @Column(name = "role")
    private long role;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;

    /**
     *在数据库中不起作用，用处仅在于向前端传输值时，省去另加的空间
     */
    @Column(name = "role_name")
    private String roleName;
}

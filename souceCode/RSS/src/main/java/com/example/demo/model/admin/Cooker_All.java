package com.example.demo.model.admin;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
public class Cooker_All {

    public Cooker_All(long id, String userId, String name, String loginID, String phoneNumber, Integer logInTimes, Integer enable, String role, Timestamp createTime, Timestamp updateTime) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.loginID = loginID;
        this.phoneNumber = phoneNumber;
        this.logInTimes = logInTimes;
        this.enable = enable;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    private long id;

    private String userId;

    private String name;

    private String loginID;

    private String phoneNumber;

    private Integer logInTimes = 0;

    private Integer enable = 1;

    private String role;

    private Timestamp createTime;

    private Timestamp updateTime;


}

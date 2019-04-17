package com.example.demo.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class RecommendForActivityModel {
    public RecommendForActivityModel(long id, String picUrl, String name, String menuName, int type, int enable, Date createTime, Date updateTime) {
        this.id = id;
        this.picUrl = picUrl;
        this.name = name;
        this.menuName = menuName;
        this.type = type;
        this.enable = enable;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    private long      id;
    private String    picUrl;
    private String    name;
    private String    menuName;
    private int       type;
    private int       enable;
    private Date createTime;
    private Date updateTime;
}

package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Activity_Recommend_All{
    public Activity_Recommend_All(long id, String picUrl, String name, String menuName) {
        this.id = id;
        this.picUrl = picUrl;
        this.name = name;
        this.menuName = menuName;
    }

    private long id;
    private String picUrl;
    private String name;
    private String menuName;
}

package com.example.demo.model;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Data
public class FoodForRecommendModel{
    public FoodForRecommendModel(long id, String picUrl, String name, String menuName) {
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

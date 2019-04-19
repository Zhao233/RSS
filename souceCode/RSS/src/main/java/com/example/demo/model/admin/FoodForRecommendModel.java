package com.example.demo.model.admin;

import lombok.Data;


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

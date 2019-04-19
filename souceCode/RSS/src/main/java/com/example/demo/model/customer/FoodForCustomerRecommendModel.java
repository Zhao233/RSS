package com.example.demo.model.customer;

import lombok.Data;

@Data
public class FoodForCustomerRecommendModel {
    public FoodForCustomerRecommendModel(Long foodID, String name, String picUrl, Double price, Integer type) {
        this.foodID = foodID;
        this.name = name;
        this.picUrl = picUrl;
        this.price = price;
        this.type = type;
    }

    private Long foodID;
    private String name;
    private String picUrl;
    private Double price;
    private Integer type;
}

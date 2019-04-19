package com.example.demo.model.customer;

import lombok.Data;

@Data
public class FoodForCustomerRecommendModel {
    public FoodForCustomerRecommendModel(Long foodID, String name, String picUrl, Double price) {
        this.foodID = foodID;
        this.name = name;
        this.picUrl = picUrl;
        this.price = price;
    }

    private Long foodID;
    private String name;
    private String picUrl;
    private Double price;
}

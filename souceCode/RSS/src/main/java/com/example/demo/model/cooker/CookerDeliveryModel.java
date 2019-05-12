package com.example.demo.model.cooker;

import lombok.Data;

@Data
public class CookerDeliveryModel {
    private Long orderID;
    private int tableNum;
    private String foodPicUrl;
    private String createTime;
}

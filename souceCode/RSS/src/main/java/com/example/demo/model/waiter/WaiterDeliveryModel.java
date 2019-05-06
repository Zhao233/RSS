package com.example.demo.model.waiter;

import lombok.Data;

@Data
public class WaiterDeliveryModel {
    private Long orderID;
    private String type;
    private int tableNum;
    private String foodPicUrl;
    private String createTime;
}

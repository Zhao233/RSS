package com.example.demo.model.waiter;

import lombok.Data;

@Data
public class WaiterDeliveryModel {
    private Long orderID;
    private Integer type;
    private int tableNum;
    private String foodPicUrl;
    private String createTime;
}

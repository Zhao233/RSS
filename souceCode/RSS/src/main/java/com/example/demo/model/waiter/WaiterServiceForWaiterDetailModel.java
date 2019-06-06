package com.example.demo.model.waiter;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class WaiterServiceForWaiterDetailModel {
    private String serviceType;
    private String picUrl;
    private Timestamp completeTime;
}

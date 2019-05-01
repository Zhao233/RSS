package com.example.demo.model.customer;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 用户在获取订单记录时使用此model
 * */
@Data
public class OrderRecordModel {
    private Long id;

    private Integer status;
    private List<String> picURL_foods;

    private String createDate;

    private Double account_final;
}

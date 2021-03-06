package com.example.demo.model.customer;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.foodInfo.Style;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class FoodForCustomerFrequentlyModel {
    public FoodForCustomerFrequentlyModel(Long id, Long userID, List<Food> foodList, List<Style> styleList, List<Integer> numList) {
        this.id = id;
        this.userID = userID;
        this.foodList = foodList;
        this.styleList = styleList;
        this.numList = numList;
    }

    private Long id;
    private Long userID;
    private List<Food> foodList;
    private List<Style> styleList;
    private List<Integer> numList;
}

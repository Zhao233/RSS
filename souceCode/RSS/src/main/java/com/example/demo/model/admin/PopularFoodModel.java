package com.example.demo.model.admin;

import com.example.demo.domain.foodInfo.Food;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class PopularFoodModel {
    List<String>  foodNames = new LinkedList<>();
    List<String>  foodUrls = new LinkedList<>();
    List<Integer> foodNum = new LinkedList<>();
}

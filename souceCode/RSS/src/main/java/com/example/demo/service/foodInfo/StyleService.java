package com.example.demo.service.foodInfo;

import com.example.demo.domain.foodInfo.Style;

import java.util.List;

public interface StyleService {
    List<Style> getStylesByFoodId(long foodId);
    List<Style> getAllStyles();
}

package com.example.demo.service.info;

import com.example.demo.domain.info.PopularFoods;
import com.example.demo.model.admin.PopularFoodModel;

public interface PopularFoodsService {
    /**========================For Admin=============================*/
    void addOne(PopularFoods popularFoods);

    PopularFoodModel getPopularFood();
}

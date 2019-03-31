package com.example.demo.service.foodInfo.imp;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.repository.foodInfo.FoodDao;
import com.example.demo.service.foodInfo.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("foodService")
public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodDao foodDao;

    @Override
    public List<Food> getFoodsByMenuId(long menuId) {
        try {
            return foodDao.getFoodsByMenuId(menuId);
        } catch (Exception e){
            return new LinkedList<Food>();
        }
    }
}

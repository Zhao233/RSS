package com.example.demo.service.foodInfo.imp;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.repository.foodInfo.FoodDao;
import com.example.demo.service.foodInfo.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("foodService")
public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodDao foodDao;

    @Override
    public Page<Food> getFoodsByMenuId(long menuId, String search, Pageable pageable) {
        return foodDao.getFoodsByMenuId(menuId, search, pageable);

    }

    @Override
    public Page<Food> getFoodList(String search, Pageable pageable) {
        if(search.equals("")){
            return foodDao.findAll(pageable);
        } else {
            return foodDao.getFoodList(search, pageable);
        }
    }

    @Override
    public Food getOne(long id) {
        return foodDao.getOne(id);
    }

    @Override
    public void addFood(Food food) {
        foodDao.save(food);
    }

    @Override
    public void updateFood(Food food) {
        foodDao.save(food);
    }

    @Override
    public void deleteFood(long id) {
        foodDao.deleteById(id);
    }
}

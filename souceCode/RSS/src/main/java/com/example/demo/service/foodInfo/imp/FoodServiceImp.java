package com.example.demo.service.foodInfo.imp;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.model.admin.FoodForRecommendModel;
import com.example.demo.repository.foodInfo.FoodDao;
import com.example.demo.service.foodInfo.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("foodService")
public class FoodServiceImp implements FoodService {
    @Autowired
    private FoodDao foodDao;

    @Override
    public Page<Food> getFoodsByMenuId(long menuId, String search, Pageable pageable) {
        return foodDao.getFoodsByMenuId(menuId, search, pageable);

    }

    /**=========================================For Admin====================================================*/
    @Override
    public Page<Food> getMostTenPopularFood() {
        return null;
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

    @Override
    public Page<FoodForRecommendModel> getFoodListForRecommend(String search, Pageable pageable) {
        return foodDao.getFoodListForRecommend(search, pageable);
    }


    /**=========================================For Customer====================================================*/

    @Override
    public List<Food> getFoodsByMenuId(long menuId) {
        return foodDao.getFoodsByMenuId(menuId);
    }

    /**
     * 获取一个id列表下对应的图片地址
     * foodIDs ： "1,2,4"
     * */
    @Override
    public List<String> getFoodsPicUrlByFoodIDs(List<Long> foodIDs) {
        return foodDao.findFoodByIdIn(foodIDs);
    }

    @Override
    public String getFoodPicUrlByFoodID(Long foodID) {
        return foodDao.getFoodPicUrlById(foodID);
    }
}

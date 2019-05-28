package com.example.demo.service.info.imp;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.foodInfo.PopularFoods;
import com.example.demo.model.admin.PopularFoodModel;
import com.example.demo.repository.foodInfo.FoodDao;
import com.example.demo.repository.info.PopularFoodsDao;
import com.example.demo.service.info.PopularFoodsService;
import com.example.demo.util.StringTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("popularFoodsService")
public class PopularFoodsServiceImp implements PopularFoodsService {
    @Autowired
    private PopularFoodsDao popularFoodsDao;

    @Autowired
    private FoodDao foodDao;

    /**========================For Admin=============================*/

    @Override
    public void addOne(PopularFoods popularFoods) {
        popularFoodsDao.save(popularFoods);
    }

    @Override
    public PopularFoodModel getPopularFood() {
        PopularFoods popularFoods = new PopularFoods();

        List<Long> foodIDList = new LinkedList<>();
        List<String> foodName = new LinkedList<>();
        List<String> foodUrl = new LinkedList<>();

        PopularFoodModel popularFoodModel = new PopularFoodModel();

        popularFoods =  popularFoodsDao.getLatestPopularFoods();
        foodIDList = StringTranslator.getListFromString(popularFoods.getFoodIDs(), 0);

        for(Long temp_foodID : foodIDList){
            Food temp_food = new Food();

            temp_food = foodDao.getOne(temp_foodID);
            foodName.add( temp_food.getName() );
            foodUrl.add( temp_food.getPicUrl() );
        }

        popularFoodModel.setFoodNames(foodName);
        popularFoodModel.setFoodUrls(foodUrl);

        return popularFoodModel;
    }
}

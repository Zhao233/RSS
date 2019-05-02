package com.example.demo.service.info.imp;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.foodInfo.Style;
import com.example.demo.domain.info.FrequentlyUsedFood;
import com.example.demo.model.customer.FoodForCustomerFrequentlyModel;
import com.example.demo.repository.foodInfo.FoodDao;
import com.example.demo.repository.foodInfo.StyleDao;
import com.example.demo.repository.info.FrequentlyUsedFoodDao;
import com.example.demo.repository.user.CustomerDao;
import com.example.demo.service.info.FrequentlyUsedFoodService;
import com.example.demo.util.StringTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("frequentlyUsedFoodService")
public class FrequentlyUsedFoodServiceImp implements FrequentlyUsedFoodService {
    @Autowired
    private FrequentlyUsedFoodDao frequentlyUsedFoodDao;

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private StyleDao styleDao;

    @Autowired
    private CustomerDao customerDao;

    @Override
    public FoodForCustomerFrequentlyModel getFrequentlyUsedFoodByUserID(String openID) {
        FrequentlyUsedFood frequentlyUsedFoodList = frequentlyUsedFoodDao.getFrequentlyUsedFoodById(openID);
        List<FoodForCustomerFrequentlyModel> modelList = new LinkedList<>();

        List<Long> foodIDList = StringTranslator.getListFromString(frequentlyUsedFoodList.getFoodsId(), 0);
        List<Long> styleIDList = StringTranslator.getListFromString(frequentlyUsedFoodList.getStylesId(), 0);
        List<Integer> nums = StringTranslator.getListFromString(frequentlyUsedFoodList.getNums(),1);

        List<Food> foodList = new LinkedList<>();
        List<Style> styleList = new LinkedList<>();

        for (int i = 0; i < foodIDList.size(); i++) {
            foodList.add(foodDao.getOne(foodIDList.get(i)));
            styleList.add(styleDao.getOne(styleIDList.get(i)));
        }

        FoodForCustomerFrequentlyModel temp_foodModel = new FoodForCustomerFrequentlyModel(frequentlyUsedFoodList.getId(), frequentlyUsedFoodList.getUserID(), foodList, styleList, nums);

        return temp_foodModel;
        }

    @Override
    public boolean addFoodToFrequentlyUsedFoodList(String openID, Long foodID, Long styID) {
        FrequentlyUsedFood frequentlyUsedFoodList = frequentlyUsedFoodDao.getFrequentlyUsedFoodById(openID);

        String foodIDs = frequentlyUsedFoodList.getFoodsId();
        String styleIDs = frequentlyUsedFoodList.getStylesId();
        String nums = frequentlyUsedFoodList.getNums();

        String[] foodIDs_ = foodIDs.split("_");
        String[] styleIDs_ = styleIDs.split("_");

        for(String temp_foodID : foodIDs_){
            if( temp_foodID.equals( String.valueOf(foodID) ) ){
                return false;
            }
        }

        foodIDs += "_"+foodID;
        styleIDs += "_1";
        nums += "_1";

        frequentlyUsedFoodList.setFoodsId(foodIDs);
        frequentlyUsedFoodList.setStylesId(styleIDs);
        frequentlyUsedFoodList.setNums(nums);

        frequentlyUsedFoodDao.save(frequentlyUsedFoodList);

        return true;
    }


}

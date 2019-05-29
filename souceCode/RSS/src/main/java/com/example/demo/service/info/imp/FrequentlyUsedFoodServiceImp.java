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
import com.example.demo.util.TimeUtil;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("frequentlyUsedFoodService")
public class FrequentlyUsedFoodServiceImp implements FrequentlyUsedFoodService {
    @Autowired
    private FrequentlyUsedFoodDao frequentlyUsedFoodDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private StyleDao styleDao;

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

        Long customerID = customerDao.getIdByOpenID(openID);

//        if(frequentlyUsedFoodList == null){
//            frequentlyUsedFoodList = new FrequentlyUsedFood();
//
//            frequentlyUsedFoodList.setFoodsId(String.valueOf(foodID));
//            frequentlyUsedFoodList.setStylesId(String.valueOf(0));
//            frequentlyUsedFoodList.setNums("1");
//            frequentlyUsedFoodList.setCreateTime(TimeUtil.getTimeNow());
//            frequentlyUsedFoodList.setUserID(customerID);
//
//            frequentlyUsedFoodDao.save(frequentlyUsedFoodList);
//            return frequentlyUsedFoodList.getFoodsId() != null;
//        }

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

        if(foodIDs.length() == 0){
            foodIDs += foodID;
            styleIDs += "1";
            nums += "1";
        } else {
            foodIDs += "_"+foodID;
            styleIDs += "_1";
            nums += "_1";
        }

        frequentlyUsedFoodList.setFoodsId(foodIDs);
        frequentlyUsedFoodList.setStylesId(styleIDs);
        frequentlyUsedFoodList.setNums(nums);

        frequentlyUsedFoodDao.save(frequentlyUsedFoodList);

        return true;
    }

    @Override
    public void saveFrequentlyUsedFoodList(String openID, String foodIDs, String styleIDs, String nums){
        List<Long> list_foodIDs = new LinkedList<>();
        List<Long> list_styleIDs = new LinkedList<>();
        List<Integer> list_nums = new LinkedList<>();

        FrequentlyUsedFood frequentlyUsedFood = frequentlyUsedFoodDao.getFrequentlyUsedFoodById(openID);

        JSONArray jsonArray_foodIDs = JSONArray.fromObject(foodIDs);
        JSONArray jsonArray_styleIDs = JSONArray.fromObject(styleIDs);
        JSONArray jsonArray_numsIDs = JSONArray.fromObject(nums);

        for(int i = 0; i < jsonArray_foodIDs.size(); i++){
            Long foodID = jsonArray_foodIDs.getLong(i);
            Long styleID = jsonArray_styleIDs.getLong(i);
            Integer num = jsonArray_numsIDs.getInt(i);

            list_foodIDs.add(foodID);
            list_styleIDs.add(styleID);
            list_nums.add(num);
        }


        frequentlyUsedFood.setFoodsId(StringTranslator.getStringFromList(list_foodIDs));
        frequentlyUsedFood.setStylesId(StringTranslator.getStringFromList(list_styleIDs));
        frequentlyUsedFood.setNums(StringTranslator.getStringFromList(list_nums));
        frequentlyUsedFood.setUpdateTime(TimeUtil.getTimeNow());

        frequentlyUsedFoodDao.save(frequentlyUsedFood);
    }

    @Override
    public void saveEmptyFrequentlyUsedFoodList(Long userID){
        FrequentlyUsedFood frequentlyUsedFood = new FrequentlyUsedFood();

        frequentlyUsedFood.setFoodsId("");
        frequentlyUsedFood.setNums("");
        frequentlyUsedFood.setStylesId("");
        frequentlyUsedFood.setUserID(userID);
        frequentlyUsedFood.setCreateTime(TimeUtil.getTimeNow());

        frequentlyUsedFoodDao.save(frequentlyUsedFood);
    }
}

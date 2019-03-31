package com.example.demo.service.foodInfo.imp;

import com.example.demo.domain.foodInfo.Style;
import com.example.demo.repository.foodInfo.FoodDao;
import com.example.demo.repository.foodInfo.StyleDao;
import com.example.demo.service.foodInfo.StyleService;
import com.example.demo.util.StringTranslater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("styleService")
public class StyleServiceImp implements StyleService {
    @Autowired
    private StyleDao styleDao;

    @Autowired
    private FoodDao foodDao;

    @Autowired
    private StringTranslater<Long> stringTranslater;

    @Override
    public List<Style> getStylesByFoodId(long foodId) {
        try {
            List<Long> styleIdList;
            List<Style> styleList = new LinkedList<>();
            String stylesString = foodDao.getStylesByFoodId(foodId);

            styleIdList = stringTranslater.getListFromString(stylesString,0);

            for(Long styleId : styleIdList){
                Style style = styleDao.getOne(styleId);

                styleList.add(style);
            }

            return styleList;
        } catch (Exception e){
            return new LinkedList<>();
        }
    }

    @Override
    public List<Style> getAllStyles() {
        try {
            return styleDao.findAll();
        } catch (Exception e){
            return new LinkedList<>();
        }
    }
}

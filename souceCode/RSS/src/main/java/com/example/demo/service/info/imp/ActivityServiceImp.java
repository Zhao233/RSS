package com.example.demo.service.info.imp;

import com.example.demo.domain.info.DiscountRecord;
import com.example.demo.domain.info.RecommendFood;
import com.example.demo.repository.info.DiscountRecordDao;
import com.example.demo.repository.info.RecommendFoodDao;
import com.example.demo.service.info.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("activityService")
public class ActivityServiceImp implements ActivityService {
    @Autowired
    private DiscountRecordDao discountRecordDao;

    @Autowired
    private RecommendFoodDao recommendFoodDao;


    @Override
    public Page<RecommendFood> getAll_Recommend(String search, Pageable pageable) {
        return recommendFoodDao.getAll(search, pageable);
    }

    @Override
    public Page<DiscountRecord> getAll_Discount(String search, Pageable pageable) {
        return discountRecordDao.getAll(search, pageable);
    }

    @Override
    public RecommendFood getOne_Recommend(long id) {
        return recommendFoodDao.getOne(id);
    }

    @Override
    public DiscountRecord getOne_Discount(long id) {
        return discountRecordDao.getOne(id);
    }

    @Override
    public void addOne_Recommend(RecommendFood recommendFood) {
        recommendFoodDao.save(recommendFood);
    }

    @Override
    public void addOne_Discount(DiscountRecord discountRecord) {
        discountRecordDao.save(discountRecord);
    }

    @Override
    public void updateOne_Recommend(RecommendFood recommendFood) {
        recommendFoodDao.save(recommendFood);
    }

    @Override
    public void updateOne_Discount(DiscountRecord discountRecord) {
        discountRecordDao.save(discountRecord);
    }

    @Override
    public void deleteOne_Recommend(long id) {
        recommendFoodDao.deleteById(id);
    }

    @Override
    public void deleteOne_Discount(long id) {
        discountRecordDao.deleteById(id);
    }
}

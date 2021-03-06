package com.example.demo.service.info;

import com.example.demo.domain.foodInfo.DiscountRecord;
import com.example.demo.domain.foodInfo.RecommendFood;
import com.example.demo.model.admin.RecommendForActivityModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityService {
    Page<RecommendForActivityModel> getAll_Recommend(String search, Pageable pageable);
    Page<DiscountRecord> getAll_Discount(String search, Pageable pageable);

    RecommendFood getOne_Recommend(long id);
    DiscountRecord getOne_Discount(long id);

    void addOne_Recommend(RecommendFood recommendFood);
    void addOne_Discount(DiscountRecord discountRecord);

    void updateOne_Recommend(RecommendFood recommendFood);
    void updateOne_Discount(DiscountRecord discountRecord);

    void deleteOne_Recommend(long id);
    void deleteOne_Discount(long id);
}

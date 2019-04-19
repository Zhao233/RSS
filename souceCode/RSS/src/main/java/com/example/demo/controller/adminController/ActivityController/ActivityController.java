package com.example.demo.controller.adminController.ActivityController;

import com.example.demo.domain.info.DiscountRecord;
import com.example.demo.domain.info.RecommendFood;
import com.example.demo.model.admin.FoodForRecommendModel;
import com.example.demo.model.admin.RecommendForActivityModel;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.info.ActivityService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private FoodService foodService;

    @ResponseBody
    @RequestMapping(value = "/getAll_Recommend")
    public Map<String, Object> getAll_Recommend(@RequestParam(name = "search") String search,
                                                @RequestParam(name = "offset") Integer offset,
                                                @RequestParam(name = "limit") Integer limit )  {
        Map<String, Object> map = new HashMap();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<RecommendForActivityModel> page;

        page = activityService.getAll_Recommend(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getAll_Discount")
    public Map<String, Object> getAll_Discount(@RequestParam(name = "search") String search,
                                               @RequestParam(name = "offset") Integer offset,
                                               @RequestParam(name = "limit") Integer limit )  {
        Map<String, Object> map = new HashMap();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<DiscountRecord> page;

        page = activityService.getAll_Discount(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllFood")
    public Map<String, Object> getAll_food(@RequestParam(name = "search") String search,
                                           @RequestParam(name = "offset") Integer offset,
                                           @RequestParam(name = "limit") Integer limit){
        Map<String, Object> map = new HashMap();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<FoodForRecommendModel> page;

        page = foodService.getFoodListForRecommend(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getRecommend/{id}")
    public Map<String, Object> getOneById_Recommend(@PathVariable Long id)  {
        HashMap<String, Object> map = new HashMap<>();

        RecommendFood recommendFood = activityService.getOne_Recommend(id);

        map.put("recommend",recommendFood);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getDiscount/{id}")
    public Map<String, Object> getOneById_Discount(@PathVariable Long id)  {
        HashMap<String, Object> map = new HashMap<>();

        DiscountRecord discountRecord = activityService.getOne_Discount(id);

        map.put("discount",discountRecord);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/recommend/add")
    public Map<String, Object> addOne_Recommend(@RequestParam(name = "foodID") List<Long> foodID,
                                                @RequestParam(name = "type") Integer type,
                                                @RequestParam(name = "enable") Integer enable)  {
        Map<String, Object> map = new HashMap();

        for(Long id : foodID){
            RecommendFood recommendFood = new RecommendFood();

            recommendFood.setFoodID(id);
            recommendFood.setType(type);
            recommendFood.setEnable(enable);
            recommendFood.setCreateTime(TimeUtil.getTimeNow());

            activityService.addOne_Recommend(recommendFood);
        }


        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/discount/add")
    public Map<String, Object> addOne_Discount(@RequestParam(name = "discount") Double discount,
                                               @RequestParam(name = "moneyLimit") Double moneyLimit,
                                               @RequestParam(name = "type") Integer type,
                                               @RequestParam(name = "startTime") String startTime,
                                               @RequestParam(name = "endTime") String endTime,
                                               @RequestParam(name = "enable") Integer enable)  {
        Map<String, Object> map = new HashMap();

        DiscountRecord discountRecord = new DiscountRecord();
        discountRecord.setDiscount(discount);
        discountRecord.setMoneyLimit(moneyLimit);
        discountRecord.setType(type);
        discountRecord.setCreateTime(TimeUtil.getTimeNow());
        discountRecord.setStartTime(TimeUtil.StringToTimeStamp(startTime));
        discountRecord.setEndTime(TimeUtil.StringToTimeStamp(endTime));
        discountRecord.setEnable(enable);

        activityService.addOne_Discount(discountRecord);

        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/recommend/update")
    public Map<String, Object> updateOne_Recommend(@RequestParam(name = "id") Long id,
                                                   @RequestParam(name = "foodID") Long foodID,
                                                   @RequestParam(name = "type") Integer type,
                                                   @RequestParam(name = "createTime") String createTime,
                                                   @RequestParam(name = "enable") Integer enable)  {
        Map<String, Object> map = new HashMap();

        RecommendFood recommendFood = new RecommendFood();
        recommendFood.setId(id);
        recommendFood.setFoodID(foodID);
        recommendFood.setCreateTime(TimeUtil.StringToTimeStamp(createTime));
        recommendFood.setUpdateTime(TimeUtil.getTimeNow());
        recommendFood.setType(type);
        recommendFood.setEnable(enable);

        activityService.updateOne_Recommend(recommendFood);

        map.put("status","SUCCEED");

        return map;
    }


    @ResponseBody
    @RequestMapping(value = "/discount/update")
    public Map<String, Object> updateOne_Discount(@RequestParam(name = "id") Long id,
                                                  @RequestParam(name = "discount") Double discount,
                                                  @RequestParam(name = "moneyLimit") Double moneyLimit,
                                                  @RequestParam(name = "type") Integer type,
                                                  @RequestParam(name = "createTime") String createTime,
                                                  @RequestParam(name = "startTime") String startTime,
                                                  @RequestParam(name = "endTime") String endTime,
                                                  @RequestParam(name = "enable") Integer enable)  {
        Map<String, Object> map = new HashMap();

        DiscountRecord discountRecord = new DiscountRecord();
        discountRecord.setId(id);
        discountRecord.setDiscount(discount);
        discountRecord.setMoneyLimit(moneyLimit);
        discountRecord.setType(type);
        discountRecord.setCreateTime(TimeUtil.StringToTimeStamp(createTime));
        discountRecord.setUpdateTime(TimeUtil.getTimeNow());
        discountRecord.setStartTime(TimeUtil.StringToTimeStamp(startTime));
        discountRecord.setEndTime(TimeUtil.StringToTimeStamp(endTime));
        discountRecord.setEnable(enable);

        activityService.updateOne_Discount(discountRecord);

        map.put("status","SUCCEED");

        return map;
    }



    @ResponseBody
    @RequestMapping(value = "/recommend/delete")
    public Map<String, Object> deleteOne_Recommend(@RequestParam(name = "list_ID") List<Long> list_ID)  {
        Map<String, Object> map = new HashMap();

        for(Long id : list_ID){
            activityService.deleteOne_Recommend(id);
        }

        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/discount/delete")
    public Map<String, Object> deleteOne_Discount(@RequestParam(name = "list_ID") List<Long> list_ID)  {
        Map<String, Object> map = new HashMap();

        for(Long id : list_ID){
            activityService.deleteOne_Discount(id);
        }

        map.put("status","SUCCEED");

        return map;
    }

}

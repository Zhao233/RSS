package com.example.demo.controller.weApp.customerController.quickServiceController;

import com.example.demo.model.customer.FoodForCustomerFrequentlyModel;
import com.example.demo.service.info.FrequentlyUsedFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/customer/quickService")
public class CustomerQuickServiceController {
    @Autowired
    private FrequentlyUsedFoodService frequentlyUsedFoodService;


    @ResponseBody
    @RequestMapping("/getFrequentlyUsedFoodList")
    public Map<String, Object> getFrequentlyUsedFoodList(@RequestParam(value = "userID") String openID){
        Map<String, Object> map = new HashMap();

        FoodForCustomerFrequentlyModel foodForCustomerFrequentlyModel = frequentlyUsedFoodService.getFrequentlyUsedFoodByUserID(openID);

        map.put("FrequentlyUsedFood",foodForCustomerFrequentlyModel);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping("/addFoodToFrequentlyUsedFoodList")
    public Map<String, Object> addFoodToFrequentlyUsedFoodList(@RequestParam(value = "openID") String openID,
                                                               @RequestParam(value = "styleID") Long styleID,
                                                               @RequestParam(value = "foodID") Long foodID){
        Map<String, Object> map = new HashMap<>();

        boolean flag = frequentlyUsedFoodService.addFoodToFrequentlyUsedFoodList(openID,foodID, styleID);

        if(flag){//添加成功
            map.put("status", "SUCCEED");
        } else {//添加失败
            map.put("status", "FAILED");
            map.put("message", "快捷菜单中已存在该菜品");
        }

        return map;
    }

    @ResponseBody
    @RequestMapping("/addFrequentlyUsedFoodList")
    public Map<String, Object> addFrequentlyUsedFoodList(@RequestParam(value = "openID") String openID,
                                                         @RequestParam(value = "foodIDs") String foodIDs,
                                                         @RequestParam(value = "styleIDs") String styleIDs,
                                                         @RequestParam(value = "nums") String nums ){
        Map<String, Object> map = new HashMap<>();

        frequentlyUsedFoodService.saveFrequentlyUsedFoodList(openID, foodIDs, styleIDs, nums);

        map.put("status", "SUCCEED");

        return map;
    }
}
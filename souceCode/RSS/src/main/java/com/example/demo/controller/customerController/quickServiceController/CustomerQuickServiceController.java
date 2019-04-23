package com.example.demo.controller.customerController.quickServiceController;

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
}

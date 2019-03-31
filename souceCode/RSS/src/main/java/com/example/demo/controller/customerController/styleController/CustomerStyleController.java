package com.example.demo.controller.customerController.styleController;

import com.example.demo.domain.foodInfo.Style;
import com.example.demo.service.foodInfo.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer/style")
public class CustomerStyleController {
    @Autowired
    private StyleService styleService;

    @ResponseBody
    @RequestMapping(value="/getStylesByFoodId")
    public Map<String, Object> getStylesByFoodId(@RequestParam(name = "foodId") Long foodId){//根据食物id获取该食物下的所有口味
        Map<String, Object> map = new HashMap();
        List<Style> styleList;

        styleList = styleService.getStylesByFoodId(foodId);

        map.put("styleList", styleList);
        map.put("foodId", String.valueOf(foodId));
        map.put("status", "SUCCESS");
        return map;
    }

}

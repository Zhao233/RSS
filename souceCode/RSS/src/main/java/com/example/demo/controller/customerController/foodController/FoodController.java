package com.example.demo.controller.customerController.foodController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class FoodController {
    @ResponseBody
    @RequestMapping(value="/getFoodByMenuId")
    public Map<String, Object> getFoodByMenuId(){//获取所有一级菜单
        Map<String, Object> map = new HashMap();


        map.put("response", "getFoodByMenuId");
        return map;
    }
}

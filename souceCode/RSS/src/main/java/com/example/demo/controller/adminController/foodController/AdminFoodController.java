package com.example.demo.controller.adminController.foodController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/food")
public class AdminFoodController {
    @ResponseBody
    @RequestMapping(value="/getFoodsByMenuId")
    public Map<String, Object> getFoodsByMenuId(@RequestParam(name = "menuId", required = true) long menuId){//获取所有一级菜单
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }
}

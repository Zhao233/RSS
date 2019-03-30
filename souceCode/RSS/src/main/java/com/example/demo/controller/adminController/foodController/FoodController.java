package com.example.demo.controller.adminController.foodController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class FoodController {
    @ResponseBody
    @RequestMapping(value="/getFoodByMenuId")
    public Map<String, Object> getFoodByMenuId(@RequestParam(name = "menuId", required = true) long menuId){//获取所有一级菜单
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodByMenuId");
        return map;
    }
}

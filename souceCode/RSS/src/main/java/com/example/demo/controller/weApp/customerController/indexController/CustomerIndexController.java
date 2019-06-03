package com.example.demo.controller.weApp.customerController.indexController;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.model.customer.FoodForCustomerRecommendModel;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.foodInfo.MenuService;
import com.example.demo.service.foodInfo.RecommendFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/customer/index")
public class CustomerIndexController {
    @Autowired
    private MenuService menuService;

    @Autowired
    private RecommendFoodService recommendFoodService;

    @Autowired
    private FoodService foodService;

    @ResponseBody
    @RequestMapping(value="/getMenuList")
    public Map<String, Object> getMenuList(){//获取所有一级菜单
        Map<String, Object> map = new HashMap();
        List<Menu> menuList = new LinkedList<>();

        menuList = menuService.getAll();

        map.put("menuList",menuList);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/getRecommendFoodList")
    /**
     * id, name, picUrl, price
     * */
    public Map<String, Object> getRecommendFoodList(){//获取所有一级菜单
        Map<String, Object> map = new HashMap();
        List<FoodForCustomerRecommendModel> recommendFoodList = new LinkedList<>();

        recommendFoodList = recommendFoodService.getCustomerRecommendModel();

        map.put("recommendFoodList",recommendFoodList);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/getFoodListByMenuID")
    public Map<String, Object> getFoodListByMenuID(@RequestParam(value = "menuID") Long menuID){//获取所有一级菜单
        Map<String, Object> map = new HashMap();
        List<Food> foodList = new LinkedList<>();

        foodList = foodService.getFoodsByMenuId(menuID);

        map.put("foodList",foodList);
        map.put("status", "SUCCEED");
        return map;
    }
}

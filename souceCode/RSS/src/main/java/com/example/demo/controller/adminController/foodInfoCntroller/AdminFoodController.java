package com.example.demo.controller.adminController.foodInfoCntroller;


import com.example.demo.domain.foodInfo.Food;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;


    /**
     * 根据menuId获取一级菜单下所有的菜品
     * */
    @ResponseBody
    @RequestMapping(value="/getFoodsByMenuId")
    public Map<String, Object> getFoodsByMenuId(@RequestParam(name = "menuId", required = true) long menuId,
                                                @RequestParam(name = "search", defaultValue = "") String search,
                                                @RequestParam(name = "offset") int offset,
                                                @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Food> page;

        page = foodService.getFoodList(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/getFoodList")
    public Map<String, Object> getFoodList(@RequestParam(name = "search") String search,
                                           @RequestParam(name = "offset") int offset,
                                           @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();
        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Food> page;

        page = foodService.getFoodList(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Map<String, Object> addFood(MultipartHttpServletRequest request,
                                       @RequestParam(name = "menuId") long menuId,
                                       @RequestParam(name = "name") String name,
                                       @RequestParam(name = "role") int role,
                                       @RequestParam(name = "styles") String styles,
                                       @RequestParam(name = "enable") int enable){
        Map<String, Object> map = new HashMap();

        Food food = new Food();
        food.setPicUrl("");
        food.setName(name);
        food.setMenuId(menuId);
        food.setRole(role);
        food.setStylesId(styles);
        food.setEnable(enable);
        food.setCreateTime(TimeUtil.getTimeNow());

        foodService.addFood(food);

        map.put("status","SUCCESS");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> updateFood(@RequestParam(name = "id") long id,
                                          MultipartHttpServletRequest request,
                                          @RequestParam(name = "menuId") long menuId,
                                          @RequestParam(name = "name") String name,
                                          @RequestParam(name = "role") int role,
                                          @RequestParam(name = "styles") String styles,
                                          @RequestParam(name = "enable") int enable){
        Map<String, Object> map = new HashMap();

        Food food = new Food();
        food.setId(id);
        food.setPicUrl("");
        food.setName(name);
        food.setMenuId(menuId);
        food.setRole(role);
        food.setStylesId(styles);
        food.setEnable(enable);
        food.setUpdateTime(TimeUtil.getTimeNow());

        foodService.updateFood(food);

        map.put("status","SUCCESS");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Map<String, Object> deleteFood(@RequestParam(name = "foodId") Long foodId){
        Map<String, Object> map = new HashMap();

        foodService.deleteFood(foodId);

        map.put("status","SUCCESS");

        return map;
    }
}

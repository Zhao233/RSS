package com.example.demo.controller.adminController.foodInfoCntroller;


import com.example.demo.domain.foodInfo.Food;
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

    /**
     * 根据menuId获取一级菜单下所有的菜品
     * */
    @ResponseBody
    @RequestMapping(value="/getFoodsByMenuId")
    public Map<String, Object> getFoodsByMenuId(@RequestParam(name = "menuId", required = true) long menuId,
                                                @RequestParam(name = "search") String search,
                                                @RequestParam(name = "offset") int offset,
                                                @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Map<String, Object> addFood(MultipartHttpServletRequest request,
                                       @RequestParam(name = "menuId") long menuId,
                                       @RequestParam(name = "name") String name,
                                       @RequestParam(name = "role") int role,
                                       @RequestParam(name = "styles") String styles){
        Map<String, Object> map = new HashMap();

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> updateFood(MultipartHttpServletRequest request,
                                       @RequestParam(name = "menuId") long menuId,
                                       @RequestParam(name = "name") String name,
                                       @RequestParam(name = "role") int role,
                                       @RequestParam(name = "styles") String styles){
        Map<String, Object> map = new HashMap();

        return null;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Map<String, Object> deleteFood(@RequestParam(name = "foodId") String foodId){
        Map<String, Object> map = new HashMap();

        return null;
    }
}

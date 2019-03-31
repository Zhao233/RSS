package com.example.demo.controller.adminController.foodController;


import com.example.demo.domain.foodInfo.Food;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/add")
    public Map<String, Object> addFood(@RequestParam(name = "menuId") long menuId,
                                       @RequestParam(name = "name") String name,
                                       @RequestParam(name = "role") int role,
                                       @RequestParam(name = "styles") String styles,
                                       @RequestParam(name = "picture") MultipartFile picture){
        Map<String, Object> map = new HashMap();

        return null;
    }
}

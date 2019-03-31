package com.example.demo.controller.adminController.menuController;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.foodInfo.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/menu")

public class AdminMenuController {
    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping(value = "/getMenuList")
    public Map<String, Object> getMenuList() {
        Map<String, Object> map = new HashMap();
        List<Menu> list_menu;

        list_menu = menuService.getMenuList();

        map.put("menuList", list_menu);
        map.put("status", "SUCCESS");
        return map;
    }
}

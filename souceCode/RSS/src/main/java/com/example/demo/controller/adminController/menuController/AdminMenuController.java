package com.example.demo.controller.adminController.menuController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/food")

public class AdminMenuController {
    @ResponseBody
    @RequestMapping(value = "/getMenuList")
    public Map<String, Object> getMenuList() {
        Map<String, Object> map = new HashMap();

        map.put("response", "getMenuList");
        return map;
    }
}

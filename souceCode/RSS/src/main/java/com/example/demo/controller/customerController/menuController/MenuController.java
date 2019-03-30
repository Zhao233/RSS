package com.example.demo.controller.customerController.menuController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController {
    @ResponseBody
    @RequestMapping(value = "/getMenuList")
    public Map<String, Object> getMenuList() {
        Map<String, Object> map = new HashMap();

        map.put("response", "getMenuList");
        return map;
    }
}

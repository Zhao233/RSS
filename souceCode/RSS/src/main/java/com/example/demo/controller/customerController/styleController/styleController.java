package com.example.demo.controller.customerController.styleController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class styleController {
    @ResponseBody
    @RequestMapping(value="/getStylesByFoodId")
    public Map<String, Object> getStylesByFoodId(){//获取所有一级菜单
        Map<String, Object> map = new HashMap();


        map.put("response", "getStylesByFoodId");
        return map;
    }

}

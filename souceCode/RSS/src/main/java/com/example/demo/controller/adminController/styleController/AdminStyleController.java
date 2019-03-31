package com.example.demo.controller.adminController.styleController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/food")
public class AdminStyleController {

    @ResponseBody
    @RequestMapping(value = "/getAllStyles")
    public Map<String, Object> getAllStyles() {
        Map<String, Object> map = new HashMap();

        map.put("response", "getAllStyles");
        return map;
    }
}

package com.example.demo.controller.adminController.userController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
    @ResponseBody
    @RequestMapping(value="/getCustomerList")
    public Map<String, Object> getCustomerList(@RequestParam(name = "search") String search,
                                                @RequestParam(name = "offset") int offset,
                                                @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }
}

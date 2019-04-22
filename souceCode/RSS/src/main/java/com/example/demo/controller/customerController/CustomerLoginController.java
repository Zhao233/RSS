package com.example.demo.controller.customerController;

import com.example.demo.service.user.CustomerLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/customer")
public class CustomerLoginController {
    @Autowired
    private CustomerLoginService customerLoginService;

    @ResponseBody
    @RequestMapping(value = "/login")
    public Map<String ,Object> customerLogin(@RequestParam("code") String loginCode){
        Map<String, Object> map = new HashMap();

        customerLoginService.getLoginSession(loginCode);


        map.put("status", "SUCCEED");
        return map;
    }


}

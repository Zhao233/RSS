package com.example.demo.controller.weApp.waiterController;

import com.example.demo.service.user.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/waiter")
public class WaiterLoginController {
    @Autowired
    private WaiterService waiterService;

    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(@RequestParam("openid") String openid,
                                     @RequestParam("secret") String secret){
        Map<String, Object> res = new HashMap<>();


        return res;
    }
}

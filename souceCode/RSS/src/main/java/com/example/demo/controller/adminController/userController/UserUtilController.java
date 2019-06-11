package com.example.demo.controller.adminController.userController;

import com.example.demo.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/util")
public class UserUtilController {
    @Autowired
    private RandomUtil randomUtil;

    @RequestMapping(value="/getRandomLoginCode")
    @ResponseBody
    public Map<String, Object> getWaiterList( ){
        HashMap<String, Object> map = new HashMap<>();

        String randomLoginCode = randomUtil.createRandomLoginCode();

        if(randomLoginCode != null){
            map.put("loginCode", randomLoginCode);
            map.put("status", "SUCCEED");
        } else {
            map.put("ERROR", "获取登录码失败");
        }

        return map;
    }
}

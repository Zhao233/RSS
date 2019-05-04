package com.example.demo.controller.weApp;

import com.example.demo.service.user.CustomerService;
import com.example.demo.service.user.LoginService;
import com.example.demo.service.user.WaiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/weapp")
public class LoginCheckController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private WaiterService waiterService;

    @ResponseBody
    @RequestMapping("/loginCheck")
    public Map<String, Object> loginCheck(@RequestParam("openid") String openid){
        Map<String, Object> map = new HashMap<>();

        boolean isLogin = false;
        int identity = 0;//无识别

        /**
         * 优先识别服务人员
         * 服务人员如果未注册，进行注册操作，注册成功后进行正常操作
         * 客户未登录，进行注册操作
         * */
        isLogin = waiterService.isLogin(openid);//服务员登录
        identity = 1;

        if( !isLogin ) {//未注册
            map.put("status", "FAILED");
        }

        if(isLogin){//已注册
            map.put("identity", identity);
            map.put("status", "SUCCEED");

            return map;
        }

        isLogin = customerService.isLogin(openid);//客户登录
        identity = 3;
        if( !isLogin ) {// 未注册
            map.put("status", "FAILED");
        }

        if(isLogin){//已注册
            map.put("identity", identity);
            map.put("status", "SUCCEED");

            return map;
        }


        map.put("status", "ERROR");
        map.put("message", "服务器错误，无法识别身份");

        return map;
    }

    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> weappLogin(@RequestParam("openid") String openid,
                                          @RequestParam("identity") Integer identity,
                                          @RequestParam("loginID") String loginID){
        Map<String, Object> map = new HashMap<>();

        switch (identity){
            case 1://服务员


                break;

            case 2://厨师
                break;

            case 3://顾客
                break;

            default://无识别
                break;
        }

        return map;
    }


    @Autowired
    private LoginService loginService;

    @ResponseBody
    @RequestMapping(value = "/getLoginSession")
    public Map<String ,Object> customerLogin(@RequestParam("code") String loginCode){
        Map<String, Object> map = new HashMap();

        Map<String, Object> map_result = loginService.getLoginSession(loginCode);


        map.put("userInfo", map_result);
        map.put("status", "SUCCEED");
        return map;
    }
}

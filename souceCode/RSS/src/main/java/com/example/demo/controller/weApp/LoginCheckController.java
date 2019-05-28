package com.example.demo.controller.weApp;

import com.example.demo.domain.info.FrequentlyUsedFood;
import com.example.demo.domain.user.Cooker;
import com.example.demo.domain.user.Customer;
import com.example.demo.domain.user.Waiter;
import com.example.demo.repository.info.FrequentlyUsedFoodDao;
import com.example.demo.service.info.FrequentlyUsedFoodService;
import com.example.demo.service.user.CookerService;
import com.example.demo.service.user.CustomerService;
import com.example.demo.service.user.LoginService;
import com.example.demo.service.user.WaiterService;
import com.example.demo.util.TimeUtil;
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

    @Autowired
    private CookerService cookerService;

    @Autowired
    private FrequentlyUsedFoodService frequentlyUsedFoodService;


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

        isLogin = cookerService.isLogin(openid);//厨师登录
        identity = 2;

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
            map.put("identity", 0);
            map.put("status", "SUCCEED");
        }

        if(isLogin){//已注册
            map.put("identity", identity);
            map.put("status", "SUCCEED");

            return map;
        }

        return map;
    }

    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> weappLogin(@RequestParam("openid") String openid,
                                          @RequestParam(value = "loginID", required = false) String loginID){
        Map<String, Object> map = new HashMap<>();

        int isExist = 0;

        isExist = waiterService.checkIsWaiterExistByLoginID(loginID);

        if(isExist == 3){//录入成功
            Waiter waiter = waiterService.registerWaiter(loginID, openid);

            map.put("status", "SUCCEED");
            map.put("userInfo", waiter);
            return map;
        }

        /**
         * 厨师的录入
         */
        isExist = cookerService.checkIsCookerExistByLoginID(loginID);

        if(isExist == 2){//录入成功
            Cooker cooker = cookerService.registerCooker(openid, loginID);

            map.put("status", "SUCCEED");
            map.put("userInfo", cooker);
            return map;
        }

        /**
         * 客户的录入
         */
        isExist = customerService.checkIsCustomerExistByLoginID(loginID);

        if(isExist == 1){//后台未录入
            Customer customer = customerService.registerCustomer(openid);

            frequentlyUsedFoodService.saveEmptyFrequentlyUsedFoodList(customer.getId());

            map.put("status", "SUCCEED");
            map.put("userInfo", customer);
            return map;
        }

        switch (isExist){
            case 1 :
                map.put("status", "FAILED");
                map.put("message", "登录码错误或管理员未录入信息，请重新输入，或联系管理员");

                return map;
            case 2:
                map.put("status", "FAILED");
                map.put("message", "登录码错误或管理员未录入信息，请重新输入，或联系管理员");

                return map;
        }

        map.put("status", "ERROR");
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

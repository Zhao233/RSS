package com.example.demo.controller.customerController.cartController;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.info.DiscountRecord;
import com.example.demo.domain.info.NonPaymentRecord;
import com.example.demo.repository.foodInfo.FoodDao;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.info.DiscountService;
import com.example.demo.service.info.NonPaymentRecordService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer/cart")
public class CustomerCartController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private NonPaymentRecordService nonPaymentRecordService;

    @ResponseBody
    @RequestMapping("/onSubmit")
    private Map<String, Object> submitCartList(@RequestParam(value = "foodIDList") String foodIDList,
                                               @RequestParam(value = "foodNumList") String foodNumList,
                                               @RequestParam(value = "styleIDList") String styleIDList,
                                               @RequestParam(value = "discountID") Long discountID,
                                               @RequestParam(value = "openID" ) String openID,
                                               @RequestParam(value = "expirationTime" ) String expirationTime,
                                               @RequestParam(value = "account" ) Double account){
        Map<String, Object> map = new HashMap<>();

        JSONArray array_foodIDList = JSONArray.fromObject(foodIDList);
        JSONArray array_foodNumList = JSONArray.fromObject(foodNumList);
        JSONArray array_styleIDList = JSONArray.fromObject(styleIDList);

        LinkedList<Long> foodIDList_ = new LinkedList<>();
        LinkedList<Integer> foodNumList_ = new LinkedList<>();
        LinkedList<Long> styleIDList_ = new LinkedList<>();

        for(int i = 0; i < array_foodIDList.size(); i++){
            Long id =  Long.valueOf( String.valueOf( array_foodIDList.get(i) ) );
            int num = (int)array_foodNumList.get(i);

            foodIDList_.add(id);
            foodNumList_.add(num);
        }

        if( checkFoodAccount(foodIDList_, foodNumList_, account) ){
            nonPaymentRecordService.addOne(foodIDList_, foodNumList_, styleIDList_, discountID, openID, Long.parseLong(expirationTime), account);

            map.put("status", "SUCCEED");
        } else {
            String message = "订单金额出错，请刷新菜单，并重新下单";

            map.put("status", "ERROR");
            map.put("message", message);
        }

        return map;
    }

    @ResponseBody
    @RequestMapping("/getDiscountList")
    private Map<String, Object> getDiscountList(@RequestParam("account") Double account){
        Map<String, Object> map = new HashMap<>();

        List<DiscountRecord> discountRecordList = discountService.getDiscount(account);

        map.put("discountList", discountRecordList);
        map.put("status", "SUCCEED");
        return map;
    }

    public boolean checkFoodAccount(List<Long> foodIDList,List<Integer> foodNumList, double account){
        double account_food = 0;

        for(int i = 0; i < foodIDList.size(); i++){
            Food food = foodService.getOne(foodIDList.get(i));

            account_food += ( food.getPrice() * foodNumList.get(i) );
        }

        return account_food == account;
    }

}

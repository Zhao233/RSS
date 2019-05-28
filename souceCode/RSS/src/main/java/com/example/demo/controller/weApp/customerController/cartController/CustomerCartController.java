package com.example.demo.controller.weApp.customerController.cartController;

import com.example.demo.asynchronousHandler.Cooker.CookerJobHandler;
import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.foodInfo.DiscountRecord;
import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.OrderRecord;
import com.example.demo.domain.user.Cooker;
import com.example.demo.service.foodInfo.FoodService;
import com.example.demo.service.info.DiscountService;
import com.example.demo.service.info.OrderRecordService;
import com.example.demo.service.user.CustomerService;
import com.example.demo.util.StringTranslator;
import com.example.demo.util.TimeUtil;
import net.sf.json.JSONArray;
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
    private OrderRecordService orderRecordService;

    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @RequestMapping("/onSubmit")
    private Map<String, Object> submitCartList(@RequestParam(value = "foodIDList") String foodIDList,
                                               @RequestParam(value = "foodNumList") String foodNumList,
                                               @RequestParam(value = "styleIDList") String styleIDList,
                                               @RequestParam(value = "tableNum") Integer tableNum,
                                               @RequestParam(value = "discountID", required = false) Long discountID,
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

        Double discount = discountService.getDiscountNum(discountID);

        Long userID = customerService.getIDByOpenID(openID);

        for(int i = 0; i < array_foodIDList.size(); i++){
            Long id =  Long.valueOf( String.valueOf( array_foodIDList.get(i) ) );
            int num = (int)array_foodNumList.get(i);

            foodIDList_.add(id);
            foodNumList_.add(num);
        }

        if( checkFoodAccount(foodIDList_, foodNumList_, account) ){
            Long recordID = orderRecordService.addOne(foodIDList_, foodNumList_, styleIDList_, discountID, openID, Long.parseLong(expirationTime), account, tableNum);

            map.put("status", "SUCCEED");
            map.put("nonPaymentRecordID", recordID);
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

    @ResponseBody
    @RequestMapping("/payForOrder")
    private Map<String, Object> payForTheOrderRecord(@RequestParam("orderRecordID") Long recordID,
                                                     @RequestParam("orderRecordID") String payment){
        Map<String, Object> map = new HashMap<>();

        OrderRecord record = orderRecordService.getOrderRecordByID(recordID);

        //支付完成


        sendCookerDeliveryRecordsToCookerFromOrder(record);
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

    private void sendCookerDeliveryRecordsToCookerFromOrder(OrderRecord record){
        List<Long> foods_id = new LinkedList<>();
        List<Integer> foods_num = new LinkedList<>();

        foods_id = StringTranslator.getListFromString(record.getFoodsID(),0);
        foods_num = StringTranslator.getListFromString(record.getFoodsNum(), 1);

        Integer tableNum = record.getTableNum();

        LinkedList<CookerDeliveryRecord> list = new LinkedList<>();

        for(int i = 0; i < foods_id.size(); i++){
            Long foodID = foods_id.get(i);

                for( int j = 0; j < foods_num.get(i); j++ ){

                    CookerDeliveryRecord deliveryRecord = new CookerDeliveryRecord();
                    deliveryRecord.setFoodId(foodID);
                    deliveryRecord.setTableNum(tableNum);
                    deliveryRecord.setOrderRecordId(record.getId());
                    deliveryRecord.setIsComplete(0);
                    deliveryRecord.setCreateTime(TimeUtil.getTimeNow());

                    CookerJobHandler.putMessageToCookerMessageBlockingQueue(deliveryRecord);//将任务发送给厨师
                }

        }

    }

}

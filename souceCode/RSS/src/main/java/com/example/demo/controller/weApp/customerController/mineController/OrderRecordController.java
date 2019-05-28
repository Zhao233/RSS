package com.example.demo.controller.weApp.customerController.mineController;

import com.example.demo.model.customer.OrderRecordModel;
import com.example.demo.service.info.OrderRecordService;
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
@RequestMapping("/customer/orderRecord")
public class OrderRecordController {
    @Autowired
    private OrderRecordService orderRecordService;

    @ResponseBody
    @RequestMapping("/getOrderRecord")
    public Map<String, Object> getOrderRecord(@RequestParam(value = "openid") String openid){
        Map<String, Object> res = new HashMap<>();
        List<OrderRecordModel> orderRecordModelList= new LinkedList<>();

        orderRecordModelList = orderRecordService.getAllOrderRecordModel(openid);

        res.put("orderList", orderRecordModelList);
        res.put("status","SUCCEED");
        return res;
    }
}
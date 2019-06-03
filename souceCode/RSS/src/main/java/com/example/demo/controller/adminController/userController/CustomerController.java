package com.example.demo.controller.adminController.userController;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.domain.user.Customer;
import com.example.demo.model.admin.RecommendForActivityModel;
import com.example.demo.service.info.OrderRecordService;
import com.example.demo.service.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderRecordService orderRecordService;

    @ResponseBody
    @RequestMapping(value="/getAll")
    public Map<String, Object> getCustomerList(@RequestParam(name = "search") String search,
                                                @RequestParam(name = "offset") int offset,
                                                @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Customer> page;

        page = customerService.getAll(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/getAllCustomerOrderRecord")
    public Map<String, Object> getAllCustomerOrderRecord(@RequestParam(name = "customerID") Long customerID,
                                                         @RequestParam(name = "offset") int offset,
                                                         @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        Page<OrderRecord> page = orderRecordService.getAllByUserID(offset, limit, customerID);

        Double totalAccount = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_ALL, customerID);
        Double monthAccount = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_MONTH, customerID);
        Double weekAccount = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_WEEK, customerID);

        map.put("totalAccount", totalAccount);
        map.put("monthAccount", monthAccount);
        map.put("weekAccount", weekAccount);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/getCustomerOrderInfo")
    public Map<String, Object> getCustomerOrderInfo(@RequestParam(name = "customerID") Long customerID){
        Map<String, Object> map = new HashMap();
        Double totalAccount = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_ALL, customerID);
        Double monthAccount = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_MONTH, customerID);
        Double weekAccount = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_WEEK, customerID);

        map.put("totalAccount", totalAccount);
        map.put("monthAccount", monthAccount);
        map.put("weekAccount", weekAccount);

        map.put("status", "SUCCESS");

        return map;
    }
}

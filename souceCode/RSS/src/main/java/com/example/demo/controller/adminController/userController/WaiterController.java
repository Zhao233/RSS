package com.example.demo.controller.adminController.userController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/waiter")
public class WaiterController {
    @ResponseBody
    @RequestMapping(value="/getWaiterList")
    public Map<String, Object> getWaiterList(@RequestParam(name = "search") String search,
                                               @RequestParam(name = "offset") int offset,
                                               @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/add")
    public Map<String, Object> addWaiter(@RequestParam(name = "name") String name){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/update")
    public Map<String, Object> updateWaiter(@RequestParam(name = "id") String id,
                                            @RequestParam(name = "name") String name){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/delete")
    public Map<String, Object> deleteWaiter(@RequestParam(name = "id") String id){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

}

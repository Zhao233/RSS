package com.example.demo.controller.adminController.userController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/cooker")
public class CookerController {
    @ResponseBody
    @RequestMapping(value="/getCookerList")
    public Map<String, Object> getCookerList(@RequestParam(name = "search") String search,
                                                @RequestParam(name = "offset") int offset,
                                                @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/add")
    public Map<String, Object> addCooker(@RequestParam(name = "role") String search,
                                         @RequestParam(name = "name") int offset,
                                         @RequestParam(name = "") int limit){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/update")
    public Map<String, Object> updateCooker(@RequestParam(name = "id") long id,
                                            @RequestParam(name = "role") String role,
                                            @RequestParam(name = "name") String name){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/delete")
    public Map<String, Object> deleteCooker(@RequestParam(name = "id") long id){
        Map<String, Object> map = new HashMap();

        map.put("response", "getFoodsByMenuId");
        return map;
    }

}

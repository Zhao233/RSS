package com.example.demo.controller.adminController.foodInfoController;

import com.example.demo.domain.foodInfo.Style;
import com.example.demo.service.foodInfo.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/style")
public class AdminStyleController {
    @Autowired
    private StyleService styleService;

    @ResponseBody
    @RequestMapping(value = "/getAllStyles")
    public Map<String, Object> getAllStyles(@RequestParam(name = "search") String search,
                                            @RequestParam(name = "offset") int offset,
                                            @RequestParam(name = "limit") int limit) {
        Map<String, Object> map = new HashMap();
        List<Style> styleList;

        styleList = styleService.getAllStyles();

        map.put("styleList", styleList);
        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/getStylesByFoodId")
    public Map<String, Object> getStylesByFoodId(@RequestParam(name = "foodId") Long foodId){
        Map<String, Object> map = new HashMap();
        List<Style> styleList;

        styleList = styleService.getStylesByFoodId(foodId);

        map.put("styleList", styleList);
        map.put("foodId", String.valueOf(foodId));
        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/add")
    public Map<String, Object> addStyle(@RequestParam(name = "name") String name){
        Map<String, Object> map = new HashMap();
        List<Style> styleList;

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/update")
    public Map<String, Object> updateStyle(@RequestParam(name = "id") String id,
                                        @RequestParam(name = "name") String name){
        Map<String, Object> map = new HashMap();
        List<Style> styleList;

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/delete")
    public Map<String, Object> deleteStyle(@RequestParam(name = "id") String id){
        Map<String, Object> map = new HashMap();
        List<Style> styleList;

        map.put("status", "SUCCESS");
        return map;
    }
}

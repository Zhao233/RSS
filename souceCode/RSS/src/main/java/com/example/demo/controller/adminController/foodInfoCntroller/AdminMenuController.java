package com.example.demo.controller.adminController.foodInfoCntroller;

import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.service.foodInfo.MenuService;
import com.example.demo.util.DateTranslator;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/menu")

public class AdminMenuController {
    @Autowired
    private MenuService menuService;

    @ResponseBody
    @RequestMapping(value = "/getMenuList")
    public Map<String, Object> getMenuList(@RequestParam(name = "search") String search,
                                           @RequestParam(name = "offset") int offset,
                                           @RequestParam(name = "limit") int limit )  {
        Map<String, Object> map = new HashMap();
        List<Menu> list_menu;

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Menu> page;

        page = menuService.getMenuList(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}")
    public Map<String, Object> getOneById(@PathVariable String id)  {
        HashMap<String, Object> map = new HashMap<>();

        Menu menu = menuService.getOne(Long.parseLong(id));

        map.put("menu",menu);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Map<String, Object> addMenu(@RequestParam(name = "name") String name,
                                       @RequestParam(name = "enable") int enable)  {
        Map<String, Object> map = new HashMap();

        Menu menu = new Menu();
        menu.setName(name);
        menu.setCreateTime(TimeUtil.getTimeNow());
        menu.setEnable(enable);

        menuService.addMenu(menu);

        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> updateMenu(@RequestParam(name = "id") long id,
                                          @RequestParam(name = "name") String name,
                                          @RequestParam(name = "createTime") String createTime,
                                          @RequestParam(name = "enable") int enable)  {
        Map<String, Object> map = new HashMap();

        Menu menu = new Menu();
        menu.setId(id);
        menu.setName(name);
        menu.setUpdateTime(TimeUtil.getTimeNow());
        menu.setCreateTime(DateTranslator.StringToTimeStamp(createTime));
        menu.setEnable(enable);

        menuService.updateMenu(menu);

        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Map<String, Object> deleteMenu(@RequestParam(name = "list_ID") List<Long> list_ID)  {
        Map<String, Object> map = new HashMap();

        for(Long id : list_ID){
            menuService.deleteMenu(id);
        }

        map.put("status","SUCCEED");

        return map;
    }
}

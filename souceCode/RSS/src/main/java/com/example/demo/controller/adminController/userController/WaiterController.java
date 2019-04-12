package com.example.demo.controller.adminController.userController;

import com.example.demo.domain.foodInfo.Food;
import com.example.demo.domain.foodInfo.Menu;
import com.example.demo.domain.user.Cooker;
import com.example.demo.domain.user.Waiter;
import com.example.demo.service.foodInfo.MenuService;
import com.example.demo.service.user.WaiterService;
import com.example.demo.util.LoginCheck;
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
@RequestMapping("/admin/waiter")
public class WaiterController {
    @Autowired
    private WaiterService waiterService;

    @ResponseBody
    @RequestMapping(value="/getAll")
    public Map<String, Object> getWaiterList(@RequestParam(name = "search") String search,
                                               @RequestParam(name = "offset") int offset,
                                               @RequestParam(name = "limit") int limit){
        Map<String, Object> map = new HashMap();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Waiter> page;

        page = waiterService.getAll(search, pageable);

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}")
    public Map<String, Object> getOneById(@PathVariable long id)  {
        HashMap<String, Object> map = new HashMap<>();

        Waiter waiter = waiterService.getOneByID(id);

        map.put("waiter",waiter);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/add")
    public Map<String, Object> addWaiter(@RequestParam(name = "name") String name,
                                         @RequestParam(name = "loginID") String loginID,
                                         @RequestParam(name = "phoneNumber") String phoneNumber,
                                         @RequestParam(name = "enable") int enable){
        Map<String, Object> map = new HashMap();

        Waiter waiter = new Waiter();
        waiter.setName(name);
        waiter.setLoginID(loginID);
        waiter.setEnable(enable);
        waiter.setPhoneNumber(phoneNumber);
        waiter.setCreateTime(TimeUtil.getTimeNow());

        waiterService.addOne(waiter);

        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/update")
    public Map<String, Object> updateWaiter(@RequestParam(name = "id") long id,
                                            @RequestParam(name = "name") String name,
                                            @RequestParam(name = "loginID") String loginID,
                                            @RequestParam(name = "phoneNumber") String phoneNumber,
                                            @RequestParam(name = "createTime") String createTime,
                                            @RequestParam(name = "enable") int enable){
        Map<String, Object> map = new HashMap();

        Waiter waiter = new Waiter();
        waiter.setId(id);
        waiter.setName(name);
        waiter.setEnable(enable);
        waiter.setLoginID(loginID);
        waiter.setUpdateTime(TimeUtil.getTimeNow());
        waiter.setCreateTime(TimeUtil.StringToTimeStamp(createTime));
        waiter.setPhoneNumber(phoneNumber);


        waiterService.updateOne(waiter);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Map<String, Object> deleteMenu(@RequestParam(name = "list_ID") List<Long> list_ID)  {
        Map<String, Object> map = new HashMap();

        for(Long id : list_ID){
            waiterService.deleteOne(id);
        }

        map.put("status","SUCCEED");

        return map;
    }

}

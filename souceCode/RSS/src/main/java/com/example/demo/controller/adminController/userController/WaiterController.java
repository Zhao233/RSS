package com.example.demo.controller.adminController.userController;

import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.domain.user.Waiter;
import com.example.demo.model.waiter.WaiterServiceForWaiterDetailModel;
import com.example.demo.service.info.WaiterDeliveryRecordService;
import com.example.demo.service.user.WaiterService;
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

import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/admin/waiter")
public class WaiterController {
    @Autowired
    private WaiterService waiterService;

    @Autowired
    private WaiterDeliveryRecordService waiterDeliveryRecordService;

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

        waiter = waiterService.getOneByID(id);

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

    @ResponseBody
    @RequestMapping(value = "/getWaiterServiceInfo")
    public Map<String, Object> getWaiterServiceInfo(@RequestParam(name = "waiterID") Long waiterID) {
        Map<String, Object> map = new HashMap();

        List<Integer> serviceTimes = new LinkedList<>();
        List<WaiterServiceForWaiterDetailModel> recentServiceList = new LinkedList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        Integer totalServiceTimes = waiterDeliveryRecordService.getAllServiceTimes(waiterID);
        Integer monthServiceTimes = waiterDeliveryRecordService.getServiceTime(WaiterDeliveryRecord.TYPE_SERVICE_MONTH, waiterID);
        Integer weekServiceTimes = waiterDeliveryRecordService.getServiceTime(WaiterDeliveryRecord.TYPE_SERVICE_WEEK, waiterID);
        Integer dayServiceTimes = waiterDeliveryRecordService.getServiceTime(WaiterDeliveryRecord.TYPE_SERVICE_DAY, waiterID);

        serviceTimes = waiterDeliveryRecordService.getServiceTimeByTime(new Timestamp(calendar.getTime().getTime()), waiterID);
        recentServiceList = waiterDeliveryRecordService.getRecentWaiterServiceRecords(waiterID);

        map.put("totalServiceTimes", totalServiceTimes);
        map.put("monthServiceTimes", monthServiceTimes);
        map.put("weekServiceTimes", weekServiceTimes);
        map.put("dayServiceTimes", dayServiceTimes);
        map.put("serviceTimes", serviceTimes == null ? new LinkedList<Integer>() : serviceTimes);
        map.put("recentServiceList", recentServiceList == null ? new LinkedList<Integer>() : recentServiceList);

        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping("/getServiceNumbers")
    public Map<String, Object> getOrderNumbers(@RequestParam("startTime") String startTime,
                                               @RequestParam("waiterID") Long waiterID){
        HashMap<String, Object> res = new HashMap<>();

        List<Integer> serviceTimes = new LinkedList<>();
        Timestamp timestamp = TimeUtil.StringToTimeStamp(startTime);

        serviceTimes = waiterDeliveryRecordService.getServiceTimeByTime(timestamp, waiterID);

        res.put("status", "SUCCEED");
        res.put("serviceTimes", serviceTimes);

        return res;
    }

}

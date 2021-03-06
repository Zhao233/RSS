package com.example.demo.controller.adminController.userController;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.domain.user.Cooker;
import com.example.demo.model.cooker.CookerServiceForCookerDetailModel;
import com.example.demo.model.waiter.WaiterServiceForWaiterDetailModel;
import com.example.demo.repository.user.CookerRoleDao;
import com.example.demo.service.info.CookerDeliveryRecordService;
import com.example.demo.service.user.CookerService;
import com.example.demo.util.TimeUtil;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/admin/cooker")
public class CookerController {
    @Autowired
    private CookerService cookerService;

    @Autowired
    private CookerDeliveryRecordService cookerDeliveryRecordService;

    @Autowired
    CookerRoleDao cookerRoleDao;

    @ResponseBody
    @RequestMapping(value = "/getAll")
    public Map<String, Object> getMenuList(@RequestParam(name = "search") String search,
                                           @RequestParam(name = "offset") int offset,
                                           @RequestParam(name = "limit") int limit )  {
        Map<String, Object> map = new HashMap();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Cooker> page;

        page = cookerService.getAll(search, pageable);

        //为每个cooker添加角色名称(roleName)
        for(Cooker cooker : page.getContent()){
            cooker.setRoleName(cookerRoleDao.getRoleNameByID(cooker.getRole()));
        }

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        map.put("status", "SUCCESS");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "getCookerRole")
    public Map<String, Object> getCookerRole(){
        HashMap<String, Object> map = new HashMap<>();

        map.put("cookerRoleList",cookerRoleDao.findAll());
        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "getLoginID")
    public String getLoginID(){

        return "random_Test";
    }

    @ResponseBody
    @RequestMapping(value = "/get/{id}")
    public Map<String, Object> getOneById(@PathVariable String id)  {
        HashMap<String, Object> map = new HashMap<>();

        Cooker cooker = cookerService.getOne(Long.parseLong(id));

        map.put("cooker",cooker);
        map.put("status", "SUCCEED");
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Map<String, Object> addMenu(@RequestParam(name = "loginID") String loginID,
                                       @RequestParam(name = "name") String name,
                                       @RequestParam(name = "phoneNumber") String phoneNumber,
                                       @RequestParam(name = "role") long role,
                                       @RequestParam(name = "enable") int enable)  {
        Map<String, Object> map = new HashMap();

        Cooker cooker = new Cooker();
        cooker.setLoginID(loginID);
        cooker.setPhoneNumber(phoneNumber);
        cooker.setRole(role);
        cooker.setName(name);
        cooker.setCreateTime(TimeUtil.getTimeNow());
        cooker.setEnable(enable);

        cookerService.addOne(cooker);

        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Map<String, Object> updateMenu(@RequestParam(name = "id") Long id,
                                          @RequestParam(name = "loginID") String loginID,
                                          @RequestParam(name = "name") String name,
                                          @RequestParam(name = "phoneNumber") String phoneNumber,
                                          @RequestParam(name = "role") Long role,
                                          @RequestParam(name = "loginTimes") Integer loginTimes,
                                          @RequestParam(name = "createTime") String createTime,
                                          @RequestParam(name = "enable") Integer enable)  {
        Map<String, Object> map = new HashMap();

        Cooker cooker = new Cooker();

        cooker = cookerService.getOne(id);

        cooker.setId(id);
        cooker.setLoginID(loginID);
        cooker.setPhoneNumber(phoneNumber);
        cooker.setRole(role);
        cooker.setLoginTimes(loginTimes);
        cooker.setName(name);
        cooker.setCreateTime(TimeUtil.StringToTimeStamp(createTime));
        cooker.setUpdateTime(TimeUtil.getTimeNow());
        cooker.setEnable(enable);

        cookerService.updateOne(cooker);

        map.put("status","SUCCEED");

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Map<String, Object> deleteMenu(@RequestParam(name = "list_ID") List<Long> list_ID)  {
        Map<String, Object> map = new HashMap();

        for(Long id : list_ID){
            cookerService.deleteOne(id);
        }

        map.put("status","SUCCEED");

        return map;
    }


    @ResponseBody
    @RequestMapping(value = "/getCookerServiceInfo")
    public Map<String, Object> getWaiterServiceInfo(@RequestParam(name = "cookerID") Long cookerID) {
        Map<String, Object> map = new HashMap();

        List<Integer> serviceTimes = new LinkedList<>();
        List<CookerServiceForCookerDetailModel> recentServiceList = new LinkedList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        Integer totalServiceTimes = cookerDeliveryRecordService.getAllServiceTimes(cookerID);
        Integer monthServiceTimes = cookerDeliveryRecordService.getServiceTime(WaiterDeliveryRecord.TYPE_SERVICE_MONTH, cookerID);
        Integer weekServiceTimes = cookerDeliveryRecordService.getServiceTime(WaiterDeliveryRecord.TYPE_SERVICE_WEEK, cookerID);
        Integer dayServiceTimes = cookerDeliveryRecordService.getServiceTime(WaiterDeliveryRecord.TYPE_SERVICE_DAY, cookerID);

        serviceTimes = cookerDeliveryRecordService.getServiceTimeByTime(new Timestamp(calendar.getTime().getTime()), cookerID);
        recentServiceList = cookerDeliveryRecordService.getRecentCookerServiceRecords(cookerID);

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
                                               @RequestParam("cookerID") Long cookerID){
        HashMap<String, Object> res = new HashMap<>();

        List<Integer> serviceTimes = new LinkedList<>();
        Timestamp timestamp = TimeUtil.StringToTimeStamp(startTime);

        serviceTimes = cookerDeliveryRecordService.getServiceTimeByTime(timestamp, cookerID);

        res.put("status", "SUCCEED");
        res.put("serviceTimes", serviceTimes);

        return res;
    }

}

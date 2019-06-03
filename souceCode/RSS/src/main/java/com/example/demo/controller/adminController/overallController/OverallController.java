package com.example.demo.controller.adminController.overallController;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.model.admin.PopularFoodModel;
import com.example.demo.service.info.OrderRecordService;
import com.example.demo.service.foodInfo.PopularFoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.*;


@Controller
@RequestMapping("/admin/overall")
public class OverallController {
    @Autowired
    private OrderRecordService orderRecordService;

    @Autowired
    private PopularFoodsService popularFoodsService;

    @ResponseBody
    @RequestMapping("/getOverallInfo")
    public Map<String, Object> getAccount(){
        HashMap<String, Object> res = new HashMap<>();
        List<Integer> orderNumber = new LinkedList<>();

        Double account_all = new Double(0);
        Double account_month = new Double(0);
        Double account_week = new Double(0);
        Double account_day = new Double(0);

        Integer order_day = new Integer(0);

        account_all = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_ALL);
        account_month = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_MONTH);
        account_week = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_WEEK);
        account_day = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_DAY);

        PopularFoodModel popularFoodModel = popularFoodsService.getPopularFood();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        orderNumber = orderRecordService.getOrderTime(new Timestamp(calendar.getTime().getTime()));

        res.put( "account_month", account_month );
        res.put( "account_week", account_week );
        res.put( "account_day", account_day );
        res.put( "account_all", account_all );
        res.put( "order_day", order_day );
        res.put( "orderNumber", orderNumber );
        res.put( "popularFood", popularFoodModel );

        res.put("status", "SUCCEED");

        return res;
    }

    @ResponseBody
    @RequestMapping("/getOrderNumbers")
    public Map<String, Object> getOrderNumbers(@RequestParam("startTime") String startTime ){
        HashMap<String, Object> res = new HashMap<>();


        return res;
    }
}
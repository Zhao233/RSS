package com.example.demo.controller.adminController.overallController;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.model.admin.PopularFoodModel;
import com.example.demo.service.info.OrderRecordService;
import com.example.demo.service.info.PopularFoodsService;
import org.hibernate.sql.OracleJoinFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        Double account_month = new Double(0);
        Double account_week = new Double(0);
        Double account_day = new Double(0);

        Integer order_day = new Integer(0);

        account_month = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_MONTH);
        account_week = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_WEEK);
        account_day = orderRecordService.getAccount(OrderRecord.TYPE_ACCOUNT_DAY);

        order_day = orderRecordService.orderTime(OrderRecord.TYPE_ORDERTIME_DAY);

        PopularFoodModel popularFoodModel = popularFoodsService.getPopularFood();

        res.put("account_month", account_month);
        res.put("account_week", account_week);
        res.put("account_day", account_day);
        res.put("order_day", order_day);
        res.put("popularFood", popularFoodModel);

        return res;
    }
}

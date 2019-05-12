package com.example.demo.timeTask;

import com.example.demo.domain.info.OrderRecord;
import com.example.demo.domain.info.PopularFoods;
import com.example.demo.service.info.OrderRecordService;
import com.example.demo.service.info.PopularFoodsService;
import com.example.demo.util.StringTranslator;
import com.example.demo.util.TimeUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class timerTask {
    @Autowired
    private OrderRecordService orderRecordService;

    @Autowired
    private PopularFoodsService popularFoodsService;

    /**
     * 每天更新一次欢迎菜品
     * */
    @Scheduled(cron = "0 0 0 ? * * ")
    public void updatePopularFoodList(){
        PopularFoods popularFoods = new PopularFoods();
        popularFoods.setCreateTime(TimeUtil.getTimeNow());

        HashMap<Long, Integer> res = new HashMap();
        Set<Long> set_id = new LinkedHashSet<>();

        res = orderRecordService.getAllOrderByTime(OrderRecord.TYPE_POPULARFOODS_DAY);
        set_id = res.keySet();

        Long[] ids = (Long[]) set_id.toArray();
        List<Long> max_id_list = new LinkedList<>();

        for(int i = 0; i < 10;i ++){
            int index_max = 0;
            int max_value = res.get(ids[i]);

            for(int j = i; j < ids.length; j++) {
                if (ids[j] > max_value) {
                    max_value = res.get(ids[j]);
                    index_max = j;
                }
            }

            moveItemToPos(ids, index_max, i);
            max_id_list.add(ids[i]);
        }

        String popular_foodID = StringTranslator.getStringFromList(max_id_list);

        popularFoods.setCreateTime(TimeUtil.getTimeNow());
        popularFoods.setFoodIDs(popular_foodID);
        popularFoods.setStartTime(TimeUtil.getTimeWithDay());
        popularFoods.setEndTime(popularFoods.getCreateTime());

        popularFoodsService.addOne(popularFoods);


//        iterator_id = set_id.iterator();
//
//        while( iterator_id.hasNext() ){
//
//        }

    }

    public void moveItemToPos(Long[] values, int pos_1, int endPos){
        for(int i = pos_1; i > endPos; i--){// 1 5 2 5 3
            Long temp = values[i];

            values[i] = values[i-1];
            values[i-1] = temp;
        }
    }
}

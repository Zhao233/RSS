package com.example.demo.service.info;

import com.example.demo.domain.info.WaiterDeliveryRecord;

import java.sql.Timestamp;
import java.util.List;

public interface WaiterDeliveryRecordService {


    Integer getServiceTime(int type, Long waiterID);

    Integer getAllServiceTimes(Long waiterID);

    List<Integer> getServiceTimeByTime(Timestamp startTime, Long waiterID);

    /**=================================For Customer=========================================*/

    WaiterDeliveryRecord callWaiter(String openid, Integer tableNum);


    /**=============================For Waiter=========================================*/
    WaiterDeliveryRecord save(WaiterDeliveryRecord record);

    WaiterDeliveryRecord findOneByID(Long id);

    /***/
}

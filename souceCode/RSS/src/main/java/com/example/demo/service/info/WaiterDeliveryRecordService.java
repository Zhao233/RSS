package com.example.demo.service.info;

import com.example.demo.domain.info.WaiterDeliveryRecord;

public interface WaiterDeliveryRecordService {


    /**=================================For Customer=========================================*/

    WaiterDeliveryRecord callWaiter(String openid, Integer tableNum);


    /**=============================For Waiter=========================================*/
    WaiterDeliveryRecord save(WaiterDeliveryRecord record);

    WaiterDeliveryRecord findOneByID(Long id);

    /***/
}

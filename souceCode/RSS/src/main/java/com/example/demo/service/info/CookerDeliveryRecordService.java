package com.example.demo.service.info;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;

public interface CookerDeliveryRecordService {

    /**===========================For Cooker===========================================*/
    CookerDeliveryRecord save(CookerDeliveryRecord record);

    CookerDeliveryRecord findOneByID(Long id);
}

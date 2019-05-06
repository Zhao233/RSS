package com.example.demo.service.info;

import com.example.demo.domain.info.WaiterDeliveryRecord;

public interface WaiterDeliveryRecordService {
    WaiterDeliveryRecord save(WaiterDeliveryRecord record);

    WaiterDeliveryRecord findOneByID(Long id);
}

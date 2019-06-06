package com.example.demo.service.info;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.model.cooker.CookerServiceForCookerDetailModel;

import java.sql.Timestamp;
import java.util.List;

public interface CookerDeliveryRecordService {
    /**===========================For Admin===========================================*/

    /**
     * 获取服务次数
     *
     * type：
     * 1 总服务次数
     * 2 月服务次数
     * 3 周服务次数
     * 4 日服务次数
     */
    Integer getServiceTime(int type, Long cookerID);

    Integer getAllServiceTimes(Long cookerID);

    /**
     * 获取服务次数
     *
     */
    List<Integer> getServiceTimeByTime(Timestamp timestamp, Long cookerID);


    List<CookerServiceForCookerDetailModel> getRecentCookerServiceRecords(Long cookerID);

    void sendCookerDeliveryRecordFromOrderInfo(Long orderRecordID, Integer tableNum, List<Long> foodList, List<Integer> numList);

    /**===========================For Cooker===========================================*/
    CookerDeliveryRecord save(CookerDeliveryRecord record);

    CookerDeliveryRecord findOneByID(Long id);
}

package com.example.demo.service.info;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;

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
    Integer getServiceTime(int type, String openid);

    Integer getAllServiceTimes(String openid);

    /**
     * 获取服务次数
     *
     */
    List<Integer> getServiceTimeByTime(Timestamp timestamp, String openid);


    /**===========================For Cooker===========================================*/
    CookerDeliveryRecord save(CookerDeliveryRecord record);

    CookerDeliveryRecord findOneByID(Long id);
}

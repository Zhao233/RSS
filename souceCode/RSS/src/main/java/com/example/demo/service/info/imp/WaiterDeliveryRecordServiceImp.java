package com.example.demo.service.info.imp;

import com.example.demo.asynchronousHandler.Waiter.WaiterJobHandler;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.repository.info.WaiterDeliveryRecordDao;
import com.example.demo.service.info.WaiterDeliveryRecordService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("waiterDeliveryRecordService")
public class WaiterDeliveryRecordServiceImp implements WaiterDeliveryRecordService {
    @Autowired
    private WaiterDeliveryRecordDao waiterDeliveryRecordDao;

    /**===============================For Customer=============================================*/
    @Override
    public WaiterDeliveryRecord callWaiter(String openid, Integer tableNum) {
        WaiterDeliveryRecord record = new WaiterDeliveryRecord();
        record.setCreateTime(TimeUtil.getTimeNow());
        record.setTableNum(tableNum);
        record.setType(WaiterDeliveryRecord.TYPE_SERVICE);
        record.setOpenID(openid);

        WaiterJobHandler.putMessageToWaiterMessageBlockingQueue(record);

        return record;
    }

    /**===============================For Waiter=============================================*/
    @Override
    public WaiterDeliveryRecord save(WaiterDeliveryRecord record) {
        return waiterDeliveryRecordDao.save(record);
    }

    @Override
    public WaiterDeliveryRecord findOneByID(Long id) {
        return waiterDeliveryRecordDao.getOne(id);
    }
}

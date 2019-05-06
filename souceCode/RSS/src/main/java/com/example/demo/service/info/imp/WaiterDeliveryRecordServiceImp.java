package com.example.demo.service.info.imp;

import com.example.demo.controller.adminController.userController.WaiterController;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.repository.info.WaiterDeliveryRecordDao;
import com.example.demo.service.info.WaiterDeliveryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("waiterDeliveryRecordService")
public class WaiterDeliveryRecordServiceImp implements WaiterDeliveryRecordService {
        @Autowired
        private WaiterDeliveryRecordDao waiterDeliveryRecordDao;


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

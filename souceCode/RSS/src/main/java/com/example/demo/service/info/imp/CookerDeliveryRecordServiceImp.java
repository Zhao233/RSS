package com.example.demo.service.info.imp;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.repository.info.CookerDeliveryRecordDao;
import com.example.demo.service.info.CookerDeliveryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("cookerDeliveryRecordService")
public class CookerDeliveryRecordServiceImp implements CookerDeliveryRecordService {
    @Autowired
    private CookerDeliveryRecordDao cookerDeliveryRecordDao;


    /**============================== For Cooker =============================================*/

    @Override
    public CookerDeliveryRecord save(CookerDeliveryRecord record) {
        return cookerDeliveryRecordDao.save(record);
    }

    @Override
    public CookerDeliveryRecord findOneByID(Long id) {
        return cookerDeliveryRecordDao.getOne(id);
    }
}

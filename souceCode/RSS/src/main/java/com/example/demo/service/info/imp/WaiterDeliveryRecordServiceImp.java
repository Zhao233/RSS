package com.example.demo.service.info.imp;

import com.example.demo.asynchronousHandler.Waiter.WaiterJobHandler;
import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.model.waiter.WaiterServiceForWaiterDetailModel;
import com.example.demo.repository.foodInfo.FoodDao;
import com.example.demo.repository.info.WaiterDeliveryRecordDao;
import com.example.demo.repository.user.WaiterDao;
import com.example.demo.service.info.WaiterDeliveryRecordService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Service("waiterDeliveryRecordService")
public class WaiterDeliveryRecordServiceImp implements WaiterDeliveryRecordService {
    @Autowired
    private WaiterDao waiterDao;

    @Autowired
    private WaiterDeliveryRecordDao waiterDeliveryRecordDao;

    @Autowired
    private FoodDao foodDao;

    /**===============================For Admin=============================================*/
    @Override
    public Integer getServiceTime(int type, Long waiterID) {
        Integer times = new Integer(0);

        Calendar calendar = Calendar.getInstance();

        Timestamp time_start = null;
        Timestamp time_end = new Timestamp(calendar.getTime().getTime());

        switch ( type ){
            case WaiterDeliveryRecord.TYPE_SERVICE_MONTH :
                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithMonth();

                break;

            case WaiterDeliveryRecord.TYPE_SERVICE_WEEK  :
                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithWeek();

                break;

            case WaiterDeliveryRecord.TYPE_SERVICE_DAY :
                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithDay();

                break;
        }

        times = waiterDeliveryRecordDao.getServiceTimes(time_start, time_end, waiterID);

        return times==null? 0 : times;
    }

    @Override
    public Integer getAllServiceTimes(Long waiterID){
        Integer num = new Integer(0);

        num = waiterDeliveryRecordDao.getAllServiceTimes(waiterID);
        return num == null? 0 : num;
    }

    @Override
    public List<Integer> getServiceTimeByTime(Timestamp startTime, Long waiterID) {
        List<Integer> serviceNumbers = new LinkedList<>();
        List<WaiterDeliveryRecord> waiterDeliveryRecords = new LinkedList<>();

        waiterDeliveryRecords = waiterDeliveryRecordDao.getAllWaiterDeliveryRecord(startTime, TimeUtil.getTimeNow(), waiterID);

        int index_orderRecord = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        for(int i = 0; i < 24; i++){
            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, 0);

            int hourStart = calendar.get(Calendar.HOUR_OF_DAY);

            int hourEnd = hourStart + 1;

//            if( temp_timestamp_start.after(endTIme) ){//如果超过了现在的时间就退出
//                break;
//            }

            int orderTime = 0;

            for(;index_orderRecord < waiterDeliveryRecords.size(); index_orderRecord++){
                Timestamp temp_timestamp = waiterDeliveryRecords.get(index_orderRecord).getCreateTime();

                Calendar temp_calendar = Calendar.getInstance();
                temp_calendar.setTime(temp_timestamp);

                int temp_hour = temp_calendar.get(Calendar.HOUR_OF_DAY);

                if(temp_hour < hourStart) {
                    break;
                }

                if(temp_hour > hourEnd ) {
                    break;
                }

                if(temp_hour >= hourStart && temp_hour <= hourEnd){
                    orderTime++;
                }

            }

            serviceNumbers.add(orderTime);

        }

        return serviceNumbers;
    }

    /**
     *获取10条最近服务记录
     */
    @Override
    public List<WaiterServiceForWaiterDetailModel> getRecentWaiterServiceRecords(Long waiterID){
        Pageable pageable = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "id"));

        List<WaiterDeliveryRecord> list = waiterDeliveryRecordDao.getRecentWaiterServiceRecord(waiterID, pageable);
        LinkedList<WaiterServiceForWaiterDetailModel> res = new LinkedList<>();

        for(WaiterDeliveryRecord temp : list){
            int type = temp.getType();
            Long foodID = temp.getFoodID();
            Timestamp completeTime = temp.getUpdateTime();

            String picUrl = foodDao.getFoodPicUrlById(foodID);
            String serviceType = "";

            switch (type){
                case WaiterDeliveryRecord.TYPE_DELIVERY :
                    serviceType = "派送";

                    break;
                case WaiterDeliveryRecord.TYPE_SERVICE:
                    serviceType = "到桌服务";
                    break;
            }

            WaiterServiceForWaiterDetailModel waiterServiceForWaiterDetail = new WaiterServiceForWaiterDetailModel();
            waiterServiceForWaiterDetail.setCompleteTime(completeTime);
            waiterServiceForWaiterDetail.setPicUrl(picUrl);
            waiterServiceForWaiterDetail.setServiceType(serviceType);

            res.add(waiterServiceForWaiterDetail);
        }

        return res;
    }

    @Override
    public void sendWaiterDeliveryRecordFromCookerDeliveryRecord(CookerDeliveryRecord cookerDeliveryRecord){
        WaiterDeliveryRecord record = new WaiterDeliveryRecord();
        record.setFoodID(cookerDeliveryRecord.getFoodId());
        record.setOrderRecordID(cookerDeliveryRecord.getOrderRecordId());
        record.setTableNum(cookerDeliveryRecord.getTableNum());
        record.setIsComplete(0);
        record.setCreateTime(TimeUtil.getTimeNow());
        record.setType(WaiterDeliveryRecord.TYPE_DELIVERY);

        WaiterJobHandler.putMessageToWaiterMessageBlockingQueue(record);
    }

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

    public Timestamp getTimeWithMonth(){
        Calendar temp_calendar = Calendar.getInstance();

        temp_calendar.set(Calendar.DAY_OF_MONTH, 1);
        temp_calendar.set(Calendar.HOUR_OF_DAY, 0);
        temp_calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(temp_calendar.getTime().getTime());
    }
    public Timestamp getTimeWithWeek(){
        Calendar temp_calendar = Calendar.getInstance();

        temp_calendar.set(Calendar.DAY_OF_WEEK, 2);
        temp_calendar.set(Calendar.HOUR_OF_DAY, 0);
        temp_calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(temp_calendar.getTimeInMillis());
    }
    public Timestamp getTimeWithDay(){
        Calendar temp_calendar = Calendar.getInstance();

        temp_calendar.set(Calendar.HOUR_OF_DAY, 0);
        temp_calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(temp_calendar.getTime().getTime());
    }
}

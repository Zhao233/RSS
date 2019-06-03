package com.example.demo.service.info.imp;

import com.example.demo.asynchronousHandler.Waiter.WaiterJobHandler;
import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.WaiterDeliveryRecord;
import com.example.demo.repository.info.WaiterDeliveryRecordDao;
import com.example.demo.repository.user.WaiterDao;
import com.example.demo.service.info.WaiterDeliveryRecordService;
import com.example.demo.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**===============================For Admin=============================================*/
    @Override
    public Integer getServiceTime(int type, String openid) {
        Long id = waiterDao.getWaiterByLoginID(openid).getId();
        Integer times = new Integer(0);

        Calendar calendar = Calendar.getInstance();

        Timestamp time_start = null;
        Timestamp time_end = new Timestamp(calendar.getTime().getTime());

        switch ( type ){
            case 1 :
                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithMonth();

                break;

            case 2 :
                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithWeek();

                break;

            case 3:
                time_end = new Timestamp(calendar.getTime().getTime());

                time_start = getTimeWithDay();

                break;
        }

        times = waiterDeliveryRecordDao.getDeliveryTime(time_start, time_end, id);

        if(times == null){
            return Integer.valueOf(0);
        }

        return times;
    }

    @Override
    public Integer getAllServiceTimes(String openid){
        Long id = waiterDao.getWaiterByOpenID(openid).getId();

        Integer num = new Integer(0);

        num = waiterDeliveryRecordDao.getAllDeliveryTimes(id);

        return num == null? 0 : num;
    }

    @Override
    public List<Integer> getServiceTimeByTime(Timestamp startTime, String openid) {
        Long id = waiterDao.getWaiterByOpenID(openid).getId();

        List<Integer> serviceNumbers = new LinkedList<>();
        List<CookerDeliveryRecord> cookerDeliveryRecords = new LinkedList<>();

        Timestamp star = TimeUtil.getTimeNow();

        cookerDeliveryRecords = waiterDeliveryRecordDao.getAllCookerDeliveryRecord(startTime, TimeUtil.getTimeNow(), id);

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

            for(;index_orderRecord < cookerDeliveryRecords.size(); index_orderRecord++){
                Timestamp temp_timestamp = cookerDeliveryRecords.get(index_orderRecord).getCreateTime();

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
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
    public Timestamp getTimeWithWeek(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, 2);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
    public Timestamp getTimeWithDay(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
}

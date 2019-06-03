package com.example.demo.service.info.imp;

import com.example.demo.domain.info.CookerDeliveryRecord;
import com.example.demo.domain.info.OrderRecord;
import com.example.demo.repository.info.CookerDeliveryRecordDao;
import com.example.demo.repository.user.CookerDao;
import com.example.demo.service.info.CookerDeliveryRecordService;
import com.example.demo.service.user.CookerService;
import com.example.demo.util.TimeUtil;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;


@Service("cookerDeliveryRecordService")
public class CookerDeliveryRecordServiceImp implements CookerDeliveryRecordService {
    @Autowired
    private CookerDao cookerDao;

    @Autowired
    private CookerDeliveryRecordDao cookerDeliveryRecordDao;

    /**============================== For Admin =============================================*/
    @Override
    public Integer getServiceTime(int type, String openid) {
        Long id = cookerDao.getCookerByOpenID(openid).getId();
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

        times = cookerDeliveryRecordDao.getDeliveryTime(time_start, time_end, id);

        if(times == null){
            return Integer.valueOf(0);
        }

        return times;
    }

    @Override
    public Integer getAllServiceTimes(String openid){
        Long id = cookerDao.getCookerByOpenID(openid).getId();
        Integer num = new Integer(0);

        num = cookerDeliveryRecordDao.getAllDeliveryTimes(id);

        return num == null? 0 : num;
    }

    @Override
    public List<Integer> getServiceTimeByTime(Timestamp startTime, String openid) {
        Long id = cookerDao.getCookerByOpenID(openid).getId();

        List<Integer> serviceNumbers = new LinkedList<>();
        List<CookerDeliveryRecord> cookerDeliveryRecords = new LinkedList<>();

        Timestamp star = TimeUtil.getTimeNow();

        cookerDeliveryRecords = cookerDeliveryRecordDao.getAllCookerDeliveryRecord(startTime, TimeUtil.getTimeNow(), id);

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

    /**============================== For Cooker =============================================*/

    @Override
    public CookerDeliveryRecord save(CookerDeliveryRecord record) {
        return cookerDeliveryRecordDao.save(record);
    }

    @Override
    public CookerDeliveryRecord findOneByID(Long id) {
        return cookerDeliveryRecordDao.getOne(id);
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

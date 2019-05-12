package com.example.demo.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimeUtil {
    private static Date date = new Date();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Timestamp getTimeNow(){
        Timestamp timestamp = new Timestamp( date.getTime() );

        return timestamp;
    }

    /**
     * 时间格式:xxxx-xx-xx:xx-xx-xx
     * */
    public static Timestamp StringToTimeStamp(String time){
        try {
            Date date = simpleDateFormat.parse(time);

            Timestamp timestamp = new Timestamp(date.getTime());

            return timestamp;
        } catch (Exception e) {
            e.printStackTrace();

            return new Timestamp(new Date().getTime());
        }
    }

    public static Timestamp MilTimeToTimeStamp(long milTime){
        return new Timestamp(milTime);
    }

    public static String getFormattedTime(Timestamp time){
        String formattedTime = "";

        Date date = new Date(time.getTime());

        formattedTime = simpleDateFormat.format(date);

        return formattedTime;
    }

    public static Timestamp getTimeWithMonth(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
    public static Timestamp getTimeWithWeek(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
    public static Timestamp getTimeWithDay(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return new Timestamp(calendar.getTime().getTime());
    }
}

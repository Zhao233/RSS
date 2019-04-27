package com.example.demo.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
}

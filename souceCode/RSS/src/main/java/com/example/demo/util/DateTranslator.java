package com.example.demo.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTranslator {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



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
}

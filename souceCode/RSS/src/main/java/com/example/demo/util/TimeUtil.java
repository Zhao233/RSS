package com.example.demo.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;

public class TimeUtil {
    private static Date date = new Date();

    public static Timestamp getTimeNow(){
        Timestamp timestamp = new Timestamp( date.getTime() );

        return timestamp;
    }
}

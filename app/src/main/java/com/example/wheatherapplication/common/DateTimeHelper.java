package com.example.wheatherapplication.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {

    public static String unixTimeStampToDate(double unixTimeStamp){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        date.setTime((long)unixTimeStamp*1000);
        return dateFormat.format(date);
    }

    public static String getDateNow(){
        DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}

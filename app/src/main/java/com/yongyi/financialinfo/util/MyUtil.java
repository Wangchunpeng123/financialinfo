package com.yongyi.financialinfo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtil {
    //long转日期
    public static String longToDate(long lo){
        Date date = new Date(lo);
       // SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sd = new SimpleDateFormat("MM-dd HH:mm");
        return sd.format(date);
    }
}

package com.skysport.core.utils;

import com.skysport.core.constant.CharConstant;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/17.
 */
public enum DateUtils {
    SINGLETONE;
    public final static String YYYYMMDD = "yyyyMMdd";
    public final static String YYYY_MM_DD = "yyyy-mm-dd";
    public final static String YYYYMMDDHHMMSS = "yyyyMMddhhmmss";




    public String getYyyy() {
        LocalDate today = LocalDate.now();
        return String.valueOf(today.getYear());
    }

    public String getYyyyMm() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        String yyyymm;
        if (month < 10) yyyymm = today.getYear() + CharConstant.ZERO + today.getMonthValue();
        else yyyymm = today.getYear() + CharConstant.EMPTY + today.getMonthValue();
        return yyyymm;

    }

    public String getYyyyMmdd() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();
        String yyyymmdd;
        if (month < 10) {
            yyyymmdd = today.getYear() + CharConstant.ZERO + today.getMonthValue() + day;
        } else {
            yyyymmdd = today.getYear() + CharConstant.EMPTY + today.getMonthValue() + day;
        }
        return yyyymmdd;

    }

    /**
     * 将日期转换成指定格式
     *
     * @param format
     * @return
     */
    public String format(String format) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }


}

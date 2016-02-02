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
    public final static String YYYY_MM_DD = "yyyy-mm-dd";
    public final static String YYYYMMDDHHMMSS = "yyyyMMddhhmmss";

    /**
     * 获取当前日子
     *
     * @return
     */
    public String getNowDate() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }

    public String getYyyyMm() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        String yyyymm;
        if (month < 10) yyyymm = today.getYear() + CharConstant.ZERO + today.getMonthValue();
        else yyyymm = today.getYear() + CharConstant.EMPTY + today.getMonthValue();
        return yyyymm;

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

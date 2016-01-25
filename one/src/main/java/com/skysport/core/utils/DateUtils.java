package com.skysport.core.utils;

import java.time.LocalDate;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/17.
 */
public enum DateUtils {
    SINGLETONE;
    private static String YYYY_MM_DD = "yyyy-mm-dd";

    /**
     * 获取当前日子
     *
     * @return
     */
    public String getNowDate() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }
}

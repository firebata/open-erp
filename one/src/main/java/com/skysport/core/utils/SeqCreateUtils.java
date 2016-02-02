package com.skysport.core.utils;

/**
 * 说明:
 * Created by zhangjh on 2015/8/24.
 */
public class SeqCreateUtils {
    public SeqCreateUtils() {
        super();
    }


    /**
     * 获取项目编号
     *
     * @param seriesId 系列id
     * @return 项目id:4位随机数+时间戳(号+月+年+秒+分+小时+毫秒)+系列id
     */
    public static String newRrojectSeq(String seriesId) {
        StringBuilder projectSeqNo = new StringBuilder();
        long randomNum = java.util.concurrent.ThreadLocalRandom.current().nextInt(1000, 10000);
        String timestamp = DateUtils.SINGLETONE.format(DateUtils.YYYYMMDDHHMMSS);
        projectSeqNo.append(randomNum).append(timestamp).append(seriesId);
        return projectSeqNo.toString();
    }

    /**
     * 获取BOM编号
     *
     * @return BOM编号:时间戳(年月号小时分秒毫秒)+2位随机数
     */
    public static String newBomSeq() {
        StringBuilder projectSeqNo = new StringBuilder();
        long randomNum = java.util.concurrent.ThreadLocalRandom.current().nextInt(10, 100);
        String timestamp = DateUtils.SINGLETONE.format(DateUtils.YYYYMMDDHHMMSS);
        projectSeqNo.append(timestamp).append(randomNum);
        return projectSeqNo.toString();
    }


}

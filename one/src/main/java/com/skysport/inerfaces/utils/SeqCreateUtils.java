package com.skysport.inerfaces.utils;

import com.skysport.core.utils.DateUtils;
import com.skysport.inerfaces.bean.develop.ProjectCategoryInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * 说明:
 * Created by zhangjh on 2015/8/24.
 */
public enum SeqCreateUtils {
    SINGLETONE;
    private static String PRJECT_SEQ_FLAG = "1";
    private static String PRJECTITEM_SEQ_FLAG = "2";
    private static String BOM_SEQ_FLAG = "3";

    /**
     * 获取项目编号
     *
     * @param seriesId 系列id
     * @return 项目id:4位随机数+时间戳(号+月+年+秒+分+小时+毫秒)+系列id
     */
    public String newRrojectSeq(String seriesId) {
        StringBuilder seqNo = getTimstamp();
        seqNo.append(PRJECT_SEQ_FLAG).append(getReplaceNum(seriesId));
        return seqNo.toString();
    }

    public String newBomSeq(String sexId) {
        StringBuilder seqNo = getTimstamp();
        seqNo.append(BOM_SEQ_FLAG).append(getReplaceNum(sexId));
        return seqNo.toString();
    }

    public String newProjectItemSeq(ProjectCategoryInfo categoryInfo) {
        StringBuilder seqNo = getTimstamp();
        seqNo.append(PRJECTITEM_SEQ_FLAG).append(getReplaceNum(categoryInfo.getCategoryAid())).append(getReplaceNum(categoryInfo.getCategoryBid()));
        return seqNo.toString();
    }

    public StringBuilder getTimstamp() {
        StringBuilder seqNo = new StringBuilder();
        long randomNum = java.util.concurrent.ThreadLocalRandom.current().nextInt(100, 1000);
        String timestamp = DateUtils.SINGLETONE.format(DateUtils.YYYYMMDDHHMMSSSSS);
        seqNo.append(timestamp).append(randomNum);
        return seqNo;
    }

    public String getReplaceNum(String input) {
        if (StringUtils.isEmpty(input)) {
            long randomNum = java.util.concurrent.ThreadLocalRandom.current().nextInt(10, 100);
            return String.valueOf(randomNum);
        }
        return input;

    }

}

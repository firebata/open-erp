package com.skysport.core.model.seqno.helper;

/**
 * Created by zhangjh on 2015/6/1.
 */
public enum IncrementNumberHelper {
    SINGLETONE;

    /**
     * @param length 返回的字符串长度
     * @param seqNo  序列号
     * @return
     */
    public String formatSeqNo(int length, int seqNo) {
        String format = "%0" + length + "d";
        String result = String.format(format, seqNo);
        return result;
    }


}

package com.skysport.inerfaces.utils;

import com.skysport.core.constant.DictionaryKeyConstant;
import com.skysport.core.instance.DictionaryInfo;
import com.skysport.core.model.seqno.helper.IncrementNumberHelper;
import com.skysport.core.model.seqno.service.IncrementNumber;

/**
 * 获取序列号的辅助类
 * Created by zhangjh on 2015/6/3.
 */
public enum BuildSeqNoHelper {
    SINGLETONE;

    /**
     * 将递增的序列号转成指定长度的序列号（采用左侧补0）
     *
     * @param incrementNumber IncrementNumber
     */
    public String getFullSeqNo(String kind_name, IncrementNumber incrementNumber) {
        return getFullSeqNo(kind_name, incrementNumber, 0);
    }

    /**
     * 此方法用户“经常改变的标志”的序列号，例如项目号按照年份、区域等组成的标志 会改变，所以不肯定把标志定义在数据字典中。
     *
     * @param kind_name
     * @param incrementNumber
     * @param defaultLength
     * @return
     */
    public String getFullSeqNo(String kind_name, IncrementNumber incrementNumber, int defaultLength) {
        int nextVal = incrementNumber.nextVal(kind_name);
        int length = defaultLength;
        if (defaultLength == 0) {
            length = Integer.parseInt(DictionaryInfo.SINGLETONE.getDictionaryValue(DictionaryKeyConstant.SEQ_NO_LENGTH, kind_name));
        }
        String fullSeqNo = IncrementNumberHelper.SINGLETONE.formatSeqNo(length, nextVal);
        return fullSeqNo;
    }

    public String getNextSeqNo(String kind_name, String currentSeqNo, IncrementNumber incrementNumber) {
        int length = Integer.parseInt(DictionaryInfo.SINGLETONE.getDictionaryValue(DictionaryKeyConstant.SEQ_NO_LENGTH, kind_name));
        String nextVal = incrementNumber.nextVal(kind_name, length, currentSeqNo);
        return nextVal;
    }


}

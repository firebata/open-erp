package com.skysport.inerfaces.utils;

import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.model.seqno.helper.IncrementNumberHelper;

/**
 * 获取序列号的辅助类
 * Created by zhangjh on 2015/6/3.
 */
public enum BuildSeqNoHelper {
    SINGLETONE;

    /**
     * 将递增的序列号转成指定长度的序列号（采用左侧补0）
     *
     */
    public String getFullSeqNo(String kind_name) {
        return getFullSeqNo(kind_name, 0);
    }


    /**
     * 此方法用户“经常改变的标志”的序列号，例如项目号按照年份、区域等组成的标志 会改变，所以不肯定把标志定义在数据字典中。
     *
     * @param kind_name
     * @param defaultLength
     * @return
     */
    public String getFullSeqNo(String kind_name, int defaultLength) {
        IncrementNumberService incrementNumberService = SpringContextHolder.getBean("incrementNumber");
        int nextVal = incrementNumberService.nextVal(kind_name);
        int length = defaultLength;
        if (defaultLength == 0) {
            length = Integer.parseInt(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.SEQ_NO_LENGTH, kind_name));
        }
        String fullSeqNo = IncrementNumberHelper.SINGLETONE.formatSeqNo(length, nextVal);
        return fullSeqNo;
    }

    public String getNextSeqNo(String kind_name, String currentSeqNo, IncrementNumberService incrementNumberService) {
        int length = Integer.parseInt(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.SEQ_NO_LENGTH, kind_name));
        String nextVal = incrementNumberService.nextVal(kind_name, length, currentSeqNo);
        return nextVal;
    }

    public String getNextSeqNo(String kind_name, String currentSeqNo) {
        IncrementNumberService incrementNumberService = SpringContextHolder.getBean("incrementNumber");
        int length = Integer.parseInt(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.SEQ_NO_LENGTH, kind_name));
        String nextVal = incrementNumberService.nextVal(kind_name, length, currentSeqNo);
        return nextVal;
    }


}

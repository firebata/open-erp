package com.skysport.core.model.init.helper;

import com.skysport.core.bean.system.DictionaryVo;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.model.init.service.IDictionaryService;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/1.
 */
public enum DictionaryHelper {
    /**
     * 单例
     */
    SINGLETONE;


    public void initDictionary() {
        IDictionaryService service = SpringContextHolder.getBean("dictionaryService");
        List<DictionaryVo> dictionaries = service.queryAllDictionaries();
        initDictionaryMap(dictionaries);
    }


    public void initDictionaryMap(List<DictionaryVo> dictionaries) {
        for (int index = 0; index < dictionaries.size(); index++) {
            DictionaryVo dictionary = dictionaries.get(index);
            DictionaryInfoCachedMap.SINGLETONE.initDictionaryMap(dictionary);
        }

    }


}

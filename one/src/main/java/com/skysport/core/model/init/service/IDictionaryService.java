package com.skysport.core.model.init.service;

import com.skysport.core.bean.system.DictionaryInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/6/1.
 */
public interface IDictionaryService {
    public List<DictionaryInfo> queryAllDictionaries();
}

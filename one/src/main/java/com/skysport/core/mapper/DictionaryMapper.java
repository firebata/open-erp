package com.skysport.core.mapper;

import com.skysport.core.bean.system.DictionaryInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhangjh
 * @version:2015年5月6日 下午4:07:33
 */
@Component("dictionaryMapper")
public interface DictionaryMapper {
    List<DictionaryInfo> searchDictionary();
}

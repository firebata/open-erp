package com.skysport.core.mapper;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by zhangjh on 2015/6/1.
 */
@Repository("tableKeyMapper")
public interface TableKeyMapper {

    void nextVal(Map<String, Object> paramsMap);

    void nextVal2(Map<String, Object> paramsMap);
}

package com.skysport.core.mapper;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhangjh on 2015/6/1.
 */
@Component("tableKeyMapper")
public interface TableKeyMapper {

    void nextVal(Map<String, Object> paramsMap);

    void nextVal2(Map<String, Object> paramsMap);
}

package com.skysport.core.model.seqno.service.impl;

import com.skysport.core.mapper.TableKeyMapper;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangjh on 2015/6/1.
 */
@Service("incrementNumber")
public class IncrementNumberServiceImpl implements IncrementNumberService {


    @Resource(name = "tableKeyMapper")
    private TableKeyMapper tableKeyMapper;

    @Override
    public int nextVal(String name) {
//        return tableKeyMapper.nextVal(name);
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("name", name);
        tableKeyMapper.nextVal(paramsMap);

        return (Integer) paramsMap.get("result");
    }

    @Override
    public int reset() {
        return 0;
    }

    @Override
    public String nextVal(String kind_name, int length, String currentSeqNo) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("kind_name", kind_name);
        paramsMap.put("length", length);
        paramsMap.put("currentSeqNo", currentSeqNo);
        tableKeyMapper.nextVal2(paramsMap);
        return (String) paramsMap.get("result");
    }
}

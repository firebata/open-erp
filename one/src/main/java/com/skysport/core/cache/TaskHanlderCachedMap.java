package com.skysport.core.cache;

import com.skysport.interfaces.bean.task.TaskHanlderVo;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016/4/6.
 */
public enum TaskHanlderCachedMap {
    SINGLETONE;
    public Map<String, TaskHanlderVo> taskHanlderCachedMap = new HashMap();

    public TaskHanlderVo queryValue(String key) {
        return taskHanlderCachedMap.get(key);
    }
}

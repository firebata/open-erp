package com.skysport.core.instance;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.constant.CharConstant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/6.
 */
public enum SystemBaseInfo {

    SINGLETONE;
    
    private Map<String, List<SelectItem2>> bomBuildInfoMaps = new HashMap<String, List<SelectItem2>>();
    private Map<String, List<SelectItem2>> projectBuildInfoMaps = new HashMap<String, List<SelectItem2>>();

    public void pushBom(String key, List<SelectItem2> values) {
        bomBuildInfoMaps.put(key, values);
    }


    public List<SelectItem2> popBom(String key) {
        return bomBuildInfoMaps.get(key);
    }

    public Map<String, List<SelectItem2>> rtnBomInfoMap() {
        return bomBuildInfoMaps;
    }

    public void pushProject(String key, List<SelectItem2> values) {
        projectBuildInfoMaps.put(key, values);
    }


    public List<SelectItem2> popProject(String key) {
        return projectBuildInfoMaps.get(key);
    }

    public Map<String, List<SelectItem2>> rtnProjectInfoMap() {
        return projectBuildInfoMaps;
    }

    /**
     * 根据id，找到对应的下拉列表
     *
     * @param items
     * @param id
     * @return
     */
    public String getName(List<SelectItem2> items, String id) {
        String name = CharConstant.EMPTY;
        for (SelectItem2 item : items) {
            if (id.equals(item.getNatrualkey())) {
                name = item.getName();
                break;
            }
        }
        return name;
    }

}

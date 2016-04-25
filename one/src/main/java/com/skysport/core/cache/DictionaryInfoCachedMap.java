package com.skysport.core.cache;

import com.skysport.core.bean.system.DictionaryVo;
import com.skysport.core.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据字典缓存
 *
 * @author: zhangjh
 * @version:2015年5月6日 下午3:45:15
 */
public enum DictionaryInfoCachedMap {

    /**
     * 数据字典单例类
     */
    SINGLETONE;

    /**
     * 数据字典信息缓存
     */
    private Map<String, Map<String, String>> dictionayMap = new ConcurrentHashMap<String, Map<String, String>>();

    private static final byte[] LOCK = new byte[0];

    public Map<String, Map<String, String>> getDictionayMap() {
        return dictionayMap;
    }

    public void initDictionaryMap(String type, Map<String, String> valueMap) {
        synchronized (LOCK) {
            dictionayMap.put(type, valueMap);
        }
    }

    public Map<String, String> getValueMapByTypeKey(String typeName) {
        return dictionayMap.get(typeName);
    }

    /**
     * 初始化数据字段集合
     *
     * @param dictionary 数据字段对象
     */
    public void initDictionaryMap(DictionaryVo dictionary) {
        if (dictionary != null) {
            String type = dictionary.getType();
            String keyName = dictionary.getKeyName();
            String valueName = dictionary.getValueName();
            Map<String, String> valueMap = getDictionayMap().get(type);
            if (null == valueMap) {
                valueMap = new HashMap<String, String>();
                getDictionayMap().put(type, valueMap);
            }
            valueMap.put(keyName, valueName);
        }

    }

    /**
     * 从数据字典中找到字段对应
     *
     * @param type
     * @param key
     * @return
     */
    public String getDictionaryValue(String type, String key) {
        String value = CommonConstant.EMPTY;
        Map<String, String> valueMap = getDictionayMap().get(type);

        if (valueMap == null || valueMap.isEmpty()) {
            return key;
        }
        for (Map.Entry<String, String> entry : valueMap.entrySet()) {
            if (key.equals(entry.getKey())) {
                value = entry.getValue();
                break;
            }
        }
        return value;
    }

    /**
     * @param type
     * @param key
     * @param defaultValue
     * @return
     */
    public String getDictionaryValue(String type, String key, String defaultValue) {
        String configValue = getDictionaryValue(type, key);
        if (StringUtils.isBlank(configValue)) {
            configValue = String.valueOf(defaultValue);
        }
        return configValue;
    }
//    /**
//     * @param <T>
//     * @param type
//     * @param key
//     * @param defaultValue
//     * @return
//     */
//    public <T> T getDictionaryValue(String type, String key, T defaultValue) {
//        String configValue = getDictionaryValue(type, key);
//        if (StringUtils.isBlank(configValue)) {
//            configValue = String.valueOf(defaultValue);
//        }
//        return (T) configValue;
//    }

}

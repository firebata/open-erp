package com.skysport.core.bean.system;

import org.springframework.stereotype.Component;

/**
 * @author zhangjh
 */
@Component("dictionary")
public class DictionaryVo {
    private int id;
    private String type;
    private String keyName;
    private String valueName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

}

package com.skysport.core.bean.system;

import com.skysport.core.bean.CommonVo;

import java.io.Serializable;

/**
 * 下拉列表
 * Created by zhangjh on 2015/6/8.
 */
public abstract class SelectItem implements CommonVo, Cloneable, Serializable {
    private String natrualkey;
    private String name;

    public String getNatrualkey() {
        return natrualkey;
    }

    public void setNatrualkey(String natrualkey) {
        this.natrualkey = natrualkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SelectItem() {
        super();
    }

    public SelectItem(String natrualkey, String name) {
        this.natrualkey = natrualkey;
        this.name = name;
    }
}

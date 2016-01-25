package com.skysport.core.bean.system;

/**
 * 下拉列表
 * Created by zhangjh on 2015/6/8.
 */
public  class SelectItem2{
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

    public SelectItem2() {

    }

    public SelectItem2(String natrualkey, String name) {
        this.natrualkey = natrualkey;
        this.name = name;
    }
}

package com.skysport.core.bean.permission;

import java.io.Serializable;
import java.util.List;

/**
 * 说明:菜单
 * Created by zhangjh on 2015/11/26.
 */
public class Menu implements Serializable {
    private String id;
    private String pid;
    private String name;
    private String url;
    private String no;
    private List<Menu> menus;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}

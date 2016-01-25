package com.skysport.core.bean.permission;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 说明:ztree的节点
 * Created by zhangjh on 2015/10/29.
 */
public class ZTreeNode {
    @JsonProperty
    private String id;
    @JsonProperty
    private String pId;
    @JsonProperty
    private String name;
    @JsonProperty
    private boolean open;
    @JsonProperty
    private boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

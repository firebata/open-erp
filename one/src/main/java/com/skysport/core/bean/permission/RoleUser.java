package com.skysport.core.bean.permission;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/29.
 */
public class RoleUser {

    private String userId;

    private String roleId;


    private List<ZTreeNode> zTreeNodes;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<ZTreeNode> getzTreeNodes() {
        return zTreeNodes;
    }

    public void setzTreeNodes(List<ZTreeNode> zTreeNodes) {
        this.zTreeNodes = zTreeNodes;
    }
}

package com.skysport.core.bean.permission;

import java.util.List;

/**
 * 说明:角色资分配信息
 * Created by zhangjh on 2015/11/19.
 */
public class ResourceRole {

    private String resourceId;

    private String roleId;

    private List<ZTreeNode> zTreeNodes;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
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

package com.skysport.inerfaces.model.permission.resource.helper;

import com.skysport.core.bean.permission.ResourceInfo;
import com.skysport.core.bean.permission.ResourceRole;
import com.skysport.core.bean.permission.ZTreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/11/19.
 */
public enum ResourceInfoHelper {

    SINGLETONE;


    /**
     * @param resourceInfos
     * @param resourceRoles
     * @return
     */
    public List<ZTreeNode> buildZTreeNodes(List<ResourceInfo> resourceInfos, List<ResourceRole> resourceRoles) {

        List<ZTreeNode> zTreeNodes = new ArrayList<>();

        for (ResourceInfo resourceInfo : resourceInfos) {

            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(resourceInfo.getNatrualkey());
            zTreeNode.setName(resourceInfo.getName());
            zTreeNode.setPId(resourceInfo.getParentId());
            zTreeNode.setOpen(false);
            zTreeNode.setChecked(false);

            if (null != resourceRoles && !resourceRoles.isEmpty()) {
                for (ResourceRole resourceRole : resourceRoles) {
                    if (resourceRole.getResourceId().equals(resourceInfo.getNatrualkey())) {
                        zTreeNode.setChecked(true);
                        break;
                    }
                }
            }

            zTreeNodes.add(zTreeNode);

        }


        return zTreeNodes;
    }
}

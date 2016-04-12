package com.skysport.inerfaces.model.permission.roleinfo.helper;

import com.skysport.core.bean.permission.RoleInfo;
import com.skysport.core.bean.permission.RoleUser;
import com.skysport.core.bean.permission.ZTreeNode;
import com.skysport.core.bean.system.SelectItem2;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/28.
 */
public enum RoleInfoHelper {
    SINGLETONE;


    public List<ZTreeNode> buildZTreeNodes(List<RoleInfo> roleInfos, List<RoleUser> roleUsers) {

        /**
         * @param roleInfos
         * @param roleUsers
         * @return
         */
        List<ZTreeNode> zTreeNodes = new ArrayList<>();
        for (RoleInfo roleInfo : roleInfos) {
            ZTreeNode zTreeNode = new ZTreeNode();
            zTreeNode.setId(roleInfo.getNatrualkey());
            zTreeNode.setName(roleInfo.getName());
            zTreeNode.setPId(roleInfo.getParentId());
            zTreeNode.setOpen(false);
            zTreeNode.setChecked(false);
            if (null != roleUsers && !roleUsers.isEmpty()) {
                for (RoleUser roleUser : roleUsers) {
                    if (roleUser.getRoleId().equals(roleInfo.getNatrualkey())) {
                        zTreeNode.setChecked(true);
                        break;
                    }
                }
            }
            zTreeNodes.add(zTreeNode);
        }
        return zTreeNodes;
    }

    /**
     * 直接在id位置替换成name就可以，前段页面用id来展示名字
     * 1,将部门id转换成部门名
     * 2,将父角色id转换成角色名
     *
     * @param roleInfos
     */
    public void turnSomeIdToNameInList(List<RoleInfo> roleInfos, List<SelectItem2> deptList) {
        if (null != roleInfos && !roleInfos.isEmpty()) {
            for (RoleInfo roleInfo : roleInfos) {
                //将部门id转换成部门名
                String deptId = roleInfo.getDeptId();
                String deptName = "";
                for (SelectItem2 selectItem2 : deptList) {
                    String deptIdBase = selectItem2.getNatrualkey();
                    if (deptIdBase.equals(deptId)) {
                        deptName = selectItem2.getName();
                        break;
                    }
                }
                deptId = deptName;//替换内容
                roleInfo.setDeptId(deptId);
                turnRoleIdToRoleName(roleInfo, roleInfos);
            }
        }

    }

    /**
     * 将父角色id转换成角色名
     *
     * @param roleInfo
     * @param roleInfos
     */
    private void turnRoleIdToRoleName(RoleInfo roleInfo, List<RoleInfo> roleInfos) {
        String parentId = roleInfo.getParentId();
        if (null != parentId) {
            for (RoleInfo roleInfo2 : roleInfos) {
                String roleid = roleInfo2.getNatrualkey();
                String name = roleInfo2.getName();
                //找到父角色id对应的角色信息
                if (parentId.equals(roleid)) {
                    roleInfo.setParentId(name);
                }
            }
        }

    }

}

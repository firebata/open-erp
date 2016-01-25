package com.skysport.inerfaces.model.permission;

import com.skysport.core.bean.permission.Menu;
import com.skysport.core.bean.permission.ResourceRole;
import com.skysport.core.bean.permission.RoleUser;
import com.skysport.core.bean.permission.ZTreeNode;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/29.
 */
public interface IPermissionService {
    List<ZTreeNode> queryAllRole(String userId);

    void saveRoleUser(RoleUser saveRoleUser);

    List<ZTreeNode> queryAllRes(String roleId);

    void saveResourceRole(ResourceRole saveResourceRole);

    List<Menu> selectMenu(String userId);
}

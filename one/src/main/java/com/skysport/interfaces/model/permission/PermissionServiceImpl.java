package com.skysport.interfaces.model.permission;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.permission.*;
import com.skysport.core.exception.BusinessException;
import com.skysport.core.model.login.service.IUserService;
import com.skysport.core.utils.UserUtils;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.constant.ReturnCodeConstant;
import com.skysport.interfaces.mapper.permission.PermissionMapper;
import com.skysport.interfaces.model.permission.resource.helper.ResourceInfoHelper;
import com.skysport.interfaces.model.permission.resource.service.IResourceInfoService;
import com.skysport.interfaces.model.permission.roleinfo.helper.RoleInfoHelper;
import com.skysport.interfaces.model.permission.roleinfo.service.IRoleInfoService;
import com.skysport.interfaces.model.permission.sidebar.ISidebarService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明:权限模块
 * Created by zhangjh on 2015/10/29.
 */
@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService {

    @Resource(name = "permissionMapper")
    private PermissionMapper permissionMapper;

    @Resource(name = "roleInfoService")
    private IRoleInfoService roleInfoService;

    @Resource(name = "resourceInfoService")
    private IResourceInfoService resourceInfoService;

    @Resource(name = "sidebarService")
    private ISidebarService sidebarService;

    @Resource(name = "userService")
    private IUserService userService;

    /**
     * 查询所有角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<ZTreeNode> queryAllRole(String userId) {
        DataTablesInfo dataTablesInfo = null;
        List<RoleInfo> roleInfos = roleInfoService.searchInfos(dataTablesInfo);
        List<RoleUser> roleUsers = null;
        if (!StringUtils.isBlank(userId) && !userId.trim().equals("null")) {
            roleUsers = permissionMapper.queryRoleUsers(userId);
        }
        List<ZTreeNode> zTreeNodes = RoleInfoHelper.SINGLETONE.buildZTreeNodes(roleInfos, roleUsers);
        return zTreeNodes;
    }


    /**
     * 查询角色的权限
     *
     * @param roleId
     * @return
     */
    @Override
    public List<ZTreeNode> queryAllRes(String roleId) {
        DataTablesInfo dataTablesInfo = null;
        List<ResourceInfo> resourceInfos = resourceInfoService.searchInfos(dataTablesInfo);

        List<ResourceRole> resourceRoles = null;
        if (!StringUtils.isBlank(roleId) && !roleId.trim().equals("null")) {
            resourceRoles = permissionMapper.queryResourceRoles(roleId);
        }

        List<ZTreeNode> zTreeNodes = ResourceInfoHelper.SINGLETONE.buildZTreeNodes(resourceInfos, resourceRoles);

        return zTreeNodes;

    }


    /**
     * 保存资源角色
     *
     * @param saveResourceRole
     */
    @Override
    public void saveResourceRole(ResourceRole saveResourceRole) {

        String roleId = saveResourceRole.getRoleId();
        List<ZTreeNode> zTreeNodes = saveResourceRole.getzTreeNodes();
        List<ResourceRole> resourceRoles = new ArrayList<>();

        if (null != zTreeNodes && !zTreeNodes.isEmpty()) {
            for (ZTreeNode zTreeNode : zTreeNodes) {

                ResourceRole resourceRole = new ResourceRole();
                resourceRole.setRoleId(roleId);
                resourceRole.setResourceId(zTreeNode.getId());
                resourceRoles.add(resourceRole);

            }

            //删除指定用户的所有角色信息
            permissionMapper.delResourceInfoByRoleId(roleId);

            //新增分配用户的角色信息
            permissionMapper.addBatchResourceRole(resourceRoles);

        }

    }

    /**
     * 查询登录用用户的权限菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<Menu> selectMenu(String userId) {
        UserInfo userInDB = UserUtils.getUserFromSession();
        if (null == userInDB) {
            throw new BusinessException(ReturnCodeConstant.USER_IS_NOT_LOGINED);
        }
        int isAdmin = userInDB.getIsAdmin();
        //顶级菜单
        List<ResourceInfo> resourceInfos;
        if (isAdmin == WebConstants.USER_IS_ADMIN) {
            resourceInfos = sidebarService.selectAdminResource();
        } else {
            resourceInfos = sidebarService.selectAllResourceByUserId(userId);
        }

        List<Menu> menusTotle = new ArrayList<>();

        for (ResourceInfo resourceInfo : resourceInfos) {
            String resourceId = resourceInfo.getNatrualkey();
            List<Menu> menus = sidebarService.selectMenu(resourceId);
//            if (null != menus && !menus.isEmpty()) {
            Menu menu = new Menu();
            menu.setId(resourceId);
            menu.setPid(WebConstants.MENU_LEVEL_TOP_ID);
            menu.setUrl(resourceInfo.getResourceUrl());
            menu.setName(resourceInfo.getName());
            menu.setMenus(menus);
            menu.setNo(resourceInfo.getNo());
            menusTotle.add(menu);
//            }
        }
        return menusTotle;
    }


    /**
     * 保存用户角色信息
     *
     * @param saveRoleUser
     */
    @Override
    public void saveRoleUser(RoleUser saveRoleUser) {
        String userId = saveRoleUser.getUserId();
        List<ZTreeNode> zTreeNodes = saveRoleUser.getzTreeNodes();
        List<RoleUser> roleUsers = new ArrayList<>();
        if (null != zTreeNodes && !zTreeNodes.isEmpty()) {

            for (ZTreeNode zTreeNode : zTreeNodes) {
                RoleUser roleUser = new RoleUser();
                roleUser.setUserId(userId);
                roleUser.setRoleId(zTreeNode.getId());
                roleUsers.add(roleUser);
            }

            //删除指定用户的所有角色信息
            permissionMapper.delRoleInfoByUserId(userId);

            //新增分配用户的角色信息
            permissionMapper.addBatchRoleUser(roleUsers);

        }

    }


}

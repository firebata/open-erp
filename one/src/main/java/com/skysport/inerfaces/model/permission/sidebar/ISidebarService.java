package com.skysport.inerfaces.model.permission.sidebar;

import com.skysport.core.bean.permission.Menu;
import com.skysport.core.bean.permission.ResourceInfo;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/11/26.
 */
public interface ISidebarService {

    List<ResourceInfo> selectAllResourceByUserId(String userId);

    List<Menu> selectMenu(String resourceId);

    List<ResourceInfo> selectAdminResource();
}

package com.skysport.interfaces.mapper.permission;

import com.skysport.core.bean.permission.Menu;
import com.skysport.core.bean.permission.ResourceInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/11/26.
 */
@Repository("sidebarMapper")
public interface SidebarMapper {

    List<ResourceInfo> selectAllResourceByUserId(Map<String, Object> params);

    List<Menu> selectMenu(String resourceId);

    List<ResourceInfo> selectAdminResource();
}


package com.skysport.interfaces.model.permission.sidebar.impl;

import com.skysport.core.bean.permission.Menu;
import com.skysport.core.bean.permission.ResourceInfo;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.permission.SidebarMapper;
import com.skysport.interfaces.model.permission.sidebar.ISidebarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/11/26.
 */
@Service("sidebarService")
public class SidebarServiceImpl implements ISidebarService {
    @Resource(name = "sidebarMapper")
    private SidebarMapper sidebarMapper;


    /**
     * @param userId
     * @return
     */
    @Override
    public List<ResourceInfo> selectAllResourceByUserId(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", WebConstants.RESOURCE_TYPE_MENU);
        params.put("pid", WebConstants.MENU_LEVEL_TOP_ID);
        params.put("userId", userId);
        return sidebarMapper.selectAllResourceByUserId(params);
    }

    @Override
    public List<Menu> selectMenu(String resourceId) {
        return sidebarMapper.selectMenu(resourceId);
    }

    @Override
    public List<ResourceInfo> selectAdminResource() {
        return sidebarMapper.selectAdminResource();
    }
}

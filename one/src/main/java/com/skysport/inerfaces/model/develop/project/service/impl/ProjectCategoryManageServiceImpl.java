package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.inerfaces.bean.develop.ProjectCategoryInfo;
import com.skysport.inerfaces.mapper.develop.ProjectCategoryManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.develop.project.service.IProjectCategoryManageService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/26.
 */
@Service("projectCategoryManageService")
public class ProjectCategoryManageServiceImpl extends CommonServiceImpl<ProjectCategoryInfo> implements IProjectCategoryManageService, InitializingBean {
    @Resource(name = "projectCategoryManageMapper")
    private ProjectCategoryManageMapper projectCategoryManageMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = projectCategoryManageMapper;

    }

    @Override
    public List<ProjectCategoryInfo> queryProjectCategoryInfo(String natrualKey) {
        return projectCategoryManageMapper.queryProjectCategoryInfo(natrualKey);
    }
}

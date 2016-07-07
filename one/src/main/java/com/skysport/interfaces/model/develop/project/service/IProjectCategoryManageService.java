package com.skysport.interfaces.model.develop.project.service;

import com.skysport.interfaces.bean.develop.ProjectCategoryInfo;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/26.
 */
public interface IProjectCategoryManageService extends ICommonService<ProjectCategoryInfo> {

    List<ProjectCategoryInfo> queryProjectCategoryInfo(String natrualKey);
}

package com.skysport.inerfaces.model.develop.project.service;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */

import com.skysport.inerfaces.bean.relation.ProjectItemProjectIdVo;

import java.util.List;

/**
 * 项目子项目id关系处理
 */
public interface IProjectItemProjectService {

    void backupRecordsToHis(List<ProjectItemProjectIdVo> vos);

    void batchInsert(List<ProjectItemProjectIdVo> vos);

}

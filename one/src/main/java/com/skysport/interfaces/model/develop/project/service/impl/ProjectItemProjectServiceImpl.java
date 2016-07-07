package com.skysport.interfaces.model.develop.project.service.impl;

import com.skysport.interfaces.bean.relation.ProjectItemProjectIdVo;
import com.skysport.interfaces.mapper.develop.relation.ProjectItemProjectMapper;
import com.skysport.interfaces.model.relation.IRelationIdDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 说明:项目子项目关系处理
 * Created by zhangjh on 2016/4/15.
 */
@Service
public class ProjectItemProjectServiceImpl implements IRelationIdDealService<ProjectItemProjectIdVo> {

    @Autowired
    ProjectItemProjectMapper projectItemProjectMapper;

    @Override
    public void backupRecordsToHis(List<ProjectItemProjectIdVo> vos) {
        projectItemProjectMapper.backupRecordsToHis(vos);
    }

    @Override
    public void batchInsert(List<ProjectItemProjectIdVo> vos) {
        if (null != vos && !vos.isEmpty()) {
            backupRecordsToHis(vos.get(0).getParentProjectId());
            projectItemProjectMapper.batchInsert(vos);
        }

    }

    @Override
    public List<String> queryProjectChildIdsByParentId(String parentId) {
        return projectItemProjectMapper.queryProjectChildIdsByParentId(parentId);
    }

    private void backupRecordsToHis(String parentProjectId) {
        projectItemProjectMapper.backupRecordsToHisByParentProjectId(parentProjectId);
    }
}

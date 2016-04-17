package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.inerfaces.bean.relation.ProjectItemProjectIdVo;
import com.skysport.inerfaces.mapper.develop.relation.ProjectItemProjectMapper;
import com.skysport.inerfaces.model.relation.IRelationIdDealService;
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

    private void backupRecordsToHis(String parentProjectId) {
        projectItemProjectMapper.backupRecordsToHisByParentProjectId(parentProjectId);
    }
}

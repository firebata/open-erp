package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.inerfaces.bean.relation.ProjectItemBomIdVo;
import com.skysport.inerfaces.mapper.develop.relation.ProjectItemBomMapper;
import com.skysport.inerfaces.model.relation.IRelationIdDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
@Service
public class ProjectItemBomServiceImpl implements IRelationIdDealService<ProjectItemBomIdVo> {
    @Autowired
    ProjectItemBomMapper projectItemBomMapper;

    @Override
    public void backupRecordsToHis(List<ProjectItemBomIdVo> vos) {
        projectItemBomMapper.backupRecordsToHis(vos);
    }

    @Override
    public void batchInsert(List<ProjectItemBomIdVo> vos) {
        backupRecordsToHis(vos.get(0).getProjectId());
        projectItemBomMapper.batchInsert(vos);
    }

    @Override
    public List<String> queryProjectChildIdsByParentId(String projectId) {
        return null;
    }

    private void backupRecordsToHis(String projectId) {
        projectItemBomMapper.backupRecordsToHis(projectId);
    }
}

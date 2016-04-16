package com.skysport.inerfaces.mapper.develop.relation;

import com.skysport.inerfaces.bean.relation.ProjectItemProjectIdVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
@Component
public interface ProjectItemProjectMapper {

    void backupRecordsToHis(List<ProjectItemProjectIdVo> vos);

    void batchInsert(List<ProjectItemProjectIdVo> vos);

    void backupRecordsToHisByParentProjectId(@Param(value ="parentProjectId") String parentProjectId);
}

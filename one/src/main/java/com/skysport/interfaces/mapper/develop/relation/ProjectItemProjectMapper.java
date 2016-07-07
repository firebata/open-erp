package com.skysport.interfaces.mapper.develop.relation;

import com.skysport.interfaces.bean.relation.ProjectItemProjectIdVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:项目子项目关系表
 * Created by zhangjh on 2016/4/15.
 */
@Repository
public interface ProjectItemProjectMapper {

    void backupRecordsToHis(List<ProjectItemProjectIdVo> vos);

    void batchInsert(List<ProjectItemProjectIdVo> vos);

    void backupRecordsToHisByParentProjectId(@Param(value = "parentProjectId") String parentProjectId);

    List<String> queryProjectChildIdsByParentId(@Param(value = "parentId") String parentId);
}

package com.skysport.inerfaces.mapper.develop.relation;

import com.skysport.inerfaces.bean.relation.ProjectItemBomIdVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
@Repository
public interface ProjectItemBomMapper {

    void backupRecordsToHis(List<ProjectItemBomIdVo> vos);

    void batchInsert(List<ProjectItemBomIdVo> vos);

    void backupRecordsToHis(@Param(value = "projectId") String projectId);


}

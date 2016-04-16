package com.skysport.inerfaces.mapper.develop.relation;

import com.skysport.inerfaces.bean.relation.ProjectItemBomIdVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
@Component
public interface ProjectItemBomMapper {

    void backupRecordsToHis(List<ProjectItemBomIdVo> vos);

    void batchInsert(List<ProjectItemBomIdVo> vos);

    void backupRecordsToHis(String projectId);
}

package com.skysport.interfaces.mapper.develop;

import com.skysport.core.mapper.ApproveMapper;
import com.skysport.interfaces.bean.develop.ProjectBomInfo;
import com.skysport.interfaces.bean.form.develop.ProjectQueryForm;
import com.skysport.core.mapper.CommonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Repository("projectItemMapper")
public interface ProjectItemMapper extends CommonMapper<ProjectBomInfo>, ApproveMapper {
    String queryCurrentSeqNo(ProjectBomInfo info);

    void addBomInfo(ProjectBomInfo info);

    void updateBomInfo(ProjectBomInfo info);

    int listFilteredInfosCounts(ProjectQueryForm queryForm);

    List<ProjectBomInfo> searchInfos(ProjectQueryForm queryForm);

    void addBatchBomInfo(List<ProjectBomInfo> info);

    void updateBatchBomInfo(List<ProjectBomInfo> intersectionProjectBomInfos);

    void delProjectitems(List<String> subtract);
}

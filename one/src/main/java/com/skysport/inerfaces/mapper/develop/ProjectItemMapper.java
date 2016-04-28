package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.ApproveDao;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.form.develop.ProjectQueryForm;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Repository("projectItemMapper")
public interface ProjectItemMapper extends CommonDao<ProjectBomInfo> ,ApproveDao{
    String queryCurrentSeqNo(ProjectBomInfo info);

    void addBomInfo(ProjectBomInfo info);

    void updateBomInfo(ProjectBomInfo info);

    int listFilteredInfosCounts(ProjectQueryForm queryForm);

    List<ProjectBomInfo> searchInfos(ProjectQueryForm queryForm);

    void addBatchBomInfo(List<ProjectBomInfo> info);

    void updateBatchBomInfo(List<ProjectBomInfo> intersectionProjectBomInfos);

    void delProjectitems(List<String> subtract);
}
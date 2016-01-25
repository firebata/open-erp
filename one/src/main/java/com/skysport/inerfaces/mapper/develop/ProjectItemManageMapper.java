package com.skysport.inerfaces.mapper.develop;

import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.form.develop.ProjectQueryForm;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Component("projectItemManageMapper")
public interface ProjectItemManageMapper extends CommonDao<ProjectBomInfo> {
    String queryCurrentSeqNo(ProjectBomInfo info);

    void addBomInfo(ProjectBomInfo info);

    void updateBomInfo(ProjectBomInfo info);

    int listFilteredInfosCounts(ProjectQueryForm queryForm);

    List<ProjectBomInfo> searchInfos(ProjectQueryForm queryForm);

    void addBatchBomInfo(List<ProjectBomInfo> info);
}

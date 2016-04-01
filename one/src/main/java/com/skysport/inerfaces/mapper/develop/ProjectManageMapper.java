package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.ProjectInfo;
import com.skysport.inerfaces.form.BaseQueyrForm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Component("projectManageMapper")
public interface ProjectManageMapper extends CommonDao<ProjectInfo> {
    String queryCurrentSeqNo(ProjectInfo info);

    void addBomInfo(ProjectInfo info);

    void updateBomInfo(ProjectInfo info);

    int listFilteredInfosCounts(BaseQueyrForm queryForm);

    List<ProjectInfo> searchInfos(BaseQueyrForm queryForm);

    void delInfoAboutProject(String natrualkey);

    void updateProjectStatus(String projectId, int status);
}

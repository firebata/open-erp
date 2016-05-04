package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.inerfaces.bean.develop.ProjectInfo;
import com.skysport.inerfaces.bean.form.BaseQueyrForm;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Repository("projectMapper")
public interface ProjectMapper extends CommonMapper<ProjectInfo> {
    String queryCurrentSeqNo(ProjectInfo info);

    void addBomInfo(ProjectInfo info);

    void updateBomInfo(ProjectInfo info);

    int listFilteredInfosCounts(BaseQueyrForm queryForm);

    List<ProjectInfo> searchInfos(BaseQueyrForm queryForm);

    void delInfoAboutProject(String natrualkey);

    void updateProjectStatus(String projectId, int status);
}

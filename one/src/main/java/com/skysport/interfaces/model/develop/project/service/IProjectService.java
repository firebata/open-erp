package com.skysport.interfaces.model.develop.project.service;

import com.skysport.interfaces.bean.develop.ProjectInfo;
import com.skysport.interfaces.bean.form.BaseQueyrForm;
import com.skysport.interfaces.bean.form.develop.ProjectQueryForm;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public interface IProjectService extends ICommonService<ProjectInfo> {

    String queryCurrentSeqNo(ProjectInfo t);


    int listFilteredInfosCounts(BaseQueyrForm queryForm);

    List<ProjectInfo> searchInfos(ProjectQueryForm queryForm);

}

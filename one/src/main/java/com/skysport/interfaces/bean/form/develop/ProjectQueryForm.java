package com.skysport.interfaces.bean.form.develop;

import com.skysport.interfaces.bean.develop.ProjectBomInfo;
import com.skysport.interfaces.bean.form.BaseQueyrForm;

/**
 * 类说明:项目查询表单
 * Created by zhangjh on 2015/7/13.
 */
public class ProjectQueryForm extends BaseQueyrForm {

    private ProjectBomInfo projectBomInfo;

    public ProjectBomInfo getProjectBomInfo() {
        return projectBomInfo;
    }

    public void setProjectBomInfo(ProjectBomInfo projectBomInfo) {
        this.projectBomInfo = projectBomInfo;
    }
}

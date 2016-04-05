package com.skysport.inerfaces.engine.workflow.helper;

import com.skysport.inerfaces.bean.task.TaskVo;
import com.skysport.inerfaces.form.task.TaskQueryForm;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明:
 * Created by zhangjh on 2016/4/1.
 */
public enum ProjectItemTaskHelper {
    SINGLETONE;


    public TaskVo getTaskInfo(TaskQueryForm taskQueryForm, HttpServletRequest request) {
        TaskVo taskInfo = taskQueryForm.getTaskInfo();
        return taskInfo;
    }
}

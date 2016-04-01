package com.skysport.inerfaces.engine.workflow.helper;

import com.skysport.inerfaces.bean.task.TaskInfo;
import com.skysport.inerfaces.form.task.TaskQueryForm;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明:
 * Created by zhangjh on 2016/4/1.
 */
public enum ProjectItemTaskHelper {
    SINGLETONE;


    public TaskInfo getTaskInfo(TaskQueryForm taskQueryForm, HttpServletRequest request) {
        TaskInfo taskInfo = taskQueryForm.getTaskInfo();
        return taskInfo;
    }
}

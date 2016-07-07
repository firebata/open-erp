package com.skysport.interfaces.engine.workflow.develop.helper;

import com.skysport.interfaces.bean.task.TaskVo;
import com.skysport.interfaces.bean.form.task.TaskQueryForm;

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

package com.skysport.inerfaces.form.task;

import com.skysport.inerfaces.bean.task.TaskInfo;
import com.skysport.inerfaces.form.BaseQueyrForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 说明:
 * Created by zhangjh on 2016/4/1.
 */
@Component
public class TaskQueryForm extends BaseQueyrForm {
    @Autowired
    private TaskInfo taskInfo;

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}

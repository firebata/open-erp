package com.skysport.interfaces.bean.form.task;

import com.skysport.interfaces.bean.task.TaskVo;
import com.skysport.interfaces.bean.form.BaseQueyrForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 说明:
 * Created by zhangjh on 2016/4/1.
 */
@Component
public class TaskQueryForm extends BaseQueyrForm {
    @Autowired
    private TaskVo taskInfo;

    public TaskVo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskVo taskInfo) {
        this.taskInfo = taskInfo;
    }
}

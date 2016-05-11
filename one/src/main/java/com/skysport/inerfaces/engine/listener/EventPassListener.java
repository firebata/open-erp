package com.skysport.inerfaces.engine.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * 说明:
 * Created by zhangjh on 2016-05-11.
 */
public abstract class EventPassListener implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        this.executeBefore(execution);
        this.executeEnd(execution);
    }

    public void executeBefore(DelegateExecution execution) {

    }

    public void executeEnd(DelegateExecution execution) {

    }
}

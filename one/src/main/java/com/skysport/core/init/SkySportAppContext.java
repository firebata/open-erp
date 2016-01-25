package com.skysport.core.init;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 说明:
 * Created by zhangjh on 2015/12/22.
 */
public class SkySportAppContext {

    @Autowired
    public static RepositoryService repositoryService;

    @Autowired
    public static RuntimeService runtimeService;

    @Autowired
    public static TaskService taskService;

    @Autowired
    public static HistoryService historyService;

}

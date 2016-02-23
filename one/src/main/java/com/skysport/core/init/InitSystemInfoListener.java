package com.skysport.core.init;

import com.skysport.core.model.init.helper.SystemInitHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 启动加载数据字段等信息
 *
 * @author: zhangjh
 * @version:2015年5月6日 下午2:49:25
 */
public class InitSystemInfoListener implements ServletContextListener {
    protected transient Log logger = LogFactory.getLog(getClass());

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        //初始化工作流
        SkySportAppContext.repositoryService = SpringContextHolder.getBean("repositoryService");
        SkySportAppContext.runtimeService = SpringContextHolder.getBean("runtimeService");
        SkySportAppContext.taskService = SpringContextHolder.getBean("taskService");
        SkySportAppContext.historyService = SpringContextHolder.getBean("historyService");

        //初始化系统基础信息
        SystemInitHelper.SINGLETONE.init();


    }

}

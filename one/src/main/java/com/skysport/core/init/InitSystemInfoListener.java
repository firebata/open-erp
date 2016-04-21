package com.skysport.core.init;

import com.skysport.core.thread.SystemInitThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 启动加载数据字段等信息
 *
 * @author: zhangjh
 * @version:2015年5月6日 下午2:49:25
 */
public class InitSystemInfoListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        new Thread(new SystemInitThread()).start();
    }

}

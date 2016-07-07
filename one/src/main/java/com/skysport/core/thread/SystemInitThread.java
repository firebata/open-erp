package com.skysport.core.thread;

import com.skysport.core.model.init.helper.SystemInitHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * 说明:
 * Created by zhangjh on 2016/4/19.
 */
public class SystemInitThread implements Runnable {
    protected transient Log logger = LogFactory.getLog(getClass());

    @Override
    public void run() {
        //初始化系统基础信息
        try {
            SystemInitHelper.SINGLETONE.init();
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
        } catch (DocumentException e) {
            logger.info(e.getMessage(), e);
        }
    }
}

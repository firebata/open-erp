package com.skysport.core.model.init.helper;

import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.init.BasicInfoHelper;
import org.dom4j.DocumentException;

import java.io.IOException;

/**
 * 系统启动，加载初始化信息
 *
 * @author: zhangjh
 * @version:2015年5月6日 下午4:30:54
 */
public enum SystemInitHelper {

    SINGLETONE;

    public void init() throws IOException, DocumentException {
        // 初始化数据字典信息
        DictionaryHelper.SINGLETONE.initDictionary();

        // 初始化系统基础信息
        BasicInfoHelper.SINGLETONE.initSystemBaseInfo();

        //初始化任务和业务处理类的对应关系
        TaskHanlderHelper.SINGLETONE.init();


    }

    /**
     * 版本号：页面资源文件用
     *
     * @return
     */
    public String getVersion() {
        return DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue("version", "version");
    }


    /**
     * 版本号：页面资源文件用
     *
     * @return
     */
    public String getEnvironment() {
        return DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.SYSTEM_ENVIRONMENT, WebConstants.SYSTEM_ENVIRONMENT_CURRENT);
    }


}

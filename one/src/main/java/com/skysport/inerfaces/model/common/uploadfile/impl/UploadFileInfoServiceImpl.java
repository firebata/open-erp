package com.skysport.inerfaces.model.common.uploadfile.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import com.skysport.inerfaces.mapper.common.UploadFileInfoMapper;
import com.skysport.inerfaces.model.common.uploadfile.IUploadFileInfoService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 说明:
 * Created by zhangjh on 2016/2/16.
 */
@Service("uploadFileInfoService")
public class UploadFileInfoServiceImpl extends CommonServiceImpl<UploadFileInfo> implements IUploadFileInfoService, InitializingBean {
    @Resource(name = "uploadFileInfoMapper")
    private UploadFileInfoMapper uploadFileInfoMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = uploadFileInfoMapper;
    }
}

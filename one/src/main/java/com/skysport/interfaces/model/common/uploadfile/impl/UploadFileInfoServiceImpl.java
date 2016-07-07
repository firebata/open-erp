package com.skysport.interfaces.model.common.uploadfile.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.interfaces.bean.common.UploadFileInfo;
import com.skysport.interfaces.mapper.common.UploadFileInfoMapper;
import com.skysport.interfaces.model.common.uploadfile.IUploadFileInfoService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        commonMapper = uploadFileInfoMapper;
    }

    @Override
    public void del(String uid, String status) {
        uploadFileInfoMapper.del(uid, status);
    }

    @Override
    public List<UploadFileInfo> queryListByBussId(String bussId, String status) {
        return uploadFileInfoMapper.queryListByBussId(bussId, status);
    }

    @Override
    public List<UploadFileInfo> queryListByBussId(String bussId, String type, String status) {
        return uploadFileInfoMapper.queryListByBussIdAndType(bussId, type, status);
    }
}

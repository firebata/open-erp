package com.skysport.inerfaces.mapper.common;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/2/16.
 */
@Component("uploadFileInfoMapper")
public interface UploadFileInfoMapper extends CommonDao<UploadFileInfo> {


    void del(@Param(value = "bussId") String bussId, @Param(value = "status") String status);

    List<UploadFileInfo> queryListByBussId(@Param(value = "bussId") String bussId, @Param(value = "status") String status);
}

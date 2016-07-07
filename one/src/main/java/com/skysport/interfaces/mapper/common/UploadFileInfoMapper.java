package com.skysport.interfaces.mapper.common;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.interfaces.bean.common.UploadFileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/2/16.
 */
@Repository("uploadFileInfoMapper")
public interface UploadFileInfoMapper extends CommonMapper<UploadFileInfo> {


    void del(@Param(value = "uid") String uid, @Param(value = "status") String status);

    List<UploadFileInfo> queryListByBussId(@Param(value = "bussId") String bussId, @Param(value = "status") String status);

    List<UploadFileInfo> queryListByBussIdAndType(@Param(value = "bussId") String bussId, @Param(value = "type") String type, @Param(value = "status") String status);


}

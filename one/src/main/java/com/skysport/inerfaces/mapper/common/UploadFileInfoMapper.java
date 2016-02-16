package com.skysport.inerfaces.mapper.common;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import org.springframework.stereotype.Component;

/**
 * 说明:
 * Created by zhangjh on 2016/2/16.
 */
@Component("uploadFileInfoMapper")
public interface UploadFileInfoMapper extends CommonDao<UploadFileInfo> {


}

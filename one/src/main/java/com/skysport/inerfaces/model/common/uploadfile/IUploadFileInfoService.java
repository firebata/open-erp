package com.skysport.inerfaces.model.common.uploadfile;

import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.bean.common.UploadFileInfo;

import java.util.List;

/**
 * 说明:文件上传业务处理接口
 * Created by zhangjh on 2016/2/16.
 */
public interface IUploadFileInfoService extends ICommonService<UploadFileInfo> {

    /**
     * 删除指定文件
     *
     * @param uid    uid
     * @param status 文件中间状态
     */
    void del(String uid, String status);

    /**
     * 查询业务id的所有文件记录
     *
     * @param bussId 业务id
     * @param status 文件中间状态
     */
    List<UploadFileInfo> queryListByBussId(String bussId, String status);
}

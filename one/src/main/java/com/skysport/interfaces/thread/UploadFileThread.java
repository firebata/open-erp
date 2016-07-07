package com.skysport.interfaces.thread;

import com.skysport.interfaces.bean.common.UploadFileInfo;
import com.skysport.interfaces.model.common.uploadfile.IUploadFileInfoService;

import java.util.List;

/**
 * 说明:保存文件记录
 * Created by zhangjh on 2016/2/16.
 */
public class UploadFileThread implements Runnable {
    private List<UploadFileInfo> uploadFileInfoList;
    private IUploadFileInfoService uploadFileInfoService;

    public UploadFileThread() {
    }

    public UploadFileThread(List<UploadFileInfo> uploadFileInfoList, IUploadFileInfoService uploadFileInfoService) {
        this.uploadFileInfoList = uploadFileInfoList;
        this.uploadFileInfoService = uploadFileInfoService;
    }

    @Override
    public void run() {
        if (null == uploadFileInfoList || uploadFileInfoList.isEmpty()) {
            uploadFileInfoService.addBatch(uploadFileInfoList);
        }
    }
}

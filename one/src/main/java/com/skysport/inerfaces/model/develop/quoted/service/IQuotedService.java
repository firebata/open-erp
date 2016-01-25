package com.skysport.inerfaces.model.develop.quoted.service;

import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.core.model.common.ICommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 说明:
 * Created by zhangjh on 2015/9/17.
 */
public interface IQuotedService extends ICommonService<QuotedInfo> {


    void updateOrAdd(QuotedInfo quotedInfo);

    void download(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws IOException;
}

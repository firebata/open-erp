package com.skysport.inerfaces.model.develop.quoted.service;

import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 说明:
 * Created by zhangjh on 2015/9/17.
 */
public interface IQuotedService extends ICommonService<QuotedInfo>{


    QuotedInfo updateOrAdd(QuotedInfo quotedInfo);

    void downloadOffer(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws IOException, InvalidFormatException;
}

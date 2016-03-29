package com.skysport.inerfaces.model.develop.project.service;

import com.skysport.inerfaces.bean.develop.SexColor;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/11/11.
 */
public interface ISexColorService extends ICommonService<SexColor> {


    List<SexColor> searchInfos2(String projectId);

    void updateSexColorByProjectIdAndSexId(SexColor sexColor);

    void updateSexColorByProjectIdAndSexId(String sexId, String mainColorNew, String mainColorOld, String projectId);

    void delByProjectId(String projectId);
}

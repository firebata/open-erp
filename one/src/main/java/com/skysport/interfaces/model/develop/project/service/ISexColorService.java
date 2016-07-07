package com.skysport.interfaces.model.develop.project.service;

import com.skysport.interfaces.bean.develop.BomInfo;
import com.skysport.interfaces.bean.develop.SexColor;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/11/11.
 */
public interface ISexColorService extends ICommonService<SexColor> {


    List<SexColor> searchInfosByProjectId(String projectId);

    void updateSexColorByProjectIdAndSexId(SexColor sexColor);

    void updateSexColorByProjectIdAndSexId(String sexId, String mainColorNew, String mainColorOld, String projectId);

    void delByProjectId(String projectId);

    void delSexColorInfoByBomInfo(BomInfo info);
}

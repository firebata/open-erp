package com.skysport.inerfaces.model.develop.project.service;

import com.skysport.core.model.common.IApproveService;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.form.develop.ProjectQueryForm;
import com.skysport.core.model.common.ICommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public interface IProjectItemManageService extends ICommonService<ProjectBomInfo>, IApproveService {

    String queryCurrentSeqNo(ProjectBomInfo t);

    void addBomInfo(ProjectBomInfo t);

    void updateBomInfo(ProjectBomInfo t);

    int listFilteredInfosCounts(ProjectQueryForm queryForm);

    List<ProjectBomInfo> searchInfos(ProjectQueryForm queryForm);

    void addBatchBomInfo(List<ProjectBomInfo> info);

    void exportMaterialDetail(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException;

    void updateMainColors(String sexId, String mainColor, String mainColorOld, String projectId);

    void delSexColorInfoByBomInfo(BomInfo info);
}

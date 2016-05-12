package com.skysport.inerfaces.model.develop.project.service;

import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.develop.ProjectCategoryInfo;
import com.skysport.inerfaces.bean.develop.ProjectInfo;
import com.skysport.inerfaces.bean.form.develop.ProjectQueryForm;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public interface IProjectItemService extends ICommonService<ProjectBomInfo>{

    String queryCurrentSeqNo(ProjectBomInfo t);


    void updateProjectBomBaseInfo(ProjectBomInfo t);

    int listFilteredInfosCounts(ProjectQueryForm queryForm);

    List<ProjectBomInfo> searchInfos(ProjectQueryForm queryForm);

    void addBatchBomInfo(List<ProjectBomInfo> info);

    void exportMaterialDetail(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException, InvalidFormatException;

    void updateMainColors(String sexId, String mainColor, String mainColorOld, String projectId);

    void delSexColorInfoByBomInfo(BomInfo info);

    void dealProjectItemsOnProjectChanged(ProjectInfo info, List<ProjectBomInfo> projectBomInfos, List<ProjectCategoryInfo> categoryInfosInDB);

    void setStatuCodeAlive(ProjectBomInfo info, String natrualKey);

    <T> T createBoms(String businessKey);
}

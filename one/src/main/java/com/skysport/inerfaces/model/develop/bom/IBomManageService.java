package com.skysport.inerfaces.model.develop.bom;

import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.relation.ProjectItemBomIdVo;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import com.skysport.core.model.common.ICommonService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public interface IBomManageService extends ICommonService<BomInfo> {

    int listFilteredInfosCounts(BomQueryForm bomQueryForm);

    List<BomInfo> searchInfos(BomQueryForm bomQueryForm);

    List<String> queryAllBomIdsByProjectId(String projectId);
    List<String> queryBomIdsNeedLapdipByProjectId(String projectId);
    /**
     * @param bomInfo
     */
    void edit(BomInfo bomInfo);

    void delByProjectId(String natrualkey);

    List<String> queryBomIds(List<String> itemIds);

    List<BomInfo> selectAllBomSexAndMainColor(String projectId);

    void delBomNotInThisIds(List<String> tempStyles);

    void delBomInThisIds(List<BomInfo> needDelBomList);

    /**
     * 通过子项目名查询所有bom信息
     *
     * @param itemIds
     * @return
     */
    List<BomInfo> queryBomInfosByProjectItemIds(List<String> itemIds);

    void downloadProductinstruction(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws IOException, InvalidFormatException;

    void delCacadBomInfo(String natrualKey);

    List<ProjectItemBomIdVo> autoCreateBomInfoAndSave(ProjectBomInfo info);
}

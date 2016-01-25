package com.skysport.inerfaces.model.develop.bom;

import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public interface IBomManageService extends ICommonService<BomInfo> {

    public int listFilteredInfosCounts(BomQueryForm bomQueryForm);

    public List<BomInfo> searchInfos(BomQueryForm bomQueryForm);

    /**
     * @param bomInfo
     */
    public void edit(BomInfo bomInfo);

    void delByProjectId(String natrualkey);

    List<String> queryBomIds(List<String> itemIds);

    List<BomInfo> selectAllBomSexAndMainColor(String projectId);

    void delBomNotInThisIds(List<String> tempStyles);
}

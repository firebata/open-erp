package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类说明:bom信息查询
 * Created by zhangjh on 2015/7/13.
 */
@Component("bomInfoManageMapper")
public interface BomInfoManageMapper extends CommonDao<BomInfo> {

    int listFilteredInfosCounts(BomQueryForm bomQueryForm);

    List<BomInfo> searchInfos(BomQueryForm bomQueryForm);

    void delByProjectId(String projectId);

    List<String> queryBomIds(List<String> projectItemIds);

    List<BomInfo> selectAllBomSexAndMainColor(String projectId);

    void delBomNotInThisIds(List<String> tempStyles);

    void delBomInThisIds(List<BomInfo> needDelBomList);

    List<BomInfo> queryBomInfosByProjectItemIds(List<String> itemIds);


}


package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.form.develop.BomQueryForm;
import com.skysport.inerfaces.bean.relation.ProjectPojectItemBomSpVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:bom信息查询
 * Created by zhangjh on 2015/7/13.
 */
@Repository("bomInfoMapper")
public interface BomInfoMapper extends CommonMapper<BomInfo> {

    int listFilteredInfosCounts(BomQueryForm bomQueryForm);

    List<BomInfo> searchInfos(BomQueryForm bomQueryForm);


    List<BomInfo> selectAllBomSexAndMainColor(String projectId);


    void delBomInThisIds(List<BomInfo> needDelBomList);

    List<BomInfo> queryBomInfosByProjectItemIds(List<String> itemIds);

    List<String> queryAllBomIdsByProjectId(String projectId);

    ProjectPojectItemBomSpVo queryIds(@Param(value = "bomId") String bomId, @Param(value = "spId") String spId);

}


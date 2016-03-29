package com.skysport.inerfaces.model.develop.fabric;

import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.join.FabricsJoinInfo;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public interface IFabricsService extends ICommonService<FabricsInfo> {

    /**
     * 根据BOMid 查询所有的面料信息
     *
     * @param bomId bomId
     * @return 查询
     */
    List<FabricsInfo> queryFabricByBomId(String bomId);

    List<FabricsInfo> queryFabricList(String natrualKey);


    /**
     * 批量修改
     *
     * @param fabricItems
     */
    List<FabricsInfo> updateBatch(List<FabricsJoinInfo> fabricItems, BomInfo bomInfo);


    List<FabricsInfo> queryAllFabricByBomId(String bomId);
}

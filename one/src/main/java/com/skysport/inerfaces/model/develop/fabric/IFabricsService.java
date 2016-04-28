package com.skysport.inerfaces.model.develop.fabric;

import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.FabricsInfo;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public interface IFabricsService extends ICommonService<FabricsInfo> {
    /**
     * @param natrualKey
     * @return
     */
    List<FabricsInfo> queryFabricList(String natrualKey);


    /**
     * 批量修改
     *
     * @param bomInfo BomInfo
     */
    List<FabricsInfo> updateOrAddBatch(BomInfo bomInfo);

    /**
     * @param bomId
     * @return
     */
    List<FabricsInfo> queryAllFabricByBomId(String bomId);
}

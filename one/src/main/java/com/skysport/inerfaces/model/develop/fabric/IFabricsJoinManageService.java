package com.skysport.inerfaces.model.develop.fabric;

import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.join.FabricsJoinInfo;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 类说明:面料相关表信息联合查询等操作
 * Created by zhangjh on 2015/7/24.
 */
public interface IFabricsJoinManageService  extends ICommonService<FabricsInfo> {
    /**
     * 批量修改
     *
     * @param fabricItems
     */
    public void updateBatch(List<FabricsInfo> fabricItems);

    /**
     * 查询bom的所有面料
     * @param natrualKey bom id
     * @return bom的所有面料
     */
    List<FabricsJoinInfo> queryFabricList(String natrualKey);

}

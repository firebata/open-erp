package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.develop.MaterialInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Repository("materialManageMapper")
public interface MaterialManageMapper {
    public MaterialInfo saveFabricFun(MaterialInfo info);

    void addBatch(List<MaterialInfo> fabricItems);

}


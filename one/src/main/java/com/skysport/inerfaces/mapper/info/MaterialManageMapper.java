package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.develop.MaterialInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Component("materialManageMapper")
public interface MaterialManageMapper {
    public MaterialInfo saveFabricFun(MaterialInfo info);

    void addBatch(List<MaterialInfo> fabricItems);

}


package com.skysport.interfaces.mapper.info;

import com.skysport.interfaces.bean.develop.MaterialInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Repository("materialMapper")
public interface MaterialMapper {
    MaterialInfo saveFabricFun(MaterialInfo info);

    void addBatch(List<MaterialInfo> fabricItems);

}


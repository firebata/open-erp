package com.skysport.interfaces.mapper.develop;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.interfaces.bean.develop.MaterialSpInfo;
import org.springframework.stereotype.Repository;

/**
 * 说明:
 * Created by zhangjh on 2016/4/21.
 */
@Repository(value = "materialSpinfoMapper")
public interface MaterialSpinfoMapper extends CommonMapper<MaterialSpInfo> {
    void addSp(MaterialSpInfo materialSpInfo);

    void updateSp(MaterialSpInfo materialSpInfo);
}

package com.skysport.interfaces.model.info.material_classic.impl;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.interfaces.bean.info.MaterialClassicInfo;
import com.skysport.interfaces.mapper.info.MaterialClassicMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("materialClassicManageService")
public class MaterialClassicManageServiceImpl extends CommonServiceImpl<MaterialClassicInfo> implements InitializingBean {

    @Resource(name = "materialClassicMapper")
    private MaterialClassicMapper materialClassicMapper;

    @Override
    public void afterPropertiesSet() {

        commonMapper = materialClassicMapper;

    }

    @Override
    public List<SelectItem2> querySelectList(String typeId) {
        return materialClassicMapper.querySelectList2(typeId);
    }


}

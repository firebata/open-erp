package com.skysport.inerfaces.model.info.material_classic.impl;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.MaterialClassicInfo;
import com.skysport.inerfaces.mapper.info.MaterialClassicManageMapper;
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

    @Resource(name = "materialClassicManageDao")
    private MaterialClassicManageMapper materialClassicManageDao;

    @Override
    public void afterPropertiesSet() {

        commonDao = materialClassicManageDao;

    }

    @Override
    public List<SelectItem2> querySelectList(String typeId) {
        return materialClassicManageDao.querySelectList2(typeId);
    }


}

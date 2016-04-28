package com.skysport.inerfaces.model.develop.position.service.impl;

import com.skysport.inerfaces.bean.develop.KFMaterialPosition;
import com.skysport.inerfaces.mapper.develop.KFMaterialPositionMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.develop.position.service.IKFMaterialPositionService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/10.
 */
@Service("kFMaterialPositionService")
public class KFMaterialPositionServiceImpl extends CommonServiceImpl<KFMaterialPosition> implements IKFMaterialPositionService, InitializingBean {

    @Resource(name = "kFMaterialPositionMapper")
    private KFMaterialPositionMapper kFMaterialPositionMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = kFMaterialPositionMapper;
    }

    public void addBatch(List<KFMaterialPosition> infos, String materialId) {
        super.del(materialId);
        super.addBatch(infos);
    }

}

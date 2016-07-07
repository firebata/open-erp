package com.skysport.interfaces.model.develop.pantone.service.impl;

import com.skysport.interfaces.bean.develop.KFMaterialPantone;
import com.skysport.interfaces.mapper.develop.KFMaterialPantoneMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.interfaces.model.develop.pantone.service.IKFMaterialPantoneService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/1/19.
 */
@Service("kFMaterialPantoneService")
public class KFMaterialPantoneServiceImpl extends CommonServiceImpl<KFMaterialPantone> implements IKFMaterialPantoneService, InitializingBean {

    @Resource(name = "kFMaterialPantoneMapper")
    private KFMaterialPantoneMapper kFMaterialPantoneMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonMapper = kFMaterialPantoneMapper;
    }

    @Override
    public void addBatch(List<KFMaterialPantone> pantoneIds, String natrualKey) {
        if (null != pantoneIds && !pantoneIds.isEmpty()) {
            super.del(natrualKey);
            super.addBatch(pantoneIds);
        }
    }
}

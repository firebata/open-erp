package com.skysport.inerfaces.model.develop.pantone.service;

import com.skysport.inerfaces.bean.develop.KFMaterialPantone;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/1/19.
 */
public interface IKFMaterialPantoneService  extends ICommonService<KFMaterialPantone> {

    void addBatch(List<KFMaterialPantone> pantoneIds, String natrualKey);
}

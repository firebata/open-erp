package com.skysport.inerfaces.model.develop.product_instruction.helper;

import com.skysport.inerfaces.bean.develop.KfProductionInstructionEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明:
 * Created by zhangjh on 2016-05-03.
 */
public enum ProductionInstructionServiceHelper {
    SINGLETONE;


    public KfProductionInstructionEntity getInfo(HttpServletRequest request) {
        KfProductionInstructionEntity entity = new KfProductionInstructionEntity();
        return entity;
    }
}

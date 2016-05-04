package com.skysport.inerfaces.model.develop.product_instruction.helper;

import com.skysport.core.utils.UuidGeneratorUtils;
import com.skysport.inerfaces.bean.develop.KfProductionInstructionEntity;
import com.skysport.inerfaces.model.develop.product_instruction.IProductionInstructionService;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明:成衣指示单辅助类
 * Created by zhangjh on 2016-05-03.
 */
public enum ProductionInstructionServiceHelper {
    SINGLETONE;


    public KfProductionInstructionEntity getInfo(HttpServletRequest request) {
        KfProductionInstructionEntity entity = new KfProductionInstructionEntity();
        return entity;
    }

    /**
     * 查询BOM对应的生产指示单信息：1,如果存在指示单信息，则直接返回；2，如果不存在，则返回只包含指示单id的指示单信息
     *
     * @param bomId                            String
     * @param productionInstructionServiceImpl IProductionInstructionService
     * @return KfProductionInstructionEntity
     */
    public KfProductionInstructionEntity getInfoOrNeedtoAdd(String bomId, IProductionInstructionService productionInstructionServiceImpl) {
        KfProductionInstructionEntity entity = productionInstructionServiceImpl.queryInfoByNatrualKey(bomId);
        if (entity == null) {
            String productionInstructionId = UuidGeneratorUtils.getNextId();
            entity = new KfProductionInstructionEntity();
            entity.setProductionInstructionId(productionInstructionId);
            entity.setUid(productionInstructionId);
            entity.setNatrualkey(productionInstructionId);
            productionInstructionServiceImpl.add(entity);

        }
        return entity;
    }
}

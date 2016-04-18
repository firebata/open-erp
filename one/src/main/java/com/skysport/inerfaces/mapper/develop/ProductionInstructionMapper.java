package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.KfProductionInstructionEntity;
import org.springframework.stereotype.Component;

/**
 * 说明:
 * Created by zhangjh on 2016/4/18.
 */
@Component
public interface ProductionInstructionMapper extends CommonDao<KfProductionInstructionEntity> {

    KfProductionInstructionEntity queryProductionInstractionInfo(String bomId);


    KfProductionInstructionEntity queryProjectAndBomInfoByBomId(String bomId);

    void updateProductionInstruction(KfProductionInstructionEntity productionInstruction);

    void addProductionInstruction(KfProductionInstructionEntity productionInstruction);
}

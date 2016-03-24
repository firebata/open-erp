package com.skysport.inerfaces.mapper.develop;

import com.skysport.inerfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.KfProductionInstructionEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/8.
 */
@Component("factoryQuotedInfoMapper")
public interface FactoryQuotedInfoMapper extends CommonDao<FactoryQuoteInfo> {

    List<FactoryQuoteInfo> queryFactoryQuoteInfoList(String bomId);

    List<String> selectAlId(String bomId);

    void deleteByIds(List<String> allPackagingIds);

    void updateProductionInstruction(KfProductionInstructionEntity productionInstruction);

    void addProductionInstruction(KfProductionInstructionEntity productionInstruction);

    KfProductionInstructionEntity queryProjectAndBomInfoByFactoryQuoteId(String quoteInfoId);

    KfProductionInstructionEntity queryProductionInstractionInfo(String factoryQuoteId);
}

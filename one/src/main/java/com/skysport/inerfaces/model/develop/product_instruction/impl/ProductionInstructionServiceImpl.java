package com.skysport.inerfaces.model.develop.product_instruction.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.utils.UuidGeneratorUtils;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.KfProductionInstructionEntity;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.develop.ProductionInstructionMapper;
import com.skysport.inerfaces.model.common.uploadfile.IUploadFileInfoService;
import com.skysport.inerfaces.model.common.uploadfile.helper.UploadFileHelper;
import com.skysport.inerfaces.model.develop.product_instruction.IProductionInstructionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016/4/18.
 */
@Service
public class ProductionInstructionServiceImpl extends CommonServiceImpl<KfProductionInstructionEntity> implements IProductionInstructionService, InitializingBean {

    @Autowired
    private ProductionInstructionMapper productionInstructionMapper;
    @Resource(name = "uploadFileInfoService")
    private IUploadFileInfoService uploadFileInfoService;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = productionInstructionMapper;
    }

    @Override
    public KfProductionInstructionEntity updateOrAddBatch(BomInfo bomInfo) {
        KfProductionInstructionEntity productionInstruction = bomInfo.getProductionInstruction();
        String productionInstructionId = productionInstruction.getProductionInstructionId();
        //有id，更新
        if (StringUtils.isNotBlank(productionInstructionId)) {
            productionInstruction.setUid(productionInstructionId);
            productionInstructionMapper.updateProductionInstruction(productionInstruction);
        } else {
            productionInstructionId = UuidGeneratorUtils.getNextId();
            productionInstruction.setProductionInstructionId(productionInstructionId);
            productionInstruction.setUid(productionInstructionId);
            productionInstructionMapper.addProductionInstruction(productionInstruction);
        }

        List<UploadFileInfo> fileInfos = productionInstruction.getSketchUrlUidUploadFileInfos();
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, productionInstructionId, uploadFileInfoService, WebConstants.FILE_KIND_SKETCH);

        fileInfos = productionInstruction.getSpecificationUrlUidUploadFileInfos();
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, productionInstructionId, uploadFileInfoService, WebConstants.FILE_KIND_SPECIFICATION);
        return productionInstruction;
    }

    @Override
    public KfProductionInstructionEntity queryProductionInstractionInfo(String bomId) {
        KfProductionInstructionEntity productionInstruction = productionInstructionMapper.queryProductionInstractionInfo(bomId);
        if (null != productionInstruction) {
            String productionInstructionId = productionInstruction.getProductionInstructionId();
            Map<String, Object> fileinfosMap = UploadFileHelper.SINGLETONE.getFileInfoMap(uploadFileInfoService, productionInstructionId, WebConstants.FILE_KIND_SKETCH);
            productionInstruction.setSketchUrlUidFileinfosMap(fileinfosMap);
            fileinfosMap = UploadFileHelper.SINGLETONE.getFileInfoMap(uploadFileInfoService, productionInstructionId, WebConstants.FILE_KIND_SPECIFICATION);
            productionInstruction.setSpecificationUrlUidFileinfosMap(fileinfosMap);
        }
        return productionInstruction;
    }


}

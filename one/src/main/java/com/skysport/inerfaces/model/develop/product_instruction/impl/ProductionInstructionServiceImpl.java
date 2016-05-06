package com.skysport.inerfaces.model.develop.product_instruction.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.utils.UuidGeneratorUtils;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import com.skysport.inerfaces.bean.develop.KfProductionInstructionEntity;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.develop.ProductionInstructionMapper;
import com.skysport.inerfaces.model.common.uploadfile.IUploadFileInfoService;
import com.skysport.inerfaces.model.common.uploadfile.helper.UploadFileHelper;
import com.skysport.inerfaces.model.develop.product_instruction.IProductionInstructionService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 说明:成衣生产指示单
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
        commonMapper = productionInstructionMapper;
    }

    @Override
    public void edit(KfProductionInstructionEntity productionInstruction) {
//        KfProductionInstructionEntity productionInstruction = bomInfo.getProductionInstruction();
        String productionInstructionId = productionInstruction.getProductionInstructionId();
        //有id，更新
        if (StringUtils.isNotBlank(productionInstructionId)) {
            productionInstruction.setUid(productionInstructionId);
            productionInstruction.setNatrualkey(productionInstructionId);
            productionInstructionMapper.updateProductionInstruction(productionInstruction);
        }
//        else {
//            productionInstructionId = UuidGeneratorUtils.getNextId();
//            productionInstruction.setProductionInstructionId(productionInstructionId);
//            productionInstruction.setUid(productionInstructionId);
//            productionInstruction.setNatrualkey(productionInstructionId);
//            productionInstructionMapper.addProductionInstruction(productionInstruction);
//        }

        List<UploadFileInfo> fileInfos = productionInstruction.getSketchUrlUidUploadFileInfos();
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, productionInstructionId, uploadFileInfoService, WebConstants.FILE_KIND_SKETCH);

        fileInfos = productionInstruction.getSpecificationUrlUidUploadFileInfos();
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, productionInstructionId, uploadFileInfoService, WebConstants.FILE_KIND_SPECIFICATION);
    }

    @Override
    public void add(KfProductionInstructionEntity productionInstruction) {
        productionInstructionMapper.addProductionInstruction(productionInstruction);
    }

    @Override
    public KfProductionInstructionEntity queryInfoByNatrualKey(String uid) {
        KfProductionInstructionEntity productionInstruction = productionInstructionMapper.queryProductionInstractionInfo(uid);
        if (null != productionInstruction) {
            String productionInstructionId = productionInstruction.getProductionInstructionId();
            Map<String, Object> fileinfosMap = UploadFileHelper.SINGLETONE.getFileInfoMap(uploadFileInfoService, productionInstructionId, WebConstants.FILE_KIND_SKETCH);
            productionInstruction.setSketchUrlUidFileinfosMap(fileinfosMap);
            fileinfosMap = UploadFileHelper.SINGLETONE.getFileInfoMap(uploadFileInfoService, productionInstructionId, WebConstants.FILE_KIND_SPECIFICATION);
            productionInstruction.setSpecificationUrlUidFileinfosMap(fileinfosMap);
        }
        return productionInstruction;
    }


    @Override
    public void updateApproveStatus(String businessKey, String status) {

    }

    @Override
    public void updateApproveStatusBatch(List<String> businessKeys, String status) {

    }

    @Override
    public void submit(String businessKey) {

    }

    @Override
    public void submit(String taskId, String businessKey) {

    }

    @Override
    public List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String natrualKey) {
        return null;
    }

    @Override
    public List<ProcessInstance> queryProcessInstancesSuspendedByBusinessKey(String natrualKey) {
        return null;
    }

    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve) {
        return null;
    }

    /**
     * 查询BOM对应的生产指示单信息：1,如果存在指示单信息，则直接返回；2，如果不存在，则返回只包含指示单id的指示单信息
     *
     * @param bomId String
     * @return KfProductionInstructionEntity
     */
    @Override
    public KfProductionInstructionEntity getInfoOrNeedtoAdd(String bomId) {
        KfProductionInstructionEntity entity = queryInfoByNatrualKey(bomId);
        if (entity == null) {
            String productionInstructionId = UuidGeneratorUtils.getNextId();
            entity = new KfProductionInstructionEntity();
            entity.setProductionInstructionId(productionInstructionId);
            entity.setUid(productionInstructionId);
            entity.setNatrualkey(productionInstructionId);
            add(entity);

        }
        return entity;
    }

}


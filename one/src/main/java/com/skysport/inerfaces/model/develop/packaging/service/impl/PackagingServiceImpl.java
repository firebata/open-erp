package com.skysport.inerfaces.model.develop.packaging.service.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.utils.UuidGeneratorUtils;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.PackagingInfo;
import com.skysport.inerfaces.bean.develop.MaterialSpInfo;
import com.skysport.inerfaces.bean.develop.join.KFPackagingJoinInfo;
import com.skysport.inerfaces.mapper.develop.MaterialSpinfoMapper;
import com.skysport.inerfaces.mapper.develop.MaterialUnitDosageMapper;
import com.skysport.inerfaces.mapper.develop.PackagingMapper;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.pantone.helper.KFMaterialPantoneServiceHelper;
import com.skysport.inerfaces.model.develop.pantone.service.IKFMaterialPantoneService;
import com.skysport.inerfaces.model.develop.position.helper.KFMaterialPositionServiceHelper;
import com.skysport.inerfaces.model.develop.position.service.IKFMaterialPositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
@Service("packagingService")
public class PackagingServiceImpl extends CommonServiceImpl<PackagingInfo> implements IPackagingService, InitializingBean {
    @Resource(name = "packagingMapper")
    private PackagingMapper packagingMapper;

    @Resource(name = "kFMaterialPositionService")
    private IKFMaterialPositionService kFMaterialPositionService;

    @Resource(name = "kFMaterialPantoneService")
    private IKFMaterialPantoneService kFMaterialPantoneService;


    @Autowired
    private MaterialUnitDosageMapper materialUnitDosageMapper;

    @Autowired
    private MaterialSpinfoMapper materialSpinfoMapper;


    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = packagingMapper;
    }

    /**
     * 保存包装材料信息
     *
     * @param bomInfo
     */
    @Override
    public List<PackagingInfo> updateOrAddBatch(BomInfo bomInfo) {
        List<KFPackagingJoinInfo> packagingItems = bomInfo.getPackagingItems();
        List<PackagingInfo> packagingsRtn = new ArrayList<>();

        //找出被删除的包材id，并删除
        String bomId = StringUtils.isBlank(bomInfo.getNatrualkey()) ? bomInfo.getBomId() : bomInfo.getNatrualkey();

        deletePackagingByIds(packagingItems, bomId);

        if (null != packagingItems) {
            //包材id存在，修改；包材id不存在则新增
            for (KFPackagingJoinInfo packagingJoinInfo : packagingItems) {
                String packagingId = packagingJoinInfo.getPackagingInfo().getPackagingId();
                MaterialSpInfo materialSpInfo = packagingJoinInfo.getMaterialSpInfo();
                if (null != materialSpInfo.getUnitPrice() && null != materialSpInfo.getTotalAmount()) {
                    BigDecimal totalPrice = materialSpInfo.getUnitPrice().multiply(materialSpInfo.getTotalAmount());
                    materialSpInfo.setTotalPrice(totalPrice);
                }
                //有id，更新
                if (StringUtils.isNotBlank(packagingId)) {
                    setPackagingId(packagingJoinInfo, packagingId, bomId);
                    packagingMapper.updateInfo(packagingJoinInfo.getPackagingInfo());
                    materialUnitDosageMapper.updateDosage(packagingJoinInfo.getMaterialUnitDosage());
                    materialSpinfoMapper.updateSp(packagingJoinInfo.getMaterialSpInfo());
                }
                //无id，新增
                else {
//                    String kind_name = buildKindName(bomInfo, packagingJoinInfo);
//                    String seqNo = BuildSeqNoHelper.SINGLETONE.getFullSeqNo(kind_name, incrementNumberService, WebConstants.MATERIAL_SEQ_NO_LENGTH);
//                    //年份+客户+地域+系列+NNN
//                    packagingId = kind_name + seqNo;

                    packagingId = UuidGeneratorUtils.getNextId();
                    setPackagingId(packagingJoinInfo, packagingId, bomId);

                    packagingMapper.add(packagingJoinInfo.getPackagingInfo());
                    //新增包材用量
                    materialUnitDosageMapper.addDosage(packagingJoinInfo.getMaterialUnitDosage());
                    //新增包材供应商信息
                    materialSpinfoMapper.addSp(packagingJoinInfo.getMaterialSpInfo());
                }
                if (null != packagingJoinInfo.getPackagingInfo().getPositionIds() && !packagingJoinInfo.getPackagingInfo().getPositionIds().isEmpty()) {
                    kFMaterialPositionService.addBatch(packagingJoinInfo.getPackagingInfo().getPositionIds(),packagingId);
                }

                //保留物料颜色信息
                if (null != packagingJoinInfo.getPackagingInfo().getPantoneIds() && !packagingJoinInfo.getPackagingInfo().getPantoneIds().isEmpty()) {
                    kFMaterialPantoneService.addBatch(packagingJoinInfo.getPackagingInfo().getPantoneIds(),packagingId);
                }
                packagingsRtn.add(packagingJoinInfo.getPackagingInfo());
            }
        }
        return packagingsRtn;
    }

    @Override
    public List<PackagingInfo> queryPackagingList(String bomId) {
        return packagingMapper.queryPackagingList(bomId);
    }

    @Override
    public List<PackagingInfo> queryPackagingByBomId(String bomId) {
        return packagingMapper.queryPackagingByBomId(bomId);
    }

//    private String buildKindName(BomInfo bomInfo, KFPackagingJoinInfo packagingJoinInfo) {
//        String kind_name = CharConstant.EMPTY;
//        StringBuilder stringBuilder = new StringBuilder();
//        String materialTypeId = StringUtils.isBlank(packagingJoinInfo.getPackagingInfo().getMaterialTypeId()) ? WebConstants.PAKING_MATERIAL_TYPE_ID : packagingJoinInfo.getPackagingInfo().getMaterialTypeId();
//        stringBuilder.append(materialTypeId);
//        stringBuilder.append(packagingJoinInfo.getPackagingInfo().getSpId());
//        stringBuilder.append(packagingJoinInfo.getPackagingInfo().getYearCode());
//        stringBuilder.append(packagingJoinInfo.getPackagingInfo().getProductTypeId());
//        return kind_name;
//    }


    private void setPackagingId(KFPackagingJoinInfo packagingJoinInfo, String packagingId, String bomId) {
        packagingJoinInfo.getPackagingInfo().setPackagingId(packagingId);
        packagingJoinInfo.getPackagingInfo().setNatrualkey(packagingId);
        packagingJoinInfo.getPackagingInfo().setBomId(bomId);
        packagingJoinInfo.getMaterialUnitDosage().setMaterialId(packagingId);
        packagingJoinInfo.getMaterialSpInfo().setMaterialId(packagingId);
        //设置物料位置的物料id
        KFMaterialPositionServiceHelper.SINGLETONE.setPositionFabricId(packagingJoinInfo.getPackagingInfo().getPositionIds(), packagingId);
        //设置物料颜色的物料id
        KFMaterialPantoneServiceHelper.SINGLETONE.setPantoneFabricId(packagingJoinInfo.getPackagingInfo().getPantoneIds(), packagingId);
    }

    private void deletePackagingByIds(List<KFPackagingJoinInfo> packagingItems, String bomId) {

        List<String> allPackagingIds = packagingMapper.selectAllPackagingId(bomId);
        List<String> needToSavePackagingId = buildNeedSavePackagingId(packagingItems);

        allPackagingIds.removeAll(needToSavePackagingId);

        if (null != allPackagingIds && !allPackagingIds.isEmpty()) {
            packagingMapper.deletePackagingByIds(allPackagingIds);
        }


    }

    private List<String> buildNeedSavePackagingId(List<KFPackagingJoinInfo> packagingItems) {

        List<String> needToSavePackagingId = new ArrayList<String>();
        if (null != packagingItems) {
            for (KFPackagingJoinInfo packagingJoinInfo : packagingItems) {
                String packagingId = packagingJoinInfo.getPackagingInfo().getPackagingId();
                needToSavePackagingId.add(packagingId);

            }
        }
        return needToSavePackagingId;

    }
}

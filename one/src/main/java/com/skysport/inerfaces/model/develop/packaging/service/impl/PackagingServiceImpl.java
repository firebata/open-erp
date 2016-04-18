package com.skysport.inerfaces.model.develop.packaging.service.impl;

import com.skysport.core.constant.CharConstant;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.core.utils.UuidGeneratorUtils;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.KFPackaging;
import com.skysport.inerfaces.bean.develop.MaterialSpInfo;
import com.skysport.inerfaces.bean.develop.join.KFPackagingJoinInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.develop.PackagingManageMapper;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.pantone.helper.KFMaterialPantoneServiceHelper;
import com.skysport.inerfaces.model.develop.pantone.service.IKFMaterialPantoneService;
import com.skysport.inerfaces.model.develop.position.helper.KFMaterialPositionServiceHelper;
import com.skysport.inerfaces.model.develop.position.service.IKFMaterialPositionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
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
public class PackagingServiceImpl extends CommonServiceImpl<KFPackaging> implements IPackagingService, InitializingBean {
    @Resource(name = "packagingManageMapper")
    private PackagingManageMapper packagingManageMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Resource(name = "kFMaterialPositionService")
    private IKFMaterialPositionService kFMaterialPositionService;

    @Resource(name = "kFMaterialPantoneService")
    private IKFMaterialPantoneService kFMaterialPantoneService;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = packagingManageMapper;
    }

    /**
     * 保存包装材料信息
     *
     * @param bomInfo
     */
    @Override
    public List<KFPackaging> updateOrAddBatch(BomInfo bomInfo) {
        List<KFPackagingJoinInfo> packagingItems = bomInfo.getPackagingItems();
        List<KFPackaging> packagingsRtn = new ArrayList<>();

        //找出被删除的包材id，并删除
        String bomId = StringUtils.isBlank(bomInfo.getNatrualkey()) ? bomInfo.getBomId() : bomInfo.getNatrualkey();

        deletePackagingByIds(packagingItems, bomId);

        if (null != packagingItems) {
            //包材id存在，修改；包材id不存在则新增
            for (KFPackagingJoinInfo packagingJoinInfo : packagingItems) {
                String packagingId = packagingJoinInfo.getKfPackaging().getPackagingId();
                MaterialSpInfo materialSpInfo = packagingJoinInfo.getMaterialSpInfo();
                if (null != materialSpInfo.getUnitPrice() && null != materialSpInfo.getTotalAmount()) {
                    BigDecimal totalPrice = materialSpInfo.getUnitPrice().multiply(materialSpInfo.getTotalAmount());
                    materialSpInfo.setTotalPrice(totalPrice);
                }
                //有id，更新
                if (StringUtils.isNotBlank(packagingId)) {
                    setPackagingId(packagingJoinInfo, packagingId, bomId);
                    packagingManageMapper.updateInfo(packagingJoinInfo.getKfPackaging());
                    packagingManageMapper.updateDosage(packagingJoinInfo.getMaterialUnitDosage());
                    packagingManageMapper.updateSp(packagingJoinInfo.getMaterialSpInfo());
                }
                //无id，新增
                else {
//                    String kind_name = buildKindName(bomInfo, packagingJoinInfo);
//                    String seqNo = BuildSeqNoHelper.SINGLETONE.getFullSeqNo(kind_name, incrementNumberService, WebConstants.MATERIAL_SEQ_NO_LENGTH);
//                    //年份+客户+地域+系列+NNN
//                    packagingId = kind_name + seqNo;

                    packagingId = UuidGeneratorUtils.getNextId();
                    setPackagingId(packagingJoinInfo, packagingId, bomId);


                    packagingManageMapper.add(packagingJoinInfo.getKfPackaging());
                    //新增包材用量
                    packagingManageMapper.addDosage(packagingJoinInfo.getMaterialUnitDosage());
                    //新增包材供应商信息
                    packagingManageMapper.addSp(packagingJoinInfo.getMaterialSpInfo());
                }
                if (null != packagingJoinInfo.getKfPackaging().getPositionIds() && !packagingJoinInfo.getKfPackaging().getPositionIds().isEmpty()) {
                    kFMaterialPositionService.addBatch(packagingJoinInfo.getKfPackaging().getPositionIds());
                }

                //保留物料颜色信息
                if (null != packagingJoinInfo.getKfPackaging().getPantoneIds() && !packagingJoinInfo.getKfPackaging().getPantoneIds().isEmpty()) {
                    kFMaterialPantoneService.addBatch(packagingJoinInfo.getKfPackaging().getPantoneIds());
                }
                packagingsRtn.add(packagingJoinInfo.getKfPackaging());
            }
        }
        return packagingsRtn;
    }

    @Override
    public List<KFPackaging> queryPackagingList(String bomId) {
        return packagingManageMapper.queryPackagingList(bomId);
    }

    @Override
    public List<KFPackaging> queryPackagingByBomId(String bomId) {
        return packagingManageMapper.queryPackagingByBomId(bomId);
    }

    private String buildKindName(BomInfo bomInfo, KFPackagingJoinInfo packagingJoinInfo) {

        String kind_name = CharConstant.EMPTY;

        StringBuilder stringBuilder = new StringBuilder();
        String materialTypeId = StringUtils.isBlank(packagingJoinInfo.getKfPackaging().getMaterialTypeId()) ? WebConstants.PAKING_MATERIAL_TYPE_ID : packagingJoinInfo.getKfPackaging().getMaterialTypeId();
        stringBuilder.append(materialTypeId);
        stringBuilder.append(packagingJoinInfo.getKfPackaging().getSpId());
        stringBuilder.append(packagingJoinInfo.getKfPackaging().getYearCode());
        stringBuilder.append(packagingJoinInfo.getKfPackaging().getProductTypeId());

        return kind_name;
    }


    private void setPackagingId(KFPackagingJoinInfo packagingJoinInfo, String packagingId, String bomId) {

        packagingJoinInfo.getKfPackaging().setPackagingId(packagingId);
        packagingJoinInfo.getKfPackaging().setNatrualkey(packagingId);
        packagingJoinInfo.getKfPackaging().setBomId(bomId);
        packagingJoinInfo.getMaterialUnitDosage().setMaterialId(packagingId);
        packagingJoinInfo.getMaterialSpInfo().setMaterialId(packagingId);
        //设置物料位置的物料id
        KFMaterialPositionServiceHelper.SINGLETONE.setPositionFabricId(packagingJoinInfo.getKfPackaging().getPositionIds(), packagingId);
        //设置物料颜色的物料id
        KFMaterialPantoneServiceHelper.SINGLETONE.setPantoneFabricId(packagingJoinInfo.getKfPackaging().getPantoneIds(), packagingId);
    }

    private void deletePackagingByIds(List<KFPackagingJoinInfo> packagingItems, String bomId) {

        List<String> allPackagingIds = packagingManageMapper.selectAllPackagingId(bomId);
        List<String> needToSavePackagingId = buildNeedSavePackagingId(packagingItems);

        allPackagingIds.removeAll(needToSavePackagingId);

        if (null != allPackagingIds && !allPackagingIds.isEmpty()) {
            packagingManageMapper.deletePackagingByIds(allPackagingIds);
        }


    }

    private List<String> buildNeedSavePackagingId(List<KFPackagingJoinInfo> packagingItems) {

        List<String> needToSavePackagingId = new ArrayList<String>();
        if (null != packagingItems) {
            for (KFPackagingJoinInfo packagingJoinInfo : packagingItems) {
                String packagingId = packagingJoinInfo.getKfPackaging().getPackagingId();
                needToSavePackagingId.add(packagingId);

            }
        }
        return needToSavePackagingId;

    }
}

package com.skysport.inerfaces.model.develop.packaging.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.inerfaces.bean.develop.KFMaterialPantone;
import com.skysport.inerfaces.bean.develop.KFMaterialPosition;
import com.skysport.inerfaces.bean.develop.PackagingInfo;
import com.skysport.inerfaces.model.develop.pantone.helper.KFMaterialPantoneServiceHelper;
import com.skysport.inerfaces.model.develop.position.helper.KFMaterialPositionServiceHelper;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
public enum PackagingServiceHelper {
    SINGLETONE;

    /**
     * 将包装材料的id转换成名称
     *
     * @param packagings
     */
    public static void translateIdToNameInPackagings(List<PackagingInfo> packagings, String seriesName) {
        if (null != packagings && !packagings.isEmpty()) {
            for (PackagingInfo packaging : packagings) {
                translateIdToNameInPackaging(seriesName, packaging);
            }
        }
    }

    public static void translateIdToNameInPackaging(String seriesName, PackagingInfo packaging) {
        List<SelectItem2> selectItem2s;
        StringBuilder stringBuilder = new StringBuilder();

        packaging.setSeriesName(seriesName);

        //设置颜色位置
        List<KFMaterialPantone> kfMaterialPantones = packaging.getPantoneIds();
        List<KFMaterialPosition> kfMaterialPositions = packaging.getPositionIds();
        String patoneIds = KFMaterialPantoneServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPantones); //颜色用/分割
        String positionIds = KFMaterialPositionServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPositions);//位置用/分割
        packaging.setPantoneId(patoneIds);
        packaging.setPositionId(positionIds);

        String spId = packaging.getSpId();
        if (StringUtils.isNotBlank(spId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("spItems");
            spId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, spId);
            packaging.setSpId(spId);
        }


        //材质列表
        String classicId = packaging.getClassicId();
        if (StringUtils.isNotBlank(classicId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("fabricClassicItems");
            classicId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, classicId);
            stringBuilder.append(classicId);
//                fabricsInfo.setClassicId(classicId);
        }

//
//                //品名列表
        String productTypeId = packaging.getProductTypeId();
        if (StringUtils.isNotBlank(productTypeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("productTypeItems");
            productTypeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, productTypeId);
            packaging.setProductTypeId(productTypeId);
//                    stringBuilder.append(productTypeId);
        }


        String teckRequired = packaging.getTechRequired();
        if (StringUtils.isNotBlank(teckRequired)) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(teckRequired);
            } else {
                stringBuilder.append(CharConstant.COMMA).append(teckRequired);
            }
        }
        BigDecimal length = packaging.getLength();
        if (null != length) {
            stringBuilder.append(CharConstant.COMMA).append("长:").append(length);
        }
        BigDecimal width = packaging.getWidth();
        if (null != width) {
            stringBuilder.append(CharConstant.COMMA).append("宽:").append(width);
        }


        // 用量单位列表
        String unitId = packaging.getUnitId();
        if (StringUtils.isNotBlank(unitId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("unitItems");
            unitId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, unitId);
            packaging.setUnitId(unitId);
        }

        packaging.setDescription(stringBuilder.toString());
    }
}

package com.skysport.inerfaces.model.develop.accessories.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.inerfaces.bean.develop.AccessoriesInfo;
import com.skysport.inerfaces.bean.develop.KFMaterialPantone;
import com.skysport.inerfaces.bean.develop.KFMaterialPosition;
import com.skysport.inerfaces.model.develop.pantone.helper.KFMaterialPantoneServiceHelper;
import com.skysport.inerfaces.model.develop.position.helper.KFMaterialPositionServiceHelper;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/23.
 */
public enum AccessoriesServiceHelper {
    SINGLETONE;

    /**
     * 将辅料信息中的id转换成名称
     *
     * @param accessoriesInfos
     */
    public static void translateIdToNameInAccessoriesInfos(List<AccessoriesInfo> accessoriesInfos, String seriesName) {

        if (null != accessoriesInfos && !accessoriesInfos.isEmpty()) {
            for (AccessoriesInfo accessoriesInfo : accessoriesInfos) {
                translateIdToNameInAccessoriesInfo(seriesName, accessoriesInfo);

            }
        }
    }

    public static void translateIdToNameInAccessoriesInfo(String seriesName, AccessoriesInfo accessoriesInfo) {
        List<SelectItem2> selectItem2s;
        StringBuilder stringBuilder = new StringBuilder();
        String spId = accessoriesInfo.getSpId();

        accessoriesInfo.setSeriesName(seriesName);
        //设置颜色位置
        List<KFMaterialPantone> kfMaterialPantones = accessoriesInfo.getPantoneIds();
        List<KFMaterialPosition> kfMaterialPositions = accessoriesInfo.getPositionIds();
        String patoneIds = KFMaterialPantoneServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPantones); //颜色用/分割
        String positionIds = KFMaterialPositionServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPositions);//位置用/分割


        accessoriesInfo.setPantoneId(patoneIds);
        accessoriesInfo.setPositionId(positionIds);
        if (StringUtils.isNotBlank(spId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("spItems");
            spId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, spId);
            accessoriesInfo.setSpId(spId);
        }


        //材质列表
        String classicId = accessoriesInfo.getClassicId();
        if (StringUtils.isNotBlank(classicId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("fabricClassicItems");
            classicId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, classicId);
            stringBuilder.append(classicId);
//                fabricsInfo.setClassicId(classicId);
        }


//                //品名列表
        String productTypeId = accessoriesInfo.getProductTypeId();
        if (StringUtils.isNotBlank(productTypeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("productTypeItems");
            productTypeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, productTypeId);
//                    stringBuilder.append(CharConstant.COMMA).append(productTypeId);
            accessoriesInfo.setProductTypeId(productTypeId);
        }


        String teckRequired = accessoriesInfo.getTechRequired();
        if (StringUtils.isNotBlank(teckRequired)) {
            if (stringBuilder.length() == 0) {
                stringBuilder.append(teckRequired);
            } else {
                stringBuilder.append(CharConstant.COMMA).append(teckRequired);
            }
        }


        BigDecimal length = accessoriesInfo.getLength();
        if (null != length) {
            stringBuilder.append(CharConstant.COMMA).append("长:").append(length);
        }
        BigDecimal width = accessoriesInfo.getWidth();
        if (null != width) {
            stringBuilder.append(CharConstant.COMMA).append("宽:").append(width);
        }


        // 用量单位列表
        String unitId = accessoriesInfo.getUnitId();
        if (StringUtils.isNotBlank(unitId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("unitItems");
            unitId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, unitId);
            accessoriesInfo.setUnitId(unitId);
        }
        accessoriesInfo.setDescription(stringBuilder.toString());
    }

}

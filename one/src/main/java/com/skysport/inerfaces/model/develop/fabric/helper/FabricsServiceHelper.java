package com.skysport.inerfaces.model.develop.fabric.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.exception.SkySportException;
import com.skysport.core.init.SkySportAppContext;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.KFMaterialPantone;
import com.skysport.inerfaces.bean.develop.KFMaterialPosition;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.constant.develop.ReturnCodeConstant;
import com.skysport.inerfaces.model.develop.fabric.impl.FabricsServiceImpl;
import com.skysport.inerfaces.model.develop.pantone.helper.KFMaterialPantoneServiceHelper;
import com.skysport.inerfaces.model.develop.position.helper.KFMaterialPositionServiceHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum FabricsServiceHelper {

    SINGLETONE;

    /**
     *
     */
    public void refreshSelect() {
        FabricsServiceImpl fabricsManageService = SpringContextHolder.getBean("fabricsManageService");
        List<SelectItem2> fabricsItems = fabricsManageService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("fabricsItems", fabricsItems);
    }

    /**
     * @param seriesName
     * @param fabrics
     * @param fabricId
     * @return
     * @throws CloneNotSupportedException
     */
    public FabricsInfo getMainFabricInfo(String seriesName, List<FabricsInfo> fabrics, String fabricId) {
        FabricsInfo info = new FabricsInfo();
        for (FabricsInfo fabricsInfo : fabrics) {
            //新增或者修改判断是否为主线面料
            if ((StringUtils.isEmpty(fabricId) && fabricsInfo.getIsShow() == WebConstants.IS_SHOW_FABRIC) ||
                    fabricId.equals(fabricsInfo.getFabricId())) {
                try {
                    info = fabricsInfo.clone();
                } catch (CloneNotSupportedException e) {
                    throw new SkySportException(ReturnCodeConstant.SYS_EXP);
                }
                translateIdToNameInFabric(seriesName, info);
                break;
            }
        }
        return info;
    }

    /**
     * 将面料的id转换成名称
     *
     * @param fabricsInfos
     * @param exchange_type
     */
    public static void translateIdToNameInFabrics(List<FabricsInfo> fabricsInfos, String seriesName, int exchange_type) {
        if (null != fabricsInfos && !fabricsInfos.isEmpty()) {
            for (FabricsInfo fabricsInfo : fabricsInfos) {
                if (exchange_type == WebConstants.FABRIC_ID_EXCHANGE_QUOTED) {
                    if (fabricsInfo.getIsShow() == WebConstants.IS_NOT_SHOW_FABRIC) {
                        continue;
                    }
                }
                FabricsServiceHelper.SINGLETONE.translateIdToNameInFabric(seriesName, fabricsInfo);
            }
        }
    }

    /**
     * @param seriesName
     * @param fabricsInfo
     */
    public static void translateIdToNameInFabric(String seriesName, FabricsInfo fabricsInfo) {
        fabricsInfo.setSeriesName(seriesName);
        //设置颜色位置
        List<KFMaterialPantone> kfMaterialPantones = fabricsInfo.getPantoneIds();
        List<KFMaterialPosition> kfMaterialPositions = fabricsInfo.getPositionIds();
        String patoneIds = KFMaterialPantoneServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPantones); //颜色用/分割
        String positionIds = KFMaterialPositionServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPositions);//位置用/分割


        fabricsInfo.setPantoneId(patoneIds);
        fabricsInfo.setPositionId(positionIds);

        StringBuilder stringBuilder = new StringBuilder();

        chgSpId(fabricsInfo);


        //材质列表
        chgClassicId(fabricsInfo, stringBuilder);


        //品名列表
        chgProductTypeId(fabricsInfo);


        //纱支密度列表
        chgSpecificationId(fabricsInfo, stringBuilder);


        //染色方式列表
        chgDyeId(fabricsInfo, stringBuilder);


        //后整理列表
        chgFinishId(fabricsInfo, stringBuilder);

        //复合或涂层列表
        String blcId = chgBlcId(fabricsInfo, stringBuilder);

        //复合或者贴膜
        if (blcId.equals(SkySportAppContext.blc_type_fuhe) || blcId.equals(SkySportAppContext.ble_type_tiemo)) {
            //复合
            if (blcId.equals(SkySportAppContext.blc_type_fuhe)) {
                //材质列表
                chgCompositeClassicId(fabricsInfo, stringBuilder);


                //品名列表
                chgCompositeProductTypeId(fabricsInfo, stringBuilder);


                //纱支密度列表
                chgCompositeSpecificationId(fabricsInfo, stringBuilder);


                //染色方式列表
                chgCompositeDyeId(fabricsInfo, stringBuilder);


                //后整理列表
                chgCompositeFinishId(fabricsInfo, stringBuilder);

            }


            //膜或涂层的材质列表
            chgMomcId(fabricsInfo, stringBuilder);


            //膜或涂层的颜色列表
            chgComocId(fabricsInfo, stringBuilder);

            chgWvpId(fabricsInfo, stringBuilder);


            chgMtId(fabricsInfo, stringBuilder);


            chgWoblcId(fabricsInfo, stringBuilder);


        }
        chgUnitId(fabricsInfo);


        fabricsInfo.setDescription(stringBuilder.toString());
    }


    public static void chgSpId(FabricsInfo fabricsInfo) {
        List<SelectItem2> selectItem2s;
        String spId = fabricsInfo.getSpId();
        selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("spItems");
        spId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, spId);
        fabricsInfo.setSpId(spId);
    }

    public static void chgClassicId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String classicId = fabricsInfo.getClassicId();
        if (StringUtils.isNotBlank(classicId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("fabricClassicItems");
            classicId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, classicId);
            stringBuilder.append(classicId);
        }
    }

    public static void chgProductTypeId(FabricsInfo fabricsInfo) {
        List<SelectItem2> selectItem2s;
        String productTypeId = fabricsInfo.getProductTypeId();
        if (StringUtils.isNotBlank(productTypeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("productTypeItems");
            String productTypeName = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, productTypeId);
//                    stringBuilder.append(CharConstant.COMMA).append(productTypeName);
            fabricsInfo.setProductTypeId(productTypeName);
        }
    }

    public static void chgSpecificationId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String specificationId = fabricsInfo.getSpecificationId();
        if (StringUtils.isNotBlank(specificationId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("specficationItems");
            specificationId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, specificationId);
            if (stringBuilder.length() == 0) {
                stringBuilder.append(specificationId);
            } else {
                stringBuilder.append(CharConstant.COMMA).append(specificationId);
            }

        }
    }

    public static void chgDyeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String dyeId = fabricsInfo.getDyeId();
        if (StringUtils.isNotBlank(dyeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("dyeItems");
            dyeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, dyeId);
            stringBuilder.append(CharConstant.COMMA).append(dyeId);
        }
    }

    public static void chgFinishId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String finishId = fabricsInfo.getFinishId();
        if (StringUtils.isNotBlank(finishId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("finishItems");
            finishId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, finishId);
            stringBuilder.append(CharConstant.COMMA).append(finishId);
        }
    }

    public static String chgBlcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String blcId = fabricsInfo.getBlcId();
        if (StringUtils.isNotBlank(blcId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("blcItems");
            blcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, blcId);
            stringBuilder.append(CharConstant.COMMA).append(blcId);
        }
        return blcId;
    }

    public static void chgCompositeClassicId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeClassicId = fabricsInfo.getCompositeClassicId();
        if (StringUtils.isNotBlank(compositeClassicId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("fabricClassicItems");
            compositeClassicId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeClassicId);
            stringBuilder.append(CharConstant.COMMA).append(compositeClassicId);
        }
    }

    public static void chgCompositeProductTypeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeProductTypeId = fabricsInfo.getCompositeProductTypeId();
        if (StringUtils.isNotBlank(compositeProductTypeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("productTypeItems");
            compositeProductTypeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeProductTypeId);
            stringBuilder.append(CharConstant.COMMA).append(compositeProductTypeId);
        }
    }

    public static void chgCompositeSpecificationId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeSpecificationId = fabricsInfo.getCompositeSpecificationId();
        if (StringUtils.isNotBlank(compositeSpecificationId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("specficationItems");
            compositeSpecificationId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeSpecificationId);
            stringBuilder.append(CharConstant.COMMA).append(compositeSpecificationId);
        }
    }

    public static void chgCompositeDyeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeDyeId = fabricsInfo.getCompositeDyeId();
        if (StringUtils.isNotBlank(compositeDyeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("dyeItems");
            compositeDyeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeDyeId);
            stringBuilder.append(CharConstant.COMMA).append(compositeDyeId);
        }
    }

    public static void chgCompositeFinishId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeFinishId = fabricsInfo.getCompositeFinishId();
        if (StringUtils.isNotBlank(compositeFinishId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("finishItems");
            compositeFinishId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeFinishId);
            stringBuilder.append(CharConstant.COMMA).append(compositeFinishId);
        }
    }

    public static void chgMomcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String momcId = fabricsInfo.getMomcId();
        if (StringUtils.isNotBlank(momcId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("momcItems");
            momcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, momcId);
            stringBuilder.append(CharConstant.COMMA).append(momcId);
        }
    }

    public static void chgComocId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String comocId = fabricsInfo.getComocId();
        if (StringUtils.isNotBlank(comocId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("comocItems");
            comocId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, comocId);
            stringBuilder.append(CharConstant.COMMA).append(comocId);
        }
    }

    public static void chgWvpId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;//透湿程度列表
        String wvpId = fabricsInfo.getWvpId();
        if (StringUtils.isNotBlank(wvpId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("wvpItems");
            wvpId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, wvpId);
            stringBuilder.append(CharConstant.COMMA).append(wvpId);
        }
    }

    public static void chgMtId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;//膜的厚度列表
        String mtId = fabricsInfo.getMtId();
        if (StringUtils.isNotBlank(mtId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("mtItems");
            mtId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, mtId);
            stringBuilder.append(CharConstant.COMMA).append(mtId);
        }
    }

    public static void chgWoblcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;// 贴膜或涂层工艺列表
        String woblcId = fabricsInfo.getWoblcId();
        if (StringUtils.isNotBlank(woblcId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("wblcItems");
            woblcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, woblcId);
            stringBuilder.append(CharConstant.COMMA).append(woblcId);
        }
    }

    public static void chgUnitId(FabricsInfo fabricsInfo) {
        List<SelectItem2> selectItem2s;// 用量单位列表
        String unitId = fabricsInfo.getUnitId();
        if (StringUtils.isNotBlank(unitId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("unitItems");
            unitId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, unitId);
            fabricsInfo.setUnitId(unitId);
        }
    }
}

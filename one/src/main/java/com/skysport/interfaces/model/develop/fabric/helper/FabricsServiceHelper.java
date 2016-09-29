package com.skysport.interfaces.model.develop.fabric.helper;

import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.exception.BusinessException;
import com.skysport.interfaces.bean.develop.FabricsInfo;
import com.skysport.interfaces.bean.develop.KFMaterialPantone;
import com.skysport.interfaces.bean.develop.KFMaterialPosition;
import com.skysport.interfaces.bean.develop.MaterialSpInfo;
import com.skysport.interfaces.bean.develop.join.FabricsJoinInfo;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.constant.ReturnCodeConstant;
import com.skysport.interfaces.model.develop.fabric.impl.FabricsServiceImpl;
import com.skysport.interfaces.model.develop.pantone.helper.MaterialPantoneServiceHelper;
import com.skysport.interfaces.model.develop.position.helper.KFMaterialPositionServiceHelper;
import com.skysport.interfaces.model.info.sp.helper.SpInfoHelper;
import com.skysport.interfaces.model.relation.material_sp.helper.MaterialSpServiceHelper;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
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
     * 将面料的id转换成名称
     *
     * @param fabricsInfos
     * @param exchange_type
     */
    public void translateIdToNameInFabrics(List<FabricsInfo> fabricsInfos, String seriesName, int exchange_type) {
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
    public void translateIdToNameInFabric(String seriesName, FabricsInfo fabricsInfo) {
        fabricsInfo.setSeriesName(seriesName);
        //设置颜色位置
        List<KFMaterialPantone> kfMaterialPantones = fabricsInfo.getPantoneIds();
        List<KFMaterialPosition> kfMaterialPositions = fabricsInfo.getPositionIds();
        String patoneIds = MaterialPantoneServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPantones); //颜色用/分割
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


        //复合或涂层列表
        String blcId = chgBlcId(fabricsInfo, stringBuilder);

        //面料功能
        chgFunction(fabricsInfo, stringBuilder);

        //复合或者贴膜
        if (WebConstants.BLC_TYPE_FUHE.equals(blcId) || WebConstants.BLE_TYPE_TIEMO.equals(blcId)) {
            //复合
            if (WebConstants.BLC_TYPE_FUHE.equals(blcId)) {

                //材质列表
                chgCompositeClassicId(fabricsInfo, stringBuilder);

                //品名列表
                chgCompositeProductTypeId(fabricsInfo, stringBuilder);


                //纱支密度列表
                chgCompositeSpecificationId(fabricsInfo, stringBuilder);


                //染色方式列表
                chgCompositeDyeId(fabricsInfo, stringBuilder);


                //防泼水列表
                chgCompositeWaterRepllentId(fabricsInfo, stringBuilder);


                //复合底面料功能
                chgCompositeFunction(fabricsInfo, stringBuilder);

            }


            //膜或涂层的材质列表
            chgMomcId(fabricsInfo, stringBuilder);


            //膜或涂层的颜色列表
            chgComocId(fabricsInfo, stringBuilder);

            chgMtId(fabricsInfo, stringBuilder);


            chgWoblcId(fabricsInfo, stringBuilder);


        }
        chgUnitId(fabricsInfo);



        fabricsInfo.setDescription(stringBuilder.toString());
    }

    private void chgCompositeFunction(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        StringBuilder stringBuilder2 = new StringBuilder();

        chgCompositeWaterProofId(fabricsInfo, stringBuilder2);

        chgCompositeWvpId(fabricsInfo, stringBuilder);

        chgCompositePermeabilityId(fabricsInfo, stringBuilder2);

        chgCompositeWaterpressureId(fabricsInfo, stringBuilder2);

        //防泼水列表
        chgCompositeWaterRepllentId(fabricsInfo, stringBuilder);

        chgCompositeUltravioletProtectionId(fabricsInfo, stringBuilder2);

        chgCompositeQuickDryId(fabricsInfo, stringBuilder2);

        chgCompositeOilProofId(fabricsInfo, stringBuilder2);

        chgCompositeAntMosquitosId(fabricsInfo, stringBuilder2);

        if (stringBuilder2.length() != 0) {
            stringBuilder.append("复合底面料功能:").append(stringBuilder2.toString());
        }

    }

    private void chgFunction(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        StringBuilder stringBuilder2 = new StringBuilder();

        chgWaterProofId(fabricsInfo, stringBuilder2);//水压

        //透湿
        chgWvpId(fabricsInfo, stringBuilder);


        chgPermeabilityId(fabricsInfo, stringBuilder2);//透气

        chgWaterpressureId(fabricsInfo, stringBuilder2);//接缝水压

        //防泼水列表
        chgWaterRepllentId(fabricsInfo, stringBuilder);

        chgUltravioletProtectionId(fabricsInfo, stringBuilder2);//抗紫外线

        chgQuickDryId(fabricsInfo, stringBuilder2);//快干

        chgOilProofId(fabricsInfo, stringBuilder2);//防油

        chgAntMosquitosId(fabricsInfo, stringBuilder2);//防蚊虫

        if (stringBuilder2.length() != 0) {
            stringBuilder.append("功能:").append(stringBuilder2.toString());
        }


    }

    /**
     * 接缝水压
     *
     * @param fabricsInfo
     * @param stringBuilder2
     */
    private void chgCompositeWaterpressureId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {
        String waterpressureId = fabricsInfo.getCompositeWaterpressureId();
        appendId(stringBuilder2, waterpressureId, WebConstants.JcSeamWaterpressureItems);
    }


    /**
     * 防泼水
     *
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgWaterRepllentId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String waterRepllentId = fabricsInfo.getWaterRepllentId();
        appendId(stringBuilder, waterRepllentId, WebConstants.WATERREPLLENTS);
    }


    /**
     * 防泼水
     *
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeWaterRepllentId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String compositeWaterRepllentId = fabricsInfo.getCompositeWaterRepllentId();
        appendId(stringBuilder, compositeWaterRepllentId, WebConstants.WATERREPLLENTS);
    }


    /**
     * 接缝水压
     *
     * @param fabricsInfo
     * @param stringBuilder2
     */
    private void chgWaterpressureId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {
        String waterpressureId = fabricsInfo.getWaterpressureId();
        appendId(stringBuilder2, waterpressureId, WebConstants.JcSeamWaterpressureItems);
    }

    /**
     * 防油
     */
    private void chgCompositeOilProofId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {

        String oilProofId = fabricsInfo.getCompositeOilProofId();
        appendId(stringBuilder2, oilProofId, WebConstants.JcOilProofItems);
    }

    /**
     * 防油
     */
    private void chgOilProofId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {

        String oilProofId = fabricsInfo.getOilProofId();
        appendId(stringBuilder2, oilProofId, WebConstants.JcOilProofItems);
    }

    /**
     * 防蚊虫
     */
    private void chgCompositeAntMosquitosId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {

        String antMosquitosId = fabricsInfo.getAntMosquitosId();
        appendId(stringBuilder2, antMosquitosId, WebConstants.JcAntMosquitosItems);
    }

    /**
     * 防蚊虫
     */
    private void chgAntMosquitosId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {

        String antMosquitosId = fabricsInfo.getCompositeAntMosquitosId();
        appendId(stringBuilder2, antMosquitosId, WebConstants.JcAntMosquitosItems);
    }


    /**
     * 快干
     */
    private void chgCompositeQuickDryId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {

        String quickDryId = fabricsInfo.getQuickDryId();
        appendId(stringBuilder2, quickDryId, WebConstants.JcQuickDryItems);
    }

    /**
     * 快干
     */
    private void chgQuickDryId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {

        String quickDryId = fabricsInfo.getCompositeQuickDryId();
        appendId(stringBuilder2, quickDryId, WebConstants.JcQuickDryItems);
    }

    /**
     * 抗紫外线
     */
    private void chgCompositeUltravioletProtectionId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {
        String ultravioletProtectionId = fabricsInfo.getCompositeWaterpressureId();
        appendId(stringBuilder2, ultravioletProtectionId, WebConstants.JcUltravioletProtectionItems);
    }

    /**
     * 接缝水压
     */
    private void chgUltravioletProtectionId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {
        String ultravioletProtectionId = fabricsInfo.getUltravioletProtectionId();
        appendId(stringBuilder2, ultravioletProtectionId, WebConstants.JcUltravioletProtectionItems);
    }

    /**
     * 透气
     */
    private void chgCompositePermeabilityId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {

        String permeabilityId = fabricsInfo.getCompositePermeabilityId();
        appendId(stringBuilder2, permeabilityId, WebConstants.AirPermeabilityItems);
    }

    /**
     * 透气
     */
    private void chgPermeabilityId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {

        String permeabilityId = fabricsInfo.getPermeabilityId();
        appendId(stringBuilder2, permeabilityId, WebConstants.AirPermeabilityItems);
    }

    /**
     * //透湿列表
     *
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeWvpId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        String wvpId = fabricsInfo.getWvpId();
        appendId(stringBuilder, wvpId, WebConstants.WVPITEMS);
    }


    /**
     * //透湿列表
     *
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgWvpId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String wvpId = fabricsInfo.getWvpId();
        appendId(stringBuilder, wvpId, WebConstants.WVPITEMS);
    }


    /**
     * 水压
     */
    private void chgCompositeWaterProofId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {
        String waterProofId = fabricsInfo.getCompositeWaterProofId();
        appendId(stringBuilder2, waterProofId, WebConstants.JcWaterProofItems);
    }


    /**
     * 水压
     */
    private void chgWaterProofId(FabricsInfo fabricsInfo, StringBuilder stringBuilder2) {
        String waterProofId = fabricsInfo.getWaterProofId();
        appendId(stringBuilder2, waterProofId, WebConstants.JcWaterProofItems);
    }


    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgClassicId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        String classicId = fabricsInfo.getClassicId();
        appendId(stringBuilder, classicId, WebConstants.FABRICCLASSICITEMS);
    }


    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgSpecificationId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String specificationId = fabricsInfo.getSpecificationId();
        if (StringUtils.isNotBlank(specificationId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.SPECFICATIONITEMS);
            specificationId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, specificationId);
            if (stringBuilder.length() == 0) {
                stringBuilder.append(specificationId);
            } else {
                stringBuilder.append(specificationId).append(CharConstant.COMMA);
            }

        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgDyeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String dyeId = fabricsInfo.getDyeId();
        appendId(stringBuilder, dyeId, WebConstants.DYEITEMS);
    }

//
//    private void appendId(StringBuilder stringBuilder2, String id, String items) {
//        if (StringUtils.isNotBlank(id)) {
//            List<SelectItem2> selectItem2s;
//            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(items);
//            id = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, id);
//            stringBuilder2.append(CharConstant.COMMA).append(id);
//        }
//    }


    private String appendId(StringBuilder stringBuilder, String id, String items) {
        if (StringUtils.isNotBlank(id)) {
            List<SelectItem2> selectItem2s;
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(items);
            id = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, id);
            stringBuilder.append(id).append(CharConstant.COMMA);
        }
        return id;
    }


    /**
     * @param fabricsInfo
     * @param stringBuilder
     * @return
     */
    public String chgBlcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String blcId = fabricsInfo.getBlcId();
        appendId(stringBuilder, blcId, WebConstants.BLCITEMS);
        return blcId;
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeClassicId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String compositeClassicId = fabricsInfo.getCompositeClassicId();
        appendId(stringBuilder, compositeClassicId, WebConstants.FABRICCLASSICITEMS);
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeProductTypeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String compositeProductTypeId = fabricsInfo.getCompositeProductTypeId();
//        if (StringUtils.isNotBlank(compositeProductTypeId)) {
//            List<SelectItem2> selectItem2s;
//            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.PRODUCTTYPEITEMS);
//            compositeProductTypeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeProductTypeId);
//            stringBuilder.append(CharConstant.COMMA).append(compositeProductTypeId);
//        }

        appendId(stringBuilder, compositeProductTypeId, WebConstants.PRODUCTTYPEITEMS);
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeSpecificationId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String compositeSpecificationId = fabricsInfo.getCompositeSpecificationId();
//        if (StringUtils.isNotBlank(compositeSpecificationId)) {
//            List<SelectItem2> selectItem2s;
//            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.SPECFICATIONITEMS);
//            compositeSpecificationId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeSpecificationId);
//            stringBuilder.append(CharConstant.COMMA).append(compositeSpecificationId);
//        }

        appendId(stringBuilder, compositeSpecificationId, WebConstants.SPECFICATIONITEMS);

    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeDyeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String compositeDyeId = fabricsInfo.getCompositeDyeId();
        appendId(stringBuilder, compositeDyeId, WebConstants.DYEITEMS);
    }


    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgMomcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String momcId = fabricsInfo.getMomcId();
        appendId(stringBuilder, momcId, WebConstants.MOMCITEMS);

    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgComocId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String comocId = fabricsInfo.getComocId();
        appendId(stringBuilder, comocId, WebConstants.COMOCITEMS);
    }


    /**
     * //膜的厚度列表
     *
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgMtId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String mtId = fabricsInfo.getMtId();

        appendId(stringBuilder, mtId, WebConstants.MTITEMS);
    }

    /**
     * // 贴膜或涂层工艺列表
     *
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgWoblcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {

        String woblcId = fabricsInfo.getWoblcId();
        appendId(stringBuilder, woblcId, WebConstants.WBLCITEMS);
    }

    /**
     * // 用量单位列表
     *
     * @param fabricsInfo
     */
    public void chgUnitId(FabricsInfo fabricsInfo) {

        String unitId = fabricsInfo.getUnitId();
        if (StringUtils.isNotBlank(unitId)) {
            List<SelectItem2> selectItem2s;
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.UNITITEMS);
            unitId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, unitId);
            fabricsInfo.setUnitId(unitId);
        }
    }

    /**
     * @param fabricsInfo
     */
    public void chgSpId(FabricsInfo fabricsInfo) {
        String spId = fabricsInfo.getSpId();
        String spName = SpInfoHelper.SINGLETONE.turnSpIdToName(spId);
        fabricsInfo.setSpId(spName);
    }

    /**
     * @param fabricsInfo
     */
    public void chgProductTypeId(FabricsInfo fabricsInfo) {
        List<SelectItem2> selectItem2s;
        String productTypeId = fabricsInfo.getProductTypeId();
        if (StringUtils.isNotBlank(productTypeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.PRODUCTTYPEITEMS);
            String productTypeName = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, productTypeId);
            fabricsInfo.setProductTypeId(productTypeName);
        }
    }

    /**
     * 所有面料成本
     *
     * @param fabricItems
     * @return
     */
    public BigDecimal caculateCostingFabric(List<FabricsJoinInfo> fabricItems) {
        BigDecimal bigDecimal = new BigDecimal(0);
        if (null != fabricItems) {
            for (FabricsJoinInfo joinInfo : fabricItems) {
                MaterialSpInfo materialSpInfo = joinInfo.getMaterialSpInfo();
                bigDecimal = bigDecimal.add(MaterialSpServiceHelper.SINGLETONE.caculateCosting(bigDecimal, materialSpInfo));
            }
        }
        return bigDecimal;
    }

    /**
     * 面料基础信息集合
     *
     * @param fabricItems
     * @return
     */
    private List<FabricsInfo> getFabrics(List<FabricsJoinInfo> fabricItems) {
        List<FabricsInfo> fabrics = new ArrayList<>();
        if (null != fabricItems) {
            //面料id存在，修改；面料id不存在则新增
            for (FabricsJoinInfo fabricsJoinInfo : fabricItems) {
                fabrics.add(fabricsJoinInfo.getFabricsInfo());
            }
        }
        return fabrics;
    }

    /**
     * @param seriesName
     * @param fabrics
     * @return
     * @throws CloneNotSupportedException
     */
    public FabricsInfo getMainFabricInfo(String seriesName, List<FabricsInfo> fabrics) {
        FabricsInfo info = new FabricsInfo();
        if (null != fabrics && !fabrics.isEmpty()) {
            for (FabricsInfo fabricsInfo : fabrics) {
                //新增或者修改判断是否为主线面料
                if (fabricsInfo.getIsShow() == WebConstants.IS_SHOW_FABRIC) {
                    try {
                        info = fabricsInfo.clone();
                    } catch (CloneNotSupportedException e) {
                        throw new BusinessException(ReturnCodeConstant.SYS_EXP);
                    }
                    translateIdToNameInFabric(seriesName, info);
                    break;
                }
            }
        }

        return info;
    }

}

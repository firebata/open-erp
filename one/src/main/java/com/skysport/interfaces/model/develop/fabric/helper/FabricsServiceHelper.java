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


        //防泼水列表
        chgFinishId(fabricsInfo, stringBuilder);

        //复合或涂层列表
        String blcId = chgBlcId(fabricsInfo, stringBuilder);

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
     * @param stringBuilder
     */
    public void chgClassicId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String classicId = fabricsInfo.getClassicId();
        if (StringUtils.isNotBlank(classicId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.FABRICCLASSICITEMS);
            classicId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, classicId);
            stringBuilder.append(classicId);
        }
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
//                    stringBuilder.append(CharConstant.COMMA).append(productTypeName);
            fabricsInfo.setProductTypeId(productTypeName);
        }
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
                stringBuilder.append(CharConstant.COMMA).append(specificationId);
            }

        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgDyeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String dyeId = fabricsInfo.getDyeId();
        if (StringUtils.isNotBlank(dyeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.DYEITEMS);
            dyeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, dyeId);
            stringBuilder.append(CharConstant.COMMA).append(dyeId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgFinishId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String finishId = fabricsInfo.getFinishId();
        if (StringUtils.isNotBlank(finishId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.FINISHITEMS);
            finishId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, finishId);
            stringBuilder.append(CharConstant.COMMA).append(finishId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     * @return
     */
    public String chgBlcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String blcId = fabricsInfo.getBlcId();
        if (StringUtils.isNotBlank(blcId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.BLCITEMS);
            blcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, blcId);
            stringBuilder.append(CharConstant.COMMA).append(blcId);
        }
        return blcId;
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeClassicId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeClassicId = fabricsInfo.getCompositeClassicId();
        if (StringUtils.isNotBlank(compositeClassicId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.FABRICCLASSICITEMS);
            compositeClassicId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeClassicId);
            stringBuilder.append(CharConstant.COMMA).append(compositeClassicId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeProductTypeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeProductTypeId = fabricsInfo.getCompositeProductTypeId();
        if (StringUtils.isNotBlank(compositeProductTypeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.PRODUCTTYPEITEMS);
            compositeProductTypeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeProductTypeId);
            stringBuilder.append(CharConstant.COMMA).append(compositeProductTypeId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeSpecificationId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeSpecificationId = fabricsInfo.getCompositeSpecificationId();
        if (StringUtils.isNotBlank(compositeSpecificationId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.SPECFICATIONITEMS);
            compositeSpecificationId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeSpecificationId);
            stringBuilder.append(CharConstant.COMMA).append(compositeSpecificationId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeDyeId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeDyeId = fabricsInfo.getCompositeDyeId();
        if (StringUtils.isNotBlank(compositeDyeId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.DYEITEMS);
            compositeDyeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeDyeId);
            stringBuilder.append(CharConstant.COMMA).append(compositeDyeId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgCompositeFinishId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String compositeFinishId = fabricsInfo.getCompositeFinishId();
        if (StringUtils.isNotBlank(compositeFinishId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.FINISHITEMS);
            compositeFinishId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeFinishId);
            stringBuilder.append(CharConstant.COMMA).append(compositeFinishId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgMomcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String momcId = fabricsInfo.getMomcId();
        if (StringUtils.isNotBlank(momcId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.MOMCITEMS);
            momcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, momcId);
            stringBuilder.append(CharConstant.COMMA).append(momcId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgComocId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;
        String comocId = fabricsInfo.getComocId();
        if (StringUtils.isNotBlank(comocId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.COMOCITEMS);
            comocId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, comocId);
            stringBuilder.append(CharConstant.COMMA).append(comocId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgWvpId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;//透湿列表
        String wvpId = fabricsInfo.getWvpId();
        if (StringUtils.isNotBlank(wvpId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.WVPITEMS);
            wvpId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, wvpId);
            stringBuilder.append(CharConstant.COMMA).append(wvpId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgMtId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;//膜的厚度列表
        String mtId = fabricsInfo.getMtId();
        if (StringUtils.isNotBlank(mtId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.MTITEMS);
            mtId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, mtId);
            stringBuilder.append(CharConstant.COMMA).append(mtId);
        }
    }

    /**
     * @param fabricsInfo
     * @param stringBuilder
     */
    public void chgWoblcId(FabricsInfo fabricsInfo, StringBuilder stringBuilder) {
        List<SelectItem2> selectItem2s;// 贴膜或涂层工艺列表
        String woblcId = fabricsInfo.getWoblcId();
        if (StringUtils.isNotBlank(woblcId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.WBLCITEMS);
            woblcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, woblcId);
            stringBuilder.append(CharConstant.COMMA).append(woblcId);
        }
    }

    /**
     * @param fabricsInfo
     */
    public void chgUnitId(FabricsInfo fabricsInfo) {
        List<SelectItem2> selectItem2s;// 用量单位列表
        String unitId = fabricsInfo.getUnitId();
        if (StringUtils.isNotBlank(unitId)) {
            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom(WebConstants.UNITITEMS);
            unitId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, unitId);
            fabricsInfo.setUnitId(unitId);
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

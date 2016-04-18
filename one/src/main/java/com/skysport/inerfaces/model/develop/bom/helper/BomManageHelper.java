package com.skysport.inerfaces.model.develop.bom.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.init.SkySportAppContext;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.bean.relation.BomMaterialIdVo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import com.skysport.inerfaces.model.develop.pantone.helper.KFMaterialPantoneServiceHelper;
import com.skysport.inerfaces.model.develop.position.helper.KFMaterialPositionServiceHelper;
import com.skysport.inerfaces.utils.BuildSeqNoHelper;
import com.skysport.inerfaces.utils.ExcelCreateHelper;
import com.skysport.inerfaces.utils.SeqCreateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiContext;
import org.jxls.transform.poi.PoiTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public class BomManageHelper extends ExcelCreateHelper {

    private static transient Logger logger = LoggerFactory.getLogger(BomManageHelper.class);

    private static BomManageHelper singletone = new BomManageHelper();

    private BomManageHelper() {
        super();
    }

    public static BomManageHelper getInstance() {
        return singletone;
    }

    /**
     * 自定义查询条件
     *
     * @param bomQueryForm
     * @param request
     */
    public static void buildBomQueryForm(BomQueryForm bomQueryForm, HttpServletRequest request) {
        bomQueryForm.getBomInfo().setProjectId(request.getParameter("projectId"));
        bomQueryForm.getBomInfo().setAreaId(request.getParameter("areaId"));
    }

    /**
     * @param intersection
     * @param sexColors
     * @param info
     * @param projectId
     * @param customerId
     * @param areaId
     * @param seriesId
     * @return
     */
    public List<BomInfo> getNeedAddBomList(List<BomInfo> intersection, List<SexColor> sexColors, ProjectBomInfo info, String projectId, String customerId, String areaId, String seriesId) {
        List<BomInfo> needAddBomList = new ArrayList<>();
        for (SexColor sexColor : sexColors) {
            String sexId = sexColor.getSexIdChild();
            String[] mainColors = sexColor.getMainColorNames().split(CharConstant.COMMA);
            for (String mainColor : mainColors) {
                BomInfo bomInfoTemp = null;
                if (intersection.isEmpty()) {//没有交集，数据库中子项目所有的bom
                    bomInfoTemp = createBomInfo(info, projectId, customerId, areaId, seriesId, sexId, mainColor);
                } else {
                    boolean isNeedAdd = true;
                    for (BomInfo bomInfo : intersection) {
                        //交集中的bom不需要新增
                        if (compareStyle(sexId, mainColor, bomInfo)) { //差集
                            isNeedAdd = false;
                            break;
                        }
                    }

                    if (isNeedAdd) {
                        bomInfoTemp = createBomInfo(info, projectId, customerId, areaId, seriesId, sexId, mainColor);
                    }
                }
                if (null != bomInfoTemp) {
                    needAddBomList.add(bomInfoTemp);
                }
            }
        }
        return needAddBomList;
    }

    /**
     * @param intersection
     * @param allStyles
     * @return
     */
    public List<BomInfo> getNeedDelBomList(List<BomInfo> intersection, List<BomInfo> allStyles) {
        List<BomInfo> needDelBomList = new ArrayList<>();

        if (intersection.isEmpty()) {//没有交集，删除数据库中子项目所有的bom
            needDelBomList = allStyles;
        } else {
            for (BomInfo dbBomInfo : allStyles) {
                boolean isNeedDel = true;
                for (BomInfo intersectionBomInfo : intersection) {
                    String sexId = dbBomInfo.getSexId();
                    String mainColor = dbBomInfo.getMainColor();
                    //交集中的bom不需要删除
                    if (compareStyle(sexId, mainColor, intersectionBomInfo)) {
                        isNeedDel = false;
                        break;
                    }
                }
                if (isNeedDel) {
                    needDelBomList.add(dbBomInfo);
                }
            }
        }


        return needDelBomList;
    }

    /**
     * @param sexColors
     * @param allStyles
     * @return bom交集
     */
    public List<BomInfo> getIntersection(List<SexColor> sexColors, List<BomInfo> allStyles) {
        List<BomInfo> intersection = new ArrayList<>();
        for (SexColor sexColor : sexColors) {
            String sexId = sexColor.getSexIdChild();
            String[] mainColors = sexColor.getMainColorNames().split(CharConstant.COMMA);
            for (String mainColor : mainColors) {
                for (BomInfo bominfo : allStyles) {
                    if ((sexId + mainColor).equals(bominfo.getSexId() + bominfo.getMainColor())) {//性别颜色相同则认为款式相同
                        intersection.add(bominfo);
                        break;
                    }
                }

            }
        }
        return intersection;
    }

    private static BomInfo createBomInfo(ProjectBomInfo info, String projectId, String customerId, String areaId, String seriesId, String sexId, String mainColor) {
        BomInfo bomInfo = new BomInfo();
        String kind_name = buildKindName(info);
        String seqNo = BuildSeqNoHelper.SINGLETONE.getFullSeqNo(kind_name, WebConstants.BOM_SEQ_NO_LENGTH);
        String categoryBId = info.getCategoryBid();
        List<SelectItem2> selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popProject("categoryBItems");
        categoryBId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, categoryBId);
        //性别属性
        selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popProject("sexItems");
        String sexName = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, sexId);
        String bomName = sexName + categoryBId;
//                //年份+客户+地域+系列+NNN
//                String bomId = kind_name + seqNo;
        String bomId = SeqCreateUtils.SINGLETONE.newBomSeq(sexId);
        bomInfo.setMainColor(mainColor);
        bomInfo.setSexId(sexId);
        bomInfo.setProjectId(projectId);
        bomInfo.setCustomerId(customerId);
        bomInfo.setAreaId(areaId);
        bomInfo.setSeriesId(seriesId);
        bomInfo.setCollectionNum(seqNo);//款式
        bomInfo.setBomId(bomId);
//                    bomInfo.setOfferAmount();
        bomInfo.setNatrualkey(bomId);
        bomInfo.setBomName(bomName);
        bomInfo.setName(bomName);
        return bomInfo;
    }


    /**
     * 比较颜色和性别
     *
     * @param sexId
     * @param mainColor
     * @param bomInfo
     * @return
     */
    private static boolean compareStyle(String sexId, String mainColor, BomInfo bomInfo) {
        return mainColor.equals(bomInfo.getMainColor()) && sexId.equals(bomInfo.getSexId());
    }

    public static String buildKindName(ProjectBomInfo info) {
        return buildKindName(info.getNatrualkey(), info.getCategoryAid(), info.getCategoryBid());
    }

    public static String buildKindName(String projectId, String categoryAid, String categoryBid) {
        return new StringBuilder().append(projectId).append(categoryAid).append(categoryBid).toString();
    }

    public static void turnSexIdToName(List<BomInfo> infos) {
        if (null != infos && !infos.isEmpty()) {
            for (BomInfo bomInfo : infos) {
                bomInfo.setSexId(getSexName(bomInfo));
            }
        }

    }

    private static String getSexName(BomInfo bomInfo) {
        List<SelectItem2> items = SystemBaseInfoCachedMap.SINGLETONE.popProject("sexItems");
        String id = bomInfo.getSexId();
        return SystemBaseInfoCachedMap.SINGLETONE.getName(items, id);
    }

    /**
     * 构造bom详细信息
     *
     * @param fabricsInfos
     * @param accessoriesInfos
     * @param packagings
     * @return
     */
    public static BomInfoDetail buildBomInfoDetail(List<FabricsInfo> fabricsInfos, List<AccessoriesInfo> accessoriesInfos, List<KFPackaging> packagings) {
        BomInfoDetail bomInfoDetail = new BomInfoDetail();
        bomInfoDetail.setPackagings(packagings);
        bomInfoDetail.setFabricsInfos(fabricsInfos);
        bomInfoDetail.setAccessoriesInfos(accessoriesInfos);
        return bomInfoDetail;
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
                fabricsInfo.setSeriesName(seriesName);
                //设置颜色位置
                List<KFMaterialPantone> kfMaterialPantones = fabricsInfo.getPantoneIds();
                List<KFMaterialPosition> kfMaterialPositions = fabricsInfo.getPositionIds();
                String patoneIds = KFMaterialPantoneServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPantones); //颜色用/分割
                String positionIds = KFMaterialPositionServiceHelper.SINGLETONE.turnIdsToNames(kfMaterialPositions);//位置用/分割


                fabricsInfo.setPantoneId(patoneIds);
                fabricsInfo.setPositionId(positionIds);


                List<SelectItem2> selectItem2s;
                StringBuilder stringBuilder = new StringBuilder();

                String spId = fabricsInfo.getSpId();
                selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("spItems");
                spId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, spId);
                fabricsInfo.setSpId(spId);


                //材质列表
                String classicId = fabricsInfo.getClassicId();
                if (StringUtils.isNotBlank(classicId)) {
                    selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("fabricClassicItems");
                    classicId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, classicId);
                    stringBuilder.append(classicId);
                }

//              fabricsInfo.setClassicId(classicId);

                //品名列表
                String productTypeId = fabricsInfo.getProductTypeId();
                if (StringUtils.isNotBlank(productTypeId)) {
                    selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("productTypeItems");
                    String productTypeName = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, productTypeId);
//                    stringBuilder.append(CharConstant.COMMA).append(productTypeName);
                    fabricsInfo.setProductTypeId(productTypeName);
                }


                //纱支密度列表
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

//              fabricsInfo.setSpecificationId(specificationId);


                //染色方式列表
                String dyeId = fabricsInfo.getDyeId();
                if (StringUtils.isNotBlank(dyeId)) {
                    selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("dyeItems");
                    dyeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, dyeId);
                    stringBuilder.append(CharConstant.COMMA).append(dyeId);
                }

//              fabricsInfo.setDyeId(dyeId);

                //后整理列表
                String finishId = fabricsInfo.getFinishId();
                if (StringUtils.isNotBlank(finishId)) {
                    selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("finishItems");
                    finishId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, finishId);
                    stringBuilder.append(CharConstant.COMMA).append(finishId);
                }
//              fabricsInfo.setFinishId(finishId);

                //复合或涂层列表
                String blcId = fabricsInfo.getBlcId();
                if (StringUtils.isNotBlank(blcId)) {
                    selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("blcItems");
                    blcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, blcId);
                    stringBuilder.append(CharConstant.COMMA).append(blcId);
                }
//                fabricsInfo.setBlcId(SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, blcId));

                //复合或者贴膜
                if (blcId.equals(SkySportAppContext.blc_type_fuhe) || blcId.equals(SkySportAppContext.ble_type_tiemo)) {
                    //复合
                    if (blcId.equals(SkySportAppContext.blc_type_fuhe)) {
                        //材质列表
                        String compositeClassicId = fabricsInfo.getCompositeClassicId();
                        if (StringUtils.isNotBlank(compositeClassicId)) {
                            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("fabricClassicItems");
                            compositeClassicId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeClassicId);
                            stringBuilder.append(CharConstant.COMMA).append(compositeClassicId);
                        }

//                fabricsInfo.setCompositeClassicId(compositeClassicId);

                        //品名列表
                        String compositeProductTypeId = fabricsInfo.getCompositeProductTypeId();
                        if (StringUtils.isNotBlank(compositeProductTypeId)) {
                            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("productTypeItems");
                            compositeProductTypeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeProductTypeId);
                            stringBuilder.append(CharConstant.COMMA).append(compositeProductTypeId);
                        }

//                fabricsInfo.setCompositeProductTypeId(compositeProductTypeId);

                        //纱支密度列表
                        String compositeSpecificationId = fabricsInfo.getCompositeSpecificationId();
                        if (StringUtils.isNotBlank(compositeSpecificationId)) {
                            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("specficationItems");
                            compositeSpecificationId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeSpecificationId);
                            stringBuilder.append(CharConstant.COMMA).append(compositeSpecificationId);
                        }

//                fabricsInfo.setCompositeSpecificationId(compositeSpecificationId);


                        //染色方式列表
                        String compositeDyeId = fabricsInfo.getCompositeDyeId();
                        if (StringUtils.isNotBlank(compositeDyeId)) {
                            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("dyeItems");
                            compositeDyeId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeDyeId);
                            stringBuilder.append(CharConstant.COMMA).append(compositeDyeId);
                        }

//                fabricsInfo.setCompositeDyeId(compositeDyeId);

                        //后整理列表
                        String compositeFinishId = fabricsInfo.getCompositeFinishId();
                        if (StringUtils.isNotBlank(compositeFinishId)) {
                            selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("finishItems");
                            compositeFinishId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, compositeFinishId);
                            stringBuilder.append(CharConstant.COMMA).append(compositeFinishId);
                        }

//                fabricsInfo.setCompositeFinishId(compositeFinishId);
                    }


                    //膜或涂层的材质列表
                    String momcId = fabricsInfo.getMomcId();
                    if (StringUtils.isNotBlank(momcId)) {
                        selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("momcItems");
                        momcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, momcId);
                        stringBuilder.append(CharConstant.COMMA).append(momcId);
                    }

//                fabricsInfo.setMomcId(momcId);


                    //膜或涂层的颜色列表
                    String comocId = fabricsInfo.getComocId();
                    if (StringUtils.isNotBlank(comocId)) {
                        selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("comocItems");
                        comocId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, comocId);
                        stringBuilder.append(CharConstant.COMMA).append(comocId);
                    }

//                fabricsInfo.setComocId(comocId);

                    //透湿程度列表
                    String wvpId = fabricsInfo.getWvpId();
                    if (StringUtils.isNotBlank(wvpId)) {
                        selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("wvpItems");
                        wvpId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, wvpId);
                        stringBuilder.append(CharConstant.COMMA).append(wvpId);
                    }

//                fabricsInfo.setWvpId(wvpId);

                    //膜的厚度列表
                    String mtId = fabricsInfo.getMtId();
                    if (StringUtils.isNotBlank(mtId)) {
                        selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("mtItems");
                        mtId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, mtId);
                        stringBuilder.append(CharConstant.COMMA).append(mtId);
                    }

//                fabricsInfo.setMtId(mtId);

                    // 贴膜或涂层工艺列表
                    String woblcId = fabricsInfo.getWoblcId();
                    if (StringUtils.isNotBlank(woblcId)) {
                        selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("wblcItems");
                        woblcId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, woblcId);
                        stringBuilder.append(CharConstant.COMMA).append(woblcId);
                    }

//                fabricsInfo.setWoblcId(woblcId);

                }


                // 用量单位列表
                String unitId = fabricsInfo.getUnitId();
                if (StringUtils.isNotBlank(unitId)) {
                    selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("unitItems");
                    unitId = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, unitId);
                    fabricsInfo.setUnitId(unitId);
                }

                fabricsInfo.setDescription(stringBuilder.toString());


            }
        }
    }


    /**
     * 将辅料信息中的id转换成名称
     *
     * @param accessoriesInfos
     */
    public static void translateIdToNameInAccessoriesInfos(List<AccessoriesInfo> accessoriesInfos, String seriesName) {

        if (null != accessoriesInfos && !accessoriesInfos.isEmpty()) {
            for (AccessoriesInfo accessoriesInfo : accessoriesInfos) {
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
    }


    /**
     * 将包装材料的id转换成名称
     *
     * @param packagings
     */
    public static void translateIdToNameInPackagings(List<KFPackaging> packagings, String seriesName) {
        if (null != packagings && !packagings.isEmpty()) {
            for (KFPackaging packaging : packagings) {

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
    }


    /**
     * 创建文件
     *
     * @param fileName
     * @param ctxPath
     * @param bomInfoDetails
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void createFile(String fileName, String ctxPath, List<BomInfoDetail> bomInfoDetails) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Workbook workbook = new HSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet();

        Font font = workbook.createFont();
        font.setFontName("仿宋_GB2312");
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
        font.setFontHeightInPoints((short) 12);
        CellStyle style = workbook.createCellStyle();
        //设置颜色
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);//前景颜色
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充方式，前色填充

        //边框填充
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setFont(font);
        style.setWrapText(false);
        DataFormat dataFormat = workbook.createDataFormat();
        style.setDataFormat(dataFormat.getFormat("@"));

        //表头
        createExcelTitle(sheet, createHelper, style, WebConstants.BOM_DETAIL_TITILE_ADVANCED);

        //总记录数
        int count = 0;
        for (BomInfoDetail bomInfoDetail : bomInfoDetails) {
            //面料
            List<FabricsInfo> fabricsInfos = bomInfoDetail.getFabricsInfos();
            //辅料
            List<AccessoriesInfo> accessoriesInfos = bomInfoDetail.getAccessoriesInfos();
            //包材
            List<KFPackaging> packagings = bomInfoDetail.getPackagings();
            count = createCellValue(createHelper, sheet, style, fabricsInfos, count);
            count = createCellValue(createHelper, sheet, style, accessoriesInfos, count);
            count = createCellValue(createHelper, sheet, style, packagings, count);
        }


        if (workbook instanceof XSSFWorkbook) {
            fileName = fileName + "x";
        }

        fireCreate(fileName, ctxPath, workbook);


    }

    /**
     * 导出每个bom中每个成衣工厂的生产指示单
     *
     * @param bomInfo
     * @param response
     * @param request
     */
    public static void downloadProductinstruction(BomInfo bomInfo, HttpServletResponse response, HttpServletRequest request) throws IOException, InvalidFormatException {
        org.springframework.core.io.Resource fileRource = new ClassPathResource("conf/pi-20160316.xlsx");
        String seriesName = bomInfo.getSeriesName();
        //面料集合
        List<FabricsInfo> fabricItems = bomInfo.getFabrics();
        //将id转成name
        BomManageHelper.translateIdToNameInFabrics(fabricItems, seriesName, WebConstants.FABRIC_ID_EXCHANGE_BOM);
        //辅料集合
        List<AccessoriesInfo> accessories = bomInfo.getAccessories();
        BomManageHelper.translateIdToNameInAccessoriesInfos(accessories, seriesName);
        //包材
        List<KFPackaging> packagings = bomInfo.getPackagings();
        BomManageHelper.translateIdToNameInPackagings(packagings, seriesName);
        //成衣厂 & 生产指示单
        List<FactoryQuoteInfo> factoryQuoteInfos = bomInfo.getFactoryQuoteInfos();

        String bomId = bomInfo.getNatrualkey() == null ? bomInfo.getBomId() : bomInfo.getNatrualkey();
        if (null == factoryQuoteInfos || factoryQuoteInfos.isEmpty()) {
            logger.info("==============================================>工厂报价信息 = null or 工厂报价信息 isEmpty ");
            return;
        }
        /**
         * 下单日期（导出时间）
         */
        String exportDate = DateUtils.SINGLETONE.getYyyy_Mm_dd();
        for (FactoryQuoteInfo factoryQuoteInfo : factoryQuoteInfos) {
            String factoryQuoteId = factoryQuoteInfo.getFactoryQuoteId();
            //指示单信息
            KfProductionInstructionEntity productionInstruction = factoryQuoteInfo.getProductionInstruction();
            if (productionInstruction == null) {
                logger.info("==============================================>bomid[{0}] 的成衣厂factoryQuoteId[{1}]对应的指示单信息为空 ", new Object[]{bomId, factoryQuoteId});
                continue;
            }
            productionInstruction.setFabrics(fabricItems);
            productionInstruction.setAccessories(accessories);
            productionInstruction.setPackagings(packagings);
            productionInstruction.setExportDate(exportDate);

            InputStream is = fileRource.getInputStream();
            String year = DateUtils.SINGLETONE.getYyyy();
            String ctxPath = new StringBuilder().append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.BASE_PATH)).append(WebConstants.FILE_SEPRITER).append(year).append(WebConstants.FILE_SEPRITER)
                    .append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.DEVELOP_PATH)).toString();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(DateUtils.SINGLETONE.getYyyyMmdd());
            stringBuilder.append(CharConstant.HORIZONTAL_LINE).append(WebConstants.BOM_PI_CN_NAME);
            stringBuilder.append(CharConstant.HORIZONTAL_LINE).append(productionInstruction.getProjectItemName());
            stringBuilder.append(CharConstant.HORIZONTAL_LINE).append(productionInstruction.getColorName());
            stringBuilder.append(CharConstant.HORIZONTAL_LINE).append(bomInfo.getName());
            stringBuilder.append(WebConstants.SUFFIX_EXCEL_XLSX);
            String fileName = stringBuilder.toString();

            //完整文件路径
            String downLoadPath = ctxPath + File.separator + fileName;
            //生成生成指示单
            OutputStream os = new FileOutputStream(downLoadPath);
            Transformer transformer = PoiTransformer.createTransformer(is, os);
            AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
            List<Area> xlsAreaList = areaBuilder.build();
            Area xlsArea = xlsAreaList.get(0);
            Context context = new PoiContext();
            context.putVar("productionInstruction", productionInstruction);
//            xlsArea.applyAt(new CellRef("Sheet2!A1"), context);
            xlsArea.applyAt(new CellRef("Sheet1!A1"), context);
            xlsArea.processFormulas();
            transformer.write();

            //下载
            UpDownUtils.download(request, response, fileName, downLoadPath);

        }

    }

    public BomInfo getProjectBomInfo(HttpServletRequest request) {
        BomInfo bomInfo = new BomInfo();
        return bomInfo;
    }

    public List<BomMaterialIdVo> getBomMaterialIdVoInFabricsInfo(List<FabricsInfo> fabrics, String bomId) {
        List<BomMaterialIdVo> bomIdVos = new ArrayList<>();
        for (FabricsInfo fabric : fabrics) {
            BomMaterialIdVo vo = new BomMaterialIdVo();
            String materialId = fabric.getNatrualkey();
            vo.setBomId(bomId);
            vo.setMaterialId(materialId);
            bomIdVos.add(vo);
        }
        return bomIdVos;
    }

    public List<BomMaterialIdVo> getBomMaterialIdVoInAccessoriesInfo(List<AccessoriesInfo> accessories, String bomId) {
        List<BomMaterialIdVo> bomIdVos = new ArrayList<>();
        for (AccessoriesInfo accessorie : accessories) {
            BomMaterialIdVo vo = new BomMaterialIdVo();
            String materialId = accessorie.getNatrualkey();
            vo.setBomId(bomId);
            vo.setMaterialId(materialId);
            bomIdVos.add(vo);
        }
        return bomIdVos;
    }

    public List<BomMaterialIdVo> getBomMaterialIdVoInKFPackaging(List<KFPackaging> packagings, String bomId) {
        List<BomMaterialIdVo> bomIdVos = new ArrayList<>();
        for (KFPackaging packaging : packagings) {
            BomMaterialIdVo vo = new BomMaterialIdVo();
            String materialId = packaging.getNatrualkey();
            vo.setBomId(bomId);
            vo.setMaterialId(materialId);
            bomIdVos.add(vo);
        }
        return bomIdVos;
    }

    public List<BomMaterialIdVo> getBomMaterialIdVoInFactoryQuoteInfo(List<FactoryQuoteInfo> factoryQuoteInfos, String bomId) {
        List<BomMaterialIdVo> bomIdVos = new ArrayList<>();
        for (FactoryQuoteInfo factoryQuoteInfo : factoryQuoteInfos) {
            BomMaterialIdVo vo = new BomMaterialIdVo();
            String materialId = factoryQuoteInfo.getFactoryQuoteId();
            vo.setBomId(bomId);
            vo.setMaterialId(materialId);
            bomIdVos.add(vo);
        }
        return bomIdVos;
    }

    public List<BomMaterialIdVo> getBomMaterialIdVoInKfProductionInstructionEntity(List<KfProductionInstructionEntity> productionInstructionEntities, String bomId) {
        List<BomMaterialIdVo> bomIdVos = new ArrayList<>();
        for (KfProductionInstructionEntity entity : productionInstructionEntities) {
            BomMaterialIdVo vo = new BomMaterialIdVo();
            String materialId = entity.getProductionInstructionId();
            vo.setBomId(bomId);
            vo.setMaterialId(materialId);
            bomIdVos.add(vo);
        }
        return bomIdVos;
    }

    public List<KfProductionInstructionEntity> buildProductInstruction(List<FactoryQuoteInfo> factoryQuoteInfos) {
        List<KfProductionInstructionEntity> list = new ArrayList<>();
        for (FactoryQuoteInfo info : factoryQuoteInfos) {
            KfProductionInstructionEntity entity = info.getProductionInstruction();
            list.add(entity);
        }
        return list;
    }

//    public List<BomMaterialIdVo> getBomMaterialIdVoInQuotedInfo(QuotedInfo quotedInfo, String bomId) {
//        List<BomMaterialIdVo> bomIdVos = new ArrayList<>();
//        BomMaterialIdVo vo = new BomMaterialIdVo();
//        String materialId = quotedInfo;
//        vo.setBomId(bomId);
//        vo.setMaterialId(materialId);
//        bomIdVos.add(vo);
//        return bomIdVos;
//    }
}

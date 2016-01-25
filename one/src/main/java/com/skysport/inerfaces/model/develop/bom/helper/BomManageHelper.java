package com.skysport.inerfaces.model.develop.bom.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.core.utils.SeqCreateUtils;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.utils.BuildSeqNoHelper;
import com.skysport.inerfaces.utils.ExcelCreateHelper;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public class BomManageHelper extends ExcelCreateHelper {
    static Logger logger = Logger.getLogger(BomManageHelper.class);

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
     * 自动生成Bom信息，并保存DB
     *
     * @param bomManageService BomManageService
     * @param info             项目信息
     */
    public static void autoCreateBomInfoAndSave(IBomManageService bomManageService, IncrementNumber incrementNumber, ProjectBomInfo info) {

        List<SexColor> sexColors = info.getSexColors();

        String projectId = info.getNatrualkey();
        String customerId = info.getCustomerId();
        String areaId = info.getAreaId();
        String seriesId = info.getSeriesId();
//        String categoryAid = info.getCategoryAid();
//        String categoryBid = info.getCategoryBid();

        List<BomInfo> bomInfos = new ArrayList();
        List<BomInfo> needUpdateBomInfos = new ArrayList();
        List<BomInfo> allStyles = bomManageService.selectAllBomSexAndMainColor(projectId.trim());


        for (SexColor sexColor : sexColors) {

            String sexId = sexColor.getSexIdChild();
            String[] mainColors = sexColor.getMainColorNames().split(CharConstant.COMMA);
//            String[] sexIds = info.getSexIds().split(CharConstant.COMMA);

            //删除bom
            delBom(allStyles, mainColors, sexId, bomManageService, projectId);

            for (String mainColor : mainColors) {

                BomInfo bomInfo = new BomInfo();

                String kind_name = buildKindName(info);

                String seqNo = BuildSeqNoHelper.SINGLETONE.getFullSeqNo(kind_name, incrementNumber, WebConstants.BOM_SEQ_NO_LENGTH);

                String categoryBId = info.getCategoryBid();

                List<SelectItem2> selectItem2s = SystemBaseInfo.SINGLETONE.popProject("categoryBItems");
                categoryBId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, categoryBId);

                //性别属性
                selectItem2s = SystemBaseInfo.SINGLETONE.popProject("sexItems");
                String sexName = SystemBaseInfo.SINGLETONE.getName(selectItem2s, sexId);

                String bomName = sexName + categoryBId;

//                //年份+客户+地域+系列+NNN
//                String bomId = kind_name + seqNo;

                String bomId = SeqCreateUtils.newBomSeq();
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

                String styleId = sexId + mainColor;

                if (allStyles.contains(styleId)) {
                    needUpdateBomInfos.add(bomInfo);
                } else {
                    bomInfos.add(bomInfo);
                }



            }
        }


        //保存bom
        if (!bomInfos.isEmpty()) {
            bomManageService.addBatch(bomInfos);
        }
        //更新bom
        bomManageService.updateBatch(needUpdateBomInfos);

    }

    /**
     * 删除BOM
     *
     * @param allStyles
     * @param mainColors
     * @param sexId
     * @param bomManageService
     * @param projectId
     */
    private static void delBom(List<BomInfo> allStyles, String[] mainColors, String sexId, IBomManageService bomManageService, String projectId) {

        if (null == allStyles || allStyles.isEmpty()) {
            return;
        }


        List<String> tempStyles = new ArrayList();
        for (String mainColor : mainColors) {
            for (BomInfo bomInfo : allStyles) {
                if (mainColor.equals(bomInfo.getMainColor()) && sexId.equals(bomInfo.getSexId())) {
                    tempStyles.add(bomInfo.getNatrualkey().trim());
                }
            }
        }

        if (tempStyles.isEmpty()) {
            bomManageService.delByProjectId(projectId); //删除子项目的所有的bom
        } else {
            bomManageService.delBomNotInThisIds(tempStyles);
        }


    }

    public static String buildKindName(ProjectBomInfo info) {
        return buildKindName(info.getNatrualkey(), info.getCategoryAid(), info.getCategoryBid());
    }

    public static String buildKindName(String projectId, String categoryAid, String categoryBid) {
        return new StringBuilder().append(projectId).append(categoryAid).append(categoryBid).toString();
    }

    public static void turnIdToName(List<BomInfo> infos) {
        if (null != infos && !infos.isEmpty()) {
            for (BomInfo bomInfo : infos) {
                bomInfo.setSexId(getSexName(bomInfo));
            }
        }

    }

    private static String getSexName(BomInfo bomInfo) {
        List<SelectItem2> items = SystemBaseInfo.SINGLETONE.popProject("sexItems");
        String id = bomInfo.getSexId();
        return SystemBaseInfo.SINGLETONE.getName(items, id);
    }

    /**
     * @param fabricsInfos     List<FabricsInfo>
     * @param accessoriesInfos List<AccessoriesInfo>
     * @param packagings       List<KFPackaging>
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
     * @param fabricsInfos
     */
    public static void translateIdToNameInFabrics(List<FabricsInfo> fabricsInfos, int exchange_type) {

        if (null != fabricsInfos && !fabricsInfos.isEmpty()) {
            for (FabricsInfo fabricsInfo : fabricsInfos) {

                if (exchange_type == WebConstants.FABRIC_ID_EXCHANGE_QUOTED) {
                    if (fabricsInfo.getIsShow() == WebConstants.IS_NOT_SHOW_FABRIC) {
                        continue;
                    }
                }

                String spId = fabricsInfo.getSpId();
                List<SelectItem2> selectItem2s = SystemBaseInfo.SINGLETONE.popBom("spItems");
                spId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, spId);
                fabricsInfo.setSpId(spId);

                //材料类别
//                String materialTypeId = fabricsInfo.getMaterialTypeId();
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("materialTypeItems");
//                materialTypeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, materialTypeId);
//                fabricsInfo.setMaterialTypeId(materialTypeId);
//                //年份
//                String
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("yearItems");
//                yearCode = SystemBaseInfo.SINGLETONE.getName(selectItem2s, yearCode);


                //材质列表
                String classicId = fabricsInfo.getClassicId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("fabricClassicItems");
                classicId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, classicId);
//              fabricsInfo.setClassicId(classicId);

                //品名列表
                String productTypeId = fabricsInfo.getProductTypeId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("productTypeItems");
                productTypeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, productTypeId);
                fabricsInfo.setProductTypeId(productTypeId);

                //纱支密度列表
                String specificationId = fabricsInfo.getSpecificationId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("specficationItems");
                specificationId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, specificationId);
//              fabricsInfo.setSpecificationId(specificationId);


                //染色方式列表
                String dyeId = fabricsInfo.getDyeId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("dyeItems");
                dyeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, dyeId);
//              fabricsInfo.setDyeId(dyeId);

                //后整理列表
                String finishId = fabricsInfo.getFinishId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("finishItems");
                finishId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, finishId);
//              fabricsInfo.setFinishId(finishId);


                //材质列表
                String compositeClassicId = fabricsInfo.getCompositeClassicId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("fabricClassicItems");
                compositeClassicId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, compositeClassicId);
//                fabricsInfo.setCompositeClassicId(compositeClassicId);

                //品名列表
                String compositeProductTypeId = fabricsInfo.getCompositeProductTypeId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("productTypeItems");
                compositeProductTypeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, compositeProductTypeId);
//                fabricsInfo.setCompositeProductTypeId(compositeProductTypeId);

                //纱支密度列表
                String compositeSpecificationId = fabricsInfo.getCompositeSpecificationId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("specficationItems");
                compositeSpecificationId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, compositeSpecificationId);
//                fabricsInfo.setCompositeSpecificationId(compositeSpecificationId);


                //染色方式列表
                String compositeDyeId = fabricsInfo.getCompositeDyeId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("dyeItems");
                compositeDyeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, compositeDyeId);
//                fabricsInfo.setCompositeDyeId(compositeDyeId);

                //后整理列表
                String compositeFinishId = fabricsInfo.getCompositeFinishId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("finishItems");
                compositeFinishId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, compositeFinishId);
//                fabricsInfo.setCompositeFinishId(compositeFinishId);


                //膜或涂层的材质列表
                String momcId = fabricsInfo.getMomcId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("momcItems");
                momcId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, momcId);
//                fabricsInfo.setMomcId(momcId);


                //膜或涂层的颜色列表
                String comocId = fabricsInfo.getComocId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("comocItems");
                comocId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, comocId);
//                fabricsInfo.setComocId(comocId);

                //透湿程度列表
                String wvpId = fabricsInfo.getWvpId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("wvpItems");
                wvpId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, wvpId);
//                fabricsInfo.setWvpId(wvpId);

                //膜的厚度列表
                String mtId = fabricsInfo.getMtId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("mtItems");
                mtId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, mtId);
//                fabricsInfo.setMtId(mtId);

                // 贴膜或涂层工艺列表
                String woblcId = fabricsInfo.getWoblcId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("wblcItems");
                woblcId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, woblcId);
//                fabricsInfo.setWoblcId(woblcId);

                //复合或涂层列表
                String blcId = fabricsInfo.getBlcId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("blcItems");
                blcId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, blcId);
//                fabricsInfo.setBlcId(SystemBaseInfo.SINGLETONE.getName(selectItem2s, blcId));

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(classicId).append(CharConstant.COMMA).append(productTypeId).append(CharConstant.COMMA).append(specificationId).append(CharConstant.COMMA).append(dyeId).append(CharConstant.COMMA).append(finishId).append(CharConstant.COMMA).append(blcId).append(CharConstant.COMMA);
                if (blcId.equals("A1")) {
                    stringBuilder.append("(").append(compositeClassicId).append(CharConstant.COMMA).append(compositeProductTypeId).append(CharConstant.COMMA)
                            .append(compositeSpecificationId).append(CharConstant.COMMA).append(compositeDyeId).append(CharConstant.COMMA).append(compositeFinishId).append(")");
                }
                stringBuilder.append(momcId).append(CharConstant.COMMA).append(comocId).append(CharConstant.COMMA).append(wvpId).append(CharConstant.COMMA).append(mtId).append(CharConstant.COMMA).append(woblcId);


//
//                //物料位置列表
//                List<KFMaterialPosition>  positionIds = fabricsInfo.getPositionIds();
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("positionItems");
//                positionIds = SystemBaseInfo.SINGLETONE.getName(selectItem2s, positionIds);

                // 用量单位列表
                String unitId = fabricsInfo.getUnitId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("unitItems");
                unitId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, unitId);
                fabricsInfo.setUnitId(unitId);

                fabricsInfo.setDescription(stringBuilder.toString());


            }
        }
    }


    public static void translateIdToNameInAccessoriesInfos(List<AccessoriesInfo> accessoriesInfos) {

        if (null != accessoriesInfos && !accessoriesInfos.isEmpty()) {
            for (AccessoriesInfo fabricsInfo : accessoriesInfos) {

                String spId = fabricsInfo.getSpId();
                List<SelectItem2> selectItem2s = SystemBaseInfo.SINGLETONE.popBom("spItems");
                spId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, spId);
                fabricsInfo.setSpId(spId);


                //材料类别
//                String materialTypeId = fabricsInfo.getMaterialTypeId();
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("materialTypeItems");
//                materialTypeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, materialTypeId);
//                fabricsInfo.setMaterialTypeId(materialTypeId);
//                //年份
//                String
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("yearItems");
//                yearCode = SystemBaseInfo.SINGLETONE.getName(selectItem2s, yearCode);


                //材质列表
                String classicId = fabricsInfo.getClassicId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("fabricClassicItems");
                classicId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, classicId);
//                fabricsInfo.setClassicId(classicId);

                //品名列表
                String productTypeId = fabricsInfo.getProductTypeId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("productTypeItems");
                productTypeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, productTypeId);
                fabricsInfo.setProductTypeId(productTypeId);

//                //纱支密度列表
//                String specificationId = fabricsInfo.getSpecificationId();
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("specficationItems");
//                specificationId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, specificationId);
//                fabricsInfo.setSpecificationId(specificationId);

                String teckRequired = fabricsInfo.getTechRequired();
                String length = fabricsInfo.getLength();
                String width = fabricsInfo.getWidth();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(classicId).append(CharConstant.COMMA).append(productTypeId).
                        append(CharConstant.COMMA).append(teckRequired).append(CharConstant.COMMA).append("长:").append(length).append(CharConstant.COMMA).append("宽:").append(width);

//
//                //物料位置列表
//                List<KFMaterialPosition>  positionIds = fabricsInfo.getPositionIds();
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("positionItems");
//                positionIds = SystemBaseInfo.SINGLETONE.getName(selectItem2s, positionIds);

                // 用量单位列表
                String unitId = fabricsInfo.getUnitId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("unitItems");
                unitId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, unitId);
                fabricsInfo.setUnitId(unitId);

                fabricsInfo.setDescription(stringBuilder.toString());


            }
        }
    }

    public static void translateIdToNameInPackagings(List<KFPackaging> packagings) {
        if (null != packagings && !packagings.isEmpty()) {
            for (KFPackaging fabricsInfo : packagings) {

                String spId = fabricsInfo.getSpId();
                List<SelectItem2> selectItem2s = SystemBaseInfo.SINGLETONE.popBom("spItems");
                spId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, spId);
                fabricsInfo.setSpId(spId);


                //材料类别
//                String materialTypeId = fabricsInfo.getMaterialTypeId();
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("materialTypeItems");
//                materialTypeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, materialTypeId);
//                fabricsInfo.setMaterialTypeId(materialTypeId);
//                //年份
//                String
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("yearItems");
//                yearCode = SystemBaseInfo.SINGLETONE.getName(selectItem2s, yearCode);


                //材质列表
                String classicId = fabricsInfo.getClassicId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("fabricClassicItems");
                classicId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, classicId);
//                fabricsInfo.setClassicId(classicId);

                //品名列表
                String productTypeId = fabricsInfo.getProductTypeId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("productTypeItems");
                productTypeId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, productTypeId);
                fabricsInfo.setProductTypeId(productTypeId);

                String teckRequired = fabricsInfo.getTechRequired();
                String length = fabricsInfo.getLength();
                String width = fabricsInfo.getWidth();

                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(classicId).append(CharConstant.COMMA).append(productTypeId).
                        append(CharConstant.COMMA).append(teckRequired).append(CharConstant.COMMA).append("长:").append(length).append(CharConstant.COMMA).append("宽:").append(width);
//
//                //物料位置列表
//                List<KFMaterialPosition>  positionIds = fabricsInfo.getPositionIds();
//                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("positionItems");
//                positionIds = SystemBaseInfo.SINGLETONE.getName(selectItem2s, positionIds);

                // 用量单位列表
                String unitId = fabricsInfo.getUnitId();
                selectItem2s = SystemBaseInfo.SINGLETONE.popBom("unitItems");
                unitId = SystemBaseInfo.SINGLETONE.getName(selectItem2s, unitId);
                fabricsInfo.setUnitId(unitId);

                fabricsInfo.setDescription(stringBuilder.toString());


            }
        }
    }


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
        createExcelTitle(sheet, createHelper, style, WebConstants.BOM_DETAIL_TITILE);

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


}

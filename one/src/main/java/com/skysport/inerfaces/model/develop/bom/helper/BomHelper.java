package com.skysport.inerfaces.model.develop.bom.helper;

import com.skysport.core.bean.system.SelectItem;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.ExcelCreateUtils;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.bean.relation.BomMaterialIdVo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.develop.accessories.helper.AccessoriesServiceHelper;
import com.skysport.inerfaces.model.develop.fabric.helper.FabricsServiceHelper;
import com.skysport.inerfaces.model.develop.packaging.helper.PackagingServiceHelper;
import com.skysport.inerfaces.utils.BuildSeqNoHelper;
import com.skysport.inerfaces.utils.SeqCreateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public class BomHelper/* extends ExcelCreateHelper */ {

    private static transient Logger logger = LoggerFactory.getLogger(BomHelper.class);

    private static BomHelper singletone = new BomHelper();

    private BomHelper() {
        super();
    }

    public static BomHelper getInstance() {
        return singletone;
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
            infos.forEach((bomInfo) -> bomInfo.setSexId(getSexName(bomInfo)));
//            for (BomInfo bomInfo : infos) {
//                bomInfo.setSexId(getSexName(bomInfo));
//            }
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
     * @param bomInfoDetails
     * @param fabricsInfos
     * @param accessoriesInfos
     * @param packagings
     * @return
     */
    public void buildBomInfoDetail(List<MaterialInfo> bomInfoDetails, List<FabricsInfo> fabricsInfos, List<AccessoriesInfo> accessoriesInfos, List<PackagingInfo> packagings) {

        fabricsInfos.forEach((fabricsInfo) -> bomInfoDetails.add(fabricsInfo));
        accessoriesInfos.forEach((fabricsInfo) -> bomInfoDetails.add(fabricsInfo));
        packagings.forEach((fabricsInfo) -> bomInfoDetails.add(fabricsInfo));

    }


//    /**
//     * 创建文件
//     *
//     * @param fileName
//     * @param ctxPath
//     * @param bomInfoDetails
//     * @throws NoSuchMethodException
//     * @throws InvocationTargetException
//     * @throws IllegalAccessException
//     */
//    public static void createFile(String fileName, String ctxPath, List<BomInfoDetail> bomInfoDetails) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//
//        Workbook workbook = new HSSFWorkbook();
//        CreationHelper createHelper = workbook.getCreationHelper();
//        Sheet sheet = workbook.createSheet();
//
//        Font font = workbook.createFont();
//        font.setFontName("仿宋_GB2312");
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
//        font.setFontHeightInPoints((short) 12);
//        CellStyle style = workbook.createCellStyle();
//        //设置颜色
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
////        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);//前景颜色
////        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//填充方式，前色填充
//
//        //边框填充
//        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
//        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
//        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
//        style.setFont(font);
//        style.setWrapText(false);
//        DataFormat dataFormat = workbook.createDataFormat();
//        style.setDataFormat(dataFormat.getFormat("@"));
//
//        //表头
//        createExcelTitle(sheet, createHelper, style, WebConstants.BOM_DETAIL_TITILE_ADVANCED);
//
//        //总记录数
//        int count = 0;
//        for (BomInfoDetail bomInfoDetail : bomInfoDetails) {
//            //面料
//            List<FabricsInfo> fabricsInfos = bomInfoDetail.getSeriesAnBomName();
//            //辅料
//            List<AccessoriesInfo> accessoriesInfos = bomInfoDetail.getAccessoriesInfos();
//            //包材
//            List<PackagingInfo> packagings = bomInfoDetail.getPackagings();
//            count = createCellValue(createHelper, sheet, style, fabricsInfos, count);
//            count = createCellValue(createHelper, sheet, style, accessoriesInfos, count);
//            count = createCellValue(createHelper, sheet, style, packagings, count);
//        }
//
//
//        if (workbook instanceof XSSFWorkbook) {
//            fileName = fileName + "x";
//        }
//
//        fireCreate(fileName, ctxPath, workbook);
//
//
//    }

    /**
     * 导出每个bom中每个成衣工厂的生产指示单
     *
     * @param bomInfo
     * @param response
     * @param request
     */
    public static void downloadProductinstruction(BomInfo bomInfo, HttpServletResponse response, HttpServletRequest request) throws IOException, InvalidFormatException {

        String seriesName = bomInfo.getSeriesName();

        //面料集合
        List<FabricsInfo> fabricItems = bomInfo.getFabrics();

        //将id转成name
        FabricsServiceHelper.SINGLETONE.translateIdToNameInFabrics(fabricItems, seriesName, WebConstants.FABRIC_ID_EXCHANGE_BOM);

        //辅料集合
        List<AccessoriesInfo> accessories = bomInfo.getAccessories();
        AccessoriesServiceHelper.SINGLETONE.translateIdToNameInAccessoriesInfos(accessories, seriesName);

        //包材
        List<PackagingInfo> packagings = bomInfo.getPackagings();
        PackagingServiceHelper.SINGLETONE.translateIdToNameInPackagings(packagings, seriesName);

        //成衣厂 & 生产指示单
//        List<FactoryQuoteInfo> factoryQuoteInfos = bomInfo.getFactoryQuoteInfos();
        KfProductionInstructionEntity productionInstruction = bomInfo.getProductionInstruction();
        String bomId = bomInfo.getNatrualkey() == null ? bomInfo.getBomId() : bomInfo.getNatrualkey();

        /**
         * 下单日期（导出时间）
         */
        String exportDate = DateUtils.SINGLETONE.getYyyy_Mm_dd();

        //指示单信息
        if (productionInstruction == null) {
            logger.info("==============================================>bomid[{0}] 的成衣厂productionInstructionId[{1}]对应的指示单信息为空 ", new Object[]{bomId, productionInstruction.getProductionInstructionId()});
        }
        productionInstruction.setFabrics(fabricItems);
        productionInstruction.setAccessories(accessories);
        productionInstruction.setPackagings(packagings);
        productionInstruction.setExportDate(exportDate);

        String year = DateUtils.SINGLETONE.getYyyy();


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DateUtils.SINGLETONE.getYyyyMmdd());
        stringBuilder.append(CharConstant.HORIZONTAL_LINE).append(WebConstants.BOM_PI_CN_NAME);
        stringBuilder.append(CharConstant.HORIZONTAL_LINE).append(productionInstruction.getProjectItemName());
        stringBuilder.append(CharConstant.HORIZONTAL_LINE).append(productionInstruction.getColorName());
        stringBuilder.append(CharConstant.HORIZONTAL_LINE).append(bomInfo.getName());
        stringBuilder.append(WebConstants.SUFFIX_EXCEL_XLSX);
        String fileName = stringBuilder.toString();

        //完整文件路径
        String ctxPath = new StringBuilder().append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.BASE_PATH)).append(WebConstants.FILE_SEPRITER).append(year).append(WebConstants.FILE_SEPRITER).append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.DEVELOP_PATH)).toString();
        String resourcePath = WebConstants.RESOURCE_PATH_PI;

        //创建文件
        String downLoadPath = ExcelCreateUtils.getInstance().create(productionInstruction, "productionInstruction", fileName, ctxPath, resourcePath);

        //下载
        UpDownUtils.download(request, response, fileName, downLoadPath);


    }


    /**
     * 获取bom和物料关系
     *
     * @param infos
     * @param bomId
     * @return
     */
    public List<BomMaterialIdVo> getBomMaterialIdVo(List<? extends SelectItem> infos, String bomId) {
        List<BomMaterialIdVo> bomIdVos = new ArrayList<>();
        for (SelectItem info : infos) {
            BomMaterialIdVo vo = new BomMaterialIdVo();
            String materialId = info.getNatrualkey();
            vo.setBomId(bomId);
            vo.setMaterialId(materialId);
            bomIdVos.add(vo);
        }
        return bomIdVos;
    }


    public List<BomMaterialIdVo> getBomMaterialIdVo(SelectItem entity, String bomId) {
        List<BomMaterialIdVo> bomIdVos = new ArrayList<>();
        BomMaterialIdVo vo = new BomMaterialIdVo();
        String materialId = entity.getNatrualkey();
        vo.setBomId(bomId);
        vo.setMaterialId(materialId);
        bomIdVos.add(vo);
        return bomIdVos;
    }


    public BomInfo getProjectBomInfo(HttpServletRequest request) {
        return new BomInfo();
    }
}

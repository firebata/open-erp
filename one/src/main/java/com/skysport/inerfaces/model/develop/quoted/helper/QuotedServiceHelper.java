package com.skysport.inerfaces.model.develop.quoted.helper;

import com.skysport.core.constant.CharConstant;
import com.skysport.inerfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.inerfaces.utils.ExcelCreateHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/17.
 */
public class QuotedServiceHelper extends ExcelCreateHelper {
    protected static transient Log logger = LogFactory.getLog(QuotedServiceHelper.class);
    private static QuotedServiceHelper quotedServiceHelper = new QuotedServiceHelper();

    public static QuotedServiceHelper getInstance() {
        return quotedServiceHelper;
    }

    /**
     * 创建报价表
     *
     * @param fileName
     * @param ctxPath
     * @param title       标题
     * @param quotedInfos
     */
    public static void createFile(String fileName, String ctxPath, String[] title, List<QuotedInfo> quotedInfos) throws IOException {

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

        createExcelTitle(sheet, createHelper, style, title);

        Row row;
        Cell cell;

        for (short rownum = (short) 1; rownum <= quotedInfos.size(); rownum++) {
            QuotedInfo quotedInfo = quotedInfos.get(rownum - 1);
            row = sheet.createRow(rownum);
            for (short cellnum = (short) 0; cellnum < title.length; cellnum++) {
                cell = row.createCell(cellnum);
                cell.setCellValue(createHelper.createRichTextString(getCellValue(cellnum, quotedInfo)));
                cell.setCellStyle(style);
                sheet.autoSizeColumn(cellnum, true);//设置自适应宽度
            }
        }


        if (workbook instanceof XSSFWorkbook) {
            fileName = fileName + "x";
        }

        fireCreate(fileName, ctxPath, workbook);

    }

    /**
     * 获取单元格内容
     *
     * @param cellnum
     * @param quotedInfo
     * @return
     */
    private static String getCellValue(short cellnum, QuotedInfo quotedInfo) {

        String cellValue;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now = simpleDateFormat.format(date);

        switch (cellnum) {
            case 0:
                cellValue = now;
                break;
            case 1:
                cellValue = quotedInfo.getProjectId();
                break;
            case 2:
                cellValue = quotedInfo.getProjectItemId();
                break;
            case 3:
                cellValue = quotedInfo.getBomId();
                break;
            case 4:
                cellValue = quotedInfo.getSpId();
                break;
            case 5:
                cellValue = quotedInfo.getMainFabricDescs();
                break;
            case 6:
                cellValue = quotedInfo.getQuotedPrice().toString();
                break;
            default:
                cellValue = CharConstant.EMPTY;
        }

        return cellValue;
    }


    public FactoryQuoteInfo getInfo(HttpServletRequest request) {
        FactoryQuoteInfo info = new FactoryQuoteInfo();
        return info;
    }
}

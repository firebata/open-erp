package com.skysport.inerfaces.utils;

import com.skysport.inerfaces.constant.WebConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/22.
 */
public class ExcelCreateHelper {
    protected static transient Log logger = LogFactory.getLog(ExcelCreateHelper.class);

    /**
     * 创建excel头
     *
     * @param sheet
     * @param createHelper
     * @param title
     */
    protected static void createExcelTitle(Sheet sheet, CreationHelper createHelper, CellStyle style, String[] title) {
        Row row;
        row = sheet.createRow(0);
        for (short cellnum = (short) 0; cellnum < title.length; cellnum++) {
            Cell cell = row.createCell(cellnum);
            cell.setCellValue(createHelper.createRichTextString(title[cellnum]));
            cell.setCellStyle(style);
            sheet.autoSizeColumn(cellnum, true);//设置自适应宽度
        }
    }


    /**
     * 生成表格内容
     *
     * @param createHelper
     * @param sheet
     * @param style
     * @param objects
     * @param startIndex
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static int createCellValue(CreationHelper createHelper, Sheet sheet, CellStyle style, List<?> objects, int startIndex) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int count = startIndex;
        if (null != objects && !objects.isEmpty()) {
            Row row;
            Cell cell;
            count = objects.size()+count;
            for (short rownum = (short) 1; rownum <= objects.size(); rownum++) {
                Object object = objects.get(rownum - 1);
                Class clazz = object.getClass();
                row = sheet.createRow(rownum + startIndex);
                for (short cellnum = (short) 0; cellnum < WebConstants.BOM_DETAIL_FIELD.length; cellnum++) {
                    logger.info("the method name is :" + "get" + WebConstants.BOM_DETAIL_FIELD[cellnum]);
                    Method m3 = clazz.getDeclaredMethod("get" + WebConstants.BOM_DETAIL_FIELD[cellnum]);
                    cell = row.createCell(cellnum);
                    cell.setCellValue(createHelper.createRichTextString((String) m3.invoke(object)));
                    cell.setCellStyle(style);
                    sheet.autoSizeColumn(cellnum, true);//设置自适应宽度
                }
            }
        }
        return count;
    }

    public static void fireCreate(String fileName, String ctxPath, Workbook workbook) {
        FileOutputStream out = null;
        try {
            File file = new File(ctxPath + File.separator + fileName);
            if (file.exists()) {
                File new_file = new File(ctxPath + File.separator + fileName + System.currentTimeMillis());
                file.renameTo(new_file);
            }
            file.delete();//先删除
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    logger.error("创建目标文件所在目录失败！");
                }
            }
            file.createNewFile();
            out = new FileOutputStream(file);
            workbook.write(out);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }
}

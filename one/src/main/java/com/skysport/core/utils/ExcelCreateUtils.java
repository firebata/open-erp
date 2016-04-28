package com.skysport.core.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.transform.Transformer;
import org.jxls.transform.poi.PoiContext;
import org.jxls.transform.poi.PoiTransformer;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/26.
 */
public class ExcelCreateUtils<T> {
    private static ExcelCreateUtils ourInstance = new ExcelCreateUtils();

    public static ExcelCreateUtils getInstance() {
        return ourInstance;
    }

    private ExcelCreateUtils() {
    }

    /**
     * 创建文件
     *
     * @param data           数据
     * @param dataName     key
     * @param fileName     文件名
     * @param ctxPath      文件存放路径
     * @param templatePath 模板路径
     * @return 新文件具体地址
     * @throws IOException            IOException
     * @throws InvalidFormatException InvalidFormatException
     */
    public String create(T data, String dataName, String fileName, String ctxPath, String templatePath) throws IOException, InvalidFormatException {
        String downLoadPath = ctxPath + File.separator + fileName;
        //生成生成指示单
        OutputStream os = new FileOutputStream(downLoadPath);
        org.springframework.core.io.Resource fileRource = new ClassPathResource(templatePath);
        InputStream is = fileRource.getInputStream();
        Transformer transformer = PoiTransformer.createTransformer(is, os);
        AreaBuilder areaBuilder = new XlsCommentAreaBuilder(transformer);
        List<Area> xlsAreaList = areaBuilder.build();
        Area xlsArea = xlsAreaList.get(0);
        Context context = new PoiContext();
        context.putVar(dataName, data);
//            xlsArea.applyAt(new CellRef("Sheet2!A1"), context);
        xlsArea.applyAt(new CellRef("Sheet1!A1"), context);
        xlsArea.processFormulas();
        transformer.write();
        return downLoadPath;
    }

    /**
     * @param data            数据
     * @param fileName     文件名
     * @param ctxPath      新文件存放路径
     * @param templatePath 模板路径
     * @return 新文件具体地址
     * @throws IOException            IOException
     * @throws InvalidFormatException InvalidFormatException
     */
    public String create(T data, String fileName, String ctxPath, String templatePath) throws IOException, InvalidFormatException {
        return create(data, "items", fileName, ctxPath, templatePath);
    }


}

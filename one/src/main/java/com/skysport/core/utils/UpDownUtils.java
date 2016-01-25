package com.skysport.core.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 说明:文件上传、下载工具类
 * Created by zhangjh on 2015/9/16.
 */
public class UpDownUtils {

    static transient Log logger = LogFactory.getLog(UpDownUtils.class);

    /**
     * 下载文件
     *
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param fileName     String
     * @param downLoadPath String
     * @throws IOException
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String fileName, String downLoadPath) throws UnsupportedEncodingException {

        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        logger.info("downLoadPath:" + downLoadPath);

        try {

            long fileLength = new File(downLoadPath).length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());

            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }

        } catch (Exception e) {
            logger.error("下载文件出现异常", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    logger.error("关闭文件流出现异常", e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    logger.error("关闭文件流出现异常", e);
                }
            }

        }
    }
}

package com.skysport.interfaces.utils;

import com.skysport.core.utils.DateUtils;
import com.skysport.interfaces.constant.WebConstants;

/**
 * 说明:
 * Created by zhangjh on 2015/9/9.
 */
public enum FileUtils {

    SINGLETONE;

    static String[] imgsArr = new String[]{"BMP", "JPEG", "JPG", "PNG", "TIF", "GIF", "ICO"};

    /**
     * 文件名格式
     *
     * @return
     */
    public String buildNewFileName() {
        String fileName;
        String timestamp = DateUtils.SINGLETONE.format(DateUtils.YYYYMMDDHHMMSS);
        long randomNum = java.util.concurrent.ThreadLocalRandom.current().nextInt(1000, 10000);
        fileName = timestamp + randomNum;
        return fileName;
    }

    /**
     * 获取响应文件的存放目录
     *
     * @param suffix
     * @return
     */
    public String getPathType(String suffix) {

        String pathType = null;
        if (WebConstants.FILE_TXT.equals(suffix.toLowerCase())) {
            pathType = WebConstants.FILE_TXT;
        } else {
            for (int index = 0; index < imgsArr.length; index++) {
                if (suffix.toUpperCase().equals(imgsArr[index])) {
                    pathType = WebConstants.FILE_IMG;
                    break;
                }
            }
            if (null == pathType) {
                pathType = WebConstants.FILE_OTHER;
            }
        }
        return pathType;
    }


}
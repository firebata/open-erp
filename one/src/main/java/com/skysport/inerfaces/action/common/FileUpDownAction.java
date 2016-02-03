package com.skysport.inerfaces.action.common;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.instance.DictionaryInfo;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.PrimaryKeyUtils;
import com.skysport.inerfaces.bean.basic.InitialPreviewConfig;
import com.skysport.inerfaces.bean.basic.InitialPreviewExtra;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/9/15.
 */
@Scope("prototype")
@Controller
@RequestMapping("/files")
public class FileUpDownAction extends BaseAction<String, Object, UserInfo> {

    Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "删除文件")
    public Map<String, Object> delete(InitialPreviewConfig config, HttpServletRequest request) {

        String key = config.getKey();


        Map<String, Object> resultMap = new HashMap<String, Object>();
        return resultMap;
    }

    /**
     * @param fileLocation
     * @param request
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "上传附件")
    public Map<String, Object> upload(@RequestParam("fileLocation") MultipartFile[] fileLocation, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        List<String> fileUrls = new ArrayList<>();
        List<InitialPreviewConfig> configs = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            String yyyymm = DateUtils.SINGLETONE.getYyyyMm();
            String separator = File.separator;
            for (MultipartFile file : fileLocation) {

                // 判断文件是否为空
                if (!file.isEmpty()) {
                    InitialPreviewConfig config = new InitialPreviewConfig();

                    String fileName = file.getOriginalFilename();
//                    String newFileName = FileUtils.SINGLETONE.buildNewFileName();
                    StringBuilder realPath = new StringBuilder(request.getSession().getServletContext().getRealPath("/"));
                    //文件保存路径
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
                    String pathType = FileUtils.SINGLETONE.getPathType(suffix);
                    String pathInPathType = DictionaryInfo.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, pathType);
                    String absoluteDirectory = realPath.toString() + new StringBuilder().append(separator).append("files").append(separator).append(pathInPathType).append(separator).append(yyyymm).append(separator).toString();
                    File targetFile = new File(absoluteDirectory, fileName);
                    String newFileNameId = PrimaryKeyUtils.getUUID();
                    if (targetFile.exists()) {

                        String relativeDirectory = new StringBuilder().append(separator).append("files").append(separator).append(pathInPathType).append(separator).append(yyyymm).append(separator).toString();
                        if (pathType.equals(WebConstants.FILE_TXT)) {
                            String txt = "<div class='file-preview-text' title='NOTES.txt'>" +
                                    "This is the sample text file content upto wrapTextLength of 250 characters" +
                                    "<span class='wrap-indicator' onclick='$(\"#show-detailed-text\").modal(\"show\")' title='NOTES.txt'>[…]</span>" +
                                    "</div>";
                            fileUrls.add(txt);
                        } else if (pathType.equals(WebConstants.FILE_IMG)) {
                            fileUrls.add("<img src='" + new StringBuilder(contextPath).append(relativeDirectory).append(newFileNameId).append(CharConstant.POINT).append(suffix).toString() + "' class='file-preview-image' alt='Desert' title='Desert'>");
                        } else {
                            String other = "<div class='file-preview-text'>" +
                                    "<h2><i class='glyphicon glyphicon-file'></i></h2>" +
                                    "Filename.xlsx" + "</div>";
                            fileUrls.add(other);
                        }

                        targetFile = new File(realPath.toString() + relativeDirectory, newFileNameId + CharConstant.POINT + suffix);
                    } else {
                        targetFile.mkdirs();
                        String relativeDirectory = new StringBuilder(contextPath).append(separator).append("files").append(separator).append(pathInPathType).append(separator).append(yyyymm).append(separator).append(fileName).toString();
                        if (pathType.equals(WebConstants.FILE_TXT)) {
                            String txt = "<div class='file-preview-text' title='NOTES.txt'>" +
                                    "This is the sample text file content upto wrapTextLength of 250 characters" +
                                    "<span class='wrap-indicator' onclick='$(\"#show-detailed-text\").modal(\"show\")' title='NOTES.txt'>[…]</span>" +
                                    "</div>";
                            fileUrls.add(txt);
                        } else if (pathType.equals(WebConstants.FILE_IMG)) {
                            fileUrls.add("<img src='" + relativeDirectory.toString() + "' class='file-preview-image' alt='Desert' title='Desert'>");
                        } else {
                            String other = "<div class='file-preview-text'>" +
                                    "<h2><i class='glyphicon glyphicon-file'></i></h2>" +
                                    "Filename.xlsx" + "</div>";
                            fileUrls.add(other);

                        }
                    }

                    config.setCaption(fileName);
                    config.setWidth("120px");
                    config.setUrl(contextPath + "/files/del");
                    config.setKey(newFileNameId);
                    InitialPreviewExtra extra = new InitialPreviewExtra();
                    extra.setId(newFileNameId);
                    config.setExtra(extra);
                    configs.add(config);
                    // 转存文件
                    file.transferTo(targetFile);

                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            resultMap.put("error", e.getMessage());
        }

        resultMap.put("initialPreview", fileUrls);
        resultMap.put("initialPreviewConfig", configs);
        // 重定向
        return resultMap;
    }


}

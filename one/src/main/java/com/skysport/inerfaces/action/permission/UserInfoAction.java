package com.skysport.inerfaces.action.permission;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.constant.DictionaryKeyConstant;
import com.skysport.core.instance.DictionaryInfo;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.PrimaryKeyUtils;
import com.skysport.core.utils.SecurityUtil;
import com.skysport.inerfaces.bean.basic.InitialPreviewConfig;
import com.skysport.inerfaces.bean.basic.InitialPreviewExtra;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.permission.userinfo.helper.UserInfoHelper;
import com.skysport.inerfaces.model.permission.userinfo.service.IUserInfoService;
import com.skysport.inerfaces.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/17.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/userinfo")
public class UserInfoAction extends BaseAction<String, Object, UserInfo> {

    @Resource(name = "userInfoService")
    private IUserInfoService userInfoService;


    /**
     * 此方法描述的是：展示list页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/add/{natrualKey}")
    @ResponseBody
    @SystemControllerLog(description = "新增用户")
    public ModelAndView add(@PathVariable String natrualKey) {
        ModelAndView mav = new ModelAndView("/system/permission/user-edit");
        mav.addObject("natrualkey", natrualKey);
        return mav;
    }

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    @SystemControllerLog(description = "查询用户信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        // HashMap<String, String> paramMap = convertToMap(params);
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(DictionaryKeyConstant.USERINFO_TABLE_COLULMN, request);
        // 总记录数
        int recordsTotal = userInfoService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = userInfoService.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<UserInfo> infos = userInfoService.searchInfos(dataTablesInfo);
        UserInfoHelper.SINGLETONE.turnSomeIdToNameInList(infos);
        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);

        return resultMap;
    }

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "编辑用户信息")
    public Map<String, Object> edit(UserInfo userInfo) throws Exception {
        String pwd = SecurityUtil.encrypt(userInfo.getPassword());
        userInfo.setPassword(pwd);
        userInfoService.edit(userInfo);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", "0");
        resultMap.put("message", "更新成功");
        return resultMap;
    }

    /**
     * @param fileLocation
     * @param request
     * @return
     */
    @RequestMapping(value = "/add/fileUpload", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "上传附件")
    public Map<String, Object> upload(@RequestParam("fileLocation") MultipartFile[] fileLocation, HttpServletRequest request) {
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
                    StringBuilder contextPath = new StringBuilder(request.getSession().getServletContext().getRealPath("/"));
                    //文件保存路径
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);
                    String pathType = FileUtils.SINGLETONE.getPathType(suffix);
                    String pathInPathType = DictionaryInfo.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, pathType);
                    String absoluteDirectory = contextPath.toString() + new StringBuilder().append(separator).append("files").append(separator).append(pathInPathType).append(separator).append(yyyymm).append(separator).toString();
                    File targetFile = new File(absoluteDirectory, fileName);
                    String newFileNameId = PrimaryKeyUtils.getUUID();
                    if (targetFile.exists()) {

                        String relativeDirectory = new StringBuilder().append(separator).append("files").append(separator).append(pathInPathType).append(separator).append(yyyymm).append(separator).toString();
                        fileUrls.add("<img src='/sky" + new StringBuilder().append(relativeDirectory).append(newFileNameId).append(CharConstant.POINT).append(suffix).toString() + "' class='file-preview-image' alt='Desert' title='Desert'>");
                        targetFile = new File(contextPath.toString() + relativeDirectory, newFileNameId + CharConstant.POINT + suffix);
                    } else {
                        targetFile.mkdirs();
                        String relativeDirectory = new StringBuilder().append(separator).append("files").append(separator).append(pathInPathType).append(separator).append(yyyymm).append(separator).append(fileName).toString();
                        fileUrls.add("<img src='/sky" + relativeDirectory.toString() + "' class='file-preview-image' alt='Desert' title='Desert'>");
                    }
                    config.setCaption(fileName);
                    config.setWidth("120px");
                    config.setUrl("/files/del");
                    config.setKey(newFileNameId);
                    InitialPreviewExtra extra = new InitialPreviewExtra();
                    extra.setId(newFileNameId);
                    config.setExtra(extra);

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

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "增加用户信息")
    public Map<String, Object> add(UserInfo userInfo) throws Exception {
        //设置ID
        userInfo.setNatrualkey(PrimaryKeyUtils.getUUID());
        UserInfoHelper.SINGLETONE.encrptPwd(userInfo);
        userInfoService.add(userInfo);
        return rtnSuccessResultMap("新增成功");

    }


    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询用户信息")
    public UserInfo info(@PathVariable String natrualKey) {

        UserInfo userInfo = userInfoService.queryInfoByNatrualKey(natrualKey);
        return userInfo;
    }

    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除用户")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        userInfoService.del(natrualKey);
        return rtnSuccessResultMap("删除成功");
    }

    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = userInfoService.querySelectList(name);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("items", commonBeans);
        resultMap.put("total_count", commonBeans.size());
        return resultMap;

    }


    @RequestMapping(value = "/usertype", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> queryUserType(HttpServletRequest request) {
        Map<String, String> resultMap = DictionaryInfo.SINGLETONE.getValueMapByTypeKey(DictionaryKeyConstant.USER_TYPE);
        return resultMap;
    }

    /**
     * 此方法描述的是：修改用户密码
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/chgpwd", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "修改用户密码")
    public Map<String, Object> chgpwd(UserInfo userInfo) throws Exception {
        String pwd = SecurityUtil.encrypt(userInfo.getPassword().trim());
        userInfo.setPassword(pwd);
        userInfoService.chgpwd(userInfo);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", "0");
        resultMap.put("message", "更新成功");
        return resultMap;
    }


}

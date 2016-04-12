package com.skysport.inerfaces.action.permission;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.utils.UuidGeneratorUtils;
import com.skysport.core.utils.SecurityUtil;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.common.uploadfile.IUploadFileInfoService;
import com.skysport.inerfaces.model.common.uploadfile.helper.UploadFileHelper;
import com.skysport.inerfaces.model.permission.userinfo.helper.UserInfoHelper;
import com.skysport.inerfaces.model.permission.userinfo.service.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class UserInfoAction extends BaseAction<UserInfo> {

    @Resource(name = "userInfoService")
    private IUserInfoService userInfoService;

    @Resource(name = "uploadFileInfoService")
    private IUploadFileInfoService uploadFileInfoService;

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
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.USERINFO_TABLE_COLUMN, request);
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
    public Map<String, Object> edit(@RequestBody UserInfo userInfo, HttpServletRequest request) throws Exception {
//        String pwd = SecurityUtil.encrypt(userInfo.getPassword());
//        userInfo.setPassword(pwd);
        userInfoService.edit(userInfo);

        //删除文件记录表的status状态为1的数据
        uploadFileInfoService.del(userInfo.getNatrualkey(), WebConstants.FILE_IN_FINISH);
        List<UploadFileInfo> fileInfos = userInfo.getFileInfos();
        //回写文件记录表的status状态为1
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, userInfo.getNatrualkey(), uploadFileInfoService, WebConstants.FILE_KIND_USER);

        return rtnSuccessResultMap("更新成功");

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
    public Map<String, Object> add(@RequestBody UserInfo userInfo, HttpServletRequest request) throws Exception {

        String uid = UuidGeneratorUtils.getNextId();

        //设置ID
        userInfo.setNatrualkey(uid);

//        UserInfoHelper.SINGLETONE.encrptPwd(userInfo);

        userInfoService.add(userInfo);

        List<UploadFileInfo> fileInfos = userInfo.getFileInfos();


        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, uid, uploadFileInfoService, WebConstants.FILE_KIND_USER);

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
        if (null != userInfo) {
            Map<String, Object> fileinfosMap = UploadFileHelper.SINGLETONE.getFileInfoMap(uploadFileInfoService, natrualKey);
            userInfo.setFileinfosMap(fileinfosMap);
        }

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

        Map<String, String> resultMap = DictionaryInfoCachedMap.SINGLETONE.getValueMapByTypeKey(WebConstants.USER_TYPE);
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
        return rtnSuccessResultMap("密码更新成功");

    }


}

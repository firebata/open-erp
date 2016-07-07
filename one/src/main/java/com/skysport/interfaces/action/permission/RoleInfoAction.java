package com.skysport.interfaces.action.permission;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.RoleInfo;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.utils.UuidGeneratorUtils;
import com.skysport.interfaces.model.permission.roleinfo.helper.RoleInfoHelper;
import com.skysport.interfaces.model.permission.roleinfo.service.IRoleInfoService;
import com.skysport.interfaces.model.system.dept.service.IDepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping("/system/roleinfo")
public class RoleInfoAction extends BaseAction<RoleInfo> {


    @Resource(name = "roleInfoService")
    private IRoleInfoService roleInfoService;

    @Resource(name = "departmentService")
    private IDepartmentService departmentService;

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/add/{natrualKey}")
    @ResponseBody
    @SystemControllerLog(description = "新增角色")
    public ModelAndView add(@PathVariable String natrualKey) {
        ModelAndView mav = new ModelAndView("/system/permission/role-edit");
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
    @SystemControllerLog(description = "查询角色列表信息")
    public Map<String, Object> search(HttpServletRequest request) {
        // HashMap<String, String> paramMap = convertToMap(params);
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.USERINFO_TABLE_COLUMN, request);
        // 总记录数
        int recordsTotal = roleInfoService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = roleInfoService.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<RoleInfo> infos = roleInfoService.searchInfos(dataTablesInfo);
        List<SelectItem2> deptList = departmentService.querySelectList(null);
        RoleInfoHelper.SINGLETONE.turnSomeIdToNameInList(infos, deptList);
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
    @SystemControllerLog(description = "更新角色信息")
    public Map<String, Object> edit(RoleInfo roleinfoInfo) {
        roleInfoService.edit(roleinfoInfo);
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
    @SystemControllerLog(description = "新增角色")
    public Map<String, Object> add(RoleInfo roleinfoInfo) {
        //设置ID
        roleinfoInfo.setNatrualkey(UuidGeneratorUtils.getNextId());
//        RoleInfoHelper.SINGLETONE.encriptPwd(roleinfoInfo);
        roleInfoService.add(roleinfoInfo);
        return rtnSuccessResultMap("新增成功");
    }


    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询角色信息")
    public RoleInfo info(@PathVariable String natrualKey) {
        RoleInfo roleinfoInfo = roleInfoService.queryInfoByNatrualKey(natrualKey);
        return roleinfoInfo;
    }

    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除角色信息")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        roleInfoService.del(natrualKey);
        return rtnSuccessResultMap("删除成功");
    }


    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = roleInfoService.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }

    /**
     * 查询部门信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/deptinfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> deptinfo(HttpServletRequest request) {
        Map<String, String> resultMap = DictionaryInfoCachedMap.SINGLETONE.getValueMapByTypeKey(WebConstants.DEPT_TYPE);
        return resultMap;

    }

    @RequestMapping(value = "/qrallrole", method = RequestMethod.GET)
    @ResponseBody
    public List<RoleInfo> queryAllRole() {
        DataTablesInfo dataTablesInfo = new DataTablesInfo();
        List<RoleInfo> roleInfos = roleInfoService.searchInfos(dataTablesInfo);
        return roleInfos;

    }

    @RequestMapping(value = "/qrallselectitems", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "")
    public Map<String, Object> queryAllSelectItems() {
//        Map<String, String> deptTypeItems = DictionaryInfoCachedMap.SINGLETONE.getValueMapByTypeKey(WebConstants.DEPT_TYPE);
        List<SelectItem2> commonBeans = departmentService.querySelectList(null);
        DataTablesInfo dataTablesInfo = new DataTablesInfo();
        List<RoleInfo> roleInfosItems = roleInfoService.searchInfos(dataTablesInfo);
        Map<String, Object> resultsMap = new HashMap<>();
        resultsMap.put("deptTypeItems", commonBeans);
        resultsMap.put("roleInfosItems", roleInfosItems);
        return resultsMap;
    }


}

package com.skysport.inerfaces.action.system;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.core.utils.PrimaryKeyUtils;
import com.skysport.inerfaces.bean.system.DepartmentInfo;
import com.skysport.inerfaces.model.system.dept.service.IDepartmentService;
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
 * 说明:
 * Created by zhangjh on 2015/12/31.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/dept")
public class DepartmentAction extends BaseAction<String, Object, DepartmentInfo> {

    @Resource(name = "departmentService")
    private IDepartmentService departmentService;

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "点击部门菜单")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/dept/dept-list");
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
    @SystemControllerLog(description = "查询部门信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.DEPT_TABLE_COLUMN_NAME, request);
//        DataTablesInfo dataTablesInfo = buildQueryDataTableInfo(request);
        // 总记录数
        int recordsTotal = departmentService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = departmentService.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<DepartmentInfo> infos = departmentService.searchInfos(dataTablesInfo);
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
    @SystemControllerLog(description = "编辑部门信息")
    public Map<String, Object> edit(DepartmentInfo departmentInfo) {

        departmentService.edit(departmentInfo);
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
    @SystemControllerLog(description = "新增部门信息")
    public Map<String, Object> add(DepartmentInfo departmentInfo) {
        //设置ID
        departmentInfo.setNatrualkey(PrimaryKeyUtils.getUUID());
        departmentService.add(departmentInfo);
        return rtnSuccessResultMap("新增成功");
    }

    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "展示部门信息")
    public DepartmentInfo info(@PathVariable String natrualKey) {
        DepartmentInfo departmentInfo = departmentService.queryInfoByNatrualKey(natrualKey);
        return departmentInfo;
    }

    /**
     * @param natrualKey String
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除部门信息")
    public Map<String, Object> del(@PathVariable String natrualKey) {

        departmentService.del(natrualKey);
        return rtnSuccessResultMap("删除成功");

    }

    /**
     * @param request HttpServletRequest
     * @return Map<String, Object>
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = departmentService.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }


    @RequestMapping(value = "/qrallselectitems", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryAllSelectItems() {

        Map<String, Object> resultsMap = new HashMap<>();
        List<SelectItem2> commonBeans = departmentService.querySelectList(null);

        resultsMap.put("deptTypeItems", commonBeans);
        return resultsMap;
    }

}

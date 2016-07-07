package com.skysport.interfaces.action.info.base;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.interfaces.bean.info.SpInfo;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.model.info.sp.helper.SpInfoHelper;
import com.skysport.interfaces.model.info.sp.service.ISpManageService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
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
import java.util.List;
import java.util.Map;

/**
 * 此类描述的是：供应商管理
 *
 * @author: zhangjh
 * @version: 2015年4月29日 下午5:34:47
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/sp")
public class SpManageAction extends BaseAction<SpInfo> {
    @Resource(name = "spManageService")
    private ISpManageService spManageService;

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "点击供应商菜单")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/sp/list2");
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
    @SystemControllerLog(description = "查询供应商信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        // HashMap<String, String> paramMap = convertToMap(params);
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.SP_TABLE_COLUMN, request);
        // 总记录数
        int recordsTotal = spManageService.listSPInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = spManageService.listFilteredSPInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<SpInfo> spInfos = spManageService.searchSP(dataTablesInfo);
        SpInfoHelper.SINGLETONE.turnMaterialTypeIdToName(spInfos);
        Map<String, Object> resultMap = buildSearchJsonMap(spInfos, recordsTotal, recordsFiltered, draw);

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
    @SystemControllerLog(description = "编辑供应商信息")
    public Map<String, Object> edit(SpInfo spInfo, HttpServletRequest request) {
        spManageService.edit(spInfo);

        SpInfoHelper.SINGLETONE.refreshSelect();
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
    @SystemControllerLog(description = "新增供应商信息")
    public Map<String, Object> add(SpInfo spInfo) {
        //设置ID
        spInfo.setSpId(BuildSeqNoHelper.SINGLETONE.getFullSeqNo(WebConstants.SP_INFO));
        spManageService.add(spInfo);

        SpInfoHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("新增成功");
    }


    /**
     * @param spId 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/spinfo/{spId}", method = RequestMethod.GET)
    @ResponseBody
    public SpInfo querySpNo(@PathVariable String spId) {
        SpInfo spInfo = spManageService.querySpInfoBySpId(spId);
        return spInfo;
    }

    /**
     * @param spId
     * @return
     */
    @RequestMapping(value = "/del/{spId}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除供应商")
    public Map<String, Object> del(@PathVariable String spId) {
        spManageService.del(spId);
        SpInfoHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("删除成功");
    }


    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String keyword = request.getParameter("keyword");
        List<SelectItem2> commonBeans = spManageService.querySelectList(keyword);
        return rtSelectResultMap(commonBeans);
    }

}

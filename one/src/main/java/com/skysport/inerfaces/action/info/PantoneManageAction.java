package com.skysport.inerfaces.action.info;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.inerfaces.bean.info.PantoneInfo;
import com.skysport.inerfaces.model.info.service.IPantoneManageService;
import com.skysport.inerfaces.model.info.service.helper.PantoneManageServiceHelper;
import com.skysport.inerfaces.utils.BuildSeqNoHelper;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 类描述的是：PANTONE管理
 * Created by zhangjh on 2015/6/4.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/pantone")
public class PantoneManageAction extends BaseAction<PantoneInfo> {

    @Resource(name = "pantoneManageService")
    private IPantoneManageService pantoneManageService;

    @Resource(name = "incrementNumber")
    private IncrementNumber incrementNumber;

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "点击PANTONE菜单")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/pantone/list2");
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
    @SystemControllerLog(description = "查询PANTONE信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        // HashMap<String, String> paramMap = convertToMap(params);
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.PANTONE_TABLE_COLUMN, request);
        // 总记录数
        int recordsTotal = pantoneManageService.listPantoneInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = pantoneManageService.listFilteredPantoneInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<PantoneInfo> pantoneInfos = pantoneManageService.searchPantoneInfos(dataTablesInfo);
        Map<String, Object> resultMap = buildSearchJsonMap(pantoneInfos, recordsTotal, recordsFiltered, draw);

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
    @SystemControllerLog(description = "编辑PANTONE")
    public Map<String, Object> edit(PantoneInfo pantoneInfo, HttpServletRequest request,
                                    HttpServletResponse respones) {
        pantoneManageService.edit(pantoneInfo);
        PantoneManageServiceHelper.SINGLETONE.refreshSelect();
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
    @SystemControllerLog(description = "增加PANTONE")
    public Map<String, Object> add(PantoneInfo pantoneInfo, HttpServletRequest request,
                                   HttpServletResponse repantoneonse) {
        //设置ID
        pantoneInfo.setPantoneId(BuildSeqNoHelper.SINGLETONE.getFullSeqNo(WebConstants.PANTONE_INFO, incrementNumber));
        pantoneManageService.add(pantoneInfo);
        PantoneManageServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("新增成功");
    }


    /**
     * @param pantoneId     主键id
     * @param request       请求信息
     * @param repantoneonse 返回信息
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{pantoneId}", method = RequestMethod.GET)
    @ResponseBody
    public PantoneInfo queryPantoneNo(@PathVariable String pantoneId, HttpServletRequest request, HttpServletResponse repantoneonse) {
        PantoneInfo pantoneInfo = pantoneManageService.queryPantoneInfoByPantoneId(pantoneId);
        return pantoneInfo;
    }

    /**
     * @param pantoneId
     * @return
     */
    @RequestMapping(value = "/del/{pantoneId}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除PANTONE")
    public Map<String, Object> del(@PathVariable String pantoneId) {
        pantoneManageService.del(pantoneId);
        PantoneManageServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("删除成功");
    }


    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = pantoneManageService.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }

}

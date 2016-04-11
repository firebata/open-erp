package com.skysport.inerfaces.action.info;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.core.model.common.ICommonService;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.model.develop.fabric.helper.FabricsServiceHelper;
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
import java.util.List;
import java.util.Map;

/**
 * 类描述的是：面料管理
 * Created by zhangjh on 2015/6/9.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/fabrics")
public class FabricsAction extends BaseAction<FabricsInfo> {
    @Resource(name = "fabricsManageService")
    private ICommonService fabricsManageService;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/fabrics/list");
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
    @SystemControllerLog(description = "")
    public Map<String, Object> search(HttpServletRequest request) {
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.FABRICS_TABLE_COLUMN, request);
        // 总记录数
        int recordsTotal = fabricsManageService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = fabricsManageService.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<FabricsInfo> fabricsInfos = fabricsManageService.searchInfos(dataTablesInfo);
        Map<String, Object> resultMap = buildSearchJsonMap(fabricsInfos, recordsTotal, recordsFiltered, draw);

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
    @SystemControllerLog(description = "")
    public Map<String, Object> edit(FabricsInfo fabricsInfo, HttpServletRequest request) {

        fabricsManageService.edit(fabricsInfo);


        FabricsServiceHelper.SINGLETONE.refreshSelect();

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
    @SystemControllerLog(description = "")
    public Map<String, Object> add(FabricsInfo fabricsInfo, HttpServletRequest request) {

        String currentNo = fabricsManageService.queryCurrentSeqNo();
        //设置ID
        fabricsInfo.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.FABRICS_INFO, currentNo, incrementNumberService));
        fabricsManageService.add(fabricsInfo);


        FabricsServiceHelper.SINGLETONE.refreshSelect();

        return rtnSuccessResultMap("新增成功");

    }


    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "")
    public FabricsInfo info(@PathVariable String natrualKey) {
        FabricsInfo fabricsInfo = (FabricsInfo) fabricsManageService.queryInfoByNatrualKey(natrualKey);
        return fabricsInfo;
    }

    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        fabricsManageService.del(natrualKey);
        FabricsServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("删除成功");
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "")
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = fabricsManageService.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }


}

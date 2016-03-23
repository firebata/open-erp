package com.skysport.inerfaces.action.info;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.model.common.ICommonService;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.inerfaces.bean.info.BondingLaminationCoatingInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.info.material.impl.helper.BondingLaminationCoatingServiceHelper;
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
 * 类说明:复合或涂层
 * Created by zhangjh on 2015/6/25.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/material/blc")
public class BondingLaminationCoatingAction extends BaseAction<String, Object, BondingLaminationCoatingInfo> {
    @Resource(name = "bondingLaminationCoatingService")
    private ICommonService bondingLaminationCoatingService;

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
    @SystemControllerLog(description = "点击复合或涂层菜单")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/material/blc/list");
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
    @SystemControllerLog(description = "查询复合或涂层信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        // HashMap<String, String> paramMap = convertToMap(params);
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.BLC_TABLE_COLUMN, request);
        // 总记录数
        int recordsTotal = bondingLaminationCoatingService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = bondingLaminationCoatingService.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<BondingLaminationCoatingInfo> infos = bondingLaminationCoatingService.searchInfos(dataTablesInfo);
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
    @SystemControllerLog(description = "编辑复合或涂层信息")
    public Map<String, Object> edit(BondingLaminationCoatingInfo areaInfo, HttpServletRequest request,
                                    HttpServletResponse respones) {
        bondingLaminationCoatingService.edit(areaInfo);
        BondingLaminationCoatingServiceHelper.SINGLETONE.refreshSelect();
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
    @SystemControllerLog(description = "增加复合或涂层")
    public Map<String, Object> add(BondingLaminationCoatingInfo areaInfo, HttpServletRequest request,
                                   HttpServletResponse reareaonse) {
        String currentNo = bondingLaminationCoatingService.queryCurrentSeqNo();
        //设置ID
        areaInfo.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.BLC_INFO, currentNo, incrementNumber));
        bondingLaminationCoatingService.add(areaInfo);
        BondingLaminationCoatingServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("新增成功");
    }


    /**
     * @param natrualKey 主键id
     * @param request    请求信息
     * @param reareaonse 返回信息
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询复合或涂层信息")
    public BondingLaminationCoatingInfo info(@PathVariable String natrualKey, HttpServletRequest request, HttpServletResponse reareaonse) {
        BondingLaminationCoatingInfo areaInfo = (BondingLaminationCoatingInfo) bondingLaminationCoatingService.queryInfoByNatrualKey(natrualKey);
        return areaInfo;
    }

    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除复合或涂层信息")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        bondingLaminationCoatingService.del(natrualKey);
        BondingLaminationCoatingServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("删除成功");
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = bondingLaminationCoatingService.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }
}
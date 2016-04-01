package com.skysport.inerfaces.action.info;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.inerfaces.bean.info.MaterialPositionInfo;
import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.model.info.material.impl.helper.PositionServiceHelper;
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
 * 类说明:物料位置
 * Created by zhangjh on 2015/7/3.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/material/position")
public class PositionManageAction extends BaseAction<MaterialPositionInfo> {
    @Resource(name = "materialPositionService")
    private ICommonService materialPositionService;

    @Resource(name = "incrementNumber")
    private IncrementNumber incrementNumber;

    /**
     * 此方法描述的是：展示list页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "点击物料位置菜单")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/material/position/list");
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
    @SystemControllerLog(description = "查询物料位置信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        // HashMap<String, String> paramMap = convertToMap(params);
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.MATERIAL_POSITION_TABLE_COLUMN, request);
        // 总记录数
        int recordsTotal = materialPositionService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = materialPositionService.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<MaterialPositionInfo> infos = materialPositionService.searchInfos(dataTablesInfo);
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
    @SystemControllerLog(description = "编辑物料位置")
    public Map<String, Object> edit(MaterialPositionInfo info, HttpServletRequest request) {
        materialPositionService.edit(info);

        PositionServiceHelper.SINGLETONE.refreshSelect();
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
    @SystemControllerLog(description = "增加物料位置")
    public Map<String, Object> add(MaterialPositionInfo info, HttpServletRequest request) {
        String currentNo = materialPositionService.queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_MATERIAL_POSITION_INFO, currentNo, incrementNumber));
        materialPositionService.add(info);

        PositionServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("新增成功");
    }


    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询物料位置")
    public MaterialPositionInfo info(@PathVariable String natrualKey) {
        MaterialPositionInfo info = (MaterialPositionInfo) materialPositionService.queryInfoByNatrualKey(natrualKey);
        return info;
    }

    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除物料位置")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        materialPositionService.del(natrualKey);
        PositionServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("删除成功");
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = materialPositionService.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }


}


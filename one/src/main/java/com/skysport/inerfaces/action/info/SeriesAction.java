package com.skysport.inerfaces.action.info;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.constant.DictionaryKeyConstant;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.inerfaces.bean.info.SeriesInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.model.info.series.SeriesManageServiceHelper;
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
 * 类描述的是：系列
 * Created by zhangjh on 2015/6/9.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/series")
public class SeriesAction extends BaseAction<String, Object, SeriesInfo> {
    @Resource(name = "seriesManageService")
    private ICommonService seriesManageService;

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
    @SystemControllerLog(description = "点击系列菜单")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/series/list");
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
    @SystemControllerLog(description = "查询系列信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        // HashMap<String, String> paramMap = convertToMap(params);
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(DictionaryKeyConstant.SERIES_TABLE_COLULMN, request);
        // 总记录数
        int recordsTotal = seriesManageService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = seriesManageService.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<SeriesInfo> seriesInfos = seriesManageService.searchInfos(dataTablesInfo);
        Map<String, Object> resultMap = buildSearchJsonMap(seriesInfos, recordsTotal, recordsFiltered, draw);

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
    @SystemControllerLog(description = "编辑系列")
    public Map<String, Object> edit(SeriesInfo seriesInfo, HttpServletRequest request,
                                    HttpServletResponse respones) {
        seriesManageService.edit(seriesInfo);

        SeriesManageServiceHelper.SINGLETONE.refreshSelect();
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
    @SystemControllerLog(description = "新增系列")
    public Map<String, Object> add(SeriesInfo seriesInfo, HttpServletRequest request) {
        String currentNo = seriesManageService.queryCurrentSeqNo();
        //设置ID
        seriesInfo.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.SERIES_INFO, currentNo, incrementNumber));
        seriesManageService.add(seriesInfo);

        SeriesManageServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("新增成功");
    }


    /**
     * @param natrualKey   主键id
     * @param request      请求信息
     * @param reseriesonse 返回信息
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询系列信息")
    public SeriesInfo info(@PathVariable String natrualKey, HttpServletRequest request, HttpServletResponse reseriesonse) {
        SeriesInfo seriesInfo = (SeriesInfo) seriesManageService.queryInfoByNatrualKey(natrualKey);
        return seriesInfo;
    }

    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除系列")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        seriesManageService.del(natrualKey);
        SeriesManageServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("删除成功");
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = seriesManageService.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }


}

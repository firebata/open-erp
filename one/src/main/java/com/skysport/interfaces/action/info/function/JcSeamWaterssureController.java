package com.skysport.interfaces.action.info.function;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.interfaces.bean.jc.JcSeamWaterssure;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.model.jc.IJcSeamWaterssureService;
import com.skysport.interfaces.model.jc.helper.JcSeamWaterssureHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 接缝水压接口层
 * Created by zhangjh on 2016-7-7 15:10:10
 */
@RestController
@RequestMapping("/system/function/seam_waterssure")
public class JcSeamWaterssureController extends BaseAction<JcSeamWaterssure> {

    @Resource(name = "jcSeamWaterssureServiceImpl")
    private IJcSeamWaterssureService jcSeamWaterssureServiceImpl;


    /**
     * 此方法描述的是：展示list页面
     *
     * @author: zhangjh
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "点击菜单")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/function/seam_waterssure/list");
        return mav;
    }

    /**
     * 查询详细信息
     *
     * @param businessKey
     * @return
     */
    @SystemControllerLog(description = "查询详细信息")
    @RequestMapping(value = "/seam_waterssure/{businessKey}", method = RequestMethod.GET)
    public JcSeamWaterssure queryByBusinessKey(@PathVariable("businessKey") String businessKey) {
        JcSeamWaterssure jcSeamWaterssure = (JcSeamWaterssure) jcSeamWaterssureServiceImpl.queryInfoByNatrualKey(businessKey);
        return jcSeamWaterssure;
    }


    /**
     * 删除
     *
     * @param businessKey
     * @return
     */
    @SystemControllerLog(description = "删除")
    @RequestMapping(value = "/del/{businessKey}", method = RequestMethod.DELETE)
    public Map<String, Object> deleteByBusinessKey(@PathVariable("businessKey") String businessKey) {
        JcSeamWaterssure jcSeamWaterssure = null;
        jcSeamWaterssureServiceImpl.del(businessKey);
        JcSeamWaterssureHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("删除成功");
    }


    /**
     * 更新
     *
     * @param jcSeamWaterssure
     * @return
     */
    @SystemControllerLog(description = "更新")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Map<String, Object> update(JcSeamWaterssure jcSeamWaterssure, HttpServletRequest request, HttpServletResponse respones) {
        jcSeamWaterssureServiceImpl.edit(jcSeamWaterssure);
        JcSeamWaterssureHelper.SINGLETONE.refreshSelect();
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
    @SystemControllerLog(description = "新增水压方式")
    public Map<String, Object> add(JcSeamWaterssure jcSeamWaterssure, HttpServletRequest request,
                                   HttpServletResponse reareaonse) {

        jcSeamWaterssureServiceImpl.add(jcSeamWaterssure);

        JcSeamWaterssureHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("新增成功");
    }

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/search")
    @ResponseBody
    @SystemControllerLog(description = "查询信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.SEAM_WATERSSURE_TABLE_COLUMN_NAME, request);
        // 总记录数
        int recordsTotal = jcSeamWaterssureServiceImpl.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = jcSeamWaterssureServiceImpl.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<JcSeamWaterssure> infos = jcSeamWaterssureServiceImpl.searchInfos(dataTablesInfo);
        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);
        return resultMap;
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = jcSeamWaterssureServiceImpl.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }
}

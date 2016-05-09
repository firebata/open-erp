package com.skysport.inerfaces.action.develop;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.inerfaces.bean.form.develop.PreQuoteQueryForm;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.develop.quoted.helper.QuotedServiceHelper;
import com.skysport.inerfaces.model.develop.quoted.service.IQuotedService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 说明:预报价
 * Created by zhangjh on 2016/4/14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/quotepre")
public class QuotePreAction extends BaseAction<QuotedInfo> {

    /**
     *
     */
    @Resource(name = "quotedService")
    private IQuotedService quotedService;

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "打开BOM列表页面")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("/development/quotepre/quotepre-list");
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
    @SystemControllerLog(description = "查询BOM列表信息")
    public Map<String, Object> search(HttpServletRequest request) {
        //组件queryFory的参数
        PreQuoteQueryForm queryForm = new PreQuoteQueryForm();
        queryForm.setDataTablesInfo(convertToDataTableQrInfo(WebConstants.PRE_QUOTE_TABLE_COLUMN_NAME, request));
        QuotedInfo info = QuotedServiceHelper.getInstance().getInfo(request, WebConstants.QUOTED_STEP_PRE);
        queryForm.setQuoteInfo(info);
        Map<String, Object> resultMap = buildSearchJsonMap(queryForm, request, quotedService);
        return resultMap;
    }

    /**
     * 此方法描述的是：展示add页面
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/add/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "新增子项目")
    public ModelAndView add(@PathVariable String natrualKey, HttpServletRequest request) {
        QuotedInfo quotedInfo = info(natrualKey);
        String taskId = (String) request.getAttribute("taskId");
        String processInstanceId = (String) request.getAttribute("processInstanceId");
        ModelAndView mav = new ModelAndView("development/quotepre/quotepre-edit");
        mav.addObject("natrualkey", natrualKey);
        mav.addObject("taskId", taskId);
        mav.addObject("processInstanceId", processInstanceId);
        mav.addObject("quotedInfo", quotedInfo);
        return mav;
    }

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "更新预报价")
    public Map<String, Object> edit(@RequestBody QuotedInfo info) {
        quotedService.edit(info);
        return rtnSuccessResultMap("更新成功");

    }

    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询BOM信息")
    public QuotedInfo info(@PathVariable String natrualKey) {
        //报价信息
        QuotedInfo quotedInfo = quotedService.queryInfoByNatrualKey(natrualKey);
        return quotedInfo;
    }

    /**
     * 此方法描述的是：表单提交
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/submit/{taskId}/{businessKey}")
    @ResponseBody
    @SystemControllerLog(description = "处理任务：调转到指定的查询详情页面")
    public ModelAndView submit(@PathVariable String taskId, @PathVariable String businessKey, HttpServletRequest request) {
        quotedService.submit(taskId, businessKey);
        ModelAndView mav = new ModelAndView("forward:/task/todo/list");
        return mav;
    }


}

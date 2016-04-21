package com.skysport.inerfaces.action.develop;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.inerfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.bean.form.develop.FactoryQuotePreQueryForm;
import com.skysport.inerfaces.model.develop.quoted.helper.QuotedServiceHelper;
import com.skysport.inerfaces.model.develop.quoted.service.IFactoryQuoteService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016/4/14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/quotepre")
public class FactoryQuotePreAction extends BaseAction<FactoryQuoteInfo> {

    /**
     *
     */
    @Resource(name = "factoryQuoteService")
    private IFactoryQuoteService factoryQuoteService;

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @SystemControllerLog(description = "打开BOM列表页面")
    public ModelAndView search() {
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
        FactoryQuotePreQueryForm queryForm = new FactoryQuotePreQueryForm();
        queryForm.setDataTablesInfo(convertToDataTableQrInfo(WebConstants.BOM_TABLE_COLUMN, request));
        FactoryQuoteInfo info = QuotedServiceHelper.getInstance().getInfo(request);
        queryForm.setQuoteInfo(info);
        Map<String, Object> resultMap = buildSearchJsonMap(queryForm, request, factoryQuoteService);
        return resultMap;
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
        QuotedInfo quotedInfo = factoryQuoteService.queryInfoByNatrualKey(natrualKey);
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
        factoryQuoteService.submit(taskId, businessKey);
        ModelAndView mav = new ModelAndView("forward:/task/todo/list");
        return mav;
    }


}

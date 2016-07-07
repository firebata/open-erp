package com.skysport.interfaces.action.develop;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.interfaces.bean.develop.QuotedInfo;
import com.skysport.interfaces.bean.form.develop.PreQuoteQueryForm;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.model.develop.quoted.helper.QuotedServiceHelper;
import com.skysport.interfaces.model.develop.quoted.service.IQuotedService;
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
 * 说明:正式报价
 * Created by zhangjh on 2016/4/14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/quoteend")
public class QuoteEndAction extends BaseAction<QuotedInfo> {

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
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/development/quoteend/quoteend-list");
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
        QuotedInfo info = QuotedServiceHelper.getInstance().getInfo(request, WebConstants.QUOTED_STEP_END);
        queryForm.setQuoteInfo(info);
        Map<String, Object> resultMap = buildSearchJsonMap(queryForm, request, quotedService);
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
        QuotedInfo quotedInfo = quotedService.queryInfoByNatrualKey(natrualKey);
        return quotedInfo;
    }


}

package com.skysport.inerfaces.action.develop;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.inerfaces.bean.develop.KfProductionInstructionEntity;
import com.skysport.inerfaces.bean.form.develop.ProInstrQueryForm;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.develop.product_instruction.IProductionInstructionService;
import com.skysport.inerfaces.model.develop.product_instruction.helper.ProductionInstructionServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016/4/14.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/prdinstr")
public class ProductionInstractionAction extends BaseAction<KfProductionInstructionEntity> {
    /**
     *
     */
    @Autowired
    private IProductionInstructionService productionInstructionServiceImpl;

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
        ModelAndView mav = new ModelAndView("/development/prdinstr/prdinstr-list");
        return mav;
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
        String taskId = (String) request.getAttribute("taskId");
        String processInstanceId = (String) request.getAttribute("processInstanceId");
        ModelAndView mav = new ModelAndView("development/proinstr/proinstr-edit");
        mav.addObject("natrualkey", natrualKey);
        mav.addObject("taskId", taskId);
        mav.addObject("processInstanceId", processInstanceId);
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
        ProInstrQueryForm queryForm = new ProInstrQueryForm();
        queryForm.setDataTablesInfo(convertToDataTableQrInfo(WebConstants.PRE_QUOTE_TABLE_COLUMN_NAME, request, WebConstants.NO_NEED_TRANSFORM_COLUMN_NAME));
        KfProductionInstructionEntity info = ProductionInstructionServiceHelper.SINGLETONE.getInfo(request);
        queryForm.setProductionInstr(info);
        Map<String, Object> resultMap = buildSearchJsonMap(queryForm, request, productionInstructionServiceImpl);
        return resultMap;
    }


    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询BOM信息")
    public KfProductionInstructionEntity info(@PathVariable String natrualKey) {
        //报价信息
        KfProductionInstructionEntity quotedInfo = productionInstructionServiceImpl.queryInfoByNatrualKey(natrualKey);
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
        productionInstructionServiceImpl.submit(taskId, businessKey);
        ModelAndView mav = new ModelAndView("forward:/task/todo/list");
        return mav;
    }
}

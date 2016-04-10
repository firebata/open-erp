package com.skysport.inerfaces.action.develop;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.bom.helper.BomManageHelper;
import com.skysport.inerfaces.model.develop.quoted.service.IQuotedService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Bom
 * Created by zhangjh on 2015/6/23.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/bom")
public class BomAction extends BaseAction<BomInfo> {

    @Resource(name = "bomManageService")
    private IBomManageService bomManageService;

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
        ModelAndView mav = new ModelAndView("/development/bom/bom-list");
        return mav;
    }

    /**
     * 此方法描述的是：展示list页面	 *
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:34:53
     */
    @RequestMapping(value = "/bom-add")
    @ResponseBody
    @SystemControllerLog(description = "打开新增BOM新增页面")
    public ModelAndView add() {
        ModelAndView mav = new ModelAndView("development/bom/bom-add");
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
    @SystemControllerLog(description = "打开新增BOM新增页面")
    public ModelAndView add(@PathVariable String natrualKey) {
        ModelAndView mav = new ModelAndView("development/bom/bom-add");
        mav.addObject("natrualkey", natrualKey);
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
        BomQueryForm bomQueryForm = new BomQueryForm();
        bomQueryForm.setDataTablesInfo(convertToDataTableQrInfo(WebConstants.BOM_TABLE_COLUMN, request));
        BomInfo bomInfo = new BomInfo();
        bomQueryForm.setBomInfo(bomInfo);
        BomManageHelper.buildBomQueryForm(bomQueryForm, request);

        // 总记录数
        int recordsTotal = bomManageService.listInfosCounts();
        int recordsFiltered = recordsTotal;

        if (!StringUtils.isBlank(bomQueryForm.getDataTablesInfo().getSearchValue())) {
            recordsFiltered = bomManageService.listFilteredInfosCounts(bomQueryForm);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));

        List<BomInfo> infos = bomManageService.searchInfos(bomQueryForm);
        BomManageHelper.turnSexIdToName(infos);
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
    @SystemControllerLog(description = "编辑BOM信息")
    public Map<String, Object> edit(@RequestBody BomInfo info) {
        info.setBomId(info.getNatrualkey());
        bomManageService.edit(info);
        Map resultMap = rtnSuccessResultMap(MSG_UPDATE_SUCCESS);
        return resultMap;
    }


    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询BOM信息")
    public BomInfo info(@PathVariable String natrualKey) {

        BomInfo info = bomManageService.queryInfoByNatrualKey(natrualKey);

        //报价信息
        QuotedInfo quotedInfo = quotedService.queryInfoByNatrualKey(natrualKey);

        info.setQuotedInfo(quotedInfo);
        return info;
    }


    /**
     * @return 导出生产指示单
     * @throws IOException
     */
    @RequestMapping("/download_productinstruction/{natrualkeys}")
    @SystemControllerLog(description = "导出生产指示单")
    public ModelAndView downloadProductinstruction(@PathVariable String natrualkeys, HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidFormatException {
        bomManageService.downloadProductinstruction(request, response, natrualkeys);
        return null;
    }


    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除主项目信息")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        bomManageService.delCacadBomInfo(natrualKey);
        return rtnSuccessResultMap("删除成功");
    }

}

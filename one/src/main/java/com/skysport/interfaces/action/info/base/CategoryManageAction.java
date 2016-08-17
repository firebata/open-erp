package com.skysport.interfaces.action.info.base;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.info.CategoryInfo;
import com.skysport.interfaces.model.info.category.CategoryManageServiceHelper;
import com.skysport.interfaces.model.info.category.ICategoryManageService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类说明:品类级别
 * Created by zhangjh on 2015/7/3.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/category")
public class CategoryManageAction extends BaseAction<CategoryInfo> {

    @Resource(name = "categoryManageService")
    private ICategoryManageService categoryManageService;

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
    @SystemControllerLog(description = "点击品类级别")
    public ModelAndView search() {
        ModelAndView mav = new ModelAndView("/system/category/list");
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
    @SystemControllerLog(description = "查询品类级别信息列表")
    public Map<String, Object> search(HttpServletRequest request) {
        // HashMap<String, String> paramMap = convertToMap(params);
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(WebConstants.CATEGORY_TABLE_COLUMN, request);
        // 总记录数
        int recordsTotal = categoryManageService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(dataTablesInfo.getSearchValue())) {
            recordsFiltered = categoryManageService.listFilteredInfosCounts(dataTablesInfo);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<CategoryInfo> infos = categoryManageService.searchInfos(dataTablesInfo);
        turnIdToName(infos);
        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);

        return resultMap;
    }

    public void turnIdToName(List<CategoryInfo> infos) {
        if (null != infos && infos.size() > 0) {
            for (CategoryInfo info : infos) {
                String level_id = info.getLevelId();
                if (level_id.equals("1")) {
                    info.setLevelId("一级品类");
                } else if (level_id.equals("2")) {
                    info.setLevelId("二级品类");
                }
            }
        }
    }

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "编辑品类级别信息")
    public Map<String, Object> edit(CategoryInfo info, HttpServletRequest request) {
        categoryManageService.edit(info);
        CategoryManageServiceHelper.SINGLETONE.refreshSelect();

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
    @SystemControllerLog(description = "增加品类级别信息")
    public Map<String, Object> add(CategoryInfo info, HttpServletRequest request) {
        String currentNo = categoryManageService.queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_CATEGORY_INFO, currentNo, incrementNumberService));
        categoryManageService.add(info);
        CategoryManageServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("新增成功");
    }


    /**
     * @param natrualKey 主键id
     * @return 根据主键id找出详细信息
     */
    @RequestMapping(value = "/info/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "查询品类级别信息")
    public CategoryInfo info(@PathVariable String natrualKey) {
        CategoryInfo info = categoryManageService.queryInfoByNatrualKey(natrualKey);
        return info;
    }

    /**
     * @param natrualKey
     * @return
     */
    @RequestMapping(value = "/del/{natrualKey}", method = RequestMethod.DELETE)
    @ResponseBody
    @SystemControllerLog(description = "删除品类级别")
    public Map<String, Object> del(@PathVariable String natrualKey) {
        categoryManageService.del(natrualKey);
        CategoryManageServiceHelper.SINGLETONE.refreshSelect();
        return rtnSuccessResultMap("删除成功");
    }

    @RequestMapping(value = "/select", method = RequestMethod.GET)
    @ResponseBody
    @SystemControllerLog(description = "")
    public Map<String, Object> querySelectList(HttpServletRequest request) {
        String name = request.getParameter("name");
        List<SelectItem2> commonBeans = categoryManageService.querySelectList(name);
        return rtSelectResultMap(commonBeans);
    }

    /**
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/searchSecond/{categoryId}", method = RequestMethod.GET)
    @ResponseBody
    public List<CategoryInfo> searchChildCategoryByCategoryId(@PathVariable String categoryId) {

        List<CategoryInfo> categoryInfos = new ArrayList();
        if (!StringUtils.isBlank(categoryId)) {
            categoryInfos = categoryManageService.searchChildCategoryByCategoryId(categoryId);
        }
        return categoryInfos;
    }


}


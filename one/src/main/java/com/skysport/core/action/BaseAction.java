package com.skysport.core.action;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.model.common.ICommonService;
import com.skysport.core.utils.Underline2CamelUtils;
import com.skysport.interfaces.bean.form.BaseQueyrForm;
import com.skysport.interfaces.constant.WebConstants;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 此类描述的是：页面列表查询通用处理
 *
 * @author: zhangjh
 * @version: 2015年4月30日 下午4:34:05
 */
public abstract class BaseAction<T> {


    protected static String MSG_UPDATE_SUCCESS = "更新成功";

    /**
     * @param baseQueyrForm
     * @param request
     * @param commonService
     * @return
     */
    public Map<String, Object> buildSearchJsonMap(BaseQueyrForm baseQueyrForm, HttpServletRequest request, ICommonService commonService) {
        // 总记录数
        int recordsTotal = commonService.listInfosCounts();
        int recordsFiltered = recordsTotal;
        if (!StringUtils.isBlank(baseQueyrForm.getDataTablesInfo().getSearchValue())) {
            recordsFiltered = commonService.listFilteredInfosCounts(baseQueyrForm);
        }
        int draw = Integer.parseInt(request.getParameter("draw"));
        List<T> infos = commonService.searchInfos(baseQueyrForm);
        this.turnIdToName(infos);
        Map<String, Object> resultMap = buildSearchJsonMap(infos, recordsTotal, recordsFiltered, draw);
        return resultMap;
    }


    /**
     * 返回datatables需要的数据
     *
     * @param results         数据结果集
     * @param recordsTotal    总记录数
     * @param recordsFiltered 实际查询记录数
     * @param draw
     * @return <K, V> Map<K, V>
     */
    public Map<String, Object> buildSearchJsonMap(List<T> results, long recordsTotal, long recordsFiltered, int draw) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data", results);
        info.put("recordsTotal", recordsTotal);
        info.put("recordsFiltered", recordsFiltered);
        info.put("draw", draw);
        return info;
    }

    /**
     * 将页面的分页信息传到后台
     *
     * @param type    列表所属表名
     * @param request HttpServletRequest对象
     * @return 数据表格信息
     */
    public DataTablesInfo convertToDataTableQrInfo(String type, HttpServletRequest request) {
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(type, request, WebConstants.NEED_TRANSFORM_COLUMN_NEME);
        return dataTablesInfo;
    }


    /**
     * @param type                  String
     * @param request               HttpServletRequest
     * @param isNeedTransformColumn int
     * @return DataTablesInfo
     */
    public DataTablesInfo convertToDataTableQrInfo(String type, HttpServletRequest request, int isNeedTransformColumn) {

        DataTablesInfo dataTablesInfo = new DataTablesInfo();
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        int draw = Integer.parseInt(request.getParameter("draw"));
        String orderColumn = buildOrderName(type, request, isNeedTransformColumn);
        String orderDir = request.getParameter("order[0][dir]");
        String searchValue = request.getParameter("search[value]");
        dataTablesInfo.setStart(start);
        dataTablesInfo.setLength(length);
        orderColumn = Underline2CamelUtils.camel2Underline(orderColumn);
        dataTablesInfo.setOrderColumn(orderColumn);
//        if(start!=0){
//
//        }
        dataTablesInfo.setOrderDir(orderDir);
        dataTablesInfo.setSearchValue(searchValue);
        dataTablesInfo.setDraw(draw);
        StringBuilder limitAfter = new StringBuilder();
        limitAfter.append("limit ").append(start).append(CharConstant.COMMA).append(length);
        dataTablesInfo.setLimitAfter(limitAfter.toString());
        return dataTablesInfo;
    }

    /**
     * 排序
     *
     * @param type
     * @param request
     * @param isNeedTransformColumn
     * @return
     */
    private String buildOrderName(String type, HttpServletRequest request, int isNeedTransformColumn) {
        String orderName = request.getParameter("order[0][column]");

        //前台排序字段名
        String pageColumnName = request.getParameter("columns[" + orderName + "][data]");

        //查找对应的数据库字段名
        String orderColumn = pageColumnName;

        if (isNeedTransformColumn == WebConstants.NEED_TRANSFORM_COLUMN_NEME) {
            orderColumn = DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(type, pageColumnName);
        }
        return orderColumn;
    }

    /**
     * 返回结果信息
     *
     * @param message
     * @return Map<String, Object>
     */
    public Map<String, Object> rtnSuccessResultMap(String message) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code", "0");
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 返回查询列表
     *
     * @param commonBeans
     * @return Map<String, Object>
     */
    public Map<String, Object> rtSelectResultMap(List<SelectItem2> commonBeans) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("items", commonBeans);
        resultMap.put("total_count", commonBeans.size());
        return resultMap;
    }

    public void turnIdToName(List<T> infos) {

    }
}

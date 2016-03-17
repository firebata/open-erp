package com.skysport.core.action;

import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.inerfaces.constant.WebConstants;

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
public class BaseAction<K, V, T> extends BaseController<T> {


    protected static String MSG_UPDATE_SUCCESS = "更新成功";

    /**
     * 返回datatables需要的数据
     *
     * @param results         数据结果集
     * @param recordsTotal    总记录数
     * @param recordsFiltered 实际查询记录数
     * @param draw
     * @param <K>             Key
     * @param <V>             值
     * @return <K, V> Map<K, V>
     */
    public <K, V> Map<K, V> buildSearchJsonMap(List<T> results, int recordsTotal, int recordsFiltered, int draw) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("data", results);
        info.put("recordsTotal", recordsTotal);
        info.put("recordsFiltered", recordsFiltered);
        info.put("draw", draw);
        return (Map<K, V>) info;
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

    public DataTablesInfo buildQueryDataTableInfo(HttpServletRequest request) {
        DataTablesInfo dataTablesInfo = convertToDataTableQrInfo(null, request, WebConstants.NO_NEED_TRANSFORM_COLUMN_NAME);
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
        String orderColumn = request.getParameter("order[0][column]");

        //前台排序字段名
        String pageColumnName = request.getParameter("columns[" + orderColumn + "][data]");

        //查找对应的数据库字段名
        String tableColumnName = pageColumnName;

        if (isNeedTransformColumn == WebConstants.NEED_TRANSFORM_COLUMN_NEME) {
            tableColumnName = DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(type, pageColumnName);
        }

        String orderDir = request.getParameter("order[0][dir]");
        String searchValue = request.getParameter("search[value]");

        dataTablesInfo.setStart(start);
        dataTablesInfo.setLength(length);
        dataTablesInfo.setOrderColumn(tableColumnName);
        dataTablesInfo.setOrderDir(orderDir);
        dataTablesInfo.setSearchValue(searchValue);
        dataTablesInfo.setStart(start);
        dataTablesInfo.setDraw(draw);

        return dataTablesInfo;
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
}

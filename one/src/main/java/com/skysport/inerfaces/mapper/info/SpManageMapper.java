package com.skysport.inerfaces.mapper.info;

import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.SpInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhangjh
 * @version:2015年5月5日 下午5:43:26
 */
@Component("spManageDao")
public interface SpManageMapper {
    /**
     * @param spId
     * @return
     */
    public SpInfo querySpInfo(String spId);

    /**
     * 查询供应商的总记录数
     *
     * @return 供应商的总记录数
     */
    public int listSPInfosCounts();

    /**
     * 查询含有过滤条件的供应商的总记录数
     *
     * @param dataTablesInfo DataTablesInfo
     * @return 含有过滤条件的供应商的总记录数
     */
    public int listFilteredSPInfosCounts(DataTablesInfo dataTablesInfo);

    /**
     * 按照过滤条件查询供应商信息
     *
     * @param dataTablesInfo
     * @return 供应商信息
     */
    public List<SpInfo> searchSP(DataTablesInfo dataTablesInfo);

    public void updateSp(SpInfo spInfo);

    public void add(SpInfo spInfo);

    public void del(String spId);

    public List<SelectItem2> querySelectList(@Param(value = "name") String name);
}

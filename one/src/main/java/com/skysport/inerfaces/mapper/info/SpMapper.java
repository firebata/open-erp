package com.skysport.inerfaces.mapper.info;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.SpInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: zhangjh
 * @version:2015年5月5日 下午5:43:26
 */
@Repository
public interface SpMapper {
    /**
     * @param spId
     * @return
     */
     SpInfo querySpInfo(String spId);

    /**
     * 查询供应商的总记录数
     *
     * @return 供应商的总记录数
     */
     int listSPInfosCounts();

    /**
     * 查询含有过滤条件的供应商的总记录数
     *
     * @param dataTablesInfo DataTablesInfo
     * @return 含有过滤条件的供应商的总记录数
     */
     int listFilteredSPInfosCounts(DataTablesInfo dataTablesInfo);

    /**
     * 按照过滤条件查询供应商信息
     *
     * @param dataTablesInfo
     * @return 供应商信息
     */
     List<SpInfo> searchSP(DataTablesInfo dataTablesInfo);

     void updateSp(SpInfo spInfo);

     void add(SpInfo spInfo);

     void del(String spId);

     List<SelectItem2> querySelectList(@Param(value = "name") String name);
}

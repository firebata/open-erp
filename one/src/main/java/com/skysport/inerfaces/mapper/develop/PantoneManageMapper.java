package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.PantoneInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/4.
 */
@Component("pantoneManageDao")
public interface PantoneManageMapper {

    /**
     * @param pantonId
     * @return
     */
    public PantoneInfo queryInfo(String pantonId);

    /**
     * 查询潘通色的总记录数
     *
     * @return 潘通色的总记录数
     */
    public int listInfosCounts();

    /**
     * 查询含有过滤条件的潘通色的总记录数
     *
     * @param dataTablesInfo DataTablesInfo
     * @return 含有过滤条件的潘通色的总记录数
     */
    public int listFilteredInfosCounts(DataTablesInfo dataTablesInfo);

    /**
     * 按照过滤条件查询潘通色信息
     *
     * @param dataTablesInfo
     * @return 潘通色信息
     */
    public List<PantoneInfo> searchInfos(DataTablesInfo dataTablesInfo);

    /**
     * 更新特定的潘通信息
     *
     * @param pantoneInfo
     */
    public void updateInfo(PantoneInfo pantoneInfo);

    /**
     * 增加潘通色
     *
     * @param pantoneInfo
     */
    public void add(PantoneInfo pantoneInfo);

    /**
     * 删除潘通色
     *
     * @param pantoneId 潘通色Id
     */
    void del(String pantoneId);

    List<SelectItem2> querySelectList(@Param(value = "name") String name);
}

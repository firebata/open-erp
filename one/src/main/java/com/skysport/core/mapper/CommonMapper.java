package com.skysport.core.mapper;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.form.BaseQueyrForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/8.
 */
public interface CommonMapper<T> {

    int listInfosCounts();

    int listFilteredInfosCounts(DataTablesInfo dataTablesInfo);

    List<T> searchInfos(DataTablesInfo dataTablesInfo);

    void updateInfo(T t);

    <T> T queryInfo(String natrualKey);

    void add(T t);

    void del(String natrualKey);

    String queryCurrentSeqNo();

    List<SelectItem2> querySelectList(@Param(value = "name") String name);

    void addBatch(List<T> infos);

    void updateBatch(List<T> infos);

    List<SelectItem2> querySelectListByParentId(@Param(value = "parentId") String parentId);

    <T> List<T> searchInfos(BaseQueyrForm baseQueyrForm);

    int listFilteredInfosCounts(BaseQueyrForm baseQueyrForm);

    String queryBusinessName(@Param(value = "businessKey")String businessKey);
}

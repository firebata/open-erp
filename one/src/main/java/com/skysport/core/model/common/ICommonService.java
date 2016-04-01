package com.skysport.core.model.common;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.form.BaseQueyrForm;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/8.
 */
public interface ICommonService<T> {

    /**
     * @return
     */
    int listInfosCounts();

    /**
     * 过滤条件的记录数
     *
     * @param dataTablesInfo
     * @return 符合查询条件的pantone记录数
     */
    int listFilteredInfosCounts(DataTablesInfo dataTablesInfo);

    /**
     * @param baseQueyrForm
     * @return
     */
    int listFilteredInfosCounts(BaseQueyrForm baseQueyrForm);

    /**
     * @param dataTablesInfo
     * @return
     */
    List<T> searchInfos(DataTablesInfo dataTablesInfo);

    /**
     * @param baseQueyrForm
     * @return
     */
    List<T> searchInfos(BaseQueyrForm baseQueyrForm);

    /**
     * @param t
     */
    void edit(T t);

    /**
     * @param natrualkey
     * @return 根据natrualkey找出供应商信息
     */
    <T> T queryInfoByNatrualKey(String natrualkey);

    void add(T t);

    void del(String natrualkey);

    String queryCurrentSeqNo();

    List<SelectItem2> querySelectList(String name);

    void addBatch(List<T> infos);

    void updateBatch(List<T> infos);


    List<SelectItem2> querySelectListByParentId(String keyword);
}

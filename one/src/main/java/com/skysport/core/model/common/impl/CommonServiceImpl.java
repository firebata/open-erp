package com.skysport.core.model.common.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.mapper.CommonMapper;
import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.bean.form.BaseQueyrForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/8.
 */
public abstract class CommonServiceImpl<T> implements ICommonService<T> {

    protected transient Log logger = LogFactory.getLog(getClass());
    public CommonMapper<T> commonMapper;



    @Override
    public int listInfosCounts() {
        return commonMapper.listInfosCounts();
    }

    @Override
    public int listFilteredInfosCounts(DataTablesInfo dataTablesInfo) {
        return commonMapper.listFilteredInfosCounts(dataTablesInfo);
    }

    /**
     * @param baseQueyrForm
     * @return
     */
    public int listFilteredInfosCounts(BaseQueyrForm baseQueyrForm) {
        return commonMapper.listFilteredInfosCounts(baseQueyrForm);
    }

    /**
     * @param baseQueyrForm
     * @return
     */
    @Override
    public List<T> searchInfos(BaseQueyrForm baseQueyrForm) {
        return commonMapper.searchInfos(baseQueyrForm);
    }

    @Override
    public List<T> searchInfos(DataTablesInfo dataTablesInfo) {
        if (dataTablesInfo == null) {
            dataTablesInfo = new DataTablesInfo();
            dataTablesInfo.setStart(0);
            dataTablesInfo.setLength(10);
        }
        return commonMapper.searchInfos(dataTablesInfo);
    }

    @Override
    public void edit(T t){
        commonMapper.updateInfo(t);
    }

    @Override
    public void add(T t) {
        commonMapper.add(t);
    }

    @Override
    public T queryInfoByNatrualKey(String natrualKey) {
        return commonMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        commonMapper.del(natrualKey);
    }

    @Override
    public String queryCurrentSeqNo() {
        return commonMapper.queryCurrentSeqNo();
    }

    @Override
    public List<SelectItem2> querySelectList(String name) {
        return commonMapper.querySelectList(name);
    }

    @Override
    public void addBatch(List<T> infos) {
        commonMapper.addBatch(infos);
    }

    @Override
    public void updateBatch(List<T> infos) {
        commonMapper.updateBatch(infos);
    }

    @Override
    public List<SelectItem2> querySelectListByParentId(String parentId) {
        return commonMapper.querySelectListByParentId(parentId);
    }
}

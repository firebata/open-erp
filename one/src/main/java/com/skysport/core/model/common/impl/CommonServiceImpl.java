package com.skysport.core.model.common.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.mapper.CommonDao;
import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.form.BaseQueyrForm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/8.
 */
public abstract class CommonServiceImpl<T> implements ICommonService<T> {

    protected transient Log logger = LogFactory.getLog(getClass());
    public CommonDao<T> commonDao;



    @Override
    public int listInfosCounts() {
        return commonDao.listInfosCounts();
    }

    @Override
    public int listFilteredInfosCounts(DataTablesInfo dataTablesInfo) {
        return commonDao.listFilteredInfosCounts(dataTablesInfo);
    }

    /**
     * @param baseQueyrForm
     * @return
     */
    public int listFilteredInfosCounts(BaseQueyrForm baseQueyrForm) {
        return commonDao.listFilteredInfosCounts(baseQueyrForm);
    }

    /**
     * @param baseQueyrForm
     * @return
     */
    @Override
    public List<T> searchInfos(BaseQueyrForm baseQueyrForm) {
        return commonDao.searchInfos(baseQueyrForm);
    }

    @Override
    public List<T> searchInfos(DataTablesInfo dataTablesInfo) {
        if (dataTablesInfo == null) {
            dataTablesInfo = new DataTablesInfo();
            dataTablesInfo.setStart(0);
            dataTablesInfo.setLength(10);
        }
        return commonDao.searchInfos(dataTablesInfo);
    }

    @Override
    public void edit(T t) {
        commonDao.updateInfo(t);
    }

    @Override
    public void add(T t) {
        commonDao.add(t);
    }

    @Override
    public T queryInfoByNatrualKey(String natrualKey) {
        return commonDao.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        commonDao.del(natrualKey);
    }

    @Override
    public String queryCurrentSeqNo() {
        return commonDao.queryCurrentSeqNo();
    }

    @Override
    public List<SelectItem2> querySelectList(String name) {
        return commonDao.querySelectList(name);
    }

    @Override
    public void addBatch(List<T> infos) {
        commonDao.addBatch(infos);
    }

    @Override
    public void updateBatch(List<T> infos) {
        commonDao.updateBatch(infos);
    }

    @Override
    public List<SelectItem2> querySelectListByParentId(String parentId) {
        return commonDao.querySelectListByParentId(parentId);
    }
}

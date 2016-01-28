package com.skysport.core.model.common.impl;

import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.mapper.CommonDao;
import com.skysport.core.model.common.ICommonService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/8.
 */
public class CommonServiceImpl<T> implements ICommonService<T> {
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

package com.skysport.inerfaces.model.info.sp.service.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.SpInfo;
import com.skysport.inerfaces.mapper.info.SpManageMapper;
import com.skysport.inerfaces.model.info.sp.service.ISpManageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 此类描述的是：
 *
 * @author: zhangjh
 * @version: 2015年4月29日 下午5:39:59
 */
@Service("spManageService")
public class SpManageServiceImpl implements ISpManageService {

    @Autowired
    private SpManageMapper spManageMapper;

    /**
     * 查询spinfo的总记录数
     */
    @Override
    public int listSPInfosCounts() {
        return spManageMapper.listSPInfosCounts();
    }

    /**
     * 按条件查询spinfo的所有信息
     */
    @Override
    public int listFilteredSPInfosCounts(DataTablesInfo dataTablesInfo) {
        // // 获取请求次数
        // int draw = 0;
        // draw = dataTablesInfo.getDraw();
        // // 数据起始位置
        // int start = dataTablesInfo.getStart();
        // // 数据长度
        // int length = dataTablesInfo.getLength();

        return spManageMapper.listFilteredSPInfosCounts(dataTablesInfo);
    }

    /**
     * 查询spinfo的所有信息
     */
    @Override
    public List<SpInfo> searchSP(DataTablesInfo dataTablesInfo) {
        return spManageMapper.searchSP(dataTablesInfo);
    }

    /**
     *
     */
    @Override
    public void edit(SpInfo spInfo) {
        spManageMapper.updateSp(spInfo);
    }

    @Override
    public SpInfo querySpInfoBySpId(@Param(value = "spId") String spId) {
        return spManageMapper.querySpInfo(spId);
    }

    @Override
    public void add(SpInfo spInfo) {
        spManageMapper.add(spInfo);
    }

    @Override
    public void del(String spId) {
        spManageMapper.del(spId);
    }

    @Override
    public List<SelectItem2> querySelectList(String name) {
        return spManageMapper.querySelectList(name);
    }


}

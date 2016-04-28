package com.skysport.inerfaces.model.info.sp.service.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.SpInfo;
import com.skysport.inerfaces.mapper.info.SpMapper;
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
    private SpMapper spMapper;

    /**
     * 查询spinfo的总记录数
     */
    @Override
    public int listSPInfosCounts() {
        return spMapper.listSPInfosCounts();
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

        return spMapper.listFilteredSPInfosCounts(dataTablesInfo);
    }

    /**
     * 查询spinfo的所有信息
     */
    @Override
    public List<SpInfo> searchSP(DataTablesInfo dataTablesInfo) {
        return spMapper.searchSP(dataTablesInfo);
    }

    /**
     *
     */
    @Override
    public void edit(SpInfo spInfo) {
        spMapper.updateSp(spInfo);
    }

    @Override
    public SpInfo querySpInfoBySpId(@Param(value = "spId") String spId) {
        return spMapper.querySpInfo(spId);
    }

    @Override
    public void add(SpInfo spInfo) {
        spMapper.add(spInfo);
    }

    @Override
    public void del(String spId) {
        spMapper.del(spId);
    }

    @Override
    public List<SelectItem2> querySelectList(String name) {
        return spMapper.querySelectList(name);
    }


}

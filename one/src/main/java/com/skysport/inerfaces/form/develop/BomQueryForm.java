package com.skysport.inerfaces.form.develop;

import com.skysport.core.bean.query.DataTablesInfo;
import com.skysport.inerfaces.bean.develop.BomInfo;

/**
 *
 * 类说明:Bom查询表单
 *
 * Created by zhangjh on 2015/7/13.
 */
public class BomQueryForm{
    private DataTablesInfo dataTablesInfo;
    private BomInfo bomInfo;
    private String starDate;
    private String endDate;

    public BomInfo getBomInfo() {
        return bomInfo;
    }

    public void setBomInfo(BomInfo bomInfo) {
        this.bomInfo = bomInfo;
    }

    public String getStarDate() {
        return starDate;
    }

    public void setStarDate(String starDate) {
        this.starDate = starDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public DataTablesInfo getDataTablesInfo() {
        return dataTablesInfo;
    }

    public void setDataTablesInfo(DataTablesInfo dataTablesInfo) {
        this.dataTablesInfo = dataTablesInfo;
    }

    public BomQueryForm() {
    }
}

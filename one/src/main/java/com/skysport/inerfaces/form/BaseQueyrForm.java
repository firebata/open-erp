package com.skysport.inerfaces.form;

import com.skysport.core.bean.page.DataTablesInfo;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public class BaseQueyrForm {

    private String starDate;
    private String endDate;

    private DataTablesInfo dataTablesInfo;

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
}

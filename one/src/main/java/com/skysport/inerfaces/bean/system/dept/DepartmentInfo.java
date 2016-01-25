package com.skysport.inerfaces.bean.system.dept;

import com.skysport.core.bean.system.SelectItem;

/**
 * 说明:
 * Created by zhangjh on 2015/12/30.
 */
public class DepartmentInfo extends SelectItem {

    private String id;
    private String deptId;
    private String deptName;
    private String parentId;
    private String deptAdmin;
    private String deptAcminCandidate;
    private String bussLicen;
    private String cityCode;
    private String countryCode;
    private int delFlag;
    private String updateTime;
    private String remark;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeptAdmin() {
        return deptAdmin;
    }

    public void setDeptAdmin(String deptAdmin) {
        this.deptAdmin = deptAdmin;
    }

    public String getDeptAcminCandidate() {
        return deptAcminCandidate;
    }

    public void setDeptAcminCandidate(String deptAcminCandidate) {
        this.deptAcminCandidate = deptAcminCandidate;
    }

    public String getBussLicen() {
        return bussLicen;
    }

    public void setBussLicen(String bussLicen) {
        this.bussLicen = bussLicen;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public int getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}

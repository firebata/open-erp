package com.skysport.interfaces.bean.develop;

import com.skysport.core.bean.CommonVo;

/**
 * 类说明:面料详细
 * Created by zhangjh on 2015/7/2.
 */
public class FabricsDetailInfo implements CommonVo {
    private String id;
    private String fabricId;
    private String specificationId;
    private String dyeId;
    private String finishId;
    private String blcId;
    private String momcId;
    private String comocId;
    private String wvpId;
    private String mtId;
    private String woblcId;
    private String remark;
    private String updateTime;
    private int delFlag;
    /**
     * 纱支规格
     */
    private String compositeSpecificationId;
    /**
     * 染色方式
     */
    private String compositeDyeId;
    /**
     * 防泼水
     */
    private String compositeFinishId;


    public FabricsDetailInfo() {
        super();
    }

    public String getCompositeSpecificationId() {
        return compositeSpecificationId;
    }

    public void setCompositeSpecificationId(String compositeSpecificationId) {
        this.compositeSpecificationId = compositeSpecificationId;
    }

    public String getCompositeDyeId() {
        return compositeDyeId;
    }

    public void setCompositeDyeId(String compositeDyeId) {
        this.compositeDyeId = compositeDyeId;
    }

    public String getCompositeFinishId() {
        return compositeFinishId;
    }

    public void setCompositeFinishId(String compositeFinishId) {
        this.compositeFinishId = compositeFinishId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
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
    public int getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getFabricId() {
        return fabricId;
    }

    public void setFabricId(String fabricId) {
        this.fabricId = fabricId;
    }

    public String getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId;
    }

    public String getDyeId() {
        return dyeId;
    }

    public void setDyeId(String dyeId) {
        this.dyeId = dyeId;
    }

    public String getFinishId() {
        return finishId;
    }

    public void setFinishId(String finishId) {
        this.finishId = finishId;
    }

    public String getBlcId() {
        return blcId;
    }

    public void setBlcId(String blcId) {
        this.blcId = blcId;
    }

    public String getMomcId() {
        return momcId;
    }

    public void setMomcId(String momcId) {
        this.momcId = momcId;
    }

    public String getComocId() {
        return comocId;
    }

    public void setComocId(String comocId) {
        this.comocId = comocId;
    }

    public String getWvpId() {
        return wvpId;
    }

    public void setWvpId(String wvpId) {
        this.wvpId = wvpId;
    }

    public String getMtId() {
        return mtId;
    }

    public void setMtId(String mtId) {
        this.mtId = mtId;
    }

    public String getWoblcId() {
        return woblcId;
    }

    public void setWoblcId(String woblcId) {
        this.woblcId = woblcId;
    }
}

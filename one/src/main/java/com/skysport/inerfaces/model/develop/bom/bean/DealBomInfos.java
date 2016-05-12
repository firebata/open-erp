package com.skysport.inerfaces.model.develop.bom.bean;

import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.develop.SexColor;
import com.skysport.inerfaces.model.develop.bom.helper.BomHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016-05-12.
 */
public class DealBomInfos {

    private ProjectBomInfo info;
    private List<BomInfo> needDelBomList;
    private List<BomInfo> needAddBomList;
    private List<BomInfo> alls;
    private List<BomInfo> needUpdateBomList;
    private List<String>  bomsNeedToStart;

    public void setInfo(ProjectBomInfo info) {
        this.info = info;
    }

    public void setNeedDelBomList(List<BomInfo> needDelBomList) {
        this.needDelBomList = needDelBomList;
    }

    public void setNeedAddBomList(List<BomInfo> needAddBomList) {
        this.needAddBomList = needAddBomList;
    }

    public void setAlls(List<BomInfo> alls) {
        this.alls = alls;
    }

    public List<String> getBomsNeedToStart() {
        return bomsNeedToStart;
    }

    public void setBomsNeedToStart(List<String> bomsNeedToStart) {
        this.bomsNeedToStart = bomsNeedToStart;
    }

    public DealBomInfos(ProjectBomInfo info) {
        this.info = info;
    }

    public List<BomInfo> getNeedDelBomList() {
        return needDelBomList;
    }

    public List<BomInfo> getNeedAddBomList() {
        return needAddBomList;
    }

    public List<BomInfo> getAlls() {
        return alls;
    }

    public ProjectBomInfo getInfo() {
        return info;
    }

    public List<BomInfo> getNeedUpdateBomList() {
        return needUpdateBomList;
    }

    public void setNeedUpdateBomList(List<BomInfo> needUpdateBomList) {
        this.needUpdateBomList = needUpdateBomList;
    }

    public DealBomInfos invoke(List<BomInfo> allStyles ) {
        List<SexColor> sexColors = info.getSexColors();
        String projectId = info.getNatrualkey();
        String customerId = info.getCustomerId();
        String areaId = info.getAreaId();
        String seriesId = info.getSeriesId();

        //获取需要更新的bom列表
        //交集
        needUpdateBomList = BomHelper.getInstance().getIntersection(sexColors, allStyles);

        //获取需要删除的bom列表
        needDelBomList = BomHelper.getInstance().getNeedDelBomList(needUpdateBomList, allStyles);

        //需要增加的bom列表
        needAddBomList = BomHelper.getInstance().getNeedAddBomList(needUpdateBomList, sexColors, info, projectId, customerId, areaId, seriesId);

        alls = new ArrayList<>();
        alls.addAll(needUpdateBomList);
        alls.addAll(needAddBomList);

        return this;
    }
}

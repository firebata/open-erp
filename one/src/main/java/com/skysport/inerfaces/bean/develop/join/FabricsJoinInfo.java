package com.skysport.inerfaces.bean.develop.join;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.develop.FabricsDetailInfo;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.MaterialSpInfo;
import com.skysport.inerfaces.bean.develop.MaterialUnitDosage;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/24.
 */
public class FabricsJoinInfo extends SelectItem2 {

    private FabricsInfo fabricsInfo;
    private FabricsDetailInfo fabricsDetailInfo;
    private MaterialSpInfo materialSpInfo;
    private MaterialUnitDosage materialUnitDosage;

    public FabricsInfo getFabricsInfo() {
        return fabricsInfo;
    }

    public void setFabricsInfo(FabricsInfo fabricsInfo) {
        this.fabricsInfo = fabricsInfo;
    }

    public FabricsDetailInfo getFabricsDetailInfo() {
        return fabricsDetailInfo;
    }

    public void setFabricsDetailInfo(FabricsDetailInfo fabricsDetailInfo) {
        this.fabricsDetailInfo = fabricsDetailInfo;
    }

    public MaterialSpInfo getMaterialSpInfo() {
        return materialSpInfo;
    }

    public void setMaterialSpInfo(MaterialSpInfo materialSpInfo) {
        this.materialSpInfo = materialSpInfo;
    }

    public MaterialUnitDosage getMaterialUnitDosage() {
        return materialUnitDosage;
    }

    public void setMaterialUnitDosage(MaterialUnitDosage materialUnitDosage) {
        this.materialUnitDosage = materialUnitDosage;
    }
}

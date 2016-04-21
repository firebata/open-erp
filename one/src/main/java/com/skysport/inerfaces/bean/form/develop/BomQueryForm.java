package com.skysport.inerfaces.bean.form.develop;

import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.form.BaseQueyrForm;

/**
 * 类说明:Bom查询表单
 * <p>
 * Created by zhangjh on 2015/7/13.
 */
public class BomQueryForm extends BaseQueyrForm {
    private BomInfo bomInfo;

    public BomInfo getBomInfo() {
        return bomInfo;
    }

    public void setBomInfo(BomInfo bomInfo) {
        this.bomInfo = bomInfo;
    }
}

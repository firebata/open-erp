package com.skysport.inerfaces.model.info.sp.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.bean.info.SpInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.utils.SelectInfoHelper;
import com.skysport.inerfaces.model.info.sp.service.impl.SpManageServiceImpl;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/1.
 */
public enum SpInfoHelper {
    /**
     *
     */
    SINGLETONE;

    public void refreshSelect() {
        SpManageServiceImpl spManageService = SpringContextHolder.getBean("spManageService");
        List<SelectItem2> spItems = spManageService.querySelectList(null);
        SystemBaseInfo.SINGLETONE.pushBom("spItems", spItems);
    }

    /**
     * 将材料类型id转换成材料名称，存放在id字段
     *
     * @param spInfos
     */
    public void turnMaterialTypeIdToName(List<SpInfo> spInfos) {
        if (null != spInfos) {
            List<SelectItem2> selectItems = SelectInfoHelper.findSelectItemsByItemsName(WebConstants.MATERIALTYPEITEMS);
            for (SpInfo spInfo : spInfos) {
                String typeId = spInfo.getType();
                for (SelectItem2 item : selectItems) {
                    String id = item.getNatrualkey();
                    if (id.equals(typeId)) {
                        spInfo.setType(item.getName());
                        break;
                    }
                }
            }
        }
    }
}

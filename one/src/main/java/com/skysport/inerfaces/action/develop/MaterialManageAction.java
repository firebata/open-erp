package com.skysport.inerfaces.action.develop;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.inerfaces.bean.develop.MaterialInfo;
import com.skysport.inerfaces.model.info.material.IMaterialManageService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 物料管理
 * Created by zhangjh on 2015/6/23.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/material")
public class MaterialManageAction extends BaseAction<MaterialInfo> {
    @Resource(name = "materialManageService")
    private IMaterialManageService materialManageService;

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "保存面料信息")
    public MaterialInfo saveFabricFun(MaterialInfo info) {
        MaterialInfo info2 = materialManageService.saveFabricFun(info);

        return info2;
    }


}

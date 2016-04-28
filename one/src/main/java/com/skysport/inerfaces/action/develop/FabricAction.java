package com.skysport.inerfaces.action.develop;

import com.skysport.core.action.BaseAction;
import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.model.develop.bom.IBomService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/9/7.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/fabrice")
public class FabricAction extends BaseAction<FabricsInfo> {

    @Resource(name = "bomManageService")
    private IBomService bomManageService;

    /**
     * 此方法描述的是：
     *
     * @author: zhangjh
     * @version: 2015年4月29日 下午5:35:09
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    @SystemControllerLog(description = "编辑面料信息")
    public Map<String, Object> edit(@RequestBody BomInfo info) {
        info.setBomId(info.getNatrualkey());
        bomManageService.edit(info);
        Map resultMap = rtnSuccessResultMap(MSG_UPDATE_SUCCESS);
        return resultMap;
    }

}

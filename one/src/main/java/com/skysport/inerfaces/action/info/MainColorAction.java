package com.skysport.inerfaces.action.info;

import com.skysport.inerfaces.bean.info.MainColor;
import com.skysport.inerfaces.model.info.main_color.IMainColorService;
import com.skysport.inerfaces.model.info.main_color.helper.MainColorHelper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/15.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/maincolor")
public class MainColorAction {

    @Resource(name = "mainColorService")
    private IMainColorService mainColorService;

    @RequestMapping(value = "/info/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    public String[] queryMainColor(@PathVariable String projectId) {
        List<MainColor> mainColorList = mainColorService.queryMainColorList(projectId);
        return MainColorHelper.SINGLETONE.turnListToArr(mainColorList);

    }
}

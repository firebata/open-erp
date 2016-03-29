package com.skysport.inerfaces.action.info;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/15.
 */
@Scope("prototype")
@Controller
@RequestMapping("/system/maincolor")
public class MainColorAction {

//    @Resource(name = "mainColorService")
//    private IMainColorService mainColorService;
//
//    @RequestMapping(value = "/info/{projectId}", method = RequestMethod.GET)
//    @ResponseBody
//    public String[] queryMainColor(@PathVariable String projectId) {
//        List<MainColor> mainColorList = mainColorService.queryMainColorList(projectId);
//        return MainColorHelper.SINGLETONE.turnListToArr(mainColorList);
//
//    }
}

package com.skysport.inerfaces.action.develop;

import com.skysport.inerfaces.bean.develop.ProjectCategoryInfo;
import com.skysport.inerfaces.model.develop.project.service.IProjectCategoryManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/8/31.
 */
@Scope("prototype")
@Controller
@RequestMapping("/development/project-category")
public class ProjectCategoryAction {

    @Resource(name = "projectCategoryManageService")
    private IProjectCategoryManageService projectCategoryManageService;

    @RequestMapping(value = "/infoCategory/{natrualKey}", method = RequestMethod.GET)
    @ResponseBody
    public List<ProjectCategoryInfo> queryProjectCategoryInfo(@PathVariable String natrualKey) {

        List<ProjectCategoryInfo> categoryInfos = new ArrayList<>();
        if (StringUtils.isNotBlank(natrualKey) && !"null".equals(natrualKey)) {
            categoryInfos = projectCategoryManageService.queryProjectCategoryInfo(natrualKey);
        }
        return categoryInfos;
    }


}

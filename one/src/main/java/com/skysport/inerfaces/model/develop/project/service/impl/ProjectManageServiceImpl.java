package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.core.model.workflow.InstanceService;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.develop.ProjectInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.form.develop.ProjectQueryForm;
import com.skysport.inerfaces.mapper.develop.ProjectManageMapper;
import com.skysport.inerfaces.model.develop.project.helper.ProjectManageHelper;
import com.skysport.inerfaces.model.develop.project.service.IProjectCategoryManageService;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemManageService;
import com.skysport.inerfaces.model.develop.project.service.IProjectManageService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Service("projectManageService")
public class ProjectManageServiceImpl extends CommonServiceImpl<ProjectInfo> implements IProjectManageService, InitializingBean {

    @Resource(name = "projectManageMapper")
    private ProjectManageMapper projectManageMapper;

    @Resource(name = "projectCategoryManageService")
    private IProjectCategoryManageService projectCategoryManageService;

    @Resource(name = "projectItemManageService")
    private IProjectItemManageService projectItemManageService;

    @Resource(name = "incrementNumber")
    private IncrementNumber incrementNumber;
    @Resource(name = "devlopmentInstanceServiceImpl")
    private InstanceService devlopmentInstanceServiceImpl;

    @Override
    public void afterPropertiesSet() {
        commonDao = projectManageMapper;
    }

    @Override
    public String queryCurrentSeqNo(ProjectInfo info) {
        return projectManageMapper.queryCurrentSeqNo(info);
    }

    @Override
    public void add(ProjectInfo info) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        UserInfo userInfo = (UserInfo) session.getAttribute(WebConstants.CURRENT_USER);

        //新增项目时组装项目名等信息
        info = ProjectManageHelper.buildProjectInfo(incrementNumber, info);
        info.setCreater(userInfo.getAliases());
        //组装项目品类信息
        info = ProjectManageHelper.buildProjectCategoryInfo(info);

        //大项目新增
        //增加主项目信息
        super.add(info);

        //增加项目的品类信息
        projectCategoryManageService.addBatch(info.getCategoryInfos());
        List<ProjectBomInfo> projectBomInfos = ProjectManageHelper.buildProjectBomInfosByProjectInfo(info, userInfo);

        //增加子项目
        projectItemManageService.addBatch(projectBomInfos);
        projectItemManageService.addBatchBomInfo(projectBomInfos);

        //启动流程
        startWorkFlow(info, userInfo);

    }

    /**
     * 启动开发流程
     *
     * @param info
     * @param userInfo
     */
    private void startWorkFlow(ProjectInfo info, UserInfo userInfo) {
        String projectId = info.getNatrualkey() == null ? info.getProjectId() : info.getNatrualkey();

        String userId = userInfo.getNatrualkey();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("projectId", projectId);
        variables.put("userId", userId);
        devlopmentInstanceServiceImpl.startProcessInstanceByKey(WebConstants.WL_PROCESS_NAME, variables);
    }

    /**
     * 项目编号是由年份+客户+地域+系列+NNN构成，但是上面的信息可能会更改，如果按照这个这个规则，项目编号应该要更改才对，但目前的处理方式是，项目编号和序号都不改变
     *
     * @param info
     */
    @Override
    public void edit(ProjectInfo info) {

//        ProjectInfo infoInDb = super.queryInfoByNatrualKey(info.getNatrualkey());

//        if (infoInDb.getStatus() == WebConstants.PROJECT_CANOT_EDIT) {
//            throw new SkySportException(DevelopmentReturnConstant.PROJECT_CANNOT_EDIT.getCode(), DevelopmentReturnConstant.PROJECT_CANNOT_EDIT.getMsg());
//        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户
        UserInfo userInfo = (UserInfo) session.getAttribute(WebConstants.CURRENT_USER);
//        UserInfo userInfo = (UserInfo) BaseController.requestThreadLocal.get().getSession().getAttribute(WebConstants.CURRENT_USER);
        //判断bom有没有生成，如果bom已生成，不能修改项目信息
//        if(){
//            throw new SkySportException("100001","bom已生成，不能修改项目信息");
//        }

        info = ProjectManageHelper.buildProjectInfo(incrementNumber, info);

        //更新t_project表
        super.edit(info);

        //删除项目相关的所有信息
        projectManageMapper.delInfoAboutProject(info.getNatrualkey());

        info = ProjectManageHelper.buildProjectCategoryInfo(info);
        //增加项目的品类信息
        projectCategoryManageService.addBatch(info.getCategoryInfos());

        List<ProjectBomInfo> projectBomInfos = ProjectManageHelper.buildProjectBomInfosByProjectInfo(info, userInfo);

        //增加子项目
        projectItemManageService.addBatch(projectBomInfos);

        projectItemManageService.addBatchBomInfo(projectBomInfos);
    }

    @Override
    public void addBomInfo(ProjectInfo info) {
        projectManageMapper.addBomInfo(info);
    }

    @Override
    public void updateBomInfo(ProjectInfo info) {
        projectManageMapper.updateBomInfo(info);
    }

    @Override
    public int listFilteredInfosCounts(ProjectQueryForm queryForm) {
        return projectManageMapper.listFilteredInfosCounts(queryForm);
    }

    @Override
    public List<ProjectInfo> searchInfos(ProjectQueryForm queryForm) {
        return projectManageMapper.searchInfos(queryForm);
    }

    @Override
    public void updateProjectStatus(String projectId, int status) {
        projectManageMapper.updateProjectStatus(projectId, status);
    }


}

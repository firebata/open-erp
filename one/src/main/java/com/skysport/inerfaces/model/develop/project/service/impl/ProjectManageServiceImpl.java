package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import com.skysport.inerfaces.bean.develop.ProjectBomInfo;
import com.skysport.inerfaces.bean.develop.ProjectCategoryInfo;
import com.skysport.inerfaces.bean.develop.ProjectInfo;
import com.skysport.inerfaces.bean.relation.ProjectItemProjectIdVo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.form.BaseQueyrForm;
import com.skysport.inerfaces.form.develop.ProjectQueryForm;
import com.skysport.inerfaces.mapper.develop.ProjectManageMapper;
import com.skysport.inerfaces.model.common.uploadfile.IUploadFileInfoService;
import com.skysport.inerfaces.model.common.uploadfile.helper.UploadFileHelper;
import com.skysport.inerfaces.model.develop.project.helper.ProjectManageHelper;
import com.skysport.inerfaces.model.develop.project.service.IProjectCategoryManageService;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemManageService;
import com.skysport.inerfaces.model.develop.project.service.IProjectManageService;
import com.skysport.inerfaces.model.relation.IRelationIdDealService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private IncrementNumberService incrementNumberService;


    @Resource(name = "uploadFileInfoService")
    private IUploadFileInfoService uploadFileInfoService;

    @Autowired
    private IRelationIdDealService projectItemProjectServiceImpl;

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

        UserInfo userInfo = UserUtils.getUserFromSession();
        String aliases = userInfo.getAliases();
        List<UploadFileInfo> fileInfos = info.getFileInfos();
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, info.getNatrualkey(), uploadFileInfoService, WebConstants.FILE_KIND_PROJECT);
        String projectId = info.getNatrualkey();
        List<ProjectCategoryInfo> categoryInfosInDB = queryCategoryInfosInDB(projectId);
        //新增项目时组装项目名等信息
        ProjectManageHelper.SINGLETONE.buildProjectInfo(info);
        info.setCreater(aliases);

        //组装项目品类信息
        info = ProjectManageHelper.SINGLETONE.buildProjectCategoryInfo(info);

        //增加主项目信息
        super.add(info);

        //增加项目的品类信息
        addBatchCategoryInfos(info.getCategoryInfos());

        //增加子项目
        List<ProjectBomInfo> projectBomInfos = ProjectManageHelper.SINGLETONE.buildProjectBomInfosByProjectInfo(info, aliases);

        //增加项目和子项目的关系
        List<ProjectItemProjectIdVo> ids = ProjectManageHelper.SINGLETONE.getProjectItemProjectIdVo(projectBomInfos, projectId);
        projectItemProjectServiceImpl.batchInsert(ids);

        projectItemManageService.dealProjectItemsOnProjectChanged(info, projectBomInfos,categoryInfosInDB);
    }


    /**
     * 项目编号是由年份+客户+地域+系列+NNN构成，但是上面的信息可能会更改，如果按照这个这个规则，项目编号应该要更改才对，但目前的处理方式是，项目编号和序号都不改变
     *
     * @param info
     */
    @Override
    public void edit(ProjectInfo info) {

        //读取session中的用户
        UserInfo userInfo = UserUtils.getUserFromSession();
        String aliases = userInfo.getAliases();
        List<UploadFileInfo> fileInfos = info.getFileInfos();
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, info.getNatrualkey(), uploadFileInfoService, WebConstants.FILE_KIND_PROJECT);
        ProjectManageHelper.SINGLETONE.buildProjectInfo(info);
        String projectId = info.getNatrualkey();
        List<ProjectCategoryInfo> categoryInfosInDB = queryCategoryInfosInDB(projectId);

        //更新t_project表
        super.edit(info);

        delInfoAboutProject(info.getNatrualkey());
        addBatchCategoryInfos(info.getCategoryInfos());


        //子项目
        List<ProjectBomInfo> projectBomInfos = ProjectManageHelper.SINGLETONE.buildProjectBomInfosByProjectInfo(info, aliases);

        //增加项目和子项目的关系
        List<ProjectItemProjectIdVo> ids = ProjectManageHelper.SINGLETONE.getProjectItemProjectIdVo(projectBomInfos, projectId);
        projectItemProjectServiceImpl.batchInsert(ids);

        projectItemManageService.dealProjectItemsOnProjectChanged(info, projectBomInfos, categoryInfosInDB);


    }

    private List<ProjectCategoryInfo> queryCategoryInfosInDB(String projectId) {
        return projectCategoryManageService.queryProjectCategoryInfo(projectId);
    }

    private void addBatchCategoryInfos(List<ProjectCategoryInfo> categoryInfos) {
        //增加项目的品类信息
        projectCategoryManageService.addBatch(categoryInfos);
    }

    private void delInfoAboutProject(String natrualkey) {
        //删除项目相关的所有信息
        projectManageMapper.delInfoAboutProject(natrualkey);
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
    public int listFilteredInfosCounts(BaseQueyrForm queryForm) {
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

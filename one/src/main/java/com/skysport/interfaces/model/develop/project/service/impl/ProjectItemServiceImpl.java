package com.skysport.interfaces.model.develop.project.service.impl;

import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.workflow.IApproveService;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.ExcelCreateUtils;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.interfaces.bean.common.UploadFileInfo;
import com.skysport.interfaces.bean.develop.*;
import com.skysport.interfaces.bean.form.develop.ProjectQueryForm;
import com.skysport.interfaces.bean.relation.ProjectItemBomIdVo;
import com.skysport.interfaces.bean.relation.ProjectItemProjectIdVo;
import com.skysport.interfaces.bean.task.TaskVo;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.engine.workflow.helper.TaskServiceHelper;
import com.skysport.interfaces.mapper.develop.ProjectItemMapper;
import com.skysport.interfaces.model.common.uploadfile.IUploadFileInfoService;
import com.skysport.interfaces.model.common.uploadfile.helper.UploadFileHelper;
import com.skysport.interfaces.model.develop.accessories.helper.AccessoriesServiceHelper;
import com.skysport.interfaces.model.develop.accessories.service.IAccessoriesService;
import com.skysport.interfaces.model.develop.bom.IBomService;
import com.skysport.interfaces.model.develop.bom.bean.DealBomInfos;
import com.skysport.interfaces.model.develop.bom.helper.BomHelper;
import com.skysport.interfaces.model.develop.fabric.IFabricsService;
import com.skysport.interfaces.model.develop.fabric.helper.FabricsServiceHelper;
import com.skysport.interfaces.model.develop.packaging.helper.PackagingServiceHelper;
import com.skysport.interfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.interfaces.model.develop.project.helper.ProjectHelper;
import com.skysport.interfaces.model.develop.project.service.IProjectCategoryManageService;
import com.skysport.interfaces.model.develop.project.service.IProjectItemService;
import com.skysport.interfaces.model.develop.project.service.ISexColorService;
import com.skysport.interfaces.model.relation.IRelationIdDealService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Service("projectItemManageService")
public class ProjectItemServiceImpl extends CommonServiceImpl<ProjectBomInfo> implements IProjectItemService, InitializingBean {
    @Autowired
    private IRelationIdDealService projectItemProjectServiceImpl;
    @Resource(name = "projectItemMapper")
    private ProjectItemMapper projectItemMapper;

    @Resource(name = "bomManageService")
    private IBomService bomManageService;

    @Resource(name = "fabricsManageService")
    private IFabricsService fabricsManageService;
    @Resource(name = "projectCategoryManageService")
    private IProjectCategoryManageService projectCategoryManageService;
    @Resource(name = "accessoriesService")
    private IAccessoriesService accessoriesService;

    @Resource(name = "packagingService")
    private IPackagingService packagingService;

    @Resource(name = "sexColorService")
    private ISexColorService sexColorService;

    @Resource(name = "uploadFileInfoService")
    private IUploadFileInfoService uploadFileInfoService;

    @Autowired
    private IApproveService projectItemTaskService;

    @Autowired
    private IRelationIdDealService projectItemBomServiceImpl;

    @Override
    public void afterPropertiesSet() {
        commonMapper = projectItemMapper;
    }

    @Override
    public ProjectBomInfo queryInfoByNatrualKey(String natrualKey) {
        ProjectBomInfo projectBomInfo = super.queryInfoByNatrualKey(natrualKey);
        List<SexColor> sexColors = sexColorService.searchInfosByProjectId(natrualKey);//
        projectBomInfo.setSexColors(sexColors);
        return projectBomInfo;
    }

    @Override
    public String queryCurrentSeqNo(ProjectBomInfo info) {
        return projectItemMapper.queryCurrentSeqNo(info);
    }

    /**
     * 项目编号是由年份+客户+地域+系列+NNN构成，但是上面的信息可能会更改，如果按照这个这个规则，项目编号应该要更改才对，但目前的处理方式是，项目编号和序号都不改变
     *
     * @param info
     */
    @Override
    public void edit(ProjectBomInfo info) {

        String seqNo = queryInfoByNatrualKey(info.getNatrualkey()).getSeqNo();
        info.setSeqNo(seqNo);

        List<UploadFileInfo> fileInfos = info.getFileInfos();
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, info.getNatrualkey(), uploadFileInfoService, WebConstants.FILE_KIND_PROJECT_ITEM);

        //更新t_project表
        super.edit(info);

        //更新t_kf_project_item_bom_baseinfo
        updateProjectBomBaseInfo(info);

        sexColorService.delByProjectId(info.getNatrualkey());

        if (null != info.getSexColors() && !info.getSexColors().isEmpty()) {
            //增加项目主颜色信息
            sexColorService.addBatch(info.getSexColors());
        }
    }


    /**
     * 启动开发流程
     *
     * @param projectId String
     */
    public void startWorkFlow(String projectId) {
        projectItemTaskService.startProcessInstanceByBussKey(projectId);
    }

    public void startWorkFlow(List<String> projectIds) {
        for (String projectId : projectIds) {
            startWorkFlow(projectId);
        }
    }

    @Override
    public void updateProjectBomBaseInfo(ProjectBomInfo info) {
        projectItemMapper.updateBomInfo(info);
    }

    @Override
    public int listFilteredInfosCounts(ProjectQueryForm queryForm) {
        return projectItemMapper.listFilteredInfosCounts(queryForm);
    }

    @Override
    public List<ProjectBomInfo> searchInfos(ProjectQueryForm queryForm) {
        return projectItemMapper.searchInfos(queryForm);
    }

    @Override
    public void addBatchBomInfo(List<ProjectBomInfo> info) {
        projectItemMapper.addBatchBomInfo(info);
    }

    /**
     * 导出bom详细表
     *
     * @param request
     * @param response
     * @param natrualkeys
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws UnsupportedEncodingException
     */
    @Override
    public void exportMaterialDetail(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException, InvalidFormatException {

        List<String> itemIds = Arrays.asList(natrualkeys.split(CharConstant.COMMA));

        StringBuilder bomDetailExcelName = new StringBuilder();
        bomDetailExcelName.append(DateUtils.SINGLETONE.getYyyyMmdd());
        bomDetailExcelName.append(CharConstant.HORIZONTAL_LINE).append(WebConstants.BOM_DETAIL_CN_NAME);

        Set<String> seriesNameSet = new HashSet<String>();
        Set<String> bomNameSet = new HashSet<>();

        //所有bomid
//        List<String> bomIds = bomManageService.queryBomIds(itemIds);
        //所有bomid
        List<BomInfo> bomInfos = bomManageService.queryBomInfosByProjectItemIds(itemIds);
        List<MaterialInfo> bomInfoDetails = new ArrayList();
        if (!bomInfos.isEmpty()) {
            for (BomInfo bomInfo : bomInfos) {
                String bomId = bomInfo.getNatrualkey();

                String seriesName = bomInfo.getSeriesName();
                seriesNameSet.add(CharConstant.HORIZONTAL_LINE + seriesName.replace("\\", "").replace("/", ""));//去重复
                String bomName = bomInfo.getName();

                if (bomNameSet.isEmpty()) {
                    bomNameSet.add(bomName);
                } else if (bomNameSet.size() < 3) {
                    bomNameSet.add(WebConstants.AND + bomName);
                }

                List<FabricsInfo> fabricsInfos = getFabricsInfos(bomId, seriesName);
                List<AccessoriesInfo> accessoriesInfos = getAccessoriesInfos(bomId, seriesName);
                List<PackagingInfo> packagingInfos = getPackagingInfos(bomId, seriesName);

                BomHelper.getInstance().buildBomInfoDetail(bomInfoDetails, fabricsInfos, accessoriesInfos, packagingInfos);


            }
        }

        String year = DateUtils.SINGLETONE.getYyyy();
        String ctxPath = new StringBuilder().append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.BASE_PATH)).append(WebConstants.FILE_SEPRITER).append(year).append(WebConstants.FILE_SEPRITER)
                .append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.DEVELOP_PATH)).toString();

        bomDetailExcelName.append(StringUtils.join(seriesNameSet.toArray(), ""));
        bomDetailExcelName.append(StringUtils.join(bomNameSet.toArray(), ""));
        bomDetailExcelName.append(WebConstants.SUFFIX_EXCEL_XLSX);
        String fileName = bomDetailExcelName.toString();

        String resourcePath = WebConstants.RESOURCE_PATH_BOM;
        //创建文件
        String downLoadPath = ExcelCreateUtils.getInstance().create(bomInfoDetails, fileName, ctxPath, resourcePath);
        //下载
        UpDownUtils.download(request, response, fileName, downLoadPath);

    }

    public List<PackagingInfo> getPackagingInfos(String bomId, String seriesName) {
        //所有包材
        List<PackagingInfo> packagingInfos = packagingService.queryPackagingByBomId(bomId);
        PackagingServiceHelper.SINGLETONE.SINGLETONE.translateIdToNameInPackagings(packagingInfos, seriesName);
        return packagingInfos;
    }

    public List<AccessoriesInfo> getAccessoriesInfos(String bomId, String seriesName) {
        //所有辅料
        List<AccessoriesInfo> accessoriesInfos = accessoriesService.queryAllAccessoriesByBomId(bomId);
        AccessoriesServiceHelper.SINGLETONE.translateIdToNameInAccessoriesInfos(accessoriesInfos, seriesName);
        return accessoriesInfos;
    }

    public List<FabricsInfo> getFabricsInfos(String bomId, String seriesName) {
        //所有面料
        List<FabricsInfo> fabricsInfos = fabricsManageService.queryAllFabricByBomId(bomId);

        //将id转成name
        FabricsServiceHelper.SINGLETONE.translateIdToNameInFabrics(fabricsInfos, seriesName, WebConstants.FABRIC_ID_EXCHANGE_BOM);
        return fabricsInfos;
    }

    /**
     * @param mainColorNew
     * @param mainColorOld
     * @param projectId    子项目
     */
    @Override
    public void updateMainColors(String sexId, String mainColorNew, String mainColorOld, String projectId) {
        sexColorService.updateSexColorByProjectIdAndSexId(sexId, mainColorNew, mainColorOld, projectId);
    }

    @Override
    public void delSexColorInfoByBomInfo(BomInfo info) {
        sexColorService.delSexColorInfoByBomInfo(info);
    }

    private List<ProjectCategoryInfo> queryCategoryInfosInDB(String projectId) {
        return projectCategoryManageService.queryProjectCategoryInfo(projectId);
    }

    /**
     * 修改大项目，处理子项目
     *
     * @param info
     */
    @Override
    public void dealProjectItemsOnProjectChanged(ProjectInfo info) {


        String projectId = info.getNatrualkey();
//        List<ProjectCategoryInfo> categoryInfosInDB = queryCategoryInfosInDB(projectId);

        //DB的projectItemsId
        List<String> projectItemsDB = projectItemProjectServiceImpl.queryProjectChildIdsByParentId(projectId);// ProjectHelper.SINGLETONE.buildProjectItemsId(categoryInfosInDB, projectId);

        //页面的projectItemsId
        List<ProjectBomInfo> projectBomInfos = ProjectHelper.SINGLETONE.buildProjectBomInfosByProjectInfo(info, projectItemsDB);
        List<String> projectItemsNew = ProjectHelper.SINGLETONE.buildProjectItemsId(projectBomInfos);

        //获取需要更新的子项目列表
        //交集
        List<String> intersection = ListUtils.intersection(projectItemsNew, projectItemsDB);
//        List<ProjectBomInfo> intersectionProjectBomInfos = ProjectHelper.SINGLETONE.getMatchProjectBomInfoList(intersection, projectBomInfos);

        //获取需要删除的
        List<String> subtract = ListUtils.subtract(projectItemsDB, intersection);

        //需要增加的
        List<String> adds = ListUtils.subtract(projectItemsNew, intersection);
        List<ProjectBomInfo> addsProjectBomInfos = ProjectHelper.SINGLETONE.getMatchProjectBomInfoList(adds, projectBomInfos);
//        List<ProjectBomInfo> updatesProjectBomInfos = ProjectHelper.SINGLETONE.getMatchProjectBomInfoList(adds, projectBomInfos);


//        updateProjectItems(updatesProjectBomInfos); 子项目没有内容需要修改
        delProjectitems(subtract);
        addProjectItems(addsProjectBomInfos);


        //增加项目和子项目的关系
        List<ProjectItemProjectIdVo> relations = ProjectHelper.SINGLETONE.getProjectItemProjectIdVo(projectBomInfos);
        projectItemProjectServiceImpl.batchInsert(relations);


        projectItemTaskService.suspendProcessInstanceByIds(subtract);//终止流程

        List<String> needToStartFlowIds = new ArrayList<>();
        needToStartFlowIds.addAll(adds);
        needToStartFlowIds.addAll(intersection);
        List<ProcessInstance> instancesIntersection = projectItemTaskService.queryProcessInstancesActiveByBusinessKey(needToStartFlowIds, WebConstants.PROJECT_ITEM_PROCESS);
        needToStartFlowIds = TaskServiceHelper.getInstance().chooseNeedToStartInUpdates(instancesIntersection, needToStartFlowIds);//筛选出已经启动流程实例的子项目

        List<ProjectBomInfo> needToStartFlowList = getNeedToStartFlowList(needToStartFlowIds, projectBomInfos);//页面传入的子项目中，过滤掉已经启动流程实例的子项目
        List<TaskVo> taskVos = TaskServiceHelper.getInstance().changeToBusinessVo(needToStartFlowList);


//        updateApproveStatusBatch(projectBomInfos);


        //启动流程
//        startWorkFlow(addAnUpdates);
        projectItemTaskService.startWorkFlow(taskVos);
    }

    private void updateProjectItems(List<ProjectBomInfo> updatesProjectBomInfos) {
        projectItemMapper.updateBatch(updatesProjectBomInfos);
    }

    /**
     * 挑选出需要启动流程的子项目
     *
     * @param addAnUpdates
     * @param projectBomInfos
     * @return
     */
    private List<ProjectBomInfo> getNeedToStartFlowList(List<String> addAnUpdates, List<ProjectBomInfo> projectBomInfos) {
        List<ProjectBomInfo> needToStartFlowList = new ArrayList<>();
        for (ProjectBomInfo bominfo : projectBomInfos) {
            String projectItemIdTem = bominfo.getNatrualkey();
            for (String projectItemId : addAnUpdates) {
                if (projectItemId.equals(projectItemIdTem)) {
                    needToStartFlowList.add(bominfo);
                }
            }
        }

        return needToStartFlowList;

    }


    @Override
    public void setStatuCodeAlive(ProjectBomInfo info, String natrualKey) {
        projectItemTaskService.setStatuCodeAlive(info, natrualKey);
    }

    private void delProjectitems(List<String> subtract) {
        if (!subtract.isEmpty()) {
            projectItemMapper.delProjectitems(subtract);
        }

    }


    private void addProjectItems(List<ProjectBomInfo> projectBomInfos) {
        if (!projectBomInfos.isEmpty()) {
            //业务数据
            addBatch(projectBomInfos);
            addBatchBomInfo(projectBomInfos);
        }

    }


    public <T> T createBoms(String businessKey) {

        ProjectBomInfo info2 = queryInfoByNatrualKey(businessKey);
        //增加项目和子项目的关系

        //生成BOM信息并保存
        DealBomInfos dealBomInfos = bomManageService.autoCreateBomInfoAndSave(info2);
        List<BomInfo> alls = dealBomInfos.getAlls();
        List<String> bomsNeedToStart = dealBomInfos.getBomsNeedToStart();

        //子项目BOM关系
        List<ProjectItemBomIdVo> bomIdVos = ProjectHelper.SINGLETONE.getProjectItemBomIdVo(alls);
        projectItemBomServiceImpl.batchInsert(bomIdVos);

        return (T) bomsNeedToStart;

    }

    @Override
    public List<FabricsInfo> getFabricsInfos(String natrualKey) {
        return getFabricsInfos(natrualKey, null);
    }

    @Override
    public List<AccessoriesInfo> getAccessoriesInfos(String natrualKey) {
        return getAccessoriesInfos(natrualKey, null);
    }

    @Override
    public List<PackagingInfo> getPackagingInfos(String natrualKey) {
        return getPackagingInfos(natrualKey, null);
    }


    @Override
    public void addBatch(List<ProjectBomInfo> infos) {

        super.addBatch(infos);

    }


}

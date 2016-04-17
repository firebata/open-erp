package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.core.model.workflow.IWorkFlowService;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.engine.workflow.helper.TaskServiceHelper;
import com.skysport.inerfaces.form.develop.ProjectQueryForm;
import com.skysport.inerfaces.mapper.develop.ProjectItemManageMapper;
import com.skysport.inerfaces.model.common.uploadfile.IUploadFileInfoService;
import com.skysport.inerfaces.model.common.uploadfile.helper.UploadFileHelper;
import com.skysport.inerfaces.model.develop.accessories.service.IAccessoriesService;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.bom.helper.BomManageHelper;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.project.helper.ProjectManageHelper;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemManageService;
import com.skysport.inerfaces.model.develop.project.service.ISexColorService;
import com.skysport.inerfaces.model.permission.userinfo.service.IStaffService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Service("projectItemManageService")
public class ProjectItemManageServiceImpl extends CommonServiceImpl<ProjectBomInfo> implements IProjectItemManageService, InitializingBean {
    @Resource(name = "projectItemManageMapper")
    private ProjectItemManageMapper projectItemManageMapper;

    @Resource(name = "bomManageService")
    private IBomManageService bomManageService;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Resource(name = "fabricsManageService")
    private IFabricsService fabricsManageService;

    @Resource(name = "accessoriesService")
    private IAccessoriesService accessoriesService;

    @Resource(name = "packagingService")
    private IPackagingService packagingService;

    @Resource(name = "sexColorService")
    private ISexColorService sexColorService;

    @Resource(name = "uploadFileInfoService")
    private IUploadFileInfoService uploadFileInfoService;

    @Autowired
    private IWorkFlowService projectItemTaskService;

    @Resource
    private IStaffService developStaffImpl;

    @Override
    public void afterPropertiesSet() {
        commonDao = projectItemManageMapper;
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
        return projectItemManageMapper.queryCurrentSeqNo(info);
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

        //更新t_project_bominfo表
        updateBomInfo(info);

        sexColorService.delByProjectId(info.getNatrualkey());


        if (null != info.getSexColors() && !info.getSexColors().isEmpty()) {
            //增加项目主颜色信息
            sexColorService.addBatch(info.getSexColors());
        }

        ProjectBomInfo info2 = super.queryInfoByNatrualKey(info.getNatrualkey());
        info.buildBomInfo(info2);


        //生成BOM信息并保存
        bomManageService.autoCreateBomInfoAndSave(info);
    }


    /**
     * 启动开发流程
     *
     * @param projectId String
     */
    private void startWorkFlow(String projectId) {
        projectItemTaskService.startProcessInstanceByBussKey(projectId);
    }


    @Override
    public void addBomInfo(ProjectBomInfo info) {
        projectItemManageMapper.addBomInfo(info);
    }

    @Override
    public void updateBomInfo(ProjectBomInfo info) {
        projectItemManageMapper.updateBomInfo(info);
    }

    @Override
    public int listFilteredInfosCounts(ProjectQueryForm queryForm) {
        return projectItemManageMapper.listFilteredInfosCounts(queryForm);
    }

    @Override
    public List<ProjectBomInfo> searchInfos(ProjectQueryForm queryForm) {
        return projectItemManageMapper.searchInfos(queryForm);
    }

    @Override
    public void addBatchBomInfo(List<ProjectBomInfo> info) {
        projectItemManageMapper.addBatchBomInfo(info);
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
    public void exportMaterialDetail(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {

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

        if (!bomInfos.isEmpty()) {

            List<BomInfoDetail> bomInfoDetails = new ArrayList<>();


            for (BomInfo bomInfo : bomInfos) {
                String bomId = bomInfo.getNatrualkey();

                String seriesName = bomInfo.getSeriesName();
                seriesNameSet.add(CharConstant.HORIZONTAL_LINE + seriesName);//去重复
                String bomName = bomInfo.getName();

                if (bomNameSet.isEmpty()) {
                    bomNameSet.add(bomName);
                } else if (bomNameSet.size() < 3) {
                    bomNameSet.add(WebConstants.AND + bomName);
                }


                //所有面料
                List<FabricsInfo> fabricsInfos = fabricsManageService.queryAllFabricByBomId(bomId);

                //将id转成name
                BomManageHelper.translateIdToNameInFabrics(fabricsInfos, seriesName, WebConstants.FABRIC_ID_EXCHANGE_BOM);


                //所有辅料
                List<AccessoriesInfo> accessoriesInfos = accessoriesService.queryAllAccessoriesByBomId(bomId);
                BomManageHelper.translateIdToNameInAccessoriesInfos(accessoriesInfos, seriesName);

                //所有包材
                List<KFPackaging> kfPackagings = packagingService.queryPackagingByBomId(bomId);
                BomManageHelper.translateIdToNameInPackagings(kfPackagings, seriesName);


                BomInfoDetail bomInfoDetail = BomManageHelper.buildBomInfoDetail(fabricsInfos, accessoriesInfos, kfPackagings);

                bomInfoDetails.add(bomInfoDetail);

            }

            String year = DateUtils.SINGLETONE.getYyyy();
            String ctxPath = new StringBuilder().append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.BASE_PATH)).append(WebConstants.FILE_SEPRITER).append(year).append(WebConstants.FILE_SEPRITER)
                    .append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.DEVELOP_PATH)).toString();

            bomDetailExcelName.append(StringUtils.join(seriesNameSet.toArray(), ""));
            bomDetailExcelName.append(StringUtils.join(bomNameSet.toArray(), ""));
            bomDetailExcelName.append(WebConstants.SUFFIX_EXCEL_XLS);
            String fileName = bomDetailExcelName.toString();


            //完整文件路径
            String downLoadPath = ctxPath + File.separator + fileName;

            BomManageHelper.createFile(fileName, ctxPath, bomInfoDetails);


            UpDownUtils.download(request, response, fileName, downLoadPath);
        }
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

    /**
     * 修改大项目，处理子项目
     *
     * @param info
     * @param projectBomInfos   页面的项目数据
     * @param categoryInfosInDB
     */
    @Override
    public void dealProjectItemsOnProjectChanged(ProjectInfo info, List<ProjectBomInfo> projectBomInfos, List<ProjectCategoryInfo> categoryInfosInDB) {
        String projectId = info.getNatrualkey();
        //页面的projectItemsId
        List<String> projectItemsNew = ProjectManageHelper.SINGLETONE.buildProjectItemsId(projectBomInfos);
        //DB的projectItemsId
        List<String> projectItemsDB = ProjectManageHelper.SINGLETONE.buildProjectItemsId(categoryInfosInDB, projectId);

        //获取需要更新的子项目列表
        //交集
        List<String> intersection = ListUtils.intersection(projectItemsNew, projectItemsDB);
//        List<ProjectBomInfo> intersectionProjectBomInfos = ProjectManageHelper.SINGLETONE.getMatchProjectBomInfoList(intersection, projectBomInfos);

        //获取需要删除的
        List<String> subtract = ListUtils.subtract(projectItemsDB, intersection);

        //需要增加的
        List<String> adds = ListUtils.subtract(projectItemsNew, intersection);
        List<ProjectBomInfo> addsProjectBomInfos = ProjectManageHelper.SINGLETONE.getMatchProjectBomInfoList(adds, projectBomInfos);


//        updateProjectItems(intersectionProjectBomInfos);  不用修改数据

        delProjectitems(subtract);

        addProjectItems(addsProjectBomInfos);

        //
        updateApproveStatusBatch(projectBomInfos);

    }

    private void delProjectitems(List<String> subtract) {
        if (!subtract.isEmpty()) {
            projectItemManageMapper.delProjectitems(subtract);
        }

    }

    private void updateProjectItems(List<ProjectBomInfo> intersectionProjectBomInfos) {
        if (!intersectionProjectBomInfos.isEmpty()) {
            updateBatch(intersectionProjectBomInfos);
            updateBatchBomInfo(intersectionProjectBomInfos);
        }
    }

    private void updateBatchBomInfo(List<ProjectBomInfo> intersectionProjectBomInfos) {
        projectItemManageMapper.updateBatchBomInfo(intersectionProjectBomInfos);
    }


    private void addProjectItems(List<ProjectBomInfo> projectBomInfos) {
        if (!projectBomInfos.isEmpty()) {
            //业务数据
            addBatch(projectBomInfos);

            addBatchBomInfo(projectBomInfos);
        }

    }

    private void updateApproveStatusBatch(List<ProjectBomInfo> projectBomInfos) {
        //审核状态：新增
        List<String> businessKeys = ProjectManageHelper.SINGLETONE.buildBusinessKeys(projectBomInfos);
        updateApproveStatusBatch(businessKeys, WebConstants.APPROVE_STATUS_NEW);
    }

    @Override
    public void updateApproveStatus(String businessKey, String status) {
        projectItemManageMapper.updateApproveStatus(businessKey, status);
    }

    @Override
    public void updateApproveStatusBatch(List<String> businessKeys, String status) {
        projectItemManageMapper.updateApproveStatusBatch(businessKeys, status);
    }

    @Override
    public void submit(String businessKey) {
        //启动流程
        startWorkFlow(businessKey);
        //状态改为待审批
        updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_UNDO);
    }

    @Override
    public void submit(String taskId, String businessKey) {

        boolean isAlive = TaskServiceHelper.getInstance().isActive(this, businessKey);
        if (StringUtils.isNotBlank(taskId) && taskId.equals("null") && !isAlive) {
            logger.warn("流程第一次启动");
            //启动流程
            startWorkFlow(businessKey);
        } else {
            //完成当前任务
            Map<String, Object> variables = new HashMap<String, Object>();
            String groupIdDevManager = developStaffImpl.getManagerStaffGroupId();
            variables.put(WebConstants.DEVLOP_MANAGER, groupIdDevManager);
            projectItemTaskService.complete(taskId, variables);
        }


        //状态改为待审批
        updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_UNDO);
    }


    @Override
    public List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String natrualKey) {
        List<ProcessInstance> processInstances = projectItemTaskService.queryProcessInstancesActiveByBusinessKey(natrualKey);
        return processInstances;
    }

    @Override
    public List<ProcessInstance> queryProcessInstancesSuspendedByBusinessKey(String natrualKey) {
        List<ProcessInstance> processInstances = projectItemTaskService.queryProcessInstancesSuspendedByBusinessKey(natrualKey);
        return processInstances;
    }

    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve) {
        Map<String, Object> variables = new HashedMap();
        String handleUserId;
        if (approve) {
            handleUserId = developStaffImpl.getManagerStaffGroupId();
            variables.put(WebConstants.DEVLOP_MANAGER, handleUserId);
        } else {
//            handleUserId = developStaffImpl.staffId();
//            variables.put(WebConstants.DEVLOP_STAFF, handleUserId);
        }
        variables.put(WebConstants.PROJECT_ITEM_PASS, approve);

        return variables;
    }

    @Override
    public void addBatch(List<ProjectBomInfo> infos) {

        super.addBatch(infos);

    }


}

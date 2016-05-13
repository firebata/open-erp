package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.workflow.IApproveService;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.ExcelCreateUtils;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.bean.form.develop.ProjectQueryForm;
import com.skysport.inerfaces.bean.relation.ProjectItemBomIdVo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.develop.ProjectItemMapper;
import com.skysport.inerfaces.model.common.uploadfile.IUploadFileInfoService;
import com.skysport.inerfaces.model.common.uploadfile.helper.UploadFileHelper;
import com.skysport.inerfaces.model.develop.accessories.helper.AccessoriesServiceHelper;
import com.skysport.inerfaces.model.develop.accessories.service.IAccessoriesService;
import com.skysport.inerfaces.model.develop.bom.IBomService;
import com.skysport.inerfaces.model.develop.bom.bean.DealBomInfos;
import com.skysport.inerfaces.model.develop.bom.helper.BomHelper;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.fabric.helper.FabricsServiceHelper;
import com.skysport.inerfaces.model.develop.packaging.helper.PackagingServiceHelper;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.project.helper.ProjectHelper;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemService;
import com.skysport.inerfaces.model.develop.project.service.ISexColorService;
import com.skysport.inerfaces.model.relation.IRelationIdDealService;
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

    @Resource(name = "projectItemMapper")
    private ProjectItemMapper projectItemMapper;

    @Resource(name = "bomManageService")
    private IBomService bomManageService;

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
                seriesNameSet.add(CharConstant.HORIZONTAL_LINE + seriesName);//去重复
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
        List<String> projectItemsNew = ProjectHelper.SINGLETONE.buildProjectItemsId(projectBomInfos);
        //DB的projectItemsId
        List<String> projectItemsDB = ProjectHelper.SINGLETONE.buildProjectItemsId(categoryInfosInDB, projectId);

        //获取需要更新的子项目列表
        //交集
        List<String> intersection = ListUtils.intersection(projectItemsNew, projectItemsDB);
//        List<ProjectBomInfo> intersectionProjectBomInfos = ProjectHelper.SINGLETONE.getMatchProjectBomInfoList(intersection, projectBomInfos);

        //获取需要删除的
        List<String> subtract = ListUtils.subtract(projectItemsDB, intersection);

        //需要增加的
        List<String> adds = ListUtils.subtract(projectItemsNew, intersection);
        List<ProjectBomInfo> addsProjectBomInfos = ProjectHelper.SINGLETONE.getMatchProjectBomInfoList(adds, projectBomInfos);


//        updateProjectItems(intersectionProjectBomInfos);  不用修改数据

        delProjectitems(subtract);

        addProjectItems(addsProjectBomInfos);

        updateApproveStatusBatch(projectBomInfos);


        List<ProcessInstance> instances = projectItemTaskService.queryProcessInstancesActiveByBusinessKey(subtract);
        projectItemTaskService.suspendProcessInstanceById(instances);//终止流程

        //启动流程
        startWorkFlow(adds);
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

    private void updateApproveStatusBatch(List<ProjectBomInfo> projectBomInfos) {
        //审核状态：新增
        List<String> businessKeys = ProjectHelper.SINGLETONE.buildBusinessKeys(projectBomInfos);
        projectItemTaskService.updateApproveStatusBatch(businessKeys, WebConstants.APPROVE_STATUS_NEW);
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
    public void addBatch(List<ProjectBomInfo> infos) {

        super.addBatch(infos);

    }


}

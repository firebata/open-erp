package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.inerfaces.bean.common.UploadFileInfo;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.form.develop.ProjectQueryForm;
import com.skysport.inerfaces.mapper.develop.ProjectItemManageMapper;
import com.skysport.inerfaces.model.common.uploadfile.IUploadFileInfoService;
import com.skysport.inerfaces.model.common.uploadfile.helper.UploadFileHelper;
import com.skysport.inerfaces.model.develop.accessories.service.IAccessoriesService;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.bom.helper.BomManageHelper;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemManageService;
import com.skysport.inerfaces.model.develop.project.service.ISexColorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    private IncrementNumber incrementNumber;


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

    @Override
    public void afterPropertiesSet() {
        commonDao = projectItemManageMapper;
    }

    @Override
    public ProjectBomInfo queryInfoByNatrualKey(String natrualKey) {

        List<SexColor> sexColors = sexColorService.searchInfosByProjectId(natrualKey);
        ProjectBomInfo projectBomInfo = super.queryInfoByNatrualKey(natrualKey);
        projectBomInfo.setSexColors(sexColors);
        return projectBomInfo;
    }

    @Override
    public String queryCurrentSeqNo(ProjectBomInfo info) {
        return projectItemManageMapper.queryCurrentSeqNo(info);
    }

//    @Override
//    public void add(ProjectBomInfo info) {
////        LocalDate today = LocalDate.now();
//        String name = ProjectManageHelper.buildProjectName(info);
//        info.setName(name);
//        info.setProjectName(name);
//
//        //增加主项目信息
//        super.add(info);
//
//        //增加项目BOM信息
//        addBomInfo(info);
//
//        List<MainColor> mainColorList = MainColorHelper.SINGLETONE.turnMainColorStrToList(info);
//
//        //增加项目主颜色信息
//        mainColorService.add(mainColorList);
//
//
//        //生成BOM信息并保存
//        BomManageHelper.autoCreateBomInfoAndSave(bomManageService, incrementNumber, info);
//
//    }

    /**
     * 项目编号是由年份+客户+地域+系列+NNN构成，但是上面的信息可能会更改，如果按照这个这个规则，项目编号应该要更改才对，但目前的处理方式是，项目编号和序号都不改变
     *
     * @param info
     */
    @Override
    public void edit(ProjectBomInfo info) {

        String seqNo = queryInfoByNatrualKey(info.getNatrualkey()).getSeqNo();
        info.setSeqNo(seqNo);

//        String name = ProjectManageHelper.buildProjectName(info);
//        info.setName(name);
//        info.setProjectName(name);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        List<UploadFileInfo> fileInfos = info.getFileInfos();
        UploadFileHelper.SINGLETONE.updateFileRecords(fileInfos, info.getNatrualkey(), uploadFileInfoService, WebConstants.FILE_KIND_PROJECT_ITEM);

        //更新t_project表
        super.edit(info);

        //更新t_project_bominfo表
        updateBomInfo(info);

//        //增加项目主颜色信息
//        mainColorService.delete(info.getNatrualkey());
//
//        List<MainColor> mainColorList = MainColorHelper.SINGLETONE.turnMainColorStrToList(info);

        sexColorService.delByProjectId(info.getNatrualkey());

        if (null != info.getSexColors() && !info.getSexColors().isEmpty()) {
            //增加项目主颜色信息
            sexColorService.addBatch(info.getSexColors());
        }


        ProjectBomInfo info2 = super.queryInfoByNatrualKey(info.getNatrualkey());

        String projectId = info2.getNatrualkey();
        String customerId = info2.getCustomerId();
        String areaId = info2.getAreaId();
        String seriesId = info2.getSeriesId();
        String categoryAid = info2.getCategoryAid();
        String categoryBid = info2.getCategoryBid();

        info.setProjectId(projectId);
        info.setCustomerId(customerId);
        info.setAreaId(areaId);
        info.setSeriesId(seriesId);
        info.setCategoryAid(categoryAid);
        info.setCategoryBid(categoryBid);

        //生成BOM信息并保存
        BomManageHelper.autoCreateBomInfoAndSave(bomManageService, incrementNumber, info);
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

}

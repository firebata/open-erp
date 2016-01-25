package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.core.instance.DictionaryInfo;
import com.skysport.core.model.seqno.service.IncrementNumber;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.inerfaces.bean.info.MainColor;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.form.develop.ProjectQueryForm;
import com.skysport.inerfaces.mapper.develop.ProjectItemManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.develop.accessories.service.IAccessoriesService;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.bom.helper.BomManageHelper;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.project.helper.ProjectManageHelper;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemManageService;
import com.skysport.inerfaces.model.develop.project.service.ISexColorService;
import com.skysport.inerfaces.model.info.main_color.IMainColorService;
import com.skysport.inerfaces.model.info.main_color.helper.MainColorHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Resource(name = "mainColorService")
    private IMainColorService mainColorService;

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

    @Override
    public void afterPropertiesSet() {
        commonDao = projectItemManageMapper;
    }

    @Override
    public ProjectBomInfo queryInfoByNatrualKey(String natrualKey) {

        List<SexColor> sexColors = sexColorService.searchInfos2(natrualKey);
        ProjectBomInfo projectBomInfo = super.queryInfoByNatrualKey(natrualKey);
        projectBomInfo.setSexColors(sexColors);
        return projectBomInfo;
    }

    @Override
    public String queryCurrentSeqNo(ProjectBomInfo info) {
        return projectItemManageMapper.queryCurrentSeqNo(info);
    }

    @Override
    public void add(ProjectBomInfo info) {
//        LocalDate today = LocalDate.now();
        String name = ProjectManageHelper.buildProjectName(info);
        info.setName(name);
        info.setProjectName(name);

        //增加主项目信息
        super.add(info);

        //增加项目BOM信息
        addBomInfo(info);

        List<MainColor> mainColorList = MainColorHelper.SINGLETONE.turnMainColorStrToList(info);

        //增加项目主颜色信息
        mainColorService.add(mainColorList);


        //生成BOM信息并保存
        BomManageHelper.autoCreateBomInfoAndSave(bomManageService, incrementNumber, info);

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

//        String name = ProjectManageHelper.buildProjectName(info);
//        info.setName(name);
//        info.setProjectName(name);

        //更新t_project表
        super.edit(info);

        //更新t_project_bominfo表
        updateBomInfo(info);

//        //增加项目主颜色信息
//        mainColorService.delete(info.getNatrualkey());
//
//        List<MainColor> mainColorList = MainColorHelper.SINGLETONE.turnMainColorStrToList(info);

        sexColorService.del(info.getNatrualkey());


        //增加项目主颜色信息
        sexColorService.addBatch(info.getSexColors());


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

    @Override
    public void exportMaterialDetail(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException {

        List<String> itemIds = Arrays.asList(natrualkeys.split(","));

        //所有bomid
        List<String> bomIds = bomManageService.queryBomIds(itemIds);


        if (!bomIds.isEmpty()) {

            List<BomInfoDetail> bomInfoDetails = new ArrayList<>();

            for (String bomId : bomIds) {
                //所有面料
                List<FabricsInfo> fabricIds = fabricsManageService.queryAllFabricByBomId(bomId);
                //将id转成name
                BomManageHelper.translateIdToNameInFabrics(fabricIds, WebConstants.FABRIC_ID_EXCHANGE_BOM);

                //所有辅料
                List<AccessoriesInfo> accessoriesInfos = accessoriesService.queryAllAccessoriesByBomId(bomId);
                BomManageHelper.translateIdToNameInAccessoriesInfos(accessoriesInfos);

                //所有包材
                List<KFPackaging> packagings = packagingService.queryPackagingByBomId(bomId);
                BomManageHelper.translateIdToNameInPackagings(packagings);


                BomInfoDetail bomInfoDetail = BomManageHelper.buildBomInfoDetail(fabricIds, accessoriesInfos, packagings);

                bomInfoDetails.add(bomInfoDetail);

            }


            LocalDate today = LocalDate.now();
            int year = today.getYear();
            String ctxPath = new StringBuilder().append(DictionaryInfo.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.BASE_PATH)).append(WebConstants.FILE_SEPRITER).append(year).append(WebConstants.FILE_SEPRITER)
                    .append(DictionaryInfo.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.DEVELOP_PATH)).toString();


            String fileName = itemIds.get(0) + "-面辅料详细.xls";

            //完整文件路径
            String downLoadPath = ctxPath + File.separator + fileName;

            BomManageHelper.createFile(fileName, ctxPath, bomInfoDetails);


            UpDownUtils.download(request, response, fileName, downLoadPath);
        }
    }
}

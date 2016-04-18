package com.skysport.inerfaces.model.develop.bom.impl;

import com.skysport.core.constant.CharConstant;
import com.skysport.core.exception.SkySportException;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.bean.relation.BomMaterialIdVo;
import com.skysport.inerfaces.bean.relation.ProjectItemBomIdVo;
import com.skysport.inerfaces.constant.develop.ReturnCodeConstant;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import com.skysport.inerfaces.mapper.info.BomInfoManageMapper;
import com.skysport.inerfaces.model.develop.accessories.service.IAccessoriesService;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.bom.helper.BomManageHelper;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.product_instruction.IProductionInstructionService;
import com.skysport.inerfaces.model.develop.project.helper.ProjectManageHelper;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemManageService;
import com.skysport.inerfaces.model.develop.quoted.service.IFactoryQuoteService;
import com.skysport.inerfaces.model.develop.quoted.service.IQuotedService;
import com.skysport.inerfaces.model.relation.IRelationIdDealService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
@Service("bomManageService")
public class BomManageServiceImpl extends CommonServiceImpl<BomInfo> implements IBomManageService, InitializingBean {

    @Resource(name = "bomInfoManageMapper")
    private BomInfoManageMapper bomInfoManageMapper;

    @Resource(name = "fabricsManageService")
    private IFabricsService fabricsManageService;

    @Resource(name = "accessoriesService")
    private IAccessoriesService accessoriesService;

    @Resource(name = "packagingService")
    private IPackagingService packagingService;

    @Resource(name = "quotedService")
    private IQuotedService quotedService;

    @Resource(name = "projectItemManageService")
    private IProjectItemManageService projectItemManageService;

    @Autowired
    private IRelationIdDealService bomMaterialServiceImpl;
    @Autowired
    private IProductionInstructionService productionInstructionServiceImpl;

    @Resource(name = "factoryQuoteService")
    private IFactoryQuoteService factoryQuoteService;

    @Override
    public void afterPropertiesSet() {
        commonDao = bomInfoManageMapper;
    }

    @Override
    public int listFilteredInfosCounts(BomQueryForm bomQueryForm) {
        return bomInfoManageMapper.listFilteredInfosCounts(bomQueryForm);
    }

    @Override
    public List<BomInfo> searchInfos(BomQueryForm bomQueryForm) {
        return bomInfoManageMapper.searchInfos(bomQueryForm);
    }

    @Override
    public List<String> queryAllBomIdsByProjectId(String projectId) {
        return bomInfoManageMapper.queryAllBomIdsByProjectId(projectId);
    }

    @Override
    public List<String> queryBomIdsNeedLapdipByProjectId(String projectId) {
        return bomInfoManageMapper.queryBomIdsNeedLapdipByProjectId(projectId);
    }

    /**
     * 查询bom信息
     *
     * @param bomId
     * @return
     */
    @Override
    public BomInfo queryInfoByNatrualKey(String bomId) {
        BomInfo bomInfo = super.queryInfoByNatrualKey(bomId);

        if (null != bomInfo) {

            //面料集合
            List<FabricsInfo> fabrics = fabricsManageService.queryFabricList(bomId);

            //辅料集合
            List<AccessoriesInfo> accessories = accessoriesService.queryAccessoriesList(bomId);

            //包材
            List<KFPackaging> packagings = packagingService.queryPackagingList(bomId);


            //成衣厂 & 生产指示单
            List<FactoryQuoteInfo> factoryQuoteInfos = factoryQuoteService.queryFactoryQuoteInfoList(bomId);

            //报价信息
            QuotedInfo quotedInfo = quotedService.queryInfoByNatrualKey(bomId);


            KfProductionInstructionEntity productionInstruction = productionInstructionServiceImpl.queryProductionInstractionInfo(bomId);

            buildBomInfo(bomInfo, fabrics, accessories, packagings, factoryQuoteInfos, quotedInfo, productionInstruction);

        }
        return bomInfo;
    }

    /**
     * 更改子项目的颜色
     *
     * @param bomInfo
     */
    private void dealMainColor(BomInfo bomInfo) {
        String mainColor = bomInfo.getMainColor();
        String sexId = bomInfo.getSexId();
        String mainColorOld = bomInfo.getMainColorOld();

        if (StringUtils.isNotEmpty(mainColor) && StringUtils.isNotEmpty(mainColorOld) && StringUtils.isNotEmpty(sexId)) {
            if (!mainColor.trim().equals(mainColorOld.trim())) {
                projectItemManageService.updateMainColors(sexId, mainColor.trim(), mainColorOld.trim(), bomInfo.getProjectId());
            }
        } else {
            throw new SkySportException(ReturnCodeConstant.UPDATE_BOM_MAINCOLOR_PARAM_ERR);
        }
    }

    /**
     * 构造bom信息
     *
     * @param bomInfo
     * @param fabrics
     * @param accessories
     * @param packagings
     * @param factoryQuoteInfos
     */
    private void buildBomInfo(BomInfo bomInfo, List<FabricsInfo> fabrics, List<AccessoriesInfo> accessories, List<KFPackaging> packagings, List<FactoryQuoteInfo> factoryQuoteInfos, QuotedInfo quotedInfo, KfProductionInstructionEntity productionInstruction) {
        bomInfo.setQuotedInfo(quotedInfo);
        bomInfo.setFabrics(fabrics);
        bomInfo.setAccessories(accessories);
        bomInfo.setPackagings(packagings);
        bomInfo.setFactoryQuoteInfos(factoryQuoteInfos);
        bomInfo.setProductionInstruction(productionInstruction);
    }

    /**
     * 修改bom信息
     *
     * @param bomInfo
     */
    @Override
    public void edit(BomInfo bomInfo) {
        String bomId = StringUtils.isEmpty(bomInfo.getBomId()) ? bomInfo.getNatrualkey() : bomInfo.getBomId();
        super.edit(bomInfo);

        //保存面料信息
        List<FabricsInfo> fabrics = fabricsManageService.updateOrAddBatch(bomInfo);

        //保存辅料信息
        List<AccessoriesInfo> accessories = accessoriesService.updateOrAddBatch(bomInfo);


        //保存包装材料信息
        List<KFPackaging> packagings = packagingService.updateOrAddBatch(bomInfo);

        //保存成衣厂信息
        List<FactoryQuoteInfo> factoryQuoteInfos = factoryQuoteService.updateOrAddBatch(bomInfo);

        KfProductionInstructionEntity productionInstruction = productionInstructionServiceImpl.updateOrAddBatch(bomInfo);

        //保存报价信息
        QuotedInfo quotedInfo = quotedService.updateOrAdd(bomInfo.getQuotedInfo());


        //如果颜色修改，需要修改bom的颜色(已在上面的修改bom方法中修改)和bom所属项目的子颜色
        dealMainColor(bomInfo);


        //增加Bom和物料的关系
        List<BomMaterialIdVo> idsFabrics = BomManageHelper.getInstance().getBomMaterialIdVoInFabricsInfo(fabrics, bomId);
        List<BomMaterialIdVo> idsAccessoriesInfo = BomManageHelper.getInstance().getBomMaterialIdVoInAccessoriesInfo(accessories, bomId);
        List<BomMaterialIdVo> idsPackaging = BomManageHelper.getInstance().getBomMaterialIdVoInKFPackaging(packagings, bomId);

        List<BomMaterialIdVo> idsFactoryQuoteInfo = BomManageHelper.getInstance().getBomMaterialIdVoInFactoryQuoteInfo(factoryQuoteInfos, bomId);
        List<BomMaterialIdVo> idsProInst = BomManageHelper.getInstance().getBomMaterialIdVoInKfProductionInstructionEntity(productionInstruction, bomId);
//        List<BomMaterialIdVo> idsQuotedInfo = BomManageHelper.getInstance().getBomMaterialIdVoInQuotedInfo(quotedInfo, bomId);
        List<BomMaterialIdVo> fulls = new ArrayList<>();
        fulls.addAll(idsFabrics);
        fulls.addAll(idsAccessoriesInfo);
        fulls.addAll(idsPackaging);
        fulls.addAll(idsFactoryQuoteInfo);
        fulls.addAll(idsProInst);
//        fulls.addAll(idsQuotedInfo);
        bomMaterialServiceImpl.batchInsert(fulls);

        buildBomInfo(bomInfo, fabrics, accessories, packagings, factoryQuoteInfos, quotedInfo, productionInstruction);

    }

    @Override
    public void delByProjectId(String projectId) {
        bomInfoManageMapper.delByProjectId(projectId);
    }

    @Override
    public List<String> queryBomIds(List<String> projectItemIds) {

        return bomInfoManageMapper.queryBomIds(projectItemIds);
    }

    @Override
    public List<BomInfo> selectAllBomSexAndMainColor(String projectId) {
        return bomInfoManageMapper.selectAllBomSexAndMainColor(projectId);
    }

    @Override
    public void delBomNotInThisIds(List<String> tempStyles) {
        bomInfoManageMapper.delBomNotInThisIds(tempStyles);
    }

    @Override
    public void delBomInThisIds(List<BomInfo> needDelBomList) {
        bomInfoManageMapper.delBomInThisIds(needDelBomList);
    }


    @Override
    public List<BomInfo> queryBomInfosByProjectItemIds(List<String> itemIds) {
        return bomInfoManageMapper.queryBomInfosByProjectItemIds(itemIds);
    }

    /**
     * 导出生产指示单，数量= sum（每个bom里面的成衣工厂）
     *
     * @param request
     * @param response
     * @param natrualkeys
     */
    @Override
    public void downloadProductinstruction(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws IOException, InvalidFormatException {
        List<String> itemIds = Arrays.asList(natrualkeys.split(CharConstant.COMMA));
        if (!itemIds.isEmpty()) {
            for (String bomId : itemIds) {
                BomInfo bomInfo = queryInfoByNatrualKey(bomId);
                BomManageHelper.downloadProductinstruction(bomInfo, response, request);
            }
        }


    }

    /**
     * 级联删除bom信息和project的颜色信息
     *
     * @param natrualKey
     */
    @Override
    public void delCacadBomInfo(String natrualKey) {
        //删除bom信息
        super.del(natrualKey);
        //删除对应子项目的性别颜色信息
        BomInfo info = queryInfoByNatrualKey(natrualKey);
        projectItemManageService.delSexColorInfoByBomInfo(info);


    }

    /**
     * 自动生成Bom信息，并保存DB
     * 修改子项目，处理bom的方式：
     * 已知数据库中子项目所有的款式（简称内部集合）和页面传入的所有款式(简称外部集合)；
     * 则，需要修改的bom为：内部集合 与 外部集合的交集
     * 需要新增的bom为：外部集合 - 交集；
     * 需要删除的bom为:内部集合 - 交集；
     *
     * @param info 子项目信息
     * @author zhangjh
     */
    @Override
    public List<ProjectItemBomIdVo> autoCreateBomInfoAndSave(ProjectBomInfo info) {

        List<SexColor> sexColors = info.getSexColors();
        String projectId = info.getNatrualkey();
        String customerId = info.getCustomerId();
        String areaId = info.getAreaId();
        String seriesId = info.getSeriesId();
        List<BomInfo> allStyles = selectAllBomSexAndMainColor(projectId.trim());

        //获取需要更新的bom列表
        //交集
        List<BomInfo> intersection = BomManageHelper.getInstance().getIntersection(sexColors, allStyles);

        //获取需要删除的bom列表
        List<BomInfo> needDelBomList = BomManageHelper.getInstance().getNeedDelBomList(intersection, allStyles);

        //需要增加的bom列表
        List<BomInfo> needAddBomList = BomManageHelper.getInstance().getNeedAddBomList(intersection, sexColors, info, projectId, customerId, areaId, seriesId);

        List<BomInfo> alls = new ArrayList<>();
        alls.addAll(intersection);
        alls.addAll(needAddBomList);


        if (!needDelBomList.isEmpty()) {
            //删除
            delBomInThisIds(needDelBomList);
        }
        if (!intersection.isEmpty()) {
            //更新bom
            updateBatch(intersection);
        }
        //获取需要新增的bom列表
        if (!needAddBomList.isEmpty()) {
            //新增bom
            addBatch(needAddBomList);
        }

        //增加项目和子项目的关系
        List<ProjectItemBomIdVo> ids = ProjectManageHelper.SINGLETONE.getProjectItemBomIdVo(alls);


        return ids;
    }

    @Override
    public void updateBatch(List<BomInfo> infos) {
        if (null != infos && !infos.isEmpty()) {
            for (BomInfo bomInfo : infos) {
                bomInfoManageMapper.updateInfo(bomInfo);
            }
        }

    }

}

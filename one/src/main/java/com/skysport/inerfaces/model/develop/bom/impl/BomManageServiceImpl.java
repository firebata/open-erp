package com.skysport.inerfaces.model.develop.bom.impl;

import com.skysport.core.constant.CharConstant;
import com.skysport.core.exception.SkySportException;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.constant.develop.ReturnCodeConstant;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import com.skysport.inerfaces.mapper.info.BomInfoManageMapper;
import com.skysport.inerfaces.model.develop.accessories.service.IAccessoriesService;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.bom.helper.BomManageHelper;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemManageService;
import com.skysport.inerfaces.model.develop.quoted.service.IFactoryQuoteService;
import com.skysport.inerfaces.model.develop.quoted.service.IQuotedService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     *
     */
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

            buildBomInfo(bomInfo, fabrics, accessories, packagings, factoryQuoteInfos);

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
    private void buildBomInfo(BomInfo bomInfo, List<FabricsInfo> fabrics, List<AccessoriesInfo> accessories, List<KFPackaging> packagings, List<FactoryQuoteInfo> factoryQuoteInfos) {
        bomInfo.setFabrics(fabrics);
        bomInfo.setAccessories(accessories);
        bomInfo.setPackagings(packagings);
        bomInfo.setFactoryQuoteInfos(factoryQuoteInfos);
    }

    /**
     * 修改bom信息
     *
     * @param bomInfo
     */
    @Override
    public void edit(BomInfo bomInfo) {

        super.edit(bomInfo);

        //保存面料信息
        List<FabricsInfo> fabrics = fabricsManageService.updateBatch(bomInfo.getFabricItems(), bomInfo);

        //保存辅料信息
        List<AccessoriesInfo> accessories = accessoriesService.updateBatch(bomInfo.getAccessoriesItems(), bomInfo);


        //保存包装材料信息
        List<KFPackaging> packagings = packagingService.updateBatch(bomInfo.getPackagingItems(), bomInfo);

        //保存成衣厂信息
        List<FactoryQuoteInfo> factoryQuoteInfos = factoryQuoteService.updateBatch(bomInfo.getFactoryQuoteInfos(), bomInfo);

        //保存报价信息
        quotedService.updateOrAdd(bomInfo.getQuotedInfo());


        //如果颜色修改，需要修改bom的颜色(已在上面的修改bom方法中修改)和bom所属项目的子颜色
        dealMainColor(bomInfo);


        buildBomInfo(bomInfo, fabrics, accessories, packagings, factoryQuoteInfos);
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

    @Override
    public void updateBatch(List<BomInfo> infos) {
        if (null != infos && !infos.isEmpty()) {
            for (BomInfo bomInfo : infos) {
                bomInfoManageMapper.updateInfo(bomInfo);
            }
        }

    }

}

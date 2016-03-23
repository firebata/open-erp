package com.skysport.inerfaces.model.develop.bom.impl;

import com.skysport.inerfaces.bean.develop.*;
import com.skysport.inerfaces.form.develop.BomQueryForm;
import com.skysport.inerfaces.mapper.info.BomInfoManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.develop.accessories.service.IAccessoriesService;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.packaging.service.IPackagingService;
import com.skysport.inerfaces.model.develop.quoted.service.IFactoryQuoteService;
import com.skysport.inerfaces.model.develop.quoted.service.IQuotedService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public BomInfo queryInfoByNatrualKey(String bomId) {
        BomInfo bomInfo = super.queryInfoByNatrualKey(bomId);
        if (null != bomInfo) {

            //面料集合
            List<FabricsInfo> fabricItems = fabricsManageService.queryFabricList(bomId);

            //辅料集合
            List<AccessoriesInfo> accessories = accessoriesService.queryAccessoriesList(bomId);

            //包材
            List<KFPackaging> packagings = packagingService.queryPackagingList(bomId);

            //成衣厂
            List<FactoryQuoteInfo> factoryQuoteInfos = factoryQuoteService.queryFactoryQuoteInfoList(bomId);

            bomInfo.setFabrics(fabricItems);

            bomInfo.setAccessories(accessories);

            bomInfo.setPackagings(packagings);

            bomInfo.setFactoryQuoteInfos(factoryQuoteInfos);

            //生产指示单
            buildProductionInstruction(factoryQuoteInfos);


        }
        return bomInfo;
    }

    /**
     * 查询生产指示单信息
     *
     * @param factoryQuoteInfos
     */
    private void buildProductionInstruction(List<FactoryQuoteInfo> factoryQuoteInfos) {
        if (null != factoryQuoteInfos) {
            for (FactoryQuoteInfo factoryQuoteInfo : factoryQuoteInfos) {
                KfProductionInstructionEntity productionInstruction = new KfProductionInstructionEntity();
                factoryQuoteInfo.setProductionInstruction(productionInstruction);
            }
        }

    }

    @Override
    public void edit(BomInfo bomInfo) {

        super.edit(bomInfo);

        //保存面料信息
        fabricsManageService.updateBatch(bomInfo.getFabricItems(), bomInfo);

        //保存辅料信息
        accessoriesService.updateBatch(bomInfo.getAccessoriesItems(), bomInfo);


        //保存包装材料信息
        packagingService.updateBatch(bomInfo.getPackagingItems(), bomInfo);

        //保存成衣厂信息
        factoryQuoteService.updateBatch(bomInfo.getFactoryQuoteInfos(), bomInfo);

        //保存报价信息
        quotedService.updateOrAdd(bomInfo.getQuotedInfo());

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

    @Override
    public void updateBatch(List<BomInfo> infos) {
        if (null != infos && !infos.isEmpty()) {
            for (BomInfo bomInfo : infos) {
                bomInfoManageMapper.updateInfo(bomInfo);
            }
        }

    }

}

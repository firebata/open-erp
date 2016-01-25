package com.skysport.inerfaces.model.develop.fabric.impl;

import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.join.FabricsJoinInfo;
import com.skysport.inerfaces.mapper.info.FabricsManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.develop.fabric.IFabricsJoinManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/27.
 */
public class FabricsJoinManageServiceImpl extends CommonServiceImpl<FabricsInfo> implements IFabricsJoinManageService, InitializingBean {
    @Resource(name = "fabricsManageDao")
    private FabricsManageMapper fabricsManageDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = fabricsManageDao;
    }

    @Override
    public void updateBatch(List<FabricsInfo> fabricItems) {
        if (null == fabricItems || fabricItems.isEmpty()) {
            return;
        }
        //先删除所有面料，再新增
        for (FabricsInfo fabricsInfo : fabricItems) {
            if (StringUtils.isNotBlank(fabricsInfo.getNatrualkey())) {
                fabricsManageDao.del(fabricsInfo.getNatrualkey());
                fabricsManageDao.delDetail(fabricsInfo.getNatrualkey());
                fabricsManageDao.delDosage(fabricsInfo.getNatrualkey());
                fabricsManageDao.delSp(fabricsInfo.getNatrualkey());
            }
        }
        fabricsManageDao.addBatch(fabricItems);
        //新增面料详细
        fabricsManageDao.addDetailBatch(fabricItems);
        //新增面料用量
        fabricsManageDao.addDosageBatch(fabricItems);
        //新增面料供应商信息
        fabricsManageDao.addSpBatch(fabricItems);

    }

    @Override
    public List<FabricsJoinInfo> queryFabricList(String natrualKey) {
        return null;
    }




}

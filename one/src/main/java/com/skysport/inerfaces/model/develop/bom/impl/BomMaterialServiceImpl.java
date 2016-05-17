package com.skysport.inerfaces.model.develop.bom.impl;

import com.skysport.inerfaces.bean.relation.BomMaterialIdVo;
import com.skysport.inerfaces.mapper.develop.relation.BomMaterialMapper;
import com.skysport.inerfaces.model.relation.IRelationIdDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
@Service
public class BomMaterialServiceImpl implements IRelationIdDealService<BomMaterialIdVo> {

    @Autowired
    BomMaterialMapper bomMaterialMapper;

    @Override
    public void backupRecordsToHis(List<BomMaterialIdVo> vos) {
        bomMaterialMapper.backupRecordsToHis(vos);
    }

    @Override
    public void batchInsert(List<BomMaterialIdVo> vos) {
        backupRecordsToHis(vos);
        bomMaterialMapper.batchInsert(vos);

    }

    @Override
    public List<String> queryProjectChildIdsByParentId(String projectId) {
        return null;
    }
}

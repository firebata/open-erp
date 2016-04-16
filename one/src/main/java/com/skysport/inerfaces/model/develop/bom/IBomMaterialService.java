package com.skysport.inerfaces.model.develop.bom;

import com.skysport.inerfaces.bean.relation.BomMaterialIdVo;

import java.util.List;

/**
 * 说明:bom 物料 id关系处理
 * Created by zhangjh on 2016/4/15.
 */
public interface IBomMaterialService {
    void backupRecordsToHis(List<BomMaterialIdVo> vos);

    void batchInsert(List<BomMaterialIdVo> vos);
}

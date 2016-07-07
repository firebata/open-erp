package com.skysport.interfaces.mapper.develop.relation;

import com.skysport.interfaces.bean.relation.BomMaterialIdVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
@Repository
public interface BomMaterialMapper {


    void backupRecordsToHis(List<BomMaterialIdVo> vos);

    void batchInsert(List<BomMaterialIdVo> vos);
}

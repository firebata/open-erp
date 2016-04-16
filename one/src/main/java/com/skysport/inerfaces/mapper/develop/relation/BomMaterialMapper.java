package com.skysport.inerfaces.mapper.develop.relation;

import com.skysport.inerfaces.bean.relation.BomMaterialIdVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
@Component
public interface BomMaterialMapper {


    void backupRecordsToHis(List<BomMaterialIdVo> vos);

    void batchInsert(List<BomMaterialIdVo> vos);
}

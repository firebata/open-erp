package com.skysport.inerfaces.mapper.develop;

import com.skysport.inerfaces.bean.develop.KFMaterialPantone;
import com.skysport.core.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/1/20.
 */
@Repository("kFMaterialPantoneMapper")
public interface KFMaterialPantoneMapper extends CommonMapper<KFMaterialPantone> {

    List<KFMaterialPantone> queryIds(@Param("materialId") String materialId);
}


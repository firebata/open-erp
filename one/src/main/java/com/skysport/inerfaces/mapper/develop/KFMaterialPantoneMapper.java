package com.skysport.inerfaces.mapper.develop;

import com.skysport.inerfaces.bean.develop.KFMaterialPantone;
import com.skysport.core.mapper.CommonDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/1/20.
 */
@Component("kFMaterialPantoneMapper")
public interface KFMaterialPantoneMapper extends CommonDao<KFMaterialPantone> {

    List<KFMaterialPantone> queryIds(@Param("materialId") String materialId);
}


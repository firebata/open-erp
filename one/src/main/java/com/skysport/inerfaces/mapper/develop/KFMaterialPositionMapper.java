package com.skysport.inerfaces.mapper.develop;

import com.skysport.inerfaces.bean.develop.KFMaterialPosition;
import com.skysport.core.mapper.CommonDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/10.
 */
@Component("kFMaterialPositionMapper")
public interface KFMaterialPositionMapper extends CommonDao<KFMaterialPosition> {

    List<KFMaterialPosition> queryIds(@Param("materialId") String materialId);


}

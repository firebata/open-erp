package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.info.MaterialTypeInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/17.
 */
@Repository("materialTypeManageDao")
public interface MaterialTypeManageMapper extends CommonDao<MaterialTypeInfo> {
}

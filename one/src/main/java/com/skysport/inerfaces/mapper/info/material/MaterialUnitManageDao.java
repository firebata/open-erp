package com.skysport.inerfaces.mapper.info.material;

import com.skysport.inerfaces.bean.info.MaterialUnitInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/2.
 */
@Component("materialUnitManageDao")
public interface MaterialUnitManageDao extends CommonDao<MaterialUnitInfo> {
}

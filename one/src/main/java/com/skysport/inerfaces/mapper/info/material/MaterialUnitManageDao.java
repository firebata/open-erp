package com.skysport.inerfaces.mapper.info.material;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.info.MaterialUnitInfo;
import org.springframework.stereotype.Repository;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/2.
 */
@Repository("materialUnitManageDao")
public interface MaterialUnitManageDao extends CommonDao<MaterialUnitInfo> {
}

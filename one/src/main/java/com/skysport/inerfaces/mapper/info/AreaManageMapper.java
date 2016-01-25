package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.info.AreaInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Component("areaManageDao")
public interface AreaManageMapper extends CommonDao<AreaInfo> {

}

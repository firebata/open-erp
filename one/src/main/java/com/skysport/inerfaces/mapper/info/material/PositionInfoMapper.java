package com.skysport.inerfaces.mapper.info.material;

import com.skysport.inerfaces.bean.info.MaterialPositionInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/2.
 */
@Component("positionManageDao")
public interface PositionInfoMapper extends CommonDao<MaterialPositionInfo> {
}

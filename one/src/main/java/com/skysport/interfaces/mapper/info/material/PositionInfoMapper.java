package com.skysport.interfaces.mapper.info.material;

import com.skysport.interfaces.bean.info.MaterialPositionInfo;
import com.skysport.core.mapper.CommonMapper;
import org.springframework.stereotype.Repository;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/2.
 */
@Repository("positionInfoMapper")
public interface PositionInfoMapper extends CommonMapper<MaterialPositionInfo> {
}

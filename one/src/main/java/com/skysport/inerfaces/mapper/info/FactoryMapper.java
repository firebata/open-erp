package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.info.FactoryInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Repository("factoryMapper")
public interface FactoryMapper extends CommonDao<FactoryInfo> {
}

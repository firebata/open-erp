package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.info.FactoryInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Component("factoryManageMapper")
public interface FactoryManageMapper extends CommonDao<FactoryInfo> {
}

package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.info.YearConfInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjh on 2015/6/17.
 */
@Component("yearConfManageDao")
public interface YearConfManageMapper extends CommonDao<YearConfInfo> {
    
}

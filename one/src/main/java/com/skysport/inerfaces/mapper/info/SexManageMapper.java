package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.info.SexInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjh on 2015/6/17.
 */
@Component("sexManageDao")
public interface SexManageMapper extends CommonDao<SexInfo> {

}

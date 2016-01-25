package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.info.CustomerInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjh on 2015/6/3.
 */
@Component("customerManageDao")
public interface CustomerManageMapper extends CommonDao<CustomerInfo> {

}

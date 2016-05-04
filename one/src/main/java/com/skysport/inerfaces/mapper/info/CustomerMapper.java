package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.inerfaces.bean.info.CustomerInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/3.
 */
@Repository("customerMapper")
public interface CustomerMapper extends CommonMapper<CustomerInfo> {

}

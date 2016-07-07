package com.skysport.interfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.interfaces.bean.info.CustomerInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/3.
 */
@Repository("customerMapper")
public interface CustomerMapper extends CommonMapper<CustomerInfo> {

}

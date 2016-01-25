package com.skysport.inerfaces.mapper.permission;

import com.skysport.core.bean.permission.ResourceInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/17.
 */
@Component("resourceInfoManageMapper")
public interface ResourceInfoManageMapper extends CommonDao<ResourceInfo> {


}

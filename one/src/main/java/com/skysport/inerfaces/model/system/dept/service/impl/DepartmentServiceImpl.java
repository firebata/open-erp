package com.skysport.inerfaces.model.system.dept.service.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.system.DepartmentInfo;
import com.skysport.inerfaces.mapper.system.DepartmentManageMapper;
import com.skysport.inerfaces.model.system.dept.service.IDepartmentService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 说明:
 * Created by zhangjh on 2015/12/30.
 */
@Service("departmentService")
public class DepartmentServiceImpl extends CommonServiceImpl<DepartmentInfo> implements IDepartmentService, InitializingBean {

    @Resource(name = "departmentManageMapper")
    private DepartmentManageMapper departmentManageMapper;


    @Override
    public void afterPropertiesSet() throws Exception {

        commonDao = departmentManageMapper;


    }

}

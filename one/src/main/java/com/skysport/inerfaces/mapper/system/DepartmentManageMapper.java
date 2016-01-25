package com.skysport.inerfaces.mapper.system;

import com.skysport.inerfaces.bean.system.dept.DepartmentInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * 说明:
 * Created by zhangjh on 2015/12/30.
 */
@Component("departmentManageMapper")
public interface DepartmentManageMapper extends CommonDao<DepartmentInfo> {

}

package com.skysport.inerfaces.mapper.info.material;

import com.skysport.inerfaces.bean.info.SpecificationInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Repository("specificationDao")
public interface SpecificationDao extends CommonDao<SpecificationInfo> {
}

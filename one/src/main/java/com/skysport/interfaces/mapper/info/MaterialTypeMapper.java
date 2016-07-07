package com.skysport.interfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.interfaces.bean.info.MaterialTypeInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/17.
 */
@Repository("materialTypeMapper")
public interface MaterialTypeMapper extends CommonMapper<MaterialTypeInfo> {
}

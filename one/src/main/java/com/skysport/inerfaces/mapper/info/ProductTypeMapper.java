package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.inerfaces.bean.info.ProductTypeInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Repository("productTypeMapper")
public interface ProductTypeMapper extends CommonMapper<ProductTypeInfo> {
}

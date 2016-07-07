package com.skysport.interfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.interfaces.bean.info.ProductTypeInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Repository("productTypeMapper")
public interface ProductTypeMapper extends CommonMapper<ProductTypeInfo> {
}

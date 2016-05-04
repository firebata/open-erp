package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.inerfaces.bean.info.YearConfInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/17.
 */
@Repository("yearConfMapper")
public interface YearConfMapper extends CommonMapper<YearConfInfo> {
    
}

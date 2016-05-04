package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.inerfaces.bean.info.SeriesInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by zhangjh on 2015/6/16.
 */
@Repository("seriesMapper")
public interface SeriesMapper extends CommonMapper<SeriesInfo> {
}

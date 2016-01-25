package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.info.SeriesInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjh on 2015/6/16.
 */
@Component("seriesManageDao")
public interface SeriesManageMapper extends CommonDao<SeriesInfo> {
}

package com.skysport.inerfaces.mapper.info.material;

import com.skysport.inerfaces.bean.info.FinishInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Repository("finishDao")
public interface FinishDao extends CommonDao<FinishInfo> {
}

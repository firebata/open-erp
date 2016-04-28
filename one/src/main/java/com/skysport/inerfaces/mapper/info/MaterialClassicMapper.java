package com.skysport.inerfaces.mapper.info;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.info.MaterialClassicInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/17.
 */
@Repository("materialClassicManageDao")
public interface MaterialClassicMapper extends CommonDao<MaterialClassicInfo> {
    List<SelectItem2> querySelectList2(String typeId);
}

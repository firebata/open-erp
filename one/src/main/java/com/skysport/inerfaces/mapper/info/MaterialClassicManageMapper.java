package com.skysport.inerfaces.mapper.info;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.MaterialClassicInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangjh on 2015/6/17.
 */
@Component("materialClassicManageDao")
public interface MaterialClassicManageMapper extends CommonDao<MaterialClassicInfo> {
    List<SelectItem2> querySelectList2(String typeId);
}

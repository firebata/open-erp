package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.AccessoriesInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/22.
 */
@Repository("accessoriesManageMapper")
public interface AccessoriesManageMapper extends CommonDao<AccessoriesInfo> {


    List<String> selectAllAccessoriesId(String bomId);

    void deleteAccessoriesByIds(List<String> allAccessoriesIds);

    List<AccessoriesInfo> queryAccessoriesList(String bomId);

    List<AccessoriesInfo> queryAllAccessoriesByBomId(String bomId);
}

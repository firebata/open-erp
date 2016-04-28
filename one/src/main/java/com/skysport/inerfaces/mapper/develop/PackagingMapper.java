package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.PackagingInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
@Repository("packagingMapper")
public interface PackagingMapper extends CommonDao<PackagingInfo> {


    List<String> selectAllPackagingId(String bomId);

    void deletePackagingByIds(List<String> allPackagingIds);

    List<PackagingInfo> queryPackagingList(String bomId);

    List<PackagingInfo> queryPackagingByBomId(String bomId);
}

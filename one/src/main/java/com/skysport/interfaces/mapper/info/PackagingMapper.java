package com.skysport.interfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.interfaces.bean.develop.PackagingInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
@Repository("packagingMapper")
public interface PackagingMapper extends CommonMapper<PackagingInfo> {


    List<String> selectAllPackagingId(String bomId);

    void deletePackagingByIds(List<String> allPackagingIds);

    List<PackagingInfo> queryPackagingList(String bomId);

    List<PackagingInfo> queryPackagingByBomId(String bomId);

    void updateBatchUnitAmount(@Param(value = "infos") List<PackagingInfo> infos);

    void updateBatchColorPrice(@Param(value = "infos") List<PackagingInfo> infos);
}

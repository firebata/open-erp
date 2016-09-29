package com.skysport.interfaces.mapper.info;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.interfaces.bean.develop.FabricsDetailInfo;
import com.skysport.interfaces.bean.develop.FabricsInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/29.
 */
@Repository("fabricsMapper")
public interface FabricsMapper extends CommonMapper<FabricsInfo> {

    List<FabricsInfo> queryFabricByBomId(String bomId);

    List<FabricsInfo> queryFabricList(String natrualKey);

    List<String> selectAllFabricId(String bomId);

    void deleteFabircsByIds(List<String> allFabricIds);

    void addDetail(FabricsDetailInfo fabricsInfo);


    void updateDetail(FabricsDetailInfo fabricsDetailInfo);


    List<FabricsInfo> queryAllFabricByBomId(String bomId);

    void updateBatchUnitAmount(@Param(value = "infos") List<FabricsInfo> infos);

    void updateBatchColorPrice(@Param(value = "infos") List<FabricsInfo> infos);
}

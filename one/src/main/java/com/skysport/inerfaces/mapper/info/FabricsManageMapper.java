package com.skysport.inerfaces.mapper.info;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.FabricsDetailInfo;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/29.
 */
@Repository("fabricsManageMapper")
public interface FabricsManageMapper extends CommonDao<FabricsInfo> {

    List<FabricsInfo> queryFabricByBomId(String bomId);

    List<FabricsInfo> queryFabricList(String natrualKey);

    List<String> selectAllFabricId(String bomId);

    void deleteFabircsByIds(List<String> allFabricIds);

    void addDetail(FabricsDetailInfo fabricsInfo);


    void updateDetail(FabricsDetailInfo fabricsDetailInfo);


    List<FabricsInfo> queryAllFabricByBomId(String bomId);
}

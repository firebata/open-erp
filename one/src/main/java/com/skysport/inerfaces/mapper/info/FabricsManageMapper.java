package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.develop.FabricsDetailInfo;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.MaterialSpInfo;
import com.skysport.inerfaces.bean.develop.MaterialUnitDosage;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/29.
 */
@Component("fabricsManageMapper")
public interface FabricsManageMapper extends CommonDao<FabricsInfo> {

    List<FabricsInfo> queryFabricByBomId(String bomId);

    void delDetail(String natrualkey);

    void delSp(String natrualkey);

    void addDetailBatch(List<FabricsInfo> fabricItems);

    void addDosageBatch(List<FabricsInfo> fabricItems);

    void addSpBatch(List<FabricsInfo> fabricItems);

    void delDosage(String natrualkey);

    List<FabricsInfo> queryFabricList(String natrualKey);

    List<String> selectAllFabricId(String bomId);

    void deleteFabircsByIds(List<String> allFabricIds);

    void addDetail(FabricsDetailInfo fabricsInfo);

    void addDosage(MaterialUnitDosage fabricsInfo);

    void addSp(MaterialSpInfo fabricsInfo);

    void updateDetail(FabricsDetailInfo fabricsDetailInfo);

    void updateDosage(MaterialUnitDosage materialUnitDosage);

    void updateSp(MaterialSpInfo materialSpInfo);

    List<FabricsInfo> queryAllFabricByBomId(String bomId);
}

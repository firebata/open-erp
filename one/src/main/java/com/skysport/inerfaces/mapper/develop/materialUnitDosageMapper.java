package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.MaterialUnitDosage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/21.
 */
@Repository(value = "materialUnitDosageMapper")
public interface MaterialUnitDosageMapper extends CommonMapper<MaterialUnitDosage> {

    void updateDosage(MaterialUnitDosage materialUnitDosage);

    void addDosage(MaterialUnitDosage materialUnitDosage);

    void addDosageBatch(List<FabricsInfo> fabricItems);

    void delDosage(String natrualkey);
}

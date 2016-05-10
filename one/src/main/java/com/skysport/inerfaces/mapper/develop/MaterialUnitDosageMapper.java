package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonMapper;
import com.skysport.inerfaces.bean.develop.MaterialUnitDosage;
import org.springframework.stereotype.Repository;

/**
 * 说明:
 * Created by zhangjh on 2016/4/21.
 */
@Repository(value = "materialUnitDosageMapper")
public interface MaterialUnitDosageMapper extends CommonMapper<MaterialUnitDosage> {


    void updateDosage(MaterialUnitDosage materialUnitDosage);

    void addDosage(MaterialUnitDosage materialUnitDosage);

    
}

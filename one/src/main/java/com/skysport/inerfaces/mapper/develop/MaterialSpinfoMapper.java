package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.MaterialSpInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/21.
 */
@Repository(value = "materialSpinfoMapper")
public interface MaterialSpinfoMapper extends CommonDao<MaterialSpInfo> {
    void addSp(MaterialSpInfo materialSpInfo);

    void updateSp(MaterialSpInfo materialSpInfo);

    void addSpBatch(List<FabricsInfo> fabricItems);

    void delSp(String natrualkey);
}

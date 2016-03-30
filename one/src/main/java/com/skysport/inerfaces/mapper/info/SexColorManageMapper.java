package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.develop.SexColor;
import com.skysport.core.mapper.CommonDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/11/11.
 */
@Component("sexColorManageMapper")
public interface SexColorManageMapper extends CommonDao<SexColor> {

    List<SexColor> searchInfosByProjectId(@Param(value = "projectId") String projectId);

    void updateSexColorByProjectIdAndSexId(SexColor sexColor);

    void delByProjectId(@Param(value = "projectId") String projectId);

    SexColor searchSexColorByProjectIdAndSexId(@Param(value = "projectId") String projectId, @Param(value = "sexIdChild") String sexId);
}

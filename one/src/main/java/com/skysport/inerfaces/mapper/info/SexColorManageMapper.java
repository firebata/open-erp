package com.skysport.inerfaces.mapper.info;

import com.skysport.inerfaces.bean.develop.SexColor;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/11/11.
 */
@Component("sexColorManageMapper")
public interface SexColorManageMapper extends CommonDao<SexColor> {

    List<SexColor> searchInfos2(String natrualKey);

}

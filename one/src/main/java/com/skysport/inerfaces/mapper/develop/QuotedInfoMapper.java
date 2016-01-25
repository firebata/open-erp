package com.skysport.inerfaces.mapper.develop;

import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/17.
 */
@Component("quotedInfoMapper")
public interface QuotedInfoMapper extends CommonDao<QuotedInfo> {

    List<QuotedInfo> queryListByProjectItemIds(List<String> itemIds);

    QuotedInfo queryIds(String bomId);


}

package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/17.
 */
@Repository("quotedInfoMapper")
public interface QuotedInfoMapper extends CommonDao<QuotedInfo> {

    List<QuotedInfo> queryListByProjectItemIds(@Param(value = "itemIds") List<String> itemIds, @Param(value = "step") int step);
}

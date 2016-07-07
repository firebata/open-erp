package com.skysport.interfaces.mapper.develop;

import com.skysport.core.mapper.ApproveMapper;
import com.skysport.core.mapper.CommonMapper;
import com.skysport.interfaces.bean.develop.QuotedInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/17.
 */
@Repository("quotedInfoMapper")
public interface QuotedInfoMapper extends CommonMapper<QuotedInfo>, ApproveMapper {

    List<QuotedInfo> queryListByProjectItemIds(@Param(value = "itemIds") List<String> itemIds, @Param(value = "step") int step);
}

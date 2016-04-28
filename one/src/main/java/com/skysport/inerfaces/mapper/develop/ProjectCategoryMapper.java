package com.skysport.inerfaces.mapper.develop;

import com.skysport.core.mapper.CommonDao;
import com.skysport.inerfaces.bean.develop.ProjectCategoryInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/26.
 */
@Repository("projectCategoryMapper")
public interface ProjectCategoryMapper  extends CommonDao<ProjectCategoryInfo> {


    List<ProjectCategoryInfo> queryProjectCategoryInfo(String natrualKey);
}

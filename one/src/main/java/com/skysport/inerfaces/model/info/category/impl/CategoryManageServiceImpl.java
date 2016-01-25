package com.skysport.inerfaces.model.info.category.impl;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.CategoryInfo;
import com.skysport.inerfaces.mapper.info.CategoryManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.info.category.ICategoryManageService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("categoryManageService")
public class CategoryManageServiceImpl extends CommonServiceImpl<CategoryInfo> implements ICategoryManageService, InitializingBean {
    @Resource(name = "categoryManageMapper")
    private CategoryManageMapper categoryManageMapper;

    @Override
    public void afterPropertiesSet() {
        commonDao = categoryManageMapper;
    }


    @Override
    public List<SelectItem2> querySelectListByLevelId(String levelId) {
        return categoryManageMapper.querySelectListByLevelId(levelId);
    }

    @Override
    public List<CategoryInfo> searchChildCategoryByCategoryId(String categoryId) {
        return categoryManageMapper.searchChildCategoryByCategoryId(categoryId);
    }


}

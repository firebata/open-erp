package com.skysport.inerfaces.model.info.category.impl;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.info.CategoryInfo;
import com.skysport.inerfaces.mapper.info.CategoryMapper;
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
    @Resource(name = "categoryMapper")
    private CategoryMapper categoryMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = categoryMapper;
    }


    @Override
    public List<SelectItem2> querySelectListByLevelId(String levelId) {
        return categoryMapper.querySelectListByLevelId(levelId);
    }

    @Override
    public List<CategoryInfo> searchChildCategoryByCategoryId(String categoryId) {
        return categoryMapper.searchChildCategoryByCategoryId(categoryId);
    }


}

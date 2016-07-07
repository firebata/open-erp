package com.skysport.interfaces.model.info.category;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.interfaces.bean.info.CategoryInfo;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/14.
 */
public interface ICategoryManageService extends ICommonService<CategoryInfo> {
    List<SelectItem2> querySelectListByLevelId(String levelId);

    List<CategoryInfo> searchChildCategoryByCategoryId(String categoryId);
}

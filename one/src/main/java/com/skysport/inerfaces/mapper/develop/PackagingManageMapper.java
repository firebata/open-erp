package com.skysport.inerfaces.mapper.develop;
import com.skysport.inerfaces.bean.develop.KFPackaging;
import com.skysport.inerfaces.bean.develop.MaterialSpInfo;
import com.skysport.inerfaces.bean.develop.MaterialUnitDosage;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
@Component("packagingManageMapper")
public interface PackagingManageMapper extends CommonDao<KFPackaging> {

    void updateDosage(MaterialUnitDosage materialUnitDosage);

    void updateSp(MaterialSpInfo materialSpInfo);

    void addDosage(MaterialUnitDosage materialUnitDosage);

    void addSp(MaterialSpInfo materialSpInfo);

    List<String> selectAllPackagingId(String bomId);

    void deletePackagingByIds(List<String> allPackagingIds);

    List<KFPackaging> queryPackagingList(String bomId);

    List<KFPackaging> queryPackagingByBomId(String bomId);
}

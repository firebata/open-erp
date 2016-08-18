package com.skysport.interfaces.model.init;

import com.skysport.interfaces.model.info.area.helper.AreaManageServiceHelper;
import com.skysport.interfaces.model.info.category.CategoryManageServiceHelper;
import com.skysport.interfaces.model.info.customer.helper.CustomerManageServiceHelper;
import com.skysport.interfaces.model.info.material.impl.helper.*;
import com.skysport.interfaces.model.info.material_classic.MaterialClassicManageServiceHelper;
import com.skysport.interfaces.model.info.material_type.MaterialTypeManageServiceHelper;
import com.skysport.interfaces.model.info.product_type.ProductTypeManageServiceHelper;
import com.skysport.interfaces.model.info.series.SeriesManageServiceHelper;
import com.skysport.interfaces.model.info.service.helper.PantoneManageServiceHelper;
import com.skysport.interfaces.model.info.sex.SexManageServiceHelper;
import com.skysport.interfaces.model.info.sp.helper.SpInfoHelper;
import com.skysport.interfaces.model.info.year_conf.YearConfManageServiceHelper;
import com.skysport.interfaces.model.jc.helper.*;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/6.
 */
public enum BasicInfoHelper {
    /**
     * 单例
     */
    SINGLETONE;


    public void initSystemBaseInfo() {


        //年份列表
        YearConfManageServiceHelper.SINGLETONE.refreshSelect();

        //客户信息
        CustomerManageServiceHelper.SINGLETONE.refreshSelect();

        //区域信息
        AreaManageServiceHelper.SINGLETONE.refreshSelect();

        //系列
        SeriesManageServiceHelper.SINGLETONE.refreshSelect();

        //性别属性
        SexManageServiceHelper.SINGLETONE.refreshSelect();

        //材料类型
        MaterialTypeManageServiceHelper.SINGLETONE.refreshSelect();

        //供应商列表
        SpInfoHelper.SINGLETONE.refreshSelect();

        //材质列表
        MaterialClassicManageServiceHelper.SINGLETONE.refreshSelect();

        //品类级别
        CategoryManageServiceHelper.SINGLETONE.refreshSelect();
        //patone
        PantoneManageServiceHelper.SINGLETONE.refreshSelect();

        // 品名列表
        ProductTypeManageServiceHelper.SINGLETONE.refreshSelect();

        // 纱支密度列表
        SpecificationServiceHelper.SINGLETONE.refreshSelect();

        // 染色方式列表
        DyeServiceHelper.SINGLETONE.refreshSelect();


        // 复合或涂层列表
        BondingLaminationCoatingServiceHelper.SINGLETONE.refreshSelect();

        // 膜或涂层的材质列表
        MaterialOfMembraneCoatingServicHelper.SINGLETONE.refreshSelect();

        // 膜或涂层的颜色列表
        ColorOfMembraneCoatingServiceHelper.SINGLETONE.refreshSelect();

        // 膜的厚度列表
        MembraneThicknessServiceHelper.SINGLETONE.refreshSelect();

        // 贴膜或涂层工艺列表
        WorkmanshipOfBondingLaminatingCoatingServiceHelper.SINGLETONE.refreshSelect();

        // 物料位置列表
        PositionServiceHelper.SINGLETONE.refreshSelect();

        //
        MaterialUnitServiceHelper.SINGLETONE.refreshSelect();

        // 防泼水列表
        WaterRepllentServiceHelper.SINGLETONE.refreshSelect();

        // 透湿列表
        WaterVapourPermeabilityServiceHelper.SINGLETONE.refreshSelect();

        //透气
        JcAirPermeabilityHelper.SINGLETONE.refreshSelect();

        //防蚊虫
        JcAntMosquitosHelper.SINGLETONE.refreshSelect();

        //防油
        JcOilProofHelper.SINGLETONE.refreshSelect();

        //快干
        JcQuickDryHelper.SINGLETONE.refreshSelect();

        //接缝水压
        JcSeamWaterpressureHelper.SINGLETONE.refreshSelect();

        //抗紫外线
        JcUltravioletProtectionHelper.SINGLETONE.refreshSelect();

        //水压
        JcWaterProofHelper.SINGLETONE.refreshSelect();

    }
}

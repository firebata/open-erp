package com.skysport.inerfaces.constant;

import java.io.File;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/14.
 */
public interface WebConstants extends TableNameConstants {

    String CURRENT_USER = "current_user";
    String FILE_SEPRITER = File.separator;

    String CATEGORY_A_LEVEL = "1";//一级品类
    String CATEGORY_B_LEVEL = "2";//二级品类

    int PROJECT_SEQ_NO_LENGTH = 4; //项目编号长度
    int BOM_SEQ_NO_LENGTH = 4; //BOM编号长度
    int MATERIAL_SEQ_NO_LENGTH = 5; //物料编号长度

    int FACTORY_QUOTE_SEQ_NO_LENGTH = 3; //成衣厂报价编号长度

    String FABRIC_MATERIAL_TYPE_ID = "M";//面料
    String LIN_FABRIC_MATERIAL_TYPE_ID = "L";//里料
    String ACCESSORIE_MATERIAL_TYPE_ID = "F";//辅料
    String PAKING_MATERIAL_TYPE_ID = "B";//包装材料
    String FITTING_MATERIAL_TYPE_ID = "P";//配件
    String CLOTH_MATERIAL_TYPE_ID = "C";//成衣

    String FINANCE_CONFIG = "finance_config";//财务相关
    String COMMISSION_RATE = "commission_rate";//佣金比例
    Float COMMISSION_RATE_DEFAULTVALUE = 0.05f;//佣金比例默认值

    int PROJECT_CAN_EDIT = 0;//可以修改
    int PROJECT_CANOT_EDIT = 1;//不可以修改

    String FILE_PATH = "file_path";
    String BASE_PATH = "base_path";
    String DEVELOP_PATH = "develop_path";


    String OPERATION_PATH = "operation_path";
    String TASK_PATH = "task_path";
    String USER_PATH = "user_path";

    //报价表表头
    String[] BOM_QUOTED_TITILE = {"OfferDate", "Project", "Program", "Item", "Supplier", "Descption", "Price"};
    //BOM表头
    String[] BOM_DETAIL_TITILE = {"Style", "Supplier", "Item", "Description", "Color", "Unit", "Q'TY", "CON'S", "NEED", "REMARKS"};
    String[] BOM_DETAIL_FIELD = {"BomName", "SpId", "ProductTypeId", "Description", "PantoneId", "UnitId", "OrderCount", "UnitAmount", "TotalAmount", "Remark"};
    //BOM增强表头
    String[] BOM_DETAIL_TITILE_ADVANCED = {"Series", "Style", "Supplier", "Item", "Description", "Color", "Position", "Unit", "UnitPrice(￥)", "Q'TY", "CON'S", "Need", "TotalPrice(￥)", "Remarks"};
    String[] BOM_DETAIL_FIELD_ADVANCED = {"SeriesName", "BomName", "SpId", "ProductTypeId", "Description", "PantoneId", "PositionId", "UnitId", "UnitPrice", "OrderCount", "UnitAmount", "TotalAmount", "TotalPrice", "Remark"};

    int FABRIC_ID_EXCHANGE_QUOTED = 0;

    int FABRIC_ID_EXCHANGE_BOM = 1;

    int IS_NOT_SHOW_FABRIC = 0;//报价不表显示

    int IS_SHOW_FABRIC = 1;//报价表显示


    int RESOURCE_TYPE_MENU = 0;
    int RESOURCE_TYPE_BTN = 1;
    String MENU_LEVEL_TOP_ID = "0";

    int USER_IS_UNLOCK = 0;//用户状态
    int USER_IS_LOCK = 1;

    int USER_IS_ADMIN = 1;//管理员
    int USER_IS_NOT_ADMIN = 0;//非管理员

    String WL_PROCESS_NAME = "skysportProcess";

    int NEED_TRANSFORM_COLUMN_NEME = 0;
    int NO_NEED_TRANSFORM_COLUMN_NAME = 1;

    String MATERIALTYPEITEMS = "materialTypeItems";

    String FILE_TXT = "txt";
    String FILE_IMG = "img";
    String FILE_OTHER = "other";

    String FILE_IN_PROCESS = "0";//文件暂存对象
    String FILE_IN_FINISH = "1";//文件为删除


    String FILE_KIND_USER = "1";//用户头像
    String FILE_KIND_SP = "2";//供应商头像
    String FILE_KIND_PROJECT = "3";//项目附件
    String FILE_KIND_PROJECT_ITEM = "4";//子项目附件

    String BOM_DETAIL_CN_NAME = "面辅料详细";
    String BOM_QUOTE_CN_NAME = "报价表";

    String AND = " and ";
    String OR = " or ";

    String SUFFIX_EXCEL = ".xls";

    String SP_TABLE_COLUMN = "sp_table_column";
    String PANTONE_TABLE_COLUMN = "pantone_table_column";
    String CUSTOMER_TABLE_COLUMN = "customer_table_column";
    String AREA_TABLE_COLULMN = "area_table_column";
    String SEQ_NO_LENGTH = "seqno_length";
    String SERIES_TABLE_COLULMN = "series_table_column";
    String YEAR_CONF_TABLE_COLUMN = "year_conf_table_column";
    String SEX_TABLE_COLUMN = "sex_table_column";
    String PRODUCT_TYPE_TABLE_COLUMN = "product_type_table_column";
    String MATERIAL_TYPE_TABLE_COLUMN = "material_type_table_column";
    String MATERIAL_CLASSIC_TABLE_COLUMN = "material_classic_table_column";
    String FACTORY_TABLE_COLUMN = "factory_table_column";
    String SPECIFICATION_TABLE_COLUMN = "specification_table_column";
    String BLC_TABLE_COLUMN = "blc_table_column";
    String COMC_TABLE_COLUMN = "comc_table_column";
    String DYE_TABLE_COLUMN = "dye_table_column";
    String FINISH_TABLE_COLUMN = "finish_table_column";
    String MOMC_TABLE_COLUMN = "momc_table_column";
    String MT_TABLE_COLUMN = "mt_table_column";
    String WVP_TABLE_COLUMN = "wvp_table_column";
    String WBLC_TABLE_COLUMN = "wblc_table_column";

    String FABRICS_TABLE_COLUMN = "fabrics_table_column";
    String CATEGORY_TABLE_COLUMN = "category_table_column";
    String MATERIAL_POSITION_TABLE_COLUMN = "material_position_table_column";
    String MATERIAL_UNIT_TABLE_COLUMN = "material_unit_table_column";


    String BOM_TABLE_COLULMN = "bom_table_colulmn";
    String PROJECT_TABLE_COLULMN = "project_table_colulmn";

    String USERINFO_TABLE_COLULMN = "userinfo_table_colulmn";
    String DEPT_TABLE_COLUMN_NAME = "dept_table_column_name";

    String USER_TYPE = "user_type";
    String LOCK_FLAG = "lock_flag";
    String IS_ONLINE = "is_online";
    String IS_LIMIT = "is_limit";

    String DEPT_TYPE = "dept_type";
}

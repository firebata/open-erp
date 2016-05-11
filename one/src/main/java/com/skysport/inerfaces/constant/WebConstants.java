package com.skysport.inerfaces.constant;

import java.io.File;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/14.
 */
public interface WebConstants extends TableNameConstants {

    String CURRENT_USER = "current_user";
    String FILE_SEPRITER = File.separator;
    String BLC_TYPE_FUHE = "A1";//复合
    String BLE_TYPE_TIEMO = "BBA";//贴膜
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


    int NEED_TRANSFORM_COLUMN_NEME = 0;
    int NO_NEED_TRANSFORM_COLUMN_NAME = 1;

    String MATERIALTYPEITEMS = "materialTypeItems";
    String SPITEMS = "spItems";
    String FABRICCLASSICITEMS = "fabricClassicItems";
    String PRODUCTTYPEITEMS = "productTypeItems";
    String PRODUCTTYPEITEMSSTRING = "productTypeItems";
    String SPECFICATIONITEMS = "specficationItems";
    String DYEITEMS = "dyeItems";
    String FINISHITEMS = "finishItems";
    String BLCITEMS = "blcItems";
    String MOMCITEMS = "momcItems";
    String COMOCITEMS = "comocItems";
    String WVPITEMS = "wvpItems";
    String MTITEMS = "mtItems";
    String WBLCITEMS = "wblcItems";
    String UNITITEMS = "unitItems";


    String FILE_TXT = "txt";
    String FILE_IMG = "img";
    String FILE_OTHER = "other";

    String FILE_IN_PROCESS = "0";//文件暂存对象
    String FILE_IN_FINISH = "1";//文件为删除


    //文件类型
    String FILE_KIND_USER = "1";//用户头像
    String FILE_KIND_SP = "2";//供应商头像
    String FILE_KIND_PROJECT = "3";//项目附件
    String FILE_KIND_PROJECT_ITEM = "4";//子项目附件
    String FILE_KIND_SKETCH = "5";//款式图
    String FILE_KIND_TRADEMARK = "6";//商标
    String FILE_KIND_SCALE = "7";//尺标
    String FILE_KIND_RINSING_MARKS = "8";//水洗标
    String FILE_KIND_SPECIFICATION = "9";//规格表


    String BOM_DETAIL_CN_NAME = "面辅料详细";
    String BOM_QUOTE_CN_NAME = "报价表";
    String BOM_PI_CN_NAME = "翊凯生成指示单";

    String AND = " and ";
    String OR = " or ";

    String SUFFIX_EXCEL_XLS = ".xls";
    String SUFFIX_EXCEL_XLSX = ".xlsx";
    String SP_TABLE_COLUMN = "sp_table_column";
    String PANTONE_TABLE_COLUMN = "pantone_table_column";
    String CUSTOMER_TABLE_COLUMN = "customer_table_column";
    String AREA_TABLE_COLUMN = "area_table_column";
    String SEQ_NO_LENGTH = "seqno_length";
    String SERIES_TABLE_COLUMN = "series_table_column";
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


    String BOM_TABLE_COLUMN = "bom_table_colulmn";
    String PROJECT_TABLE_COLUMN = "project_table_colulmn";
    String PROJECT_ITEM_TABLE_COLUMN = "project_table_colulmn";
    String USERINFO_TABLE_COLUMN = "userinfo_table_colulmn";
    String DEPT_TABLE_COLUMN_NAME = "dept_table_column_name";
    String PRE_QUOTE_TABLE_COLUMN_NAME = "pre_quote_table_column_name";

    String USER_TYPE = "user_type";
    String LOCK_FLAG = "lock_flag";
    String IS_ONLINE = "is_online";
    String IS_LIMIT = "is_limit";

    String DEPT_TYPE = "dept_type";

    int NEED_TO_UPDATE_PROJECT_SEX_COLOR = 1;
    int NEED_TO_DEL_PROJECT_SEX_COLOR = 2;

    // 用户组别名
    String DEVLOP_STAFF = "devlop_staff"; //开发部员工
    String DEVLOP_STAFF_GROUP = "devlop_staff_group"; //开发部员工
    String DEVLOP_MANAGER = "devlop_manager";//开发部经理
    String MANAGER_ASSISTANT = "manager_assistant";//总经理助理
    String CEO = "ceo";//总经理
    String OPERATION_STAFF = "operation_staff";
    String OPERATION_MANAGER = "operation_manager";
    String FINANCE_STAFF = "finance_staff";
    String FINANCE_MANAGER = "finance_manager";

    //
    String PROJECT_ITEM_PROCESS = "projectitemProcess";
    String APPROVE_BOM_PROCESS = "approveBomProcess";//审核Bom
    String OFFLINEWORK_PROCESS = "offlineworkProcess";//线下任务
    String APPROVE_PREQUOTED_PROCESS = "approvePreQuotedProcess";//预报价
    String COLORSIMPLES_PROCESS = "lapdipProcess";//色样
    String APPROV_EDEV_SIMPLES_PROCESS = "approveDevSamplesProcess";//审核开发样
    String APPROVE_FINALLY_QUOTED_PROCESS = "approveFinallyQuotedProcess";//审核最终的报价（和选择的打样的BOM数量一致）
    String APPROVE_FINALLY_BOM_PROCESS = "approveFinallyBomProcess";//审核BOM审核过程

    String PROJECT_ITEM_PASS = "projectItemPass";//项目审核意见

    String APPROVE_STATUS_NEW = "1";//新建
    String APPROVE_STATUS_UNDO = "2";//待审批
    String APPROVE_STATUS_REJECT = "3";//驳回
    String APPROVE_STATUS_PASS = "4";//审核通过
    String APPROVE_STATUS_OTHER = "5";//其他

    String SYSTEM_ENVIRONMENT = "system_environment";//软件产品环境
    String SYSTEM_ENVIRONMENT_CURRENT = "current";//当前软件产品环境
    String SDE = "sde";//开发环境
    String STE = "ste";//测试环境
    String SPE = "spe";//生产环境
    String STATECODE_ACTIVE = "1";
    String STATECODE_SUSPENDED = "2";
    String STATECODE_ALIVE = "0";//ACTIVE和SUSPENDED 都是ALIVE

    //pantone类型
    String pantone_kind = "pantone_kind";
    String pantone_kind_tpx_tcx = "tpx_tcx";
    String pantone_kind_c_u = "c_u";

    String MENU_SESSION_TAG = "menusTotle";


    //参考工厂报价
    int QUOTE_REFERENCE_YES = 1;


    //excel模板路径
    String RESOURCE_PATH_PI = "conf/templates/pi-20160316.xlsx";//成衣生产指示单模板
    String RESOURCE_PATH_BOM = "conf/templates/bom-20160426.xlsx";//bom物料清单模板
    String RESOURCE_PATH_QUOTE = "conf/templates/quote-20160426.xlsx";//报价模板

    int QUOTED_STEP_PRE = 0;//预报价
    int QUOTED_STEP_END = 1;//


    String PROJECT_ITEM_ID ="project_item_id";

}

/**
 * Created by zhangjh on 2015/7/10.
 */
(function ($) {
    "use strict";
    var title_type = "辅料_";
    var path = $.basepath();
    var saveAccessoriesFunURL = path + "/development/bom/saveAccessoriesFun";
    var bom_selectURL = path + "/system/baseinfo/bom_select";
    $.extend({
        initAccessories: initAccessories,
        accessoriesItems: buildAccessoriesItems
    });

    /**
     *刷新时，暂存信息
     * @param index
     * @returns {{}}
     */
    function getAccessoriesItem(index) {

        var accessoriesInfo = {};//fula基本信息
        accessoriesInfo.spId = $("#spIdF" + index).val();
        accessoriesInfo.yearCode = $("#yearCodeF" + index).val();
        accessoriesInfo.classicId = $("#classicIdF" + index).val();
        accessoriesInfo.pantoneId = $("#pantoneIdF" + index).val();
        accessoriesInfo.productTypeId = $("#productTypeIdF" + index).val();
        var positionIds = $("#positionIdsF" + index).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }
        accessoriesInfo.positionIds = materialPositions;

        accessoriesInfo.materialTypeId = $("#materialTypeIdF" + index).val();
        accessoriesInfo.techRequired = $("#techRequiredF" + index).val();
        accessoriesInfo.width = $("#widthF" + index).val();
        accessoriesInfo.length = $("#lengthF" + index).val();
        accessoriesInfo.nameNum = index;
        accessoriesInfo.accessoriesId = $("#accessoriesId" + index).val();

        accessoriesInfo.orderCount = $("#orderCountF" + index).val();
        accessoriesInfo.attritionRate = $("#attritionRateF" + index).val();
        accessoriesInfo.unitPrice = $("#unitPriceF" + index).val();
        accessoriesInfo.totalAmount = $("#totalAmountF" + index).val();
        accessoriesInfo.totalPrice = $("#totalPriceF" + index).val();

        accessoriesInfo.unitId = $("#unitIdF" + index).val();
        accessoriesInfo.unitAmount = $("#unitAmountF" + index).val();
        return accessoriesInfo;
    }

    /**
     * 保存时，获取信息
     * @param index
     * @returns {{}}
     */
    function buildAccessoriesItem(index) {
        var accessoriesItem = {};

        var accessoriesInfo = {};//fula基本信息
        accessoriesInfo.spId = $("#spIdF" + index).val();
        accessoriesInfo.yearCode = $("#yearCodeF" + index).val();
        accessoriesInfo.classicId = $("#classicIdF" + index).val();
        accessoriesInfo.pantoneId = $("#pantoneIdF" + index).val();
        accessoriesInfo.productTypeId = $("#productTypeIdF" + index).val();
        var positionIds = $("#positionIdsF" + index).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }
        accessoriesInfo.positionIds = materialPositions;

        accessoriesInfo.materialTypeId = $("#materialTypeIdF" + index).val();
        accessoriesInfo.techRequired = $("#techRequiredF" + index).val();
        accessoriesInfo.width = $("#widthF" + index).val();
        accessoriesInfo.length = $("#lengthF" + index).val();
        accessoriesInfo.nameNum = index;
        accessoriesInfo.accessoriesId = $("#accessoriesId" + index).val();

        var materialSpInfo = {};//包材用量信息
        materialSpInfo.orderCount = $("#orderCountF" + index).val();
        materialSpInfo.attritionRate = $("#attritionRateF" + index).val();
        materialSpInfo.unitPrice = $("#unitPriceF" + index).val();
        materialSpInfo.totalAmount = $("#totalAmountF" + index).val();
        materialSpInfo.totalPrice = $("#totalPriceF" + index).val();
        materialSpInfo.accessoriesId = accessoriesInfo.accessoriesId;

        var materialUnitDosage = {};//包材单位用量
        materialUnitDosage.unitId = $("#unitIdF" + index).val();
        materialUnitDosage.unitAmount = $("#unitAmountF" + index).val();
        materialUnitDosage.accessoriesId = accessoriesInfo.accessoriesId;

        accessoriesItem.accessoriesInfo = accessoriesInfo;
        accessoriesItem.materialSpInfo = materialSpInfo;
        accessoriesItem.materialUnitDosage = materialUnitDosage;

        return accessoriesItem;
    }


    /**
     * 辅料信息
     * @returns {Array}
     */
    function buildAccessoriesItems() {
        var size = $("div[id^=accessoriesAllInfoId]").length;
        var accessoriesItems = [];
        for (var index = 1; index <= size; index++) {
            var accessoriesItem = buildAccessoriesItem(index);
            accessoriesItems.push(accessoriesItem);
        }
        return accessoriesItems
    }


    function initAccessories(accessories) {
        for (var index = 0; index < accessories.length; index++) {
            addAccessories(accessories[index]);
        }
    }


    /**
     * 启动表单校验监听
     * @param _id 当前表单序号
     */
    var startBootstrapValidatorListner = function (_id) {
        $('#accessoriesFormId' + _id).bootstrapValidator({
            //live: 'disabled',
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fieldsDesc
        }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
            console.log("辅料_" + _id + "验证成功");
            saveAccessoriesFun(_id);
        });
    }

    /**
     * 初始化辅料nextIdNum的表单信息
     * @param nextIdNum 辅料序号
     * @param _accessoriesInfo 辅料表单值
     */
    function initAccessoriesFields(nextIdNum, _accessoriesInfo) {

        //保存值
        var accessoriesInfo = $.extend({}, _accessoriesInfo);

        bom.accessories.push(accessoriesInfo);

        //初始化赋值
        Object.keys(_accessoriesInfo).map(function (key) {


            //处理位置多选
            if (key == 'positionIds') {

                var arr = _accessoriesInfo[key];
                var positionIds = [];
                if (null != arr) {
                    for (var idx = 0, len = arr.length; idx < len; idx++) {
                        positionIds.push(arr[idx].positionId);
                    }
                }
                $('#positionIds' + "F" + nextIdNum).selectpicker("val", positionIds);
                //$('#sexIds').val(arr);
            }
            else {
                //下拉框
                $("#" + key + "F" + nextIdNum).val(_accessoriesInfo[key]);
            }

        });

    }

    /**
     * 添加辅料
     */
    var addAccessories = function (accessoriesInfo) {

        //if (accessoriesInfo == undefined){
        //    accessoriesInfo = accessories;
        //}

        $("div[id^=accessoriesAllInfoId]").hide(); //页面加载时，辅料全部隐藏

        $("span[id^=accessoriesEyeId]").removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");

        var size = $("div[id^=accessoriesAllInfoId]").length;

        var nextIdNum = size + 1;

        var data = {
            "accessories": [
                {
                    "currenId": nextIdNum,
                    "accessoriesDivId": "accessoriesDivId" + nextIdNum,
                    "accessoriesTitleId": "accessoriesTitleId" + nextIdNum,
                    "accessoriesIdF": "accessoriesIdF" + nextIdNum,
                    "accessoriesTitleName": title_type + nextIdNum,
                    "accessoriesEyeId": "accessoriesEyeId" + nextIdNum,
                    "accessoriesTrashId": "accessoriesTrashId" + nextIdNum,
                    "accessoriesRepeatId": "accessoriesRepeatId" + nextIdNum,
                    "accessoriesCopyId": "accessoriesCopyId" + nextIdNum,
                    "accessoriesFormId": "accessoriesFormId" + nextIdNum,
                    "accessoriesAllInfoId": "accessoriesAllInfoId" + nextIdNum,
                    "accessoriesDetailId": "accessoriesDetailId" + nextIdNum,
                    "materialTypeIdF": "materialTypeIdF" + nextIdNum,
                    "spIdF": "spIdF" + nextIdNum,
                    "yearCodeF": "yearCodeF" + nextIdNum,
                    "classicIdF": "classicIdF" + nextIdNum,
                    "pantoneIdF": "pantoneIdF" + nextIdNum,
                    "productTypeIdF": "productTypeIdF" + nextIdNum,
                    "techRequiredF": "techRequiredF" + nextIdNum,
                    "lengthF": "lengthF" + nextIdNum,
                    "widthF": "widthF" + nextIdNum,
                    "unitIdF": "unitIdF" + nextIdNum,
                    "positionIdsF": "positionIdsF" + nextIdNum,
                    "unitAmountF": "unitAmountF" + nextIdNum,
                    "orderCountF": "orderCountF" + nextIdNum,
                    "attritionRateF": "attritionRateF" + nextIdNum,
                    "unitPriceF": "unitPriceF" + nextIdNum,
                    "totalAmountF": "totalAmountF" + nextIdNum,
                    "totalPriceF": "totalPriceF" + nextIdNum
                }
            ]
        };

        var myTemplate = Handlebars.compile($("#accessories-template").html());
        $("#accessoriesItemInfo").append(myTemplate(data));

        $("div[id^=accessoriesAllInfoId]").hide(); //页面加载时，辅料全部隐藏
        $("span[id^=accessoriesEyeId]").removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");

        //加载下拉列表数据,付初始值
        reloadAccessoriesDetailSelectData(nextIdNum, function () {
            if (accessoriesInfo != undefined) {

                initAccessoriesFields(nextIdNum, accessoriesInfo);
                //表单字段监听
                startBootstrapValidatorListner(nextIdNum);


            }
        });


    }


    //删除辅料
    function deleteFraicById(index) {
        bom.accessories.splice(index - 1, 1);
    }

    /**
     * 克隆辅料
     * @param _this
     * @param index
     */
    function copyAccessories(_this, index) {
        var accessoriesItem = getAccessoriesItem(index);
        accessoriesItem.accessoriesId = null;
        addAccessories(accessoriesItem);

    }

    var deleteFun = function (id) {
        //当前辅料id
        var curId = id;
        var accessoriesArrLength = $("div[id^=accessoriesAllInfoId]").length;
        //删除当前辅料和之后的所有辅料
        for (var index = curId; index <= accessoriesArrLength; index++) {
            //$("div[id^=accessoriesAllInfoId]")
            $("#accessoriesDivId" + index).remove();
            //保存当前节点之后的数据
            if (index >= curId) {

                if (index == curId) {
                    deleteFraicById(index);
                }


                var formDataStr = $("#accessoriesFormId" + (index + 1)).serialize();
                if (formDataStr != '') {
                    saveAccessoriesById(index, formDataStr);
                }

            }
        }
        //重新生成辅料
        var maxAccessoriesId = accessoriesArrLength - 1;
        for (var index = curId; index <= maxAccessoriesId; index++) {
            addAccessories();
        }

        $("div[id^=accessoriesAllInfoId]").hide(); //全部隐藏


    }

    var doDel = function (result, id) {
        if (result) {
            deleteFun(id);
        }
    }

    var trashAccessoriesSelect = function (_this, id) {
        var saveFlag = bom.accessories[id - 1].saveFlag;
        if (saveFlag == true) {
            bootbox.confirm("辅料_" + id + "已保存，确定要删除", function (result) {
                doDel(result, id);
            });
        }
        else {
            deleteFun(id);
        }

    }

    var saveAccessories = function (_this, id) {
        $('#accessoriesFormId' + id).bootstrapValidator('validate');
    }

    var saveAccessoriesFun = function (id) {
        var formDataStr = $("#accessoriesFormId" + id).serialize();
        saveAccessoriesById(id, formDataStr);


        bom.accessories[id - 1].saveFlag = true;//已保存

    }


    var saveAccessoriesById = function (id, formDataStr) {
        var jsonObj = $.strToJson(formDataStr);
        bom.accessories[id - 1] = jsonObj;
        if (!$("#accessoriesAllInfoId" + id).is(':hidden')) {
            bom.accessories[id - 1].showFlag = true;//是否显示
        }
        bom.accessories[id - 1].currenId = id;//当前序号

        $.sendRestFulAjax(saveAccessoriesFunURL, jsonObj, 'GET', 'json', function (data) {
            _doAccessoriesSuccess_info(data, id);
        });

    }


    /**
     * 显示或者展示div
     * @param _this
     * @param id
     */
    var showOrHideAccessories = function (_this, id) {
        var accessoriesEyeId = "#accessoriesEyeId" + id;
        var accessoriesTrashId = "#accessoriesTrashId" + id;
        $("#accessoriesAllInfoId" + id).toggle(300,
            function () {
                if ($(this).is(':hidden')) {
                    $(accessoriesEyeId).removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");
                    $(accessoriesTrashId).addClass("disabled");
                }
                else {
                    $(accessoriesEyeId).removeClass("glyphicon glyphicon-eye-close").addClass("glyphicon glyphicon-eye-open");
                    $(accessoriesTrashId).removeClass("disabled");
                }
            }
        );
    }

    /**
     * 当后台的基础信息修改后，点击刷新，可以刷新cookies信息
     */
    var refreshAccessoriesSelect = function (_this, index) {
        var accessoriesItem = getAccessoriesItem(index);
        reloadBomSelect(index, function () {
            initAccessoriesFields(index, accessoriesItem);
        });
    }


    var reloadBomSelect = function (id, callback) {
        $.sendRestFulAjax(bom_selectURL, null, 'GET', 'json', function (data) {
            _doAccessoriesSuccess_info(data, id, callback);
        });
    }

    //第一次初始化下拉列表
    var reloadAccessoriesDetailSelectData = function (id, callback) {
        reloadBomSelect(id, callback);
    }


    //cookie重新赋值，给下拉列表赋值
    var _doAccessoriesSuccess_info = function (_data, id, callback) {
        //$.cookie('systemBaseMaps', JSON.stringify(_data));//JSON 数据转化成字符串
        initAccessoriesSelect(_data, id, callback);
    }

    //给下拉列表赋值
    var initAccessoriesSelect = function (_data, id, callback) {
        console.info("加载辅料" + id + "的下拉列表");
        var idNum = id;
        var data = _data;//JSON.parse($.cookie('systemBaseMaps'));//字符串转化成JSON 数据

        //材料类别
        var materialTypeIdItems = data["materialTypeItems"];
        $("#materialTypeIdF" + idNum).empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#materialTypeIdF" + idNum));
        $.each(materialTypeIdItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#materialTypeIdF" + idNum));
        });


        //供应商
        var spItems = data["spItems"];
        $("#spIdF" + idNum).empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#spIdF" + idNum));
        $.each(spItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#spIdF" + idNum));
        });

        //年份
        var yearCodeItems = data["yearItems"];
        $("#yearCodeF" + idNum).empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#yearCodeF" + idNum));
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#yearCodeF" + idNum));
        });


        //材质列表
        var classicIdItems = data["accessoriesClassicItems"];
        $("#classicIdF" + idNum).empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#classicIdF" + idNum));
        $.each(classicIdItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#classicIdF" + idNum));
        });

        //品名列表
        var productTypeIdItems = data["productTypeItems"];
        $("#productTypeIdF" + idNum).empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#productTypeIdF" + idNum));
        $.each(productTypeIdItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#productTypeIdF" + idNum));
        });


        //物料位置列表
        var positionItems = data["positionItems"];
        $("#positionIdsF" + idNum).empty();
        $.each(positionItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($("#positionIdsF" + idNum));
        });
        $('#positionIdsF' + idNum).selectpicker({noneSelectedText: '请选择...'});
        $('#positionIdsF' + idNum).selectpicker('refresh');


        // 用量单位列表
        var unitIdItems = data["unitItems"];
        $("#unitIdF" + idNum).empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#unitIdF" + idNum));
        $.each(unitIdItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#unitIdF" + idNum));
        });

        if ($.isFunction(callback)) {
            callback();
        }

    }

    /**
     *
     * @type {{bomId: string, accessoriesId: string, nameNum: string, accessoriesName: string, materialTypeId: string, classicId: string, spId: string, yearCode: string, productTypeId: string, serialNumber: string, pantoneId: string, positionIds: string, unitId: string, unitAmount: string, showFlag: boolean, saveFlag: boolean, delFlag: boolean, currenId: number}}
     */
    var accessories = {
        bomId: "",
        accessoriesId: "",
        nameNum: "",
        accessoriesName: "",
        materialTypeId: "",
        classicId: "",
        spId: "",
        yearCode: "",
        productTypeId: "",
        serialNumber: "",
        pantoneId: "",
        positionIds: "",
        unitId: "",
        unitAmount: "",
        showFlag: false,//页面是否展示
        saveFlag: false,//是否已保存
        delFlag: false,//是否已删除
        currenId: 0 //辅料下标
    }
    /**
     * 新增/修改校验字段描述
     * @returns {{name: {validators: {notEmpty: {message: string}}}, customerId: {validators: {notEmpty: {message: string}}}}}
     */
    var fieldsDesc =
    {
        materialTypeIdF: {
            validators: {
                notEmpty: {
                    message: '材料类别为必填项'
                }
            }
        },
        spIdF: {
            validators: {
                notEmpty: {
                    message: '供应商为必填项'
                }
            }
        },
        yearCodeF: {
            validators: {
                notEmpty: {
                    message: '年份为必填项'
                }
            }
        },
        classicIdF: {
            validators: {
                notEmpty: {
                    message: '材质为必填项'
                }
            }
        },
        pantoneIdF: {
            validators: {
                notEmpty: {
                    message: '颜色为必填项'
                }
            }
        },
        productTypeIdF: {
            validators: {
                notEmpty: {
                    message: '品名为必填项'
                }
            }
        },
        unitIdF: {
            validators: {
                notEmpty: {
                    message: '用量单位为必填项'
                }
            }
        },
        unitAmountF: {
            validators: {
                notEmpty: {
                    message: '用量为必填项'
                }
            }
        },
        positionIdsF: {
            validators: {
                notEmpty: {
                    message: '物料位置为必填项'
                }
            }
        },
        orderCountF: {
            validators: {
                notEmpty: {
                    message: '尺码总数量为必填项'
                }
            }
        },
        attritionRateF: {
            validators: {
                notEmpty: {
                    message: '损耗率为必填项'
                }
            }
        },
        unitPriceF: {
            validators: {
                notEmpty: {
                    message: '单价为必填项'
                }
            }
        },
        totalAmountF: {
            validators: {
                notEmpty: {
                    message: '尺码总数量为必填项'
                }
            }
        },
        totalPriceF: {
            validators: {
                notEmpty: {
                    message: '总价为必填项'
                }
            }
        }

    };

    /**
     * 面板内容初始化
     */
    $(document).ready(function () {

        //$("#bomDescDetail").hide();

        //第一步，页面加载时，加载所有数据/分步加载数据

        //页面加载时，辅料全部隐藏
        $("div[id^=accessoriesAllInfoId]").hide();

        //赋初始化值：保存标志，显示标志
        var size = $("div[id^=accessoriesAllInfoId]").length;
        bom.accessories[size - 1] = accessories;
        //点击添加辅料
        $("#imgAddAccessories").click(function () {
            addAccessories();
            //赋初始化值：保存标志，显示标志
            var size = $("div[id^=accessoriesAllInfoId]").length;
            bom.accessories[size - 1] = accessories;
        });

    });
    var bom = {
        projectId: "",//项目id
        bomId: "",//BOMId
        accessories: [],//所有辅料
    }
    window.saveAccessories = saveAccessories;
    window.refreshAccessoriesSelect = refreshAccessoriesSelect;
    window.trashAccessoriesSelect = trashAccessoriesSelect;
    window.showOrHideAccessories = showOrHideAccessories;
    window.copyAccessories = copyAccessories;

}(jQuery));

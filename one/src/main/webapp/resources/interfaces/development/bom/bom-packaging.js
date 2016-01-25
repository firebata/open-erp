/**
 * Created by zhangjh on 2015/7/10.
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var savePackagingFunURL = path + "/development/bom/savePackagingFun";
    var bom_selectURL = path + "/system/baseinfo/bom_select";
    var title_type = "包材_";
    $.extend({
        initPackaging: initPackaging,
        packagingItems: buildPackagingItems
    });


    function buildPackagingItem(index) {
        var packagingItem = {};

        var kfPackaging = {};//基本信息
        kfPackaging.spId = $("#spIdP" + index).val();
        kfPackaging.yearCode = $("#yearCodeP" + index).val();
        kfPackaging.classicId = $("#classicIdP" + index).val();
        kfPackaging.pantoneId = $("#pantoneIdP" + index).val();
        kfPackaging.productTypeId = $("#productTypeIdP" + index).val();

        var positionIds = $("#positionIdsP" + index).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }
        kfPackaging.positionIds = materialPositions;

        kfPackaging.materialTypeId = $("#materialTypeIdP" + index).val();
        kfPackaging.techRequired = $("#techRequiredP" + index).val();
        kfPackaging.width = $("#widthP" + index).val();
        kfPackaging.length = $("#lengthP" + index).val();
        kfPackaging.nameNum = index;
        kfPackaging.packagingId = $("#packagingIdP" + index).val();

        var materialSpInfo = {};//包材用量信息
        materialSpInfo.orderCount = $("#orderCountP" + index).val();
        materialSpInfo.attritionRate = $("#attritionRateP" + index).val();
        materialSpInfo.unitPrice = $("#unitPriceP" + index).val();
        materialSpInfo.totalAmount = $("#totalAmountP" + index).val();
        materialSpInfo.totalPrice = $("#totalPriceP" + index).val();
        materialSpInfo.packagingId = kfPackaging.packagingId;

        var materialUnitDosage = {};//包材单位用量
        materialUnitDosage.unitId = $("#unitIdP" + index).val();
        materialUnitDosage.unitAmount = $("#unitAmountP" + index).val();
        materialUnitDosage.packagingId = kfPackaging.packagingId;

        packagingItem.kfPackaging = kfPackaging;
        packagingItem.materialSpInfo = materialSpInfo;
        packagingItem.materialUnitDosage = materialUnitDosage;
        return packagingItem;
    }


    /**
     * 包材信息
     * @returns {Array}
     */
    function buildPackagingItems() {
        var size = $("div[id^=packagingAllInfoId]").length;
        var packagingItems = [];
        for (var index = 1; index <= size; index++) {
            var packagingItem = buildPackagingItem(index);
            packagingItems.push(packagingItem);
        }
        return packagingItems;
    }

    function initPackaging(packagings) {
        for (var index = 0; index < packagings.length; index++) {
            addPackaging(packagings[index]);
        }
    }


    /**
     * 启动表单校验监听
     * @param _id 当前表单序号
     */
    var startBootstrapValidatorListner = function (_id) {
        $('#packagingFormId' + _id).bootstrapValidator({
            //live: 'disabled',
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fieldsDesc
        }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
            console.log(title_type + _id + "验证成功");
            savePackagingFun(_id);
        });
    }

    /**
     * 初始化包材nextIdNum的表单信息
     * @param nextIdNum 包材序号
     * @param _packagingInfo 包材表单值
     */
    function initPackagingFields(nextIdNum, _packagingInfo) {

        //保存值
        var packagingInfo = $.extend({}, _packagingInfo);

        bom.packagings.push(packagingInfo);

        //初始化赋值
        Object.keys(_packagingInfo).map(function (key) {

            //处理位置多选
            if (key == 'positionIds') {
                var arr = _packagingInfo[key];
                var positionIds = [];
                if (null != arr) {
                    for (var idx = 0, len = arr.length; idx < len; idx++) {
                        positionIds.push(arr[idx].positionId);
                    }
                }
                $('#positionIds' + "P" + nextIdNum).selectpicker("val", positionIds);
                //$('#sexIds').val(arr);
            }
            else {
                //下拉框
                $("#" + key + "P" + nextIdNum).val(_packagingInfo[key]);
            }

        });

    }

    /**
     * 添加包材
     */
    var addPackaging = function (packagingInfo) {

        //if (packagingInfo == undefined){
        //    packagingInfo = packaging;
        //}


        var size = $("div[id^=packagingAllInfoId]").length;

        var nextIdNum = size + 1;

        var data = {
            "packaging": [
                {
                    "currenId": nextIdNum,
                    "packagingDivId": "packagingDivId" + nextIdNum,
                    "packagingTitleId": "packagingTitleId" + nextIdNum,
                    "packagingIdP": "packagingIdP" + nextIdNum,
                    "packagingTitleName": title_type + nextIdNum,
                    "packagingEyeId": "packagingEyeId" + nextIdNum,
                    "packagingTrashId": "packagingTrashId" + nextIdNum,
                    "packagingRepeatId": "packagingRepeatId" + nextIdNum,
                    "packagingCopyId": "packagingCopyId" + nextIdNum,
                    "packagingFormId": "packagingFormId" + nextIdNum,
                    "packagingAllInfoId": "packagingAllInfoId" + nextIdNum,
                    "packagingDetailId": "packagingDetailId" + nextIdNum,
                    "materialTypeIdP": "materialTypeIdP" + nextIdNum,
                    "spIdP": "spIdP" + nextIdNum,
                    "yearCodeP": "yearCodeP" + nextIdNum,
                    "classicIdP": "classicIdP" + nextIdNum,
                    "pantoneIdP": "pantoneIdP" + nextIdNum,
                    "productTypeIdP": "productTypeIdP" + nextIdNum,
                    "techRequiredP": "techRequiredP" + nextIdNum,
                    "lengthP": "lengthP" + nextIdNum,
                    "widthP": "widthP" + nextIdNum,
                    "unitIdP": "unitIdP" + nextIdNum,
                    "positionIdsP": "positionIdsP" + nextIdNum,
                    "unitAmountP": "unitAmountP" + nextIdNum,
                    "orderCountP": "orderCountP" + nextIdNum,
                    "attritionRateP": "attritionRateP" + nextIdNum,
                    "unitPriceP": "unitPriceP" + nextIdNum,
                    "totalAmountP": "totalAmountP" + nextIdNum,
                    "totalPriceP": "totalPriceP" + nextIdNum
                }
            ]
        };

        var myTemplate = Handlebars.compile($("#packaging-template").html());
        $("#packagingItemInfo").append(myTemplate(data));

        $("div[id^=packagingAllInfoId]").hide(); //页面加载时，包材全部隐藏

        $("span[id^=packagingEyeId]").removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");

        //加载下拉列表数据,付初始值
        reloadPackagingDetailSelectData(nextIdNum, function () {
            if (packagingInfo != undefined) {


                initPackagingFields(nextIdNum, packagingInfo);

                //表单字段监听
                startBootstrapValidatorListner(nextIdNum);

            }
        });


    }


    //删除包材
    function deletePackagingById(index) {
        bom.packagings.splice(index - 1, 1);
    }

    function copyPackaging(_this, index) {
        var packagingItem = getPackagingItem(index);
        packagingItem.packagingId = null;
        addPackaging(packagingItem);

        //if (bom.packagings[index] == undefined || $.trim(bom.packagings[index]) == '') {
        //    bootbox.alert("请先保存包材_" + index);
        //}
    }

    var deleteFun = function (id) {
        //当前包材id
        var curId = id;
        var packagingArrLength = $("div[id^=packagingAllInfoId]").length;
        //删除当前包材和之后的所有包材
        for (var index = curId; index <= packagingArrLength; index++) {
            //$("div[id^=packagingAllInfoId]")

            $("#packagingDivId" + index).remove();

            //保存当前节点之后的数据
            if (index >= curId) {

                if (index == curId) {
                    deletePackagingById(index);
                }


                var formDataStr = $("#packagingFormId" + (index + 1)).serialize();
                if (formDataStr != '') {
                    savePackagingById(index, formDataStr);
                }

            }
        }
        //重新生成包材
        var maxPackagingId = packagingArrLength - 1;
        for (var index = curId; index <= maxPackagingId; index++) {
            addPackaging();
        }

        $("div[id^=packagingAllInfoId]").hide(); //全部隐藏


    }

    var doDel = function (result, id) {
        if (result) {
            deleteFun(id);
        }
    }

    var trashPackagingSelect = function (_this, id) {
        var saveFlag = bom.packagings[id - 1].saveFlag;
        if (saveFlag == true) {
            bootbox.confirm(title_type + id + "已保存，确定要删除", function (result) {
                doDel(result, id);
            });
        }
        else {
            deleteFun(id);
        }

    }

    var savePackaging = function (_this, id) {
        $('#packagingFormId' + id).bootstrapValidator('validate');
    }

    var savePackagingFun = function (id) {
        var formDataStr = $("#packagingFormId" + id).serialize();
        savePackagingById(id, formDataStr);


        bom.packagings[id - 1].saveFlag = true;//已保存

    }


    var savePackagingById = function (id, formDataStr) {
        var jsonObj = $.strToJson(formDataStr);
        bom.packagings[id - 1] = jsonObj;
        if (!$("#packagingAllInfoId" + id).is(':hidden')) {
            bom.packagings[id - 1].showFlag = true;//是否显示
        }
        bom.packagings[id - 1].currenId = id;//当前序号
        $.sendRestFulAjax(savePackagingFunURL, jsonObj, 'GET', 'json', function (data) {
            _doPackagingSuccess_info(data, id);
        });
    }


    /**
     * 显示或者展示div
     * @param _this
     * @param id
     */
    var showOrHidePackaging = function (_this, id) {
        var packagingEyeId = "#packagingEyeId" + id;
        var packagingTrashId = "#packagingTrashId" + id;
        $("#packagingAllInfoId" + id).toggle(300,
            function () {
                if ($(this).is(':hidden')) {
                    $(packagingEyeId).removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");
                    $(packagingTrashId).addClass("disabled");
                }
                else {
                    $(packagingEyeId).removeClass("glyphicon glyphicon-eye-close").addClass("glyphicon glyphicon-eye-open");
                    $(packagingTrashId).removeClass("disabled");
                }
            }
        );
    }


    /**
     * 包材信息
     * @returns {Array}
     */
    function getPackagingItem(index) {

        var kfPackaging = {};//基本信息

        kfPackaging.spId = $("#spIdP" + index).val();
        kfPackaging.yearCode = $("#yearCodeP" + index).val();
        kfPackaging.classicId = $("#classicIdP" + index).val();
        kfPackaging.pantoneId = $("#pantoneIdP" + index).val();
        kfPackaging.productTypeId = $("#productTypeIdP" + index).val();

        var positionIds = $("#positionIdsP" + index).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }
        kfPackaging.positionIds = materialPositions;

        kfPackaging.materialTypeId = $("#materialTypeIdP" + index).val();
        kfPackaging.techRequired = $("#techRequiredP" + index).val();
        kfPackaging.width = $("#widthP" + index).val();
        kfPackaging.length = $("#lengthP" + index).val();
        kfPackaging.nameNum = index;
        kfPackaging.packagingId = $("#packagingIdP" + index).val();

        kfPackaging.orderCount = $("#orderCountP" + index).val();
        kfPackaging.attritionRate = $("#attritionRateP" + index).val();
        kfPackaging.unitPrice = $("#unitPriceP" + index).val();
        kfPackaging.totalAmount = $("#totalAmountP" + index).val();
        kfPackaging.totalPrice = $("#totalPriceP" + index).val();

        kfPackaging.unitId = $("#unitIdP" + index).val();
        kfPackaging.unitAmount = $("#unitAmountP" + index).val();

        return kfPackaging;
    }

    /**
     * 当后台的基础信息修改后，点击刷新，
     */
    var refreshPackagingSelect = function (_this, id) {
        var packagingItem = getPackagingItem(id);
        reloadBomSelect(id, function () {
            initPackagingFields(id, packagingItem);
        });
    }


    var reloadBomSelect = function (id, callback) {
        $.sendRestFulAjax(bom_selectURL, null, 'GET', 'json', function (data) {
            _doPackagingSuccess_info(data, id, callback);
        });
    }

    //第一次初始化下拉列表
    var reloadPackagingDetailSelectData = function (id, callback) {
        reloadBomSelect(id, callback);

        //if ($.cookie('systemBaseMaps') == undefined) {
        //    //第一次初始化下拉列表，存放到cookies中
        //    $.sendRestFulAjax(path + "/system/baseinfo/bom_select", null, 'GET', 'json', function (data) {
        //        _doPackagingSuccess_info(data, id);
        //    });
        //}
        //else {
        //    //第二次，直接从cookies中读取
        //    initPackagingSelect(nextIdNum);
        //}
    }


    //cookie重新赋值，给下拉列表赋值
    var _doPackagingSuccess_info = function (_data, id, callback) {
        //$.cookie('systemBaseMaps', JSON.stringify(_data));//JSON 数据转化成字符串
        initPackagingSelect(_data, id, callback);
    }

    //给下拉列表赋值
    var initPackagingSelect = function (_data, id, callback) {
        console.info("加载包材" + id + "的下拉列表");
        var idNum = id;
        var data = _data;//JSON.parse($.cookie('systemBaseMaps'));//字符串转化成JSON 数据

        //材料类别
        var materialTypeIdItems = data["materialTypeItems"];
        var materialTypeIdPId = "#materialTypeIdP" + idNum;
        $(materialTypeIdPId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(materialTypeIdPId));
        $.each(materialTypeIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(materialTypeIdPId));
        });


        //供应商
        var spItems = data["spItems"];
        var spIdPId = "#spIdP" + idNum;
        $(spIdPId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(spIdPId));
        $.each(spItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(spIdPId));
        });

        //年份
        var yearCodeItems = data["yearItems"];
        var yearCodePId = "#yearCodeP" + idNum;
        $(yearCodePId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(yearCodePId));
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(yearCodePId));
        });


        //材质列表
        var classicIdItems = data["accessoriesClassicItems"];
        var classicIdPId = "#classicIdP" + idNum;
        $(classicIdPId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(classicIdPId));
        $.each(classicIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(classicIdPId));
        });

        //品名列表
        var productTypeIdItems = data["productTypeItems"];
        var productTypeIdPId = "#productTypeIdP" + idNum;
        $(productTypeIdPId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(productTypeIdPId));
        $.each(productTypeIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(productTypeIdPId));
        });


        //物料位置列表
        var positionItems = data["positionItems"];
        var positionIdsPId = "#positionIdsP" + idNum;
        $(positionIdsPId).empty();
        $.each(positionItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(positionIdsPId));
        });
        $(positionIdsPId).selectpicker({noneSelectedText: '请选择...'});
        $(positionIdsPId).selectpicker('refresh');


        // 用量单位列表
        var unitIdItems = data["unitItems"];
        var unitIdPId = "#unitIdP" + idNum;
        $(unitIdPId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(unitIdPId));
        $.each(unitIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(unitIdPId));
        });

        if ($.isFunction(callback)) {
            callback();
        }

    }

    /**
     *
     * @type {{bomId: string, packagingId: string, nameNum: string, packagingName: string, materialTypeId: string, classicId: string, spId: string, yearCode: string, productTypeId: string, serialNumber: string, pantoneId: string, positionIds: string, unitId: string, unitAmount: string, showFlag: boolean, saveFlag: boolean, delFlag: boolean, currenId: number}}
     */
    var packaging = {
        bomId: "",
        packagingId: "",
        nameNum: "",
        packagingName: "",
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
        currenId: 0 //包材下标
    }
    /**
     * 新增/修改校验字段描述
     * @returns {{name: {validators: {notEmpty: {message: string}}}, customerId: {validators: {notEmpty: {message: string}}}}}
     */
    var fieldsDesc =
    {
        materialTypeId: {
            validators: {
                notEmpty: {
                    message: '材料类别为必填项'
                }
            }
        },
        spId: {
            validators: {
                notEmpty: {
                    message: '供应商为必填项'
                }
            }
        },
        yearCode: {
            validators: {
                notEmpty: {
                    message: '年份为必填项'
                }
            }
        },
        classicId: {
            validators: {
                notEmpty: {
                    message: '材质为必填项'
                }
            }
        },
        pantoneId: {
            validators: {
                notEmpty: {
                    message: '颜色为必填项'
                }
            }
        },
        productTypeId: {
            validators: {
                notEmpty: {
                    message: '品名为必填项'
                }
            }
        },
        unitId: {
            validators: {
                notEmpty: {
                    message: '用量单位为必填项'
                }
            }
        },
        unitAmount: {
            validators: {
                notEmpty: {
                    message: '用量为必填项'
                }
            }
        },
        positionIds: {
            validators: {
                notEmpty: {
                    message: '物料位置为必填项'
                }
            }
        },
        orderCount: {
            validators: {
                notEmpty: {
                    message: '尺码总数量为必填项'
                }
            }
        },
        attritionRate: {
            validators: {
                notEmpty: {
                    message: '损耗率为必填项'
                }
            }
        },
        unitPrice: {
            validators: {
                notEmpty: {
                    message: '单价为必填项'
                }
            }
        },
        totalAmount: {
            validators: {
                notEmpty: {
                    message: '尺码总数量为必填项'
                }
            }
        },
        totalPrice: {
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

        //页面加载时，包材全部隐藏
        $("div[id^=packagingAllInfoId]").hide();

        //点击添加包材
        $("#imgAddPackaging").click(function () {
            addPackaging();
            //赋初始化值：保存标志，显示标志
            var size = $("div[id^=packagingAllInfoId]").length;
            bom.packagings[size - 1] = packaging;
        });

    });
    var bom = {
        projectId: "",//项目id
        bomId: "",//BOMId
        packagings: [],//所有包材
    }
    window.savePackaging = savePackaging;
    window.refreshPackagingSelect = refreshPackagingSelect;
    window.trashPackagingSelect = trashPackagingSelect;
    window.showOrHidePackaging = showOrHidePackaging;
    window.copyPackaging = copyPackaging;

}(jQuery));

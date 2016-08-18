/**
 * Created by zhangjh on 2015/7/10.
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    //var savePackagingFunURL = path + "/development/bom/savePackagingFun";
    var bom_selectURL = path + "/system/baseinfo/bom_select";
    var title_type = "包材_";
    $.extend({
        initPackaging: initPackaging,
        buildPackagingItems: buildPackagingItems,
        refreshAllPackagingId: refreshAllPackagingId
    });

    /**
     * 刷新所有的包装材料id
     * @param packagings
     */
    function refreshAllPackagingId(packagings) {
        if (null != packagings) {
            for (var idx = 0, len = packagings.length; idx < len; idx++) {
                var packaging = packagings[idx];
                var packagingId = packaging.packagingId;
                var serialNumber = packaging.serialNumber;
                if (null != fabricId) {
                    $("#packagingIdP" + serialNumber).val(packagingId);
                }

            }
        }
    }


    /**
     * 包材信息
     * @returns {Array}
     */
    function buildPackagingItems() {
        var size = $("div[id^=packagingAllInfoId]").length;
        var packagingItems = [];
        for (var idx = 1; idx <= size; idx++) {
            var packagingItem = buildPackagingItem(idx);
            packagingItems.push(packagingItem);
        }
        return packagingItems;
    }

    function initPackaging(packagings) {
        for (var idx = 0; idx < packagings.length; idx++) {
            addPackaging(packagings[idx]);
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
            console.log(title_type + _id + "验证成功" + e);
            savePackagingFun(_id);
        });
    };

    /**
     * 初始化包材idNum的表单信息
     * @param idNum 包材序号
     * @param _packagingInfo 包材表单值
     */
    function initPackagingFields(idNum, _packagingInfo) {

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
                $('#positionIds' + "P" + idNum).selectpicker("val", positionIds);
                //$('#sexIds').val(arr);
            }
            else {
                //下拉框
                $("#" + key + "P" + idNum).val(_packagingInfo[key]);
            }

        });


        var pantoneIdsArr = _packagingInfo["pantoneIds"];
        if (pantoneIdsArr != null) {
            var _pantoneIdsArr = $.turnPantoneInfoToSelectOption(pantoneIdsArr);
            var $id = $('#pantoneIdsP' + idNum);
            $.reloadPantoneId($id, _pantoneIdsArr);
        }

    }

    var buildMyTemplateData = function (idNum) {
        return {
            "packaging": [
                {
                    "currenId": idNum,
                    "packagingDivId": "packagingDivId" + idNum,
                    "packagingTitleId": "packagingTitleId" + idNum,
                    "packagingIdP": "packagingIdP" + idNum,
                    "serialNumberP": "serialNumberP" + idNum,
                    "packagingTitleName": title_type + idNum,
                    "packagingsNameP": "packagingsNameP" + idNum,
                    "packagingEyeId": "packagingEyeId" + idNum,
                    "packagingTrashId": "packagingTrashId" + idNum,
                    "packagingRepeatId": "packagingRepeatId" + idNum,
                    "packagingCopyId": "packagingCopyId" + idNum,
                    "packagingFormId": "packagingFormId" + idNum,
                    "packagingAllInfoId": "packagingAllInfoId" + idNum,
                    "packagingDetailId": "packagingDetailId" + idNum,
                    "materialTypeIdP": "materialTypeIdP" + idNum,
                    "spIdP": "spIdP" + idNum,
                    "yearCodeP": "yearCodeP" + idNum,
                    "classicIdP": "classicIdP" + idNum,
                    "pantoneIdsP": "pantoneIdsP" + idNum,
                    "productTypeIdP": "productTypeIdP" + idNum,
                    "techRequiredP": "techRequiredP" + idNum,
                    "lengthP": "lengthP" + idNum,
                    "widthP": "widthP" + idNum,
                    "remarkP":"remarkP"+idNum,
                    "unitIdP": "unitIdP" + idNum,
                    "positionIdsP": "positionIdsP" + idNum,
                    "positionIdBlP": "positionIdBlP" + idNum,
                    "unitAmountP": "unitAmountP" + idNum,
                    "orderCountP": "orderCountP" + idNum,
                    "attritionRateP": "attritionRateP" + idNum,
                    "unitPriceP": "unitPriceP" + idNum,
                    "colorPriceP": "colorPriceP" + idNum,
                    "colorAmountP": "colorAmountP" + idNum,
                    "totalAmountP": "totalAmountP" + idNum,
                    "totalPriceP": "totalPriceP" + idNum
                }
            ]
        };
    };
    /**
     * 添加包材
     */
    var addPackaging = function (packagingInfo) {

        var size = $("div[id^=packagingAllInfoId]").length;

        var idNum = size + 1;

        var data = buildMyTemplateData(idNum);

        var myTemplate = Handlebars.compile($("#packaging-template").html());
        $("#packagingItemInfo").append(myTemplate(data));

        $("div[id^=packagingAllInfoId]").hide(); //页面加载时，包材全部隐藏

        $("span[id^=packagingEyeId]").removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");

        //加载下拉列表数据,付初始值
        reloadPackagingDetailSelectData(idNum, function () {
            if (packagingInfo != undefined) {

                initPackagingFields(idNum, packagingInfo);
                //表单字段监听
                startBootstrapValidatorListner(idNum);
            } else {
                var $id = $('#pantoneIdsP' + idNum);
                $.reloadPantoneId($id, []);
            }
        });


    };


    //删除包材
    function deletePackagingById(idNum) {
        bom.packagings.splice(idNum - 1, 1);
    }

    function copyPackaging(_this, idNum) {
        var packagingItem = getPackagingItem(idNum);
        packagingItem.packagingId = null;
        addPackaging(packagingItem);

    }

    var deleteFun = function (idNum) {
        //当前包材idNum
        var $packagingAllInfoId = $("div[id^=packagingAllInfoId]");
        var packagingArrLength = $packagingAllInfoId.length;
        //删除当前包材和之后的所有包材
        //noinspection JSDuplicatedDeclaration
        for (var idx = idNum; idx <= packagingArrLength; idx++) {
            //$("div[id^=packagingAllInfoId]")

            $("#packagingDivId" + idx).remove();

            //保存当前节点之后的数据
            if (idx >= idNum) {

                if (idx == idNum) {
                    deletePackagingById(idx);
                }


                var formDataStr = $("#packagingFormId" + (idx + 1)).serialize();
                if (formDataStr != '') {
                    savePackagingById(idx, formDataStr);
                }

            }
        }
        //重新生成包材
        var maxPackagingId = packagingArrLength - 1;
        //循环增加包材
        for (var idx = idNum; idx <= maxPackagingId; idx++) addPackaging();

        $packagingAllInfoId.hide(); //全部隐藏


    };

    var doDel = function (result, idNum) {
        if (result) {
            deleteFun(idNum);
        }
    };

    var trashPackagingSelect = function (_this, idNum) {
        var saveFlag = bom.packagings[idNum - 1].saveFlag;
        if (saveFlag == true) {
            bootbox.confirm(title_type + idNum + "已保存，确定要删除", function (result) {
                doDel(result, idNum);
            });
        }
        else {
            deleteFun(idNum);
        }

    };

    var savePackaging = function (_this, idNum) {
        $('#packagingFormId' + idNum).bootstrapValidator('validate');
    };

    var savePackagingFun = function (idNum) {
        var formDataStr = $("#packagingFormId" + idNum).serialize();
        savePackagingById(idNum, formDataStr);


        bom.packagings[idNum - 1].saveFlag = true;//已保存

    };


    var savePackagingById = function (idNum, formDataStr) {
        var jsonObj = $.strToJson(formDataStr);
        bom.packagings[idNum - 1] = jsonObj;
        if (!$("#packagingAllInfoId" + idNum).is(':hidden')) {
            bom.packagings[idNum - 1].showFlag = true;//是否显示
        }
        bom.packagings[idNum - 1].currenId = idNum;//当前序号
        //$.sendRestFulAjax(savePackagingFunURL, jsonObj, 'GET', 'json', function (data) {
        //    _doPackagingSuccess_info(data, idNum);
        //});
    };


    /**
     * 显示或者展示div
     * @param _this
     * @param idNum
     */
    var showOrHidePackaging = function (_this, idNum) {
        var packagingEyeId = "#packagingEyeId" + idNum;
        var packagingTrashId = "#packagingTrashId" + idNum;
        $("#packagingAllInfoId" + idNum).toggle(300,
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
    };


    /**
     * 当后台的基础信息修改后，点击刷新，
     */
    var refreshPackagingSelect = function (_this, idNum) {
        var packagingItem = getPackagingItem(idNum);
        reloadBomSelect(idNum, function () {
            initPackagingFields(idNum, packagingItem);
        });
        var $id = $('#pantoneIdsP' + idNum);
        $.reloadPantoneId($id, packagingItem['pantoneIds']);
    };


    var reloadBomSelect = function (idNum, callback) {
        $.sendRestFulAjax(bom_selectURL, null, 'GET', 'json', function (data) {
            _doPackagingSuccess_info(data, idNum, callback);
        });
    };

    //第一次初始化下拉列表
    var reloadPackagingDetailSelectData = function (idNum, callback) {
        reloadBomSelect(idNum, callback);

    };


    //cookie重新赋值，给下拉列表赋值
    var _doPackagingSuccess_info = function (_data, idNum, callback) {
        //$.cookie('systemBaseMaps', JSON.stringify(_data));//JSON 数据转化成字符串
        initPackagingSelect(_data, idNum, callback);
    };

    //给下拉列表赋值
    var initPackagingSelect = function (_data, idNum, callback) {
        //console.info("加载包材" + idNum + "的下拉列表");
        var data = _data;//JSON.parse($.cookie('systemBaseMaps'));//字符串转化成JSON 数据

        //材料类别
        var materialTypeIdItems = data["materialTypeItems"];
        var materialTypeIdPId = "#materialTypeIdP" + idNum;
        $(materialTypeIdPId).empty();
        $("<option></option>").val('').text("...请选择...").appendTo($(materialTypeIdPId));
        $.each(materialTypeIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(materialTypeIdPId));
        });


        //供应商
        var spItems = data["spItems"];
        var spIdPId = "#spIdP" + idNum;
        $(spIdPId).empty();
        $("<option></option>").val('').text("...请选择...").appendTo($(spIdPId));
        $.each(spItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(spIdPId));
        });

        //年份
        var yearCodeItems = data["yearItems"];
        var yearCodePId = "#yearCodeP" + idNum;
        $(yearCodePId).empty();
        $("<option></option>").val('').text("...请选择...").appendTo($(yearCodePId));
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(yearCodePId));
        });


        //材质列表
        var classicIdItems = data["accessoriesClassicItems"];
        var classicIdPId = "#classicIdP" + idNum;
        $(classicIdPId).empty();
        $("<option></option>").val('').text("...请选择...").appendTo($(classicIdPId));
        $.each(classicIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(classicIdPId));
        });

        //品名列表
        var productTypeIdItems = data["productTypeItems"];
        var productTypeIdPId = "#productTypeIdP" + idNum;
        $(productTypeIdPId).empty();
        $("<option></option>").val('').text("...请选择...").appendTo($(productTypeIdPId));
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
        $(positionIdsPId).selectpicker({noneSelectedText: '...请选择...'});
        $(positionIdsPId).selectpicker('refresh');



        // 用量单位列表
        var unitIdItems = data["unitItems"];
        var unitIdPId = "#unitIdP" + idNum;
        $(unitIdPId).empty();
        $("<option></option>").val('').text("...请选择...").appendTo($(unitIdPId));
        $.each(unitIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(unitIdPId));
        });

        if ($.isFunction(callback)) {
            callback();
        }

    };

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
    };
    /**
     * 新增/修改校验字段描述
     * @returns {{name: {validators: {notEmpty: {message: string}}}, customerId: {validators: {notEmpty: {message: string}}}}}
     */
    var fieldsDesc =
    {
        materialTypeIdP: {
            validators: {
                notEmpty: {
                    message: '材料类别为必填项'
                }
            }
        },
        spIdP: {
            validators: {
                notEmpty: {
                    message: '供应商为必填项'
                }
            }
        },
        yearCodeP: {
            validators: {
                notEmpty: {
                    message: '年份为必填项'
                }
            }
        },
        classicIdP: {
            validators: {
                notEmpty: {
                    message: '材质为必填项'
                }
            }
        },
        pantoneIdsP: {
            validators: {
                notEmpty: {
                    message: '颜色为必填项'
                }
            }
        },
        productTypeIdP: {
            validators: {
                notEmpty: {
                    message: '品名为必填项'
                }
            }
        },
        unitIdP: {
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
        positionIdsP: {
            validators: {
                notEmpty: {
                    message: '物料位置为必填项'
                }
            }
        },
        orderCountP: {
            validators: {
                notEmpty: {
                    message: '尺码总数量为必填项'
                }
            }
        },
        attritionRateP: {
            validators: {
                notEmpty: {
                    message: '损耗率为必填项'
                }
            }
        },
        unitPriceP: {
            validators: {
                notEmpty: {
                    message: '单价为必填项'
                }
            }
        },
        totalAmountP: {
            validators: {
                notEmpty: {
                    message: '尺码总数量为必填项'
                }
            }
        },
        totalPriceP: {
            validators: {
                notEmpty: {
                    message: '总价为必填项'
                }
            }
        }

    };

    /**
     *
     * @param idNum
     * @returns {{}}
     */
    function getPackagingItem(idNum) {

        var kfPackaging = {};//基本信息
        kfPackaging.spId = $("#spIdP" + idNum).val();
        kfPackaging.yearCode = $("#yearCodeP" + idNum).val();
        kfPackaging.classicId = $("#classicIdP" + idNum).val();
        //kfPackaging.pantoneIds = $("#pantoneIdsP" + idNum).val();
        kfPackaging.productTypeId = $("#productTypeIdP" + idNum).val();
        //颜色多选
        var pantoneIds = $("#pantoneIdsP" + idNum).select2('data');
        kfPackaging.pantoneIds = pantoneIds;

        var positionIds = $("#positionIdsP" + idNum).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }
        var positionIdBlP = $("#positionIdBlP" + idNum).val();
        kfPackaging.positionIds = materialPositions;
        kfPackaging.positionIdBl = positionIdBlP;
        kfPackaging.materialTypeId = $("#materialTypeIdP" + idNum).val();
        kfPackaging.techRequired = $("#techRequiredP" + idNum).val();
        kfPackaging.width = $("#widthP" + idNum).val();
        kfPackaging.length = $("#lengthP" + idNum).val();
        kfPackaging.nameNum = idNum;
        kfPackaging.serialNumber = idNum;
        kfPackaging.remark =$("#remarkP" + idNum).val();
        kfPackaging.packagingId = $("#packagingIdP" + idNum).val();
        kfPackaging.packagingsName = $("#packagingsNameP" + idNum).val();
        kfPackaging.orderCount = $("#orderCountP" + idNum).val();
        kfPackaging.attritionRate = $("#attritionRateP" + idNum).val();
        kfPackaging.unitPrice = $("#unitPriceP" + idNum).val();
        kfPackaging.colorPrice = $("#colorPriceP" + idNum).val();
        kfPackaging.colorAmount = $("#colorAmountP" + idNum).val();
        kfPackaging.totalAmount = $("#totalAmountP" + idNum).val();
        kfPackaging.totalPrice = $("#totalPriceP" + idNum).val();
        kfPackaging.unitId = $("#unitIdP" + idNum).val();
        kfPackaging.unitAmount = $("#unitAmountP" + idNum).val();

        return kfPackaging;
    }


    /**
     *
     * @param idNum
     * @returns {{}}
     */
    function buildPackagingItem(idNum) {
        var packagingItem = {};

        var packagingInfo = {};//基本信息

        packagingInfo.spId = $("#spIdP" + idNum).val();
        packagingInfo.yearCode = $("#yearCodeP" + idNum).val();
        packagingInfo.classicId = $("#classicIdP" + idNum).val();
        packagingInfo.productTypeId = $("#productTypeIdP" + idNum).val();

        //颜色多选
        var pantoneIds = $("#pantoneIdsP" + idNum).val();
        var materialPantoneIds = [];
        if (null != pantoneIds && pantoneIds.length > 0) {
            for (var idx = 0, len = pantoneIds.length; idx < len; idx++) {
                var materialPantone = {"pantoneId": pantoneIds[idx]};
                materialPantoneIds.push(materialPantone)
            }
        }
        packagingInfo.pantoneIds = materialPantoneIds;


        var positionIds = $("#positionIdsP" + idNum).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }
        var positionIdBlP = $("#positionIdBlP" + idNum).val();
        packagingInfo.positionIds = materialPositions;
        packagingInfo.positionIdBl = positionIdBlP;
        packagingInfo.materialTypeId = $("#materialTypeIdP" + idNum).val();
        packagingInfo.techRequired = $("#techRequiredP" + idNum).val();
        packagingInfo.width = $("#widthP" + idNum).val();
        packagingInfo.length = $("#lengthP" + idNum).val();
        packagingInfo.nameNum = idNum;
        packagingInfo.packagingId = $("#packagingIdP" + idNum).val();
        packagingInfo.serialNumber = idNum;
        packagingInfo.packagingsName = $("#packagingsNameP" + idNum).val();
        packagingInfo.remark = $("#remarkP" + idNum).val();

        var materialSpInfo = {};//包材用量信息
        materialSpInfo.orderCount = $("#orderCountP" + idNum).val();
        materialSpInfo.attritionRate = $("#attritionRateP" + idNum).val();
        materialSpInfo.unitPrice = $("#unitPriceP" + idNum).val();
        materialSpInfo.totalAmount = $("#totalAmountP" + idNum).val();
        materialSpInfo.totalPrice = $("#totalPriceP" + idNum).val();
        materialSpInfo.packagingId = packagingInfo.packagingId;
        materialSpInfo.colorPrice = $("#colorPriceP" + idNum).val();
        materialSpInfo.colorAmount = $("#colorAmountP" + idNum).val();

        var materialUnitDosage = {};//包材单位用量
        materialUnitDosage.unitId = $("#unitIdP" + idNum).val();
        materialUnitDosage.unitAmount = $("#unitAmountP" + idNum).val();
        materialUnitDosage.packagingId = packagingInfo.packagingId;

        packagingItem.packagingInfo = packagingInfo;
        packagingItem.materialSpInfo = materialSpInfo;
        packagingItem.materialUnitDosage = materialUnitDosage;

        return packagingItem;
    }

    /**
     * 面板内容初始化
     */
    $(document).ready(function () {

        //页面加载时，包材全部隐藏
        $("div[id^=packagingAllInfoId]").hide();

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
        packagings: []//所有包材
    };
    window.savePackaging = savePackaging;
    window.refreshPackagingSelect = refreshPackagingSelect;
    window.trashPackagingSelect = trashPackagingSelect;
    window.showOrHidePackaging = showOrHidePackaging;
    window.copyPackaging = copyPackaging;

}(jQuery));

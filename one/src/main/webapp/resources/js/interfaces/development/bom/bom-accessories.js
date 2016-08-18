/**
 * Created by zhangjh on 2015/7/10.
 */
(function ($) {
    "use strict";
    var title_type = "辅料_";
    var path = $.basepath();
    //var saveAccessoriesFunURL = path + "/development/bom/saveAccessoriesFun";
    var bom_selectURL = path + "/system/baseinfo/bom_select";
    $.extend({
        initAccessories: initAccessories,
        buildAccessoriesItems: buildAccessoriesItems,
        refreshAllAccessoriesId: refreshAllAccessoriesId
    });


    function refreshAllAccessoriesId(accessories) {
        if (null != accessories) {
            for (var idx = 0, len = accessories.length; idx < len; idx++) {
                var accessoriesInfo = accessories[idx];
                var accessoriesId = accessoriesInfo.accessoriesId;
                var serialNumber = accessoriesInfo.serialNumber;
                if (null != fabricId) {
                    $("#accessoriesIdF" + serialNumber).val(accessoriesId);
                }

            }
        }
    }

    /**
     * 辅料信息
     * @returns {Array}
     */
    function buildAccessoriesItems() {
        var size = $("div[id^=accessoriesAllInfoId]").length;
        var accessoriesItems = [];
        for (var idx = 1; idx <= size; idx++) {
            var accessoriesItem = buildAccessoriesItem(idx);
            accessoriesItems.push(accessoriesItem);
        }
        return accessoriesItems
    }


    function initAccessories(accessories) {
        for (var idx = 0; idx < accessories.length; idx++) {
            addAccessories(accessories[idx]);
        }
    }


    /**
     * 启动表单校验监听
     * @param _id 当前表单序号
     */
    var startBootstrapValidatorListner = function (idNum) {
        $('#accessoriesFormId' + idNum).bootstrapValidator({
            //live: 'disabled',
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fieldsDesc
        }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
            console.log("辅料_" + idNum + "验证成功");
            saveAccessoriesFun(idNum);
        });
    }

    /**
     * 初始化辅料idNum的表单信息
     * @param idNum 辅料序号
     * @param _accessoriesInfo 辅料表单值
     */
    function initAccessoriesFields(idNum, _accessoriesInfo) {

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
                $('#positionIds' + "F" + idNum).selectpicker("val", positionIds);
                //$('#sexIds').val(arr);
            }
            else {
                //下拉框
                $("#" + key + "F" + idNum).val(_accessoriesInfo[key]);
            }

        });


        var pantoneIdsArr = _accessoriesInfo["pantoneIds"];

        if (pantoneIdsArr != null) {
            var _pantoneIdsArr = $.turnPantoneInfoToSelectOption(pantoneIdsArr);
            var $id = $('#pantoneIdsF' + idNum);
            $.reloadPantoneId($id, _pantoneIdsArr);
        }


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

        var idNum = size + 1;

        var data = buildMyTemplateData(idNum);

        var myTemplate = Handlebars.compile($("#accessories-template").html());
        $("#accessoriesItemInfo").append(myTemplate(data));

        $("div[id^=accessoriesAllInfoId]").hide(); //页面加载时，辅料全部隐藏
        $("span[id^=accessoriesEyeId]").removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");

        //加载下拉列表数据,付初始值
        reloadAccessoriesDetailSelectData(idNum, function () {
            if (accessoriesInfo != null) {
                initAccessoriesFields(idNum, accessoriesInfo);
                //表单字段监听
                startBootstrapValidatorListner(idNum);
            }
            else {
                var $id = $('#pantoneIdsF' + idNum);
                $.reloadPantoneId($id, []);
            }
        });


    }


    //删除辅料
    function deleteFraicById(idNum) {
        bom.accessories.splice(idNum - 1, 1);
    }

    /**
     * 克隆辅料
     * @param _this
     * @param idNum
     */
    function copyAccessories(_this, idNum) {
        var accessoriesItem = getAccessoriesItem(idNum);
        accessoriesItem.accessoriesId = null;
        addAccessories(accessoriesItem);
    }

    var deleteFun = function (idNum) {
        //当前辅料id
        var accessoriesArrLength = $("div[id^=accessoriesAllInfoId]").length;
        //删除当前辅料和之后的所有辅料
        for (var idx = idNum; idx <= accessoriesArrLength; idx++) {
            //$("div[id^=accessoriesAllInfoId]")
            $("#accessoriesDivId" + idx).remove();
            //保存当前节点之后的数据
            if (idx >= idNum) {
                if (idx == idNum) {
                    deleteFraicById(idx);
                }
                var formDataStr = $("#accessoriesFormId" + (idx + 1)).serialize();
                if (formDataStr != '') {
                    saveAccessoriesById(idx, formDataStr);
                }

            }
        }
        //重新生成辅料
        var maxAccessoriesId = accessoriesArrLength - 1;
        for (var idx = idNum; idx <= maxAccessoriesId; idx++) {
            addAccessories();
        }

        $("div[id^=accessoriesAllInfoId]").hide(); //全部隐藏


    }

    var doDel = function (result, idNum) {
        if (result) {
            deleteFun(idNum);
        }
    }

    var trashAccessoriesSelect = function (_this, idNum) {
        var saveFlag = bom.accessories[idNum - 1].saveFlag;
        if (saveFlag == true) {
            bootbox.confirm("辅料_" + idNum + "已保存，确定要删除", function (result) {
                doDel(result, idNum);
            });
        }
        else {
            deleteFun(idNum);
        }

    }

    var saveAccessories = function (_this, idNum) {
        $('#accessoriesFormId' + idNum).bootstrapValidator('validate');
    }

    var saveAccessoriesFun = function (idNum) {
        var formDataStr = $("#accessoriesFormId" + idNum).serialize();
        saveAccessoriesById(idNum, formDataStr);
        bom.accessories[idNum - 1].saveFlag = true;//已保存

    }


    var saveAccessoriesById = function (idNum, formDataStr) {
        var jsonObj = $.strToJson(formDataStr);
        bom.accessories[idNum - 1] = jsonObj;
        if (!$("#accessoriesAllInfoId" + idNum).is(':hidden')) {
            bom.accessories[idNum - 1].showFlag = true;//是否显示
        }
        bom.accessories[idNum - 1].currenId = idNum;//当前序号

        //$.sendRestFulAjax(saveAccessoriesFunURL, jsonObj, 'GET', 'json', function (data) {
        //    _doAccessoriesSuccess_info(data, idNum);
        //});

    };


    /**
     * 显示或者展示div
     * @param _this
     * @param id
     */
    var showOrHideAccessories = function (_this, idNum) {
        var accessoriesEyeId = "#accessoriesEyeId" + idNum;
        var accessoriesTrashId = "#accessoriesTrashId" + idNum;
        $("#accessoriesAllInfoId" + idNum).toggle(300,
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
    };

    /**
     * 当后台的基础信息修改后，点击刷新，可以刷新cookies信息
     */
    var refreshAccessoriesSelect = function (_this, idNum) {
        var accessoriesItem = getAccessoriesItem(idNum);
        reloadBomSelect(idNum, function () {
            initAccessoriesFields(idNum, accessoriesItem);
        });
        var $id = $('#pantoneIdsF' + idNum);
        $.reloadPantoneId($id, accessoriesItem['pantoneIds']);
    }


    var reloadBomSelect = function (idNum, callback) {
        $.sendRestFulAjax(bom_selectURL, null, 'GET', 'json', function (data) {
            _doAccessoriesSuccess_info(data, idNum, callback);
        });
    }

    //第一次初始化下拉列表
    var reloadAccessoriesDetailSelectData = function (idNum, callback) {
        reloadBomSelect(idNum, callback);
    }


    //cookie重新赋值，给下拉列表赋值
    var _doAccessoriesSuccess_info = function (_data, idNum, callback) {
        //$.cookie('systemBaseMaps', JSON.stringify(_data));//JSON 数据转化成字符串
        initAccessoriesSelect(_data, idNum, callback);
    }

    //给下拉列表赋值
    var initAccessoriesSelect = function (_data, idNum, callback) {
        //console.info("加载辅料" + idNum + "的下拉列表");
        var data = _data;//JSON.parse($.cookie('systemBaseMaps'));//字符串转化成JSON 数据

        //材料类别
        var materialTypeIdItems = data["materialTypeItems"];
        var $materialTypeIdF = $("#materialTypeIdF" + idNum);
        $materialTypeIdF.empty();
        $("<option></option>").val('').text("...请选择...").appendTo($materialTypeIdF);
        $.each(materialTypeIdItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($materialTypeIdF);
        });


        //供应商
        var spItems = data["spItems"];
        var $spIdF = $("#spIdF" + idNum);
        $spIdF.empty();
        $("<option></option>").val('').text("...请选择...").appendTo($spIdF);
        $.each(spItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($spIdF);
        });

        //年份
        var yearCodeItems = data["yearItems"];
        var $yearCodeF = $("#yearCodeF" + idNum);
        $yearCodeF.empty();
        $("<option></option>").val('').text("...请选择...").appendTo($yearCodeF);
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($yearCodeF);
        });


        //材质列表
        var classicIdItems = data["accessoriesClassicItems"];
        $("#classicIdF" + idNum).empty();
        $("<option></option>").val('').text("...请选择...").appendTo($("#classicIdF" + idNum));
        $.each(classicIdItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#classicIdF" + idNum));
        });

        //品名列表
        var productTypeIdItems = data["productTypeItems"];
        var $productTypeIdF = $("#productTypeIdF" + idNum);
        $productTypeIdF.empty();
        $("<option></option>").val('').text("...请选择...").appendTo($productTypeIdF);
        $.each(productTypeIdItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($productTypeIdF);
        });


        //物料位置列表
        var positionItems = data["positionItems"];
        var $positionIdsF = $("#positionIdsF" + idNum);
        $positionIdsF.empty();
        $.each(positionItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($positionIdsF);
        });
        $positionIdsF.selectpicker({noneSelectedText: '...请选择...'});
        $positionIdsF.selectpicker('refresh');


        // 用量单位列表
        var unitIdItems = data["unitItems"];
        var $unitIdF = $("#unitIdF" + idNum);
        $unitIdF.empty();
        $("<option></option>").val('').text("...请选择...").appendTo($unitIdF);
        $.each(unitIdItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($unitIdF);
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
        pantoneIdsF: {
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
        colorPriceF: {
            validators: {
                notEmpty: {
                    message: '各色单价为必填项'
                }
            }
        },
        colorAmountF: {
            validators: {
                notEmpty: {
                    message: '各色用量为必填项'
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


    var buildMyTemplateData = function (idNum) {
        var data = {
            "accessories": [
                {
                    "currenId": idNum,
                    "accessoriesDivId": "accessoriesDivId" + idNum,
                    "accessoriesTitleId": "accessoriesTitleId" + idNum,
                    "accessoriesIdF": "accessoriesIdF" + idNum,
                    "serialNumberF": "serialNumberF" + idNum,
                    "accessoriesTitleName": title_type + idNum,
                    "accessoriesNameF": "accessoriesNameF" + idNum,
                    "accessoriesEyeId": "accessoriesEyeId" + idNum,
                    "accessoriesTrashId": "accessoriesTrashId" + idNum,
                    "accessoriesRepeatId": "accessoriesRepeatId" + idNum,
                    "accessoriesCopyId": "accessoriesCopyId" + idNum,
                    "accessoriesFormId": "accessoriesFormId" + idNum,
                    "accessoriesAllInfoId": "accessoriesAllInfoId" + idNum,
                    "accessoriesDetailId": "accessoriesDetailId" + idNum,
                    "materialTypeIdF": "materialTypeIdF" + idNum,
                    "spIdF": "spIdF" + idNum,
                    "yearCodeF": "yearCodeF" + idNum,
                    "classicIdF": "classicIdF" + idNum,
                    "pantoneIdsF": "pantoneIdsF" + idNum,
                    "productTypeIdF": "productTypeIdF" + idNum,
                    "techRequiredF": "techRequiredF" + idNum,
                    "lengthF": "lengthF" + idNum,
                    "widthF": "widthF" + idNum,
                    "remarkF":"remarkF"+idNum,
                    "unitIdF": "unitIdF" + idNum,
                    "positionIdsF": "positionIdsF" + idNum,
                    "positionIdBlF": "positionIdBlF" + idNum,
                    "unitAmountF": "unitAmountF" + idNum,
                    "orderCountF": "orderCountF" + idNum,
                    "attritionRateF": "attritionRateF" + idNum,
                    "unitPriceF": "unitPriceF" + idNum,
                    "colorPriceF": "colorPriceF" + idNum,
                    "colorAmountF": "colorAmountF" + idNum,
                    "totalAmountF": "totalAmountF" + idNum,
                    "totalPriceF": "totalPriceF" + idNum
                }
            ]
        };

        return data;
    }


    /**
     * 保存时，获取信息
     * @param idNum
     * @returns {{}}
     */
    function buildAccessoriesItem(idNum) {
        var accessoriesItem = {};

        var accessoriesInfo = {};//fula基本信息
        accessoriesInfo.spId = $("#spIdF" + idNum).val();
        accessoriesInfo.yearCode = $("#yearCodeF" + idNum).val();
        accessoriesInfo.classicId = $("#classicIdF" + idNum).val();
        accessoriesInfo.productTypeId = $("#productTypeIdF" + idNum).val();

        var pantoneIds = $("#pantoneIdsF" + idNum).val();
        var materialPantoneIds = [];
        if (null != pantoneIds && pantoneIds.length > 0) {
            //noinspection JSDuplicatedDeclaration
            for (var idx = 0, len = pantoneIds.length; idx < len; idx++) {
                var materialPantoneId = {"pantoneId": pantoneIds[idx]};
                materialPantoneIds.push(materialPantoneId)
            }
        }
        accessoriesInfo.pantoneIds = materialPantoneIds;

        var positionIds = $("#positionIdsF" + idNum).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }
        var positionIdBlF = $("#positionIdBlF" + idNum).val();
        accessoriesInfo.positionIds = materialPositions;
        accessoriesInfo.positionIdBl = positionIdBlF;

        accessoriesInfo.materialTypeId = $("#materialTypeIdF" + idNum).val();
        accessoriesInfo.techRequired = $("#techRequiredF" + idNum).val();
        accessoriesInfo.width = $("#widthF" + idNum).val();
        accessoriesInfo.length = $("#lengthF" + idNum).val();
        accessoriesInfo.nameNum = idNum;
        accessoriesInfo.accessoriesId = $("#accessoriesIdF" + idNum).val();
        accessoriesInfo.serialNumber = idNum;
        accessoriesInfo.accessoriesName = $("#accessoriesNameF" + idNum).val();
        accessoriesInfo.name = $("#accessoriesNameF" + idNum).val();
        accessoriesInfo.remark = $("#remarkF" + idNum).val();

        var materialSpInfo = {};//包材用量信息
        materialSpInfo.orderCount = $("#orderCountF" + idNum).val();
        materialSpInfo.attritionRate = $("#attritionRateF" + idNum).val();
        materialSpInfo.unitPrice = $("#unitPriceF" + idNum).val();
        materialSpInfo.colorPrice = $("#colorPriceF" + idNum).val();
        materialSpInfo.colorAmount = $("#colorAmountF" + idNum).val();
        materialSpInfo.totalAmount = $("#totalAmountF" + idNum).val();
        materialSpInfo.totalPrice = $("#totalPriceF" + idNum).val();
        materialSpInfo.accessoriesId = accessoriesInfo.accessoriesId;

        var materialUnitDosage = {};//包材单位用量
        materialUnitDosage.unitId = $("#unitIdF" + idNum).val();
        materialUnitDosage.unitAmount = $("#unitAmountF" + idNum).val();
        materialUnitDosage.accessoriesId = accessoriesInfo.accessoriesId;

        accessoriesItem.accessoriesInfo = accessoriesInfo;
        accessoriesItem.materialSpInfo = materialSpInfo;
        accessoriesItem.materialUnitDosage = materialUnitDosage;

        return accessoriesItem;
    }


    /**
     *刷新时，暂存信息
     * @param idNum
     * @returns {{}}
     */
    function getAccessoriesItem(idNum) {

        var accessoriesInfo = {};//fula基本信息
        accessoriesInfo.spId = $("#spIdF" + idNum).val();
        accessoriesInfo.yearCode = $("#yearCodeF" + idNum).val();
        accessoriesInfo.classicId = $("#classicIdF" + idNum).val();
        accessoriesInfo.productTypeId = $("#productTypeIdF" + idNum).val();

        //颜色多选
        var pantoneIds = $("#pantoneIdsF" + idNum).select2('data');
        accessoriesInfo.pantoneIds = pantoneIds;


        var positionIds = $("#positionIdsF" + idNum).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }
        var positionIdBlF = $("#positionIdBlF" + idNum).val();

        accessoriesInfo.positionIds = materialPositions;
        accessoriesInfo.positionIdBl = positionIdBlF;
        accessoriesInfo.materialTypeId = $("#materialTypeIdF" + idNum).val();
        accessoriesInfo.techRequired = $("#techRequiredF" + idNum).val();
        accessoriesInfo.width = $("#widthF" + idNum).val();
        accessoriesInfo.length = $("#lengthF" + idNum).val();
        accessoriesInfo.nameNum = idNum;
        accessoriesInfo.accessoriesId = $("#accessoriesIdF" + idNum).val();
        accessoriesInfo.remark = $("#remarkF" + idNum).val();
        accessoriesInfo.orderCount = $("#orderCountF" + idNum).val();
        accessoriesInfo.attritionRate = $("#attritionRateF" + idNum).val();
        accessoriesInfo.unitPrice = $("#unitPriceF" + idNum).val();
        accessoriesInfo.colorPrice = $("#colorPriceF" + idNum).val();
        accessoriesInfo.colorAmount = $("#colorAmountF" + idNum).val();
        accessoriesInfo.totalAmount = $("#totalAmountF" + idNum).val();
        accessoriesInfo.totalPrice = $("#totalPriceF" + idNum).val();
        accessoriesInfo.unitId = $("#unitIdF" + idNum).val();
        accessoriesInfo.unitAmount = $("#unitAmountF" + idNum).val();
        return accessoriesInfo;
    }


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

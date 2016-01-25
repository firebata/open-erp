/**
 * Created by zhangjh on 2015/7/10.
 */
(function ($) {

    "use strict";
    var path = $.basepath();
    var selectURL = path + "/system/sp/select";
    var bom_selectURL = path + "/system/baseinfo/bom_select";
    var saveFabricFunURL = path + "/development/bom/saveFabricFun";
    var _pantoneSelectURL = path + '/system/pantone/select';

    $.extend({
        initFabric: initFabric,
        fabricItems: fabricItems
    });


    function fabricItems() {
        var size = $("div[id^=fabricAllInfoId]").length;
        var fabricItems = [];
        for (var index = 1; index <= size; index++) {
            var fabricItem = buildFabricItem(index);
            fabricItems.push(fabricItem);
        }
        return fabricItems;
    }

    /**
     * 切换
     * @param blcIdVal
     * @param _idNum
     * @param f
     */
    function showOrHideCompositeDiv(blcIdVal, _idNum, f) {

        if (blcIdVal === 'A1') {
            $("#compositeDiv" + _idNum).show();
        } else {
            $("#compositeDiv" + _idNum).hide();
        }
        f();

    }


    /**
     *
     * @param materialTypeIdVal
     * @param idNum
     * @param callback
     */
    function reloadSpSelect(materialTypeIdVal, idNum, callback) {
        $.sendRestFulAjax(selectURL, {'keyword': materialTypeIdVal}, 'GET', 'json', function (_data) {
            var spIdField = $("#spId" + idNum);
            spIdField.empty();
            $("<option></option>").val('').text("请选择...").appendTo(spIdField);
            $.each(_data["items"], function (key, value) {
                $("<option></option>")
                    .val(value["natrualkey"])
                    .text(value["name"])
                    .appendTo(spIdField);
            });

            callback();
        });
    }


    function initFabric(fabricItems) {
        for (var index = 0; index < fabricItems.length; index++) {
            addFabric(fabricItems[index]);
        }
    }

    /**
     * 启动表单校验监听
     * @param _id 当前表单序号
     */
    var startBootstrapValidatorListner = function (_id) {
        $('#fabricFormId' + _id).bootstrapValidator({
            //live: 'disabled',
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fieldsDesc
        }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
            console.log("面料_" + _id + "验证成功" + e);
            saveFabricFun(_id);
        });
    };

    /**
     * select2的option为{id:id,text:text},所以需要将返回的数据转换需要的格式
     * @param pantoneIdsArr
     * @returns {Array}
     */
    function turnPantoneInfoToSelect2Option(pantoneIdsArr) {
        var _pantoneIdsArr = [];
        for (var idx = 0; idx < pantoneIdsArr.length; idx++) {
            var pantone = pantoneIdsArr[idx];
            var _pantone = {};
            _pantone.id = pantone.pantoneId;
            _pantone.text = pantone.pantoneName;
            _pantoneIdsArr.push(_pantone);
        }
        return _pantoneIdsArr;
    }

    /**
     * 初始化面料nextIdNum的表单信息
     * @param nextIdNum 面料序号
     * @param _fabricInfo 面料表单值
     */
    function initFabricFields(nextIdNum, _fabricInfo) {

        //保存值
        var fabricInfo = $.extend({}, _fabricInfo);

        bom.fabricItems.push(fabricInfo);

        //初始化赋值
        Object.keys(_fabricInfo).map(function (key) {

            //下拉框
            $("#" + key + nextIdNum).val(_fabricInfo[key]);

            //复合与单布选项特殊处理
            if (key === 'blcId') {
                showOrHideCompositeDiv(_fabricInfo[key], nextIdNum, function () {
                });
            }
            //处理位置多选
            if (key == 'positionIds') {
                //var arr = _fabricInfo[key].split(',');
                var arr = _fabricInfo[key];
                var positionIds = [];
                if (null != arr) {
                    for (var idx = 0, len = arr.length; idx < len; idx++) {
                        positionIds.push(arr[idx].positionId);
                    }
                }
                $('#positionIds' + nextIdNum).selectpicker("val", positionIds);
                //$('#sexIds').val(arr);
            }


            ////面料颜色
            //if (key == 'pantoneIds') {
            //    var arr = _fabricInfo[key];
            //    initPantoneField(nextIdNum, arr);
            //}

        });


        //初始供应商信息
        initSpSelect(nextIdNum, _fabricInfo);

        var pantoneIdsArr = _fabricInfo["pantoneIds"];
        if (pantoneIdsArr != null) {
            var _pantoneIdsArr = turnPantoneInfoToSelect2Option(pantoneIdsArr);
            reloadPantoneId(nextIdNum, _pantoneIdsArr);
        }


    }

    /**
     *初始供应商信息
     * @param nextIdNum
     * @param _fabricInfo
     */
    function initSpSelect(nextIdNum, _fabricInfo) {

        var materialTypeIdVal = $("materialTypeId" + nextIdNum).val();
        var idNum = nextIdNum;
        reloadSpSelect(materialTypeIdVal, idNum, function () {
            $("#spId" + idNum).val(_fabricInfo['spId']);
        });

    }

    /**
     * 添加面料
     */
    var addFabric = function (fabricInfo) {

        //if (fabricInfo == undefined){
        //    fabricInfo = fabric;
        //}

        $("div[id^=fabricAllInfoId]").hide(); //页面加载时，面料全部隐藏

        $("span[id^=fabricEyeId]").removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");

        var size = $("div[id^=fabricAllInfoId]").length;

        var nextIdNum = size + 1;

        var data = buildMyTemplateData(nextIdNum);

        var myTemplate = Handlebars.compile($("#fabric-template").html());
        $("#fabricsItemInfo").append(myTemplate(data));

        $("div[id^=fabricAllInfoId]").hide(); //页面加载时，面料全部隐藏
        $("span[id^=fabricEyeId]").removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");

        //加载下拉列表数据,
        reloadFabricDetailSelectData(nextIdNum, function () {
            callbackInitFabricFields(nextIdNum, fabricInfo);
        });


    };

    /**
     * 赋初始值
     * @param nextIdNum
     * @param fabricInfo
     */
    var callbackInitFabricFields = function (nextIdNum, fabricInfo) {

        if (fabricInfo != null) {
            initFabricFields(nextIdNum, fabricInfo);
            //表单字段监听
            startBootstrapValidatorListner(nextIdNum);

        }

    };


    //删除面料
    function deleteFraicById(index) {
        bom.fabricItems.splice(index - 1, 1);
    }

    /**
     * 克隆面料
     * @param _this
     * @param index
     */
    function copyFabric(_this, index) {

        var fabricItem = getFabricItem(index);
        fabricItem.fabricId = null;
        addFabric(fabricItem);

        //if (bom.fabricItems[index] == undefined || $.trim(bom.fabricItems[index]) == '') {
        //    bootbox.alert("请先保存面料_" + index);
        //}
    }

    var deleteFun = function (id) {
        //当前面料id
        var curId = id;
        var fabricArrLength = $("div[id^=fabricAllInfoId]").length;
        //删除当前面料和之后的所有面料
        for (var index = curId; index <= fabricArrLength; index++) {
            //$("div[id^=fabricAllInfoId]")
            $("#fabricDivId" + index).remove();
            //保存当前节点之后的数据
            if (index >= curId) {

                if (index == curId) {
                    deleteFraicById(index);
                }


                var formDataStr = $("#fabricFormId" + (index + 1)).serialize();
                if (formDataStr != '') {
                    saveFabricById(index, formDataStr);
                }

            }
        }
        //重新生成面料
        var maxFabricId = fabricArrLength - 1;
        for (var index = curId; index <= maxFabricId; index++) {
            addFabric();
        }

        $("div[id^=fabricAllInfoId]").hide(); //全部隐藏


    };

    var doDel = function (result, id) {
        if (result) {
            deleteFun(id);
        }
    };

    var trashFabricSelect = function (_this, id) {
        var saveFlag = bom.fabricItems[id - 1].saveFlag;
        if (saveFlag == true) {
            bootbox.confirm("面料_" + id + "已保存，确定要删除", function (result) {
                doDel(result, id);
            });
        }
        else {
            deleteFun(id);
        }

    };


    var saveFabricFun = function (id) {
        var formDataStr = $("#fabricFormId" + id).serialize();
        saveFabricById(id, formDataStr);


        bom.fabricItems[id - 1].saveFlag = true;//已保存

    };


    var saveFabricById = function (id, formDataStr) {
        var jsonObj = $.strToJson(formDataStr);
        bom.fabricItems[id - 1] = jsonObj;
        if (!$("#fabricAllInfoId" + id).is(':hidden')) {
            bom.fabricItems[id - 1].showFlag = true;//是否显示
        }
        bom.fabricItems[id - 1].currenId = id;//当前序号
        $.sendRestFulAjax(saveFabricFunURL, jsonObj, 'GET', 'json', function (data) {
            _doFabricSuccess_info(data, id);
        });
    };


    /**
     * 显示或者展示div
     * @param _this
     * @param id
     */
    var showOrHideFabric = function (_this, id) {
        var fabricEyeId = "#fabricEyeId" + id;
        var fabricTrashId = "#fabricTrashId" + id;
        $("#fabricAllInfoId" + id).toggle(300,
            function () {
                if ($(this).is(':hidden')) {
                    $(fabricEyeId).removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");
                    $(fabricTrashId).addClass("disabled");
                }
                else {
                    $(fabricEyeId).removeClass("glyphicon glyphicon-eye-close").addClass("glyphicon glyphicon-eye-open");
                    $(fabricTrashId).removeClass("disabled");
                }
            }
        );
    };

    /**
     * 当后台的基础信息修改后，点击刷新，可以刷新cookies信息
     */
    var refreshFabricSelect = function (_this, index) {
        var fabricItem = getFabricItem(index);
        reloadBomSelect(index, function () {
            initFabricFields(index, fabricItem);
        });

        reloadPantoneId(index, fabricItem['pantoneIds']);
    };

    function initPantoneField(_index, pantoneIdsArr) {
        $.initSelect2sByArr('pantoneIds' + _index, 'pantoneId', 'pantoneName', pantoneIdsArr);
    }

    /**
     * 刷新面料颜色
     * @param index
     * @param _pantoneIds
     */
    function reloadPantoneId(index, _pantoneIds) {
        $.select2s('pantoneIds' + index, _pantoneSelectURL, 'Pantone not selected', 'Please, select pantone', function () {
            return _pantoneIds;
        });
        //callback();
    }

    var reloadBomSelect = function (id, callback) {
        $.sendRestFulAjax(bom_selectURL, null, 'GET', 'json', function (data) {
            _doFabricSuccess_info(data, id, callback);
        });
    };

    //第一次初始化下拉列表
    var reloadFabricDetailSelectData = function (id, callback) {
        reloadBomSelect(id, callback);

        //reloadPantoneId(id, function () {
        //    initPantoneField(id, null);
        //});

        //if ($.cookie('systemBaseMaps') == undefined) {
        //    //第一次初始化下拉列表，存放到cookies中
        //    $.sendRestFulAjax(path + "/system/baseinfo/bom_select", null, 'GET', 'json', function (data) {
        //        _doFabricSuccess_info(data, id);
        //    });
        //}
        //else {
        //    //第二次，直接从cookies中读取
        //    initFabricSelect(nextIdNum);
        //}
    };


    //cookie重新赋值，给下拉列表赋值
    var _doFabricSuccess_info = function (_data, id, callback) {
        //$.cookie('systemBaseMaps', JSON.stringify(_data));//JSON 数据转化成字符串
        initFabricSelect(_data, id, callback);
    };

    //给下拉列表赋值
    var initFabricSelect = function (_data, id, callback) {
        var idNum = id;
        var data = _data;//JSON.parse($.cookie('systemBaseMaps'));//字符串转化成JSON 数据

        //材料类别
        var materialTypeIdItems = data["materialTypeItems"];
        var materialTypeId = "#materialTypeId" + idNum;
        $(materialTypeId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(materialTypeId));
        $.each(materialTypeIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(materialTypeId));
        });

        //供应商
        var spItems = data["spItems"];
        var spId = "#spId" + idNum;
        $(spId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(spId));
        //$.each(spItems, function (i, item) {
        //    $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(spId));
        //});

        //年份
        var yearCodeItems = data["yearItems"];
        var yearCode = "#yearCode" + idNum;
        $(yearCode).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(yearCode));
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(yearCode));
        });

        //材质列表
        var classicIdItems = data["fabricClassicItems"];
        var classicId = "#classicId" + idNum;
        $(classicId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(classicId));
        $.each(classicIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(classicId));
        });

        //品名列表
        var productTypeIdItems = data["productTypeItems"];
        var productTypeId = "#productTypeId" + idNum;
        $(productTypeId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(productTypeId));
        $.each(productTypeIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(productTypeId));
        });

        //纱支密度列表
        var specificationIdItems = data["specficationItems"];
        var specificationId = "#specificationId" + idNum;
        $(specificationId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(specificationId));
        $.each(specificationIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(specificationId));
        });

        //染色方式列表
        //noinspection JSDuplicatedDeclaration
        var dyeIdItems = data["dyeItems"];
        var dyeId = "#dyeId" + idNum;
        $(dyeId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(dyeId));
        $.each(dyeIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(dyeId));
        });

        //后整理列表
        var finishIdItems = data["finishItems"];
        var finishId = "#finishId" + idNum;
        $(finishId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(finishId));
        $.each(finishIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(finishId));
        });

        //复合或涂层列表
        var blcIdItems = data["blcItems"];
        var blcId = "#blcId" + idNum;
        $(blcId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(blcId));
        $.each(blcIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(blcId));
        });

        //材质列表
        var classicIdItems = data["fabricClassicItems"];
        var compositeClassicId = "#compositeClassicId" + idNum;
        $(compositeClassicId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(compositeClassicId));
        $.each(classicIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(compositeClassicId));
        });


        //品名列表
        var productTypeIdItems = data["productTypeItems"];
        var compositeProductTypeId = "#compositeProductTypeId" + idNum;
        $(compositeProductTypeId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(compositeProductTypeId));
        $.each(productTypeIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(compositeProductTypeId));
        });


        //纱支密度列表
        var specificationIdItems = data["specficationItems"];
        var compositeSpecificationId = "#compositeSpecificationId" + idNum;
        $(compositeSpecificationId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(compositeSpecificationId));
        $.each(specificationIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(compositeSpecificationId));
        });

        //染色方式列表
        var dyeIdItems = data["dyeItems"];
        var compositeDyeId = "#compositeDyeId" + idNum;
        $(compositeDyeId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(compositeDyeId));
        $.each(dyeIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(compositeDyeId));
        });

        //后整理列表
        var finishIdItems = data["finishItems"];
        var compositeFinishId = "#compositeFinishId" + idNum;
        $(compositeFinishId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(compositeFinishId));
        $.each(finishIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(compositeFinishId));
        });

        //膜或涂层的材质列表
        var mcIdItems = data["momcItems"];
        var momcId = "#momcId" + idNum;
        $(momcId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(momcId));
        $.each(mcIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(momcId));
        });

        //膜或涂层的颜色列表
        var comcIdItems = data["comocItems"];
        var comocId = "#comocId" + idNum;
        $(comocId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(comocId));
        $.each(comcIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(comocId));
        });

        //透湿程度列表
        var wvpIdItems = data["wvpItems"];
        var wvpId = "#wvpId" + idNum;
        $(wvpId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(wvpId));
        $.each(wvpIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(wvpId));
        });

        //膜的厚度列表
        var mtIdItems = data["mtItems"];
        var mtId = "#mtId" + idNum;
        $(mtId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(mtId));
        $.each(mtIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(mtId));
        });

        // 贴膜或涂层工艺列表
        var woblcIdItems = data["wblcItems"];
        var woblcId = "#woblcId" + idNum;
        $(woblcId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(woblcId));
        $.each(woblcIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(woblcId));
        });

        //物料位置列表
        var positionItems = data["positionItems"];
        var positionIds = "#positionIds" + idNum;
        $(positionIds).empty();
        $.each(positionItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(positionIds));
        });
        $('#positionIds' + idNum).selectpicker({noneSelectedText: '请选择...'});
        $('#positionIds' + idNum).selectpicker('refresh');

        // 用量单位列表
        var unitIdItems = data["unitItems"];
        var unitId = "#unitId" + idNum;
        $(unitId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(unitId));
        $.each(unitIdItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(unitId));
        });

        if ($.isFunction(callback)) {
            callback();
        }

    };

    /**
     *
     * @type {{bomId: string, fabricId: string, fabricsName: string, materialTypeId: string, classicId: string, spId: string, yearCode: string, productTypeId: string, serialNumber: string, pantoneIds: string, positionIds: string, dyeId: string, finishId: string, blcId: string, momcId: string, comocId: string, wvpId: string, mtId: string, woblcId: string, unitId: string, unitAmount: string}}
     */
    var fabric = {
        bomId: "",
        fabricId: "",
        nameNum: "",
        fabricsName: "",
        materialTypeId: "",
        classicId: "",
        spId: "",
        yearCode: "",
        productTypeId: "",
        serialNumber: "",
        pantoneIds: "",
        positionIds: "",
        dyeId: "",
        finishId: "",
        blcId: "",
        momcId: "",
        comocId: "",
        wvpId: "",
        mtId: "",
        woblcId: "",
        unitId: "",
        unitAmount: "",
        showFlag: false,//页面是否展示
        saveFlag: false,//是否已保存
        delFlag: false,//是否已删除
        currenId: 0 //面料下标
    };


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
        pantoneIds: {
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
        specificationId: {
            validators: {
                notEmpty: {
                    message: '纱支密度为必填项'
                }
            }
        },
        dyeId: {
            validators: {
                notEmpty: {
                    message: '染色方式为必填项'
                }
            }
        },
        finishId: {
            validators: {
                notEmpty: {
                    message: '后整理为必填项'
                }
            }
        },
        blcId: {
            validators: {
                notEmpty: {
                    message: '复合或涂层为必填项'
                }
            }
        }
    };

    var buildMyTemplateData = function (nextIdNum) {
        var data = {
            "fabric": [
                {
                    "currenId": nextIdNum,
                    "fabricDivId": "fabricDivId" + nextIdNum,
                    "fabricTitleId": "fabricTitleId" + nextIdNum,
                    "fabricId": "fabricId" + nextIdNum,
                    "fabricTitleName": "面料_" + nextIdNum,
                    "fabricEyeId": "fabricEyeId" + nextIdNum,
                    "fabricTrashId": "fabricTrashId" + nextIdNum,
                    "fabricRepeatId": "fabricRepeatId" + nextIdNum,
                    "fabricCopyId": "fabricCopyId" + nextIdNum,
                    "fabricFormId": "fabricFormId" + nextIdNum,
                    "fabricAllInfoId": "fabricAllInfoId" + nextIdNum,
                    "fabricDetailId": "fabricDetailId" + nextIdNum,
                    "materialTypeId": "materialTypeId" + nextIdNum,
                    "spId": "spId" + nextIdNum,
                    "yearCode": "yearCode" + nextIdNum,
                    "classicId": "classicId" + nextIdNum,
                    "pantoneIds": "pantoneIds" + nextIdNum,
                    "productTypeId": "productTypeId" + nextIdNum,
                    "specificationId": "specificationId" + nextIdNum,
                    "dyeId": "dyeId" + nextIdNum,
                    "finishId": "finishId" + nextIdNum,
                    "blcId": "blcId" + nextIdNum,

                    "compositeDiv": "compositeDiv" + nextIdNum,
                    "compositeClassicId": "compositeClassicId" + nextIdNum,
                    "compositePantoneId": "compositePantoneId" + nextIdNum,
                    "compositeProductTypeId": "compositeProductTypeId" + nextIdNum,
                    "compositeSpecificationId": "compositeSpecificationId" + nextIdNum,
                    "compositeDyeId": "compositeDyeId" + nextIdNum,
                    "compositeFinishId": "compositeFinishId" + nextIdNum,

                    "momcId": "momcId" + nextIdNum,
                    "comocId": "comocId" + nextIdNum,
                    "wvpId": "wvpId" + nextIdNum,
                    "mtId": "mtId" + nextIdNum,
                    "woblcId": "woblcId" + nextIdNum,
                    "isShow": "isShow" + nextIdNum,
                    "unitId": "unitId" + nextIdNum,
                    "positionIds": "positionIds" + nextIdNum,
                    "unitAmount": "unitAmount" + nextIdNum,
                    "orderCount": "orderCount" + nextIdNum,
                    "attritionRate": "attritionRate" + nextIdNum,
                    "unitPrice": "unitPrice" + nextIdNum,
                    "totalAmount": "totalAmount" + nextIdNum,
                    "totalPrice": "totalPrice" + nextIdNum
                }
            ]
        };
        return data;
    };

    function getFabricItem(index) {

        var fabricsInfo = {};//面料基本信息

        fabricsInfo.spId = $("#spId" + index).val();
        fabricsInfo.yearCode = $("#yearCode" + index).val();
        fabricsInfo.classicId = $("#classicId" + index).val();
        fabricsInfo.pantoneIds = $("#pantoneIds" + index).val();
        fabricsInfo.productTypeId = $("#productTypeId" + index).val();

        //颜色多选
        var pantoneIds = $("#pantoneIds" + index).val();
        var materialPantoneIds = [];
        if (null != pantoneIds && pantoneIds.length > 0) {
            //noinspection JSDuplicatedDeclaration
            for (var idx = 0, len = pantoneIds.length; idx < len; idx++) {
                var materialPantoneId = {"pantoneId": pantoneIds[idx]};
                materialPantoneIds.push(materialPantoneId)
            }
        }
        fabricsInfo.pantoneIds = materialPantoneId;

        //位置多选
        var positionIds = $("#positionIds" + index).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }

        fabricsInfo.positionIds = materialPositions;
        fabricsInfo.materialTypeId = $("#materialTypeId" + index).val();
        fabricsInfo.nameNum = index;
        fabricsInfo.fabricId = $("#fabricId" + index).val();
        fabricsInfo.isShow = $("#isShow" + index).val();
        fabricsInfo.compositeClassicId = $("#compositeClassicId" + index).val();
        fabricsInfo.compositePantoneId = $("#compositePantoneId" + index).val();
        fabricsInfo.compositeProductTypeId = $("#compositeProductTypeId" + index).val();

        fabricsInfo.specificationId = $("#specificationId" + index).val();
        fabricsInfo.dyeId = $("#dyeId" + index).val();
        fabricsInfo.finishId = $("#finishId" + index).val();
        fabricsInfo.compositeSpecificationId = $("#compositeSpecificationId" + index).val();
        fabricsInfo.compositeDyeId = $("#compositeDyeId" + index).val();
        fabricsInfo.compositeFinishId = $("#compositeFinishId" + index).val();
        fabricsInfo.blcId = $("#blcId" + index).val();
        fabricsInfo.momcId = $("#momcId" + index).val();
        fabricsInfo.comocId = $("#comocId" + index).val();
        fabricsInfo.wvpId = $("#wvpId" + index).val();
        fabricsInfo.mtId = $("#mtId" + index).val();
        fabricsInfo.woblcId = $("#woblcId" + index).val();

        fabricsInfo.orderCount = $("#orderCount" + index).val();
        fabricsInfo.attritionRate = $("#attritionRate" + index).val();
        fabricsInfo.unitPrice = $("#unitPrice" + index).val();
        fabricsInfo.totalAmount = $("#totalAmount" + index).val();
        fabricsInfo.totalPrice = $("#totalPrice" + index).val();

        fabricsInfo.unitId = $("#unitId" + index).val();
        fabricsInfo.unitAmount = $("#unitAmount" + index).val();


        return fabricsInfo;
    }


    /**
     * 获取每个面料的详细信息
     * @param index
     */
    function buildFabricItem(index) {
        var fabricItem = {};
        var fabricsInfo = {};//面料基本信息
        fabricsInfo.spId = $("#spId" + index).val();
        fabricsInfo.yearCode = $("#yearCode" + index).val();
        fabricsInfo.classicId = $("#classicId" + index).val();
        fabricsInfo.pantoneIds = $("#pantoneIds" + index).val();
        fabricsInfo.productTypeId = $("#productTypeId" + index).val();

        var pantoneIds = $("#pantoneIds" + index).val();
        var materialPantoneIds = [];
        if (null != pantoneIds && pantoneIds.length > 0) {
            //noinspection JSDuplicatedDeclaration
            for (var idx = 0, len = pantoneIds.length; idx < len; idx++) {
                var materialPantoneId = {"pantoneId": pantoneIds[idx]};
                materialPantoneIds.push(materialPantoneId)
            }
        }
        fabricsInfo.pantoneIds = materialPantoneIds;

        var positionIds = $("#positionIds" + index).val();
        var materialPositions = [];
        if (null != positionIds && positionIds.length > 0) {
            for (var idx = 0, len = positionIds.length; idx < len; idx++) {
                var materialPosition = {"positionId": positionIds[idx]};
                materialPositions.push(materialPosition)
            }
        }

        fabricsInfo.positionIds = materialPositions;
        fabricsInfo.materialTypeId = $("#materialTypeId" + index).val();
        fabricsInfo.nameNum = index;
        fabricsInfo.fabricId = $("#fabricId" + index).val();
        fabricsInfo.isShow = $("#isShow" + index).val();
        fabricsInfo.compositeClassicId = $("#compositeClassicId" + index).val();
        fabricsInfo.compositePantoneId = $("#compositePantoneId" + index).val();
        fabricsInfo.compositeProductTypeId = $("#compositeProductTypeId" + index).val();

        var fabricsDetailInfo = {};//面料描述信息
        fabricsDetailInfo.specificationId = $("#specificationId" + index).val();
        fabricsDetailInfo.dyeId = $("#dyeId" + index).val();
        fabricsDetailInfo.finishId = $("#finishId" + index).val();
        fabricsDetailInfo.compositeSpecificationId = $("#compositeSpecificationId" + index).val();
        fabricsDetailInfo.compositeDyeId = $("#compositeDyeId" + index).val();
        fabricsDetailInfo.compositeFinishId = $("#compositeFinishId" + index).val();
        fabricsDetailInfo.blcId = $("#blcId" + index).val();
        fabricsDetailInfo.momcId = $("#momcId" + index).val();
        fabricsDetailInfo.comocId = $("#comocId" + index).val();
        fabricsDetailInfo.wvpId = $("#wvpId" + index).val();
        fabricsDetailInfo.mtId = $("#mtId" + index).val();
        fabricsDetailInfo.woblcId = $("#woblcId" + index).val();
        fabricsDetailInfo.fabricId = fabricsInfo.fabricId;

        var materialSpInfo = {};//面料用量信息
        materialSpInfo.orderCount = $("#orderCount" + index).val();
        materialSpInfo.attritionRate = $("#attritionRate" + index).val();
        materialSpInfo.unitPrice = $("#unitPrice" + index).val();
        materialSpInfo.totalAmount = $("#totalAmount" + index).val();
        materialSpInfo.totalPrice = $("#totalPrice" + index).val();
        materialSpInfo.fabricId = fabricsInfo.fabricId;

        var materialUnitDosage = {};//面料单位用量
        materialUnitDosage.unitId = $("#unitId" + index).val();
        materialUnitDosage.unitAmount = $("#unitAmount" + index).val();
        materialUnitDosage.fabricId = fabricsInfo.fabricId;
        fabricItem.fabricsInfo = fabricsInfo;
        fabricItem.fabricsDetailInfo = fabricsDetailInfo;
        fabricItem.materialSpInfo = materialSpInfo;
        fabricItem.materialUnitDosage = materialUnitDosage;
        return fabricItem;
    }

    /**
     * 监控下拉列表变化
     */
    function monitorSelectChange() {

        var _id = $(this).attr('name');
        //选择复合时，显示响应的
        if (_id == 'blcId') {
            var blcIdVal = $(this).val();
            var thisId = $(this).attr('id');
            var idNum = thisId.substring(5);
            showOrHideCompositeDiv(blcIdVal, idNum, function () {

            });
        }
        //选择材料类别
        else if (_id == 'materialTypeId') {
            var materialTypeIdVal = $(this).val();
            var thisId = $(this).attr('id');
            //noinspection JSDuplicatedDeclaration
            var idNum = thisId.substring(14);
            reloadSpSelect(materialTypeIdVal, idNum, function () {

            });
        }

    }

    /**
     * 面板内容初始化
     */
    $(function () {

        //显示复合面料信息
        $("#fabricsItemInfo").on("change", "select", monitorSelectChange);


        //$("#bomDescDetail").hide();

        //第一步，页面加载时，加载所有数据/分步加载数据

        //页面加载时，面料全部隐藏
        $("div[id^=fabricAllInfoId]").hide();

        //点击添加面料
        $("#imgAddFabric").click(function () {
            addFabric();
            //赋初始化值：保存标志，显示标志
            var size = $("div[id^=fabricAllInfoId]").length;
            bom.fabricItems[size - 1] = fabric;
        });
    });

    var bom = {
        projectId: "",//项目id
        bomId: "",//BOMId
        fabricItems: []//所有面料
    };
    //window.saveFabric = saveFabric;
    window.refreshFabricSelect = refreshFabricSelect;
    window.trashFabricSelect = trashFabricSelect;
    window.showOrHideFabric = showOrHideFabric;
    window.copyFabric = copyFabric;

}(jQuery));
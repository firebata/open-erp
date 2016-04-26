/**
 * Created by zhangjh on 2015/7/10.
 */
(function ($) {

    "use strict";

    var path = $.basepath();
    var title_type = "成衣厂_";
    // var saveFactoryFunURL = path + "/development/bom/saveFactoryFun";
    var bom_selectURL = path + "/system/baseinfo/bom_select";
   
    //var $fileListLi = $("#filesList");
    //var sketchUrlUidUploadFileInfos = [];
    //var specificationUrlUidUploadFileInfos = [];
    

    $.extend({
        initFactory: initFactory,
        buildFactoryQuoteInfos: buildFactoryQuoteInfos
    });

    /**
     *
     * @param index
     * @returns {{}}
     */
    function buildFactoryQuoteInfo(index) {

        var factoryQuoteInfo = {};

        factoryQuoteInfo.factoryQuoteId = $("#factoryQuoteId" + index).val();
        factoryQuoteInfo.offerAmount = $("#offerAmount" + index).val();
        factoryQuoteInfo.fabricsEndDate = $("#fabricsEndDate" + index).val();
        factoryQuoteInfo.accessoriesEndDate = $("#accessoriesEndDate" + index).val();
        factoryQuoteInfo.preOfferDate = $("#preOfferDate" + index).val();
        factoryQuoteInfo.clothReceivedDate = $("#clothReceivedDate" + index).val();
        factoryQuoteInfo.quoteReference = $("#quoteReference" + index).val();
        factoryQuoteInfo.spId = $("#spIdC" + index).val();
        factoryQuoteInfo.euroPrice = $("#euroPrice" + index).val();
        factoryQuoteInfo.factoryOffer = $("#factoryOffer" + index).val();
        factoryQuoteInfo.factoryMargins = $("#factoryMargins" + index).val();
        factoryQuoteInfo.nameNum = index;
        factoryQuoteInfo.serialNumber = index;
        return factoryQuoteInfo;
    }

    /**
     * 报价信息
     * @returns {Array}
     */
    function buildFactoryQuoteInfos() {
        var size = $("div[id^=factoryAllInfoId]").length;
        var factoryQuoteInfos = [];
        for (var index = 1; index <= size; index++) {
            var factoryQuoteInfo = buildFactoryQuoteInfo(index);
            factoryQuoteInfos.push(factoryQuoteInfo);
        }
        return factoryQuoteInfos;
    }


    function initFactory(factoryItems) {
        for (var index = 0; index < factoryItems.length; index++) {
            addFactory(factoryItems[index]);
        }
    }


    /**
     * 启动表单校验监听
     * @param _id 当前表单序号
     */
    var startBootstrapValidatorListner = function (_id) {
        $('#factoryFormId' + _id).bootstrapValidator({
            //live: 'disabled',
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fieldsDesc
        }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
            console.log("成衣厂_" + _id + "验证成功");
            saveFactoryFun(_id);
        });
    }

    /**
     * 初始化成衣厂nextIdNum的表单信息
     * @param nextIdNum 成衣厂序号
     * @param _factoryInfo 成衣厂表单值
     */
    function initFactoryFields(nextIdNum, _factoryInfo) {

        //保存值
        var factoryInfo = $.extend({}, _factoryInfo);

        bom.factoryItems.push(factoryInfo);

        //初始化赋值
        Object.keys(_factoryInfo).map(function (key) {
            var keyVal = _factoryInfo[key];
            if (key === 'spId') {
                //下拉框
                $("#spIdC" + nextIdNum).val(keyVal);
            } else {

                //下拉框
                $("#" + key + nextIdNum).val(keyVal);
            }
        });
    }


    /**
     * 添加成衣厂
     */
    var addFactory = function (factoryInfo) {

        var size = $("div[id^=factoryAllInfoId]").length;
        var nextIdNum = size + 1;
        var data = {
            "factory": [
                {

                    "currenId": nextIdNum,
                    "factoryDivId": "factoryDivId" + nextIdNum,
                    "factoryTitleId": "factoryTitleId" + nextIdNum,
                    "factoryQuoteId": "factoryQuoteId" + nextIdNum,
                    "factoryTitleName": title_type + nextIdNum,
                    "factoryEyeId": "factoryEyeId" + nextIdNum,
                    "factoryTrashId": "factoryTrashId" + nextIdNum,
                    "factoryRepeatId": "factoryRepeatId" + nextIdNum,
                    "factoryFloppyDiskId": "factoryFloppyDiskId" + nextIdNum,
                    "factoryFormId": "factoryFormId" + nextIdNum,
                    "factoryAllInfoId": "factoryAllInfoId" + nextIdNum,
                    "factoryDetailId": "factoryDetailId" + nextIdNum,
                    "spIdC": "spIdC" + nextIdNum,
                    "offerAmount": "offerAmount" + nextIdNum,
                    "quoteReference": "quoteReference" + nextIdNum,
                    "preOfferDate": "preOfferDate" + nextIdNum,
                    "clothReceivedDate": "clothReceivedDate" + nextIdNum,
                    "fabricsEndDate": "fabricsEndDate" + nextIdNum,
                    "accessoriesEndDate": "accessoriesEndDate" + nextIdNum,
                    "factoryOffer": "factoryOffer" + nextIdNum,
                    "factoryMargins": "factoryMargins" + nextIdNum,
                    //"lpPrice": "lpPrice" + nextIdNum,
                    "euroPrice": "euroPrice" + nextIdNum,

                    //"exchangeCosts": "exchangeCosts" + nextIdNum,
                    //"costing": "costing" + nextIdNum
                    "productionInstructionId": "productionInstructionId" + nextIdNum,
                    "productionInstructionDetailId": "productionInstructionDetailId" + nextIdNum,
                    "productionInstructionTitleName": "productionInstructionTitleName" + nextIdNum,
                    "cropRequirements": "cropRequirements" + nextIdNum,
                    "qualityRequirements": "qualityRequirements" + nextIdNum,
                    "finishPressingRequirements": "finishPressingRequirements" + nextIdNum,
                    "spcialTech": "spcialTech" + nextIdNum,
                    "packingRequirements": "packingRequirements" + nextIdNum,
                    "overstitch": "overstitch" + nextIdNum,
                    "overstitchSpace": "overstitchSpace" + nextIdNum,
                    "blindstitch": "blindstitch" + nextIdNum,
                    "blindstitchSpace": "blindstitchSpace" + nextIdNum,
                    "overlock": "overlock" + nextIdNum,
                    "overlockSpace": "overlockSpace" + nextIdNum,
                    "trademarkCode": "trademarkCode" + nextIdNum,
                    "trademarkRemark": "trademarkRemark" + nextIdNum,
                    "scaleCode": "scaleCode" + nextIdNum,
                    "scaleRemark": "scaleRemark" + nextIdNum,
                    "rinsingMarksCode": "rinsingMarksCode" + nextIdNum,
                    "rinsingMarksRemark": "rinsingMarksRemark" + nextIdNum,
                    "sketchUrlUid": "sketchUrlUid" + nextIdNum,
                    "specificationUrlUid": "specificationUrlUid" + nextIdNum
                }
            ]
        };

        var myTemplate = Handlebars.compile($("#factory-template").html());
        $("#factoryItemInfo").append(myTemplate(data));


        $("div[id^=factoryAllInfoId]").hide(); //页面加载时，成衣厂全部隐藏

        $("span[id^=factoryEyeId]").removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");

        //加载下拉列表数据,付初始值
        reloadFactoryDetailSelectData(nextIdNum, function () {
            if (factoryInfo != null) {
                initFactoryFields(nextIdNum, factoryInfo);
                //表单字段监听
                startBootstrapValidatorListner(nextIdNum);
            }
            // else {
            //     initFileInput(nextIdNum, factoryInfo);
            // }


        });


    }


    //删除成衣厂
    function deleteFactoryById(idNum) {
        bom.factoryItems.splice(idNum - 1, 1);
    }

    function copyFactory(idNum) {
        if (bom.factoryItems[idNum] == undefined || $.trim(bom.factoryItems[idNum]) == '') {
            bootbox.alert("请先保存成衣厂_" + idNum);
        }
    }

    var deleteFun = function (idNum) {
        //当前成衣厂idNum
        var curId = idNum;
        var factoryArrLength = $("div[id^=factoryAllInfoId]").length;
        //删除当前成衣厂和之后的所有成衣厂
        for (var index = curId; index <= factoryArrLength; index++) {
            //$("div[id^=factoryAllInfoId]")
            $("#factoryDivId" + index).remove();
            //保存当前节点之后的数据
            if (index >= curId) {

                if (index == curId) {
                    deleteFactoryById(index);
                }

                var formDataStr = $("#factoryFormId" + (index + 1)).serialize();
                if (formDataStr != '') {
                    saveFactoryById(index, formDataStr);
                }


            }
        }
        //重新生成成衣厂
        var maxFactoryId = factoryArrLength - 1;

        for (var index = curId; index <= maxFactoryId; index++) {
            addFactory();
        }

        $("div[id^=factoryAllInfoId]").hide(); //全部隐藏


    }

    var doDel = function (result, idNum) {
        if (result) {
            deleteFun(idNum);
        }
    }

    var trashFactorySelect = function (_this, idNum) {
        var saveFlag = bom.factoryItems[idNum - 1].saveFlag;
        if (saveFlag == true) {
            bootbox.confirm("成衣厂_" + idNum + "已保存，确定要删除", function (result) {
                doDel(result, idNum);
            });
        }
        else {
            deleteFun(idNum);
        }

    }

    var saveFactory = function (_this, idNum) {
        $('#factoryFormId' + idNum).bootstrapValidator('validate');
    }

    var saveFactoryFun = function (idNum) {
        var formDataStr = $("#factoryFormId" + idNum).serialize();
        saveFactoryById(idNum, formDataStr);

        bom.factoryItems[idNum - 1].saveFlag = true;//已保存
    }

    var saveFactoryById = function (idNum, formDataStr) {
        var jsonObj = $.strToJson(formDataStr);
        bom.factoryItems[idNum - 1] = jsonObj;
        if (!$("#factoryAllInfoId" + idNum).is(':hidden')) {
            bom.factoryItems[idNum - 1].showFlag = true;//是否显示
        }
        bom.factoryItems[idNum - 1].currenId = idNum;//当前序号
        //$.sendRestFulAjax(saveFactoryFunURL, jsonObj, 'GET', 'json', function (data) {
        //    _doFactorySuccess_info(data, idNum);
        //});
    }


    /**
     * 显示或者展示div
     * @param _this
     * @param idNum
     */
    var showOrHideFactory = function (_this, idNum) {
        var factoryEyeId = "#factoryEyeId" + idNum;
        var factoryTrashId = "#factoryTrashId" + idNum;
        $("#factoryAllInfoId" + idNum).toggle(300,
            function () {
                if ($(this).is(':hidden')) {
                    $(factoryEyeId).removeClass("glyphicon glyphicon-eye-open").addClass("glyphicon glyphicon-eye-close");
                    $(factoryTrashId).addClass("disabled");
                }
                else {
                    $(factoryEyeId).removeClass("glyphicon glyphicon-eye-close").addClass("glyphicon glyphicon-eye-open");
                    $(factoryTrashId).removeClass("disabled");
                }
            }
        );
    }

    /**
     * 当后台的基础信息修改后，点击刷新，可以刷新cookies信息
     */
    var refreshFactorySelect = function (_this, idNum) {
        var factoryQuoteInfo = buildFactoryQuoteInfo(idNum);
        reloadBomSelect(idNum, function () {
            initFactoryFields(idNum, factoryQuoteInfo);
        });
    }


    var reloadBomSelect = function (idNum, callback) {
        $.sendRestFulAjax(bom_selectURL, null, 'GET', 'json', function (data) {
            _doFactorySuccess_info(data, idNum, callback);
        });
    }

    //第一次初始化下拉列表
    var reloadFactoryDetailSelectData = function (idNum, callback) {
        reloadBomSelect(idNum, callback);
    }


    //cookie重新赋值，给下拉列表赋值
    var _doFactorySuccess_info = function (_data, idNum, callback) {
        initFactorySelect(_data, idNum, callback);
    }

    //给下拉列表赋值
    var initFactorySelect = function (_data, idNum, callback) {
        var data = _data;
        //供应商
        var spItems = data["spItems"];
        $("#spIdC" + idNum).empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#spIdC" + idNum));
        $.each(spItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#spIdC" + idNum));
        });


        if ($.isFunction(callback)) {
            callback();
        }

    }

    /**
     *
     * @type {{bomId: string, spIdC: string, nameNum: string, factoryName: string, offerAmount: string, clothReceivedDate: string, quoteReference: string, preOfferDate: string, accessoriesEndDate: string, serialNumber: string, fabricsEndDate: string, exchangeCosts: string, euroPrice: string, costing: string, showFlag: boolean, saveFlag: boolean, delFlag: boolean, currenId: number}}
     */
    var factory = {
        bomId: "",
        spIdC: "",
        nameNum: "",
        factoryName: "",
        offerAmount: "",
        clothReceivedDate: "",
        quoteReference: "",
        preOfferDate: "",
        accessoriesEndDate: "",
        serialNumber: "",
        fabricsEndDate: "",
        exchangeCosts: "",
        euroPrice: "",
        costing: "",
        showFlag: false,//页面是否展示
        saveFlag: false,//是否已保存
        delFlag: false,//是否已删除
        currenId: 0 //成衣厂下标
    }


    /**
     * 新增/修改校验字段描述
     * @returns {{name: {validators: {notEmpty: {message: string}}}, customerId: {validators: {notEmpty: {message: string}}}}}
     */
    var fieldsDesc =
    {
        offerAmount: {
            validators: {
                notEmpty: {
                    message: '订单数量为必填项'
                }
            }
        },
        quoteReference: {
            validators: {
                notEmpty: {
                    message: '报价参考为必填项'
                }
            }
        },
        preOfferDate: {
            validators: {
                notEmpty: {
                    message: '成衣报价时间为必填项'
                }
            }
        },
        clothReceivedDate: {
            validators: {
                notEmpty: {
                    message: '材质为必填项'
                }
            }
        },
        fabricsEndDate: {
            validators: {
                notEmpty: {
                    message: '面料交货时间为必填项'
                }
            }
        },
        accessoriesEndDate: {
            validators: {
                notEmpty: {
                    message: '品名为必填项'
                }
            }
        },
        euroPrice: {
            validators: {
                notEmpty: {
                    message: '工厂欧元报价为必填项'
                }
            }
        },
        costing: {
            validators: {
                notEmpty: {
                    message: '用量为必填项'
                }
            }
        },
        exchangeCosts: {
            validators: {
                notEmpty: {
                    message: '换汇成本为必填项'
                }
            }
        },
        lpPrice: {
            validators: {
                notEmpty: {
                    message: '包装费为必填项'
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
        factoryOffer: {
            validators: {
                notEmpty: {
                    message: '工厂报价利润率为必填项'
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

        //页面加载时，成衣厂全部隐藏
        $("div[id^=factoryAllInfoId]").hide();

        //点击添加成衣厂
        $("#imgAddFactory").click(function () {
            addFactory();
            //赋初始化值：保存标志，显示标志
            var size = $("div[id^=factoryAllInfoId]").length;
            bom.factoryItems[size - 1] = factory;
        });


      

    });


    var bom = {
        projectId: "",//项目id
        bomId: "",//BOMId
        factoryItems: [],//所有成衣厂
    }
    window.saveFactory = saveFactory;
    window.refreshFactorySelect = refreshFactorySelect;
    window.trashFactorySelect = trashFactorySelect;
    window.showOrHideFactory = showOrHideFactory;
    window.copyFactory = copyFactory;

}(jQuery));

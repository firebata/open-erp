/*!
 * bominfo
 * Copyright 2011-2015 zhangjh.
 * Licensed under MTT (https://github.com/firebata/skysport/blob/master/LICENSE)
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var saveUrl = path + "/development/bom/edit";
    var listUrl = path + "/development/bom/list";
    var mainColorOld;
    $.extend({
        bomSave: bomSave,
        bomSubmit: bomSubmit,
        bomAutoBackup: bomAutoBackup,
        mainColorEditInBom: mainColorEditInBom,
        caculateCostingVal: caculateCostingVal
    });

    $(function () {

        initBom();
        //赋值价格
        $("#factoryItemInfo").on("change", "select", cb);

        $("#bomAddPageForm").on("blur", "input", monitorInputBlur);

    });


    /**
     *
     * @param firstChar 序号开始的下标值
     * @param idNum 所有下标值
     * @param materialType 所属类型
     * @returns {{idNum: *, materialType: (string|*)}}
     */
    function getIdNumAndMaterialType(idNum, materialType) {
        var firstChar = "";
        if (firstChar == "F" || firstChar == "P") {//辅料 或者包装材料
            idNum = idNum.substr(1);//过滤掉F
            firstChar = idNum.charAt(0);
        }
        materialType = firstChar;
        return {idNum: idNum, materialType: materialType};
    }

    /**
     *监控损耗率和单价发生变化时，动态计算价格
     */
    function monitorInputBlur() {
        var _name = $(this).attr('name');
        var _thisId = $(this).attr('id');
        var idNum = "";
        var materialType = "";
        var isMonitorPrice = false;
        if (_name == 'offerAmount') { //订单数量
            var fabricSize = $("div[id^=fabricAllInfoId]").length;
            var packagingSize = $("div[id^=packagingAllInfoId]").length;
            var accessoriesSize = $("div[id^=accessoriesAllInfoId]").length;
            //序号从1开始
            for (var idx = 1, len = fabricSize + 1; idx < len; idx++) {
                caculatePriceAndAmout(idx, '');
            }
            for (var idx = 1, len = packagingSize + 1; idx < len; idx++) {
                caculatePriceAndAmout(idx, 'P');
            }
            for (var idx = 1, len = accessoriesSize + 1; idx < len; idx++) {
                caculatePriceAndAmout(idx, 'F');
            }
            caculateCostingVal();
        }
        else {
            //单位用量
            if (_name == 'unitAmount') {
                idNum = _thisId.substring(10);
                isMonitorPrice = true;
            }
            //损耗率
            else if (_name == 'attritionRate') {
                idNum = _thisId.substring(13);
                isMonitorPrice = true;
            }
            //单价
            else if (_name == 'unitPrice') {
                idNum = _thisId.substring(9);
                isMonitorPrice = true;
            }
            if (isMonitorPrice) {
                var __ret = getIdNumAndMaterialType(idNum, materialType);
                idNum = __ret.idNum;
                materialType = __ret.materialType;
                caculatePriceAndAmout(idNum, materialType);
                caculateCostingVal();
            }
        }

    }

    function getInputVal(_name, idNum, materialType) {
        var _$val = $("#" + _name + materialType + idNum).val();
        return _$val;
    }

    /**
     * 成本核算
     */
    function caculateCostingVal() {
        var costingVal = 0;
        $("input[name='colorPrice']").each(
            function () {
                var _$thisVal = $(this).val();
                //是否是有效值
                if ($.strIsEmpty(_$thisVal)) {
                    _$thisVal = 0;
                }
                costingVal = $.floatAdd(costingVal, parseFloat(_$thisVal));
            }
        )
        $("#costing").val(costingVal);
    }

    /**
     * 计算供应商的价格信息
     * @param idNum
     * @param materialType
     */
    function caculatePriceAndAmout(idNum, materialType) {
        var unitPriceVal = getInputVal("unitPrice", idNum, materialType); //单位价格
        var attritionRateVal = getInputVal("attritionRate", idNum, materialType);//损耗率
        var unitAmountVal = getInputVal("unitAmount", idNum, materialType);//单位用量
        var offerAmountVal = $("#offerAmount").val();
        var materialTypeIdNum = materialType + idNum;
        if ($.strIsNotEmpty(unitPriceVal) && $.strIsNotEmpty(attritionRateVal) && $.strIsNotEmpty(unitAmountVal)) {
            if (offerAmountVal == '0') {
                bootbox.alert("订单数量为0.");
            }
            //各色用量
            var colorAmountVal = $.multiply(unitAmountVal, (1 + parseFloat(attritionRateVal)));
            $("#colorAmount" + materialTypeIdNum).val(colorAmountVal);
            //各色单价
            var colorPriceVal = $.multiply(colorAmountVal, unitPriceVal);
            $("#colorPrice" + materialTypeIdNum).val(colorPriceVal);
            //各色总用量
            var totalAmountVal = $.multiply(offerAmountVal, colorAmountVal);
            $("#totalAmount" + materialTypeIdNum).val(totalAmountVal);
            //各色总价格
            var totalPriceVal = $.multiply(offerAmountVal, colorPriceVal);
            $("#totalPrice" + materialTypeIdNum).val(totalPriceVal);

        } else {


        }


    }


    /**
     * 点击编辑
     * @param _this
     */
    function mainColorEditInBom(_this) {
        // var mainColorDisabled = $('#mainColor').attr("disabled");
        var classVal = $(_this).attr("class");
        var editClassVal = 'glyphicon glyphicon-edit';
        var okClassVal = 'glyphicon glyphicon-ok';
        var $mainColor = $('#mainColor');
        if (classVal == editClassVal) {
            $mainColor.removeAttr("disabled");
            $(_this).removeClass(editClassVal).addClass(okClassVal);
        } else if (classVal == okClassVal) {
            $mainColor.attr("disabled", "disabled");
            $(_this).removeClass(okClassVal).addClass(editClassVal);
        }
    }

    function initQuetoInfo(quoteReference, _thisId, f) {

        if (quoteReference === '1') {//参考工厂报价
            var idNum = _thisId.substring(14);
            $("#factoryOffer").val($("#factoryOffer" + idNum).val());
            $("#factoryMargins").val($("#factoryMargins" + idNum).val());
            $("#euroPrice").val($("#euroPrice" + idNum).val());
        }
        f();
    }

    function cb() {
        if ($(this).attr('name') === 'quoteReference') {
            var quoteReference = $(this).val();
            var thisId = $(this).attr('id');
            initQuetoInfo(quoteReference, thisId, function () {

            });
        }
    }

    function initBom() {
        //初始化描述信息
        //var natrualkey = $("#natrualkey").val();
        $.initBomDesc(function (_data) {

            //初始化报价信息
            $.iniBomQuotedInfo(_data.quotedInfo);

            //初始化面料
            $.initFabric(_data.fabrics);

            //初始化辅料
            $.initAccessories(_data.accessories);

            //初始化包装材料
            $.initPackaging(_data.packagings);

            //初始成衣厂
            $.initFactory(_data.factoryQuoteInfos);

            $.initProductinst(_data);


        });

    }


    /**
     * 保存数据
     */
    function bomSaveFun(needToLisPage) {

        $.sendJsonAjax(saveUrl, bominfo, function (_bomInfo) {
            if (null == needToLisPage) {
                window.location.href = listUrl;
            } else {
                bootbox.alert("成功暂存数据.");
                if (null != _bomInfo) {
                    var fabrics = _bomInfo["fabrics"];
                    var accessories = _bomInfo["accessories"];
                    var packagings = _bomInfo["packagings"];
                    $.refreshAllFabricId(fabrics);
                    $.refreshAllAccessoriesId(accessories);
                    $.refreshAllPackagingId(packagings);
                }
            }

        })

    }


    function toSaveBomInfo(needToLisPage) {
        //描述信息
        $.buildBomDesc(bominfo);

        //包材信息
        var fabricItems = $.buildFabricItems();
        bominfo.fabricItems = fabricItems;

        //辅料信息
        var accessoriesItems = $.buildAccessoriesItems();
        bominfo.accessoriesItems = accessoriesItems;

        //包装材料信息
        var packagingItems = $.buildPackagingItems();
        bominfo.packagingItems = packagingItems;

        //成衣厂
        var factoryQuoteInfos = $.buildFactoryQuoteInfos();
        bominfo.factoryQuoteInfos = factoryQuoteInfos;

        //生产指示单
        var productionInstruction = $.buildProductionInstr();
        bominfo.productionInstruction = productionInstruction;

        $.buildBomQuoted(bominfo);

        bomSaveFun(needToLisPage);
    }

    /**
     * 构建保存数据，并保存
     */
    function bomSave(needToLisPage) {
        var mainColorOld = $("#mainColorOld").val();
        var mainColor = $("#mainColor").val();
        if (mainColorOld != mainColor) {
            bootbox.confirm({
                size: 'small',
                message: "你准备把BOM原主颜色[" + mainColorOld + "]修改为新的颜色[" + mainColor + "],确定修改吗?",
                callback: function (result) {
                    if (result) {
                        toSaveBomInfo(needToLisPage);
                    }
                }
            })
        }
        else {
            toSaveBomInfo(needToLisPage);
        }


    }

    var bominfo = {};

    function bomAutoBackup() {
        bomSave(false);
    }

    function bomSubmit() {

    }
}(jQuery));
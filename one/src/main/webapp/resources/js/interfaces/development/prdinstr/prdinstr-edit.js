/**
 * Created by zhangjh on 2016-05-04.
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var fileUploadURL = path + "/files/upload";
    var isSubmitAction = 'N';
    var infoURL = path + "/development/prdinstr/info/";
    var editURL = path + "/development/prdinstr/edit/";
    var listURL = path + "/development/prdinstr/list/";
    var submitURL = path + "/development/prdinstr/submit/";
    // $.extend({
    //     initProductinst: initProductinst,
    //     buildProductionInstr: buildProductionInstr
    // });

    /**
     *
     * @param _data
     */
    function initProductinst(_productionInstruction) {
        // var _productionInstruction = _data.productionInstruction;
        if (null != _productionInstruction) {
            Object.keys(_productionInstruction).map(function (key_2) {//遍历工艺单信息
                // if (key_2 != 'clothReceivedDate' && key_2 != 'natrualkey' && key_2 != 'spId' && key_2 != 'fabricId') {//避免覆盖上面工厂信息中的clothReceivedDate
                $("#" + key_2).val(_productionInstruction[key_2]);
                // }
            });

        }
        initFileInput(_productionInstruction);
    }

    /**
     *
     * @returns {{}}
     */
    function buildProductionInstr() {
        var productionInstruction = {};
        productionInstruction.factoryQuoteId = $("#factoryQuoteId").val();
        productionInstruction.productionInstructionId = $("#uid").val();
        productionInstruction.cropRequirements = $("#cropRequirements").val();
        productionInstruction.offerAmount = $("#offerAmount").val();
        productionInstruction.spId = $("#spIdC").val();
        productionInstruction.clothReceivedDate = $("#clothReceivedDate").val();
        productionInstruction.qualityRequirements = $("#qualityRequirements").val();
        productionInstruction.finishPressingRequirements = $("#finishPressingRequirements").val();
        productionInstruction.spcialTech = $("#spcialTech").val();
        productionInstruction.packingRequirements = $("#packingRequirements").val();
        productionInstruction.overstitch = $("#overstitch").val();
        productionInstruction.overstitchSpace = $("#overstitchSpace").val();
        productionInstruction.blindstitch = $("#blindstitch").val();
        productionInstruction.blindstitchSpace = $("#blindstitchSpace").val();
        productionInstruction.overlock = $("#overlock").val();
        productionInstruction.overlockSpace = $("#overlockSpace").val();
        productionInstruction.trademarkCode = $("#trademarkCode").val();
        productionInstruction.trademarkRemark = $("#trademarkRemark").val();
        productionInstruction.scaleCode = $("#scaleCode").val();
        productionInstruction.scaleRemark = $("#scaleRemark").val();
        productionInstruction.rinsingMarksCode = $("#rinsingMarksCode").val();
        productionInstruction.rinsingMarksRemark = $("#rinsingMarksRemark").val();
        productionInstruction.sketchUrlUid = $("#sketchUrlUid").val();
        productionInstruction.specificationUrlUid = $("#specificationUrlUid").val();
        //上传文件
        var sketchUrlUidUploadFileInfos = [];
        var specificationUrlUidUploadFileInfos = [];

        try {
            var initialPreviewConfigs = $("#sketchUrlUid").fileinput().data()["fileinput"].ajaxRequests[0].responseJSON.initialPreviewConfig;//上传文件返回的数据
            $.buildUploadedFileInfos(sketchUrlUidUploadFileInfos, initialPreviewConfigs);
        } catch (e) {

        }

        try {
            var initialPreviewConfigs = $("#specificationUrlUid").fileinput().data()["fileinput"].ajaxRequests[0].responseJSON.initialPreviewConfig;//上传文件返回的数据
            $.buildUploadedFileInfos(specificationUrlUidUploadFileInfos, initialPreviewConfigs);
        } catch (e) {

        }

        productionInstruction.sketchUrlUidUploadFileInfos = sketchUrlUidUploadFileInfos;
        productionInstruction.specificationUrlUidUploadFileInfos = specificationUrlUidUploadFileInfos;
        //
        return productionInstruction;


    }

    /**
     * 初始化上传插件
     * @param nextIdNum
     * @param _factoryInfo
     */
    function initFileInput(_productionInstruction) {


        var sketchUrlUidFileinfosMap = null;
        var specificationUrlUidFileinfosMap = null;

        if (null != _productionInstruction) {
            sketchUrlUidFileinfosMap = _productionInstruction["sketchUrlUidFileinfosMap"];
            specificationUrlUidFileinfosMap = _productionInstruction["specificationUrlUidFileinfosMap"];
        }

        var $sketchUrlUid = $("#sketchUrlUid");
        $.loadFileInput($sketchUrlUid, null, sketchUrlUidFileinfosMap, fileUploadURL);
        $.fileInputAddListenr(null, $sketchUrlUid, null, function () {
        }, getIsSubmitAction);

        var $specificationUrlUid = $("#specificationUrlUid");
        $.loadFileInput($specificationUrlUid, null, specificationUrlUidFileinfosMap, fileUploadURL);

        $.fileInputAddListenr(null, $specificationUrlUid, null, function () {
        }, getIsSubmitAction);


    }


    function getIsSubmitAction() {
        return isSubmitAction;
    }


    $(function () {

        var bomId = $("#bomId").val();
        $.sendRestFulAjax(infoURL + bomId, null, 'GET', 'json', _doSuccess_info);
        $("#prdinstrForm").on('click', '#saveBtn', tosave);
        $("#prdinstrForm").on('click', '#submitBtn', tosubmit);
    });


    function _doSuccess_info(_data) {
        initProductinst(_data);
        var $btnDIV = $("#btnInfo");
        var approveStatus = _data["approveStatus"];
        var bomId = $("#bomId").val();
        var taskId = $("#taskId").val();
        var stateCode = $("#stateCode").val();
        var processInstanceId = $("#processInstanceId").val();

        $.showHandleBtn($btnDIV, approveStatus, tosave, bomId, taskId, stateCode, processInstanceId);
    }

    function tosave() {
        var info = buildProductionInstr();//$("#prdinstrForm").serialize();
        $.sendJsonAjax(editURL, info, function () {
            window.location.href = listURL;
        })
    }

    function tosubmit() {
        var nk = $("#natrualkey").val();
        var taksId = "null";
        $.sendJsonAjax(submitURL + taksId + "/" + nk, {}, function () {
            window.location.href = listURL;
        })
    }

}(jQuery));

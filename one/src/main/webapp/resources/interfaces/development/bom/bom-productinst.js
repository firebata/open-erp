(function ($) {
    "use strict";
    var path = $.basepath();
    var fileUploadURL = path + "/files/upload";
    var isSubmitAction = 'N';
    $.extend({
        initProductinst: initProductinst
    });


    function initProductinst(_productionInstruction) {
        if (null != _productionInstruction) {
            Object.keys(_productionInstruction).map(function (key_2) {//遍历工艺单信息
                if (key_2 != 'clothReceivedDate') {//避免覆盖上面工厂信息中的clothReceivedDate
                    $("#" + key_2).val(_productionInstruction[key_2]);
                }
            });

        }
        initFileInput(_productionInstruction);
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
            sketchUrlUidFileinfosMap = _productionInstruction["productionInstruction"]["sketchUrlUidFileinfosMap"];
            specificationUrlUidFileinfosMap = _productionInstruction["productionInstruction"]["specificationUrlUidFileinfosMap"];
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

}(jQuery));

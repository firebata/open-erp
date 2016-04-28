/**
 * 报价
 * Created by zhangjh on 2015/9/18.
 */

(function ($) {
    "use strict";
    $.extend({
        iniBomQuotedInfo: iniBomQuotedInfo,
        buildBomQuoted: buildBomQuoted
    })
    /**
     * 初始化报价信息
     * @param _data
     */
    function iniBomQuotedInfo(_data) {
        var natrualkey = $("#natrualkey").val();
        if (_data != null && natrualkey != '' && natrualkey != 'null') {
            Object.keys(_data).map(function (key) {
                $("#" + key).val(_data[key]);
                // $('#offerDescDetail input').filter(function () {
                //     return key == this.name;
                // }).val(_data[key]);
                //$("#" + key).val(_data[key]);
            });
        }
    }


   

    /**
     * 报价信息
     */
    function buildBomQuoted(bominfo) {
        bominfo.quotedInfo = {};
        bominfo.quotedInfo.factoryOffer = $("#factoryOffer").val();
        bominfo.quotedInfo.factoryMargins = $("#factoryMargins").val();
        bominfo.quotedInfo.costing = $("#costing").val();
        bominfo.quotedInfo.lpPrice = $("#lpPrice").val();
        bominfo.quotedInfo.euroPrice = $("#euroPrice").val();
        bominfo.quotedInfo.exchangeCosts = $("#exchangeCosts").val();
        bominfo.quotedInfo.bomId = $("#natrualkey").val();
        bominfo.quotedInfo.spId = $("#spId").val();
        bominfo.quotedInfo.fabricId = $("#fabricId").val();
    }


}(jQuery));

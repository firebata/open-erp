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
                $('#offerDescDetail input').filter(function () {
                    return key == this.name;
                }).val(_data[key]);
                //$("#" + key).val(_data[key]);
            });
        }
    }


    function cbOfferDescDetail() {
        //工厂利润率改变，重新计算欧元报价
        if ($(this).attr('id') === 'euroPrice') {
            var exchangeCosts = $("#exchangeCosts").val();
            var factoryOffer = $("#factoryOffer").val();
            //var euroPrice = factoryOffer * (1 + Number(factoryMargins));
            if ($.strIsEmpty(exchangeCosts)){
                bootbox.alert("请输入换汇成本");
                return;
            }
            if($.strIsEmpty(factoryOffer)){
                bootbox.alert("请输入工厂报价");
                return;
            }
            var euroPrice = factoryOffer / exchangeCosts;
            $("#euroPrice").val(euroPrice.toFixed(2));
        }
        else if ($(this).attr('id') === 'costing') {//成本核算
            var costingVal = 0;
            $("input[name='totalPrice']").each(
                function () {
                    var _$thisVal = $(this).val();
                    //是否是有效值
                    if ($.strIsEmpty(_$thisVal)) {
                        _$thisVal = 0;
                    }
                    costingVal += parseFloat(_$thisVal);
                }
            )
            $(this).val(costingVal);
        }

    }

    $(function () {

        // $("#offerDescTitle").click(function () {
        //     $("#offerDescDetail").toggle(300);
        // });

        //监听价格变动
        $("#offerDescDetail").on("click", "input", cbOfferDescDetail);


    })
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

    }


}(jQuery));

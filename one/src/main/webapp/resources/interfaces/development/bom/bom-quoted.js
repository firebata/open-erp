/**
 * 报价
 * Created by zhangjh on 2015/9/18.
 */

(function ($) {

    /**
     * 初始化报价信息
     * @param _data
     */
    var iniBomQuotedInfo = function (_data) {
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


    function cb() {
        //工厂利润率改变，重新计算欧元报价
        if ($(this).attr('id') === 'euroPrice') {
            var exchangeCosts = $("#exchangeCosts").val();
            var factoryOffer = $("#factoryOffer").val();
            //var euroPrice = factoryOffer * (1 + Number(factoryMargins));
            var euroPrice = factoryOffer / exchangeCosts;
            $("#euroPrice").val(euroPrice.toFixed(4));
        }
    }

    $(function () {

        // $("#offerDescTitle").click(function () {
        //     $("#offerDescDetail").toggle(300);
        // });

        //监听价格变动
        $("#offerDescDetail").on("click", "input", cb);

    })


    $.extend({
        iniBomQuotedInfo: iniBomQuotedInfo
    })


}(jQuery));

/**
 * Created by zhangjh on 2015/11/25.
 */
(function ($) {
    "use strict";
    var qrMenuURL = path + "/system/permission/qrMenu";

    $(function () {
        $.sendJsonAjax(qrMenuURL, null, showMenu);
        // addClickListenerOnLi();
    });

    //生成菜单
    function showMenu(data) {
        var myTemplate = Handlebars.compile($("#menu-template").html());
        //注册一个比较大小的Helper,判断二级菜单是否有三级菜单
        Handlebars.registerHelper("compare", function (v1, options) {
            if (v1 === undefined || v1 === null || v1.length === 0) {
                //不满足条件执行{{else}}部分
                return options.inverse(this);
            } else {
                //满足添加继续执行
                return options.fn(this);
            }
        });

        //判断是否是控制台页面
        Handlebars.registerHelper("isConsolePage", function (v1, options) {
            if (v1 == '999') {
                //满足添加继续执行
                return options.fn(this);
            } else {
                //不满足条件执行{{else}}部分
                return options.inverse(this);
            }
        });


        $("#sidebarUL").append(myTemplate(data));
    }

    // //菜单增加点击监听
    // function addClickListenerOnLi() {
    //     $("#sidebar").on("click", "li", setLiActive);
    // }

    //设置菜单点击选中状态
    // function setLiActive() {
    //
    //     $(".active").removeClass("active");
    //     $(this).removeClass("active").addClass("active");
    //
    //
    // }


}(jQuery));
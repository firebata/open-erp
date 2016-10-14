/**
 * Bom描述信息
 * Created by zhangjh on 2015/7/23.
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var infoUrl = path + "/development/bom/info/";
    var projectSelectUrl = path + "/system/baseinfo/project_select";
    var appURL = path + '/resources/js/i18n/app/';
    $.extend({
        initBomDesc: initDesc,
        buildBomDesc: buildBomDesc
    })

    /**
     * 初始化描述信息
     */
    function initDesc(bomid, callback) {

        //初始化下拉列表
        reloadDescSelectData();

        initDescFileds(bomid, callback);

        //国际化
        i18nDesc();


    }

    function buildBomDesc(bominfo) {

        bominfo.offerAmount = $("#offerAmount").val();
        bominfo.projectId = $("#projectId").val();
        bominfo.sexId = $("#sexId").val();
        bominfo.mainColorOld = $("#mainColorOld").val();
        bominfo.mainColor = $("#mainColor").val();
        bominfo.fabricsEndDate = $("#fabricsEndDate").val();
        bominfo.accessoriesEndDate = $("#accessoriesEndDate").val();
        bominfo.preOfferDate = $("#preOfferDate").val();
        bominfo.clothReceivedDate = $("#clothReceivedDate").val();
        bominfo.natrualkey = $("#natrualkey").val();

    }

    function i18nDesc() {
        jQuery.i18n.properties({
            name: 'bom-add', //资源文件名称
            path: appURL, //资源文件路径
            mode: 'map', //用Map的方式使用资源文件中的值
            //language : 'zh',
            callback: function () {//加载成功后设置显示内容
                $('#customerLableId').html($.i18n.prop('CUSTOMER'));
                $('#saveBtnId').html($.i18n.prop('SAVA BTN'));
                $('#commitBtnId').html($.i18n.prop('SUBMIT BTN'));
            }
        });
    }


    //赋初始值
    function initDescFileds(bomid, callback) {
        if (bomid != '' && bomid != 'null') {
            $.sendRestFulAjax(infoUrl + bomid, null, 'GET', 'json', function (_data) {
                if (null != _data || "" != _data) {
                    if ($("#curBomId").val() == $("#refBomId").val()) {
                        Object.keys(_data).map(function (key) {
                            $('#bomDesc input').filter(function () {
                                return key == this.name;
                            }).val(_data[key]);
                            $('#bomDesc select').filter(function () {
                                return key == this.name;
                            }).val(_data[key]);
                            // $("#" + key).val(_data[key]);
                        });
                    }
                    callback(_data);
                }
                else{
                    bootbox.alert("没有找到BOM编号为"+bomid+"的信息.");
                }
            });
        }
    }

    //第一次初始化下拉列表 & 添加下拉列表监听事件
    var reloadDescSelectData = function () {
        if ($("#curBomId").val() == $("#refBomId").val()) {
            $.sendRestFulAjax(projectSelectUrl, null, 'GET', 'json', initDescSelect);
        }
    }

    function initDescSelect(_data) {

        var data = _data;
        //客户
        var yearCodeItems = data["customerItems"];
        $("#customerId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#customerId"));
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#customerId"));
        });

        //区域
        var areaItems = data["areaItems"];
        $("#areaId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#areaId"));
        $.each(areaItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#areaId"));
        });

        //系列
        var seriesItems = data["seriesItems"];
        $("#seriesId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#seriesId"));
        $.each(seriesItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#seriesId"));
        });


    }

    $(function () {

        $("#bomDescTitle").click(function () {
            $("#bomDescDetail").toggle(300);
        });

    })
}(jQuery));

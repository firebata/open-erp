/**
 * Created by zhangjh on 2015/10/27.
 */
(function ($) {

    "use strict";
    var path = $.basepath();
    $.extend({
        saveroleinfo: saveroleinfo
    });

    var user_infoURL = path + "/system/roleinfo/info/";
    var deptTypeURL = path + "/system/roleinfo/deptinfo";
    var qrallselectitemsURL = path + "/system/roleinfo/qrallselectitems";
    var newURL = path + "/system/roleinfo/new";
    var editURL = path + "/system/roleinfo/edit";
    var _1URL = path + "/system/permission/user-tab/1";
    var fieldsDesc =
    {
        name: {
            validators: {
                notEmpty: {
                    message: '名称为必填项'
                }
            }
        },
        roleDesc: {
            validators: {
                notEmpty: {
                    message: '角色描述'
                }
            }
        }
    };


    var doSaveAction = function () {
        var formDataStr = $("#roleinfoForm").serialize();
        var natrualkey = $("#natrualkey").val();
        var url;
        if (natrualkey == '' || natrualkey == 'null') {
            url = newURL;
        } else {
            url = editURL;
        }
        $.sendRestFulAjax(url, formDataStr, 'POST', 'json', function () {
            window.location.href = _1URL;
        });

    }


    function saveroleinfo() {
        //执行表单监听
        $('#roleinfoForm').bootstrapValidator('validate');
    }


    //启动表单校验监听
    $('#roleinfoForm').bootstrapValidator({
        //live: 'disabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: fieldsDesc
    }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
        doSaveAction();
    });


    $(function () {
        $.sendRestFulAjax(qrallselectitemsURL, null, 'GET', 'json', initSelectCallBack);
    })

    /**
     * 初始化部門信息
     * @param _data
     */
    function initSelectCallBack(_data) {

        var deptTypeItems = _data["deptTypeItems"]
        $("#deptTypeId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#deptId"));
        $.each(deptTypeItems, function (key, value) {
            $("<option></option>")
                .val(value["natrualkey"])
                .text(value["name"])
                .appendTo($("#deptId"));
        });


        var roleInfosItems = _data["roleInfosItems"];
        $("#parentId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#parentId"));
        $.each(roleInfosItems, function (key, value) {
            $("<option></option>")
                .val(value["natrualkey"])
                .text(value["name"])
                .appendTo($("#parentId"));
        });


        //初始化表单
        callback();
    }

    function callback() {
        var natrualkey = $("#natrualkey").val();
        if (natrualkey != '') {
            $.sendRestFulAjax(user_infoURL + natrualkey, null, 'GET', 'json', initFormFields);
        }
    }

    function initFormFields(_data) {
        //初始化赋值
        Object.keys(_data).map(function (key) {
            //下拉框
            $("#" + key).val(_data[key]);
        });
    }


}(jQuery));















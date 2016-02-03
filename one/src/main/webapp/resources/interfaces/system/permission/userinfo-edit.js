/**
 * Created by zhangjh on 2015/10/27.
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var newURL = path + "/system/userinfo/new";
    var user_infoURL = path + "/system/userinfo/info/";
    var editURL = path + "/system/userinfo/edit";
    var _0URL = path + "/system/permission/user-tab/0";
    var usertypeURL = path + "/system/userinfo/usertype";
    var fileUploadURL = path + "/files/upload";

    $.extend({
        saveuserinfo: saveuserinfo
    });

    var fieldsDesc =
    {
        name: {
            validators: {
                notEmpty: {
                    message: '名称为必填项'
                }
            }
        },
        aliases: {
            validators: {
                notEmpty: {
                    message: '用户别名为必填项'
                }
            }
        },
        password: {
            validators: {
                notEmpty: {
                    message: '密码为必填项'
                }
            }
        },
        rptUserPwd: {
            validators: {
                notEmpty: {
                    message: '密码确认为必填项'
                }
            }
        },
        userType: {
            validators: {
                notEmpty: {
                    message: '用户类型为必填项'
                }
            }
        },
        userEmail: {
            validators: {
                notEmpty: {
                    message: '用户邮箱为必填项'
                }
            }
        },
        userMobile: {
            validators: {
                notEmpty: {
                    message: '用户手机为必填项'
                }
            }
        }
    };


    var doSaveAction = function () {
        var formDataStr = $("#userinfoForm").serialize();
        var natrualkey = $("#natrualkey").val();
        var url;
        if (natrualkey == '' || natrualkey == 'null') {
            url = newURL;
        } else {
            url = editURL;
        }
        $.sendRestFulAjax(url, formDataStr, 'POST', 'json', function () {
            window.location.href = _0URL;
        });

    }


    function saveuserinfo() {
        //执行表单监听
        $('#userinfoForm').bootstrapValidator('validate');
    }


    //启动表单校验监听
    $('#userinfoForm').bootstrapValidator({
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

        //用户类型
        $.sendRestFulAjax(usertypeURL, null, 'GET', 'json', initSelect);


        //上传文件控制
        $("#fileLocation").fileinput({
            //showCaption: false,
            uploadUrl: fileUploadURL, // server upload action
            //initialPreview: [
            //    "<img src='http://himg.bdimg.com/sys/portrait/item/ee0d6f696463674e08' class='file-preview-image' alt='Desert' title='Desert'>"
            //]
        });

        $("#fileLocation").on("filepredelete", predeleteCallback);

        $("#fileLocation").on("filepredelete", function (jqXHR) {
            var abort = true;

           bootbox.confirm("Are you sure you want to delete this file?",function(result){

                abort = result;
            });

            return abort; // you can also send any data/object that you can receive on `filecustomerror` event
        });
    })

    function predeleteCallback(){

    }

    function confirm(msg){
        bootbox.confirm("Are you sure you want to delete this file?",function(result){

        });
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

    var initSelect = function (data) {

        $("#userType").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#userType"));
        $.each(data, function (key, value) {
            $("<option></option>")
                .val(key)
                .text(value)
                .appendTo($("#userType"));
        });
        callback();
    }


}(jQuery));















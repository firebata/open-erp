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
        $('#fileLocation').fileinput('upload');
        doSaveAction();
    });

    /**
     * response
     * @param response
     * @param f
     */
    function dealUploadedFile(response, $element, callback) {
        var configArr = response["initialPreviewConfig"];
        for (var idx = 0; idx < configArr.length; idx++) {
            $("#" + configArr[idx]['extra']['id']).remove();
            var ahref = '<li  id=\"' + configArr[idx]['extra']['id'] + '\"><a href=\"' + configArr[idx]['extra']['url'] + '\" class=\"blue\" target=\"_blank\">' + configArr[idx]['caption'] + '</a></li>';
            $element.append(ahref);
        }

    }

    $(function () {

        //用户类型
        $.sendRestFulAjax(usertypeURL, null, 'GET', 'json', initSelect);

        //上传文件控制
        $("#fileLocation").fileinput({
            //showCaption: false,
            language: "zh",
            'previewFileType': 'any',
            allowedFileExtensions: ["jpg", "png", "gif", "xls", "xlsx", "pdf", "jpeg"],
            //allowedPreviewTypes: ["jpg", "png", "gif", "jpeg"],
            uploadUrl: fileUploadURL, // server upload action
        });

        $('#fileLocation').on('fileuploaded', function (event, data, previewId, index) {
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
            console.log('File uploaded triggered');

            dealUploadedFile(response, $("#avatarsList"), function () {
            });

        });


        $("#fileLocation").on("filepredelete", function (jqXHR) {
            var abort = true;
            if (confirm("是否删除该资源?")) {
                abort = false;
            }
            return abort; // you can also send any data/object that you can receive on `filecustomerror` event
        });

        $('#fileLocation').on('filedeleted', function(event, key) {
            console.log('Key = ' + key);
            $("#" + key).remove();
        });

    })


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
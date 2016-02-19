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
    var uploadFileInfos = [];
    var isSubmit = "N";
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
        var userInfo = $.strToJson(formDataStr);
        userInfo.fileInfos = uploadFileInfos;

        $.sendJsonAjax(url, userInfo, function () {
            //$.sendRestFulAjax(saveUrl,bominfo, 'POST', 'json', function () {
            window.location.href = _0URL;
        })
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
        $('#fileLocation').fileinput('upload');//批量提交
        isSubmit = "Y";
    });

    /**
     * response
     * @param response
     * @param f
     */
    function dealUploadedFile(initialPreviewConfig, $element, callback) {

        for (var idx = 0; idx < initialPreviewConfig.length; idx++) {
            $("#" + initialPreviewConfig[idx]['extra']['id']).remove();
            var ahref = '<li  id=\"' + initialPreviewConfig[idx]['extra']['id'] + '\"><a href=\"' + initialPreviewConfig[idx]['extra']['url'] + '\"  download=\"' + initialPreviewConfig[idx]["caption"] + '\" class=\"blue\" target=\"_blank\">' + initialPreviewConfig[idx]['caption'] + '</a></li>';
            $element.append(ahref);
        }
        callback();
    }

    function initFileLocation(initialPreview, initialPreviewConfig) {
        //上传文件控制
        $("#fileLocation").fileinput({
            //showCaption: false,
            language: "zh",
            'previewFileType': 'any',
            'uploadAsync': false,
            allowedFileExtensions: ["jpg", "png", "gif", "xls", "xlsx", "pdf", "jpeg"],
            initialPreview: initialPreview,
            initialPreviewConfig: initialPreviewConfig,
            //allowedPreviewTypes: ["jpg", "png", "gif", "jpeg"],
            uploadUrl: fileUploadURL, // server upload action
        });
    }

    $(function () {

        //用户类型
        $.sendRestFulAjax(usertypeURL, null, 'GET', 'json', initSelect);

        //initFileLocation(null, null);



        $('#fileLocation').on('fileuploaded', function (event, data, previewId, index) {
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
            console.log('File uploaded triggered');
            buildUploadFileInfos(response);
            var initialPreviewConfig = response["initialPreviewConfig"];
            dealUploadedFile(initialPreviewConfig, $("#avatarsList"), function () {
            });

        });


        $("#fileLocation").on("filepredelete", function (jqXHR) {
            var abort = true;
            if (confirm("是否删除该资源?")) {
                abort = false;
            }
            return abort; // you can also send any data/object that you can receive on `filecustomerror` event
        });


        $('#fileLocation').on('filedeleted', function (event, key) {
            console.log('Key = ' + key);
            $("#" + key).remove();
        });


        /**
         * 批量上传成功后'uploadAsync':false
         */
        $('#fileLocation').on('filebatchuploadsuccess', function (event, data, previewId, index) {
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
            buildUploadFileInfos(response);
            if (isSubmit == 'Y') {
                doSaveAction();
            }
        });
    })

    /**
     * 构造文件上传记录
     * @param response
     */
    function buildUploadFileInfos(response) {
        var initialPreviewConfigs = response["initialPreviewConfig"];
        for (var idx = 0; idx < initialPreviewConfigs.length; idx++) {
            var initialPreviewConfig = initialPreviewConfigs[idx];
            var uid = initialPreviewConfig['extra']['id'];
            var uploadFileInfo = {};
            uploadFileInfo.uid = uid;
            uploadFileInfos.push(uploadFileInfo);
        }
        dealUploadedFile(initialPreviewConfigs, $("#avatarsList"), function () {
        });
    }

    function initSelectCallback() {

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

        loadFile(_data["fileinfosMap"])
    }


    function loadFile(fileinfosMap) {

        var initialPreview = fileinfosMap["initialPreview"];
        var initialPreviewConfig = fileinfosMap["initialPreviewConfig"];
        initFileLocation(initialPreview, initialPreviewConfig);
        //$("#fileLocation").fileinput({
        //    initialPreview: initialPreview,
        //    initialPreviewConfig: initialPreviewConfig
        //});

    }


    /**
     *
     * @param data
     */
    var initSelect = function (data) {

        $("#userType").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#userType"));
        $.each(data, function (key, value) {
            $("<option></option>")
                .val(key)
                .text(value)
                .appendTo($("#userType"));
        });

        initSelectCallback();
    }


}(jQuery));
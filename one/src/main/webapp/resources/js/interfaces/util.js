/*!
 * 前端js公用方法插件
 * Copyright 2011-2015 zhangjh.
 * Licensed under MIT (https://github.com/firebata/skysport/blob/master/LICENSE)
 */
(function ($) {
    "use strict";
    var submitBusURL = getContextPath() + "/task/submit";
    var passBusURL = getContextPath() + "/task/pass";
    var rejectBusURL = getContextPath() + "/task/reject";
    // var listCommentsURL = getContextPath() + "/task/list_comments/";
    //扩展常用方法
    $.extend({
        sendRestFulAjax: sendRestFulAjax,//ajax
        sendJsonAjax: sendJsonAjax,
        downloadAjax: downloadAjax,
        strToJson: strToJson,//&连接字符串转json对象
        strIsEmpty: strIsEmpty,//判断字符串为空
        strIsNotEmpty: strIsNotEmpty,//判断字符串不为空
        basepath: getContextPath,
        select2s: select2s,
        initSelect2s: initSelect2s,
        initSelect2sByArr: initSelect2sByArr,
        createDownloadLink: createDownloadLink,
        loadFileInput: loadFileInput,//初始化文件上传插件内容
        fileInputAddListenr: fileInputAddListenr,//上传插件动作监控
        buildUploadedFileInfos: buildUploadedFileInfos,//已上传的文件信息
        delTableRecord: delTableRecord,
        approveBuss: approveBuss,
        submitBuss: submitBuss,
        showHandleBtn: showHandleBtn,
        multiply: multiply,
        floatAdd: floatAdd,
        floatSub: floatSub,
        floatMul: floatMul,
        floatDiv: floatDiv,
        toSubmitApprove: toSubmitApprove
    })
    ;

    $.fn.extend({
        arrayVal: arrayVal,//返回模糊查询的数组值
    });

    function getContextPath() {
        var result = "";
        if (spe != environment_current) {
            var pathName = document.location.pathname;
            var index = pathName.substr(1).indexOf("/");
            result = pathName.substr(0, index + 1);
        }
        return result;
    }

    /**
     *返回数组值
     * @returns {Array}
     */
    function arrayVal() {
        var _self = $(this);
        var result = [];
        if (_self.length > 0) {
            _self.each(function (index, obj) {
                result.push($(obj).val());
            });

        }
        return result;
    }

    /**
     *
     * @param _url 路径
     * @param _data 数据
     * @param _type HTTP方法
     * @param _dataType 数据类型
     * @param _doSuccess 成功回调函数
     */
    function sendRestFulAjax(_url, _data, _type, _dataType, _doSuccess, async) {
        var type = strIsEmpty(_type) ? 'POST' : _type;
        //var dataType = _type == "undefined" || $.trim(_dataType) == '' ? 'json' : _dataType;
        var sf = strIsEmpty(_doSuccess) ? doSucess : _doSuccess;
        var async = strIsEmpty(async) ? true : async;
        $.ajax({
            url: _url,
            data: _data,
            type: type,
            //dataType: dataType,
            success: sf,
            async: async,
            error: doNotSucess
        });
    }

    Array.prototype.contains = function (needle) {
        for (var i in this) {
            if (this[i] == needle) return true;
        }
        return false;
    }

    /**
     *
     * @param _url 路径
     * @param _data 数据
     * @param _type HTTP方法
     * @param _dataType 数据类型
     * @param _doSuccess 成功回调函数
     */
    function sendJsonAjax(_url, _data, _doSuccess) {
        var sf = isFun(_doSuccess) ? _doSuccess : doSucess;
        $.ajax({
            url: _url,
            data: JSON.stringify(_data),
            type: "POST",
            dataType: "json",
            contentType: "application/json",
            success: sf,
            error: doNotSucess
        });
    }

    /**
     *
     * @param _url 路径
     * @param _data 数据
     * @param _type HTTP方法
     * @param _dataType 数据类型
     * @param _doSuccess 成功回调函数
     */
    function downloadAjax(_url, _data, _contentType, _doSuccess) {
        var sf = isFun(_doSuccess) ? _doSuccess : doSucess;
        $.ajax({
            url: _url,
            data: JSON.stringify(_data),
            type: "POST",
            dataType: "json",
            contentType: _contentType,
            success: sf,
            error: doNotSucess
        });
    }

    /*bootbox.setLocale("zh_CN");*/
    var doNotSucess = function (XMLHttpRequest, textStatus, errorThrown) {
        //console.error(XMLHttpRequest);
        bootbox.alert("本次操作失败.");
    }

    var doSucess = function (XMLHttpRequest, textStatus, errorThrown) {
        //bootbox.alert(XMLHttpRequest);
    }

    /**
     * 将“&”连接的字符串转换成Json
     * @param str “&”连接的字符串
     * @returns {Object}  Json
     */
    function strToJson(str) {
        str = str.replace(/&/g, "','");
        str = str.replace(/=/g, "':'");
        str = "({'" + str + "'})";
        var obj = eval(str);
        return obj;
    }

    /**
     * 校验字符串为空
     * @param input 字符串
     * @returns {boolean}
     */
    function strIsEmpty(input) {
        return input == null || $.trim(input) == '' || $.trim(input) == 'null';
    }

    function isFun(input) {
        return input != undefined && $.isFunction(input);
    }

    /**
     * 加
     * @param arg1
     * @param arg2
     * @returns {number}
     */
    function floatAdd(arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length
        } catch (e) {
            r2 = 0
        }
        m = Math.pow(10, Math.max(r1, r2));
        return (arg1 * m + arg2 * m) / m;
    }

    /**
     * 减
     * @param arg1
     * @param arg2
     * @returns {string}
     */
    function floatSub(arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length
        } catch (e) {
            r2 = 0
        }
        m = Math.pow(10, Math.max(r1, r2));
        //动态控制精度长度
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    }

    /**
     * 乘
     * @param arg1
     * @param arg2
     * @returns {number}
     */
    function floatMul(arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length
        } catch (e) {
        }
        try {
            m += s2.split(".")[1].length
        } catch (e) {
        }
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }


    /**
     * 除
     * @param arg1
     * @param arg2
     * @returns {number}
     */
    function floatDiv(arg1, arg2) {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length
        } catch (e) {
        }
        try {
            t2 = arg2.toString().split(".")[1].length
        } catch (e) {
        }

        r1 = Number(arg1.toString().replace(".", ""));

        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * Math.pow(10, t2 - t1);
    }

    function multiply(arg1, arg2) {
        var m = 0; //扩大后的两数相乘比初始值相乘扩大的倍数
        var s1 = arg1.toString();
        var s2 = arg2.toString();

        try {
            //获取第一个参数的小数部分长度，去掉小数点后，小数部分的长度就是初始值的小数点右移的位数
            m += s1.split(".")[1].length;
        } catch (e) {
        }

        try {
            ////获取第二个参数的小数部分长度，去掉小数点后，小数部分的长度就是初始值的小数点右移的位数
            m += s2.split(".")[1].length;
        } catch (e) {
        }

        //返回值：将参数的小数点去掉然后相乘，最后除以Mah.pow(10,m)
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }

    /**
     * 校验字符串不为空
     * @param input 字符串
     * @returns {boolean}
     */
    function strIsNotEmpty(input) {
        return !strIsEmpty(input);
    }

    /**
     * 初始化select2列表
     * @param _data 表单对象
     */
    function initSelect2sByArr($element, items) {
        if (items != null) {
            for (var idx = 0; idx < items.length; idx++) {
                var item = items[idx];
                var option = new Option(item.text, item.id, true, true);
                // Append it to the select
                $element.append(option);
            }
        }
        $element.trigger('change');
    }

    /**
     * 初始化select2列表
     * @param _data 表单对象
     */
    function initSelect2s(_fieldId, _id, _name, _data) {
        if (_fieldId == null || _id == null || _name == null || _data == null) {
            console.error("init select2 error:_id or _name is null or undefined");
            return;
        }
        var id = null;
        var text = null;

        Object.keys(_data).map(function (key) {
            if (key == _id) {
                id = _data[key];
            }
            else if (key == _name) {
                //text =  '<table><tr><td bgcolor="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>' + _data[key] + '</td></tr></table>'
                text = _data[key];
            }
        });
        var $element = $('#' + _fieldId).select2(); // the select element you are working with
        var option = new Option(text, id, true, true);
        $element.append(option);
        $element.trigger('change');
    }

    function createDownloadLink(filepath) {
        var tj = jQuery.noConflict();
        window.location.href = filepath;
    }

    var formatRepo = function (repo) {
        if (repo.loading) return repo.text;
        var markup = repo.name + '-' + repo.natrualkey;
        return markup;
    }

    var formatRepoSelection = function (repo) {
        return repo.text || repo.id;
    }


    /**
     *
     * @param $element select节点
     * @param _url ajax url
     * @param _placeholder
     * @param _searchInputPlaceholder
     * @param _queryInitData  初始化数据：数组，格式 [{id:id:text:text},{id:id:text:text}...]
     * @param _templateResult 选中的txt做进一步修饰，如增加颜色等
     */
    function select2s($element, _url, _placeholder, _searchInputPlaceholder, _queryInitData, _templateResult) {
        if (_url == null) {
            console.error("the url or _selectClass is null or undefined");
            return;
        }
        _placeholder = _placeholder == null ? 'No item not selected' : _placeholder;
        _searchInputPlaceholder = _searchInputPlaceholder == null ? 'Please, select one item' : _searchInputPlaceholder;
        //_templateResult = _templateResult == null ? function () {
        //} : _templateResult;
        $.fn.select2.defaults.set("width", "style");
        $element.select2({
            placeholder: _placeholder,
            searchInputPlaceholder: _searchInputPlaceholder,
            //maximumSelectionLength: 1,
            minimumInputLength: 2,//最少输入两个字符才能搜索
            language: "zh-CN",
            //allowClear: true,
            width: '100%',
            ajax: {
                url: _url,
                delay: 250,
                method: 'GET',
                dataType: 'json',
                quietMillis: 500,
                data: function (params) {
                    return {
                        name: params.term,
                        limit: 10
                    }

                },
                processResults: function (_data) {
                    var data = _data.items;
                    $.each(data, function (index, value) {
                        value.id = value.natrualkey;
                        value.text = value.name;
                    });
                    return {
                        results: data
                    };
                }
                //cache: true
            },
            formatRepo: formatRepo,
            templateSelection: formatRepoSelection,
            templateResult: _templateResult,
            escapeMarkup: function (m) {
                return m;
            }
        });

        initSelect2sByArr($element, _queryInitData());

    }

    /**
     * response
     * @param response
     * @param f
     */
    function dealUploadedFile($fileListLi, initialPreviewConfig, callback) {
        for (var idx = 0; idx < initialPreviewConfig.length; idx++) {
            $("#" + initialPreviewConfig[idx]['extra']['id']).remove();
            var ahref = '<li  id=\"' + initialPreviewConfig[idx]['extra']['id'] + '\"><a href=\"' + initialPreviewConfig[idx]['extra']['url'] + '\"  download=\"' + initialPreviewConfig[idx]["caption"] + '\" class=\"blue\" target=\"_blank\">' + initialPreviewConfig[idx]['caption'] + '</a></li>';
            $fileListLi.append(ahref);
        }
        callback();
    }

    /**
     * 初始化上传插件
     * @param $fileInput
     * @param initialPreview
     * @param initialPreviewConfig
     * @param fileUploadURL
     */
    function initFileLocation($fileInput, initialPreview, initialPreviewConfig, fileUploadURL) {
        //上传文件控制
        $fileInput.fileinput({
            //showCaption: false,
            overwriteInitial: false,//是否覆盖initial preview content
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

    /**
     * 初始化上传插件
     * @param $fileInput
     * @param fileinfosMap
     * @param fileUploadURL
     */
    function loadFileInput($fileInput, $fileListLi, fileinfosMap, fileUploadURL) {
        if (fileinfosMap == null) {
            fileinfosMap = {"initialPreview": [], initialPreviewConfig: []}
        }
        var initialPreview = fileinfosMap["initialPreview"];
        var initialPreviewConfig = fileinfosMap["initialPreviewConfig"];

        initFileLocation($fileInput, initialPreview, initialPreviewConfig, fileUploadURL);
        if (null != $fileListLi) {
            dealUploadedFile($fileListLi, initialPreviewConfig, function () {
            });
        }

        //$fileInput.fileinput({
        //    initialPreview: initialPreview,
        //    initialPreviewConfig: initialPreviewConfig
        //});

    }

    /**
     * 解析上传成功的文件信息
     * @param uploadFileInfos
     * @param initialPreviewConfigs
     */
    function buildUploadedFileInfos(uploadFileInfos, initialPreviewConfigs) {

        //null != uploadFileInfos 在bom详细表单起作用
        if (null != initialPreviewConfigs && null != uploadFileInfos) {
            for (var idx = 0; idx < initialPreviewConfigs.length; idx++) {
                var initialPreviewConfig = initialPreviewConfigs[idx];
                var uid = initialPreviewConfig['extra']['id'];
                var uploadFileInfo = {};
                uploadFileInfo.uid = uid;
                uploadFileInfos.push(uploadFileInfo);
            }
        }

    }

    /**
     * 构造文件上传记录
     * @param response
     */
    function buildUploadFileInfos($fileListLi, response, uploadFileInfos) {
        var initialPreviewConfigs = response["initialPreviewConfig"];

        buildUploadedFileInfos(uploadFileInfos, initialPreviewConfigs);

        if (null != $fileListLi) {
            dealUploadedFile($fileListLi, initialPreviewConfigs, function () {
            });
        }
    }


    function fileInputAddListenr($fileListLi, $fileInput, uploadFileInfos, doSaveAction, getIsSubmitAction) {


        $fileInput.on("filepredelete", function (jqXHR) {
            var abort = true;
            if (confirm("是否删除该资源?")) {
                abort = false;
            }
            return abort; // you can also send any data/object that you can receive on `filecustomerror` event
        });


        $fileInput.on('filedeleted', function (event, key) {
            $("#" + key).remove();
        });

        $fileInput.on('filebatchuploadcomplete', function (event, files, extra) {
            console.log('File batch upload complete');
        });


        $fileInput.on('fileuploaded', function (event, data, previewId, index) {
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;

            buildUploadFileInfos($fileListLi, response, uploadFileInfos);

            //var initialPreviewConfig = response["initialPreviewConfig"];
            //dealUploadedFile($fileListLi, initialPreviewConfig, function () {
            //});

        });


        /**
         * 批量上传成功后'uploadAsync':false
         */
        $fileInput.on('filebatchuploadsuccess', function (event, data, previewId, index) {
            var form = data.form, files = data.files, extra = data.extra,
                response = data.response, reader = data.reader;
            buildUploadFileInfos($fileListLi, response, uploadFileInfos);
            if (getIsSubmitAction() == 'Y') {
                doSaveAction();
            }
        });
    }


    /**
     * 删除信息
     * @param _natrualkey
     */
    function delTableRecord(_natrualkey, _url, $table, _msg) {
        // natrualkey = _natrualkey;
        if (null == _msg) {
            _msg = "您正在执行删除操作，确定执行删除吗？"
        }
        bootbox.confirm(_msg, function (result) {
            doDel(result, _url, $table);
        });

    }

    /**
     *
     * @param result
     */
    var doDel = function (result, _url, $table) {
        if (result)  $.sendRestFulAjax(_url, null, 'DELETE', 'json',
            function () {
                _doSuccess_del($table);
            });
        //natrualkey = ""; //将修改项的natrualkey置为空
    }


    /**
     * 删除信息后的处理
     * @private
     */
    var _doSuccess_del = function ($table) {
        $table.ajax.reload();
    }


    $(function () {
        $.ajaxSetup({
            beforeSend: beforeSend
        });
    })

    function beforeSend(xhr) {
        // console.log("ajax提交...");
    }

    /**
     *  业务详细页面的按钮显示处理：保存，提交审核(未启动流程),提交审核(流程已启动，被驳回后的状态)
     * @param $btnDIV
     * @param _approveStatus
     * @param saveFun
     * @param _businessKey
     * @param _taskId
     * @param _stateCode
     */
    function showHandleBtn($btnDIV, _approveStatus, saveFun, _businessKey, _taskId, _stateCode, processInstanceId) {
        if (strIsEmpty(_taskId)) {
            _taskId = "null";
        }
        var html = ""
        if (_approveStatus == approve_status_new || _approveStatus == approve_status_reject) {
            html = "<div class='col-xs-offset-4 col-xs-1'><button type='button' id='saveBtn' class='btn btn-info btn-md'>保  存</button></div>";
            if (_stateCode == statecode_alive && strIsNotEmpty(_taskId)) {//流程在运行中：审核走通用接口，只需要传递业务主键和任务id(这个界面值来源于)
                html += "<div class='col-xs-1'><button type='button' class='btn btn-info btn-md' onclick='javascript:$.submitBuss(\"" + _businessKey + "\",\"" + _taskId + "\")'>提交审核</button></div>";
            } else {
                html += "<div class='col-xs-1'><button type='button' class='btn btn-info btn-md'  id='submitBtn' >提交审核</button></div>";
            }
        }
        else if (_approveStatus == approve_status_undo) {
            if (_stateCode == statecode_alive && strIsNotEmpty(_taskId)) {
                html = "<div class='col-xs-offset-6 col-xs-1'><button type='button' class='btn btn-info btn-md' onclick='javascript:$.approveBuss(\"" + _businessKey + "\",\"" + _taskId + "\",\"" + processInstanceId + "\")'>审核</button></div>";
            }
        }
        $btnDIV.html(html);
    }


    function approveBuss(_businessKey, _taskId, _processInstanceId) {
        showApproveForms(_businessKey, _taskId, _processInstanceId);
    }

    function submitBuss(_businessKey, _taskId) {
        window.location.href = submitBusURL + "/" + _taskId + "/" + _businessKey;
        // $.sendJsonAjax(submitBusURL + "/" + _taskId + "/" + _businessKey, {}, function () {
        //     window.location.href = project_listURL;
        // })
    }


    function showApproveForms(_businessKey, _taskId, _processInstanceId) {
        bootbox.dialog({
                title: "请输入审核意见.",
                message: '<div class="row">  ' +
                '<div class="col-md-12"> ' +
                '<form class="form-horizontal"> ' +
                '<div class="form-group"> ' +
                '<label class="col-md-2 control-label" for="suggestion">意见</label> ' +
                '<div class="col-md-10"> ' +
                '<textarea id="suggestion" name="suggestion" type="text" placeholder="审核意见" class="form-control  autosize-transition"  /> ' +
                ' </div> ' +
                '</div> ' +
                '</form> </div>  </div>',
                buttons: {
                    success: {
                        label: "审核通过",
                        className: "btn-success",
                        callback: function () {
                            var suggestion = $('#suggestion').val();
                            addCommentsAndApprove(_businessKey, _taskId, _processInstanceId, suggestion, approve_status_pass);
                            // Example.show("你选择了审核通过");
                        }
                    },
                    danger: {
                        label: "驳回",
                        className: "btn-danger",
                        callback: function () {
                            var suggestion = $('#suggestion').val();
                            addCommentsAndApprove(_businessKey, _taskId, _processInstanceId, suggestion, approve_status_reject);
                            // Example.show("你选择了驳回");
                        }
                    }
                }
            }
        );
    }


    /**
     * 增加审核评论 并做审核处理：通过或者驳回
     * @param _businessKey
     * @param _taskId
     * @param _processInstanceId
     * @param _message
     * @param _approve_status
     */
    function addCommentsAndApprove(_businessKey, _taskId, _processInstanceId, _message, _approve_status) {
        var _data = {
            businessKey: _businessKey,
            taskId: _taskId,
            processInstanceId: _processInstanceId,
            message: _message
        };

        var url = null;
        if (_approve_status == approve_status_pass) { //审核通过
            url = passBusURL;
        } else if (_approve_status == approve_status_reject) { //驳回
            url = rejectBusURL;
        }
        if (strIsNotEmpty(url)) {
            $.post(url, _data, function (resp) {
                window.location.href = getContextPath() + "/task/todo/list";
            });

            // $.sendJsonAjax(url, _data, null)
        }
    }

    /**
     *
     * @param submitURL
     * @param listURL
     */
    function toSubmitApprove(submitURL, listURL) {
        var nk = $("#natrualkey").val();
        var taksId = "null";
        $.sendJsonAjax(submitURL + taksId + "/" + nk, {}, function () {
            window.location.href = listURL;
        })
    }

}(jQuery));
/*!
 * 前端js公用方法插件
 * Copyright 2011-2015 zhangjh.
 * Licensed under MIT (https://github.com/firebata/skysport/blob/master/LICENSE)
 */
(function ($) {
    "use strict";
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
        initSelect2sByArr:initSelect2sByArr
    });


    $.fn.extend({
        arrayVal: arrayVal,//返回模糊查询的数组值
    });


    function getContextPath() {
        var pathName = document.location.pathname;
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0, index + 1);
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
    function sendRestFulAjax(_url, _data, _type, _dataType, _doSuccess) {
        var type = strIsEmpty(_type) ? 'POST' : _type;
        //var dataType = _type == "undefined" || $.trim(_dataType) == '' ? 'json' : _dataType;
        var sf = strIsEmpty(_doSuccess) ? doSucess : _doSuccess;
        $.ajax({
            url: _url,
            data: _data,
            type: type,
            //dataType: dataType,
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
        return input == undefined || $.trim(input) == ''
    }

    function isFun(input) {
        return input != undefined && $.isFunction(input);
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
    function initSelect2sByArr(_fieldId, _id, _name, _arr) {
        if (_fieldId == null || _id == null || _name == null || _arr == null) {
            console.error("init select2 error:_id or _name is null or undefined");
            return;
        }
        var id = null;
        var text = null;
        var $element = $('#' + _fieldId).select2(); // the select element you are working with
        for (var idx = 0; idx <_arr.length; idx++) {
            var _data = _arr[idx];
            Object.keys(_data).map(function (key) {
                if (key == _id) {
                    id = _data[key];
                }
                else if (key == _name) {
                    //text =  '<table><tr><td bgcolor="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>' + _data[key] + '</td></tr></table>'
                    text = _data[key];
                }
            });
            var option = new Option(text, id, true, true);
            $element.append(option);
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


    var formatRepo = function (repo) {
        if (repo.loading) return repo.text;
        var markup = repo.name + '-' + repo.natrualkey;
        return markup;
    }

    var formatRepoSelection = function (repo) {
        return repo.text || repo.id;
    }


    /**
     *  elect2的重载方法
     *  注意：下拉列表的key名字为：natrualkey;name的名字为name
     * @param _selectId
     * @param _url
     * @param _placeholder
     * @param _searchInputPlaceholder
     * @param _templateResult
     */
    function select2s(_selectId, _url, _placeholder, _searchInputPlaceholder,_queryInitData,_templateResult) {


        if (_url == null || _selectId == null) {
            console.error("the url or _selectClass is null or undefined");
            return;
        }
        _placeholder = _placeholder == null ? 'No item not selected' : _placeholder;
        _searchInputPlaceholder = _searchInputPlaceholder == null ? 'Please, select one item' : _searchInputPlaceholder;
        //_templateResult = _templateResult == null ? function () {
        //} : _templateResult;
        $.fn.select2.defaults.set("width", "style");
        $('#' + _selectId).select2({
            placeholder: _placeholder,
            searchInputPlaceholder: _searchInputPlaceholder,
            //maximumSelectionLength: 1,
            minimumInputLength: 2,//最少输入两个字符才能搜索
            language: "zh-CN",
            allowClear: true,
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
                },
                initSelection: function (element, callback) {
                    callback(_queryInitData());
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
    }


}(jQuery));
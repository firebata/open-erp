/**
 * 列表展示内容
 * @returns {*[]}
 */
var columnsName = [
    {"data": "name"},
    {"data": "deptAdmin"},
    {"data": "bussLicen"},
    {"data": "parentId"},
    {"data": "cityCode"},
    {"data": "countryCode"},
    {"data": null}
];


/**
 * 新增/修改校验字段描述
 * @returns {{name: {validators: {notEmpty: {message: string}}}, customerId: {validators: {notEmpty: {message: string}}}}}
 */
var fieldsDesc =
{
    name: {
        validators: {
            notEmpty: {
                message: '区域名称为必填项'
            }
        }
    },
    deptAdmin: {
        validators: {
            notEmpty: {
                message: '部门负责人为必填项'
            }
        }
    }
}


/**
 * 初始化列表
 * @param _data 表单对象
 */
var initSelect = function (_data) {
    var id = null;
    var text = null;

    Object.keys(_data).map(function (key) {
        if (key == 'customerId') {
            id = _data[key];
        }
        else if (key == 'customerName') {
            text = _data[key];
        }
    });

    var $element = $('.js-data-example-ajax').select2(); // the select element you are working with
    var option = new Option(text, id, true, true);
    $element.append(option);
    $element.trigger('change');

}

/**
 * 重置下拉框
 */
var resetSelect = function () {
    $('.js-data-example-ajax').select2('val', '');
}


/**
 * 立即执行
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var qrallselectitemsURL = path + "/system/dept/qrallselectitems";
    $(function () {
        $.sendRestFulAjax(qrallselectitemsURL, null, 'GET', 'json', initSelectCallBack);
    })


    function initSelectCallBack(_data) {
        var deptTypeItems = _data["deptTypeItems"]
        $("#deptTypeId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#parentId"));
        $.each(deptTypeItems, function (key, value) {
            $("<option></option>")
                .val(value["natrualkey"])
                .text(value["name"])
                .appendTo($("#parentId"));
        });

    }


})(jQuery)








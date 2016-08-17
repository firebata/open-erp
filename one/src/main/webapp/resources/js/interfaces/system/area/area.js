/**
 * Created by zhangjh on 2015/6/9.
 */
/**
 * 列表展示内容
 * @returns {*[]}
 */
var columnsName = [
    {"data": "name"},
    {"data": "customerName"},
    {"data": "remark"},
    {"data": "updateTime"},
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
    customerId: {
        validators: {
            notEmpty: {
                message: '客户名称为必填项'
            }
        }
    }
}

/**
 * 初始化列表
 * @param _data 表单对象
 */
function initSelect(_data) {
    var $element = $("#customerId");
    var path = $.basepath();
    $.select2s($element, path + '/system/customer/select', 'Customer not selected', 'Please, select customer', function () {
        return [{"id": _data["customerId"], "text": _data["customerName"]}];
    });
    //$.initSelect2s('customerId', 'customerId', 'customerName', _data);

}

/**
 * 重置下拉框
 */
var resetSelect = function () {
    $('.js-data-example-ajax').val(null).trigger("change");
}


$(function () {

    initSelect({});

    $('#resetBtn').click(function () {
        $('.js-data-example-ajax').select2({
            allowClear: true,
            placeholder: 'Customer not selected',
            data: {}
        });
    });


})








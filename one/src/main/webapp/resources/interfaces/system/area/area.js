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
    $.initSelect2s('customerId', 'customerId', 'customerName', _data);
    //
    //
    //var id = null;
    //var text = null;
    //Object.keys(_data).map(function (key) {
    //    if (key == 'customerId') {
    //        id = _data[key];
    //    }
    //    else if (key == 'customerName') {
    //        //text =  '<table><tr><td bgcolor="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>' + _data[key] + '</td></tr></table>'
    //        text = _data[key];
    //    }
    //});
    //var $element = $('#customerId').select2(); // the select element you are working with
    //var option = new Option(text, id, true, true);
    //$element.append(option);
    //$element.trigger('change');
}

/**
 * 重置下拉框
 */
var resetSelect = function () {
    $('.js-data-example-ajax').select2('val', '');
}


$(function () {
    var path = $.basepath();
    $('#myModal').on('shown.bs.modal', function (e) {
        $.select2s('customerId', path + '/system/customer/select', 'Customer not selected', 'Please, select customer');
    });

    $('#resetBtn').click(function () {
        $('.js-data-example-ajax').select2({
            allowClear: true,
            placeholder: 'Customer not selected',
            data: {}
        });
    });


})

function formatState(state) {
    if (!state.id) {
        return state.text;
    }
    var $state = $(
        '<table><tr><td bgcolor="red">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>' + state.text + '</td></tr></table>'
    );
    return $state;
}






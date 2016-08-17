/**
 * Created by zhangjh on 2015/6/18.
 */
/**
 * 列表展示内容
 * @returns {*[]}
 */
var columnsName = [
    {"data": "name"},
    {"data": "materialTypeName"},
    {"data": "remark"},
    {"data": "updateTime"},
    {"data": null}
];


/**
 * 新增/修改校验字段描述
 * @returns {{name: {validators: {notEmpty: {message: string}}}, materialTypeId: {validators: {notEmpty: {message: string}}}}}
 */
var fieldsDesc =
{
    name: {
        validators: {
            notEmpty: {
                message: '名称为必填项'
            }
        }
    },
    materialTypeId: {
        validators: {
            notEmpty: {
                message: '材料类型为必填项'
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
        if (key == 'materialTypeId') {
            id = _data[key];
        }
        else if (key == 'materialTypeName') {
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

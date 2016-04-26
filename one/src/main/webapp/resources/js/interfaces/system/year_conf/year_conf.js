/**
 * Created by zhangjh on 2015/6/18.
 */
/**
 * 列表展示内容
 * @returns {*[]}
 */
var columnsName = [
    {"data": "yearCode"},
    {"data": "name"},
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
                message: '中文名称为必填项'
            }
        }
    }
}


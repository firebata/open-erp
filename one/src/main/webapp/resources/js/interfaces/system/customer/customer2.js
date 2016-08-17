/**
 * 列表展示内容
 * @returns {*[]}
 */
var columnsName = [
    {"data": "name"},
    {"data": "fullName"},
    {"data": "address"},
    {"data": "email"},
    {"data": "tel"},
    {"data": "corTime"},
    {"data": "updateTime"},
    {"data": null}
];


/**
 * 新增/修改校验字段描述
 * @returns {{name: {validators: {notEmpty: {message: string}}}, fullName: {validators: {notEmpty: {message: string}}}, email: {validators: {notEmpty: {message: string}}}, corTime: {validators: {notEmpty: {message: string}}}, address: {validators: {notEmpty: {message: string}}}, contact: {validators: {notEmpty: {message: string}}}, tel: {validators: {notEmpty: {message: string}}}}}
 */
var fieldsDesc =
{
    name: {
        validators: {
            notEmpty: {
                message: '客户名称为必填项'
            }
        }
    }
}



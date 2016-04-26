/**
 * Created by zhangjh on 2015/6/9.
 */
/**
 * 列表展示内容
 * @returns {*[]}
 */
var columnsName = [
    {"data": "name"},
    {"data": "remark"},
    {"data": null}
];


/**
 * 新增/修改校验字段描述
 * @returns {{name: {validators: {notEmpty: {message: string}}}}}
 */
var fieldsDesc =
{
    name: {
        validators: {
            notEmpty: {
                message: '区域名称为必填项'
            }
        }
    }
}






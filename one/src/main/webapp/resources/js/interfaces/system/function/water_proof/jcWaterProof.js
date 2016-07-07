/**
 * Created by zhangjh on 2016-07-05.
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
                message: '水压为必填项'
            }
        }
    }
}



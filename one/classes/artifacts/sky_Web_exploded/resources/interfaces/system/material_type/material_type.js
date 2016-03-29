/**
 * Created by zhangjh on 2015/6/18.
 */
/**
 * 列表展示内容
 * @returns {*[]}
 */
var columnsName = [
    {"data": "materialTypeCode"},
    {"data": "name"},
    {"data": "levelId"},
    {"data": "remark"},
    {"data": null}
];


/**
 * 新增/修改校验字段描述
 * @returns {{materialTypeCode: {validators: {notEmpty: {message: string}}}, name: {validators: {notEmpty: {message: string}}}, levelId: {validators: {notEmpty: {message: string}}}}}
 */
var fieldsDesc =
{
    materialTypeCode: {
        validators: {
            notEmpty: {
                message: '类型编码为必填项'
            }
        }
    },
    name: {
        validators: {
            notEmpty: {
                message: '中文名称 is required and cannot be empty'
            }
        }
    },
    levelId: {
        validators: {
            notEmpty: {
                message: '级别为必填项'
            }
        }
    }
}

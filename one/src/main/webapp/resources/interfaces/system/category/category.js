/**
 * Created by zhangjh on 2015/7/3.
 */
/**
 * 列表展示内容
 * @returns {*[]}
 */
var columnsName = [
    {"data": "name"},
    {"data": "levelId"},
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
    }
}
$(function () {
    $("#defaultForm").on("change", "#levelId", cb);
})

function cb() {
    initParentId();
}
var initSelect = function (_data) {
    var id = null;
    Object.keys(_data).map(function (key) {
        if (key == 'levelId') {
            id = _data[key];
        }
    });
    $('.selectpicker').selectpicker('val', id);

    initParentId(_data["parentCategoryId"]);
}


function initParentId(parentCategoryId) {
    var levelId = $("#levelId").val();
    var selectURL = $.basepath() + '/system/category/select';

    $("#parentCategoryId").empty();
    $("<option></option>").val('').text("请选择...").appendTo($("#parentCategoryId"));
    //选择了二级平泪
    if (levelId == 2) {
        $.sendRestFulAjax(selectURL, {"name": 1}, 'GET', 'json', function (_data) {
            initSelectCallBack(_data, parentCategoryId)
        });
    }
}


function initSelectCallBack(_data, parentCategoryId) {
    $("#parentCategoryId").empty();
    $("<option></option>").val('').text("请选择...").appendTo($("#parentCategoryId"));
    $.each(_data['items'], function (key, value) {
        $("<option></option>")
            .val(value["natrualkey"])
            .text(value["name"])
            .appendTo($("#parentCategoryId"));
    });
    $("#parentCategoryId").val(parentCategoryId);
}








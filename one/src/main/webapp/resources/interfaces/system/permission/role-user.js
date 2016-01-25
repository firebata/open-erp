/**
 * 用户角色分配信息处理模块
 * Created by zhangjh on 2015/10/27.
 */
(function ($) {

    "use strict";
    var path = $.basepath();
    var saveRoleUserURL = path + "/system/permission/saveRoleUser";
    var selectURL = path + "/system/userinfo/select";
    var queryAllRoleURL = path + "/system/permission/queryAllRole/";
    $.extend({
        reloadRoleUser: reloadRoleUser
    });

    $(function () {
        reloadRoleUser();

        $("#saveRoleUserBtn").on("click", saveRoleUser);
        $("#userId").on("change", changeUser);

    })


    var setting = {

        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        }

    };


    /**
     * 设置选中状态
     */
    function setCheck() {
        var zTree = $.fn.zTree.getZTreeObj("roleUserTree"),
        //type = {"Y": "p", "N": "ps"};
            type = {"Y": "s", "N": "s"};
        zTree.setting.check.chkboxType = type;

    }

    function changeUser() {
        var userId = $(this).val();
        $.sendJsonAjax(queryAllRoleURL + userId, {}, initZTree);
    }


    /**
     * 保存角色用户分配信息
     */
    function saveRoleUser() {

        var userId = $("#userId").val();
        if (userId == undefined || userId === '') {
            bootbox.alert("本次操作失败.");
        } else {
            var treeObj = $.fn.zTree.getZTreeObj("roleUserTree");
            var selectRoles = treeObj.getCheckedNodes(true);
            console.info("selectRoles:" + selectRoles);
            var roleUser = {};
            roleUser.userId = userId;
            roleUser.zTreeNodes = selectRoles;
            $.sendJsonAjax(saveRoleUserURL, roleUser);
        }

    }

    /**
     * 查询用户信息
     */
    function reloadRoleUser() {

        $.sendJsonAjax(selectURL, {}, initUserInfo);

    }

    /**
     * 初始化用户下拉列表
     * @param _data
     */
    function initUserInfo(_data) {

        //材料类别
        var userId = "#userId";
        $(userId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(userId));
        $.each(_data.items, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(userId));
        });
        var userId = 'null';
        $.sendJsonAjax(queryAllRoleURL + userId, {}, initZTree);
    }


    /**
     * 初始化角色树
     * @param _data
     */
    function initZTree(_data) {
        var zNodes = _data;
        $.fn.zTree.init($("#roleUserTree"), setting, eval(zNodes));
        setCheck();
        $.fn.zTree.getZTreeObj("roleUserTree").expandAll(true);
    }

}(jQuery));















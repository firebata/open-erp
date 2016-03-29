/**
 * 用户角色分配信息处理模块
 * Created by zhangjh on 2015/10/27.
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var queryAllResURL = path + "/system/permission/queryAllRes/";
    var saveResourceRoleURL = path + "/system/permission/saveResourceRole";
    var roleinfoSelectURL = path + "/system/roleinfo/select";
    $.extend({
        reloadResourceRole: reloadResourceRole
    });

    $(function () {
        reloadResourceRole();

        $("#saveResourceRoleBtn").on("click", saveResourceRole);

        $("#roleId").on("change", changeRole);

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
        var zTree = $.fn.zTree.getZTreeObj("ResourceRoleTree"),
            type = {"Y": "p", "N": "ps"};
        zTree.setting.check.chkboxType = type;
    }


    function changeRole() {
        var roleId = $(this).val();
        $.sendJsonAjax(queryAllResURL + roleId, {}, initZTree);
    }


    /**
     * 保存角色用户分配信息
     */
    function saveResourceRole() {

        var roleId = $("#roleId").val();
        if (roleId == undefined || roleId === '') {
            bootbox.alert("本次操作失败.");
        } else {
            var treeObj = $.fn.zTree.getZTreeObj("ResourceRoleTree");
            var selectRoles = treeObj.getCheckedNodes(true);
            //console.info("selectRoles:" + selectRoles);
            var roleUser = {};
            roleUser.roleId = roleId;
            roleUser.zTreeNodes = selectRoles;
            $.sendJsonAjax(saveResourceRoleURL, roleUser);
        }

    }

    /**
     * 查询用户信息
     */
    function reloadResourceRole() {
        $.sendJsonAjax(roleinfoSelectURL, {}, initRoleInfo);
    }

    /**
     * 初始化用户下拉列表
     * @param _data
     */
    function initRoleInfo(_data) {

        //材料类别
        var roleId = "#roleId";
        $(roleId).empty();
        $("<option></option>").val('').text("请选择...").appendTo($(roleId));
        $.each(_data.items, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($(roleId));
        });
        var roleId = 'null';
        $.sendJsonAjax(queryAllResURL + roleId, {}, initZTree);
    }


    /**
     * 初始化角色树
     * @param _data
     */
    function initZTree(_data) {
        var zNodes = _data;
        var tem = JSON.stringify(zNodes);
        //console.info("tem:" + tem);
        $.fn.zTree.init($("#ResourceRoleTree"), setting, zNodes);
        setCheck();
    }


}(jQuery));















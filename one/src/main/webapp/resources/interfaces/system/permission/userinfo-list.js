/**
 * Created by zhangjh on 2015/10/27.
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var searchUrl = path + "/system/userinfo/search";
    var chgPWDURL = path + "/system/userinfo/chgpwd";
    var _0URL = path + "/system/permission/user-tab/0";
    $.extend({
        userinfo: userinfo,
        chgpwd: chgpwd //改密码
    });

    var columnsName = [
        {"data": "name"},
        {"data": "userType"},
        {"data": "isOnline"},
        {"data": "lockFlag"},
        {"data": "isLimit"},
        {"data": "lastLoginTime"},
        {"data": "remark"},
        {"data": null}
    ];


    $("#savePWD").on("click", function () {
        //执行表单监听
        $('#defaultForm').bootstrapValidator('validate');
    });

    /**
     * 改密码
     */
    function dealChgPwd() {


        //启动表单校验监听
        $('#defaultForm').bootstrapValidator({
            //live: 'disabled',
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: pwdFiled
        }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
            var $form = $(e.target);
            var data = $form.serialize();
            $.sendRestFulAjax(chgPWDURL, data, null, null, _doSuccess_save);
        });
    }

    $(function () {


        //处理改密
        dealChgPwd();

        var tpl = $("#userInfotpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        var indexOpreation = columnsName.length - 1;

        $('#userInfoExample').DataTable({
            ajax: {
                url: searchUrl
            },
            serverSide: true,
            columns: columnsName,
            order: [[0, "desc"]], /*默认第一列倒序*/
            columnDefs: [
                {
                    targets: indexOpreation,
                    render: function (data, type, row, meta) {
                        var context =
                        {
                            func: [
                                {"name": "更新", "fn": "$.userinfo(\'" + data.natrualkey + "\')", "type": "primary"},
                                {"name": "改密", "fn": "$.chgpwd(\'" + data.natrualkey + "\')", "type": "warning"},
                                {"name": "删除", "fn": "$.userDel(\'" + data.natrualkey + "\')", "type": "danger"}
                            ]
                        };
                        var html = template(context);
                        return html;
                    }
                }
            ],
            "language": {
                "lengthMenu": "_MENU_",
                "zeroRecords": "没有找到记录",
                "info": "第 _PAGE_ 页 ( 共 _PAGES_ 页 )",
                "infoEmpty": "无记录",
                "infoFiltered": "(从 _MAX_ 条记录过滤)",
                "paginate": {
                    "previous": "上一页",
                    "next": "下一页"
                }
            },
            "dom": "<'row'<'col-xs-2'l><'#mytool.col-xs-4'><'col-xs-6'f>r>" +
            "t" +
            "<'row'<'col-xs-6'i><'col-xs-6'p>>",
            initComplete: function () {
                $("#mytool").append('<button type="button" id="addBtnAddListPage" class="btn btn-primary" data-toggle="modal">新增</button>');
            }
        });


        //事件传递
        $("#userinfo").on("click", "#addBtnAddListPage", function () {
            var userId = "null";
            userinfo(userId);
        })


    });

    function userinfo(userId) {
        window.location.href = path + "/system/userinfo/add/" + userId;
    }


    /**
     * 显示改密页面
     * @param _userId 用户id
     */
    function chgpwd(_userId) {
        $("#password").empty();
        $("#natrualkey").val(_userId);
        $("#chgpwdModal").modal("show");
    }


    var pwdFiled =
    {
        password: {
            validators: {
                notEmpty: {
                    message: '请输入密码'
                }
            }
        }
    };


    /**
     *
     * @private
     */
    function _doSuccess_save() {
        var length = $(".modal-backdrop").length;
        for (var index = 0; index < length; index++) {
            $("#chgpwdModal").modal('hide');//移除模态框遮罩层
        }
        window.location.href = _0URL;
    }


}(jQuery));















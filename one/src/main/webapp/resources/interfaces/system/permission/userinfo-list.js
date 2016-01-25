/**
 * Created by zhangjh on 2015/10/27.
 */
(function ($) {
    "use strict";
    var path = $.basepath();
    var searchUrl = path + "/system/userinfo/search";
    $.extend({
        userinfo: userinfo
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

    var table;
    $(function () {

        var tpl = $("#userInfotpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        var indexOpreation = columnsName.length - 1;

        table = $('#userInfoExample').DataTable({
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
                                {"name": "修改", "fn": "$.userinfo(\'" + data.natrualkey + "\')", "type": "primary"},
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

}(jQuery));















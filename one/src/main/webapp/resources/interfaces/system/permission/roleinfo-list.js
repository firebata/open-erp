/**
 * Created by zhangjh on 2015/10/27.
 */
(function ($) {
    "use strict";
    $.extend({
        roleinfo: roleinfo
    });
    var path = $.basepath();
    var searchUrl = path + "/system/roleinfo/search";
    var addURL = path + "/system/roleinfo/add/";
    var columnsName = [
        {"data": "name"},
        {"data": "parentId"},
        {"data": "deptTypeId"},
        {"data": "roleDesc"},
        {"data": "remark"},
        {"data": null}
    ];

    var table;
    $(function () {

        var tpl = $("#roleInfotpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        var indexOpreation = columnsName.length - 1;

        table = $('#roleInfoExample').DataTable({
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
                                {"name": "更新", "fn": "$.roleinfo(\'" + data.natrualkey + "\')", "type": "primary"},
                                {"name": "删除", "fn": "$.roleDel(\'" + data.natrualkey + "\')", "type": "danger"}
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
            "dom": "<'row'<'col-xs-2'l><'#myRoleTool.col-xs-4'><'col-xs-6'f>r>" +
            "t" +
            "<'row'<'col-xs-6'i><'col-xs-6'p>>",
            initComplete: function () {
                $("#myRoleTool").append('<button type="button" id="addBtnAddInRoleTab" class="btn btn-primary" data-toggle="modal">新增</button>');
            }
        });

        //事件传递
        $("#roleinfo").on("click", "#addBtnAddInRoleTab", function () {
            var roleId = "null";
            roleinfo(roleId);
        })

    });

    function roleinfo(roleId) {
        window.location.href = addURL + roleId;
    }

}(jQuery));















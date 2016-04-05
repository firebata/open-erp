/**
 * Created by zhangjh on 2015/7/16.
 */
(function () {
    "use strict";
    var path = $.basepath();
    var project_selectURL = path + "/system/baseinfo/project_select";
    /**
     * 列表展示内容
     * @returns {*[]}
     */
    var columnsName = [
        {"data": null},
        {"data": "task.name"},
        {"data": "task.createTime"},
        {"data": "processInstance.suspended"},
        {"data": null}
    ];


    var mHtml = '';
    var table;

    $(function () {
        var tpl = $("#tpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        var indexOpreation = columnsName.length - 1;
        table = $('#example').DataTable({
            ajax: {
                url: "search"
            },
            serverSide: true,
            columns: columnsName,
            order: [[1, "desc"]], /*默认第一列倒序*/
            columnDefs: [
                {
                    targets: 0,
                    render: function (data, type, row, meta) {
                        var html = "<input type='checkbox' name='checkList' value='" + data.natrualkey + "'>";
                        return html;
                    }
                },
                {
                    targets: indexOpreation,
                    render: function (data, type, row, meta) {
                        var context =
                        {
                            func: [
                                {"name": "更新", "fn": "$.editProject(\'" + data.natrualkey + "\')", "type": "primary"}
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
            "dom": "<'row'<'col-xs-1'l><'#mytool.col-xs-9'><'col-xs-2'f>r>" +
            "t" +
            "<'row'<'col-xs-6'i><'col-xs-6'p>>",
            initComplete: function () {
                $("#mytool").append(mHtml);
            }

        });


    });


    var editProject = function (_projectId) {
        window.location.href = "add/" + _projectId;
    }
    $.editProject = editProject;
}());
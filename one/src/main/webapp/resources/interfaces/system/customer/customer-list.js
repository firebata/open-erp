var table;
var editFlag = false;
$(function () {

    $('#start_date').datetimepicker();

    var tpl = $("#tpl").html();
    //预编译模板
    var template = Handlebars.compile(tpl);


    table = $('#example').DataTable({
        ajax: {
            url: "search"
        },
        serverSide: true,
        columns: [
            {"data": "natrualkey"},
            {"data": "name"},
            {"data": "fullName"},
            {"data": "address"},
            {"data": "email"},
            {"data": "tel"},
            {"data": "corTime"},
            {"data": null}
        ],
        order: [[ 0, "desc" ]],/*默认第一列倒序*/        
        columnDefs: [
            {
                targets: 7,
                render: function (data, type, row, meta) {
                    var context =
                    {
                        func: [
                            {"name": "修改", "fn": "info(\'" + data.natrualkey + "\')", "type": "primary"},
                            {"name": "删除", "fn": "del(\'" + data.natrualkey + "\')", "type": "danger"}
                        ]
                    };
                    var html = template(context);
                    return html;
                }
            }

        ],
        "language": {
            "lengthMenu": "_MENU_ 条记录每页",
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
            $("#mytool").append('<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" >ADD</button>');
        }

    });

    $("#save").click(save);
});
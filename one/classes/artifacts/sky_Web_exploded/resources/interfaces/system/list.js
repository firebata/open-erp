/**
 * Created by zhangjh on 2015/6/9.
 */
var table;
$(function () {

    var modalID = "myModal";

    var searchUrl = "search";

    if (typeof(clientModalID) != "undefined") {
        modalID=clientModalID;
    }

    if (typeof(clientSearchUrl) != "undefined") {
        searchUrl=clientSearchUrl;
    }

    console.log("modalID:"+modalID);
    $('#start_date').datetimepicker();

    var tpl = $("#tpl").html();
    //预编译模板
    var template = Handlebars.compile(tpl);
    var indexOpreation =columnsName.length-1;

    table = $('#example').DataTable({
        ajax: {
            url:searchUrl
        },
        serverSide: true,
        columns:columnsName,
        order: [[ 0, "desc" ]],/*默认第一列倒序*/
        columnDefs: [
            {
                targets: indexOpreation,
                render: function (data, type, row, meta) {
                    var context =
                    {
                        func: [
                            {"name": "更新", "fn": "info(\'" + data.natrualkey + "\')", "type": "primary"},
                            {"name": "删除", "fn": "del(\'" + data.natrualkey + "\')", "type": "danger"}
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
            $("#mytool").append('<button type="button" id="addBtnAddListPage" class="btn btn-primary" data-toggle="modal" data-target="#'+modalID+'">新增</button>');
        }

    });

});
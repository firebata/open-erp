/**
 * Created by zhangjh on 2015/7/21.
 */
(function () {
    "use strict";
    var _delURL = path + "/development/bom/del/";
    $.extend({
        editBom: editBom,
        delBom: delBom
    });

    /**
     * 列表展示内容
     * @returns {*[]}
     */
    var columnsName = [
        {"data": null},
        {"data": "projectName"},
        {"data": "name"},
        {"data": "mainColor"},
        {"data": "sexId"},
        {"data": "offerAmount"},
        {"data": "remark"},
        {"data": null}
    ];
    //var mHtml = '<div class="dataTables_length col-xs-4" id="projectId_example_length"><label>子项目编号&nbsp;    <input name="projectId" id="projectId" class="form-control input-sm" placeholder="" aria-controls="example"></label></div>';

    var mHtml = '<div class="dataTables_length col-xs-2 col-xs-offset-10" id="prodinst_example_length"><label><button type="button" class="btn btn-info btn-sm" onclick="javascript:_mergeList(\'download_productinstruction\')">生产指示单</button></label></div>';
    var table;
    $(function () {

        var tpl = $("#tpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        var indexOpreation = columnsName.length - 1;
        table = $('#example').DataTable({
            ajax: {
                url: "search",
                data: setPersonalParam
            },
            serverSide: true,
            columns: columnsName,
            order: [[1, "desc"]], /*默认第一列倒序*/
            fnDrawCallback: reloadDetailSelectData,
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
                                {"name": "更新", "fn": "$.editBom(\'" + data.natrualkey + "\')", "type": "primary"},
                                {"name": "删除", "fn": "$.delBom(\'" + data.natrualkey + "\')", "type": "danger"}
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

    var projectId = "-1";

    function editBom(_bomId) {
        window.location.href = "add/" + _bomId;
    }

   function delBom(_bomId) {
        var $talbe = $('#example').DataTable();
        var _url = _delURL + _bomId;
        var _msg = "删除BOM会级联删除所属子项目中的颜色信息，确定删除吗?"
        $.delTableRecord(_bomId, _url, $talbe, _msg);
    }


    var setPersonalParam = function (d) {
        d.projectId = projectId = $("#projectId").val();
    }


    var reloadTable = function () {
        //触发dt的重新加载数据的方法
        table.ajax.reload();
    }

    var addSelectChangeListner = function () {
        $("#projectId").change(reloadTable);
    }


    //第一次初始化下拉列表 & 添加下拉列表监听事件
    var reloadDetailSelectData = function () {
        addSelectChangeListner();
        //$.sendRestFulAjax(path + "/system/baseinfo/project_select", null, 'GET', 'json', initSelect);
    }
    $.editBom = editBom;
    $.delBom = delBom;


}());
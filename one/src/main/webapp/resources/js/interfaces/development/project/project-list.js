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
    var columnsName = function () {
        var columnsName = [
            {"data": null},
            {"data": "name"},
            {"data": "customerId"},
            {"data": "areaId"},
            {"data": "seriesId"},
            {"data": "sampleDelivery"},
            {"data": "creater"},
            {"data": "createTime"},
            {"data": "remark"},
            {"data": "updateTime"},
            {"data": null}
        ];
        return columnsName;
    }


    var mHtml = ' <div class="dataTables_length col-xs-3" id="yearCode_example_length"><select name="yearCode" placeholder="选择" id="yearCode" aria-controls="example" class="form-control input-sm placeholder"></select></div><div class="dataTables_length col-xs-3" id="customerId_example_length"><select name="customerId" id="customerId" aria-controls="example" class="form-control input-sm"></select></div><div class="dataTables_length col-xs-3" id="areaId_example_length"><select name="areaId" id="areaId" aria-controls="example" class="form-control input-sm"></select></div><div class="dataTables_length col-xs-3   " id="seriesId_example_length"><select name="seriesId" id="seriesId" aria-controls="example" class="form-control input-sm"></select></div>';
    var table;

    $(function () {
        var tpl = $("#tpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        var indexOpreation = columnsName().length - 1;
        table = $('#example').DataTable({
            ajax: {
                url: "search",
                data: setPersonalParam
            },
            serverSide: true,
            columns: columnsName(),
            order: [[columnsName().length - 2, "desc"]], /*默认第一列倒序*/
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
                                {"name": "更新", "fn": "$.editProject(\'" + data.natrualkey + "\')", "type": "primary"},
                                {"name": "删除", "fn": "del(\'" + data.natrualkey + "\')", "type": "danger"}
                            ]
                        };
                        var html = template(context);
                        return html;
                    }
                }
            ],
            language: {
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
            dom: "<'row'<'col-xs-1'l><'#mytool.col-xs-9'><'col-xs-2'f>r>" +
            "t" +
            "<'row'<'col-xs-6'i><'col-xs-6'p>>",
            initComplete: function () {
                $("#mytool").append(mHtml);
            }

        });


    });

    var yearCode = "-1";
    var customerId = "-1";
    var areaId = "-1";
    var seriesId = "-1";


    var editProject = function (_projectId) {
        window.location.href = "add/" + _projectId;
    }

    var setPersonalParam = function (d) {
        d.yearCode = yearCode = $("#yearCode").val();
        d.customerId = customerId = $("#customerId").val();
        d.areaId = areaId = $("#areaId").val();
        d.seriesId = seriesId = $("#seriesId").val();
    }

    var reloadTable = function () {
        //触发dt的重新加载数据的方法
        table.ajax.reload();
    }

    var addSelectChangeListner = function () {
        $("#yearCode").change(reloadTable);
        $("#customerId").change(reloadTable);
        $("#areaId").change(reloadTable);
        $("#seriesId").change(reloadTable);
    }


    //第一次初始化下拉列表 & 添加下拉列表监听事件
    var reloadDetailSelectData = function () {
        $.sendRestFulAjax(project_selectURL, null, 'GET', 'json', initSelect);
    }


    var initSelect = function (_data) {
        addSelectChangeListner();
        var data = _data;
        //年份
        var yearCodeItems = data["yearItems"];
        $("#yearCode").empty();
        $("<option></option>").val('').text("...请选择年份...").appendTo($("#yearCode"));
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#yearCode"));
        });
        $("#yearCode").val(yearCode == undefined ? '' : yearCode);

        //客户
        var customerItems = data["customerItems"];
        $("#customerId").empty();
        $("<option></option>").val('').text("...请选择客户...").appendTo($("#customerId"));
        $.each(customerItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#customerId"));
        });
        $("#customerId").val(customerId == undefined ? '' : customerId);

        //区域
        var areaItems = data["areaItems"];
        $("#areaId").empty();
        $("<option></option>").val('').text("...请选择区域...").appendTo($("#areaId"));
        $.each(areaItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#areaId"));
        });
        $("#areaId").val(areaId == undefined ? '' : areaId);

        //系列
        var seriesItems = data["seriesItems"];
        $("#seriesId").empty();
        $("<option></option>").val('').text("...请选择系列...").appendTo($("#seriesId"));
        $.each(seriesItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#seriesId"));
        });
        $("#seriesId").val(seriesId == undefined ? '' : seriesId);
    }
    $.editProject = editProject;
}());
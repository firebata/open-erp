(function ($) {
    var table;
    var spId = "";

    $.extend({
        infoSp: info,
        delSp: del
    });

    /**
     * 查询信息
     * @param _spId
     */
    function info(_spId) {
        //先加载下拉列表，再加载信息
        locadSelect(function () {
            $.sendRestFulAjax("spinfo/" + _spId, null, 'GET', 'json', _doSuccess_info);
            spId = _spId;
        });

    }

    /**
     * 查询信息成功后，相关操作
     * @param _data
     * @private
     */
    var _doSuccess_info = function (_data) {
        var data = _data;

        Object.keys(data).map(function (key) {
            $('#' + key).val(data[key]);
        });
        $("#myModal").modal("show");

//        var myTemplate = Handlebars.compile($("#info-template").html());
//        $('#defaultForm').html(myTemplate(data));
    }

    /**
     * 删除信息
     * @param _spId
     */
    function del(_spId) {
        spId = _spId;
        bootbox.confirm("确定删除？", doDel);
    }

    /**
     *
     * @param result
     */
    var doDel = function (result) {
        if (result)  $.sendRestFulAjax("del/" + spId, null, 'DELETE', 'json', _doSuccess_del);
    }
    /**
     * 删除信息后的处理
     * @private
     */
    var _doSuccess_del = function () {
        table.ajax.reload( null, false);
    }

    /**
     * Created by zhangjh on 2015/5/27.
     */
    var save = function () {
        //执行表单监听
        $('#defaultForm').bootstrapValidator('validate');
    }

    /**
     * 修改数据
     * @param name
     */
    function edit(_url, _data, _type, _dataType) {
        var data = _data + "&spId=" + spId;
        $.sendRestFulAjax(_url, data, _type, _dataType, _doSuccess_edit);
    }

    function _doSuccess_edit(data) {
        //遮罩层的数量
        var length = $(".modal-backdrop").length;
        for (var index = 0; index < length; index++) {
            $("#myModal").modal('hide');//移除模态框遮罩层
        }
        //$(".modal-backdrop").remove();//移除遮罩层
        //$('<div class="modal-backdrop"></div>').appendTo(document.body);
        table.ajax.reload( null, false);
        //console.log(data.code);
        spId = ""; //将修改项的spid置为空
    }


    $('#resetBtn').click(function () {
        $('#defaultForm').data('bootstrapValidator').resetForm(true);
    });


    function locadSelect(callback) {

        $.sendRestFulAjax($.basepath() + '/system/baseinfo/select/materialTypeItems', null, 'GET', 'json', function (_data) {
            $("#type").empty();
            $("<option></option>").val('').text("请选择...").appendTo($("#type"));
            $.each(_data, function (key, value) {
                $("<option></option>")
                    .val(value["natrualkey"])
                    .text(value["name"])
                    .appendTo($("#type"));
            });
            callback();
        });


    }


    $(document).ready(function () {

            //modal显示式，重置Form
            $('#myModal').on('shown.bs.modal', function (e) {
                //新增时,加载下拉列表
                if (spId == '') {
                    $('#defaultForm').data('bootstrapValidator').resetForm(true);

                    locadSelect(function () {

                    });
                }

            });


            //关闭model则，将pantoneId置为空
            $('#myModal').on('hidden.bs.modal', function (e) {
                spId = '';
            });


            //启动表单校验监听
            $('#defaultForm').bootstrapValidator({
                    //live: 'disabled',
                    message: 'This value is not valid',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        name: {
                            validators: {
                                notEmpty: {
                                    message: '供应商名称为必填项'
                                }
                            }
                        },
                        type: {
                            validators: {
                                notEmpty: {
                                    message: '供应商类型为必填项'
                                }
                            }
                        }

                    }
                }
            );
        })
        .on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            // Use Ajax to submit form data
            var url = $form.attr('action');
            var type = "POST"
            if (spId == "") {
                url = "new";
            }
            var data = $form.serialize();
            edit(url, data, type, "");

        });


    $(function () {

        var tpl = $("#spInfotpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);

        table = $('#example').DataTable({
            ajax: {
                url: "search"
            },
            createdRow: function ( row, data, index ) {
                /* 设置表格中的内容居中 */
                $('td', row).attr("class","text-center");
            },
            serverSide: true,
            columns: [
                {"data": "name"},
                {"data": "type"},
                {"data": "tel"},
                {"data": "address"},
                {"data": "remark"},
                {"data": "updateTime"},
                {"data": null}
            ],
            order: [[5, "desc"]], /*默认第一列倒序*/
            columnDefs: [
                {
                    targets: 6,
                    render: function (data, type, row, meta) {
                        var context =
                        {
                            func: [
                                {"name": "更新", "fn": "$.infoSp(\'" + data.spId + "\')", "type": "primary"},
                                {"name": "删除", "fn": "$.delSp(\'" + data.spId + "\')", "type": "danger"}
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
                $("#mytool").append('<button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal" >新增</button>');
            }

        });

        $("#save").click(save);

    });


}(jQuery));
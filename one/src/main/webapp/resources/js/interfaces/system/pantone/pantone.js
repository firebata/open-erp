(function () {
    var table;
    var pantoneId = "";
    $.extend({
        infoPantone: info,
        delPantone: del
    });


    /**
     * 查询信息
     * @param _pantoneId
     */
    function info(_pantoneId) {
        loadPantoneTypeSelect(_pantoneId, function () {
            initFormField(_pantoneId);
        });
    }

    function initFormField(_pantoneId) {
        $.sendRestFulAjax("info/" + _pantoneId, null, 'GET', 'json', _doSuccess_info);
    }

    function initialPantoneType(data) {
        var $kind = $("#kind");
        $kind.empty();
        $("<option></option>").val('').text("请选择...").appendTo($kind);
        $.each(data, function (key, value) {
            $("<option></option>").val(key).text(value).appendTo($kind);
        });
    }

    function loadPantoneTypeSelect(_pantoneId, _callback) {
        $.sendRestFulAjax("pantone_type/", null, 'GET', 'json', function (data) {
            initialPantoneType(data);
            _callback();
            pantoneId = _pantoneId;
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
            $('#defaultForm input').filter(function () {
                return key == this.name;
            }).val(data[key]);
        });

        $("#myModal").modal("show");
//        var myTemplate = Handlebars.compile($("#info-template").html());
//        $('#defaultForm').html(myTemplate(data));
    }

    /**
     * 删除信息
     * @param _pantoneId
     */
    function del(_pantoneId) {
        pantoneId = _pantoneId;
        bootbox.confirm("确定删除？", doDel);
    }

    /**
     *
     * @param result
     */
    var doDel = function (result) {
        if (result)  $.sendRestFulAjax("del/" + pantoneId, null, 'DELETE', 'json', _doSuccess_del);
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
        var data = _data + "&pantoneId=" + pantoneId;
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
        console.log(data.code);
        pantoneId = ""; //将修改项的spid置为空
    }


    $('#resetBtn').click(function () {
        $('#defaultForm').data('bootstrapValidator').resetForm(true);
    });


    $(document).ready(function () {
            //modal显示式，重置Form
            $('#myModal').on('shown.bs.modal', function (e) {
                if (pantoneId == '') {
                    $('#defaultForm').data('bootstrapValidator').resetForm(true);
                    loadPantoneTypeSelect(pantoneId, function () {
                       
                    });
                }
            });

            //关闭model则，将pantoneId置为空
            $('#myModal').on('hidden.bs.modal', function (e) {
                pantoneId = '';
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
                        colorNo: {
                            validators: {
                                notEmpty: {
                                    message: '色号为必填项'
                                }
                            }
                        },
                        enName: {
                            validators: {
                                notEmpty: {
                                    message: '英文名称为必填项'
                                }
                            }
                        },
                        zhName: {
                            validators: {
                                notEmpty: {
                                    message: '中文名称为必填项'
                                }
                            }
                        },
                        pageNo: {
                            validators: {
                                notEmpty: {
                                    message: '页码为必填项'
                                }
                            }
                        },
                        codeColor: {
                            validators: {
                                notEmpty: {
                                    message: '颜色为必填项'
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
            if (pantoneId == "") {
                url = "new";
                type = "POST"
            }
            var data = $form.serialize();
            edit(url, data, type, "");
        });


    $(function () {

        var tpl = $("#tplPantone").html();
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
                {"data": "colorNo"},
                {"data": "enName"},
                {"data": "zhName"},
                {"data": "pageNo"},
                {"data": "updateTime"},
                {"data": null},
                {"data": null}
            ],
            order: [[4, "desc"]], /*默认第一列倒序*/
            "createdRow": function (row, data, dataIndex) {
                $('td', row).eq(5).attr('bgcolor', data.codeColor);
            },
            columnDefs: [
                {
                    targets: 5,
                    render: function (data, type, row, meta) {
                        return "";
                    }
                },
                {
                    targets:6,
                    render: function (data, type, row, meta) {
                        var context =
                        {
                            func: [
                                {"name": "更新", "fn": "$.infoPantone(\'" + data.pantoneId + "\')", "type": "primary"},
                                {"name": "删除", "fn": "$.delPantone(\'" + data.pantoneId + "\')", "type": "danger"}
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

}());
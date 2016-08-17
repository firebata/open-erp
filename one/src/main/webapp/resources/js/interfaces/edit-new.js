/**
 *
 */
(function () {

    "use strict";
    var natrualkey = "";

    /**
     * 查询信息
     * @param _natrualkey
     */
    var info = function (_natrualkey) {
        $.sendRestFulAjax("info/" + _natrualkey, null, 'GET', 'json', _doSuccess_info);
        natrualkey = _natrualkey;
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

        //初始化下拉框
        if ($.isFunction(window.initSelect)) {
            initSelect(data);
        }

        $("#myModal").modal("show");
//        var myTemplate = Handlebars.compile($("#info-template").html());
//        $('#defaultForm').html(myTemplate(data));
    }

    /**
     * 删除信息
     * @param _natrualkey
     */
    var del = function (_natrualkey) {
        natrualkey = _natrualkey;
        bootbox.confirm("确定删除？", doDel);
    }
    /**
     *
     * @param result
     */
    var doDel = function (result) {
        if (result)  $.sendRestFulAjax("del/" + natrualkey, null, 'DELETE', 'json', _doSuccess_del);
        natrualkey = ""; //将修改项的natrualkey置为空
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
        var data = _data + "&natrualkey=" + natrualkey;
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
        natrualkey = ""; //将修改项的natrualkey置为空
    }

    /**
     * 重置表单
     */
    $('#resetBtn').click(function () {
        //重置添加的表单
        $('#defaultForm').data('bootstrapValidator').resetForm(true);
        //重置select2
        //初始化下拉框
        if ($.isFunction(window.resetSelect)) {
            window.resetSelect();
        }
        //清空remark
        $('#remark').val("");

    });


    $(document).ready(function () {

        $("#save").click(save);

        //modal显示式，重置Form
        $('#myModal').on('shown.bs.modal', function (e) {
            if (natrualkey == '') {
                $('#defaultForm').data('bootstrapValidator').resetForm(true);
            }
        });
        //modal关闭时，重置Form
        $('#myModal').on('hidden.bs.modal', function (e) {
            $('#defaultForm').data('bootstrapValidator').resetForm(true);
            natrualkey = ""; //将修改项的natrualkey置为空
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
            fields: fieldsDesc()
        }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
            // Prevent form submission
            e.preventDefault();

            // Get the form instance
            var $form = $(e.target);

            // Get the BootstrapValidator instance
            var bv = $form.data('bootstrapValidator');

            // Use Ajax to submit form data
            var url = $form.attr('action');
            var type = "POST"
            if (natrualkey == "") {
                url = "new";
                type = "POST"
            }
            var data = $form.serialize();
            edit(url, data, type, "");

        });
    })
}());

//var _natrualkey="";
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
    natrualkey = ""; //将修改项的spid置为空
}


$('#resetBtn').click(function () {
    $('#defaultForm').data('bootstrapValidator').resetForm(true);
});


$(document).ready(function () {

        //modal显示式，重置Form
        $('#myModal').on('shown.bs.modal', function (e) {
            if (natrualkey == '') {
                $('#defaultForm').data('bootstrapValidator').resetForm(true);
            }
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
                                message: '客户名称为必填项'
                            }
                        }
                    },
                    fullName: {
                        validators: {
                            notEmpty: {
                                message: '客户全称为必填项'
                            }
                        }
                    },
                    email: {
                        validators: {
                            notEmpty: {
                                message: '邮箱为必填项'
                            }
                        }
                    },
                    address: {
                        validators: {
                            notEmpty: {
                                message: '地址为必填项'
                            }
                        }
                    }
                    ,
                    contact: {
                        validators: {
                            notEmpty: {
                                message: '联系人为必填项'
                            }
                        }
                    },
                    tel: {
                        validators: {
                            notEmpty: {
                                message: '联系电话为必填项'
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
        if (natrualkey == "") {
            url = "new";
            type = "POST"
        }
        var data = $form.serialize();
        edit(url, data, type, "");

    });


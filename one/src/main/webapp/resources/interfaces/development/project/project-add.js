/**
 * Created by zhangjh on 2015/7/14.
 */
(function () {
    "use strict";
    var path = $.basepath();
    var project_selectURL = path + "/system/baseinfo/project_select";
    var infoURL = path + "/development/project/info/";
    var newURL = path + "/development/project/new";
    var editURL = path + "/development/project/edit";
    var listURL = path + "/development/project/list";
    var searchSecondURL = path + "/system/category/searchSecond/";
    var infoCategoryURL = path + "/development/project-category/infoCategory/";
    $(function () {
        reloadDetailSelectData();
        $("#projectForm").on("change", "select", cb);
    })

    //第一次初始化下拉列表
    var reloadDetailSelectData = function () {
        $.sendRestFulAjax(project_selectURL, null, 'GET', 'json', initSelectCallBack);
    }


    var initSelectCallBack = function (_data) {
        initSelect(_data);
        initOther();
    }

    var initOther = function () {
        var natrualkey = $("#natrualkey").val();
        if (natrualkey != '') {
            $.sendRestFulAjax(infoURL + natrualkey, null, 'GET', 'json', initFormFields);
        }

    }

    /**
     * 初始化表单
     * @param _data
     */
    var initFormFields = function (_data) {
        Object.keys(_data).map(function (key) {

            $('#projectForm input').filter(function () {
                return key == this.name;
            }).val(_data[key]);


            $("#" + key).val(_data[key]);
            if (key == 'categoryAid') {
                //
                //var categoryAid = _data[key];
                //initCategoryB(categoryAid);

                //var arr = _data[key].split(',');
                //$('#categoryBid').selectpicker('val', arr);
            }
            else {
                //下拉框
                //$("#" + key).val(_data[key]);
            }
        });

        initCategory();

    }

    var initCategory = function () {
        var natrualkey = $("#natrualkey").val();
        var url = infoCategoryURL + natrualkey;
        $.sendRestFulAjax(url, null, 'GET', 'json', initCategoryFields);
    }

    var initCategoryFields = function (_data) {
        console.info("_data:" + _data);
        if (_data === '' || _data.length === 0) return;
        var categoryAid = _data[0].categoryAid;
        $("#categoryAid").val(_data[0].categoryAid);

        initCategoryB(categoryAid, function () {
            selectCategoryB(_data);
        });

    }

    var selectCategoryB = function (_data) {
        var categoryBids = [];
        for (var index = 0; index < _data.length; index++) {
            categoryBids.push(_data[index].categoryBid);
        }
        $('#categoryBid').selectpicker("val", categoryBids)
    }


    var initSelect = function (_data) {
        var data = _data;
        //年份
        var yearCodeItems = data["yearItems"];
        $("#yearCode").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#yearCode"));
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#yearCode"));
        });

        //客户
        var yearCodeItems = data["customerItems"];
        $("#customerId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#customerId"));
        $.each(yearCodeItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#customerId"));
        });

        //区域
        var areaItems = data["areaItems"];
        $("#areaId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#areaId"));
        $.each(areaItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#areaId"));
        });

        //系列
        var seriesItems = data["seriesItems"];
        $("#seriesId").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#seriesId"));
        $.each(seriesItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#seriesId"));
        });

        //一级品类
        var categoryAItems = data["categoryAItems"];
        $("#categoryAid").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#categoryAid"));
        $.each(categoryAItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#categoryAid"));
        });


    }

    /**
     *
     */
    var saveProject = function () {
        //执行表单验证
        $('#projectForm').bootstrapValidator('validate');
    }


    var doSaveAction = function () {
        //var $tag_obj = $('#mainColorNames').data('tag');
        project.categoryAid = '';
        project.categoryBid = '';
        //project.collectionNumber = '';

        var formDataStr = $("#projectForm").serialize();
        //var formDataJson = $.strToJson(formDataStr);

        var natrualkey = $("#natrualkey").val();
        var url;
        if (natrualkey === '' || natrualkey === 'null') {
            url = newURL;
        } else {
            url = editURL;
        }

        var categoryBid = $("#categoryBid").val();
        $.sendRestFulAjax(url, formDataStr + "&categoryBid=" + categoryBid, 'POST', 'json', function () {
            window.location.href = listURL;
        });
    }


    /**
     * 新增/修改校验字段描述
     * @returns {{name: {validators: {notEmpty: {message: string}}}, customerId: {validators: {notEmpty: {message: string}}}}}
     */
    var fieldsDesc =
    {
        yearCode: {
            validators: {
                notEmpty: {
                    message: '年份为必填项'
                }
            }
        },
        customerId: {
            validators: {
                notEmpty: {
                    message: '客户为必填项'
                }
            }
        },
        areaId: {
            validators: {
                notEmpty: {
                    message: '区域为必填项'
                }
            }
        },
        seriesId: {
            validators: {
                notEmpty: {
                    message: '区域为必填项'
                }
            }
        },
        categoryAid: {
            validators: {
                notEmpty: {
                    message: '品类一级名称为必填项'
                }
            }
        },
        categoryBid: {
            validators: {
                notEmpty: {
                    message: '品类二级名称为必填项'
                }
            }
        }
    }


    //启动表单校验监听
    $('#projectForm').bootstrapValidator({
        //live: 'disabled',
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: fieldsDesc
    }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
        doSaveAction();
    });


    /**
     * 回调函数
     */
    function cb() {
        if ($(this).attr('id') === 'categoryAid') {
            var categoryAid = $(this).val();
            initCategoryB(categoryAid, function () {

            });
        }
    }

    /**
     * 根据一级品类id的改变，查询二级品类id
     * @param categoryAid
     */
    function initCategoryB(categoryAid, callback) {

        $.sendRestFulAjax(searchSecondURL + categoryAid, null, 'GET', 'json', function (data) {

            $('#categoryBid').empty();

            //二级品类
            $.each(data, function (index, value) {
                $("<option></option>")
                    .val(value.natrualkey)
                    .text(value.name)
                    .appendTo($("#categoryBid"));
            });

            $('#categoryBid').selectpicker({noneSelectedText: '请选择...'});
            $('#categoryBid').selectpicker('refresh');
            callback();
        });


    }


    var project = {
        projectId: "",
        projectName: "",
        categoryAid: "",
        categoryBid: "",
        collectionNumber: "",
        yearCode: "",
        customerId: "",
        areaId: "",
        seriesId: "",
        sampleDelivery: "",//推销样交期
        isNeedSwatch: "",
        isNeedPreOffer: "",
        needPreOfferDate: "",
        sketchReceivedDate: "",
        fileLocation: []
    }

    $.saveProject = saveProject;


}());
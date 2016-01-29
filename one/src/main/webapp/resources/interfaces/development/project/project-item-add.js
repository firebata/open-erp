/**
 * Created by zhangjh on 2015/7/14.
 */
(function () {

    "use strict";
    var path = $.basepath();
    var project_selectURL = path + "/system/baseinfo/project_select";
    var project_infoURL = path + "/development/project_item/info/";
    var project_listURL = path + "/development/project_item/list";
    var project_newURL = path + "/development/project_item/new";
    var project_editURL = path + "/development/project_item/edit";

    $(function () {
        reloadDetailSelectData();
        initTags();
    })


    //第一次初始化下拉列表
    var reloadDetailSelectData = function () {
        $.sendRestFulAjax(project_selectURL, null, 'GET', 'json', initSelectCallBack);
    }

    /**
     * 初始化标签
     */
    var initTags = function () {
        var tag_input = $('#mainColorNames');
        try {
            tag_input.tag(
                {
                    placeholder: tag_input.attr('placeholder'),
                    //enable typeahead by specifying the source array
                    source: ace.vars['US_STATES']//defined in ace.js >> ace.enable_search_ahead
                    /**
                     //or fetch data from database, fetch those that match "query"
                     source: function(query, process) {
						  $.ajax({url: 'remote_source.php?q='+encodeURIComponent(query)})
						  .done(function(result_items){
							process(result_items);
						  });
						}
                     */
                }
            )

            //programmatically add a new
            //var $tag_obj = $('#mainColorNames').data('tag');
            //$tag_obj.add('Programmatically Added');
        }
        catch (e) {
            //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
            tag_input.after('<textarea id="' + tag_input.attr('id') + '" name="' + tag_input.attr('name') + '" rows="3">' + tag_input.val() + '</textarea>').remove();
            //$('#form-field-tags').autosize({append: "\n"});
        }

    }

    var initSelectCallBack = function (_data) {
        initSelect(_data);
        initOther();
    }

    var initOther = function () {
        var natrualkey = $("#natrualkey").val();
        if (natrualkey != '') {
            $.sendRestFulAjax(project_infoURL + natrualkey, null, 'GET', 'json', initFormFields);
        }

    }

    /**
     * 初始化表单
     * @param _data
     */
    var initFormFields = function (_data) {

        console.info("the info of project:" + _data);

        Object.keys(_data).map(function (key) {

            $('#projectForm input').filter(function () {
                return key == this.name;
            }).val(_data[key]);


            if (key == 'sexIds') {
                if (null != _data[key]) {
                    var arr = _data[key].split(',');
                    $('#sexIds').selectpicker("val", arr);
                }
            }
            else {

                //下拉框
                $("#" + key).val(_data[key]);

                if (key != 'fileLocation' && key != 'collectionNumber' && key != 'natrualkey') {
                    $("#" + key).attr('disabled', 'disabled');
                }
            }
        });
        //初始化性别属性，再初始化性别属性和颜色
        $.initSexColors(_data["sexColors"]);

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

        //性别属性
        var sexItems = data["sexItems"];
        $("#sexIds").empty();

        $.each(sexItems, function (i, item) {
            $("<option></option>").val(item["natrualkey"]).text(item["name"]).appendTo($("#sexIds"));
        });

        $('#sexIds').selectpicker({noneSelectedText: '请选择...'});
        $('#sexIds').selectpicker('refresh');

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

        //二级品类
        var categoryBItems = data["categoryBItems"];
        $("#categoryBid").empty();
        $("<option></option>").val('').text("请选择...").appendTo($("#categoryBid"));
        $.each(categoryBItems, function (i, item) {
            $("<option></option>")
                .val(item["natrualkey"])
                .text(item["name"])
                .appendTo($("#categoryBid"));
        });


    }

    /**
     *
     */
    var saveProject = function () {
        //执行表单验证
        $('#projectForm').bootstrapValidator('validate');
    }

    $(document).ready(function () {
        multiselect();
    });

    var multiselect = function () {
        //$('#sexIds').multiselect({
        //    onDropdownHide: function(event) {
        //        alert('Dropdown closed.');
        //    }
        //});
    }


    var doSaveAction = function () {
        //var $tag_obj = $('#mainColorNames').data('tag');
        //var tagValues = $tag_obj.values.join(',');
        project.categoryAid = '';
        project.categoryBid = '';
        project.collectionNumber = '';

        //var formDataStr = $("#projectForm").serialize();
        //var formDataJson = $.strToJson(formDataStr);

        var natrualkey = $("#natrualkey").val();
        var url;
        if (natrualkey === '' || natrualkey === 'null') {
            url = project_newURL;
        } else {
            url = project_editURL;
        }

        //var sexIds = $("#sexIds").val();


        var projectItemInfo = {};
        projectItemInfo.natrualkey = natrualkey;
        var sexColors = [];

        var sexIds = $("[id^=sexIdChild]").arrayVal();

        for (var index = 0, len = sexIds.length; index < len; index++) {
            var sexColor = {};
            sexColor.sexIdChild = sexIds[index];
            sexColor.sexName = $("#sexName" + sexIds[index]).val();
            sexColor.projectId = natrualkey;
            var $tag_obj = $('#mainColorNames' + sexIds[index]).data('tag');
            var tagValues = $tag_obj.values.join(',');
            sexColor.mainColorNames = tagValues;
            sexColors.push(sexColor);
        }
        projectItemInfo.sexColors = sexColors;
        var sexIdsSelect = $("#sexIds").val();
        projectItemInfo.sexIds = sexIdsSelect.join(",");

        $.sendJsonAjax(url, projectItemInfo, function () {
            window.location.href = project_listURL;
        })

    }


    /**
     * 新增/修改校验字段描述
     * @returns {{name: {validators: {notEmpty: {message: string}}}, customerId: {validators: {notEmpty: {message: string}}}}}
     */
    var fieldsDesc = function () {
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
            //sampleDelivery: {
            //    validators: {
            //        notEmpty: {
            //            message: '样品交付日期为必填项'
            //        }
            //    }
            //},
            //needPreOfferDate: {
            //    validators: {
            //        notEmpty: {
            //            message: '预报价日期为必填项'
            //        }
            //    }
            //},
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
            },
            sexIds: {
                validators: {
                    notEmpty: {
                        message: '性别属性为必填项'
                    }
                }
            },
            collectionNumber: {
                validators: {
                    notEmpty: {
                        message: '款式数量为必填项'
                    }
                }
            },
            mainColorNames: {
                validators: {
                    notEmpty: {
                        message: '性别属性为必填项'
                    }
                }
            }
            //,
            //sketchReceivedDate: {
            //    validators: {
            //        notEmpty: {
            //            message: '产品描述收到时间为必填项'
            //        }
            //    }
            //}
        }
        return fieldsDesc;
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
        fields: fieldsDesc()
    }).on('success.form.bv', function (e) { //表单校验成功，ajax提交数据
        doSaveAction();
    });


    //$("#fileLocation").fileinput({
    //    uploadUrl: path + "/development/project/fileUpload",
    //    uploadAsync: true,
    //    minFileCount: 1,
    //    maxFileCount: 5
    //});

    var project = {
        projectId: "",
        projectName: "",
        categoryAid: "",
        categoryBid: "",
        collectionNumber: "",
        sexIds: [],//性别属性
        mainColors: [],//色组
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
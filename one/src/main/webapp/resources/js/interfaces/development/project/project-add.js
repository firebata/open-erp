/**
 * 项目新增/修改处理模块
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
        var qrAreaURL = path + "/system/area/select2";
        var isSubmitAction = "N";//是否点击的
        var uploadFileInfos = [];
        var $fileInput = $("#fileLocation");
        var $fileListLi = $("#filesList");
        var fileUploadURL = path + "/files/upload";


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

        //function initArea() {
        //    var customerId = _data[0].customerId;
        //    //初始化区域id
        //    initAreaId(customerId, function () {
        //        $("#areaId").val(_data["areaId"]);
        //    });
        //}

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

            });

            initCategory();

            $.loadFileInput($fileInput, $fileListLi, _data["fileinfosMap"], fileUploadURL);

            //initArea(_data);

        }

        var initCategory = function () {
            var natrualkey = $("#natrualkey").val();
            var url = infoCategoryURL + natrualkey;
            $.sendRestFulAjax(url, null, 'GET', 'json', initCategoryFields);
        }

        var initCategoryFields = function (_data) {

            selectCategoryA(_data);

            /*
             //console.info("_data:" + _data);
             if (_data === '' || _data.length === 0) return;
             var categoryAid = _data[0].categoryAid;
             $("#categoryAid").val(_data[0].categoryAid);
             //!*初始化二级品类
             initCategoryB(categoryAid, function () {
             selectCategoryB(_data);
             });
             */


        }

        /**
         * 一级品类
         * @param _data
         */
        var selectCategoryA = function (_data) {
            var categoryAids = [];
            var categorys = []

            for (var index = 0; index < _data.length; index++) {
                if (categoryAids.length == 0) {
                    categoryAids.push(_data[index].categoryAid);
                    var category = {};
                    category.categoryAid = _data[index].categoryAid;
                    categorys.push(category);
                } else {
                    var isExited = false;
                    for (var index1 = 0; index1 < categoryAids.length; index1++) {
                        if (categoryAids[index1] == _data[index].categoryAid) {
                            isExited = true;
                            break;
                        }
                    }

                    if (!isExited) {
                        categoryAids.push(_data[index].categoryAid);
                        var category = {};
                        category.categoryAid = _data[index].categoryAid;
                        categorys.push(category);
                    }
                }
            }

            for (var index = 0; index < categorys.length; index++) {
                if (categorys[index].categoryBids == null) {
                    categorys[index].categoryBids = [];
                }
                for (var index1 = 0; index1 < _data.length; index1++) {
                    if (_data[index1].categoryAid == categorys[index].categoryAid) {
                        if (!categorys[index].categoryBids.contains(_data[index1].categoryBid)) {
                            categorys[index].categoryBids.push(_data[index1].categoryBid);
                        }
                    }
                }
            }


            initCategorys(categorys, categoryAids)
        }


        //var selectCategoryB = function (_data) {
        //    var categoryBids = [];
        //    for (var index = 0; index < _data.length; index++) {
        //        categoryBids.push(_data[index].categoryBid);
        //    }
        //    $('#categoryBid').selectpicker("val", categoryBids)
        //}


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

            var natrualkey = $("#natrualkey").val();
            var url;
            if (natrualkey === '' || natrualkey === 'null') {
                url = newURL;
                toSave(url, natrualkey);
            } else {
                url = editURL;
                bootbox.confirm({
                    size: 'small',
                    message: "修改项目的品类信息，可能删除下属子项目和BOM,确定修改吗?",
                    callback: function (result) {
                        if (result) {
                            toSave(url, natrualkey);
                        }
                    }
                })
            }


        }


        function toSave(url, natrualkey) {
            var formDataStr = $("#projectForm").serialize();
            formDataStr = decodeURIComponent(formDataStr);
            var projectInfo = $.strToJson(formDataStr);
            projectInfo.fileInfos = uploadFileInfos;

            //项目品类信息
            var categoryAids = $("[id^=categoryAidChild]").arrayVal();
            var categoryInfos = [];
            for (var index = 0, len = categoryAids.length; index < len; index++) {
                var categoryBids = $("#categoryBid" + categoryAids[index]).val();
                for (var idx1 = 0, len1 = categoryBids.length; idx1 < len1; idx1++) {
                    var projectCategoryInfo = {};
                    projectCategoryInfo.categoryAid = categoryAids[index];
                    projectCategoryInfo.projectId = natrualkey;
                    projectCategoryInfo.categoryBid = categoryBids[idx1];
                    categoryInfos.push(projectCategoryInfo);
                }
            }

            projectInfo.categoryInfos = categoryInfos;

            $.sendJsonAjax(url, projectInfo, function () {
                //$.sendRestFulAjax(saveUrl,bominfo, 'POST', 'json', function () {
                window.location.href = listURL;
            })
        }

        /**
         *
         * @param categoryAid
         */
        function newCategoryDiv(categoryAid) {

            var data = {
                "category": [
                    {
                        "categoryDivId": "categoryDivId" + categoryAid,
                        "categoryAidChild": "categoryAidChild" + categoryAid,
                        "categoryAname": "categoryAname" + categoryAid,
                        "categoryBid": "categoryBid" + categoryAid
                    }
                ]
            };

            var luoxiaomei = $("#category-template").html();
            var myTemplate = Handlebars.compile(luoxiaomei);
            $("#categoryDivAll").append(myTemplate(data));


        }


        /**
         *
         * @param sexColors
         */
        function initCategorys(categorys, categoryAids) {

            //一级分类
            $('#categoryAid').selectpicker("val", categoryAids);

            createCategoryDiv(function (categoryA) {

                for (var index = 0, len = categorys.length; index < len; index++) {
                    if (categorys[index].categoryAid == categoryA.categoryAid) {
                        categoryA.categoryBids = categorys[index]["categoryBids"];
                    }
                }

                //*初始化二级品类
                initCategoryB(categoryA.categoryAid, function () {
                    initCategoryDivFields(categoryA);
                });


            });


        }


        function initCategoryDivFields(category) {
            if (null != category) {
                $("#categoryAidChild" + category["categoryAid"]).val(category["categoryAid"]);
                $("#categoryAname" + category["categoryAid"]).val(category["categoryAname"]);
                if (null != category["categoryBids"]) {
                    $('#categoryBid' + category["categoryAid"]).selectpicker("val", category["categoryBids"]);
                }

            }
        }

        function createCategoryDiv(_initCategory) {

            //已显示的品类模块
            var categoryAids = $("[id^=categoryAidChild]").arrayVal();


            //一级品类多选框
            var categoryAidsSelect = $("#categoryAid").val();

            if (categoryAids == null) {
                categoryAids = [];
            }

            if (categoryAidsSelect == null) {
                categoryAidsSelect = [];
            }

            var categoryAidsLen =  categoryAids.length;
            if(categoryAidsLen == 0 ){
                $( "[id^=categoryDivId]").remove();
            }else{
                // var $categoryAidsArr = $("[id^=categoryAidChild]");
                // for(var idx=0,len=$categoryAidsArr.length;idx<len;idx++){
                //     if($.isEmptyObject($categoryAidsArr.val())){
                //
                //     }
                // }
                //删除
                for (var index = 0, len = categoryAids.length; index < len; index++) {
                    var isHave = false;
                    var categoryAid = categoryAids[index];
                    if (!categoryAidsSelect.contains(categoryAid)) {
                        $("#categoryDivId" + categoryAid).remove();
                    }
                }
            }

            //新增
            for (var index = 0, len = categoryAidsSelect.length; index < len; index++) {
                var isHave = false;
                var categoryAid = categoryAidsSelect[index];
                if (!categoryAids.contains(categoryAid)) {
                    newCategoryDiv(categoryAid);
                    if ($.isFunction(_initCategory)) {
                        var categoryA = {};
                        categoryA.categoryAid = categoryAid;
                        var categoryAname = $("#categoryAid option[value='" + categoryAid + "']").text();
                        categoryA.categoryAname = categoryAname;
                        _initCategory(categoryA);
                    }
                }

            }
        }


        /**
         * 回调函数：监听下拉变化
         */
        function monitorSelectChange() {

            var _id = $(this).attr('id');
            if (_id === 'categoryAid') {
                var categoryAid = $(this).val();
                createCategoryDiv(function (categoryA) {
                    //*初始化二级品类
                    initCategoryB(categoryA.categoryAid, function () {
                        initCategoryDivFields(categoryA);
                    });
                });
            }
            else if (_id === 'customerId') {
                var customerId = $(this).val();
                initAreaId(customerId, function () {

                });
            }


        }

        /**
         *
         * @param customerId 客户id
         * @param callback 回调函数：为区域赋初始值
         */
        function initAreaId(customerId, callback) {
            $.sendRestFulAjax(qrAreaURL, {'keyword': customerId}, 'GET', 'json', function (_data) {
                var $areaId = $("#areaId");
                $areaId.empty();
                $("<option></option>").val('').text("...请选择...").appendTo($areaId);
                $.each(_data["items"], function (key, value) {
                    $("<option></option>").val(value["natrualkey"]).text(value["name"]).appendTo($areaId);
                });
                callback();
            });
        }


        function initCategoryA(categoryAItems) {
            $("#categoryAid").empty();

            $.each(categoryAItems, function (i, item) {
                $("<option></option>")
                    .val(item["natrualkey"])
                    .text(item["name"])
                    .appendTo($("#categoryAid"));
            });
            $('#categoryAid').selectpicker({noneSelectedText: '...请选择...'});
            $('#categoryAid').selectpicker('refresh');
        }


        /**
         * 根据一级品类id的改变，查询二级品类id
         * @param categoryAid
         * @param callback 为二级品类赋初始值
         */
        function initCategoryB(categoryAid, callback) {
            var $categoryBid = $('#categoryBid' + categoryAid);
            $.sendRestFulAjax(searchSecondURL + categoryAid, null, 'GET', 'json', function (data) {

                $categoryBid.empty();
                //二级品类
                $.each(data, function (index, value) {
                    $("<option></option>")
                        .val(value.natrualkey)
                        .text(value.name)
                        .appendTo($categoryBid);
                });
                $categoryBid.selectpicker({noneSelectedText: '...请选择...'});
                $categoryBid.selectpicker('refresh');
                callback();
            }, true);
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


        var initSelect = function (_data) {
            var data = _data;
            //年份
            var yearCodeItems = data["yearItems"];
            $("#yearCode").empty();
            $("<option></option>").val('').text("...请选择...").appendTo($("#yearCode"));
            $.each(yearCodeItems, function (i, item) {
                $("<option></option>")
                    .val(item["natrualkey"])
                    .text(item["name"])
                    .appendTo($("#yearCode"));
            });

            //客户
            var yearCodeItems = data["customerItems"];
            $("#customerId").empty();
            $("<option></option>").val('').text("...请选择...").appendTo($("#customerId"));
            $.each(yearCodeItems, function (i, item) {
                $("<option></option>")
                    .val(item["natrualkey"])
                    .text(item["name"])
                    .appendTo($("#customerId"));
            });

            //区域
            var areaItems = data["areaItems"];
            $("#areaId").empty();
            $("<option></option>").val('').text("...请选择...").appendTo($("#areaId"));
            $.each(areaItems, function (i, item) {
                $("<option></option>")
                    .val(item["natrualkey"])
                    .text(item["name"])
                    .appendTo($("#areaId"));
            });

            //系列
            var seriesItems = data["seriesItems"];
            $("#seriesId").empty();
            $("<option></option>").val('').text("...请选择...").appendTo($("#seriesId"));
            $.each(seriesItems, function (i, item) {
                $("<option></option>")
                    .val(item["natrualkey"])
                    .text(item["name"])
                    .appendTo($("#seriesId"));
            });

            //一级品类
            var categoryAItems = data["categoryAItems"];
            initCategoryA(categoryAItems)

        }


        function getIsSubmitAction() {
            return isSubmitAction;
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
            $fileInput.fileinput('upload');//批量提交
            var hasExtraData = $.isEmptyObject($fileInput.fileinput("getExtraData"));
            if (hasExtraData) {
                doSaveAction();
            }
        });


        $(function () {

            reloadDetailSelectData();

            $("#projectForm").on("change", "select", monitorSelectChange);

            $.fileInputAddListenr($fileListLi, $fileInput, uploadFileInfos, doSaveAction, getIsSubmitAction);
        })

        $.saveProject = saveProject;
    }()
);
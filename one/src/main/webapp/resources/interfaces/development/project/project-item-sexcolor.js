/**
 * Created by zhangjh on 2015/11/6.
 */
(function ($) {
    "use strict";
    $.extend({
        initSexColors: initSexColors
    });

    $(function () {

        //监听价格变动
        $("#projectForm").on("change", "#sexIds", function () {
            createSexColorDiv(initSexColor);
        });


    });

    /**
     *
     * @param sexColors
     */
    function initSexColors(sexColors) {
        createSexColorDiv();
        for (var index = 0, len = sexColors.length; index < len; index++) {
            initSexColor(sexColors[index]);
        }

    }


    /**
     * 初始化
     */
    function initSexColor(sexColor) {
        //初始化赋值
        Object.keys(sexColor).map(function (key) {
            //$("[name='" + key + "']").val(sexColor[key]);
            $("#" + key + sexColor["sexIdChild"]).val(sexColor[key]);

            //初始化色组:undefined/null/空字符串
            if (sexColor["mainColorNames"]) {
                var mainColors = sexColor["mainColorNames"].split(",");
                var $tag_obj = $('#mainColorNames' + sexColor["sexIdChild"]).data('tag');
                $.each(mainColors, function (n, value) {
                    $tag_obj.add(value);
                });
            }
        });

    }

    function createSexColorDiv(_initSexColor) {

        //已显示的性别颜色模块
        var sexIds = $("[id^=sexIdChild]").arrayVal();
        //性别属性多选择框
        var sexIdsSelect = $("#sexIds").val();
        if (sexIds == null) {
            sexIds = [];
        }
        if (sexIdsSelect == null) {
            sexIdsSelect = [];
        }

        //删除
        for (var index = 0, len = sexIds.length; index < len; index++) {
            var isHave = false;
            var sexId = sexIds[index];
            for (var index1 = 0, len1 = sexIdsSelect.length; index1 < len1; index1++) {
                if (sexIdsSelect[index1] === sexId) {
                    isHave = true;
                }
            }

            if (!isHave) {
                $("#sexColorDivId" + sexId).remove();
            }
        }

        //新增
        for (var index = 0, len = sexIdsSelect.length; index < len; index++) {
            var isHave = false;
            var sexId = sexIdsSelect[index];

            for (var index1 = 0, len1 = sexIds.length; index1 < len1; index1++) {
                if (sexIds[index1] === sexId) {
                    isHave = true;
                    break;
                }
            }

            if (!isHave) {

                newSexColorDiv(sexId);//创建性别属性和颜色DIV

                if ($.isFunction(_initSexColor)) {
                    var sexColor = {};
                    sexColor.sexIdChild = sexId;
                    var sexName = $("#sexIds option[value='" + sexId + "']").text();//从性别属性中找到性别属性di对应的name
                    sexColor.sexName = sexName;
                    _initSexColor(sexColor);
                }
            }
        }
    }

    function newSexColorDiv(sexId) {

        var data = {
            "sexcolor": [
                {
                    "sexColorDivId": "sexColorDivId" + sexId,
                    "sexIdChild": "sexIdChild" + sexId,
                    "sexName": "sexName" + sexId,
                    "mainColorNames": "mainColorNames" + sexId
                }
            ]
        };

        var luoxiaomei = $("#sexcolor-template").html();
        var myTemplate = Handlebars.compile(luoxiaomei);
        $("#sexColorDivAll").append(myTemplate(data));

        initTags(sexId);

    }


    /**
     * 初始化标签
     */
    var initTags = function (sexId) {
        var tag_input = $('#mainColorNames' + sexId);
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


}(jQuery));

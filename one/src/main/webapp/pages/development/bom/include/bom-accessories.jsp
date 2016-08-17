<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="accessoriesInfo">
    <h5 class="header smaller lighter blue">
        辅&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;料
        <span class="glyphicon glyphicon-plus-sign blue" alt="增加辅料" id="imgAddAccessories"></span>
    </h5>

    <div id="accessoriesItemInfo">


    </div>

</div>
<script id="accessories-template" type="text/x-handlebars-template">
    {{#each accessories}}
    <div id="{{accessoriesDivId}}" class="bom-info">
        <div class="bom-info form-group" id="{{accessoriesTitleId}}" style="margin: 0 auto;">
            <input type="hidden" name="accessoriesId" id="{{accessoriesIdF}}"/>
            <input type="hidden" name="serialNumber" id="{{serialNumberF}}"/>
            <label class="col-xs-2 text-left green" style="text-align: left;">
                <input type="text" id="{{accessoriesNameF}}" name="accessoriesName"    placeholder="{{accessoriesTitleName}} "  class="selfAdapta"/>
            </label>
            <label class="col-xs-1 col-md-offset-4 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-eye-open" id="{{accessoriesEyeId}}"
                      onclick="javascript:showOrHideAccessories(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-trash" id="{{accessoriesTrashId}}"
                      onclick="javascript:trashAccessoriesSelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-repeat" id="{{accessoriesRepeatId}}"
                      onclick="javascript:refreshAccessoriesSelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-import" id="{{accessoriesCopyId}}"
                      onclick="javascript:copyAccessories(this,'{{currenId}}')"></span>
            </label>
        </div>
        <form id="{{accessoriesFormId}}" method="post" class="form-horizontal" action="edit">
            <div id="{{accessoriesAllInfoId}}" class="bom-info">
                <div id="{{accessoriesDetailId}}" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{accessoriesTitleName}}--%>详&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;细</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{materialTypeIdF}}"> 类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别 <a href="<%=path%>/system/material_type/list" target="_blank">+</a> </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{materialTypeIdF}}"
                                    name="materialTypeId" placeholder="材料类别">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{spIdF}}"> 供&nbsp;&nbsp;应&nbsp;&nbsp;商 <a href="<%=path%>/system/sp/list" target="_blank">+</a></label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{spIdF}}" name="spId"
                                    placeholder="供应商">
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{yearCodeF}}"> 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份  <a href="<%=path%>/system/year_conf/list" target="_blank">+</a> </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{yearCodeF}}" name="yearCode"
                                    placeholder="年份">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{classicIdF}}"> 材&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;质 <a href="<%=path%>/system/material_classic/list" target="_blank">+</a></label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{classicIdF}}" name="classicId"
                                    placeholder="材质">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{pantoneIdsF}}"> 颜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色 <a href="<%=path%>/system/pantone/list" target="_blank">+</a></label>

                        <div class="col-xs-3">
                            <%--<input type="text" id="{{pantoneIdF}}" name="pantoneId" placeholder="颜色"   class="col-xs-10 col-sm-12"/>--%>
                            <select class="col-xs-12  col-sm-12 form-control" multiple="multiple" name="pantoneIds"
                                    placeholder="颜色" id="{{pantoneIdsF}}"></select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{productTypeIdF}}"> 品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名 <a href="<%=path%>/system/product_type/list" target="_blank">+</a></label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{productTypeIdF}}" name="productTypeId"
                                    placeholder="品名">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{techRequiredF}}" data-toggle="tooltip"
                               title="工艺要求"> 工艺要求 <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>

                        <div class="col-xs-3">
                            <input type="text" id="{{techRequiredF}}" name="techRequired" placeholder="工艺要求"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{lengthF}}"> 长度(CM) <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{lengthF}}" name="length" placeholder="长度(CM)"  class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{widthF}}" data-toggle="tooltip" title="宽度(CM)">宽度(CM)<a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{widthF}}" name="width" placeholder="宽度(CM)"  class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{remarkF}}"> 备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注 <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{remarkF}}" name="remark" placeholder="备注" class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                </div>

                <div id="accessoriesUnitDosage1" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{accessoriesTitleName}}--%>单位用量&位置</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{unitIdF}}"> 用量单位 <a href="<%=path%>/system/material/unit/list" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{unitIdF}}" name="unitId" placeholder="用量单位">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{unitAmountF}}"> 单位用量 <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{unitAmountF}}" name="unitAmount" placeholder="用量" class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{positionIdsF}}">物料位置<a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <select data-style="btn-info" class="selectpicker show-menu-arrow" data-width="100%"  id="{{positionIdsF}}" name="positionIds" multiple placeholder="物料位置">"
                            </select>
                            <a href="<%=path%>/system/material/position/list" target="_blank">+</a>
                        </div>

                        <label class="col-xs-2  control-label " style="color: red" for="{{positionIdBlF}}"> 所属归类<a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{positionIdBlF}}" name="isShow"
                                    placeholder="">
                                <option value="">...请选择...</option>
                                <option value="0">大身</option>
                                <option value="1">袖子</option>
                                <option value="2">帽子</option>
                                <option value="3">内里</option>
                                <option value="4">拉链</option>
                                <option value="5">包装材料</option>
                            </select>
                        </div>

                    </div>
                </div>

                <div id="accessoriesSpinfo1" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{accessoriesTitleName}}--%>供应商信息</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{attritionRateF}}"> 损耗率(小数) <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{attritionRateF}}" name="attritionRate" placeholder="损耗率，e.g.0.03"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                        <label class="col-xs-2  control-label" for="{{unitPriceF}}" data-toggle="tooltip"
                               data-placement="top" title="用量单位的价格"> 单价(￥) <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{unitPriceF}}" name="unitPrice" placeholder="单价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{colorAmountF}}" data-toggle="tooltip"
                               title="各色用量 =单位用量 *(1 + 损耗率)"> 各色用量 <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{colorAmountF}}" name="colorAmount" placeholder="各色用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{colorPriceF}}" title="各色单价 = 各色用量 * 单价">各色单价(￥) <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{colorPriceF}}" name="colorPrice" placeholder="各色单价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{totalAmountF}}" data-toggle="tooltip"
                               title="各色总用量 = 订单数量 * 各色用量"> 各色总用量 <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalAmountF}}" name="totalAmount" placeholder="各色总用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{totalPriceF}}" title="各色总用量的价格和 = 订单数量 *  各色单价 ">总价(￥) <a href="<%=path%>/system/product_type/list" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalPriceF}}" name="totalPrice" placeholder="总价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>
                </div>

            </div>
        </form>

    </div>
    {{/each}}
</script>
<%--
  Created by IntelliJ IDEA.
  User: zhangjh
  Date: 2015/7/3
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script id="accessories-template" type="text/x-handlebars-template">
    {{#each accessories}}
    <div id="{{accessoriesDivId}}" class="bom-info">
        <div class="bom-info form-group" id="{{accessoriesTitleId}}" style="margin: 0 auto;">
            <input type="hidden" name="accessoriesId" id="{{accessoriesIdF}}"/>
            <label class="col-xs-1 control-label text-left green" style="text-align: left;">{{accessoriesTitleName}} </label>
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
                                    class="widget-title lighter">{{accessoriesTitleName}}详细</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{materialTypeIdF}}"> 材料类别 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{materialTypeIdF}}"       name="materialTypeId" placeholder="材料类别">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{spIdF}}"> 供应商 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{spIdF}}" name="spId" placeholder="供应商">
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{yearCodeF}}"> 年份 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{yearCodeF}}" name="yearCode"           placeholder="年份">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{classicIdF}}"> 材质 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{classicIdF}}" name="classicId"         placeholder="材质">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{pantoneIdF}}"> 颜色 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{pantoneIdF}}" name="pantoneId" placeholder="颜色"   class="col-xs-10 col-sm-12"/>
                        </div>
                        <label class="col-xs-2  control-label" for="{{productTypeIdF}}"> 品名 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{productTypeIdF}}" name="productTypeId"    placeholder="品名">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{techRequiredF}}" data-toggle="tooltip"  title="工艺要求"> 工艺要求 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{techRequiredF}}" name="techRequired" placeholder="工艺要求"   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{lengthF}}"> 长度（CM） </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{lengthF}}" name="length"   placeholder="长度（CM）" class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{widthF}}" data-toggle="tooltip"   title="宽度（CM）"> 宽度（CM）</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{widthF}}" name="width" placeholder="宽度（CM）"    class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{remarkF}}"> 备注 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{remarkF}}" name="remark"      placeholder="备注" class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>





                </div>

                <div id="accessoriesUnitDosage1" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter">{{accessoriesTitleName}}单位用量&位置</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{unitIdF}}"> 用量单位 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{unitIdF}}" name="unitId"
                                    placeholder="用量单位">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label"
                               for="{{unitAmountF}}"> 单位用量 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{unitAmountF}}" name="unitAmount" placeholder="用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{positionIdsF}}">物料位置</label>
                        <div class="col-xs-3">
                            <select data-style="btn-info" class="selectpicker show-menu-arrow" data-width="100%"
                                    id="{{positionIdsF}}" name="positionIds" multiple placeholder="物料位置">"
                            </select>
                        </div>

                    </div>

                </div>

                <div id="accessoriesSpinfo1" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter">{{accessoriesTitleName}}供应商信息</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{orderCountF}}" data-toggle="tooltip"
                               title="当前BOM的订单数"> 各色总数量 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{orderCountF}}" name="orderCount" placeholder="各色总数量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{attritionRateF}}"> 损耗率 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{attritionRateF}}" name="attritionRate"
                                   placeholder="损耗率" class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label"
                               for="{{totalAmountF}}" data-toggle="tooltip" title="各色总数量 * 单位用量"> 各色总用量 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{totalAmountF}}" name="totalAmount" placeholder="各色总用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{unitPriceF}}"> 单价 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{unitPriceF}}" name="unitPrice" placeholder="单价"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                        <label class="col-xs-2  control-label" for="{{totalPriceF}}"> 总价 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{totalPriceF}}" name="totalPrice" placeholder="总价"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>
                </div>

            </div>
        </form>

    </div>
    {{/each}}
</script>
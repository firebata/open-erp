<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="packagingInfo">
    <h5 class="header smaller lighter blue">
        包材
        <span class="glyphicon glyphicon-plus-sign blue" alt="增加包装材料" id="imgAddPackaging"></span>
    </h5>
    <div id="packagingItemInfo">


    </div>
</div>
<script id="packaging-template" type="text/x-handlebars-template">
    {{#each packaging}}
    <div id="{{packagingDivId}}" class="bom-info">
        <div class="bom-info form-group" id="{{packagingTitleId}}" style="margin: 0 auto;">
            <input type="hidden" name="packagingId" id="{{packagingIdP}}"/>
            <label class="col-xs-1 text-left green" style="text-align: left;">
                <input type="text" id="{{packagingsNameP}}" name="packagingsName" placeholder="{{packagingTitleName}} "  /></label>
            <label class="col-xs-1 col-md-offset-4 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-eye-open" id="{{packagingEyeId}}" onclick="javascript:showOrHidePackaging(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-trash" id="{{packagingTrashId}}"   onclick="javascript:trashPackagingSelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-repeat" id="{{packagingRepeatId}}"  onclick="javascript:refreshPackagingSelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-import" id="{{packagingCopyId}}"   onclick="javascript:copyPackaging(this,'{{currenId}}')"></span>
            </label>
        </div>
        <form id="{{packagingFormId}}" method="post" class="form-horizontal" action="edit">
            <div id="{{packagingAllInfoId}}" class="bom-info">
                <div id="{{packagingDetailId}}" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{packagingTitleName}}--%>详细</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{materialTypeIdP}}"> 材料类别 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{materialTypeIdP}}"       name="materialTypeId" placeholder="材料类别">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{spIdP}}"> 供应商 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{spIdP}}" name="spId" placeholder="供应商">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{yearCodeP}}"> 年份 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{yearCodeP}}" name="yearCode"           placeholder="年份">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{classicIdP}}"> 材质 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{classicIdP}}" name="classicId"         placeholder="材质">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{pantoneIdsP}}"> 颜色 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12  col-sm-12 form-control" multiple="multiple" name="pantoneIds" placeholder="颜色"    id="{{pantoneIdsP}}"></select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{productTypeIdP}}"> 品名 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{productTypeIdP}}" name="productTypeId"    placeholder="品名">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{techRequiredP}}" data-toggle="tooltip"  title="工艺要求"> 工艺要求 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{techRequiredP}}" name="techRequired" placeholder="工艺要求"   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{lengthP}}"> 长度（CM） </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{lengthP}}" name="length"   placeholder="长度（CM）" class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{widthP}}" data-toggle="tooltip"  title="宽度（CM）"> 宽度（CM） </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{widthP}}" name="width"  placeholder="宽度（CM）"  class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{remarkP}}"> 备注 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{remarkP}}" name="remark" placeholder="备注" class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>
                </div>

                <div id="packagingUnitDosage1" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{packagingTitleName}}--%>单位用量&位置</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{unitIdP}}"> 用量单位 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{unitIdP}}" name="unitId"
                                    placeholder="用量单位">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label"          for="{{unitAmountP}}"> 单位用量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{unitAmountP}}" name="unitAmount" placeholder="用量"     class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{positionIdsP}}">物料位置</label>
                        <div class="col-xs-3">
                            <select data-style="btn-info" class="selectpicker show-menu-arrow" data-width="100%"
                                    id="{{positionIdsP}}" name="positionIds" multiple placeholder="物料位置">"
                            </select>
                        </div>


                    </div>

                </div>

                <div id="packagingSpinfo1" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5  class="widget-title lighter"><%--{{packagingTitleName}}--%>供应商信息</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{orderCountP}}" data-toggle="tooltip"     title="当前BOM的订单数"> 各色总数量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{orderCountP}}" name="orderCount" placeholder="各色总数量"      class="col-xs-10 col-sm-12"/>
                        </div>
                        <label class="col-xs-2  control-label" for="{{attritionRateP}}"> 损耗率 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{attritionRateP}}" name="attritionRate"    placeholder="损耗率" class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label"   for="{{totalAmountP}}" data-toggle="tooltip" title="各色总数量 * 单位用量"> 各色总用量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalAmountP}}" name="totalAmount" placeholder="各色总用量"    class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{unitPriceP}}"> 单价 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{unitPriceP}}" name="unitPrice" placeholder="单价"     class="col-xs-10 col-sm-12"/>
                        </div>
                        <label class="col-xs-2  control-label" for="{{totalPriceP}}"> 总价 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalPriceP}}" name="totalPrice" placeholder="总价"      class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>
                </div>

            </div>
        </form>

    </div>
    {{/each}}
</script>
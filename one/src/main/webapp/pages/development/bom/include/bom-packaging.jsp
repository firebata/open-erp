<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="packagingInfo">
    <h5 class="header smaller lighter blue">
        包&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;材
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
            <label class="col-xs-2 text-left green" style="text-align: left;">
                <input type="text" id="{{packagingsNameP}}" name="packagingsName"    placeholder="{{packagingTitleName}}"  class="selfAdapta"/></label>
            <label class="col-xs-1 col-md-offset-4 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-eye-open" id="{{packagingEyeId}}"
                      onclick="javascript:showOrHidePackaging(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-trash" id="{{packagingTrashId}}"
                      onclick="javascript:trashPackagingSelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-repeat" id="{{packagingRepeatId}}"
                      onclick="javascript:refreshPackagingSelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-import" id="{{packagingCopyId}}"
                      onclick="javascript:copyPackaging(this,'{{currenId}}')"></span>
            </label>
        </div>
        <form id="{{packagingFormId}}" method="post" class="form-horizontal" action="edit">
            <div id="{{packagingAllInfoId}}" class="bom-info">
                <div id="{{packagingDetailId}}" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{packagingTitleName}}--%>详&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;细</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{materialTypeIdP}}"> 材料类别 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{materialTypeIdP}}"
                                    name="materialTypeId" placeholder="材料类别">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{spIdP}}"> 供&nbsp;&nbsp;应&nbsp;&nbsp;商 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{spIdP}}" name="spId"
                                    placeholder="供应商">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{yearCodeP}}"> 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{yearCodeP}}" name="yearCode"
                                    placeholder="年份">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{classicIdP}}"> 材&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;质 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{classicIdP}}" name="classicId"
                                    placeholder="材质">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{pantoneIdsP}}"> 颜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12  col-sm-12 form-control" multiple="multiple" name="pantoneIds"
                                    placeholder="颜色" id="{{pantoneIdsP}}"></select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{productTypeIdP}}"> 品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名 </label>

                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{productTypeIdP}}" name="productTypeId"
                                    placeholder="品名">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{techRequiredP}}" data-toggle="tooltip"
                               title="工艺要求"> 工艺要求 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{techRequiredP}}" name="techRequired" placeholder="工艺要求"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{lengthP}}"> 长度(CM) </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{lengthP}}" name="length" placeholder="长度(CM)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{widthP}}" data-toggle="tooltip" title="宽度(CM)">
                            宽度(CM) </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{widthP}}" name="width" placeholder="宽度(CM)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{remarkP}}"> 备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{remarkP}}" name="remark" placeholder="备注"
                                   class="col-xs-10 col-sm-12"/>
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

                        <label class="col-xs-2  control-label" for="{{unitAmountP}}"> 单位用量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{unitAmountP}}" name="unitAmount" placeholder="用量"
                                   class="col-xs-10 col-sm-12"/>
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
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{packagingTitleName}}--%>供应商信息</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{attritionRateP}}"> 损耗率(小数形式) </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{attritionRateP}}" name="attritionRate" placeholder="损耗率，e.g.0.03"
                                   class="col-xs-10 col-sm-12"/>
                        </div>


                        <label class="col-xs-2  control-label" for="{{unitPriceP}}" title="用量单位的价格">  单价(￥) </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{unitPriceP}}" name="unitPrice" placeholder="单价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>


                    </div>


                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{colorAmountP}}" data-toggle="tooltip"
                               title="各色用量 =单位用量 *(1 + 损耗率)"> 各色用量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{colorAmountP}}" name="colorAmount" placeholder="各色用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{colorPriceP}}" title="各色单价 = 各色用量 * 单价">
                            各色单价(￥) </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{colorPriceP}}" name="colorPrice" placeholder="各色单价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{totalAmountP}}" data-toggle="tooltip"
                               title="各色总用量 = 订单数量 * 各色用量"> 各色总用量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalAmountP}}" name="totalAmount" placeholder="各色总用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{totalPriceP}}" title="各色总用量的价格和 = 订单数量 *  各色单价 "> 总价(￥) </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalPriceP}}" name="totalPrice" placeholder="总价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>
                </div>

            </div>
        </form>

    </div>
    {{/each}}
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="factoryInfo">
    <h5 class="header smaller lighter blue">
        成衣厂
        <span class="glyphicon glyphicon-plus-sign blue" alt="增加成衣厂" id="imgAddFactory"></span>
    </h5>
    <div id="factoryItemInfo">

    </div>
</div>
<script id="factory-template" type="text/x-handlebars-template">
    {{#each factory}}
    <div id="{{factoryDivId}}" class="bom-info">
        <div class="bom-info form-group" id="{{factoryTitleId}}" style="margin: 0 auto;">
            <input type="hidden" name="factoryQuoteId" id="{{factoryQuoteId}}"/>
            <label class="col-xs-1 control-label text-left green"
                   style="text-align: left;">{{factoryTitleName}} </label>
            <label class="col-xs-1 col-md-offset-4 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-eye-open" id="{{factoryEyeId}}"
                      onclick="javascript:showOrHideFactory(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-trash" id="{{factoryTrashId}}"
                      onclick="javascript:trashFactorySelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-repeat" id="{{factoryRepeatId}}"
                      onclick="javascript:refreshFactorySelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue ">
                <span class="glyphicon glyphicon-floppy-disk" id="{{factoryFloppyDiskId}}"
                      onclick="javascript:saveFactory(this,'{{currenId}}')"></span>
            </label>
            <%--<label class="col-xs-1 control-label no-padding-right blue ">--%>
                <%--&lt;%&ndash;<span class="glyphicons glyphicons-more-items" id="{{factoryCopyId}}" onclick="javascript:copyFactory(this,'{{currenId}}')"></span>&ndash;%&gt;--%>
                <%--<span class="glyphicons glyphicons-more-items" id="{{factoryCopyId}}"--%>
                      <%--onclick="javascript:copyFactory(this,'{{currenId}}')"></span>--%>
            <%--</label>--%>
        </div>
        <form id="{{factoryFormId}}" method="post" class="form-horizontal" action="edit">
            <div id="{{factoryAllInfoId}}" class="bom-info">
                <div id="{{factoryDetailId}}" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter">{{factoryTitleName}}详细</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{offerAmount}}"> 订单数量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{offerAmount}}" name="offerAmount" placeholder="订单数量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="control-label  col-xs-2" for="{{spIdC}}"> 成衣厂 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{spIdC}}" name="factoryId"
                                    placeholder="成衣厂">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{preOfferDate}}"> 成衣报价时间 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{preOfferDate}}" name="preOfferDate" placeholder="成衣报价时间"
                                   class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{clothReceivedDate}}"> 成衣收到时间 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{clothReceivedDate}}" name="clothReceivedDate" placeholder="成衣收到时间"
                                   class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{fabricsEndDate}}"> 面料交货时间 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{fabricsEndDate}}" name="fabricsEndDate" placeholder="面料交货时间"
                                   class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{accessoriesEndDate}}"> 辅料交货时间 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{accessoriesEndDate}}" name="accessoriesEndDate"
                                   placeholder="辅料交货时间" class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{factoryOffer}}"> 工厂报价 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{factoryOffer}}" name="factoryOffer" placeholder="工厂报价"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{factoryMargins}}"> 工厂报价利润率</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{factoryMargins}}" name="factoryMargins" placeholder=" 工厂报价利润率"  class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>


                    <div class="form-group">
                       <%-- <label class="col-xs-2  control-label" for="{{euroPrice}}">工厂欧元报价(€)</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{euroPrice}}" name="euroPrice" placeholder="工厂欧元报价(€)"              class="col-xs-10 col-sm-12"/>
                        </div>--%>

                        <label class="col-xs-2  control-label" for="{{quoteReference}}"> 报价参考 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{quoteReference}}"        name="quoteReference" placeholder="报价参考">
                                <option value="">请选择...</option>
                                <option value="0">不参考</option>
                                <option value="1">参考</option>
                            </select>
                        </div>
                    </div>

                </div>
            </div>
        </form>

    </div>
    {{/each}}
</script>
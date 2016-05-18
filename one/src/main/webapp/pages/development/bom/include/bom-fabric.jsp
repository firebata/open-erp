<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="fabricsInfo">
    <h5 class="header smaller lighter blue">
        面料
        <span class="glyphicon glyphicon-plus-sign blue" alt="增加面料" id="imgAddFabric"></span>
    </h5>

    <div id="fabricsItemInfo">

    </div>
    <%--<div class="hr hr32 hr-dotted"></div>--%>
</div>
<script id="fabric-template" type="text/x-handlebars-template">
    {{#each fabric}}
    <div id="{{fabricDivId}}" class="bom-info">
        <div class="bom-info form-group" id="{{fabricTitleId}}" style="margin: 0 auto;">
            <input type="hidden" name="fabricId" id="{{fabricId}}"/>
            <input type="hidden" name="serialNumber" id="{{serialNumber}}"/>
            <label class="col-xs-2 text-left green" style="text-align: left;">
                <input type="text" id="{{fabricName}}" name="fabricName" placeholder="{{fabricTitleName}}"  class="selfAdapta"/>
            </label>
            <label class="col-xs-2 text-left green" style="text-align: left;">
                <input type="text" id="{{fabricNo}}" name="fabricNo" placeholder="{{fabricNoName}}"   class="selfAdapta" />
            </label>
            <label class="col-xs-1 col-md-offset-4 control-label no-padding-right blue">
                <span class="glyphicon glyphicon-eye-open" id="{{fabricEyeId}}"
                      onclick="javascript:showOrHideFabric(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue">
                <span class="glyphicon glyphicon-trash" id="{{fabricTrashId}}"
                      onclick="javascript:trashFabricSelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue">
                <span class="glyphicon glyphicon-repeat" id="{{fabricRepeatId}}"
                      onclick="javascript:refreshFabricSelect(this,'{{currenId}}')"></span>
            </label>
            <label class="col-xs-1 control-label no-padding-right blue">
                <span class="glyphicon glyphicon-import" id="{{fabricCopyId}}"
                      onclick="javascript:copyFabric(this,'{{currenId}}')"></span>
            </label>
        </div>
        <form id="{{fabricFormId}}" method="post" class="form-horizontal" action="edit">
            <div id="{{fabricAllInfoId}}" class="bom-info modal-body">
                <div id="{{fabricDetailId}}" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{fabricTitleName}}--%>详细</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{materialTypeId}}"> 材料类别 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{materialTypeId}}"
                                    name="materialTypeId" placeholder="材料类别">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{spId}}"> 供应商 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{spId}}" name="spId" placeholder="供应商">
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{yearCode}}"> 年份 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{yearCode}}" name="yearCode"
                                    placeholder="年份">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{classicId}}"> 材质 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{classicId}}" name="classicId"
                                    placeholder="材质">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{pantoneIds}}"> 颜色 </label>
                        <div class="col-xs-3">
                            <%--<input type="text" id="{{pantoneId}}" name="pantoneId" placeholder="颜色"--%>
                            <%--class="col-xs-10 col-sm-12"/>--%>
                            <select class="col-xs-12  col-sm-12 form-control" multiple="multiple" name="pantoneIds"
                                    placeholder="颜色" id="{{pantoneIds}}"></select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{productTypeId}}"> 品名 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{productTypeId}}" name="productTypeId"
                                    placeholder="品名">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{specificationId}}"> 纱支密度 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{specificationId}}"
                                    name="specificationId" placeholder="纱支密度">
                            </select>
                        </div>

                        <label class="col-xs-2 control-label" for="{{dyeId}}"> 染色方式 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{dyeId}}" name="dyeId"
                                    placeholder="染色方式">
                            </select>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{finishId}}"> 后整理 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{finishId}}" name="finishId"
                                    placeholder="后整理">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{blcId}}"> 复合或涂层 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{blcId}}" name="blcId"
                                    placeholder="复合或涂层">
                            </select>
                        </div>

                    </div>

                    <div id="{{compositeDiv}}">
                        <div id="{{tiemoDiv}}">
                            <div class="form-group">
                                <label class="col-xs-2  control-label" for="{{compositeClassicId}}"> 材质 </label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeClassicId}}"
                                            name="compositeClassicId" placeholder="材质">
                                    </select>
                                </div>

                                <label class="col-xs-2  control-label" for="{{compositePantoneIds}}"> 颜色 </label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12  col-sm-12 form-control" multiple="multiple" name="compositePantoneIds"
                                            placeholder="颜色" id="{{compositePantoneIds}}"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2  control-label" for="{{compositeProductTypeId}}"> 品名 </label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeProductTypeId}}"
                                            name="compositeProductTypeId"
                                            placeholder="品名">
                                    </select>
                                </div>

                                <label class="col-xs-2  control-label" for="{{compositeSpecificationId}}"> 纱支密度 </label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeSpecificationId}}"
                                            name="compositeSpecificationId" placeholder="纱支密度">
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 control-label" for="{{compositeDyeId}}"> 染色方式 </label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeDyeId}}"
                                            name="compositeDyeId" placeholder="染色方式">
                                    </select>
                                </div>

                                <label class="col-xs-2  control-label" for="{{compositeFinishId}}"> 后整理 </label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeFinishId}}"
                                            name="compositeFinishId" placeholder="后整理">
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="{{momcId}}"> 膜或涂层材质 </label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{momcId}}" name="momcId"
                                        placeholder="膜或涂层材质">
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="{{comocId}}"> 膜或涂层颜色 </label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{comocId}}" name="comocId"
                                        placeholder="膜或涂层颜色">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="{{wvpId}}"> 透湿程度 </label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{wvpId}}" name="wvpId"
                                        placeholder="透湿程度">
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="{{mtId}}"> 膜的厚度 </label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{mtId}}" name="mtId"
                                        placeholder="膜的厚度">
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="{{woblcId}}"> 贴膜或涂层工艺 </label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{woblcId}}" name="woblcId"
                                        placeholder="贴膜或涂层工艺">
                                </select>
                            </div>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{isShow}}"> 报价表中是否显示 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{isShow}}" name="isShow"
                                    placeholder="报价表中">
                                <option value="">请选择...</option>
                                <option value="0">不显示</option>
                                <option value="1">显示</option>
                            </select>
                        </div>
                    </div>

                </div>
                <div id="fabricUnitDosage1" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{fabricTitleName}}--%>单位用量&位置</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{unitId}}"> 用量单位 </label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{unitId}}" name="unitId"
                                    placeholder="用量单位">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{unitAmount}}"> 单位用量 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{unitAmount}}" name="unitAmount" placeholder="用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{positionIds}}">物料位置</label>
                        <div class="col-xs-3">
                            <select data-style="btn-info" class="selectpicker show-menu-arrow" data-width="100%"
                                    id="{{positionIds}}" name="positionIds" multiple placeholder="物料位置">"
                            </select>
                        </div>

                    </div>

                </div>

                <div id="fabricSpinfo1" class="bom-info">
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter"><%--{{fabricTitleName}}--%>供应商信息</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{attritionRate}}"> 损耗率 </label>

                        <div class="col-xs-3">
                            <input type="text" id="{{attritionRate}}" name="attritionRate"
                                   placeholder="损耗率，e.g.0.03" class="col-xs-10 col-sm-12"/>
                        </div>


                        <label class="col-xs-2  control-label" for="{{unitPrice}}"  title="用量单位的价格"> 单价（￥） </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{unitPrice}}" name="unitPrice" placeholder="单价（￥）"
                                   class="col-xs-10 col-sm-12"/>
                        </div>


                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{colorAmount}}" data-toggle="tooltip"
                               title="各色用量 =单位用量 *（1 + 损耗率）"> 各色用量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{colorAmount}}" name="colorAmount" placeholder="各色用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{colorPrice}}" title="各色单价 = 各色用量 * 单价">
                            各色单价（￥） </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{colorPrice}}" name="colorPrice" placeholder="各色单价（￥）"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{totalAmount}}" data-toggle="tooltip"
                               title="订单数量 * 各色用量"> 各色总用量 </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalAmount}}" name="totalAmount" placeholder="各色总用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{totalPrice}}" title="各色总用量的价格和 = 订单数量 *  各色单价 "> 总价（￥） </label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalPrice}}" name="totalPrice" placeholder="总价（￥）"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>
                </div>

            </div>
        </form>

    </div>
    {{/each}}
</script>
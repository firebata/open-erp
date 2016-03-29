<%--
  Created by IntelliJ IDEA.
  User: zhangjh
  Date: 2015/7/3
  Time: 17:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                        <label class="col-xs-2  control-label" for="{{euroPrice}}">工厂欧元报价(€)</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{euroPrice}}" name="euroPrice" placeholder="工厂欧元报价(€)"              class="col-xs-10 col-sm-12"/>
                        </div>

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


                <div id="{{productionInstructionDetailId}}" class="bom-info">
                    <input type="hidden" name="productionInstructionId" id="{{productionInstructionId}}"/>
                    <div class="form-group">
                        <div class="col-xs-12">
                            <div class="widget-header widget-header-blue widget-header-flat">
                                <i class="ace-icon fa fa-hand-o-right blue"></i> &nbsp;&nbsp;<h5
                                    class="widget-title lighter">指示单信息</h5>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{cropRequirements}}"> 裁剪要求 </label>
                        <div class="col-xs-3">
                            <textarea id="{{cropRequirements}}" name="cropRequirements" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="裁剪要求" ></textarea>
                        </div>

                        <label class="col-xs-2  control-label" for="{{qualityRequirements}}"> 工艺及质量要求</label>
                        <div class="col-xs-3">
                            <textarea id="{{qualityRequirements}}" name="qualityRequirements" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="工艺及质量要求" ></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{finishPressingRequirements}}"> 整烫要求 </label>
                        <div class="col-xs-3">
                            <textarea id="{{finishPressingRequirements}}" name="finishPressingRequirements" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="整烫要求" ></textarea>
                        </div>

                        <label class="col-xs-2  control-label" for="{{spcialTech}}"> 特殊工艺</label>
                        <div class="col-xs-3">
                            <textarea id="{{spcialTech}}" name="spcialTech" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="特殊工艺" ></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{packingRequirements}}"> 包装要求 </label>
                        <div class="col-xs-3">
                            <textarea id="{{packingRequirements}}" name="packingRequirements" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="包装要求" ></textarea>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{overstitch}}">面线</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{overstitch}}" name="overstitch" placeholder="面线"      class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="control-label  col-xs-2" for="{{overstitchSpace}}">面线针距</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{overstitchSpace}}" name="overstitchSpace" placeholder="面线针距"        class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{blindstitch}}">暗线</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{blindstitch}}" name="blindstitch" placeholder="暗线"       class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="control-label  col-xs-2" for="{{blindstitchSpace}}">暗线针距</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{blindstitchSpace}}" name="blindstitchSpace" placeholder="暗线针距"     class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{overlock}}">拷边</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{overlock}}" name="overlock" placeholder="拷边"      class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="control-label  col-xs-2" for="{{overlockSpace}}">拷边针距</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{overlockSpace}}" name="overlockSpace" placeholder="拷边针距"  class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{trademarkCode}}">商标编码</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{trademarkCode}}" name="trademarkCode" placeholder="商标编码"      class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="control-label  col-xs-2" for="{{trademarkRemark}}">商标描述</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{trademarkRemark}}" name="trademarkRemark" placeholder="商标描述"    class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{scaleCode}}">尺标编码</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{scaleCode}}" name="scaleCode" placeholder="尺标编码"      class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="control-label  col-xs-2" for="{{scaleRemark}}">尺标描述</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{scaleRemark}}" name="scaleRemark" placeholder="尺标描述"       class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{rinsingMarksCode}}">水洗标编码</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{rinsingMarksCode}}" name="rinsingMarksCode" placeholder="水洗标编码"    class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="control-label  col-xs-2" for="{{rinsingMarksRemark}}">水洗标描述</label>
                        <div class="col-xs-3">
                            <input type="text" id="{{rinsingMarksRemark}}" name="rinsingMarksRemark" placeholder="水洗标描述"      class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{sketchUrlUid}}"> 款式图 </label>
                        <div class="col-xs-3">
                            <input id="{{sketchUrlUid}}" type="file" multiple class="file-loading col-xs-12"   name="fileLocation">
                        </div>
                        <label class="col-xs-2  control-label" for="{{specificationUrlUid}}"> 规格表</label>
                        <div class="col-xs-3">
                            <input id="{{specificationUrlUid}}" type="file" multiple class="file-loading col-xs-12"   name="fileLocation">
                        </div>
                    </div>
                    <div class="form-group">



                    </div>

                </div>
            </div>
        </form>

    </div>
    {{/each}}
</script>
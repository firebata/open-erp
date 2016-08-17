<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style type="text/css">
    .divcss5 {
        border: 1px dashed #000;
    }
</style>
<div id="fabricsInfo">
    <h5 class="header smaller lighter blue">
        面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;料
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
                <input type="text" id="{{fabricName}}" name="fabricName" placeholder="{{fabricTitleName}}"
                       class="selfAdapta"/>
            </label>
            <label class="col-xs-2 text-left green" style="text-align: left;">
                <input type="text" id="{{fabricNo}}" name="fabricNo" placeholder="{{fabricNoName}}" class="selfAdapta"/>
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
                                    class="widget-title lighter"><%--{{fabricTitleName}}--%>详&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;细</h5>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label  col-xs-2" for="{{materialTypeId}}"> 类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别 <a href="javascript:openChildW('<%=path%>/system/material_type/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{materialTypeId}}"
                                    name="materialTypeId" placeholder="类别">
                            </select>

                        </div>
                        <label class="col-xs-2  control-label" for="{{blcId}}"> 复合/涂层 <a href="javascript:openChildW('<%=path%>/system/material/blc/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{blcId}}" name="blcId"
                                    placeholder="复合/涂层">
                            </select>
                        </div>

                    </div>


                    <div class="form-group">


                        <label class="col-xs-2  control-label" for="{{yearCode}}"> 年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份  <a href="javascript:openChildW('<%=path%>/system/year_conf/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{yearCode}}" name="yearCode"
                                    placeholder="年份">
                            </select>
                        </div>
                        <label class="col-xs-2  control-label" for="{{spId}}"> 供&nbsp;&nbsp;应&nbsp;&nbsp;商  <a href="javascript:openChildW('<%=path%>/system/sp/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{spId}}" name="spId" placeholder="供应商">
                            </select>
                        </div>

                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{classicId}}"> 材&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;质  <a href="javascript:openChildW('<%=path%>/system/material_classic/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{classicId}}" name="classicId"
                                    placeholder="材质">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{pantoneIds}}"> 颜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色  <a href="javascript:openChildW('<%=path%>/system/pantone/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12  col-sm-12 form-control" multiple="multiple" name="pantoneIds"
                                    placeholder="颜色" id="{{pantoneIds}}"></select>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{productTypeId}}"> 品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名  <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{productTypeId}}" name="productTypeId"
                                    placeholder="品名">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{specificationId}}"> 纱支密度  <a href="javascript:openChildW('<%=path%>/system/material/specification/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{specificationId}}"
                                    name="specificationId" placeholder="纱支密度">
                            </select>
                        </div>


                    </div>


                    <div class="form-group">

                        <label class="col-xs-2 control-label" for="{{dyeId}}"> 染色方式  <a href="javascript:openChildW('<%=path%>/system/material/dye/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{dyeId}}" name="dyeId"
                                    placeholder="染色方式">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label " style="color: red" for="{{isShow}}"> 主&nbsp;&nbsp;面&nbsp;&nbsp;料<a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;&nbsp;&nbsp;</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{isShow}}" name="isShow"
                                    placeholder="报价表中">
                                <option value="">...请选择...(BOM必须选择一个面料)</option>
                                <option value="0">不显示</option>
                                <option value="1">显示</option>
                            </select>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{finishId}}"> 防&nbsp;&nbsp;泼&nbsp;&nbsp;水  <a href="javascript:openChildW('<%=path%>/system/material/finish/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{finishId}}" name="finishId"
                                    placeholder="防泼水">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{wvpId}}"> 透&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;湿  <a href="javascript:openChildW('<%=path%>/system/material/wvp/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{wvpId}}" name="wvpId"
                                    placeholder="透湿">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{waterProofId}}"> 水&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;压  <a href="javascript:openChildW('<%=path%>/system/function/water_proof/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{waterProofId}}" name="waterProofId"
                                    placeholder="水压">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{permeabilityId}}"> 透&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气  <a href="javascript:openChildW('<%=path%>/system/function/air_permeability/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{permeabilityId}}"
                                    name="permeabilityId"
                                    placeholder="透气">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{waterpressureId}}"> 接缝水压  <a href="javascript:openChildW('<%=path%>/system/function/seam_waterpressure/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{waterpressureId}}"
                                    name="waterpressureId"
                                    placeholder="接缝水压">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{ultravioletProtectionId}}"> 抗紫外线  <a href="javascript:openChildW('<%=path%>/system/function/ultraviolet_protection/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{ultravioletProtectionId}}"
                                    name="ultravioletProtectionId"
                                    placeholder="抗紫外线">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{quickDryId}}"> 快&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;干  <a href="javascript:openChildW('<%=path%>/system/function/quick_dry/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{quickDryId}}" name="quickDryId"
                                    placeholder="快干">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{oilProofId}}"> 防&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;油  <a href="javascript:openChildW('<%=path%>/system/function/oil_proof/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{oilProofId}}" name="oilProofId"
                                    placeholder="防油">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{antMosquitosId}}"> 防&nbsp;&nbsp;蚊&nbsp;&nbsp;虫  <a href="javascript:openChildW('<%=path%>/system/function/anti_mosquitos/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{antMosquitosId}}"
                                    name="antMosquitosId"
                                    placeholder="防蚊虫">
                            </select>
                        </div>
                    </div>

                    <div id="{{compositeDiv}}" class="divcss5">
                        <div id="{{tiemoDiv}}">
                            <div class="form-group">
                                <label class="col-xs-2  control-label" for="{{compositeClassicId}}"> 材&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;质  <a href="javascript:openChildW('<%=path%>/system/material_classic/list')" target="_blank">+</a></label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeClassicId}}"
                                            name="compositeClassicId" placeholder="材质">
                                    </select>
                                </div>

                                <label class="col-xs-2  control-label" for="{{compositePantoneIds}}"> 颜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色  <a href="<%=path%>" target="_blank">+</a></label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12  col-sm-12 form-control" multiple="multiple"
                                            name="compositePantoneIds"
                                            placeholder="颜色" id="{{compositePantoneIds}}"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2  control-label" for="{{compositeProductTypeId}}"> 品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名  <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">+</a></label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeProductTypeId}}" name="compositeProductTypeId" placeholder="品名"></select>
                                </div>

                                <label class="col-xs-2  control-label" for="{{compositeSpecificationId}}"> 纱支密度  <a href="javascript:openChildW('<%=path%>/system/material/specification/list')" target="_blank">+</a></label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeSpecificationId}}" name="compositeSpecificationId" placeholder="纱支密度"></select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 control-label" for="{{compositeDyeId}}"> 染色方式  <a href="javascript:openChildW('<%=path%>/system/material/dye/list')" target="_blank">+</a></label>
                                <div class="col-xs-3">
                                    <select class="col-xs-12" data-style="btn-info" id="{{compositeDyeId}}" name="compositeDyeId" placeholder="染色方式"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="{{momcId}}"> 膜/涂层材质  <a href="javascript:openChildW('<%=path%>/system/material/momc/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{momcId}}" name="momcId"
                                        placeholder="膜/涂层材质">
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="{{comocId}}"> 膜/涂层颜色  <a href="javascript:openChildW('<%=path%>/system/material/comoc/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{comocId}}" name="comocId"
                                        placeholder="膜/涂层颜色">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-xs-2  control-label" for="{{woblcId}}"> 膜/涂层工艺  <a href="javascript:openChildW('<%=path%>/system/material/wblc/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{woblcId}}" name="woblcId"
                                        placeholder="贴膜/涂层工艺">
                                </select>
                            </div>
                            <label class="col-xs-2  control-label" for="{{mtId}}"> 膜&nbsp;&nbsp;厚&nbsp;&nbsp;度  <a href="javascript:openChildW('<%=path%>/system/material/mt/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{mtId}}" name="mtId"
                                        placeholder="膜的厚度">
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="{{compositeFinishId}}"> 防&nbsp;&nbsp;泼&nbsp;&nbsp;水  <a href="javascript:openChildW('<%=path%>/system/material/finish/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{compositeFinishId}}"
                                        name="compositeFinishId" placeholder="防泼水">
                                </select>
                            </div>
                            <label class="col-xs-2  control-label" for="{{compositeWvpId}}"> 透&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;湿  <a href="javascript:openChildW('<%=path%>/system/material/wvp/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{compositeWvpId}}"
                                        name="compositeWvpId"
                                        placeholder="透湿">
                                </select>
                            </div>
                        </div>

                        <div class="form-group">

                            <label class="col-xs-2  control-label" for="{{compositeWaterProofId}}"> 水&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;压  <a href="javascript:openChildW('<%=path%>/system/function/water_proof/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{compositeWaterProofId}}"
                                        name="compositeWaterProofId"
                                        placeholder="水压">
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="{{compositePermeabilityId}}"> 透&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;气  <a href="javascript:openChildW('<%=path%>/system/function/air_permeability/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{compositePermeabilityId}}"
                                        name="compositePermeabilityId"
                                        placeholder="透气">
                                </select>
                            </div>
                        </div>

                        <div class="form-group">

                            <label class="col-xs-2  control-label" for="{{compositeWaterpressureId}}"> 接缝水压  <a href="javascript:openChildW('<%=path%>/system/function/seam_waterpressure/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{compositeWaterpressureId}}"
                                        name="compositeWaterpressureId"
                                        placeholder="接缝水压">
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="{{compositeUltravioletProtectionId}}">
                                抗紫外线  <a href="<%=path%>" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info"
                                        id="{{compositeUltravioletProtectionId}}"
                                        name="compositeUltravioletProtectionId"
                                        placeholder="抗紫外线">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-xs-2  control-label" for="{{compositeQuickDryId}}"> 快&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;干  <a href="javascript:openChildW('<%=path%>/system/function/quick_dry/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{compositeQuickDryId}}"
                                        name="compositeQuickDryId"
                                        placeholder="快干">
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="{{compositeOilProofId}}"> 防&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;油  <a href="javascript:openChildW('<%=path%>/system/function/oil_proof/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{compositeOilProofId}}"
                                        name="compositeOilProofId"
                                        placeholder="防油">
                                </select>
                            </div>
                        </div>
                        <div class="form-group">

                            <label class="col-xs-2  control-label" for="{{compositeAntMosquitosId}}"> 防&nbsp;&nbsp;蚊&nbsp;&nbsp;虫  <a href="javascript:openChildW('<%=path%>/system/function/anti_mosquitos/list')" target="_blank">+</a></label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="{{compositeAntMosquitosId}}"
                                        name="compositeAntMosquitosId"
                                        placeholder="防蚊虫">
                                </select>
                            </div>
                        </div>

                    </div>

                    <div class="form-group">

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
                        <label class="col-xs-2  control-label" for="{{unitId}}"> 用量单位  <a href="javascript:openChildW('<%=path%>/system/material/unit/list')" target="_blank">+</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{unitId}}" name="unitId"
                                    placeholder="用量单位">
                            </select>
                        </div>

                        <label class="col-xs-2  control-label" for="{{unitAmount}}"> 单位用量 <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>

                        <div class="col-xs-3">
                            <input type="text" id="{{unitAmount}}" name="unitAmount" placeholder="用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{positionIds}}">物料位置 <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <select data-style="btn-info" class="selectpicker show-menu-arrow" data-width="100%"
                                    id="{{positionIds}}" name="positionIds" multiple placeholder="物料位置">"
                            </select>
                            <a href="javascript:openChildW('<%=path%>/system/material/position/list')" target="_blank">+</a>
                        </div>

                        <label class="col-xs-2  control-label " style="color: red" for="{{positionIdBl}}"> 所属归类 <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <select class="col-xs-12" data-style="btn-info" id="{{positionIdBl}}" name="isShow"
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
                        <label class="col-xs-2  control-label" for="{{attritionRate}}"> 损&nbsp;&nbsp;耗&nbsp;&nbsp;率 <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>

                        <div class="col-xs-3">
                            <input type="text" id="{{attritionRate}}" name="attritionRate"
                                   placeholder="损耗率，e.g.0.03" class="col-xs-10 col-sm-12"/>
                        </div>


                        <label class="col-xs-2  control-label" for="{{unitPrice}}" title="用量单位的价格"> 单价(￥) <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{unitPrice}}" name="unitPrice" placeholder="单价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>


                    </div>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="{{colorAmount}}" data-toggle="tooltip"
                               title="各色用量 =单位用量 *(1 + 损耗率)"> 各色用量 <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{colorAmount}}" name="colorAmount" placeholder="各色用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{colorPrice}}" title="各色单价 = 各色用量 * 单价">
                            各色单价(￥) <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{colorPrice}}" name="colorPrice" placeholder="各色单价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>

                    <div class="form-group">

                        <label class="col-xs-2  control-label" for="{{totalAmount}}" data-toggle="tooltip"
                               title="订单数量 * 各色用量"> 各色总用量 <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalAmount}}" name="totalAmount" placeholder="各色总用量"
                                   class="col-xs-10 col-sm-12"/>
                        </div>

                        <label class="col-xs-2  control-label" for="{{totalPrice}}" title="各色总用量的价格和 = 订单数量 *  各色单价 ">
                            总价(￥) <a href="javascript:openChildW('<%=path%>/system/product_type/list')" target="_blank">&nbsp;</a></label>
                        <div class="col-xs-3">
                            <input type="text" id="{{totalPrice}}" name="totalPrice" placeholder="总价(￥)"
                                   class="col-xs-10 col-sm-12"/>
                        </div>
                    </div>
                </div>

            </div>
        </form>

    </div>
    {{/each}}
</script>
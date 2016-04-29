<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="offerInfo">
    <div id="offerDescTitle">
        <h5 class="header smaller lighter blue">
            报价表
        </h5>
    </div>
    <div id="offerDescDetail" <%--style="display: none"--%>>
        <div class="form-group">
            <input type="hidden" name="spId" id="spId"/>
            <input type="hidden" name="fabricId" id="fabricId"/>
            <label class="col-xs-2  control-label" for="costing">成本核算（￥）</label>
            <div class="col-xs-3">
                <input type="text" id="costing" name="costing" placeholder="成本核算（￥）" class="col-xs-10 col-sm-12"/>
            </div>
            <label class="col-xs-2  control-label" for="exchangeCosts"> 换汇成本（小数形式） </label>
            <div class="col-xs-3">
                <input type="text" id="exchangeCosts" name="exchangeCosts" placeholder="换汇成本，e.g.0.65"
                       class="col-xs-10 col-sm-12"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-xs-2  control-label" for="factoryOffer"> 工厂报价（￥） </label>
            <div class="col-xs-3">
                <input type="text" id="factoryOffer" name="factoryOffer" placeholder="工厂报价"
                       class="col-xs-10 col-sm-12"/>
            </div>
            <label class="col-xs-2  control-label" for="euroPrice">欧元报价(€)</label>
            <div class="col-xs-3">
                <input type="text" id="euroPrice" name="euroPrice" placeholder="欧元报价(€)" class="col-xs-10 col-sm-12"/>
            </div>
        </div>

        <div class="form-group">
            <label class="col-xs-2  control-label" for="factoryMargins"> 工厂报价利润率</label>
            <div class="col-xs-3">
                <input type="text" id="factoryMargins" name="factoryMargins" placeholder=" 工厂报价利润率"
                       class="col-xs-10 col-sm-12"/>
            </div>
            <label class="col-xs-2  control-label" for="lpPrice"> 包装费(€) </label>
            <div class="col-xs-3">
                <input type="text" id="lpPrice" name="lpPrice" placeholder="包装费(€) " class="col-xs-10 col-sm-12"/>
            </div>
        </div>
    </div>
</div>
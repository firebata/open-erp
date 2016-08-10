<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="bomDesc">
    <div id="bomDescTitle">
        <h5 class="header smaller lighter blue">
            描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述
            <small></small>
            <input type="hidden" name="natrualkey" id="natrualkey" value="${natrualkey}"/>
            <input type="hidden" name="sexId" id="sexId" />
            <input type="hidden" name="projectId" id="projectId" />
            <input type="hidden" name="approveStatus" id="approveStatus" value="${approveStatus}"/>
            <input type="hidden" name="taskId" id="taskId" value="${taskId}"/>
            <input type="hidden" name="stateCode" id="stateCode" value="${stateCode}"/>
            <input type="hidden" name="processInstanceId" id="processInstanceId" value="${processInstanceId}"/>
        </h5>
    </div>
    <div id="bomDescDetail">
        <div class="form-group">
            <label class="col-xs-2  control-label" for="customerId" id="customerLableId"> 客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户 </label>
            <div class="col-xs-3">
                <select class="col-xs-12" data-style="btn-info" id="customerId" name="customerId"
                        placeholder="客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户" disabled="disabled">
                </select>
            </div>
            <label class="col-xs-2  control-label" for="areaId"> 区&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;域 </label>

            <div class="col-xs-3">
                <select class="col-xs-12" data-style="btn-info" id="areaId" name="areaId"
                        placeholder="区域" disabled="disabled">
                </select>
            </div>


        </div>

        <%-- <!-- #section:custom/extra.hr -->
     <div class="hr hr32 hr-dotted"></div>--%>
        <div class="form-group">
            <label class="col-xs-2  control-label" for="seriesId"> 系&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;列 </label>

            <div class="col-xs-3">
                <select class="col-xs-12" data-style="btn-info" id="seriesId" name="seriesId"
                        placeholder="系列" disabled="disabled">
                </select>
            </div>

            <label class="col-xs-2  control-label" for="collectionNum"> 款&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式 </label>

            <div class="col-xs-3">
                <input type="text" id="collectionNum" name="collectionNum" placeholder="款式"
                       class="col-xs-10 col-sm-12" disabled="disabled"/>
            </div>

        </div>

        <div class="form-group">

            <label class="col-xs-2  control-label" for="mainColor"> 主&nbsp;&nbsp;颜&nbsp;&nbsp;色 </label>

            <div class="col-xs-3" align="center">
                <input type="text" id="mainColor" name="mainColor" placeholder="主颜色"
                       class="col-xs-10 col-sm-10" disabled="disabled"/>
                <input type="hidden" name="mainColorOld" id="mainColorOld"/>
                <span class="glyphicon glyphicon-edit" id="mainColorEditBtnId"    onclick="javascript:$.mainColorEditInBom(this)"></span>
            </div>


            <label class="col-xs-2  control-label" for="offerAmount"> 订单数量 </label>

            <div class="col-xs-3">
                <input type="text" id="offerAmount" name="offerAmount" placeholder="订单数量"
                       class="col-xs-10 col-sm-12"/>
            </div>
        </div>
    </div>
</div>
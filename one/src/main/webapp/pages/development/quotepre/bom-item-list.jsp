<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="modal fade" id="editBomItemModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:1280px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <label class="col-xs-2  control-label"
                       style="font-size: 28px;color: #ff2013"> 面辅成本(￥):</label>
                <div class="col-xs-3" style="vertical-align:middle;">
                    <input type="number"  name="costing" placeholder="面辅成本(￥)" disabled="disabled" ng-model="costing" style="border:0px;font-size: 26px;color: #ff2013" class="col-xs-11 col-sm-10"/>
                </div>
                <label class="col-xs-2  control-label"
                       style="font-size: 28px;color: #ff2013"> 最终报价(€):</label>
                <div class="col-xs-3" style="vertical-align:middle;">
                    <input type="number"  name="quotedPrice" placeholder="最终报价(€) "  value="{{(((costing+ laborCost +costing * factoryMargins + laborCost * factoryMargins) / exchangeCosts + lpPrice) * (1 + commission/100)).toFixed(3)}}" style="border:0px;font-size: 26px;color: #ff2013" class="col-xs-10 col-sm-12" />
                </div>
            </div>
            <form id="defaultForm" method="post" class="form-horizontal" action="edit">
                <div class="modal-body">
                    <div class="tabbable">
                        <ul class="nav nav-tabs" id="myBomTab">
                            <li class="active">
                                <a data-toggle="tab" href="#面料">
                                    <i class="blue icon-user bigger-110"></i>
                                    面料
                                </a>
                            </li>
                            <li>
                                <a data-toggle="tab" href="#辅料">
                                    <i class="icon-rocket"></i>
                                    辅料
                                </a>
                            </li>
                            <li>
                                <a data-toggle="tab" href="#包装材料">
                                    <i class="icon-rocket"></i>
                                    包材
                                </a>
                            </li>
                        </ul>

                        <div class="tab-content">
                            <div id="面料" class="tab-pane  in active">
                                <%@ include file="include/item-fabric.jsp" %>
                            </div>
                            <div id="辅料" class="tab-pane">
                                <%@ include file="include/item-accessories.jsp" %>
                            </div>
                            <div id="包装材料" class="tab-pane">
                                <%@ include file="include/item-packaging.jsp" %>
                            </div>
                        </div>

                    </div>

                </div>
                <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>--%>
                <%--<button type="button" class="btn btn-info" id="save">确认</button>--%>
                <%--</div>--%>
            </form>
        </div>
    </div>
</div>
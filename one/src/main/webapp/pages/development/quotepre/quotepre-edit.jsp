<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>预报价</title>
    <jsp:include page="../../base/hb-headc.jsp"></jsp:include>
    <jsp:include page="../../base/upload.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/ace.css" class="ace-main-stylesheet"
          id="main-ace-style"/>
</head>
<body ng-app="preQuoteApp" ng-controller="preQuoteCtr" ng-init="">
<div class="breadcrumbs" id="breadcrumbs">
    <ol class="breadcrumb">
        <li><a href="#">开发</a></li>
        <li><a href="#">预报价</a></li>
        <li class="active">新增/修改</li>
    </ol>
</div>
<div class="page-content">
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <form class="form-horizontal" role="form" id="quotepreForm">

                <div id="quotepreDesc">
                    <div id="quotepreDescTitle">
                        <h5 class="header smaller lighter blue">
                            报价信息
                            <small></small>
                            <input type="hidden" name="natrualkey" id="natrualkey" value="${natrualkey}"/>
                            <input type="hidden" name="approveStatus" id="approveStatus"
                                   value="${quotedInfo.approveStatus}"/>
                            <input type="hidden" name="taskId" id="taskId" value="${taskId}"/>
                            <input type="hidden" name="stateCode" id="stateCode" value="${quotedInfo.stateCode}"/>
                            <input type="hidden" name="processInstanceId" id="processInstanceId"
                                   value="${processInstanceId}"/>
                            <input type="hidden" name="spId" id="spId"/>
                            <input type="hidden" name="fabricId" id="fabricId"/>
                        </h5>
                    </div>
                    <div id="quotepreDescDetail">
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="projectName"> 项&nbsp;&nbsp;目&nbsp;&nbsp;名&nbsp;&nbsp;称 </label>
                            <div class="col-xs-3">
                                <input type="text" id="projectName" name="projectName" placeholder="项目名称"
                                       class="col-xs-10 col-sm-12" value="${quotedInfo.projectName}"
                                       disabled="disabled"/>
                            </div>
                            <label class="col-xs-2  control-label" for="bomName">款&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式</label>
                            <div class="col-xs-3">
                                <input type="text" id="bomName" name="bomName" placeholder="款式"
                                       class="col-xs-10 col-sm-12" value="${quotedInfo.bomName}" disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="spName"> 成&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;衣&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;厂 </label>
                            <div class="col-xs-3">
                                <input type="text" id="spName" name="spName" placeholder="成衣厂"
                                       class="col-xs-10 col-sm-12" value="${quotedInfo.spName}" disabled="disabled"/>
                            </div>

                            <label class="col-xs-2  control-label" for="costing">面辅成本(￥)</label>
                            <div class="col-xs-3">
                                <input type="number" id="costing" name="costing" placeholder="面辅成本(￥)"
                                       ng-model="costing"
                                       class="col-xs-11 col-sm-10"
                                />
                                <button type="button" id="editBomBtn" class="btn btn-info col-xs-1 col-sm-2"
                                        data-toggle="modal"
                                        data-target="#editBomItemModal">修改
                                </button>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="laborCost">工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费</label>
                            <div class="col-xs-3">
                                <input type="number" id="laborCost" name="laborCost" placeholder="工费，e.g.0.15"
                                       ng-model="laborCost" class="col-xs-10 col-sm-12"/>
                            </div>
                            <label class="col-xs-2  control-label" for="factoryMargins"> 工厂利润率</label>
                            <div class="col-xs-3">
                                <input type="number" id="factoryMargins" name="factoryMargins"
                                       placeholder="工厂利润率，e.g.0.15" ng-model="factoryMargins"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="factoryOffer"> 工厂报价(￥) </label>
                            <div class="col-xs-3">
                                <input type="text" id="factoryOffer" name="factoryOffer" placeholder="工厂报价"
                                       class="col-xs-10 col-sm-12"
                                       value="{{((laborCost + costing) * (1 + factoryMargins)).toFixed(2)}}"
                                       disabled="disabled"/>
                            </div>

                            <label class="col-xs-2  control-label" for="exchangeCosts">
                                换&nbsp;&nbsp;汇成&nbsp;&nbsp;本</label>
                            <div class="col-xs-3">
                                <input type="number" id="exchangeCosts" name="exchangeCosts" placeholder="换汇成本，e.g.0.65"
                                       ng-model="exchangeCosts" class="col-xs-10 col-sm-12"/>
                            </div>

                            <%--  <label class="col-xs-2  control-label" for="euroPrice">欧元报价(€)</label>
                              <div class="col-xs-3">
                                  <input type="number" id="euroPrice" name="euroPrice" placeholder="欧元报价(€)" class="col-xs-10 col-sm-12"  ng-model="euroPrice"  ng-bind="factoryOffer / exchangeCosts"  ng-init="euroPrice =${quotedInfo.euroPrice==null?0:quotedInfo.euroPrice}"/>
                              </div>--%>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="lpPrice"> 包&nbsp;&nbsp;&nbsp;&nbsp;装&nbsp;&nbsp;费(€) </label>
                            <div class="col-xs-3">
                                <input type="number" id="lpPrice" name="lpPrice" placeholder="包装费(€) "
                                       ng-model="lpPrice" class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="col-xs-2  control-label" for="exchangeCosts"> 佣金百分比</label>
                            <div class="col-xs-3">
                                <input type="number" id="commission" name="commission" placeholder="佣金，e.g.3"
                                       ng-model="commission" class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label"> </label>
                            <div class="col-xs-3"></div>
                            <label class="col-xs-2  control-label" for="quotedPrice"
                                   style="font-size: 30px;color: #ff2013"> 最终报价(€):</label>
                            <div class="col-xs-3" style="vertical-align:middle;">
                                <input type="text" id="quotedPrice" name="quotedPrice" placeholder="最终报价(€) "
                                       style="border:0px;font-size: 30px;color: #ff2013" class="col-xs-10 col-sm-12"
                                       ng-model="quotedPrice"/>
                                <%--value="{{(((costing + laborCost + costing * factoryMargins + laborCost * factoryMargins) / exchangeCosts + lpPrice) * (1 + commission/100)).toFixed(3)}}--%>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="btnInfo">
                </div>
            </form>
        </div>
        <!-- Button trigger modal -->
    </div>
</div>
</body>
</html>
<jsp:include page="bom-item-list.jsp"></jsp:include>
<jsp:include page="../../base/hb-footj.jsp"></jsp:include>
<script type="text/javascript" src="<%=path%>/resources/js/angular/angular.min.js"></script>
<%--<script type="text/javascript" src="<%=path%>/resources/js/interfaces/development/quotepre/quotepre-edit.js"></script>--%>
<script type="text/javascript">

    var preQuoteApp = angular.module('preQuoteApp', []);
    preQuoteApp.controller('preQuoteCtr', function ($scope, $http) {
        $scope.costing = ${quotedInfo.costing==null?0:quotedInfo.costing};
        $scope.laborCost = ${quotedInfo.laborCost==null?0:quotedInfo.laborCost};
        $scope.factoryMargins = ${quotedInfo.factoryMargins==null?0:quotedInfo.factoryMargins};
        $scope.exchangeCosts = ${quotedInfo.exchangeCosts==null?0:quotedInfo.exchangeCosts};
        $scope.lpPrice = ${quotedInfo.lpPrice==null?0:quotedInfo.lpPrice};
        $scope.commission = ${quotedInfo.commission==null?0:quotedInfo.commission};
        $scope.quotedPrice= ((($scope.costing + $scope.laborCost + $scope.costing * $scope.factoryMargins + $scope.laborCost * $scope.factoryMargins) / $scope.exchangeCosts + $scope.lpPrice) * (1 + $scope.commission/100)).toFixed(3);
        var url = "/development/quotepre/qr_items_of_bom/" + $("#natrualkey").val();
        $http.get(url).success(function (data) {
            $scope.fabricsInfos = data.fabricsInfos;
            $scope.accessoriesInfos = data.accessoriesInfos;
            $scope.packagingInfos = data.packagingInfos;
        });
        $scope.change = function (row,$event) {




            console.info($event);


        };
    });
    preQuoteApp.filter("fixed", function () {
        return function (input, len) {
            var length = len == null ? 3 : len;
            return input.toFixed(length);
        }
    });


    /*$(function () {


     $("table tr td").on("change", "input", function () {
     var costingTemp = $("#costing").val();//最初的面辅料成本
     var $tr = $(this).parent().parent();
     var unitAmount = $tr.find("td:eq(2) input").val();
     var $unitPrice = $tr.find("td:eq(3) input");
     var $colorPrice = $tr.find("td:eq(4) input");

     var unitPrice = $unitPrice.val();
     var colorPriceTemp = $colorPrice.val();
     var colorPrice = (unitAmount * unitPrice).toFixed(3);
     $colorPrice.val(colorPrice);
     var costing = (parseFloat(costingTemp) + (colorPrice - colorPriceTemp)).toFixed(3);
     //            var scope = angular.element(document.querySelector('#costing')).scope();
     var scope = $('#costing').scope();
     scope.costing = costing;
     //                $scope.costing = costing;
     scope.$apply();
     })


     });*/


</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改BOM信息</title>
    <jsp:include page="../../base/common.jsp" flush="true"></jsp:include>
    <jsp:include page="../../base/commonTable.jsp" flush="true"></jsp:include>
    <jsp:include page="../../base/headResources.jsp"></jsp:include>
    <jsp:include page="../../base/upload.jsp"></jsp:include>
    <script language="javascript" type="text/javascript"
            src="<%=path%>/resources/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="<%=path%>/resources/css/page-header.css"/>
    <link rel="stylesheet" href="<%=path%>/resources/css/font-awesome.min.css"/>
</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
        try {
            ace.settings.check('breadcrumbs', 'fixed')
        } catch (e) {

        }
    </script>

    <ul class="breadcrumb">
        <li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">开发</a>
        </li>

        <li><a href="#">BOM</a></li>
        <li class="active">新增和修改</li>
    </ul>
    <!-- /.breadcrumb -->
    <!-- /section:basics/content.searchbox -->
</div>

<div class="page-content">
    <%--<%@ include file="base/ace-setting.jsp"%>--%>

    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <form class="form-horizontal" role="form">
                <div id="bomDesc">
                    <div id="bomDescTitle">
                        <h5 class="header smaller lighter blue">
                            描述
                            <small></small>
                            <input type="hidden" name="natrualkey" id="natrualkey" value="${natrualkey}"/>
                        </h5>
                    </div>
                    <div id="bomDescDetail">
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="customerId" id="customerLableId"> 客户 </label>

                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="customerId" name="customerId"
                                        placeholder="客户" disabled="disabled">
                                    <option value="1" selected>客户</option>
                                </select>
                            </div>
                            <label class="col-xs-2  control-label" for="areaId"> 区域 </label>

                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="areaId" name="areaId"
                                        placeholder="区域" disabled="disabled">
                                    <option value="1" selected>区域</option>
                                </select>
                            </div>


                        </div>

                        <%-- <!-- #section:custom/extra.hr -->
                     <div class="hr hr32 hr-dotted"></div>--%>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="seriesId"> 系列 </label>

                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="seriesId" name="seriesId"
                                        placeholder="系列" disabled="disabled">
                                    <option value="1" selected>系列</option>
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="collectionNum"> 款式 </label>

                            <div class="col-xs-3">
                                <input type="text" id="collectionNum" name="collectionNum" placeholder="款式"
                                       class="col-xs-10 col-sm-12" disabled="disabled"/>
                            </div>

                        </div>

                        <div class="form-group">

                            <label class="col-xs-2  control-label" for="mainColor"> 主颜色 </label>

                            <div class="col-xs-3">
                                <input type="text" id="mainColor" name="mainColor" placeholder="主颜色"
                                       class="col-xs-10 col-sm-12" disabled="disabled"/>
                            </div>


                            <label class="col-xs-2  control-label" for="offerAmount"> 订单数量 </label>

                            <div class="col-xs-3">
                                <input type="text" id="offerAmount" name="offerAmount" placeholder="订单数量"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                        </div>

                        <%--<div class="form-group">--%>
                        <%--<label class="col-xs-2  control-label" for="fabricsEndDate"> 面料交货时间 </label>--%>

                        <%--<div class="col-xs-3">--%>
                        <%--<input type="text" id="fabricsEndDate" name="fabricsEndDate" placeholder="面料交货时间"--%>
                        <%--class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>--%>
                        <%--</div>--%>

                        <%--<label class="col-xs-2  control-label" for="accessoriesEndDate"> 辅料交货时间 </label>--%>

                        <%--<div class="col-xs-3">--%>
                        <%--<input type="text" id="accessoriesEndDate" name="accessoriesEndDate"--%>
                        <%--placeholder="辅料交货时间" class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>--%>
                        <%--</div>--%>


                        <%--</div>--%>

                        <%--<div class="form-group">--%>
                        <%--<label class="col-xs-2  control-label" for="preOfferDate"> 成衣报价时间 </label>--%>

                        <%--<div class="col-xs-3">--%>
                        <%--<input type="text" id="preOfferDate" name="preOfferDate" placeholder="成衣报价时间"--%>
                        <%--class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>--%>
                        <%--</div>--%>

                        <%--<label class="col-xs-2  control-label" for="clothReceivedDate"> 成衣收到时间 </label>--%>

                        <%--<div class="col-xs-3">--%>
                        <%--<input type="text" id="clothReceivedDate" name="clothReceivedDate" placeholder="成衣收到时间"--%>
                        <%--class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>--%>
                        <%--</div>--%>

                        <%--</div>--%>


                    </div>
                </div>
                <div id="fabricsInfo">
                    <h5 class="header smaller lighter blue">
                        面料
                        <span class="glyphicon glyphicon-plus-sign blue" alt="增加面料" id="imgAddFabric"></span>
                    </h5>

                    <div id="fabricsItemInfo">

                    </div>
                    <%--<div class="hr hr32 hr-dotted"></div>--%>
                </div>
                <div id="accessoriesInfo">
                    <h5 class="header smaller lighter blue">
                        辅料
                        <%--<small>+</small><img src="<%=path%>/resources/images/add.png" alt="增加辅料"   id="imgAddAccessories">--%>
                        <span class="glyphicon glyphicon-plus-sign blue" alt="增加辅料" id="imgAddAccessories"></span>
                    </h5>

                    <div id="accessoriesItemInfo">


                    </div>

                </div>
                <div id="packagingInfo">
                    <h5 class="header smaller lighter blue">
                        包材
                        <span class="glyphicon glyphicon-plus-sign blue" alt="增加包装材料" id="imgAddPackaging"></span>
                    </h5>
                    <div id="packagingItemInfo">


                    </div>
                </div>
                <div id="factoryInfo">
                    <h5 class="header smaller lighter blue">
                        成衣厂
                        <span class="glyphicon glyphicon-plus-sign blue" alt="增加成衣厂" id="imgAddFactory"></span>
                    </h5>
                    <div id="factoryItemInfo">

                    </div>
                </div>
                <div id="offerInfo">
                    <div id="offerDescTitle">
                        <h5 class="header smaller lighter blue">
                            报价表
                        </h5>
                    </div>
                    <div id="offerDescDetail" style="display: none">
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="factoryOffer"> 工厂报价 </label>
                            <div class="col-xs-3">
                                <input type="text" id="factoryOffer" name="factoryOffer" placeholder="工厂报价"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="col-xs-2  control-label" for="factoryMargins"> 工厂报价利润率</label>
                            <div class="col-xs-3">
                                <input type="text" id="factoryMargins" name="factoryMargins" placeholder=" 工厂报价利润率"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                        </div>


                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="lpPrice"> 包装费(€) </label>
                            <div class="col-xs-3">
                                <input type="text" id="lpPrice" name="lpPrice" placeholder="包装费"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="col-xs-2  control-label" for="euroPrice">工厂欧元报价(€)</label>
                            <div class="col-xs-3">
                                <input type="text" id="euroPrice" name="euroPrice" placeholder="工厂欧元报价(€)"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="exchangeCosts"> 换汇成本 </label>
                            <div class="col-xs-3">
                                <input type="text" id="exchangeCosts" name="exchangeCosts" placeholder="换汇成本"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="col-xs-2  control-label" for="costing">成本核算</label>
                            <div class="col-xs-3">
                                <input type="text" id="costing" name="costing" placeholder="成本核算"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                        </div>
                    </div>


                </div>
                <div id="bomBtnInfo">
                    <div class="col-xs-offset-2 col-xs-2">
                        <button type="button" class="btn btn-info btn-md" onclick="javascript:$.bomAutoBackup()"
                                id="autoBackupId">暂存
                        </button>
                    </div>
                    <div class="col-xs-2">
                        <button type="button" class="btn btn-info btn-md" onclick="javascript:$.bomSave()"
                                id="saveBtnId">保存
                        </button>
                    </div>
                    <div class="col-xs-2">
                        <button type="button" class="btn btn-info btn-md" onclick="javascript:$.bomSubmit()"
                                id="commitBtnId">提交
                        </button>
                    </div>
                </div>
            </form>

        </div>
        <!-- Button trigger modal -->
    </div>
    <%--<%@ include file="../../system/material/bom-material-list.jsp" %>--%>
</div>
<jsp:include page="../../base/footCommon.jsp"></jsp:include>
<jsp:include page="../../base/aceFoot.jsp"></jsp:include>
<%@ include file="bom-fabric.jsp" %>
<%@ include file="bom-accessories.jsp" %>
<%@ include file="bom-packaging.jsp" %>
<%@ include file="bom-factory.jsp" %>
</body>


</html>
<script type="text/javascript" src='<%=path%>/resources/js/fileinput.js'></script>
<script type="text/javascript" src='<%=path%>/resources/js/fileinput_locale_zh.js'></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-add.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-desc.js?v=<%=version%>"></script>
<script type="text/javascript"
        src="<%=path%>/resources/interfaces/development/bom/bom-fabric.js?v=<%=version%>"></script>
<script type="text/javascript"
        src="<%=path%>/resources/interfaces/development/bom/bom-accessories.js?v=<%=version%>"></script>
<script type="text/javascript"
        src="<%=path%>/resources/interfaces/development/bom/bom-packaging.js?v=<%=version%>"></script>
<script type="text/javascript"
        src="<%=path%>/resources/interfaces/development/bom/bom-factory.js?v=<%=version%>"></script>
<script type="text/javascript"
        src="<%=path%>/resources/interfaces/development/bom/bom-quoted.js?v=<%=version%>"></script>
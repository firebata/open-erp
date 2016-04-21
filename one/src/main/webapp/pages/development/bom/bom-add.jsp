<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改BOM信息</title>
    <jsp:include page="../../base/hb-headc.jsp"></jsp:include>
    <jsp:include page="../../base/upload.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/ace.css" class="ace-main-stylesheet"      id="main-ace-style"/>
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
                <div class="tabbable">
                    <ul class="nav nav-tabs" id="myBomTab">
                        <li class="active">
                            <a data-toggle="tab" href="#描述">
                                <i class="pink icon-dashboard bigger-110"></i>
                                描述
                            </a>
                        </li>
                        <li>
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
                        <li>
                            <a data-toggle="tab" href="#成衣厂">
                                <i class="icon-rocket"></i>
                                成衣
                            </a>
                        </li>
                        <li>
                            <a data-toggle="tab" href="#成衣指示单">
                                <i class="icon-rocket"></i>
                                成衣指示单
                            </a>
                        </li>
                        <li>
                            <a data-toggle="tab" href="#报价">
                                <i class="icon-rocket"></i>
                                报价
                            </a>
                        </li>
                        <li>
                            <a data-toggle="tab" href="#处理">
                                <i class="icon-rocket"></i>
                                处理
                            </a>
                        </li>
                    </ul>

                    <div class="tab-content">
                        <div id="描述" class="tab-pane in active">
                            <%@ include file="include/bom-desc.jsp" %>
                        </div>
                        <div id="面料" class="tab-pane">
                            <%@ include file="include/bom-fabric.jsp" %>
                        </div>
                        <div id="辅料" class="tab-pane">
                            <%@ include file="include/bom-accessories.jsp" %>
                        </div>
                        <div id="包装材料" class="tab-pane">
                            <%@ include file="include/bom-packaging.jsp" %>
                        </div>
                        <div id="成衣厂" class="tab-pane">
                            <%@ include file="include/bom-factory.jsp" %>
                        </div>
                        <div id="成衣指示单" class="tab-pane">
                            <%@ include file="include/bom-productinst.jsp" %>
                        </div>
                        <div id="报价" class="tab-pane">
                            <%@ include file="include/bom-offer.jsp" %>
                        </div>
                        <div id="处理" class="tab-pane">
                            <%@ include file="include/bom-handle.jsp" %>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <!-- Button trigger modal -->
    </div>
    <%--<%@ include file="../../system/material/bom-material-list.jsp" %>--%>
</div>
</body>
</html>
<jsp:include page="../../base/hb-footj.jsp"></jsp:include>
<script type="text/javascript" src='<%=path%>/resources/js/fileinput.js'></script>
<script type="text/javascript" src='<%=path%>/resources/js/fileinput_locale_zh.js'></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-add.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-desc.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-fabric.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-accessories.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-packaging.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-factory.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-quoted.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/development/bom/bom-productinst.js?v=<%=version%>"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="base/path.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title>翊凯-供应链管理系统</title>
    <meta name="description" content="overview &amp; stats"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <link rel="shortcut icon" href="<%=path%>/resources/images/favicon.ico" type="image/x-icon">
    <meta name="keywords" content="skysport,翊凯"/>
    <meta name="description" content="skysport,翊凯"/>
    <jsp:include page="base/hb-headc.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/ace.min.css" class="ace-main-stylesheet"
          id="main-ace-style"/>
</head>

<body class="no-skin">
<%@ include file="top.jsp" %>
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>


    <%--<div class="sidebar-shortcuts" id="sidebar-shortcuts">
        <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
            <button class="btn btn-success">
                <i class="ace-icon fa fa-signal"></i>
            </button>

            <button class="btn btn-info">
                <i class="ace-icon fa fa-pencil"></i>
            </button>

            <!-- #section:basics/sidebar.layout.shortcuts -->
            <button class="btn btn-warning">
                <i class="ace-icon fa fa-users"></i>
            </button>

            <button class="btn btn-danger">
                <i class="ace-icon fa fa-cogs"></i>
            </button>

            <!-- /section:basics/sidebar.layout.shortcuts -->
        </div>

        <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
            <span class="btn btn-success"></span>

            <span class="btn btn-info"></span>

            <span class="btn btn-warning"></span>

            <span class="btn btn-danger"></span>
        </div>
    </div><!-- /.sidebar-shortcuts -->--%>

    <!-- #section:basics/sidebar.layout.minimize -->


    <%@ include file="sidebar.jsp" %>
    <div class="main-content">
        <iframe id="mainIframe" src="main-content" width="100%"></iframe>
    </div>
    <!-- /.main-content -->


</div>
<!-- /.main-container -->

<!-- basic scripts -->


</body>
</html>
<jsp:include page="base/hb-footj.jsp"></jsp:include>
<script type="text/javascript" language="javascript">

    $(function () {
//				//等待iframe加载完成，才
        $("#mainIframe").load(function () {
            $("#mainIframe").height($(window.frames["mainIframe"]).contents().height());
        });


    });

</script>
<!--控制菜单显示-->
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/sidebar.js"></script>


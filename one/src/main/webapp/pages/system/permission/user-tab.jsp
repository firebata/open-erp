<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="../../base/common.jsp" flush="true"></jsp:include>
    <jsp:include page="../../base/commonTable.jsp" flush="true"></jsp:include>
    <link rel="stylesheet" href="<%=path%>/resources/css/zTree/zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="<%=path%>/resources/js/zTree/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/zTree/jquery.ztree.excheck-3.5.min.js"></script>
    <link rel="stylesheet" href="<%=path%>/resources/css/page-header.css"/>
</head>
<body>
<div class="page-content">
    <div class="page-header">
        <h1>
            系统管理
            <small><i class="icon-double-angle-right"></i> 用户&角色&权限</small>
            <input type="hidden" name="tabNo" id="tabNo" value="${tabNo}"/>
        </h1>
    </div>


    <ul class="nav nav-tabs" id="myTab">
        <li class="active"><a href="#userinfo">用户信息</a></li>
        <li><a href="#roleinfo">角色信息</a></li>
        <li><a href="#userrole">角色分配</a></li>
        <li><a href="#permission">权限分配</a></li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane active" id="userinfo">
            <%@ include file="user-list.jsp" %>
        </div>
        <div class="tab-pane" id="roleinfo">
            <%@ include file="role-list.jsp" %>
        </div>
        <div class="tab-pane" id="userrole">
            <%@ include file="role-user.jsp" %>
        </div>
        <div class="tab-pane" id="permission">
            <%@ include file="permission.jsp" %>
        </div>
    </div>
</div>
<script>
    $(function () {
//        $('#myTab a').eq(-2).tab('show');//初始化显示哪个tab
//        $('#myTab a:last').tab('show');
        var tabNo = $("#tabNo").val();
        $('#myTab a').eq(tabNo).tab('show');//初始化显示哪个tab
        $('#myTab a').click(function (e) {
            e.preventDefault();//阻止a链接的跳转行为
            $(this).tab('show');//显示当前选中的链接及关联的content
        })
    })
</script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>权限管理</title>
    <jsp:include page="../../base/hb-headc.jsp"></jsp:include>
    <link rel="stylesheet" href="<%=path%>/resources/css/zTree/zTreeStyle/zTreeStyle.css" type="text/css">
    <jsp:include page="../../base/hb-footj.jsp"></jsp:include>
    <script type="text/javascript" src="<%=path%>/resources/js/zTree/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="<%=path%>/resources/js/zTree/jquery.ztree.excheck-3.5.min.js"></script>
</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">
    <ol class="breadcrumb">
        <li><a href="#">系统管理</a></li>
        <li><a href="#">用户&角色&权限</a></li>
        <li class="active">列表</li>
    </ol>
</div>
<div class="page-content">
    <input type="hidden" name="tabNo" id="tabNo" value="${tabNo}"/>

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
    function loadEachData($this, tabNo) {
        var idVal = $this.attr("id");
        if(idVal=='userinfo'){
            $.loadUserList();
        }
        else if(idVal=='roleinfo'){
            $.loadRoleList();
        }
        else if(idVal=='userrole'){

        }
        else if(idVal=='userinfo'){

        }
    }
    $(function () {
//        $('#myTab a').eq(-2).tab('show');//初始化显示哪个tab
//        $('#myTab a:last').tab('show');
        var tabNo = $("#tabNo").val();
        $('#myTab a').eq(tabNo).tab('show');//初始化显示哪个tab

//        if(tabNo==0){
//            $.loadUserList();
//        }
        $('#myTab a').click(function (e) {
            var $this =$(this);
            e.preventDefault();//阻止a链接的跳转行为
            $this.tab('show');//显示当前选中的链接及关联的content
//            loadEachData($this,tabNo);
        })
    })
</script>
</body>
</html>

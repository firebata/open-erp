<%@ page import="com.skysport.interfaces.constant.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" class="no-js">
<head>
    <title>翊凯供应链</title>
    <meta name="keywords" content="翊凯供应链"/>
    <meta name="description" content="翊凯供应链系统登录页面"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="zhangjh">
    <!-- CSS -->
    <link rel="shortcut icon" href="<%=path%>/resources/images/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/bootstrap.css"/>
    <link rel="stylesheet" href="<%=path%>/resources/css/reset.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/supersized.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/style.css">
</head>

<body>

<div class="page-container">
    <h1>Supply Chain Management</h1>
    <form action="<%=path%>/login" method="post">
        <% if (environment_current.equals(WebConstants.SDE)) {%>
        <input type="text" name="username" id="username" class="username" value="admin">
        <input type="password" name="password" class="password" value="123456">
        <%} else {%>
        <input type="text" name="username" id="username" class="username" placeholder="Skysport ID*">
        <input type="password" name="password" class="password" placeholder="Password*">
        <%}%>
        <button type="submit">Sign in</button>
        <div class="error">
            <span>${msg}</span>
        </div>

        <div class="has-error">
            ${msg}
        </div>
    </form>
    <!-- 	<div class="connect">
        <p>Or connect with:</p>
        <p>
            <a class="facebook" href=""></a> <a class="twitter" href=""></a>
        </p>
    </div> -->
</div>
<script type="text/javascript">
    var path = '<%=path%>';
    //    $.cookie('path',path)_;
</script>
<script src="<%=path%>/resources/js/jquery/jquery-2.1.3.min.js"></script>
<script src="<%=path%>/resources/js/supersized.3.2.7.min.js"></script>
<script src="<%=path%>/resources/js/supersized-init.js"></script>
<script src="<%=path%>/resources/js/scripts.js"></script>

</body>

</html>
<script type="text/javascript">

    $(function () {
        $("#username").focus();
    })

</script>
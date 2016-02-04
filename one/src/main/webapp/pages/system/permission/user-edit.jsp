<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新增/修改用户信息</title>
    <jsp:include page="../../base/common.jsp" flush="true"></jsp:include>
    <jsp:include page="../../base/commonTable.jsp" flush="true"></jsp:include>
    <jsp:include page="../../base/headResources.jsp"></jsp:include>
    <jsp:include page="../../base/upload.jsp"></jsp:include>
    <script language="javascript" type="text/javascript"
            src="<%=path%>/resources/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="<%=path%>/resources/css/page-header.css"/>
</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">系统管理</a>
        </li>
        <li><a href="#">用户信息</a></li>
        <li class="active">新增和修改</li>
    </ul>
</div>

<div class="page-content">
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <form class="form-horizontal" role="form" id="userinfoForm">
                <div id="userDesc" class="row">
                    <div id="userDescTitle">
                        <h5 class="header smaller lighter blue">
                            基本信息
                            <input type="hidden" name="natrualkey" id="natrualkey" value="${natrualkey}"/>
                        </h5>
                    </div>
                    <div id="userDescDetail">
                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="name">用户名</label>

                            <div class="col-xs-12 col-sm-4">
                                <input type="text" id="name" name="name"
                                       placeholder="用户名" class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="aliases"> 用户别名 </label>

                            <div class="col-xs-12 col-sm-4">
                                <input type="text" id="aliases" name="aliases" placeholder="用户别名"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="userType"> 用户类型</label>

                            <div class="col-xs-12 col-sm-4">
                                <select class="col-xs-12" data-style="btn-info" id="userType" name="userType"
                                        placeholder="用户类型">
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="userEmail"> 用户邮箱</label>

                            <div class="col-xs-12 col-sm-4">
                                <input type="text" id="userEmail" name="userEmail" placeholder="用户邮箱"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="userMobile"> 用户手机</label>

                            <div class="col-xs-12 col-sm-4">
                                <input type="text" id="userMobile" name="userMobile" placeholder="用户手机"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>


                    </div>
                </div>
                <div id="avatars" class="row">
                    <h5 class="header smaller lighter blue">
                        头像信息
                    </h5>

                    <div class="row">
                        <label class="col-xs-7  col-sm-4  control-label">上传头像</label>
                        <div class="col-xs-12 col-sm-4">
                            <input id="fileLocation" type="file" class="file-loading col-xs-12 col-sm-4"
                                   name="fileLocation">
                        </div>
                    </div>

                    <div class="row">
                        <label class="col-xs-7  col-sm-4  control-label">文件列表</label>
                        <div class="col-xs-12 col-sm-4" >
                            <ul id="avatarsList">

                            </ul>
                        </div>
                    </div>
                </div>
                <hr/>
                <div id="userBtnInfo" class="row">
                    <div class="col-xs-offset-6 col-xs-9">
                        <button type="button" class="btn btn-info btn-md" onclick="javascript:$.saveuserinfo()">保存
                        </button>
                    </div>
                </div>
            </form>

        </div>
        <!-- Button trigger modal -->
    </div>
</div>

</body>
</html>
<script type="text/javascript" src='<%=path%>/resources/js/fileinput.js'></script>
<script type="text/javascript" src='<%=path%>/resources/js/fileinput_locale_zh.js'></script>
<script type="text/javascript"
        src="<%=path%>/resources/interfaces/system/permission/userinfo-edit.js?v=<%=version%>"></script>
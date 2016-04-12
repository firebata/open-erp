<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新增/修改角色信息</title>
    <jsp:include page="../../base/hb-headc.jsp"></jsp:include>
    <jsp:include page="../../base/upload.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/ace.css" class="ace-main-stylesheet"
          id="main-ace-style"/>
</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">系统管理</a>
        </li>
        <li><a href="#">角色信息</a></li>
        <li class="active">新增和修改</li>
    </ul>
</div>

<div class="page-content">
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <form class="form-horizontal" role="form" id="roleinfoForm">
                <div id="roleDescDiv" class="row">
                    <div id="roleDescTitle">
                        <h5 class="header smaller lighter blue">
                            基本信息
                            <input type="hidden" name="natrualkey" id="natrualkey" value="${natrualkey}"/>
                        </h5>
                    </div>
                    <div id="roleDescDetail">
                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="name">角色名</label>
                            <div class="col-xs-12 col-sm-4">
                                <input type="text" id="name" name="name" placeholder="角色名" class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="parentId">父角色</label>
                            <div class="col-xs-12 col-sm-4">
                                <select class="col-xs-12" data-style="btn-info" id="parentId" name="parentId"
                                        placeholder="父角色"></select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="deptId">部门</label>
                            <div class="col-xs-12 col-sm-4">
                                <select class="col-xs-12" data-style="btn-info" id="deptId" name="deptId"
                                        placeholder="部门">
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="roleDesc"> 角色描述</label>
                            <div class="col-xs-12 col-sm-4">
                                <input type="text" id="roleDesc" name="roleDesc" placeholder="角色描述"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-7  col-sm-4  control-label" for="remark"> 备注</label>
                            <div class="col-xs-12 col-sm-4">
                                <input type="text" id="remark" name="remark" placeholder="备注"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                    </div>
                </div>

                <hr/>
                <div id="roleBtnInfo" class="row">
                    <div class="col-xs-offset-6 col-xs-9">
                        <button type="button" class="btn btn-info btn-md" onclick="javascript:$.saveroleinfo()">保存
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
<jsp:include page="../../base/hb-footj.jsp"></jsp:include>
<script type="text/javascript"
        src="<%=path%>/resources/interfaces/system/permission/roleinfo-edit.js?v=<%=version%>"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查询区域信息</title>
    <jsp:include page="../../base/common.jsp" flush="true"></jsp:include>
    <jsp:include page="../../base/commonTable.jsp" flush="true"></jsp:include>
    <link rel="stylesheet" href="<%=path%>/resources/css/page-header.css"/>
</head>
<body>
<div class="page-content">
    <%--<%@ include file="base/ace-setting.jsp"%>--%>
    <div class="page-header">
        <h1>
            系统管理
            <small><i class="icon-double-angle-right"></i> 部门信息</small>
        </h1>
    </div>
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <table id="example" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>部门名称</th>
                    <th>部门负责人</th>
                    <th>所属公司</th>
                    <th>上级部门</th>
                    <th>所在地区</th>
                    <th>所在国家</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
                <!-- tbody是必须的 -->
            </table>
        </div>
        <!-- Button trigger modal -->
    </div>

    <%@ include file="dept-add.jsp" %>
</div>
<jsp:include page="../../base/footCommon.jsp"></jsp:include>
<!--定义操作列按钮模板-->
<script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
</script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/system/dept/dept.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/system/list.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/interfaces/system/edit.js?v=<%=version%>"></script>
</body>

</html>
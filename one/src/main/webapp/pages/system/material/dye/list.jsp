<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查询染色方式的信息</title>
    <jsp:include page="../../../base/hb-headc.jsp" flush="true"></jsp:include>
    <jsp:include page="../../../base/hb-footj.jsp"></jsp:include>
</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">
    <ol class="breadcrumb">
        <li><a href="#">系统管理</a></li>
        <li><a href="#">染色方式</a></li>
        <li class="active">列表</li>
    </ol>
</div>
<div class="page-content">
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <table id="example" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th style="text-align: center">名称</th>
                    <th style="text-align: center">备注</th>
                    <th style="text-align: center">最后修改时间</th>
                    <th style="text-align: center">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
                <!-- tbody是必须的 -->
            </table>
        </div>
        <!-- Button trigger modal -->
    </div>

    <%@ include file="edit.jsp" %>
</div>
<!--定义操作列按钮模板-->
<script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
</script>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/system/material/dye/dye.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/system/list.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/system/edit.js?v=<%=version%>"></script>
</body>


</html>

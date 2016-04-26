<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查询待办信息</title>
    <jsp:include page="../../base/hb-headc.jsp"></jsp:include>
    <!--<script type="text/javascript" src="/dt-page/extjs.js"></script>-->
</head>
<body>
<div class="page-content">

    <div class="page-header">
        <h1>
            任务管理
            <small><i class="icon-double-angle-right"></i> 我的待办</small>
        </h1>
    </div>
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <table id="example" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>选择</th>
                    <th>当前节点</th>
                    <th>任务创建时间</th>
                    <th>流程状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
                <!-- tbody是必须的 -->
            </table>


        </div>
        <!-- Button trigger modal -->
    </div>
</div>
<!--定义操作列按钮模板-->
<script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
</script>


</body>
</html>
<jsp:include page="../../base/hb-footj.jsp"></jsp:include>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/task/list/undo-list.js?v=<%=version%>"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查询项目信息</title>
    <jsp:include page="../../base/hb-headc.jsp"></jsp:include>
    <!--<script type="text/javascript" src="/dt-page/extjs.js"></script>-->
</head>
<body>
<div class="page-content">

    <div class="page-header">
        <h1>
            开发
            <small><i class="icon-double-angle-right"></i> 子项目列表信息</small>
        </h1>
    </div>
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <table id="example" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th>选择</th>
                    <th>项目名称</th>
                    <th>客户</th>
                    <th>区域</th>
                    <th>系列</th>
                    <th>推销样交期</th>
                    <th>创建人</th>
                    <th>项目创建时间</th>
                    <th>备注</th>
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
<script type="text/javascript">

    /**
     *
     *
     * */
    function doCall(_type, natrualkeys) {
        window.location.href = path + "/development/project_item/" + _type + "/" + natrualkeys;
    }

    /**
     * 批量合并
     * 未做
     */
    function _mergeList(_type) {
        var str = '';
        $("input[name='checkList']:checked").each(function (i, o) {
            str += $(this).val();
            str += ",";
        });
        if (str.length > 0) {
//            var IDS = str.substr(0, str.length - 1);
            doCall(_type, str);
        } else {
            bootbox.alert("至少选择一条记录操作");
        }
    }

</script>
<jsp:include page="../../base/hb-footj.jsp"></jsp:include>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/development/project/project-item-list.js?v=<%=version%>"></script>
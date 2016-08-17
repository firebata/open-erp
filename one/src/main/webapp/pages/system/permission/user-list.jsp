<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="page-content">
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <table id="userInfoExample" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th style="text-align: center">用户名</th>
                    <th style="text-align: center">用户类型</th>
                    <th style="text-align: center">是否在线</th>
                    <th style="text-align: center">是否锁定</th>
                    <th style="text-align: center">受权限限制</th>
                    <th style="text-align: center">上次登录时间</th>
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
    <%@ include file="chgpwd.jsp" %>
</div>
<!--定义操作列按钮模板-->
<script id="userInfotpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
</script>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/system/permission/userinfo-list.js?v=<%=version%>"></script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="page-content">
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            选择角色：<select id="roleId" name="roleId" data-style="btn-info"></select>
            &nbsp;&nbsp;&nbsp;
            <span class="glyphicon glyphicon-ok" id="saveResourceRoleBtn"></span>
        </div>

        <div class="col-xs-12">
            <div class="content_wrap">
                <div class="zTreeDemoBackground left">
                    <ul id="ResourceRoleTree" class="ztree"></ul>
                </div>
            </div>
        </div>


    </div>
</div>
<script type="text/javascript"
        src="<%=path%>/resources/js/interfaces/system/permission/resource-role.js?v=<%=version%>"></script>
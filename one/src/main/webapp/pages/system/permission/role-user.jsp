<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="page-content">
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            选择用户：<select id="userId" name="userId" data-style="btn-info"></select>
            &nbsp;&nbsp;&nbsp;
               <span class="glyphicon glyphicon-ok" id="saveRoleUserBtn"></span>
        </div>

        <div class="col-xs-12">
            <div class="content_wrap">
                <div class="zTreeDemoBackground left">
                    <ul id="roleUserTree" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript"      src="<%=path%>/resources/js/interfaces/system/permission/role-user.js?v=<%=version%>"></script>

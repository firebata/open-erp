<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="../../base/path.jsp" %>
<div class="modal fade" id="materialListModal" role="dialog" aria-labelledby="materialListModalLabel"
     aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="materialListModalLabel">物料列表</h4>
      </div>
      <form id="defaultForm" method="post" class="form-horizontal" action="edit">
        <div class="modal-body">
          <div class="row">
            <div class="col-xs-12">
              <table id="example" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                  <th style="text-align: center">材质名称</th>
                  <th style="text-align: center">所属类型</th>
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

        </div>
      </form>
    </div>
  </div>
</div>
<%@ include file="bom-material-add.jsp" %>
<!--定义操作列按钮模板-->
<script id="tpl" type="text/x-handlebars-template">
  {{#each func}}
  <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  {{/each}}
</script>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/system/material/material_list.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/system/list.js?v=<%=version%>"></script>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/system/edit.js?v=<%=version%>"></script>
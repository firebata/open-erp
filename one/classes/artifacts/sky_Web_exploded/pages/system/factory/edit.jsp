<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增/修改</h4>
            </div>
            <form id="defaultForm" method="post" class="form-horizontal" action="edit">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">名称</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="name" name="name"   placeholder="名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">全称</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="factoryFullname" name="factoryFullname"   placeholder="全称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">联系人</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="factoryContact" name="factoryContact"   placeholder="联系人">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">联系电话</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="factoryTel1" name="factoryTel1"   placeholder="联系电话">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">邮件地址</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="email" name="email"   placeholder="邮件地址">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">地址</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="factoryAddress" name="factoryAddress"   placeholder="邮件地址">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">法人代表</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="factoryLegal" name="factoryLegal"   placeholder="法人代表">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">营业执照</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="factoryLicense" name="factoryLicense"   placeholder="营业执照">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">合作时间</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="cooperationTime" name="cooperationTime"   placeholder="合作时间">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">备注</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="remark" name="remark"  placeholder="备注"/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-info"   id="save">保存</button>
                    <button type="button" class="btn btn-info" id="resetBtn">重置</button>
                </div>
            </form>
        </div>
    </div>
</div>

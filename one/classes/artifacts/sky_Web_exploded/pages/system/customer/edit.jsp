<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
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
                                    <label class="col-xs-3 control-label">客户名称</label>
                                    <div class="col-xs-7">
                                        <input type="text" class="form-control" id="name" name="name"   placeholder="客户名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">客户全称</label>
                                    <div class="col-xs-7">
                                        <input type="text" class="form-control" id="fullName" name="fullName"  placeholder="客户全称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">客户邮箱1</label>
                                    <div class="col-xs-7">
                                        <input type="text" class="form-control" id="email" name="email"  placeholder="客户邮箱1">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">客户邮箱2</label>
                                    <div class="col-xs-7">
                                        <input type="text" class="form-control" id="email2" name="email2" placeholder="客户邮箱2">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">地址</label>
                                    <div class="col-xs-7">
                                        <input type="text" class="form-control" id="address" name="address"  placeholder="地址">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">联系人</label>
                                    <div class="col-xs-7">
                                        <input type="text" class="form-control" id="contact" name="contact"    placeholder="联系人"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">联系手机</label>

                                    <div class="col-xs-7">
                                        <input type="text" class="form-control" id="phone" name="phone"   placeholder="联系手机"/>
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
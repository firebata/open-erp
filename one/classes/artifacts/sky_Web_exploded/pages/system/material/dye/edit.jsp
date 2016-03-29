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
                                    <label class="col-xs-3 control-label">名称</label>
                                    <div class="col-xs-7">
                                        <input type="text" class="form-control" id="name" name="name"   placeholder="名称">
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
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
                                    <label class="col-lg-3 control-label">色号</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="colorNo" name="colorNo"
                                               placeholder="色号">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">英文名称</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="enName" name="enName"
                                               placeholder="英文名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">中文名称</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="zhName" name="zhName"
                                               placeholder="中文名称">
                                    </div>
                                </div>
                                <div class="form-group">

                                    <label class="col-lg-3 control-label">页码</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="pageNo" name="pageNo"
                                               placeholder="页码">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">颜色</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="codeColor" name="codeColor"
                                               placeholder="颜色">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">备注</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="remark" name="remark"
                                               placeholder="备注"/>
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
<script type="text/javascript" src="<%=path%>/resources/interfaces/system/pantone/pantone.js"/>
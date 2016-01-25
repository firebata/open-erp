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
                                    <label class="col-lg-3 control-label">供应商名称</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="name" name="name"
                                               placeholder="供应商名称">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">供应商类型</label>
                                    <div class="col-lg-7">
                                        <select  class="form-control" data-style="btn-info" id="type" name="type"  placeholder="供应商类型">
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">联系人</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="contact" name="contact"
                                               placeholder="联系人">
                                    </div>
                                </div>
                                <div class="form-group">

                                    <label class="col-lg-3 control-label">联系电话</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="tel" name="tel"
                                               placeholder="联系电话">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">邮件地址</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="email" name="email"
                                               placeholder="邮件地址">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-3 control-label">地址</label>

                                    <div class="col-lg-7">
                                        <input type="text" class="form-control" id="address" name="address"
                                               placeholder="地址">
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
<script type="text/javascript" src="<%=path%>/resources/interfaces/system/sp/sp.js?v=<%=version%>"/>
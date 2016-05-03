<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>成衣生产指示单</title>
    <jsp:include page="../../base/hb-headc.jsp"></jsp:include>
    <jsp:include page="../../base/upload.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/ace.css" class="ace-main-stylesheet"
          id="main-ace-style"/>
</head>
<body>
<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
        try {
            ace.settings.check('breadcrumbs', 'fixed')
        } catch (e) {
        }
    </script>
    <ul class="breadcrumb">
        <li><i class="ace-icon fa fa-home home-icon"></i>开发</li>
        <li><a href="#">报价</a></li>
        <li class="active">预报价</li>
    </ul>
    <!-- /.breadcrumb -->
    <!-- /section:basics/content.searchbox -->
</div>

<div class="page-content">
    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <form class="form-horizontal" role="form" id="quotepreForm">
                <div id="quotepreDesc">
                    <div id="quotepreDescTitle">
                        <h5 class="header smaller lighter blue">
                            报价信息
                            <small></small>
                            <input type="hidden" name="natrualkey" id="natrualkey" value="${natrualkey}"/>
                            <input type="hidden" name="taskId" id="taskId" value="${taskId}"/>
                            <input type="hidden" name="stateCode" id="stateCode" value="${stateCode}"/>
                            <input type="hidden" name="processInstanceId" id="processInstanceId" value="${processInstanceId}"/>
                        </h5>
                    </div>
                    <div id="quotepreDescDetail">
                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="cropRequirements"> 裁剪要求 </label>
                            <div class="col-xs-3">
                                <textarea id="cropRequirements" name="cropRequirements"
                                          class="autosize-transition form-control col-xs-10 col-sm-12"
                                          placeholder="裁剪要求"></textarea>
                            </div>

                            <label class="col-xs-2  control-label" for="qualityRequirements"> 工艺及质量要求</label>
                            <div class="col-xs-3">
                                <textarea id="qualityRequirements" name="qualityRequirements"
                                          class="autosize-transition form-control col-xs-10 col-sm-12"
                                          placeholder="工艺及质量要求"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="finishPressingRequirements"> 整烫要求 </label>
                            <div class="col-xs-3">
                                <textarea id="finishPressingRequirements" name="finishPressingRequirements"
                                          class="autosize-transition form-control col-xs-10 col-sm-12"
                                          placeholder="整烫要求"></textarea>
                            </div>

                            <label class="col-xs-2  control-label" for="spcialTech"> 特殊工艺</label>
                            <div class="col-xs-3">
                                <textarea id="spcialTech" name="spcialTech"
                                          class="autosize-transition form-control col-xs-10 col-sm-12"
                                          placeholder="特殊工艺"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="packingRequirements"> 包装要求 </label>
                            <div class="col-xs-3">
                                <textarea id="packingRequirements" name="packingRequirements"
                                          class="autosize-transition form-control col-xs-10 col-sm-12"
                                          placeholder="包装要求"></textarea>
                            </div>

                        </div>

                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="overstitch">面线</label>
                            <div class="col-xs-3">
                                <input type="text" id="overstitch" name="overstitch" placeholder="面线"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="control-label  col-xs-2" for="overstitchSpace">面线针距</label>
                            <div class="col-xs-3">
                                <input type="text" id="overstitchSpace" name="overstitchSpace" placeholder="面线针距"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="blindstitch">暗线</label>
                            <div class="col-xs-3">
                                <input type="text" id="blindstitch" name="blindstitch" placeholder="暗线"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="control-label  col-xs-2" for="blindstitchSpace">暗线针距</label>
                            <div class="col-xs-3">
                                <input type="text" id="blindstitchSpace" name="blindstitchSpace" placeholder="暗线针距"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="overlock">拷边</label>
                            <div class="col-xs-3">
                                <input type="text" id="overlock" name="overlock" placeholder="拷边"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="control-label  col-xs-2" for="overlockSpace">拷边针距</label>
                            <div class="col-xs-3">
                                <input type="text" id="overlockSpace" name="overlockSpace" placeholder="拷边针距"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                        </div>

                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="trademarkCode">商标编码</label>
                            <div class="col-xs-3">
                                <input type="text" id="trademarkCode" name="trademarkCode" placeholder="商标编码"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="control-label  col-xs-2" for="trademarkRemark">商标描述</label>
                            <div class="col-xs-3">
                                <input type="text" id="trademarkRemark" name="trademarkRemark" placeholder="商标描述"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="scaleCode">尺标编码</label>
                            <div class="col-xs-3">
                                <input type="text" id="scaleCode" name="scaleCode" placeholder="尺标编码"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="control-label  col-xs-2" for="scaleRemark">尺标描述</label>
                            <div class="col-xs-3">
                                <input type="text" id="scaleRemark" name="scaleRemark" placeholder="尺标描述"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                        </div>

                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="rinsingMarksCode">水洗标编码</label>
                            <div class="col-xs-3">
                                <input type="text" id="rinsingMarksCode" name="rinsingMarksCode" placeholder="水洗标编码"
                                       class="col-xs-10 col-sm-12"/>
                            </div>

                            <label class="control-label  col-xs-2" for="rinsingMarksRemark">水洗标描述</label>
                            <div class="col-xs-3">
                                <input type="text" id="rinsingMarksRemark" name="rinsingMarksRemark" placeholder="水洗标描述"
                                       class="col-xs-10 col-sm-12"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label  col-xs-2" for="sketchUrlUid"> 款式图 </label>
                            <div class="col-xs-3">
                                <input id="sketchUrlUid" type="file" multiple class="file-loading col-xs-12"
                                       name="fileLocation">
                            </div>
                            <label class="col-xs-2  control-label" for="specificationUrlUid"> 规格表</label>
                            <div class="col-xs-3">
                                <input id="specificationUrlUid" type="file" multiple class="file-loading col-xs-12"
                                       name="fileLocation">
                            </div>
                        </div>

                    </div>
                </div>

                <div id="quotepreBtnInfo">

                </div>
            </form>


        </div>
        <!-- Button trigger modal -->
    </div>
</div>
</body>
</html>
<jsp:include page="../../base/hb-footj.jsp"></jsp:include>
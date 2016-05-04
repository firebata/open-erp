<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../../base/path.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新增/修改项目信息</title>
    <jsp:include page="../../base/hb-headc.jsp"></jsp:include>
    <jsp:include page="../../base/upload.jsp"></jsp:include>
    <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
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
        <li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">开发</a>
        </li>
        <li><a href="#">项目</a></li>
        <li class="active">新增/修改</li>
    </ul>
    <!-- /.breadcrumb -->
    <!-- /section:basics/content.searchbox -->
</div>

<div class="page-content">


    <!-- PAGE CONTENT BEGINS -->
    <div class="row">
        <div class="col-xs-12">
            <form class="form-horizontal" role="form" id="projectForm">
                <div id="projectDesc">
                    <div id="projectDescTitle">
                        <h5 class="header smaller lighter blue">
                            基本信息
                            <small></small>
                            <input type="hidden" name="natrualkey" id="natrualkey" value="${natrualkey}"/>
                        </h5>
                    </div>
                    <div id="projectDescDetail">
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="yearCode"> 年份 </label>

                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="yearCode" name="yearCode"
                                        placeholder="年份">
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="customerId"> 客户 </label>

                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="customerId" name="customerId"
                                        placeholder="客户">
                                </select>
                            </div>

                        </div>

                        <%-- <!-- #section:custom/extra.hr -->
                     <div class="hr hr32 hr-dotted"></div>--%>
                        <div class="form-group">
                            <label class="col-xs-2  control-label" for="areaId"> 区域 </label>

                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="areaId" name="areaId"    placeholder="区域">
                                </select>
                            </div>

                            <label class="col-xs-2  control-label" for="seriesId"> 系列 </label>
                            <div class="col-xs-3">
                                <select class="col-xs-12" data-style="btn-info" id="seriesId" name="seriesId"      placeholder="系列">
                                </select>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-xs-2  control-label"
                                   for="sampleDelivery"> 样品交付日期 </label>

                            <div class="col-xs-3">
                                <input type="text" id="sampleDelivery" name="sampleDelivery"
                                       placeholder="样品交期" class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>
                            </div>

                            <label class="col-xs-2  control-label"
                                   for="needPreOfferDate"> 预报价日期 </label>

                            <div class="col-xs-3">
                                <input type="text" id="needPreOfferDate" name="needPreOfferDate"
                                       placeholder="预报价日期" class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>
                            </div>

                        </div>

                    </div>
                </div>
                <div id="fabricsInfo">
                    <h5 class="header smaller lighter blue">
                        BOM基础信息
                    </h5>

                    <div class="form-group">
                        <label class="col-xs-2  control-label" for="categoryAid"> 品类一级名称 </label>
                        <div class="col-xs-3">
                            <select data-style="btn-info" class="selectpicker show-menu-arrow" data-width="100%"    id="categoryAid" multiple placeholder="品类一级名称">"
                            </select>
                        </div>
                    </div>
                    <!--性别-->
                    <div id="categoryDivAll">
                        <%@ include file="project-category.jsp" %>
                    </div>
                </div>
                <div id="accessoriesInfo">
                    <h5 class="header smaller lighter blue">
                        附件信息
                    </h5>

                    <div class="form-group">
                        <label class="col-xs-2  control-label"         for="sketchReceivedDate"> 产品描述收到时间 </label>

                        <div class="col-xs-3">
                            <input type="text" id="sketchReceivedDate" name="sketchReceivedDate"            placeholder="产品描述收到时间" class="col-xs-10 col-sm-12" onClick="WdatePicker()"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2  control-label"            for="fileLocation"> 附件上传 </label>
                        <div class="col-xs-10">
                            <input id="fileLocation" type="file" multiple class="file-loading col-xs-12"    name="fileLocation">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-7  col-sm-4  control-label">文件列表</label>
                        <div class="col-xs-12 col-sm-4" >
                            <ul id="filesList">

                            </ul>
                        </div>
                    </div>
                </div>

                <div id="projectBtnInfo">
                    <div class="col-xs-offset-6 col-xs-9">
                        <button type="button" class="btn btn-info btn-md" onclick="javascript:$.saveProject()">保存</button>
                    </div>
                </div>
            </form>

        </div>
        <!-- Button trigger modal -->
    </div>
</div>

</body>

</html>
<jsp:include page="../../base/hb-footj.jsp"></jsp:include>
<script type="text/javascript" src='<%=path%>/resources/js/fileinput.js'></script>
<script type="text/javascript" src='<%=path%>/resources/js/fileinput_locale_zh.js'></script>
<script type="text/javascript" src="<%=path%>/resources/js/interfaces/development/project/project-add.js?v=<%=version%>"></script>
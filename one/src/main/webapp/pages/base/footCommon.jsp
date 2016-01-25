<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../base/path.jsp"%>
<script type="text/javascript" src="<%=path%>/resources/js/typeahead-bs2.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/ace-elements.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/ace.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/bootstrap-colorpicker.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/bootstrap-tag.min.js"></script>

<script type="text/javascript">
    if ("ontouchend" in document){
        document.write("<script src='<%=path%>/resources/js/jquery.mobile.custom.min.js'>"+ "<"+"/script>");
    }
</script>

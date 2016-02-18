<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>error page</title>
    <script type="text/javascript">
        $(function () {
            $("#center-div").center(true);
        })
    </script>
</head>
<body style="margin: 0;padding: 0;background-color: #f5f5f5;">
<div id="center-div">
    <table style="height: 100%; width: 600px; text-align: center;">
        <tr>
            <td>
                <img width="220" height="393" src="${basePath}/images/common/error.png"
                     style="float: left; padding-right: 20px;" alt=""/>
                <%= exception.getMessage()%>
                <p style="line-height: 12px; color: #666666; font-family: Tahoma, '宋体'; font-size: 12px; text-align: left;">
                    <a href="javascript:history.go(-1);">返回</a>!!!
                </p>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
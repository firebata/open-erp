<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th style="text-align: center">
            类型
        </th>
        <th style="text-align: center">
            描述
        </th>
        <th style="text-align: center">
            单位用量
        </th>
        <%--<th style="text-align: center">
            损耗率(小数)
        </th>--%>
        <th style="text-align: center">
            单价(￥)
        </th>
        <th style="text-align: center">
            各色单价(￥)
        </th>
    </tr>
    </thead>
    <tbody>
    <tr ng-repeat="item in packagingInfos">
        <input type="hidden" name="fabricId" ng-model="item.fabricId"/>
        <td>{{item.productTypeId}}</td>
        <td>{{item.description}}</td>
        <td><input type="text" ng-model="item.unitAmount" value="{{item.unitAmount}}"></td>
        <td><input type="text" ng-model="item.unitPrice" value="{{item.unitPrice}}"></td>
        <td style="display:table-cell; vertical-align:middle">{{(item.unitAmount * item.unitPrice) | fixed:3}}</td>
    </tr>
    </tbody>
</table>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script id="menu-template" type="text/x-handlebars-template">
    {{#each this}}
    <li>
        <a href="#" class="dropdown-toggle">
            <span class="menu-text"> {{name}} </span>
            <b class="arrow fa fa-angle-down"></b>
        </a>
        <ul class="submenu">
            {{#each menus}}
            {{#compare menus}}
            <li class="">
                <a href="#" class="dropdown-toggle">
                    <i class="menu-icon fa fa-caret-right"></i>
                    {{name}}
                    <b class="arrow fa fa-angle-down"></b>
                </a>
                <b class="arrow"></b>
                <ul class="submenu">
                    {{#each menus}}
                    {{#compare menus}}
                    <li class="">
                        <a href="#">
                            <i class="menu-icon fa fa-caret-right"></i>
                            {{name}}
                            <b class="arrow fa fa-angle-down"></b>
                        </a>
                        <b class="arrow"></b>
                    </li>
                    {{else}}
                    <li>
                        <a href="javascript:clickMenu('<%=path%>{{url}}')">
                            <i class="menu-icon fa fa-caret-right"></i>
                            {{name}}
                        </a>
                        <b class="arrow"></b>
                    </li>
                    {{/compare}}
                    {{/each}}
                </ul>
            </li>
            {{else}}
            <li>
                <a href="javascript:clickMenu('<%=path%>{{url}}')">
                    {{name}}
                </a>
            </li>
            {{/compare}}
            {{/each}}
        </ul>
    </li>
    {{/each}}
</script>
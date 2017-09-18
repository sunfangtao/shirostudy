<!DOCTYPE html>
<%@ page pageEncoding="UTF8" %>
<%@ include file="../common/lib.jsp" %>

<html>
<head>
    <meta charset="utf-8">
    <shiro:hasRole name="admin">
        <title>角色权限管理</title>
    </shiro:hasRole>
    <shiro:hasRole name="appdevelop">
        <title>连接查看</title>
    </shiro:hasRole>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-form-item">
    <ul class="layui-nav" lay-filter="nav_bar">
        <shiro:hasRole name="admin">
            <li class="layui-nav-item layui-this"><a>角色管理</a></li>
            <li class="layui-nav-item"><a>权限管理</a></li>
            <li class="layui-nav-item"><a>模块管理</a></li>
            <li class="layui-nav-item"><a>用户管理</a></li>
            <li class="layui-nav-item"><a>APP连接管理</a></li>
        </shiro:hasRole>
        <shiro:hasRole name="appdevelop">
            <shiro:lacksRole name="admin">
                <li class="layui-nav-item layui-this"><a>APP连接管理</a></li>
            </shiro:lacksRole>
        </shiro:hasRole>
        <li class="layui-nav-item"><a href="${ctx}/logout">退出</a></li>
    </ul>
</div>

<iframe id="iframe" style="border: none" width="100%" height="700px"
        <shiro:hasRole name="admin">
            src= "${ctx}/page/role.jsp"
        </shiro:hasRole>
        <shiro:hasRole name="appdevelop">
            src= "${ctx}/page/app_permission.jsp"
        </shiro:hasRole>
></iframe>

<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
<script src="${ctx}/static/js/index.js" charset="utf-8"></script>

</body>
</html>
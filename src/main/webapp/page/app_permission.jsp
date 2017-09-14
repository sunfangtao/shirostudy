<!DOCTYPE html>
<%@ page pageEncoding="UTF8" %>
<%@ include file="../common/lib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>APP权限</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
</head>
<body>

<div class="layui-form">
    <div class="layui-form-item">
        <div class="layui-inline">
            <button class="layui-btn layui-btn-small" onclick="addAppPermission()">
                <i class="layui-icon">&#xe608;</i> 添加
            </button>
        </div>
    </div>
</div>

<div id="permission_table" lay-filter="permissionTable"></div>

<form id="edit_permission" style="display:none;padding: 5px;" class="layui-form layui-form-pane">
    <div class="layui-form-item" style="display:none;">
        <label class="layui-form-label">权限ID</label>
        <div class="layui-input-block">
            <input id="id" class="layui-input" type="label" name="id" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">资源地址</label>
        <div class="layui-input-block">
            <input id="url" class="layui-input" type="text" name="url" lay-verify="required" autocomplete="off"
                   placeholder="请输入资源地址">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">访问标识</label>
        <div class="layui-input-block">
            <input id="type" class="layui-input" type="text" name="type" lay-verify="required" autocomplete="off"
                   placeholder="请输入访问标识">
        </div>
    </div>

    <div class="layui-form-item" pane="">
        <label class="layui-form-label">需要登录</label>
        <div class="layui-input-block">
            <input id="is_user" type="checkbox" checked="" name="is_user" lay-skin="switch"
                   lay-filter="switchPermission"
                   lay-text="是|否" value="1">
        </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <textarea id="remarks" placeholder="请输入内容" name="remarks" class="layui-textarea"></textarea>
        </div>
    </div>
</form>

<script type="text/html" id="barTool">
    <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">认证</a>
</script>

<script type="text/html" id="validateTpl">
    {{#  if(d.is_user == 0){ }}
    <span style="color: #F581B1;">{{ '免登录' }}</span>
    {{#  } else { }}
    {{ '需要登录' }}
    {{#  } }}
</script>

<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
<script src="${ctx}/static/js/app_permission.js" charset="utf-8"></script>
</body>
</html>
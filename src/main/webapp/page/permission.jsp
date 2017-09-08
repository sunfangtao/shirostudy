<!DOCTYPE html>
<%@ page pageEncoding="UTF8" %>
<%@ include file="../common/lib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>权限</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
</head>
<body>

<button class="layui-btn layui-btn-small" onclick="addPermission()">
    <i class="layui-icon">&#xe608;</i> 添加
</button>

<form class="layui-form">
    <div class="layui-input-block" style="width: 120px">
        <select id="permission_validate_type" lay-filter="permission_validate_select">
            <option value="2">全部</option>
            <option value="0">有效</option>
            <option value="1">无效</option>
        </select>
    </div>
</form>

<div id="permission_table" lay-filter="permissionTable"></div>

<form id="edit_permission" style="display:none;padding: 5px;" class="layui-form layui-form-pane">
    <div class="layui-form-item" style="display:none;">
        <label class="layui-form-label">权限ID</label>
        <div class="layui-input-block">
            <input id="permissionId" class="layui-input" type="label" name="permissionId" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">所属模块</label>
        <div class="layui-input-block">
            <select id="module_type" name="moduleId" lay-filter="module_select">
                <option value="" >请选择模块</option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">权限别名</label>
        <div class="layui-input-block">
            <input id="name" class="layui-input" type="text" name="name" lay-verify="required" autocomplete="off"
                   placeholder="请输入权限别名">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">权限标识</label>
        <div class="layui-input-block">
            <input id="permission" class="layui-input" type="text" name="permission" lay-verify="required"
                   autocomplete="off"
                   placeholder="请输入权限标识">
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
        <label class="layui-form-label">是否删除</label>
        <div class="layui-input-block">
            <input id="del_flag" type="checkbox" checked="" name="del_flag" lay-skin="switch" lay-filter="switchPermission"
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
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
</script>

<script type="text/html" id="validateTpl">
    {{#  if(d.del_flag == 0){ }}
    <span style="color: #F581B1;">{{ '有效' }}</span>
    {{#  } else { }}
    {{ '无效' }}
    {{#  } }}
</script>

<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
<script src="${ctx}/static/js/permission.js" charset="utf-8"></script>
</body>
</html>
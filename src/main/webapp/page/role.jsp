<!DOCTYPE html>
<%@ page pageEncoding="UTF8" %>
<%@ include file="../common/lib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>角色</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/static/layui_css/adjustment.css" media="all">
</head>
<body>

<button class="layui-btn layui-btn-small" onclick="addRole()">
    <i class="layui-icon">&#xe608;</i> 添加
</button>

<form class="layui-form">
    <div class="layui-input-block" style="width: 120px">
        <select id="validate_type" lay-filter="validate_select">
            <option value="2">全部</option>
            <option value="0">有效</option>
            <option value="1">无效</option>
        </select>
    </div>
</form>

<div id="role_talbe" lay-filter="roleTable"></div>

<form id="edit_role" style="display:none;padding: 5px;" class="layui-form layui-form-pane">
    <div class="layui-form-item" style="display:none;">
        <label class="layui-form-label">角色ID</label>
        <div class="layui-input-block">
            <input id="roleId" class="layui-input" type="label" name="roleId" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-block">
            <input id="name" class="layui-input" type="text" name="name" lay-verify="required" autocomplete="off"
                   placeholder="请输入角色名称">
        </div>
    </div>

    <div class="layui-form-item" pane="">
        <label class="layui-form-label">是否删除</label>
        <div class="layui-input-block">
            <input id="del_flag" type="checkbox" checked="" name="del_flag" lay-skin="switch" lay-filter="switchTest"
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

<form id="edit_role_permission" style="display:none;padding: 5px;">
    <div id="edit_role_tree" class="layui-collapse sy-layui-collapse" lay-filter="role_permission">
        <div class="layui-colla-item sy-layui-colla-item">
            <h2 class="layui-colla-title sy-layui-colla-title"></h2>
            <div class="sy-colla-checkbox">
                <input type="checkbox" name="" title=" 父权限1" lay-skin="primary">
                <span>父权限1</span>
            </div>
            <div class="sy-colla-blank0px"></div>
            <div class="layui-colla-content sy-layui-colla-content">
                <ul class="sy-colla-submenu">
                    <li><input type="checkbox" name="" title=" 子权限1" lay-skin="primary"><span>子权限1</span></li>
                    <li><input type="checkbox" name="" title=" 子权限2" lay-skin="primary"><span>子权限2</span></li>
                </ul>
            </div>
            <div class="sy-colla-blank0px"></div>
        </div>
    </div>
</form>

<script type="text/html" id="barTool">
    <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="upd">修改权限</a>
</script>

<script type="text/html" id="validateTpl">
    {{#  if(d.del_flag == 0){ }}
    <span style="color: #F581B1;">{{ '有效' }}</span>
    {{#  } else { }}
    {{ '无效' }}
    {{#  } }}
</script>

<script type="text/html" id="dateTpl">
    {{ d.create_date.split('.')[0] }}
</script>

<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>
<script src="${ctx}/static/js/role.js" charset="utf-8"></script>
</body>
</html>
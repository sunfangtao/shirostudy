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

<div class="layui-container">
    <div class="layui-row">
        <table class="layui-table" lay-data="{height:313, url:'/rolePermission/roles',page:true, id:'idTest',
                    request: {pageName: 'page',limitName: 'pageSize'}}" lay-filter="roleTable">
            <thead>
            <tr>
                <th lay-data="{field:'id', width:200, sort: true, align:'center'}">ID</th>
                <th lay-data="{field:'name', width:150, align:'center'}">权限名</th>
                <th lay-data="{field:'permission', width:150, align:'center'}">权限标识</th>
                <th lay-data="{field:'url', width:150, align:'center'}">资源地址</th>
                <th lay-data="{field:'type', width:150, align:'center'}">映射标识</th>
                <th lay-data="{field:'module', width:150, align:'center'}">模块名称</th>
                <%--<th lay-data="{field:'create_by', width:150, align:'center'}">创建者</th>--%>
                <%--<th lay-data="{field:'create_date', width:180, align:'center'}">创建日期</th>--%>
                <%--<th lay-data="{field:'update_by', width:150, align:'center'}">更新者</th>--%>
                <%--<th lay-data="{field:'update_date', width:180, align:'center'}">更新日期</th>--%>
                <th lay-data="{field:'del_flag', width:120, align:'center', templet: '#validateTpl'}">是否有效</th>
                <th lay-data="{field:'remarks', width:120, align:'center'}">备注</th>
                <th lay-data="{fixed: 'right', width:150, align:'center', toolbar: '#barTool'}"></th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<form id="edit_permission" style="display:none;padding: 5px;" class="layui-form layui-form-pane">
    <div class="layui-form-item" style="display:none;">
        <label class="layui-form-label">角色ID</label>
        <div class="layui-input-block">
            <input id="roleId" class="layui-input" type="label" name="roleId" lay-verify="required" autocomplete="off">
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
            <input id="del_flag" type="checkbox" name="del_flag" value="1" lay-skin="switch" lay-filter="switchTest"
                   lay-text="是|否" checked>
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
<script src="${ctx}/static/js/role.js" charset="utf-8"></script>
</body>
</html>
<!DOCTYPE html>
<%@ page pageEncoding="UTF8" %>
<%@ include file="../common/lib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>系统模块</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
</head>
<body>

<shiro:hasPermission name="module/addModule">
    <button class="layui-btn layui-btn-small" onclick="addModule()">
        <i class="layui-icon">&#xe608;</i> 添加
    </button>
</shiro:hasPermission>

<shiro:hasPermission name="module/getAllModule">
    <div id="module_talbe" lay-filter="moduleTable"></div>
</shiro:hasPermission>

<form id="edit_module" style="display:none;padding: 5px;" class="layui-form layui-form-pane">
    <div class="layui-form-item" style="display:none;">
        <label class="layui-form-label">模块ID</label>
        <div class="layui-input-block">
            <input id="moduleId" class="layui-input" type="label" name="moduleId" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">模块名称</label>
        <div class="layui-input-block">
            <input id="name" class="layui-input" type="text" name="name" lay-verify="required" autocomplete="off"
                   placeholder="请输入模块名称">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">访问地址</label>
        <div class="layui-input-block">
            <input id="address" class="layui-input" type="text" name="address" lay-verify="required"
                   autocomplete="off"
                   placeholder="请输入访问地址">
        </div>
    </div>

    <div class="layui-form-item" pane="">
        <label class="layui-form-label">是否删除</label>
        <div class="layui-input-block">
            <input id="del_flag" type="checkbox" checked="" name="del_flag" lay-skin="switch"
                   lay-filter="switchModule"
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

<script type="text/html" id="dateTpl">
    {{ d.create_date.split('.')[0] }}
</script>

<script src="${ctx}/static/layui/layui.js" charset="utf-8"></script>

<script>
    layui.use(['table', 'layer', 'form'], function () {
        var table = layui.table;
        var $ = layui.jquery;
        var form = layui.form;

        <shiro:hasPermission name="module/getAllModule">
        // 执行渲染
        table.render({
            id: 'module_talbe',
            elem: '#module_talbe', // 指定原始表格元素选择器（推荐id选择器）
            height: 700, // 容器高度
            cols: [[
                // {field: 'id', title: 'ID', width: 150, align: 'center'},
                {field: 'name', title: '角色名', width: 200, align: 'center'},
                {field: 'address', title: '访问地址', width: 300, align: 'center'},
                {field: 'create_name', title: '创建者', width: 200, align: 'center'},
                {field: 'create_date', title: '创建日期', width: 200, align: 'center', templet: '#dateTpl'},
                {field: 'del_flag', title: '是否有效', width: 200, align: 'center', templet: '#validateTpl'},
                {field: 'remarks', title: '备注', width: 400, align: 'center'},
                <shiro:hasPermission name="module/updateModule">
                {fixed: 'right', width: 200, align: 'center', toolbar: '#barTool'}
                </shiro:hasPermission>
            ]], // 设置表头
            request: {
                pageName: 'page', // 页码的参数名称，默认：page
                limitName: 'pageSize' // 每页数据量的参数名，默认：limit
            },
            limit: 15,
            even: true,
            page: true,
            limits: [10, 15, 20],
            url: ctx + '/module/getAllModule',
        });
        </shiro:hasPermission>

        table.on('tool(moduleTable)', function (obj) {
            if (obj.event == 'del') {
                // 设置模块无效
                deleteModule(obj);
            } else {
                // 编辑模块
                editModule(obj);
            }
        });
    });
</script>

<script src="${ctx}/static/js/module.js" charset="utf-8"></script>
</body>
</html>
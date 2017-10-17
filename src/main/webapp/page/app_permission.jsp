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

        <shiro:hasPermission name="appPermission/getAllAppPermissions">

            <shiro:hasPermission name="module/getAllModule">
                <div class="layui-form-item">
                    <label class="layui-form-label">所属模块</label>
                    <div class="layui-input-block">
                        <select id="module_select" name="moduleId" lay-filter="module_select">
                            <option value="">请选择模块</option>
                        </select>
                    </div>
                </div>
            </shiro:hasPermission>

            <label class="layui-form-label">资源地址</label>
            <div class="layui-input-inline">
                <input id="selecttype" class="layui-input" type="text" name="selecttype" lay-verify="required"
                       autocomplete="off"
                       placeholder="请输入资源地址">
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn-small" onclick="reloadTable()">
                    <i class="layui-icon">&#xe608;</i> 搜索
                </button>
            </div>
        </shiro:hasPermission>

        <shiro:hasPermission name="appPermission/addAppPermission">
            <div class="layui-inline">
                <button class="layui-btn layui-btn-small" onclick="addAppPermission()">
                    <i class="layui-icon">&#xe608;</i> 添加
                </button>
            </div>
        </shiro:hasPermission>
    </div>
</div>

<shiro:hasPermission name="appPermission/getAllAppPermissions">
    <div id="permission_table" lay-filter="permissionTable"></div>
</shiro:hasPermission>

<form id="edit_permission" style="display:none;padding: 5px;" class="layui-form layui-form-pane">
    <div class="layui-form-item" style="display:none;">
        <label class="layui-form-label">权限ID</label>
        <div class="layui-input-block">
            <input id="id" class="layui-input" type="label" name="id" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">所属模块</label>
        <div class="layui-input-block">
            <select id="module_type" name="moduleId" lay-filter="module_type">
                <option value="">请选择模块</option>
            </select>
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

<script>
    layui.use([
        'table', 'layer', 'form', 'element'], function () {
        var table = layui.table;
        var $ = layui.jquery;
        var form = layui.form;

        // 执行渲染
        table.render({
            id: 'permission_table',
            elem: '#permission_table', // 指定原始表格元素选择器（推荐id选择器）
            height: 700, // 容器高度
            cols: [[
                // {field: 'id', title: 'ID', width: 150, align: 'center'},
                {field: 'type', title: '映射标识', width: 200, align: 'center'},
                <shiro:hasPermission name="appPermission/updateAppPermission">
                {field: 'url', title: '资源地址', width: 240, align: 'center'},
                {field: 'module', title: '所属模块', width: 240, align: 'center'},
                </shiro:hasPermission>
                {field: 'is_user', title: '是否需要登录', width: 200, align: 'center', templet: '#validateTpl'},
                {field: 'remarks', title: '备注', width: 700, align: 'center'},
                <shiro:hasPermission name="appPermission/updateAppPermission">
                {fixed: 'right', width: 150, align: 'center', toolbar: '#barTool'}
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
            url: ctx + '/appPermission/getAllAppPermissions',
            where: {
                type: $('#selecttype').val(),
                moduleId: $('#module_select').val()
            }
        });

        table.on('tool(permissionTable)', function (obj) {
            if (obj.event == 'del') {
                // 设置权限无效
                deletePermission(obj);
            } else {
                // 编辑权限
                editPermission(obj);
            }
        });

        form.on('select(module_select)', function (data) {
            reloadTable();
        });

        selectionInitA();
    });
</script>

<script src="${ctx}/static/js/app_permission.js" charset="utf-8"></script>

</body>
</html>
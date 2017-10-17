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

<div class="layui-form">

    <shiro:hasPermission name="rolePermission/addRole">
        <div class="layui-inline">
            <button class="layui-btn layui-btn-small" onclick="addRole()">
                <i class="layui-icon">&#xe608;</i> 添加
            </button>
        </div>
    </shiro:hasPermission>

    <shiro:hasPermission name="rolePermission/roles">
        <div class="layui-inline" style="width: 120px">
            <select id="validate_type" lay-filter="validate_select">
                <option value="2">全部</option>
                <option value="0">有效</option>
                <option value="1">无效</option>
            </select>
        </div>
    </shiro:hasPermission>
</div>

<shiro:hasPermission name="rolePermission/roles">
    <div id="role_talbe" lay-filter="roleTable"></div>
</shiro:hasPermission>

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
            <input id="del_flag" type="checkbox" checked="" name="del_flag" lay-skin="switch"
                   lay-filter="switchTest"
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
            <%--<div class="sy-colla-blank0px"></div>--%>
            <%--<div class="layui-colla-content sy-layui-colla-content">--%>
            <%--<ul class="sy-colla-submenu">--%>
            <%--<li><input type="checkbox" name="" title=" 子权限1" lay-skin="primary"><span>子权限1</span></li>--%>
            <%--<li><input type="checkbox" name="" title=" 子权限2" lay-skin="primary"><span>子权限2</span></li>--%>
            <%--</ul>--%>
            <%--</div>--%>
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

<script>

    layui.use(['table', 'layer', 'form', 'element'], function () {
        var table = layui.table;
        var $ = layui.jquery;
        var form = layui.form;

        // 执行渲染
        table.render({
            id: 'role_talbe',
            elem: '#role_talbe', // 指定原始表格元素选择器（推荐id选择器）
            height: 700, // 容器高度
            cols: [[
                // {field: 'id', title: 'ID', width: 150, align: 'center'},
                {field: 'name', title: '角色名', width: 300, align: 'center'},
                {field: 'create_name', title: '创建者', width: 200, align: 'center'},
                {field: 'create_date', title: '创建日期', width: 200, align: 'center', templet: '#dateTpl'},
                {field: 'del_flag', title: '是否有效', width: 200, align: 'center', templet: '#validateTpl'},
                {field: 'remarks', title: '备注', width: 400, align: 'center'},
                <shiro:hasPermission name="roleEdit">
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
            url: ctx + '/rolePermission/roles',
            where: {
                del_flag: $('#validate_type').val()
            }
        });

        form.on('select(validate_select)', function (data) {
            reloadTable();
        });

        table.on('tool(roleTable)', function (obj) {
            if (obj.event == 'upd') {
                // 修改权限
                updateRolePermission(obj);
            } else if (obj.event == 'del') {
                // 设置角色无效
                deleteRole(obj);
            } else {
                // 编辑角色
                editRole(obj);
            }
        });
    });

</script>

<script src="${ctx}/static/js/role.js" charset="utf-8"></script>
</body>
</html>
<!DOCTYPE html>
<%@ page pageEncoding="UTF8" %>
<%@ include file="../common/lib.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <title>系统用户</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${ctx}/static/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${ctx}/static/layui_css/adjustment.css" media="all">
</head>
<body>

<shiro:hasPermission name="user/addUser">
    <button class="layui-btn layui-btn-small" onclick="addUser()">
        <i class="layui-icon">&#xe608;</i> 添加
    </button>
</shiro:hasPermission>

<shiro:hasPermission name="user/getUser">
    <div id="user_talbe" lay-filter="userTable"></div>
</shiro:hasPermission>

<form id="edit_user" style="display:none;padding: 5px;" class="layui-form layui-form-pane">
    <div class="layui-form-item" style="display:none;">
        <label class="layui-form-label">用户ID</label>
        <div class="layui-input-block">
            <input id="userId" class="layui-input" type="label" name="userId" autocomplete="off">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">名称</label>
        <div class="layui-input-block">
            <input id="name" class="layui-input" type="text" name="name" lay-verify="required" autocomplete="off"
                   placeholder="请输入名称">
        </div>
    </div>

    <div id="account_layout" class="layui-form-item">
        <label class="layui-form-label">账号</label>
        <div class="layui-input-block">
            <input id="login_name" class="layui-input" type="text" name="login_name" lay-verify="required"
                   autocomplete="off"
                   placeholder="请输入账号">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
            <input id="password" class="layui-input" type="text" name="password" autocomplete="off"
                   placeholder="请输入密码">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-block">
            <input id="phone" class="layui-input" type="text" name="phone" autocomplete="off"
                   placeholder="请输入账号">
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

<form id="edit_user_role" style="display:none;padding: 5px;">
    <div id="edit_userrole_tree" class="layui-collapse sy-layui-collapse" lay-filter="user_role">
        <%--<div class="layui-colla-item sy-layui-colla-item">--%>
        <%--<h2 class="layui-colla-title sy-layui-colla-title"></h2>--%>
        <%--<div class="sy-colla-checkbox">--%>
        <%--<input type="checkbox" name="" title=" 父权限1" lay-skin="primary">--%>
        <%--<span>父权限1</span>--%>
        <%--</div>--%>
        <%--<div class="sy-colla-blank0px"></div>--%>
        <%--<div class="layui-colla-content sy-layui-colla-content">--%>
        <%--<ul class="sy-colla-submenu">--%>
        <%--<li><input type="checkbox" name="" title=" 子权限1" lay-skin="primary"><span>子权限1</span></li>--%>
        <%--<li><input type="checkbox" name="" title=" 子权限2" lay-skin="primary"><span>子权限2</span></li>--%>
        <%--</ul>--%>
        <%--</div>--%>
        <%--<div class="sy-colla-blank0px"></div>--%>
        <%--</div>--%>
    </div>
</form>

<script type="text/html" id="barTool">
    <a class="layui-btn layui-btn-mini" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-danger layui-btn-mini" lay-event="upd">更新角色</a>
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
            id: 'user_talbe',
            elem: '#user_talbe', // 指定原始表格元素选择器（推荐id选择器）
            height: 700, // 容器高度
            cols: [[
                // {field: 'id', title: 'ID', width: 150, align: 'center'},
                {field: 'name', title: '名称', width: 200, align: 'center'},
                {field: 'login_name', title: '账号', width: 300, align: 'center'},
                // {field: 'password', title: '密码', width: 200, align: 'center'},
                {field: 'phone', title: '电话', width: 200, align: 'center'},
                {field: 'create_date', title: '创建日期', width: 200, align: 'center', templet: '#dateTpl'},
                {field: 'del_flag', title: '是否有效', width: 200, align: 'center', templet: '#validateTpl'},
                {field: 'remarks', title: '备注', width: 200, align: 'center'},
                <shiro:hasPermission name="user/updateUser">
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
            url: ctx + '/user/getUser',
        });

        table.on('tool(userTable)', function (obj) {
            if (obj.event == 'del') {
                // 设置模块无效
                deleteUser(obj);
            } else if (obj.event == 'upd') {
                // 编辑用户角色
                updateUserRole(obj);
            } else {
                // 编辑模块
                editUser(obj);
            }
        });
    });
</script>

<script src="${ctx}/static/js/user.js" charset="utf-8"></script>
</body>
</html>
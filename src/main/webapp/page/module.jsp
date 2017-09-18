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

<shiro:hasRole name="admin">
    <button class="layui-btn layui-btn-small" onclick="addModule()">
        <i class="layui-icon">&#xe608;</i> 添加
    </button>

    <div id="module_talbe" lay-filter="moduleTable"></div>

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
</shiro:hasRole>
<shiro:lacksRole name="admin">
    <div class="layui-form-item">
        <label class="layui-form-label">无权查看</label>
    </div>
</shiro:lacksRole>
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
<script src="${ctx}/static/js/module.js" charset="utf-8"></script>
</body>
</html>
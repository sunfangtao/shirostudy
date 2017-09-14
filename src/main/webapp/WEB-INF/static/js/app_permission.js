layui.use(['table', 'layer', 'form', 'element'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;

    // 执行渲染
    table.render({
        id: 'permission_table',
        elem: '#permission_table', // 指定原始表格元素选择器（推荐id选择器）
        height: 500, // 容器高度
        cols: [[
            // {field: 'id', title: 'ID', width: 150, align: 'center'},
            {field: 'type', title: '映射标识', width: 200, align: 'center'},
            {field: 'url', title: '资源地址', width: 240, align: 'center'},
            {field: 'is_user', title: '是否需要登录', width: 200, align: 'center',templet: '#validateTpl'},
            {field: 'remarks', title: '备注', width: 200, align: 'center'},
            {fixed: 'right', width: 150, align: 'center', toolbar: '#barTool'}
        ]], // 设置表头
        request: {
            pageName: 'page', // 页码的参数名称，默认：page
            limitName: 'pageSize' // 每页数据量的参数名，默认：limit
        },
        limit: 10,
        even: true,
        page: true,
        limits: [10, 15, 20],
        url: ctx + '/appPermission/getAllAppPermissions',
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

});

/**
 * 重新渲染角色表格
 */
function reloadTable() {
    var table = layui.table;
    var $ = layui.jquery;
    table.reload('permission_table', {
        limit: 10,
        even: true,
        page: true,
    });
}

/**
 * 重置角色信息表单
 */
function resetForm() {
    layui.jquery('#edit_permission')[0].reset();
}

/**
 * 删除角色信息
 * @param obj
 */
function deletePermission(obj) {
    if (obj.data.is_user == 1) {
        layer.msg("无需重复操作！", {time: 800});
        return;
    }
    layer.confirm('需要登录才能使用?', function (index) {
        var $ = layui.jquery;
        $.ajax({
            cache: false,
            type: 'post',
            url: ctx + "/appPermission/updateAppPermission",
            async: false,
            data: {
                id: obj.data.id,
                is_user: 1
            },
            error: function (request) {
                layer.msg("更新失败!", {time: 1500});
            },
            success: function (json) {
                if ($('#validate_type').val() == 2) {
                    // 同步更新缓存对应的值
                    obj.update({
                        is_user: 1
                    });
                } else {
                    reloadTable();
                }
                layer.close(index);
                layer.msg("更新成功!", {time: 800});
            }
        });
    });
}

/**
 * 编辑权限
 * @param obj
 */
function editPermission(obj) {
    var $ = layui.jquery;
    resetForm();
    layer.open({
        title: "编辑权限信息",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['保存'],
        area: ['400px'],
        content: $("#edit_permission"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/appPermission/updateAppPermission',
                data: $("#edit_permission").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        // 同步更新缓存对应的值
                        obj.update({
                            remarks: $("#remarks").val(),
                            is_user: $("#del_flag").prop('checked') ? 1 : 0,
                            url: $("#url").val(),
                            type: $("#type").val(),
                        });
                        layer.close(index);
                        layer.msg(data.data.info, {time: 800});
                    } else {
                        layer.msg(data.message, {time: 1500});
                    }
                },
                error: function (request) {
                    layer.msg("修改失败!", {time: 1500});
                }
            });
        },
        success: function (layero, index) {
            $("#id").val(obj.data.id);
            $("#url").val(obj.data.url);
            $("#type").val(obj.data.type);
            $("#remarks").val(obj.data.remarks);
            if (obj.data.del_flag == 0) {
                $("#is_user").removeProp("checked");
                layui.form.render('checkbox', 'switchPermission');
            }
        }
    });
}

/**
 * 新增权限
 */
function addAppPermission() {
    var $ = layui.jquery;
    resetForm();
    layer.open({
        title: "新建权限信息",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['新增'],
        area: ['400px'],
        content: $("#edit_permission"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/appPermission/addAppPermission',
                data: $("#edit_permission").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        // 刷新表格
                        reloadTable();
                        layer.close(index);
                        layer.msg(data.data.info, {time: 800});
                    } else {
                        layer.msg(data.message, {time: 1500});
                    }
                },
                error: function (request) {
                    layer.msg("新增失败!", {time: 1500});
                }
            });
        }
    });
}
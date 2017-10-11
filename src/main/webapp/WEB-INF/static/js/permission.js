layui.use(['table', 'layer', 'form', 'element'], function () {
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
            {field: 'name', title: '权限别名', width: 200, align: 'center'},
            {field: 'permission', title: '权限标识', width: 300, align: 'center'},
            {field: 'url', title: '资源地址', width: 300, align: 'center'},
            {field: 'type', title: '映射标识', width: 300, align: 'center'},
            {field: 'module', title: '模块名称', width: 240, align: 'center'},
            // {field: 'create_by', title: '创建者', width: 200, align: 'center'},
            // {field: 'create_date', title: '创建日期', width: 200, align: 'center', templet: '#dateTpl'},
            // {field: 'update_by', title: '更新者', width: 200, align: 'center'},
            // {field: 'update_date', title: '更新日期', width: 200, align: 'center', templet: '#dateTpl'},
            {field: 'del_flag', title: '是否有效', width: 200, align: 'center', templet: '#validateTpl'},
            {field: 'remarks', title: '备注', width: 200, align: 'center'},
            {fixed: 'right', width: 150, align: 'center', toolbar: '#barTool'}
        ]], // 设置表头
        request: {
            pageName: 'page', // 页码的参数名称，默认：page
            limitName: 'pageSize' // 每页数据量的参数名，默认：limit
        },
        limit: 15,
        even: true,
        page: true,
        limits: [10, 15, 20],
        url: ctx + '/rolePermission/getAllPermissions',
        where: {
            del_flag: $('#permission_validate_select').val()
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

    form.on('select(permission_validate_select)', function (data) {
        reloadTable();
    });

    form.on('select(module_select)', function (data) {
        reloadTable();
    });

    selectionInit();
});

/**
 * 初始化模块下拉
 */
function selectionInit() {
    var $ = layui.jquery;

    $.ajax({
        type: 'post',
        url: ctx + "/module/getAllModule",
        async: false,
        dataType: 'json',
        data: {
            isAll: true
        },
        error: function (request) {
            layer.msg("模块获取失败!", {time: 1500});
        },
        success: function (data) {
            var moduleArr = data.data;
            for (var i = 0; i < moduleArr.length; i++) {
                var result = moduleArr[i];
                $("#module_type").append("<option value=\"" + result.id + "\">" + result.name + "</option>");
                $("#module_select").append("<option value=\"" + result.id + "\">" + result.name + "</option>");
            }
            var form = layui.form;
            form.render('select');
        }
    });
}

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
        where: {
            del_flag: $('#permission_validate_select').val(),
            moduleId: $('#module_select').val()
        }
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
    if (obj.data.del_flag == 1) {
        layer.msg("无需重复操作！", {time: 800});
        return;
    }

    layer.confirm('真的删除么?', function (index) {
        var $ = layui.jquery;
        $.ajax({
            cache: false,
            type: 'post',
            url: ctx + "/rolePermission/updatePermission",
            async: false,
            data: {
                permissionId: obj.data.id,
                del_flag: 1
            },
            error: function (request) {
                layer.msg("删除失败!", {time: 1500});
            },
            success: function (json) {
                if ($('#validate_type').val() == 2) {
                    // 同步更新缓存对应的值
                    obj.update({
                        del_flag: 1
                    });
                } else {
                    reloadTable();
                }
                layer.close(index);
                layer.msg("删除成功!", {time: 800});
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
                url: ctx + '/rolePermission/updatePermission',
                data: $("#edit_permission").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        if ($('#permission_validate_select').val() == 2) {
                            // 同步更新缓存对应的值
                            obj.update({
                                name: $("#name").val(),
                                remarks: $("#remarks").val(),
                                del_flag: $("#del_flag").prop('checked') ? 1 : 0,
                                permission: $("#permission").val(),
                                url: $("#url").val(),
                                type: $("#type").val(),
                                module_id: $("#module_type").val(),
                                module: $("#module_type").find("option:selected").text()
                            });
                        } else {
                            reloadTable();
                        }
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
            $("#module_type").val(obj.data.module_id);
            $("#permissionId").val(obj.data.id);
            $("#name").val(obj.data.name);
            $("#permission").val(obj.data.permission);
            $("#url").val(obj.data.url);
            $("#type").val(obj.data.type);
            $("#remarks").val(obj.data.remarks);
            if (obj.data.del_flag == 0) {
                $("#del_flag").removeProp("checked");
                layui.form.render('checkbox', 'switchPermission');
            }
        }
    });
}

/**
 * 新增权限
 */
function addPermission() {
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
                url: ctx + '/rolePermission/addPermission',
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
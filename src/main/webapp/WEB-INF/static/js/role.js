layui.use(['table', 'layer', 'form', 'element'], function () {
    var table = layui.table;
    var $ = layui.jquery;
    var form = layui.form;

    // 执行渲染
    table.render({
        id: 'role_talbe',
        elem: '#role_talbe', // 指定原始表格元素选择器（推荐id选择器）
        height: 500, // 容器高度
        cols: [[
            // {field: 'id', title: 'ID', width: 150, align: 'center'},
            {field: 'name', title: '角色名', width: 300, align: 'center'},
            {field: 'create_name', title: '创建者', width: 200, align: 'center'},
            {field: 'create_date', title: '创建日期', width: 200, align: 'center', templet: '#dateTpl'},
            {field: 'del_flag', title: '是否有效', width: 200, align: 'center', templet: '#validateTpl'},
            {field: 'remarks', title: '备注', width: 200, align: 'center'},
            {fixed: 'right', width: 200, align: 'center', toolbar: '#barTool'}
        ]], // 设置表头
        request: {
            pageName: 'page', // 页码的参数名称，默认：page
            limitName: 'pageSize' // 每页数据量的参数名，默认：limit
        },
        limit: 10,
        even: true,
        page: true,
        limits: [10, 15, 20],
        url: ctx + '/rolePermission/roles',
        where: {
            del_flag: $('#validate_type').val()
        }
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

    form.on('select(validate_select)', function (data) {
        reloadTable();
    });
});

/**
 * 重新渲染角色表格
 */
function reloadTable() {
    var table = layui.table;
    var $ = layui.jquery;
    table.reload('role_talbe', {
        limit: 10,
        even: true,
        page: true,
        where: {
            del_flag: $('#validate_type').val()
        }
    });
}

/**
 * 重置角色信息表单
 */
function resetForm() {
    layui.jquery('#edit_role')[0].reset();
    layui.jquery('#edit_role_permission')[0].reset();
}

/**
 * 删除角色信息
 * @param obj
 */
function deleteRole(obj) {
    if (obj.data.del_flag == 1) {
        layer.msg("无需重复操作！", {time: 800});
        return;
    }

    layer.confirm('真的删除么?', function (index) {
        var $ = layui.jquery;
        $.ajax({
            cache: false,
            type: 'post',
            url: ctx + "/rolePermission/updateRole",
            async: false,
            data: {
                roleId: obj.data.id,
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
 * 编辑角色
 * @param obj
 */
function editRole(obj) {
    var $ = layui.jquery;
    resetForm();
    layer.open({
        title: "编辑角色信息",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['保存'],
        area: ['400px'],
        content: $("#edit_role"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/rolePermission/updateRole',
                data: $("#edit_role").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        if ($('#validate_type').val() == 2) {
                            // 同步更新缓存对应的值
                            obj.update({
                                name: $("#name").val(),
                                remarks: $("#remarks").val(),
                                del_flag: $("#del_flag").prop('checked') ? 1 : 0
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
            $("#roleId").val(obj.data.id);
            $("#remarks").val(obj.data.remarks);
            $("#name").val(obj.data.name);
            if (obj.data.del_flag == 0) {
                $("#del_flag").removeProp("checked");
                layui.form.render('checkbox');
            }
        }
    });
}

/**
 * 更新角色权限
 * @param obj
 */
function updateRolePermission(obj) {
    var $ = layui.jquery;
    resetForm();
    rolePermissionInit(obj.data.id);
    layer.open({
        title: "编辑角色权限",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['保存'],
        area: ['400px', '600px'],
        content: $("#edit_role_permission"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/rolePermission/updateRolePermissions',
                data: {
                    roleId: obj.data.id,
                    permissionIds: getCheckedIds()
                },
                success: function (data) {
                    layer.close(index);
                }
            });
        }
    });
}

function rolePermissionInit(roleId) {
    var $ = layui.jquery;
    var form = layui.form;
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: ctx + '/rolePermission/getRolePermissions',
        data: {
            roleId: roleId,
            isHas: true// 将包含所有的可获取的系统权限
        },
        success: function (data) {
            console.info(data);
            if (data.result == "success") {
                $('#edit_role_tree').html("");
                var json = data.data.array;

                var html = "";
                var start = "<div class=\"layui-colla-item sy-layui-colla-item\">"
                start += "<h2 class=\"layui-colla-title sy-layui-colla-title\"></h2>";
                start += "<div class=\"sy-colla-checkbox\">";
                start += "<input type=\"checkbox\" lay-skin=\"primary\""

                for (var key in json) {
                    var content = "";
                    content += ("<span>" + key + "</span></div>");
                    content += "<div class=\"sy-colla-blank0px\"></div>";
                    content += "<div class=\"layui-colla-content sy-layui-colla-content\">";
                    content += "<ul class=\"sy-colla-submenu\">";
                    var value = json[key];
                    var length = value.length;
                    var j = length;

                    for (var i = 0; i < length; i++) {
                        content += "<li><input type=\"checkbox\" lay-skin=\"primary\"";
                        content += (" id=\"" + value[i].id + "\"");
                        if (value[i].isHas) {
                            content += (" checked");
                            j--;
                        }
                        content += ("><span>" + value[i].name + "</span></li>");
                    }
                    content += "</ul></div><div class=\"sy-colla-blank0px\"></div></div>";
                    html += (start + (j == 0 ? " checked>" : ">") + content);
                }
                console.info(html);
                $('#edit_role_tree').html(html);
                layui.element.init();

                var Choice = {
                    checkAll: function () {
                        var selectionButton = $('.sy-colla-checkbox').find(':checkbox');
                        selectionButton.change(function () {
                            var submenu = $(this).parent().siblings('.sy-layui-colla-content').find(':checkbox');
                            if ($(this).prop('checked')) {
                                submenu.prop('checked', true);
                            } else {
                                submenu.prop('checked', false);
                            }
                        });
                        var submenu1 = selectionButton.parent().siblings('.sy-layui-colla-content').find(':checkbox');
                        submenu1.change(function () {
                            var submenu2 = $(this).parents('.sy-colla-submenu').find(':checkbox');
                            var isAllSelected = true;
                            submenu2.each(function () {
                                isAllSelected &= $(this).prop('checked');
                            });
                            var selectionButton1 = $(this).parents('.sy-layui-colla-content').siblings('.sy-colla-checkbox').find(':checkbox');
                            if (isAllSelected) {
                                selectionButton1.prop('checked', true);
                            } else {
                                selectionButton1.prop('checked', false);
                            }
                        });
                        form.render('checkbox'); // 刷新select选择框渲染
                    }
                };
                Choice.checkAll();
            } else {
                layer.msg(data.message, {time: 1500});
            }
        },
        error: function (request) {
            layer.msg("获取失败!", {time: 1500});
        }
    });
}

function getCheckedIds() {
    var $ = layui.jquery;
    var selectionButton = $('.sy-colla-checkbox').find(':checkbox');
    var submenu = selectionButton.parent().siblings('.sy-layui-colla-content').find(':checkbox');

    var ids = "";
    submenu.each(function () {
        ids += ($(this).prop('checked') ? ($(this).prop('id') + "@") : "");
    });
    return ids.substring(0, ids.length - 1);
}

/**
 * 新增角色
 */
function addRole() {
    var $ = layui.jquery;
    resetForm();
    layer.open({
        title: "新建角色信息",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['新增'],
        area: ['400px'],
        content: $("#edit_role"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/rolePermission/addRole',
                data: $("#edit_role").serialize(),
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
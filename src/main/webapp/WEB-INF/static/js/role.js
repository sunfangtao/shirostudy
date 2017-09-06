layui.use(['table', 'layer', 'form'], function () {
    var table = layui.table;
    // 执行渲染
    table.render({
        elem: '#role_talbe', // 指定原始表格元素选择器（推荐id选择器）
        height: 500, // 容器高度
        cols: [[
            // {field: 'id', title: 'ID', width: 150, align: 'center'},
            {field: 'name', title: '角色名', width: 150, align: 'center'},
            {field: 'create_by', title: '创建者', width: 180, align: 'center'},
            {field: 'create_date', title: '创建日期', width: 200, align: 'center', templet: '#dateTpl'},
            {field: 'del_flag', title: '是否有效', width: 120, align: 'center', templet: '#validateTpl'},
            {field: 'remarks', title: '备注', width: 150, align: 'center'},
            {fixed: 'right', width: 180, align: 'center', toolbar: '#barTool'}
        ]], // 设置表头
        request: {
            pageName: 'page', // 页码的参数名称，默认：page
            limitName: 'pageSize' // 每页数据量的参数名，默认：limit
        },
        even: true,
        page: true,
        limits: [10, 15, 20],
        url: '/rolePermission/roles'
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

    table.reload('idTest', {
        where: { // 设定异步数据接口的额外参数，任意设
            aaaaaa: 'xxx',
            bbb: 'yyy'
            //…
        }
    });
});

function resetForm() {
    layui.jquery('#edit_role')[0].reset();
}

/**
 * 删除角色信息
 * @param obj
 */
function deleteRole(obj) {
    layer.confirm('真的删除么?', function (index) {
        layui.jquery.ajax({
            cache: false,
            type: 'post',
            url: "/rolePermission/updateRole",
            async: false,
            data: {
                roleId: obj.data.id,
                del_flag: 1
            },
            error: function (request) {
                layer.msg("删除失败!", {time: 1500});
            },
            success: function (json) {
                // obj.del(); // 删除对应行（tr）的DOM结构
                obj.update({
                    del_flag: 1
                });
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
                url: '/rolePermission/updateRole',
                data: $("#edit_role").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        // 同步更新缓存对应的值
                        obj.update({
                            name: $("#name").val(),
                            remarks: $("#remarks").val(),
                            del_flag: $("#del_flag").prop('checked') ? 1 : 0
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

}

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
                url: '/rolePermission/addRole',
                data: $("#edit_role").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        // 刷新表格
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
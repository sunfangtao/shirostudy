layui.use(['table', 'layer'], function () {
    var table = layui.table;
    table.on('tool(roleTable)', function (obj) {
        if (obj.event == 'del') { // 删除
            deleteRole(obj);
        } else { // 编辑
            editRole(obj);
            // 同步更新缓存对应的值
            obj.update({
                name: '123'
                , title: 'xxx'
            });
        }
    });
});

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
                obj.del(); // 删除对应行（tr）的DOM结构
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
                url: '/rolePermission/updateRole',
                data: $("#edit_role").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        alert(data.message);
                    }
                    layer.msg(data.message, {time: 800});
                    layer.close(index);
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
            $("#del_flag").checked = (obj.data.del_flag == 1 ? false : true);
        }
    });
}
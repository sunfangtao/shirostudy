/**
 * 重新渲染模块表格
 */
function reloadTable() {
    var table = layui.table;
    var $ = layui.jquery;
    table.reload('module_talbe', {
        limit: 15,
        even: true,
        page: true,
    });
}

/**
 * 重置角色信息表单
 */
function resetForm() {
    layui.jquery('#edit_module')[0].reset();
}

/**
 * 删除模块信息
 * @param obj
 */
function deleteModule(obj) {
    if (obj.data.del_flag == 1) {
        layer.msg("无需重复操作！", {time: 800});
        return;
    }
    layer.confirm('真的删除么?', function (index) {
        var $ = layui.jquery;
        $.ajax({
            cache: false,
            type: 'post',
            url: ctx + "/module/updateModule",
            async: false,
            data: {
                moduleId: obj.data.id,
                del_flag: 1
            },
            error: function (request) {
                layer.msg("删除失败!", {time: 1500});
            },
            success: function (json) {
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
 * 编辑模块
 * @param obj
 */
function editModule(obj) {
    var $ = layui.jquery;
    resetForm();
    layer.open({
        title: "编辑模块信息",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['保存'],
        area: ['400px'],
        content: $("#edit_module"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/module/updateModule',
                data: $("#edit_module").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        // 同步更新缓存对应的值
                        obj.update({
                            name: $("#name").val(),
                            address: $("#address").val(),
                            remarks: $("#remarks").val(),
                            del_flag: $("#del_flag").prop('checked') ? 1 : 0,
                            isDirect: $("#is_redict").prop('checked') ? 1 : 0
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
            $("#moduleId").val(obj.data.id);
            $("#address").val(obj.data.address);
            $("#remarks").val(obj.data.remarks);
            $("#name").val(obj.data.name);
            if (obj.data.del_flag == 0) {
                $("#del_flag").removeProp("checked");
                layui.form.render('checkbox');
            }
            if (obj.data.isDirect == 0) {
                $("#is_redict").removeProp("checked");
                layui.form.render('checkbox');
            }
        }
    });
}

/**
 * 新增模块
 */
function addModule() {
    var $ = layui.jquery;
    resetForm();
    layer.open({
        title: "新建模块信息",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['新增'],
        area: ['400px'],
        content: $("#edit_module"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/module/addModule',
                data: $("#edit_module").serialize(),
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
/**
 * 重新渲染模块表格
 */
function reloadTable() {
    var table = layui.table;
    var $ = layui.jquery;
    table.reload('user_talbe', {
        limit: 15,
        even: true,
        page: true,
    });
}

/**
 * 重置角色信息表单
 */
function resetForm() {
    layui.jquery('#edit_user')[0].reset();
}

/**
 * 编辑用户角色
 * @param obj
 */
function updateUserRole(obj) {
    var $ = layui.jquery;
    resetForm();
    roleInit(obj.data.id);
    layer.open({
        title: "编辑用户角色",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['保存'],
        area: ['400px', '600px'],
        content: $("#edit_user_role"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/rolePermission/updateUserRoles',
                data: {
                    roleIds: getCheckedIds(),
                    userId: obj.data.id
                },
                success: function (data) {
                    layer.msg("操作成功!", {time: 800});
                    layer.close(index);
                },
                error: function (request) {
                    layer.msg("删除失败!", {time: 1500});
                }
            });
        }
    });
}

/**
 * 删除模块信息
 * @param obj
 */
function deleteUser(obj) {
    if (obj.data.del_flag == 1) {
        layer.msg("无需重复操作！", {time: 800});
        return;
    }
    layer.confirm('真的删除么?', function (index) {
        var $ = layui.jquery;
        $.ajax({
            cache: false,
            type: 'post',
            url: ctx + "/user/updateUser",
            async: false,
            data: {
                userId: obj.data.id,
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
 * 编辑用户
 * @param obj
 */
function editUser(obj) {
    var $ = layui.jquery;
    resetForm();
    $("#account_layout").css("display", "none");
    layer.open({
        title: "编辑用户信息",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['保存'],
        area: ['400px'],
        content: $("#edit_user"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/user/updateUser',
                data: $("#edit_user").serialize(),
                success: function (data) {
                    if (data.result == "success") {
                        // 同步更新缓存对应的值
                        obj.update({
                            name: $("#name").val(),
                            phone: $("#phone").val(),
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
            $("#userId").val(obj.data.id);
            $("#login_name").val(obj.data.login_name);
            $("#moduleId").val(obj.data.id);
            $("#phone").val(obj.data.phone);
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
 * 新增用户
 */
function addUser() {
    var $ = layui.jquery;
    resetForm();
    $("#account_layout").css("display", "block");
    layer.open({
        title: "新建用户信息",
        type: 1,
        skin: 'layui-layer-rim',
        shadeClose: true,
        btn: ['新增'],
        area: ['400px'],
        content: $("#edit_user"),
        yes: function (index) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: ctx + '/user/addUser',
                data: $("#edit_user").serialize(),
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

function roleInit(userId) {
    var $ = layui.jquery;
    var form = layui.form;
    $.ajax({
        type: 'post',
        dataType: 'json',
        url: ctx + '/rolePermission/userRoles',
        data: {
            userId: userId,
            isHas: true// 将包含所有的可获取的系统权限
        },
        success: function (data) {
            console.info(data);
            if (data.result == "success") {
                $('#edit_userrole_tree').html("");
                var json = data.data.array;

                var html = "";
                var start = "<div class=\"layui-colla-item sy-layui-colla-item sy-layui-colla-item-mar\">";
                start += "<div class=\"sy-colla-checkbox\">";
                start += "<input type=\"checkbox\" lay-skin=\"primary\" id=\"";

                for (var i = 0; i < json.length; i++) {
                    var content = "";
                    content += ("<span>" + json[i].name + "</span></div>");
                    content += "<div class=\"sy-colla-blank0px\"></div></div>";
                    html += (start + json[i].id + "\"" + (json[i].isHas ? " checked>" : ">") + content);
                }
                console.info(html);
                $('#edit_userrole_tree').html(html);
                layui.element.init();
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

    var ids = "";
    selectionButton.each(function () {
        ids += ($(this).prop('checked') ? ($(this).prop('id') + "@") : "");
    });
    return ids.substring(0, ids.length - 1);
}
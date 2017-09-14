layui.use('element', function () {
    var element = layui.element;
    var $ = layui.jquery;

    element.on('nav(nav_bar)', function (data) {
        console.info(data.text());
        if (data.text() == "角色管理") {
            $("#iframe").attr("src", ctx + "/page/role.jsp");
        } else if (data.text() == "权限管理") {
            $("#iframe").attr("src", ctx + "/page/permission.jsp");
        } else if (data.text() == "模块管理") {
            $("#iframe").attr("src", ctx + "/page/module.jsp");
        } else if (data.text() == "用户管理") {
            $("#iframe").attr("src", ctx + "/page/user.jsp");
        } else if (data.text() == "APP连接管理") {
            $("#iframe").attr("src", ctx + "/page/app_permission.jsp");
        }
    });
});
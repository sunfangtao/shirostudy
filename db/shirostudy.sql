/*
Navicat MySQL Data Transfer

Source Server         : 192.168.17.108
Source Server Version : 50173
Source Host           : 192.168.17.108 :3306
Source Database       : shirostudy

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-09-11 18:17:49
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `module_id` varchar(64) NOT NULL COMMENT '业务子服务模块编号',
  `name` varchar(20) NOT NULL COMMENT '别名',
  `permission` varchar(50) NOT NULL COMMENT '权限标识',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(200) DEFAULT '' COMMENT '备注信息',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `id` varchar(64) NOT NULL COMMENT '编号',
  `url` varchar(200) DEFAULT NULL COMMENT '链接地址',
  `type` varchar(50) DEFAULT NULL COMMENT '链接映射标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`),
  UNIQUE KEY `type` (`type`),
  UNIQUE KEY `url` (`url`,`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '获取系统所有角色', 'rolePermission/roles', '1', '2017-09-09 17:24:15', null, null, '获取系统所有角色，与获取用户所有角色区分开', '0', '15797f5a-d598-4406-9537-fc2b4bede158', 'rolePermission/roles', 'rolePermission/roles');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '更新权限基本信息', 'rolePermission/updatePermission', '1', '2017-09-09 17:28:08', null, null, '更新权限基本信息', '0', '1cf7e050-a284-4237-966e-afb6478f49d4', 'rolePermission/updatePermission', 'rolePermission/updatePermission');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '获取角色的权限', 'rolePermission/getRolePermissions', '1', '2017-09-09 17:28:51', null, null, '获取角色的权限', '0', '25651deb-8061-4fc5-9357-4174b839e4f2', 'rolePermission/getRolePermissions', 'rolePermission/getRolePermissions');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '新增模块', 'module/addModule', '1', '2017-09-11 08:39:15', '1', '2017-09-11 08:58:37', '新增模块', '0', '2f90844c-97d7-4c37-a08f-5ba42cebe2f9', 'module/addModule', 'module/addModule');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '新建角色', 'rolePermission/addRole', '1', '2017-09-09 17:20:39', '1', '2017-09-09 17:24:23', '新建角色', '0', '35ac90f4-4992-473f-9d87-561372808ea6', 'rolePermission/addRole', 'rolePermission/addRole');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '更新角色基本信息', 'rolePermission/updateRole', '1', '2017-09-09 17:25:26', null, null, '包括名称等的基本信息，不包括权限', '0', '41d104cc-c02d-4d07-b891-0de19c5f12d1', 'rolePermission/updateRole', 'rolePermission/updateRole');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '获取系统所有模块', 'module/getAllModule', '1', '2017-09-11 08:41:01', '1', '2017-09-11 08:58:31', '获取系统所有模块', '0', '4bc62775-9339-467b-8454-a75b1942d801', 'module/getAllModule', 'module/getAllModule');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '获取资源的权限', 'rolePermission/getResourcePermissions', '1', '2017-09-09 17:29:25', null, null, '获取资源的权限', '0', '5e096a2a-40f9-44a3-8fb8-7a22d550e24c', 'rolePermission/getResourcePermissions', 'rolePermission/getResourcePermissions');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '更新角色权限', 'rolePermission/updateRolePermissions', '1', '2017-09-09 17:26:36', null, null, '更新角色权限，不包括角色基本信息', '0', '5e4b3201-af90-4b56-b23c-0f65cb39d161', 'rolePermission/updateRolePermissions', 'rolePermission/updateRolePermissions');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '获取系统所有权限', 'rolePermission/getAllPermissions', '1', '2017-09-09 17:29:57', null, null, '获取系统所有权限', '0', '63a2833c-e92c-4fec-b62d-42453e8925fe', 'rolePermission/getAllPermissions', 'rolePermission/getAllPermissions');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '更新模块', 'module/updateModule', '1', '2017-09-11 08:40:12', '1', '2017-09-11 08:58:34', '更新模块', '0', '8772170e-3ea8-4a7e-817c-b2ead1ac3d88', 'module/updateModule', 'module/updateModule');
INSERT INTO permission VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '新建权限', 'rolePermission/addPermission', '1', '2017-09-09 17:27:29', null, null, '新建权限', '0', 'fbe5bc98-a946-447a-937f-af1b5fa249c9', 'rolePermission/addPermission', 'rolePermission/addPermission');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `remarks` varchar(200) DEFAULT '' COMMENT '备注信息',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role VALUES ('3355f34e-8a58-490f-bb8d-90b3d674f724', 'sdfg', '1', '2017-09-11 15:54:55', null, '0');

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `permission_id` varchar(64) NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_id` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COMMENT='角色-权限';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO role_permission VALUES ('90', '3355f34e-8a58-490f-bb8d-90b3d674f724', '15797f5a-d598-4406-9537-fc2b4bede158');
INSERT INTO role_permission VALUES ('91', '3355f34e-8a58-490f-bb8d-90b3d674f724', '1cf7e050-a284-4237-966e-afb6478f49d4');
INSERT INTO role_permission VALUES ('92', '3355f34e-8a58-490f-bb8d-90b3d674f724', '25651deb-8061-4fc5-9357-4174b839e4f2');
INSERT INTO role_permission VALUES ('93', '3355f34e-8a58-490f-bb8d-90b3d674f724', '2f90844c-97d7-4c37-a08f-5ba42cebe2f9');
INSERT INTO role_permission VALUES ('94', '3355f34e-8a58-490f-bb8d-90b3d674f724', '35ac90f4-4992-473f-9d87-561372808ea6');
INSERT INTO role_permission VALUES ('95', '3355f34e-8a58-490f-bb8d-90b3d674f724', '41d104cc-c02d-4d07-b891-0de19c5f12d1');
INSERT INTO role_permission VALUES ('96', '3355f34e-8a58-490f-bb8d-90b3d674f724', '4bc62775-9339-467b-8454-a75b1942d801');
INSERT INTO role_permission VALUES ('97', '3355f34e-8a58-490f-bb8d-90b3d674f724', '5e096a2a-40f9-44a3-8fb8-7a22d550e24c');
INSERT INTO role_permission VALUES ('98', '3355f34e-8a58-490f-bb8d-90b3d674f724', '5e4b3201-af90-4b56-b23c-0f65cb39d161');
INSERT INTO role_permission VALUES ('99', '3355f34e-8a58-490f-bb8d-90b3d674f724', '63a2833c-e92c-4fec-b62d-42453e8925fe');
INSERT INTO role_permission VALUES ('100', '3355f34e-8a58-490f-bb8d-90b3d674f724', '8772170e-3ea8-4a7e-817c-b2ead1ac3d88');
INSERT INTO role_permission VALUES ('101', '3355f34e-8a58-490f-bb8d-90b3d674f724', 'fbe5bc98-a946-447a-937f-af1b5fa249c9');

-- ----------------------------
-- Table structure for `sub_server`
-- ----------------------------
DROP TABLE IF EXISTS `sub_server`;
CREATE TABLE `sub_server` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '子服务器名称',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `address` varchar(100) DEFAULT NULL COMMENT '子服务器访问地址',
  `remarks` varchar(200) DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sub_server
-- ----------------------------
INSERT INTO sub_server VALUES ('8d3a502a-a039-4bef-849e-09aa8ea0fa22', '角色权限模块', '1', '2017-09-09 17:15:09', '0', 'http://221.0.91.34:4200/rolePermission/', '角色权限管理模块，负责新建角色、权限、服务模块，以及分配角色的权限等功能');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '编号',
  `parent_id_set` text,
  `parent_id` varchar(64) NOT NULL COMMENT '创建者编号',
  `login_name` varchar(40) NOT NULL COMMENT '登录名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `longtitude` double DEFAULT NULL COMMENT '经度',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `photo` varchar(200) DEFAULT NULL COMMENT '头像',
  `login_ip` varchar(20) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `remarks` varchar(200) DEFAULT '' COMMENT '备注信息',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标记',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO sys_user VALUES ('1', '0', '0', 's123456789', '248698d0841cf61fad3d0e4e6d538fda', '贝格角色权限管理员', '', null, null, '莱山区明达西路11号', null, null, null, '2017-08-28 11:18:45', '0', '2017-09-11 15:20:56', '1', '', '0', null);
INSERT INTO sys_user VALUES ('2', '1', '1', 'bgxxh', '248698d0841cf61fad3d0e4e6d538fda', '贝格最高级管理', '185621728', null, null, '莱山区明达西路11号', null, null, null, '2017-09-09 10:06:41', '0', '2017-09-11 15:27:08', '1', '测试1', '0', null);

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` varchar(64) NOT NULL COMMENT '用户编号',
  `role_id` varchar(64) NOT NULL COMMENT '角色编号',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='用户-角色';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO user_role VALUES ('10', '1', '3355f34e-8a58-490f-bb8d-90b3d674f724', '0', '2017-09-11 18:04:14');
INSERT INTO user_role VALUES ('13', '2', '3355f34e-8a58-490f-bb8d-90b3d674f724', '1', '2017-09-11 18:16:37');

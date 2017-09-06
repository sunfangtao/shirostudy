/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : shirostudy

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-06 18:30:38
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
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注信息',
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

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `name` varchar(20) NOT NULL COMMENT '角色名称',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role VALUES ('56653077-06d9-42a6-a6c8-c6e748fa8d2b', '贝格超级管理员', '就不告诉你', '2017-09-01 15:50:28', '这是备注这是备注这是备注', '1');
INSERT INTO role VALUES ('a97385e1-4562-4373-95de-2f56e4cdb530', '贝格二级管理员', '就不告诉你', '2017-09-06 14:31:38', '测试角色', '1');
INSERT INTO role VALUES ('aaf01d7d-79b0-4ca4-8085-cac3f311b0a1', '贝格四级管理员', '就不告诉你', '2017-09-06 14:53:38', '还要测试', '1');
INSERT INTO role VALUES ('c5b84517-95c1-4274-9a22-36c59bccf290', '贝格三级管理员', '就不告诉你', '2017-09-06 14:51:10', '再次测试', '1');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-权限';

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `server_module`
-- ----------------------------
DROP TABLE IF EXISTS `server_module`;
CREATE TABLE `server_module` (
  `id` varchar(64) NOT NULL COMMENT '模块编号',
  `server_id` varchar(64) NOT NULL COMMENT '子服务器编号',
  `name` varchar(20) NOT NULL COMMENT '模块名称',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`server_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子服务器中的模块信息';

-- ----------------------------
-- Records of server_module
-- ----------------------------

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
  `address` varchar(100) NOT NULL COMMENT '子服务器访问地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sub_server
-- ----------------------------

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
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `del_flag` tinyint(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO sys_user VALUES ('就不告诉你', '0', '0', 's123456789', '248698d0841cf61fad3d0e4e6d538fda', '贝格管理员', '18562172893', null, null, '莱山区明达西路11号', null, null, null, '2017-08-28 11:18:45', '0', null, null, '最高级管理员', '0');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色';

-- ----------------------------
-- Records of user_role
-- ----------------------------

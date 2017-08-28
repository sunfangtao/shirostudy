/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : shirostudy

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-08-28 17:36:58
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `server_id` varchar(64) NOT NULL COMMENT '业务子服务编号',
  `name` varchar(20) NOT NULL COMMENT '别名',
  `permission` varchar(50) NOT NULL COMMENT '权限标识',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建日期',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO permission VALUES ('24f7eeec-6701-4d05-b75b-07f351c57a19', '1', '删除', 'wwwe', '111', '2017-08-25 17:48:06', null, null, null, '0');
INSERT INTO permission VALUES ('73b748e4-d82d-496e-a69a-baa05ebea322', '1', '添加', 'www', '111', '2017-08-25 17:48:06', null, null, null, '0');
INSERT INTO permission VALUES ('b7ec6d88-ce25-4e72-adf0-a5b67f29ac1f', '1', '更新', 'wws', '111', '2017-08-25 17:48:05', null, null, null, '0');

-- ----------------------------
-- Table structure for `plat_user`
-- ----------------------------
DROP TABLE IF EXISTS `plat_user`;
CREATE TABLE `plat_user` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '编号',
  `login_name` varchar(40) NOT NULL COMMENT '登录名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(11) NOT NULL COMMENT '手机',
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
-- Records of plat_user
-- ----------------------------
INSERT INTO plat_user VALUES ('1', 's123456789', '248698d0841cf61fad3d0e4e6d538fda', 'q', 'q', null, '185', null, null, null, '2017-08-28 11:18:45', '1', null, null, null, '0');

-- ----------------------------
-- Table structure for `resource_permission`
-- ----------------------------
DROP TABLE IF EXISTS `resource_permission`;
CREATE TABLE `resource_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `permission` varchar(100) NOT NULL COMMENT '权限',
  `url` varchar(100) NOT NULL COMMENT '连接',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='资源匹配';

-- ----------------------------
-- Records of resource_permission
-- ----------------------------
INSERT INTO resource_permission VALUES ('2', 'wwwe', '/loginController/logi2', '0');
INSERT INTO resource_permission VALUES ('6', 'sss', '/loginControllr/loin2', '0');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role VALUES ('0900bdb1-da45-497a-9402-60f963945ce9', '普通管理员', '111', '2017-08-25 17:42:34', '没有备注', '0');
INSERT INTO role VALUES ('0d319f6c-af0c-4bb2-8e11-cad2d7a300dc', '普通管理员', '111', '2017-08-25 17:43:59', '没有备注', '0');
INSERT INTO role VALUES ('14fe5c65-35eb-4037-8fef-a1b70c5ce4a7', '普通管理员', '111', '2017-08-25 17:46:22', '没有备注', '0');
INSERT INTO role VALUES ('281434d6-294e-4a64-a26b-37e81e640c61', '用户', '111', '2017-08-25 17:48:05', '没有备注', '0');
INSERT INTO role VALUES ('2990c8f1-8e74-47a3-b9b8-e8ca8713c2b6', '用户', '111', '2017-08-25 17:46:22', '没有备注', '0');
INSERT INTO role VALUES ('353a0dad-cb65-4ae0-8bea-ac0a848de339', '用户', '111', '2017-08-25 17:43:59', '没有备注', '0');
INSERT INTO role VALUES ('5f82b7f4-f760-4a2e-966e-f1aa833cf8a8', '超级管理员', '111', '2017-08-25 17:46:22', '没有备注', '0');
INSERT INTO role VALUES ('64fc3c83-6052-4339-8d89-72372afc8580', '超级管理员', '111', '2017-08-25 17:42:34', '没有备注', '0');
INSERT INTO role VALUES ('6aabcdba-4b2c-420f-88e1-6947f2c463ad', '超级管理员', '111', '2017-08-25 17:43:58', '没有备注', '0');
INSERT INTO role VALUES ('89f2fa0a-8796-4a7b-8ee5-1486d09bcfd5', '普通管理员', '111', '2017-08-25 17:48:05', '没有备注', '0');
INSERT INTO role VALUES ('97b13d6f-0306-4a26-8560-4661b3aa0b0e', '超级管理员', '111', '2017-08-25 17:48:05', '没有备注', '0');
INSERT INTO role VALUES ('bd6ceeed-5d00-400b-b129-3d4d2227c87d', '用户', '111', '2017-08-25 17:42:34', '没有备注', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色-权限';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO role_permission VALUES ('1', '0900bdb1-da45-497a-9402-60f963945ce9', '24f7eeec-6701-4d05-b75b-07f351c57a19');
INSERT INTO role_permission VALUES ('2', '0900bdb1-da45-497a-9402-60f963945ce9', '73b748e4-d82d-496e-a69a-baa05ebea322');
INSERT INTO role_permission VALUES ('3', '0900bdb1-da45-497a-9402-60f963945ce9', 'b7ec6d88-ce25-4e72-adf0-a5b67f29ac1f');

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sub_server
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户-角色';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO user_role VALUES ('1', '1', '0900bdb1-da45-497a-9402-60f963945ce9', '1', '2017-08-28 11:23:20');

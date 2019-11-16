CREATE SCHEMA IF NOT EXISTS `dragonshard_sample_custom_2`
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

USE `dragonshard_sample_custom_2`;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
  `id`            bigint UNSIGNED UNIQUE NOT NULL,
  `login_name`    varchar(16)            NOT NULL COMMENT '登陆名',
  `password`      varchar(256)           NOT NULL COMMENT '密码',
  `nickname`      varchar(50)            NOT NULL COMMENT '用户名',
  `email`         varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status`        smallint(2)  DEFAULT 0 NOT NULL COMMENT '状态 0：禁用 1：正常',
  `create_time`   datetime(3)            NOT NULL COMMENT '创建时间',
  `modified_time` datetime(3)            NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  COMMENT ='系统用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
delete
from sys_user
where id > 0;
INSERT INTO `sys_user`
VALUES ('1', 'user_custom_2-1', 'password', 'Custom_2-1', 'user_custom_2-1@dragonshard.net', '1',
        '2018-11-05 17:19:05',
        '2018-12-13 15:04:04'),
       ('2', 'user_custom_2-2', 'password', 'Custom_2-2', 'user_custom_2-2@dragonshard.net', '1',
        '2018-11-05 17:19:06',
        '2018-12-13 15:04:05'),
       ('3', 'user_custom_2-3', 'password', 'Custom_2-3', 'user_custom_2-3@dragonshard.net', '1',
        '2018-11-05 17:19:07',
        '2018-12-13 15:04:06'),
       ('4', 'user_custom_2-4', 'password', 'Custom_2-4', 'user_custom_2-4@dragonshard.net', '1',
        '2018-11-05 17:19:08',
        '2018-12-13 15:04:07');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
  `id`            bigint UNSIGNED AUTO_INCREMENT NOT NULL,
  `role_name`     varchar(64)                    NOT NULL COMMENT '角色名称',
  `create_time`   datetime(3)                    NOT NULL COMMENT '创建时间',
  `modified_time` datetime(3)                    NOT NULL COMMENT '修改时间',
  `remark`        varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  COMMENT ='角色表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
delete
from sys_role
where id > 0;
INSERT INTO `sys_role`
VALUES ('1', 'super-admin-role-custom_2', '2018-11-12 15:59:43', '2018-11-12 15:59:47',
        'super-admin-role-custom_2'),
       ('2', 'admin-role-custom_2-1', '2018-11-12 16:00:17', '2018-11-12 16:00:19',
        'admin-role-custom_2-1'),
       ('3', 'admin-role-custom_2-2', '2018-11-12 16:00:18', '2018-11-12 16:00:20',
        'admin-role-custom_2-2'),
       ('4', 'admin-role-custom_2-3', '2018-11-12 16:00:19', '2018-11-12 16:00:21',
        'admin-role-custom_2-3');
COMMIT;


-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`
(
  `id`      bigint UNSIGNED UNIQUE NOT NULL,
  `uid`     bigint UNSIGNED DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint UNSIGNED DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  COMMENT ='系统用户角色关系表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
delete
from sys_user_role
where id > 0;
INSERT INTO `sys_user_role`
VALUES ('1', '1', '1'),
       ('2', '2', '2'),
       ('3', '3', '3'),
       ('4', '4', '4');
COMMIT;





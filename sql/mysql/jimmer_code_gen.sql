/*
 Navicat Premium Data Transfer

 Source Server         : localhost3307
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3307
 Source Schema         : jimmer_code_gen

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 09/10/2023 11:16:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_source`;
CREATE TABLE `gen_data_source`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `type`          varchar(500) NOT NULL COMMENT '数据库类型',
    `name`          varchar(500) NOT NULL COMMENT '名称',
    `host`          varchar(500) NOT NULL COMMENT '主机',
    `port`          varchar(500) NOT NULL COMMENT '端口',
    `url_suffix`    varchar(500) NOT NULL COMMENT '链接后缀',
    `username`      varchar(500) NOT NULL COMMENT '用户名',
    `password`      varchar(500) NOT NULL COMMENT '密码',
    `order_key`     bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成数据源'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_schema
-- ----------------------------
DROP TABLE IF EXISTS `gen_schema`;
CREATE TABLE `gen_schema`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `data_source_id` bigint       NOT NULL COMMENT '数据源 ID',
    `name`           varchar(500) NOT NULL COMMENT '名称',
    `order_key`      bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`   datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`  datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`         varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_schema_data_source` (`data_source_id`) USING BTREE,
    CONSTRAINT `fk_schema_data_source` FOREIGN KEY (`data_source_id`) REFERENCES `gen_data_source` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成数据架构'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `schema_id`     bigint       NOT NULL COMMENT '数据架构',
    `name`          varchar(500) NOT NULL COMMENT '表名称',
    `comment`       varchar(500) NOT NULL COMMENT '表注释',
    `type`          varchar(500) NOT NULL COMMENT '表种类',
    `order_key`     bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_table_schema` (`schema_id`) USING BTREE,
    CONSTRAINT `fk_table_schema` FOREIGN KEY (`schema_id`) REFERENCES `gen_schema` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_column`;
CREATE TABLE `gen_column`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id`          bigint       NOT NULL COMMENT '归属表',
    `order_key`         bigint       NOT NULL COMMENT '列在表中顺序',
    `name`              varchar(500) NOT NULL COMMENT '列名称',
    `type_code`         int(0)       NOT NULL COMMENT '列对应 JDBCType 码值',
    `type`              varchar(500) NOT NULL COMMENT '列类型',
    `display_size`      bigint       NOT NULL DEFAULT 0 COMMENT '列展示长度',
    `numeric_precision` bigint       NOT NULL DEFAULT 0 COMMENT '列精度',
    `default_value`     varchar(500) NULL     DEFAULT NULL COMMENT '列默认值',
    `comment`           varchar(500) NOT NULL COMMENT '列注释',
    `is_pk`             tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否主键',
    `is_auto_increment` tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否自增',
    `is_fk`             tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否外键',
    `is_unique`         tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否唯一索引',
    `is_not_null`       tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否非空',
    `created_time`      datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`     datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`            varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_column_table` (`table_id`) USING BTREE,
    CONSTRAINT `fk_column_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成列'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_association
-- ----------------------------
DROP TABLE IF EXISTS `gen_association`;
CREATE TABLE `gen_association`
(
    `id`                bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `comment`           varchar(500)                                                   NOT NULL DEFAULT '' COMMENT '关联注释',
    `source_column_id`  bigint                                                         NOT NULL COMMENT '主列',
    `target_column_id`  bigint                                                         NOT NULL COMMENT '从列',
    `association_type`  enum ('ONE_TO_ONE','ONE_TO_MANY','MANY_TO_ONE','MANY_TO_MANY') NOT NULL COMMENT '关联类型',
    `dissociate_action` enum ('NONE','SET_NULL','DELETE')                              NULL     DEFAULT NULL COMMENT '脱钩行为',
    `is_fake`           tinyint(1)                                                     NOT NULL DEFAULT 1 COMMENT '是否伪外键',
    `order_key`         bigint                                                         NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`      datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`     datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`            varchar(500)                                                   NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_association_source_column` (`source_column_id`) USING BTREE,
    INDEX `fk_association_target_column` (`target_column_id`) USING BTREE,
    CONSTRAINT `fk_association_source_column` FOREIGN KEY (`source_column_id`) REFERENCES `gen_column` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_association_target_column` FOREIGN KEY (`target_column_id`) REFERENCES `gen_column` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_package
-- ----------------------------
DROP TABLE IF EXISTS `gen_package`;
CREATE TABLE `gen_package`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `parent_id`     bigint       NULL     DEFAULT NULL COMMENT '父包',
    `name`          varchar(500) NOT NULL COMMENT '包名称',
    `order_key`     bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_package_parent` (`parent_id`) USING BTREE,
    CONSTRAINT `fk_package_parent` FOREIGN KEY (`parent_id`) REFERENCES `gen_package` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成包'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_entity
-- ----------------------------
DROP TABLE IF EXISTS `gen_entity`;
CREATE TABLE `gen_entity`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `package_id`    bigint       NULL     DEFAULT NULL COMMENT '所属包',
    `table_id`      bigint       NOT NULL COMMENT '对应表',
    `name`          varchar(500) NOT NULL COMMENT '类名称',
    `comment`       varchar(500) NOT NULL COMMENT '类注释',
    `author`        varchar(500) NOT NULL DEFAULT '' COMMENT '作者',
    `is_add`        tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否生成添加功能',
    `is_edit`       tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否生成编辑功能',
    `is_list`       tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否生成列表功能',
    `is_query`      tinyint(1)   NOT NULL DEFAULT 1 COMMENT '是否生成查询功能',
    `order_key`     bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `u_entity_table` (`table_id`) USING BTREE,
    INDEX `fk_entity_package` (`package_id`) USING BTREE,
    INDEX `fk_entity_table` (`table_id`) USING BTREE,
    CONSTRAINT `fk_entity_package` FOREIGN KEY (`package_id`) REFERENCES `gen_package` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
    CONSTRAINT `fk_entity_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成实体'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_enum
-- ----------------------------
DROP TABLE IF EXISTS `gen_enum`;
CREATE TABLE `gen_enum`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `package_id`    bigint       NULL     DEFAULT NULL COMMENT '所属包',
    `name`          varchar(500) NOT NULL COMMENT '枚举名',
    `comment`       varchar(500) NOT NULL COMMENT '枚举注释',
    `order_key`     bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_enum_package` (`package_id`) USING BTREE,
    CONSTRAINT `fk_enum_package` FOREIGN KEY (`package_id`) REFERENCES `gen_package` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成枚举'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_enum_item
-- ----------------------------
DROP TABLE IF EXISTS `gen_enum_item`;
CREATE TABLE `gen_enum_item`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `enum_id`       bigint       NOT NULL COMMENT '对应枚举 ID',
    `name`          varchar(500) NOT NULL COMMENT '元素名',
    `value`         varchar(500) NOT NULL COMMENT '元素值',
    `comment`       varchar(500) NOT NULL DEFAULT '' COMMENT '元素注释',
    `created_time`  datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_enum_item_enum` (`enum_id`) USING BTREE,
    CONSTRAINT `fk_enum_item_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成枚举元素'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_property
-- ----------------------------
DROP TABLE IF EXISTS `gen_property`;
CREATE TABLE `gen_property`
(
    `id`                     bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `entity_id`              bigint                                                         NOT NULL COMMENT '归属实体',
    `column_id`              bigint                                                         NULL     DEFAULT NULL COMMENT '对应列',
    `name`                   varchar(500)                                                   NOT NULL COMMENT '属性名',
    `comment`                varchar(500)                                                   NOT NULL COMMENT '属性注释',
    `type`                   varchar(500)                                                   NOT NULL COMMENT '属性类型',
    `type_table_id`          bigint                                                         NULL     DEFAULT NULL COMMENT '类型对应表',
    `is_list`                tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否列表',
    `is_not_null`            tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否非空',
    `is_id`                  tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否Id',
    `id_generation_type`     enum ('AUTO','USER','IDENTITY','SEQUENCE')                     NULL     DEFAULT NULL COMMENT 'Id 生成类型',
    `is_key`                 tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否为业务键属性',
    `is_logical_delete`      tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否为逻辑删除属性',
    `is_id_view`             tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否为 ID 视图属性',
    `id_view_annotation`     varchar(500)                                                   NULL     DEFAULT NULL COMMENT 'ID 视图注释',
    `association_type`       enum ('ONE_TO_ONE','ONE_TO_MANY','MANY_TO_ONE','MANY_TO_MANY') NULL     DEFAULT NULL COMMENT '关联类型',
    `association_annotation` varchar(500)                                                   NULL     DEFAULT NULL COMMENT '关联注释',
    `dissociate_annotation`  varchar(500)                                                   NULL     DEFAULT NULL COMMENT '脱钩注释',
    `other_annotation`       varchar(500)                                                   NULL     DEFAULT NULL COMMENT '其他注释',
    `enum_id`                bigint                                                         NULL     DEFAULT NULL COMMENT '对应枚举 ID',
    `created_time`           datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`          datetime(0)                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`                 varchar(500)                                                   NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `fk_property_column` (`column_id`) USING BTREE,
    INDEX `fk_property_entity` (`entity_id`) USING BTREE,
    INDEX `fk_property_enum` (`enum_id`) USING BTREE,
    INDEX `fk_property_type_table` (`type_table_id`) USING BTREE,
    CONSTRAINT `fk_property_column` FOREIGN KEY (`column_id`) REFERENCES `gen_column` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_entity` FOREIGN KEY (`entity_id`) REFERENCES `gen_entity` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_type_table` FOREIGN KEY (`type_table_id`) REFERENCES `gen_table` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成属性'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS `gen_type_mapping`;
CREATE TABLE `gen_type_mapping`
(
    `id`              bigint       NOT NULL,
    `type_expression` varchar(500) NOT NULL COMMENT '类型表达式',
    `is_regex`        tinyint(1)   NOT NULL DEFAULT 0 COMMENT '是否正则',
    `property_type`   varchar(500) NOT NULL COMMENT '属性类型',
    `order_key`       bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`    datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`   datetime(0)  NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`          varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '列到属性类型映射'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
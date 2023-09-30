/*
 Navicat Premium Data Transfer

 Source Server         : localhost3307
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3307
 Source Schema         : jimmer-code-gen

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 28/09/2023 12:09:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_association
-- ----------------------------
DROP TABLE IF EXISTS `gen_association`;
CREATE TABLE `gen_association`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '关联注释',
  `source_column_id` bigint(0) NOT NULL COMMENT '主列',
  `target_column_id` bigint(0) NOT NULL COMMENT '从列',
  `association_type` enum('OneToOne','OneToMany','ManyToOne','ManyToMany') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '关联类型',
  `dissociate_action` enum('NONE','SET_NULL','DELETE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '脱钩行为',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_association_source_column`(`source_column_id`) USING BTREE,
  INDEX `fk_association_target_column`(`target_column_id`) USING BTREE,
  CONSTRAINT `fk_association_source_column` FOREIGN KEY (`source_column_id`) REFERENCES `gen_column` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_association_target_column` FOREIGN KEY (`target_column_id`) REFERENCES `gen_column` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_column`;
CREATE TABLE `gen_column`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_id` bigint(0) NOT NULL COMMENT '归属表',
  `order_key` bigint(0) NOT NULL COMMENT '列在表中顺序',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列名称',
  `type_code` int(0) NOT NULL COMMENT '列对应 JDBCType 码值',
  `type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列类型',
  `display_size` bigint(0) NOT NULL DEFAULT 0 COMMENT '列展示长度',
  `numeric_precision` bigint(0) NOT NULL DEFAULT 0 COMMENT '列精度',
  `default_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '列默认值',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列注释',
  `is_pk` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否主键（1是）',
  `is_auto_increment` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否自增（1是）',
  `is_fk` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否外键（1是）',
  `is_unique` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否唯一索引（1是）',
  `is_not_null` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否非空（1是）',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_column_table`(`table_id`) USING BTREE,
  CONSTRAINT `fk_column_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成列' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
DROP TABLE IF EXISTS `gen_data_source`;
CREATE TABLE `gen_data_source`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库类型',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `host` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主机',
  `port` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '端口',
  `username` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成数据源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_entity
-- ----------------------------
DROP TABLE IF EXISTS `gen_entity`;
CREATE TABLE `gen_entity`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `package_id` bigint(0) NULL COMMENT '所属包',
  `table_id` bigint(0) NULL COMMENT '对应表',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类名称',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类注释',
  `author` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '作者',
  `gen_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `is_add` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否生成添加功能（1是）',
  `is_edit` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否生成编辑功能（1是）',
  `is_list` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否生成列表功能（1是）',
  `is_query` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否生成查询功能（1是）',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_entity_package`(`package_id`) USING BTREE,
  INDEX `fk_entity_table`(`table_id`) USING BTREE,
  CONSTRAINT `fk_entity_package` FOREIGN KEY (`package_id`) REFERENCES `gen_package` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_entity_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成实体' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_enum
-- ----------------------------
DROP TABLE IF EXISTS `gen_enum`;
CREATE TABLE `gen_enum`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '枚举名',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '枚举注释',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成枚举' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_enum_item
-- ----------------------------
DROP TABLE IF EXISTS `gen_enum_item`;
CREATE TABLE `gen_enum_item`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `enum_id` bigint(0) NOT NULL COMMENT '对应枚举 ID',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '元素名',
  `value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '元素值',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '元素注释',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_enum_item_enum`(`enum_id`) USING BTREE,
  CONSTRAINT `fk_enum_item_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成枚举元素' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_package
-- ----------------------------
DROP TABLE IF EXISTS `gen_package`;
CREATE TABLE `gen_package`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` bigint(0) NULL COMMENT '父包',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '包名称',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_package_parent`(`parent_id`) USING BTREE,
  CONSTRAINT `fk_package_parent` FOREIGN KEY (`parent_id`) REFERENCES `gen_package` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成包' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_property
-- ----------------------------
DROP TABLE IF EXISTS `gen_property`;
CREATE TABLE `gen_property`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `entity_id` bigint(0) NOT NULL COMMENT '归属实体',
  `column_id` bigint(0) NULL COMMENT '对应列',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性名',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性注释',
  `type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性类型',
  `type_table_id` bigint(0) NULL COMMENT '类型对应表',
  `is_list` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否列表（1是）',
  `is_not_null` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否非空（1是）',
  `is_id` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否Id（1是）',
  `id_generation_type` enum('AUTO','USER','IDENTITY','SEQUENCE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'Id 生成类型',
  `is_key` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为业务键属性（1是）',
  `is_logical_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为逻辑删除属性（1是）',
  `is_id_view` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为 ID 视图属性（1是）',
  `id_view_annotation` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT 'ID 视图注释',
  `association_type` enum('OneToOne','OneToMany','ManyToOne','ManyToMany') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '关联类型',
  `association_annotation` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '关联注释',
  `dissociate_annotation` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '脱钩注释',
  `other_annotation` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '其他注释',
  `enum_id` bigint(0) NULL COMMENT '对应枚举 ID',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_property_column`(`column_id`) USING BTREE,
  INDEX `fk_property_entity`(`entity_id`) USING BTREE,
  INDEX `fk_property_enum`(`enum_id`) USING BTREE,
  CONSTRAINT `fk_property_column` FOREIGN KEY (`column_id`) REFERENCES `gen_column` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT,
  CONSTRAINT `fk_property_entity` FOREIGN KEY (`entity_id`) REFERENCES `gen_entity` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_property_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成属性' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_schema
-- ----------------------------
DROP TABLE IF EXISTS `gen_schema`;
CREATE TABLE `gen_schema`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `data_source_id` bigint(0) NOT NULL COMMENT '数据源 ID',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_schema_data_source`(`data_source_id`) USING BTREE,
  CONSTRAINT `fk_schema_data_source` FOREIGN KEY (`data_source_id`) REFERENCES `gen_data_source` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成数据架构' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `schema_id` bigint(0) NOT NULL COMMENT '数据架构',
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表名称',
  `comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表注释',
  `type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表种类',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_table_schema`(`schema_id`) USING BTREE,
  CONSTRAINT `fk_table_schema` FOREIGN KEY (`schema_id`) REFERENCES `gen_schema` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS `gen_type_mapping`;
CREATE TABLE `gen_type_mapping`  (
  `id` bigint(0) NOT NULL,
  `type_expression` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型表达式',
  `is_regex` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否正则（1是）',
  `property_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性类型',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '列到属性类型映射' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

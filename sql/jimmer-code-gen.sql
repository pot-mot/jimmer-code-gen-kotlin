/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3307
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3307
 Source Schema         : jimmer-code-gen

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 04/08/2023 15:37:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表描述',
  `class_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '实体类名称',
  `package_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '包名',
  `module_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名',
  `function_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '功能名',
  `author` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '作者',
  `gen_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `is_add` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否生成添加功能（1是）',
  `is_edit` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否生成编辑功能（1是）',
  `is_list` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否生成列表功能（1是）',
  `is_query` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否生成查询功能（1是）',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_association
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_association`;
CREATE TABLE `gen_table_association`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_association_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '表关联名称',
  `source_table_id` bigint(0) NOT NULL COMMENT '主表编号',
  `source_column_id` bigint(0) NOT NULL COMMENT '主字段编号',
  `target_table_id` bigint(0) NOT NULL COMMENT '从表编号',
  `target_column_id` bigint(0) NOT NULL COMMENT '从字段编号',
  `association_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'OneToOne' COMMENT '关联类型（OneToOne, ManyToOne, OneToMany, ManyToMany）',
  `association_express` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '关联表达式',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表关联' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_association
-- ----------------------------

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint(0) NOT NULL COMMENT '归属表编号',
  `column_sort` bigint(0) NOT NULL COMMENT '列在表中顺序',
  `column_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列名称',
  `column_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列类型',
  `column_display_size` bigint(0) NOT NULL DEFAULT 0 COMMENT '列展示长度',
  `column_precision` bigint(0) NOT NULL DEFAULT 0 COMMENT '列精度',
  `column_default` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列默认值',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列描述',
  `is_pk` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否主键（1是）',
  `is_increment` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否自增（1是）',
  `is_unique` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否唯一索引（1是）',
  `is_not_null` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否非空（1是）',
  `is_virtual_column` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否虚拟列（1是）',
  `field_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段名',
  `field_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段类型',
  `field_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段描述',
  `is_list` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否为列表字段（1是）',
  `list_sort` bigint(0) NOT NULL DEFAULT 0 COMMENT '字段在列表中顺序',
  `is_add` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否为添加字段（1是）',
  `add_sort` bigint(0) NOT NULL DEFAULT 0 COMMENT '字段在新增表单中顺序',
  `is_edit` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否为编辑字段（1是）',
  `edit_sort` bigint(0) NOT NULL DEFAULT 0 COMMENT '字段在修改表单中顺序',
  `required` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为必填字段（1是）',
  `read_only` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为只读字段（1是）',
  `is_query` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否为查询字段（1是）',
  `query_sort` bigint(0) NOT NULL DEFAULT 0 COMMENT '字段在查询表单中顺序',
  `query_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'EQ' COMMENT '查询类型（EQ、NE、GT、GTE、LT、LTE、BETWEEN、IN、LIKE、ILIKE）',
  `dict_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '字典类型',
  `is_sort` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为排序字段（1是）',
  `sort_direction` tinyint(1) NOT NULL DEFAULT 0 COMMENT '排序方向（0 ASC 1 DESC）',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS `gen_type_mapping`;
CREATE TABLE `gen_type_mapping`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `column_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列类型表达式',
  `is_regex` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否正则（1是）',
  `field_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段类型',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_type_mapping
-- ----------------------------
INSERT INTO `gen_type_mapping` VALUES (1, 'varchar', 0, 'java.lang.String', 1, '2023-08-03 10:47:04', '2023-08-03 10:47:47', '');
INSERT INTO `gen_type_mapping` VALUES (2, 'char', 0, 'java.lang.String', 2, '2023-08-03 10:47:47', '2023-08-03 10:47:47', '');
INSERT INTO `gen_type_mapping` VALUES (3, '(tiny|medium|long)?text', 1, 'java.lang.String', 3, '2023-08-03 10:48:21', '2023-08-04 10:46:48', '');
INSERT INTO `gen_type_mapping` VALUES (4, 'decimal', 0, 'java.math.BigDecimal', 4, '2023-08-03 10:48:48', '2023-08-03 10:48:54', '');
INSERT INTO `gen_type_mapping` VALUES (5, '(tiny|small|medium)?int', 1, 'java.lang.Integer', 5, '2023-08-03 10:51:14', '2023-08-04 10:46:48', '');
INSERT INTO `gen_type_mapping` VALUES (6, 'integer', 0, 'java.lang.Integer', 6, '2023-08-03 10:51:44', '2023-08-03 10:51:44', '');
INSERT INTO `gen_type_mapping` VALUES (7, 'int4', 0, 'java.lang.Integer', 7, '2023-08-03 10:52:01', '2023-08-03 10:52:01', '');
INSERT INTO `gen_type_mapping` VALUES (8, 'int8', 0, 'java.lang.Long', 8, '2023-08-03 10:52:29', '2023-08-03 10:52:29', '');
INSERT INTO `gen_type_mapping` VALUES (9, 'bigint', 0, 'java.lang.Long', 9, '2023-08-03 10:52:29', '2023-08-03 10:52:29', '');
INSERT INTO `gen_type_mapping` VALUES (10, 'date', 0, 'java.util.Date', 10, '2023-08-03 10:52:51', '2023-08-03 10:52:51', '');
INSERT INTO `gen_type_mapping` VALUES (11, 'datetime', 0, 'java.time.LocalDateTime', 11, '2023-08-03 10:55:03', '2023-08-03 10:55:03', '');
INSERT INTO `gen_type_mapping` VALUES (12, 'timestamp', 0, 'java.time.LocalDateTime', 12, '2023-08-03 10:55:25', '2023-08-03 10:55:25', '');
INSERT INTO `gen_type_mapping` VALUES (13, 'time', 0, 'java.time.LocalTime', 13, '2023-08-03 10:55:50', '2023-08-03 10:55:50', '');
INSERT INTO `gen_type_mapping` VALUES (14, 'boolean', 0, 'java.lang.Boolean', 14, '2023-08-03 10:56:52', '2023-08-03 10:56:52', '');
INSERT INTO `gen_type_mapping` VALUES (15, 'tinyint unsigned', 0, 'java.lang.Integer', 15, '2023-08-03 11:19:44', '2023-08-03 11:19:44', '');
INSERT INTO `gen_type_mapping` VALUES (16, 'int unsigned', 0, 'java.lang.Long', 16, '2023-08-03 11:23:03', '2023-08-03 11:23:03', '');
INSERT INTO `gen_type_mapping` VALUES (17, 'bigint unsigned', 0, 'java.lang.Long', 17, '2023-08-04 10:43:48', '2023-08-04 10:43:48', '');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dict_type_id` bigint(0) NOT NULL COMMENT '字典类型编号',
  `dict_sort` bigint(0) NULL DEFAULT 0 COMMENT '字典内排序',
  `dict_label` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典键值',
  `enum_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '枚举名',
  `enum_label` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '枚举标签',
  `enum_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '枚举值',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dict_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '字典类型',
  `order_key` bigint(0) NOT NULL DEFAULT 0 COMMENT '自定排序',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modified_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;

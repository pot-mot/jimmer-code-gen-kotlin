SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `gen_data_source`;
CREATE TABLE `gen_data_source`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `type`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据库类型',
    `name`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
    `host`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主机',
    `port`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '端口',
    `username`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
    `password`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
    `order_key`     bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成数据源'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_schema`;
CREATE TABLE `gen_schema`
(
    `id`             bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `data_source_id` bigint(0)                                                     NOT NULL COMMENT '数据源 ID',
    `name`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
    `order_key`      bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`   datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`  datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成数据架构'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_table_group`;
CREATE TABLE `gen_table_group`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `parent_id`     bigint(0)                                                     NULL COMMENT '父组',
    `name`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组名称',
    `order_key`     bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成表分组'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `schema_id`     bigint(0)                                                     NOT NULL COMMENT '数据架构',
    `group_id`      bigint(0)                                                     NULL COMMENT '所属组',
    `name`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表名称',
    `comment`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表注释',
    `type`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '表种类',
    `order_key`     bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_column`;
CREATE TABLE `gen_column`
(
    `id`                       bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id`                 bigint(0)                                                     NOT NULL COMMENT '归属表',
    `order_key`                bigint(0)                                                     NOT NULL COMMENT '列在表中顺序',
    `name`                     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列名称',
    `type_code`                int(0)                                                        NOT NULL COMMENT '列对应 JDBCType 码值',
    `type`                     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列类型',
    `display_size`             bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '列展示长度',
    `numeric_precision` bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '列精度',
    `default_value`            varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '列默认值',
    `comment`                  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列注释',
    `is_pk`                    tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否主键（1是）',
    `is_auto_increment`        tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否自增（1是）',
    `is_fk`                    tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否外键（1是）',
    `is_unique`                tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否唯一索引（1是）',
    `is_not_null`              tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否非空（1是）',
    `created_time`             datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`            datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`                   varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成列'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_association`;
CREATE TABLE `gen_association`
(
    `id`               bigint(0)                                                                                               NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `comment`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                           NOT NULL DEFAULT '' COMMENT '关联注释',
    `source_column_id` bigint(0)                                                                                               NOT NULL COMMENT '主列',
    `target_column_id` bigint(0)                                                                                               NOT NULL COMMENT '从列',
    `association_type` enum ('OneToOne','OneToMany','ManyToOne','ManyToMany') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '关联类型',
    `dissociate_action`              enum ('NONE', 'SET_NULL', 'DELETE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci      NULL COMMENT '脱钩行为',
    `order_key`        bigint(0)                                                                                               NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`     datetime(0)                                                                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`    datetime(0)                                                                                             NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                           NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成关联'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_type_mapping`;
CREATE TABLE `gen_type_mapping`
(
    `id`                     bigint(0)                                                     NOT NULL,
    `column_type_expression` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '列类型表达式',
    `is_regex`               tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否正则（1是）',
    `property_type`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '属性类型',
    `order_key`              bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`           datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`          datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`                 varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '列到属性类型映射'
  ROW_FORMAT = Dynamic;

INSERT INTO `gen_type_mapping`
VALUES (1, 'varchar', 0, 'String', 1, '2023-08-03 10:47:04', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (2, 'char', 0, 'String', 2, '2023-08-03 10:47:47', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (3, '(tiny|medium|long)?text', 1, 'String', 3, '2023-08-03 10:48:21', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (4, 'decimal', 0, 'BigDecimal', 4, '2023-08-03 10:48:48', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (5, '(tiny|small|medium)?int', 1, 'Integer', 5, '2023-08-03 10:51:14', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (6, 'integer', 0, 'Integer', 6, '2023-08-03 10:51:44', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (7, 'int4', 0, 'Integer', 7, '2023-08-03 10:52:01', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (8, 'int8', 0, 'Long', 8, '2023-08-03 10:52:29', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (9, 'bigint', 0, 'Long', 9, '2023-08-03 10:52:29', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (10, 'date', 0, 'Date', 10, '2023-08-03 10:52:51', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (11, 'datetime', 0, 'LocalDateTime', 11, '2023-08-03 10:55:03', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (12, 'timestamp', 0, 'LocalDateTime', 12, '2023-08-03 10:55:25', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (13, 'time', 0, 'LocalTime', 13, '2023-08-03 10:55:50', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (14, 'boolean', 0, 'Boolean', 14, '2023-08-03 10:56:52', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (15, 'tinyint unsigned', 0, 'Integer', 15, '2023-08-03 11:19:44', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (16, 'int unsigned', 0, 'Long', 16, '2023-08-03 11:23:03', '2023-08-05 10:42:55', '');
INSERT INTO `gen_type_mapping`
VALUES (17, 'bigint unsigned', 0, 'Long', 17, '2023-08-04 10:43:48', '2023-08-05 10:42:55', '');

DROP TABLE IF EXISTS `gen_entity`;
CREATE TABLE `gen_entity`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id`      bigint(0)                                                     NULL COMMENT '对应表',
    `class_name`    varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类名称',
    `class_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类注释',
    `package_name`  varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '包名',
    `module_name`   varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '模块名',
    `function_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '功能名',
    `author`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '作者',
    `gen_path`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
    `is_add`        tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '是否生成添加功能（1是）',
    `is_edit`       tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '是否生成编辑功能（1是）',
    `is_list`       tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '是否生成列表功能（1是）',
    `is_query`      tinyint(1)                                                    NOT NULL DEFAULT 1 COMMENT '是否生成查询功能（1是）',
    `order_key`     bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成实体'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_property`;
CREATE TABLE `gen_property`
(
    `id`                             bigint(0)                                                                                                             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `entity_id`                      bigint(0)                                                                                                             NOT NULL COMMENT '归属实体',
    `column_id`                      bigint(0)                                                                                                             NULL COMMENT '对应列',
    `name`                           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                                         NOT NULL COMMENT '属性名',
    `is_id`                          tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '是否Id（1是）',
    `id_generation_type`             enum ('AUTO', 'USER', 'IDENTITY', 'SEQUENCE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                 DEFAULT 'AUTO' COMMENT 'Id 生成类型',
    `is_key`                         tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '是否为业务键属性（1是）',
    `is_logical_delete`              tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '是否为逻辑删除属性（1是）',
    `property_association_type`      enum ('OneToOne', 'ManyToOne', 'OneToMany', 'ManyToMany') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci            NULL COMMENT '属性关联类型',
    `property_type`                  enum ('IdView', 'ManyToManyView', 'Formula', 'Transient') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci            NULL COMMENT '属性类型',
    `property_annotation_expression` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                                         NULL COMMENT '属性注解表达式',
    `property_comment`               varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                                         NOT NULL COMMENT '属性注释',
    `enum_id`                        bigint(0)                                                                                                             NULL COMMENT '对应枚举 ID',
    `created_time`                   datetime(0)                                                                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`                  datetime(0)                                                                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`                         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                                         NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成属性'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_dto`;
CREATE TABLE `gen_dto`
(
    `id`                             bigint(0)                                                                                                             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `entity_id`                      bigint(0)                                                                                                             NOT NULL COMMENT '对应实体',
    `name`                           varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                                         NOT NULL COMMENT '属性名',
    `type`                           enum ('VIEW', 'INSERT', 'INSERTONLY') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                NOT NULL DEFAULT 'VIEW' COMMENT 'DTO 类型',
    `created_time`                   datetime(0)                                                                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`                  datetime(0)                                                                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`                         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                                         NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成 DTO'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_dto_property`;
CREATE TABLE `gen_dto_property`
(
    `id`                             bigint(0)                                                                                                             NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dto_id`                         bigint(0)                                                                                                             NOT NULL COMMENT '对应 DTO',
    `property_id`                    bigint(0)                                                                                                             NULL COMMENT '对应属性',
    `is_list`                        tinyint(1)                                                                                                            NOT NULL DEFAULT 1 COMMENT '是否在列表中（1是）',
    `list_sort`                      bigint(0)                                                                                                             NOT NULL DEFAULT 0 COMMENT '在列表中顺序',
    `is_add`                         tinyint(1)                                                                                                            NOT NULL DEFAULT 1 COMMENT '是否在新增表单中（1是）',
    `add_sort`                       bigint(0)                                                                                                             NOT NULL DEFAULT 0 COMMENT '在新增表单中顺序',
    `is_add_required`                tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '是否为新增时必填（1是）',
    `is_edit`                        tinyint(1)                                                                                                            NOT NULL DEFAULT 1 COMMENT '是否在编辑表单中（1是）',
    `edit_sort`                      bigint(0)                                                                                                             NOT NULL DEFAULT 0 COMMENT '在编辑表单中顺序',
    `is_edit_required`               tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '是否为编辑时必填（1是）',
    `is_edit_read_only`              tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '是否为编辑时只读（1是）',
    `is_query`                       tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '是否为查询属性（1是）',
    `query_sort`                     bigint(0)                                                                                                             NOT NULL DEFAULT 0 COMMENT '在查询属性中顺序',
    `query_type`                     enum ('EQ','NE','GT','GTE','LT','LTE','BETWEEN','IN','LIKE','ILIKE') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'EQ' COMMENT '查询类型',
    `is_sort`                        tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '是否为排序属性（1是）',
    `sort_direction`                 tinyint(1)                                                                                                            NOT NULL DEFAULT 0 COMMENT '排序方向（0 ASC 1 DESC）',
    `created_time`                   datetime(0)                                                                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time`                  datetime(0)                                                                                                           NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`                         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci                                                         NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成 DTO 属性'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `gen_enum`;
CREATE TABLE `gen_enum`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '' COMMENT '枚举名',
    `comment`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '' COMMENT '枚举注释',
    `order_key`     bigint(0)                                                     NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成枚举'
  ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `gen_enum_item`;
CREATE TABLE `gen_enum_item`
(
    `id`            bigint(0)                                                     NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `enum_id`       bigint(0)                                                     NOT NULL COMMENT '对应枚举 ID',
    `name`          varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '' COMMENT '元素名',
    `value`         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '' COMMENT '元素值',
    `comment`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL     DEFAULT '' COMMENT '元素注释',
    `created_time`  datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `modified_time` datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
    `remark`        varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成枚举元素'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

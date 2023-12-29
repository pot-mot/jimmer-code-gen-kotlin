SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `gen_model`;
DROP TABLE IF EXISTS `gen_enum`;
DROP TABLE IF EXISTS `gen_enum_item`;
DROP TABLE IF EXISTS `gen_data_source`;
DROP TABLE IF EXISTS `gen_schema`;
DROP TABLE IF EXISTS `gen_table`;
DROP TABLE IF EXISTS `gen_column`;
DROP TABLE IF EXISTS `gen_association`;
DROP TABLE IF EXISTS `gen_column_reference`;
DROP TABLE IF EXISTS `gen_table_index`;
DROP TABLE IF EXISTS `gen_entity`;
DROP TABLE IF EXISTS `gen_property`;
DROP TABLE IF EXISTS `gen_index_column_mapping`;
DROP TABLE IF EXISTS `gen_type_mapping`;
DROP TABLE IF EXISTS `gen_column_default`;

-- ----------------------------
-- Table structure for gen_model
-- ----------------------------
CREATE TABLE `gen_model`
(
    `id`                  bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`                varchar(500) NOT NULL COMMENT '名称',
    `graph_data`          longtext     NOT NULL COMMENT 'Graph 数据',
    `language`            varchar(500) NOT NULL COMMENT '语言',
    `data_source_type`    varchar(500) NOT NULL COMMENT '数据源类型',
    `package_path`        varchar(500) NOT NULL COMMENT '包路径',
    `sync_convert_entity` boolean      NOT NULL DEFAULT TRUE COMMENT '同步转换实体',
    `created_time`        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`              varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成模型'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_enum
-- ----------------------------
CREATE TABLE `gen_enum`
(
    `id`            bigint                   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `model_id`      bigint                   NULL COMMENT '模型',
    `package_path`  varchar(500)             NOT NULL COMMENT '包路径',
    `name`          varchar(500)             NOT NULL COMMENT '枚举名',
    `comment`       varchar(500)             NOT NULL COMMENT '枚举注释',
    `enum_type`     enum ('NAME', 'ORDINAL') NULL     DEFAULT NULL COMMENT '枚举类型',
    `order_key`     bigint                   NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime                 NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime                 NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`        varchar(500)             NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_enum_model` (`model_id`) USING BTREE,
    CONSTRAINT `fk_enum_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成枚举'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_enum_item
-- ----------------------------
CREATE TABLE `gen_enum_item`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `enum_id`       bigint       NOT NULL COMMENT '对应枚举',
    `name`          varchar(500) NOT NULL COMMENT '元素名',
    `mapped_value`  varchar(500) NOT NULL COMMENT '映射值',
    `comment`       varchar(500) NOT NULL DEFAULT '' COMMENT '元素注释',
    `order_key`     bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_enum_item_enum` (`enum_id`) USING BTREE,
    CONSTRAINT `fk_enum_item_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成枚举元素'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
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
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成数据源'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_schema
-- ----------------------------
CREATE TABLE `gen_schema`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `data_source_id` bigint       NOT NULL COMMENT '数据源',
    `name`           varchar(500) NOT NULL COMMENT '名称',
    `order_key`      bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`         varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_schema_data_source` (`data_source_id`) USING BTREE,
    CONSTRAINT `fk_schema_data_source` FOREIGN KEY (`data_source_id`) REFERENCES `gen_data_source` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成数据架构'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
CREATE TABLE `gen_table`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `model_id`      bigint       NULL COMMENT '模型',
    `schema_id`     bigint       NULL COMMENT '数据架构',
    `name`          varchar(500) NOT NULL COMMENT '表名称',
    `comment`       varchar(500) NOT NULL COMMENT '表注释',
    `type`          varchar(500) NOT NULL COMMENT '表种类',
    `order_key`     bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_table_model` (`model_id`) USING BTREE,
    INDEX `idx_table_schema` (`schema_id`) USING BTREE,
    CONSTRAINT `fk_table_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_table_schema` FOREIGN KEY (`schema_id`) REFERENCES `gen_schema` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_column
-- ----------------------------
CREATE TABLE `gen_column`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id`          bigint       NOT NULL COMMENT '归属表',
    `name`              varchar(500) NOT NULL COMMENT '列名称',
    `type_code`         int(0)       NOT NULL COMMENT '列 JDBCType 码值',
    `overwrite_by_type` boolean      NOT NULL DEFAULT FALSE COMMENT '覆盖为字面类型',
    `type`              varchar(500) NOT NULL COMMENT '列字面类型',
    `display_size`      bigint       NOT NULL DEFAULT 0 COMMENT '列展示长度',
    `numeric_precision` bigint       NOT NULL DEFAULT 0 COMMENT '列精度',
    `default_value`     varchar(500) NULL     DEFAULT NULL COMMENT '列默认值',
    `comment`           varchar(500) NOT NULL COMMENT '列注释',
    `part_of_pk`        boolean      NOT NULL DEFAULT FALSE COMMENT '是否主键',
    `auto_increment`    boolean      NOT NULL DEFAULT FALSE COMMENT '是否自增',
    `type_not_null`     boolean      NOT NULL DEFAULT FALSE COMMENT '是否非空',
    `business_key`      boolean      NOT NULL DEFAULT FALSE COMMENT '是否为业务键',
    `logical_delete`    boolean      NOT NULL DEFAULT FALSE COMMENT '是否为逻辑删除',
    `enum_id`           bigint       NULL     DEFAULT NULL COMMENT '对应枚举',
    `order_key`         bigint       NOT NULL COMMENT '列在表中顺序',
    `created_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`            varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_column_table` (`table_id`) USING BTREE,
    INDEX `idx_column_enum` (`enum_id`) USING BTREE,
    CONSTRAINT `fk_column_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成列'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_association
-- ----------------------------
CREATE TABLE `gen_association`
(
    `id`                bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `model_id`          bigint                                                         NULL COMMENT '模型',
    `name`              varchar(500)                                                   NOT NULL COMMENT '名称',
    `source_table_id`   bigint                                                         NOT NULL COMMENT '主表',
    `target_table_id`   bigint                                                         NOT NULL COMMENT '从表',
    `association_type`  enum ('ONE_TO_ONE','ONE_TO_MANY','MANY_TO_ONE','MANY_TO_MANY') NOT NULL COMMENT '关联类型',
    `dissociate_action` enum ('NONE','SET_NULL','DELETE')                              NULL     DEFAULT NULL COMMENT '脱钩行为',
    `fake`              boolean                                                        NOT NULL DEFAULT TRUE COMMENT '是否伪外键',
    `order_key`         bigint                                                         NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`      datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`     datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`            varchar(500)                                                   NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_association_model` (`model_id`) USING BTREE,
    INDEX `idx_association_source_column` (`source_table_id`) USING BTREE,
    INDEX `idx_association_target_column` (`target_table_id`) USING BTREE,
    CONSTRAINT `fk_association_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_association_source_column` FOREIGN KEY (`source_table_id`) REFERENCES `gen_table` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_association_target_column` FOREIGN KEY (`target_table_id`) REFERENCES `gen_table` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成关联'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_column_reference
-- ----------------------------
CREATE TABLE `gen_column_reference`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `association_id`   bigint       NOT NULL COMMENT '关联',
    `source_column_id` bigint       NOT NULL COMMENT '主列',
    `target_column_id` bigint       NOT NULL COMMENT '从列',
    `order_key`        bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`           varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_column_reference_association` (`association_id`) USING BTREE,
    INDEX `idx_column_reference_source_column` (`source_column_id`) USING BTREE,
    INDEX `idx_column_reference_target_column` (`target_column_id`) USING BTREE,
    CONSTRAINT `fk_column_reference_association` FOREIGN KEY (`association_id`) REFERENCES `gen_association` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_reference_source_column` FOREIGN KEY (`source_column_id`) REFERENCES `gen_column` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_reference_target_column` FOREIGN KEY (`target_column_id`) REFERENCES `gen_column` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '列引用'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table_index
-- ----------------------------
CREATE TABLE `gen_table_index`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id`      bigint       NOT NULL COMMENT '归属表',
    `name`          varchar(500) NOT NULL COMMENT '名称',
    `unique_index`  boolean      NOT NULL DEFAULT FALSE COMMENT '是否唯一索引',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_index_table` (`table_id`) USING BTREE,
    CONSTRAINT `fk_index_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '表索引'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_index_column_mapping
-- ----------------------------
CREATE TABLE `gen_index_column_mapping`
(
    `index_id`  bigint NOT NULL COMMENT '唯一索引',
    `column_id` bigint NOT NULL COMMENT '列',
    PRIMARY KEY (`index_id`, `column_id`) USING BTREE,
    CONSTRAINT `fk_columns_mapping_index` FOREIGN KEY (`index_id`) REFERENCES `gen_table_index` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_index_mapping_column` FOREIGN KEY (`column_id`) REFERENCES `gen_column` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '唯一索引与列关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_entity
-- ----------------------------
CREATE TABLE `gen_entity`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `model_id`      bigint       NULL COMMENT '模型',
    `package_path`  varchar(500) NOT NULL COMMENT '包路径',
    `table_id`      bigint       NOT NULL COMMENT '对应表',
    `name`          varchar(500) NOT NULL COMMENT '类名称',
    `comment`       varchar(500) NOT NULL COMMENT '类注释',
    `author`        varchar(500) NOT NULL DEFAULT '' COMMENT '作者',
    `order_key`     bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`        varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `u_entity_table` (`table_id`) USING BTREE,
    INDEX `idx_entity_model` (`model_id`) USING BTREE,
    INDEX `idx_entity_table` (`table_id`) USING BTREE,
    CONSTRAINT `fk_entity_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT `fk_entity_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '生成实体'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_property
-- ----------------------------
CREATE TABLE `gen_property`
(
    `id`                     bigint                                                         NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `entity_id`              bigint                                                         NOT NULL COMMENT '归属实体',
    `column_id`              bigint                                                         NULL     DEFAULT NULL COMMENT '对应列',
    `name`                   varchar(500)                                                   NOT NULL COMMENT '属性名',
    `comment`                varchar(500)                                                   NOT NULL COMMENT '属性注释',
    `type`                   varchar(500)                                                   NOT NULL COMMENT '属性类型',
    `type_table_id`          bigint                                                         NULL     DEFAULT NULL COMMENT '类型对应表',
    `list_type`              boolean                                                        NOT NULL DEFAULT FALSE COMMENT '是否列表',
    `type_not_null`          boolean                                                        NOT NULL DEFAULT FALSE COMMENT '是否非空',
    `id_property`            boolean                                                        NOT NULL DEFAULT FALSE COMMENT '是否Id',
    `id_generation_type`     enum ('AUTO','USER','IDENTITY','SEQUENCE')                     NULL     DEFAULT NULL COMMENT 'Id 生成类型',
    `key_property`           boolean                                                        NOT NULL DEFAULT FALSE COMMENT '是否为业务键属性',
    `logical_delete`         boolean                                                        NOT NULL DEFAULT FALSE COMMENT '是否为逻辑删除属性',
    `id_view`                boolean                                                        NOT NULL DEFAULT FALSE COMMENT '是否为 视图属性',
    `id_view_annotation`     varchar(500)                                                   NULL     DEFAULT NULL COMMENT 'ID 视图注释',
    `association_type`       enum ('ONE_TO_ONE','ONE_TO_MANY','MANY_TO_ONE','MANY_TO_MANY') NULL     DEFAULT NULL COMMENT '关联类型',
    `association_annotation` varchar(500)                                                   NULL     DEFAULT NULL COMMENT '关联注释',
    `dissociate_annotation`  varchar(500)                                                   NULL     DEFAULT NULL COMMENT '脱钩注释',
    `other_annotation`       varchar(500)                                                   NULL     DEFAULT NULL COMMENT '其他注释',
    `enum_id`                bigint                                                         NULL     DEFAULT NULL COMMENT '对应枚举',
    `order_key`              bigint                                                         NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`           datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`          datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`                 varchar(500)                                                   NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_property_column` (`column_id`) USING BTREE,
    INDEX `idx_property_entity` (`entity_id`) USING BTREE,
    INDEX `idx_property_enum` (`enum_id`) USING BTREE,
    INDEX `idx_property_type_table` (`type_table_id`) USING BTREE,
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
CREATE TABLE `gen_type_mapping`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `data_source_type` varchar(500) NOT NULL COMMENT '数据源类型',
    `type_expression`  varchar(500) NOT NULL COMMENT '数据库类型表达式',
    `language`         varchar(500) NOT NULL COMMENT '语言',
    `property_type`    varchar(500) NOT NULL COMMENT '属性类型',
    `order_key`        bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`           varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '列到属性类型映射'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
CREATE TABLE `gen_column_default`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `data_source_type`  varchar(500) NOT NULL COMMENT '数据源类型',
    `type_code`         int(0)       NOT NULL COMMENT 'JDBCType 码值',
    `type`              varchar(500) NOT NULL COMMENT '字面类型',
    `display_size`      bigint       NOT NULL DEFAULT 0 COMMENT '列展示长度',
    `numeric_precision` bigint       NOT NULL DEFAULT 0 COMMENT '列精度',
    `default_value`     varchar(500) NULL     DEFAULT NULL COMMENT '默认值',
    `order_key`         bigint       NOT NULL DEFAULT 0 COMMENT '自定排序',
    `created_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `remark`            varchar(500) NOT NULL DEFAULT '' COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '列默认'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

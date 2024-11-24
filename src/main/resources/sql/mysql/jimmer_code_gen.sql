SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `gen_model` CASCADE;
DROP TABLE IF EXISTS `gen_enum` CASCADE;
DROP TABLE IF EXISTS `gen_enum_item` CASCADE;
DROP TABLE IF EXISTS `gen_data_source` CASCADE;
DROP TABLE IF EXISTS `gen_schema` CASCADE;
DROP TABLE IF EXISTS `gen_table` CASCADE;
DROP TABLE IF EXISTS `gen_column` CASCADE;
DROP TABLE IF EXISTS `gen_association` CASCADE;
DROP TABLE IF EXISTS `gen_column_reference` CASCADE;
DROP TABLE IF EXISTS `gen_table_index` CASCADE;
DROP TABLE IF EXISTS `gen_entity` CASCADE;
DROP TABLE IF EXISTS `gen_property` CASCADE;
DROP TABLE IF EXISTS `gen_index_column_mapping` CASCADE;
DROP TABLE IF EXISTS `gen_type_mapping` CASCADE;
DROP TABLE IF EXISTS `gen_column_default` CASCADE;
DROP TABLE IF EXISTS `gen_super_table_mapping` CASCADE;
DROP TABLE IF EXISTS `gen_super_entity_mapping` CASCADE;

-- ----------------------------
-- Table structure for gen_model
-- ----------------------------
CREATE TABLE `gen_model`
(
    `id`                         bigint       NOT NULL AUTO_INCREMENT,
    `name`                       varchar(500) NOT NULL COMMENT '名称',
    `graph_data`                 longtext     NOT NULL COMMENT 'Graph 数据',
    `language`                   varchar(500) NOT NULL COMMENT '语言',
    `data_source_type`           varchar(500) NOT NULL COMMENT '数据源类型',
    `author`                     varchar(500) NOT NULL COMMENT '作者',
    `package_path`               varchar(500) NOT NULL COMMENT '包路径',
    `table_path`                 varchar(500) NOT NULL COMMENT '表路径',
    `database_naming_strategy`   varchar(500) NOT NULL COMMENT '数据库命名策略',
    `real_fk`                    boolean      NOT NULL COMMENT '启用真实外键',
    `id_view_property`           boolean      NOT NULL COMMENT '生成 IdView 属性',
    `logical_deleted_annotation` varchar(500) NOT NULL COMMENT '逻辑删除注解',
    `table_annotation`           boolean      NOT NULL COMMENT '生成 Table 注解',
    `column_annotation`          boolean      NOT NULL COMMENT '生成 Column 注解',
    `join_table_annotation`      boolean      NOT NULL COMMENT '生成 JoinTable 注解',
    `join_column_annotation`     boolean      NOT NULL COMMENT '生成 JoinColumn 注解',
    `table_name_prefixes`        varchar(500) NOT NULL COMMENT '转换实体时移除的表名前缀',
    `table_name_suffixes`        varchar(500) NOT NULL COMMENT '转换实体时移除的表名后缀',
    `table_comment_prefixes`     varchar(500) NOT NULL COMMENT '转换实体时移除的表注释前缀',
    `table_comment_suffixes`     varchar(500) NOT NULL COMMENT '转换实体时移除的表注释后缀',
    `column_name_prefixes`       varchar(500) NOT NULL COMMENT '转换属性时移除的列名前缀',
    `column_name_suffixes`       varchar(500) NOT NULL COMMENT '转换属性时移除的列名后缀',
    `column_comment_prefixes`    varchar(500) NOT NULL COMMENT '转换属性时移除的列注释前缀',
    `column_comment_suffixes`    varchar(500) NOT NULL COMMENT '转换属性时移除的列注释后缀',
    `remark`                     varchar(500) NOT NULL COMMENT '备注',
    `created_time`               datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`              datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成模型'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_enum
-- ----------------------------
CREATE TABLE `gen_enum`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `model_id`      bigint       NULL COMMENT '模型',
    `package_path`  varchar(500) NOT NULL COMMENT '包路径',
    `name`          varchar(500) NOT NULL COMMENT '枚举名',
    `comment`       varchar(500) NOT NULL COMMENT '枚举注释',
    `enum_type`     varchar(500) NULL     DEFAULT NULL COMMENT '枚举类型',
    `remark`        varchar(500) NOT NULL COMMENT '备注',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_enum_model` (`model_id`) USING BTREE,
    CONSTRAINT `fk_enum_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成枚举'
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
    `comment`       varchar(500) NOT NULL COMMENT '元素注释',
    `order_key`     bigint       NOT NULL COMMENT '排序键',
    `default_item`  boolean      NOT NULL COMMENT '是否是默认值',
    `remark`        varchar(500) NOT NULL COMMENT '备注',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_enum_item_enum` (`enum_id`) USING BTREE,
    CONSTRAINT `fk_enum_item_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成枚举元素'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
CREATE TABLE `gen_data_source`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `type`          varchar(500) NOT NULL COMMENT '数据库类型',
    `name`          varchar(500) NOT NULL COMMENT '名称',
    `url`           varchar(500) NOT NULL COMMENT '链接',
    `username`      varchar(500) NOT NULL COMMENT '用户名',
    `password`      varchar(500) NOT NULL COMMENT '密码',
    `remark`        varchar(500) NOT NULL COMMENT '备注',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成数据源'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_schema
-- ----------------------------
CREATE TABLE `gen_schema`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `data_source_id` bigint       NOT NULL COMMENT '数据源',
    `name`           varchar(500) NOT NULL COMMENT '名称',
    `remark`         varchar(500) NOT NULL COMMENT '备注',
    `created_time`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_schema_data_source` (`data_source_id`) USING BTREE,
    CONSTRAINT `fk_schema_data_source` FOREIGN KEY (`data_source_id`) REFERENCES `gen_data_source` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成数据架构'
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
    `remark`        varchar(500) NOT NULL COMMENT '备注',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_table_model` (`model_id`) USING BTREE,
    INDEX `idx_table_schema` (`schema_id`) USING BTREE,
    CONSTRAINT `fk_table_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_table_schema` FOREIGN KEY (`schema_id`) REFERENCES `gen_schema` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_super_table_mapping
-- ----------------------------
CREATE TABLE `gen_super_table_mapping`
(
    `super_table_id`   bigint NOT NULL COMMENT '上级表',
    `inherit_table_id` bigint NOT NULL COMMENT '继承表',
    PRIMARY KEY (`super_table_id`, `inherit_table_id`),
    CONSTRAINT `fk_super_table_mapping_super_table` FOREIGN KEY (`super_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_super_table_mapping_inherit_table` FOREIGN KEY (`inherit_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '上级表与继承表关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_column
-- ----------------------------
CREATE TABLE `gen_column`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id`          bigint       NOT NULL COMMENT '归属表',
    `name`              varchar(500) NOT NULL COMMENT '名称',
    `type_code`         int(0)       NOT NULL COMMENT 'JdbcType 码值',
    `overwrite_by_raw`  boolean      NOT NULL COMMENT '覆盖为字面类型',
    `raw_type`          varchar(500) NOT NULL COMMENT '字面类型',
    `data_size`         bigint       NOT NULL COMMENT '长度',
    `numeric_precision` bigint       NOT NULL COMMENT '精度',
    `default_value`     varchar(500) NULL     DEFAULT NULL COMMENT '默认值',
    `comment`           varchar(500) NOT NULL COMMENT '注释',
    `part_of_pk`        boolean      NOT NULL COMMENT '是否为主键的部分',
    `auto_increment`    boolean      NOT NULL COMMENT '是否自增',
    `type_not_null`     boolean      NOT NULL COMMENT '是否非空',
    `business_key`      boolean      NOT NULL COMMENT '是否为业务键',
    `key_group`         varchar(500) NULL     DEFAULT NULL COMMENT '业务键组',
    `logical_delete`    boolean      NOT NULL COMMENT '是否为逻辑删除',
    `enum_id`           bigint       NULL     DEFAULT NULL COMMENT '对应枚举',
    `order_key`         bigint       NOT NULL COMMENT '排序键',
    `remark`            varchar(500) NOT NULL COMMENT '备注',
    `created_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_column_table` (`table_id`) USING BTREE,
    INDEX `idx_column_enum` (`enum_id`) USING BTREE,
    CONSTRAINT `fk_column_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成列'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_association
-- ----------------------------
CREATE TABLE `gen_association`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `model_id`          bigint       NULL COMMENT '模型',
    `name`              varchar(500) NOT NULL COMMENT '名称',
    `source_table_id`   bigint       NOT NULL COMMENT '主表',
    `target_table_id`   bigint       NOT NULL COMMENT '从表',
    `type`              varchar(500) NOT NULL COMMENT '关联类型',
    `dissociate_action` varchar(500) NULL     DEFAULT NULL COMMENT '脱钩行为',
    `update_action`     varchar(500) NOT NULL COMMENT '更新行为',
    `delete_action`     varchar(500) NOT NULL COMMENT '删除行为',
    `fake`              boolean      NOT NULL COMMENT '是否伪外键',
    `remark`            varchar(500) NOT NULL COMMENT '备注',
    `created_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_association_model` (`model_id`) USING BTREE,
    INDEX `idx_association_source_column` (`source_table_id`) USING BTREE,
    INDEX `idx_association_target_column` (`target_table_id`) USING BTREE,
    CONSTRAINT `fk_association_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_association_source_column` FOREIGN KEY (`source_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_association_target_column` FOREIGN KEY (`target_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成关联'
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
    `order_key`        bigint       NOT NULL COMMENT '排序键',
    `remark`           varchar(500) NOT NULL COMMENT '备注',
    `created_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_column_reference_association` (`association_id`) USING BTREE,
    INDEX `idx_column_reference_source_column` (`source_column_id`) USING BTREE,
    INDEX `idx_column_reference_target_column` (`target_column_id`) USING BTREE,
    CONSTRAINT `fk_column_reference_association` FOREIGN KEY (`association_id`) REFERENCES `gen_association` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_reference_source_column` FOREIGN KEY (`source_column_id`) REFERENCES `gen_column` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_reference_target_column` FOREIGN KEY (`target_column_id`) REFERENCES `gen_column` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '列引用'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table_index
-- ----------------------------
CREATE TABLE `gen_table_index`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id`      bigint       NOT NULL COMMENT '归属表',
    `name`          varchar(500) NOT NULL COMMENT '名称',
    `unique_index`  boolean      NOT NULL COMMENT '是否唯一索引',
    `remark`        varchar(500) NOT NULL COMMENT '备注',
    `created_time`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_index_table` (`table_id`) USING BTREE,
    CONSTRAINT `fk_index_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '表索引'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_index_column_mapping
-- ----------------------------
CREATE TABLE `gen_index_column_mapping`
(
    `index_id`  bigint NOT NULL COMMENT '索引',
    `column_id` bigint NOT NULL COMMENT '列',
    PRIMARY KEY (`index_id`, `column_id`) USING BTREE,
    CONSTRAINT `fk_columns_mapping_index` FOREIGN KEY (`index_id`) REFERENCES `gen_table_index` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_index_mapping_column` FOREIGN KEY (`column_id`) REFERENCES `gen_column` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '索引与列关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_entity
-- ----------------------------
CREATE TABLE `gen_entity`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `model_id`          bigint       NULL COMMENT '模型',
    `package_path`      varchar(500) NOT NULL COMMENT '包路径',
    `table_id`          bigint       NOT NULL COMMENT '对应表',
    `name`              varchar(500) NOT NULL COMMENT '类名称',
    `overwrite_name`    boolean      NOT NULL COMMENT '覆盖自动生成类名称',
    `comment`           varchar(500) NOT NULL COMMENT '类注释',
    `overwrite_comment` boolean      NOT NULL COMMENT '覆盖自动生成注释',
    `author`            varchar(500) NOT NULL COMMENT '作者',
    `can_add`           boolean      NOT NULL COMMENT '是否可以创建',
    `can_edit`          boolean      NOT NULL COMMENT '是否可以修改',
    `can_delete`        boolean      NOT NULL COMMENT '是否可以删除',
    `can_query`         boolean      NOT NULL COMMENT '是否可以查询',
    `has_page`          boolean      NOT NULL COMMENT '是否具有页面',
    `remark`            varchar(500) NOT NULL COMMENT '备注',
    `created_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `u_entity_table` (`table_id`) USING BTREE,
    INDEX `idx_entity_model` (`model_id`) USING BTREE,
    INDEX `idx_entity_table` (`table_id`) USING BTREE,
    CONSTRAINT `fk_entity_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_entity_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成实体'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_super_entity_mapping
-- ----------------------------
CREATE TABLE `gen_super_entity_mapping`
(
    `super_entity_id`   bigint NOT NULL COMMENT '上级实体',
    `inherit_entity_id` bigint NOT NULL COMMENT '继承实体',
    PRIMARY KEY (`super_entity_id`, `inherit_entity_id`),
    CONSTRAINT `fk_super_entity_mapping_super_entity` FOREIGN KEY (`super_entity_id`) REFERENCES `gen_entity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_super_entity_mapping_inherit_entity` FOREIGN KEY (`inherit_entity_id`) REFERENCES `gen_entity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '上级实体与继承实体关联表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_property
-- ----------------------------
CREATE TABLE `gen_property`
(
    `id`                        bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `entity_id`                 bigint       NOT NULL COMMENT '归属实体',
    `column_id`                 bigint       NULL     DEFAULT NULL COMMENT '对应列',
    `name`                      varchar(500) NOT NULL COMMENT '属性名',
    `overwrite_name`            boolean      NOT NULL COMMENT '覆盖自动生成属性名',
    `comment`                   varchar(500) NOT NULL COMMENT '属性注释',
    `overwrite_comment`         boolean      NOT NULL COMMENT '覆盖自动生成注释',
    `type`                      varchar(500) NOT NULL COMMENT '属性类型',
    `type_table_id`             bigint       NULL     DEFAULT NULL COMMENT '类型对应表',
    `list_type`                 boolean      NOT NULL COMMENT '是否列表',
    `type_not_null`             boolean      NOT NULL COMMENT '是否非空',
    `id_property`               boolean      NOT NULL COMMENT '是否 ID 属性',
    `id_generation_annotation`  varchar(500) NULL     DEFAULT NULL COMMENT 'ID 生成类型',
    `key_property`              boolean      NOT NULL COMMENT '是否为业务键属性',
    `key_group`                 varchar(500) NULL     DEFAULT NULL COMMENT '业务键组',
    `logical_delete`            boolean      NOT NULL COMMENT '是否为逻辑删除属性',
    `id_view`                   boolean      NOT NULL COMMENT '是否为 ID 视图属性',
    `id_view_target`            varchar(500) NULL     DEFAULT NULL COMMENT 'ID 视图目标',
    `association_type`          varchar(500) NULL     DEFAULT NULL COMMENT '关联类型',
    `long_association`          boolean      NOT NULL COMMENT '是否为长关联',
    `mapped_by`                 varchar(500) NULL     DEFAULT NULL COMMENT '映射镜像',
    `input_not_null`            boolean      NULL     DEFAULT NULL COMMENT '输入非空',
    `join_column_metas`         varchar(500) NULL     DEFAULT NULL COMMENT '关联列元数据',
    `join_table_meta`           varchar(500) NULL     DEFAULT NULL COMMENT '关联表元数据',
    `dissociate_annotation`     varchar(500) NULL     DEFAULT NULL COMMENT '脱钩注解',
    `other_annotation`          varchar(500) NULL     DEFAULT NULL COMMENT '其他注解',
    `enum_id`                   bigint       NULL     DEFAULT NULL COMMENT '对应枚举',
    `order_key`                 bigint       NOT NULL COMMENT '排序键',
    `in_list_view`              boolean      NOT NULL COMMENT '是否在列表视图DTO中',
    `in_detail_view`            boolean      NOT NULL COMMENT '是否在详情视图DTO中',
    `in_insert_input`           boolean      NOT NULL COMMENT '是否在新增入参DTO中',
    `in_update_input`           boolean      NOT NULL COMMENT '是否在修改入参DTO中',
    `in_specification`          boolean      NOT NULL COMMENT '是否在查询规格DTO中',
    `in_option_view`            boolean      NOT NULL COMMENT '是否在选项视图DTO中',
    `in_short_association_view` boolean      NOT NULL COMMENT '是否在短关联视图DTO中',
    `in_long_association_view`  boolean      NOT NULL COMMENT '是否在长关联视图DTO中',
    `in_long_association_input` boolean      NOT NULL COMMENT '是否在长关联入参DTO中',
    `remark`                    varchar(500) NOT NULL COMMENT '备注',
    `created_time`              datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`             datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_property_column` (`column_id`) USING BTREE,
    INDEX `idx_property_entity` (`entity_id`) USING BTREE,
    INDEX `idx_property_enum` (`enum_id`) USING BTREE,
    INDEX `idx_property_type_table` (`type_table_id`) USING BTREE,
    CONSTRAINT `fk_property_column` FOREIGN KEY (`column_id`) REFERENCES `gen_column` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_entity` FOREIGN KEY (`entity_id`) REFERENCES `gen_entity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_type_table` FOREIGN KEY (`type_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '生成属性'
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
    `order_key`        bigint       NOT NULL COMMENT '排序键',
    `remark`           varchar(500) NOT NULL COMMENT '备注',
    `created_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '列到属性类型映射'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
CREATE TABLE `gen_column_default`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `data_source_type`  varchar(500) NULL COMMENT '数据源类型',
    `type_code`         int(0)       NOT NULL COMMENT 'JdbcType 码值',
    `raw_type`          varchar(500) NOT NULL COMMENT '字面类型',
    `data_size`         bigint       NOT NULL COMMENT '列长度',
    `numeric_precision` bigint       NOT NULL COMMENT '列精度',
    `default_value`     varchar(500) NULL     DEFAULT NULL COMMENT '默认值',
    `order_key`         bigint       NOT NULL COMMENT '排序键',
    `remark`            varchar(500) NOT NULL COMMENT '备注',
    `created_time`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
    COMMENT = '列默认'
  ROW_FORMAT = Dynamic;

INSERT INTO `gen_column_default`
(`data_source_type`, `type_code`, `raw_type`, `data_size`, `numeric_precision`, `default_value`, `order_key`, `remark`)
VALUES (NULL, 12, 'VARCHAR', 255, 0, NULL, 1, ''),
       ('PostgreSQL', 12, 'TEXT', 0, 0, NULL, 2, '');

SET FOREIGN_KEY_CHECKS = 1;

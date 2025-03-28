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
DROP TABLE IF EXISTS `gen_model_sub_group` CASCADE;

-- ----------------------------
-- Table structure for gen_model
-- ----------------------------
CREATE TABLE `gen_model`
(
    `id`                         bigint       NOT NULL AUTO_INCREMENT,
    `name`                       varchar(500) NOT NULL,
    `graph_data`                 longtext     NOT NULL,
    `language`                   varchar(500) NOT NULL,
    `data_source_type`           varchar(500) NOT NULL,
    `view_type`                  varchar(500) NOT NULL,
    `author`                     varchar(500) NOT NULL,
    `package_path`               varchar(500) NOT NULL,
    `table_path`                 varchar(500) NOT NULL,
    `database_naming_strategy`   varchar(500) NOT NULL,
    `real_fk`                    boolean      NOT NULL,
    `id_view_property`           boolean      NOT NULL,
    `default_id_type`            int          NOT NULL,
    `generated_id_annotation`    varchar(500) NOT NULL,
    `logical_deleted_annotation` varchar(500) NOT NULL,
    `date_time_format_in_view`   boolean      NOT NULL,
    `table_annotation`           boolean      NOT NULL,
    `column_annotation`          boolean      NOT NULL,
    `join_table_annotation`      boolean      NOT NULL,
    `join_column_annotation`     boolean      NOT NULL,
    `table_name_prefixes`        varchar(500) NOT NULL,
    `table_name_suffixes`        varchar(500) NOT NULL,
    `table_comment_prefixes`     varchar(500) NOT NULL,
    `table_comment_suffixes`     varchar(500) NOT NULL,
    `column_name_prefixes`       varchar(500) NOT NULL,
    `column_name_suffixes`       varchar(500) NOT NULL,
    `column_comment_prefixes`    varchar(500) NOT NULL,
    `column_comment_suffixes`    varchar(500) NOT NULL,
    `remark`                     varchar(500) NOT NULL,
    `created_time`               TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_model` IS '生成模型';
COMMENT ON COLUMN `gen_model`.`id` IS 'ID';
COMMENT ON COLUMN `gen_model`.`name` IS '名称';
COMMENT ON COLUMN `gen_model`.`graph_data` IS 'Graph 数据';
COMMENT ON COLUMN `gen_model`.`language` IS '语言';
COMMENT ON COLUMN `gen_model`.`data_source_type` IS '数据源类型';
COMMENT ON COLUMN `gen_model`.`view_type` IS '视图类型';
COMMENT ON COLUMN `gen_model`.`author` IS '作者';
COMMENT ON COLUMN `gen_model`.`package_path` IS '包路径';
COMMENT ON COLUMN `gen_model`.`table_path` IS '表路径';
COMMENT ON COLUMN `gen_model`.`database_naming_strategy` IS '数据库命名策略';
COMMENT ON COLUMN `gen_model`.`real_fk` IS '启用真实外键';
COMMENT ON COLUMN `gen_model`.`id_view_property` IS '生成 IdView 属性';
COMMENT ON COLUMN `gen_model`.`default_id_type` IS '默认 Id 类型';
COMMENT ON COLUMN `gen_model`.`generated_id_annotation` IS 'Id 注解';
COMMENT ON COLUMN `gen_model`.`logical_deleted_annotation` IS '逻辑删除注解';
COMMENT ON COLUMN `gen_model`.`date_time_format_in_view` IS '在前端视图中进行日期格式化';
COMMENT ON COLUMN `gen_model`.`table_annotation` IS '生成 Table 注解';
COMMENT ON COLUMN `gen_model`.`column_annotation` IS '生成 Column 注解';
COMMENT ON COLUMN `gen_model`.`join_table_annotation` IS '生成 JoinTable 注解';
COMMENT ON COLUMN `gen_model`.`join_column_annotation` IS '生成 JoinColumn 注解';
COMMENT ON COLUMN `gen_model`.`table_name_prefixes` IS '转换实体时移除的表名前缀';
COMMENT ON COLUMN `gen_model`.`table_name_suffixes` IS '转换实体时移除的表名后缀';
COMMENT ON COLUMN `gen_model`.`table_comment_prefixes` IS '转换实体时移除的表注释前缀';
COMMENT ON COLUMN `gen_model`.`table_comment_suffixes` IS '转换实体时移除的表注释后缀';
COMMENT ON COLUMN `gen_model`.`column_name_prefixes` IS '转换属性时移除的列名前缀';
COMMENT ON COLUMN `gen_model`.`column_name_suffixes` IS '转换属性时移除的列名后缀';
COMMENT ON COLUMN `gen_model`.`column_comment_prefixes` IS '转换属性时移除的列注释前缀';
COMMENT ON COLUMN `gen_model`.`column_comment_suffixes` IS '转换属性时移除的列注释后缀';
COMMENT ON COLUMN `gen_model`.`remark` IS '备注';
COMMENT ON COLUMN `gen_model`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_model`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_enum
-- ----------------------------
CREATE TABLE `gen_enum`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `model_id`      bigint       NULL,
    `sub_group_id`  BIGINT                DEFAULT NULL,
    `package_path`  varchar(500) NOT NULL,
    `name`          varchar(500) NOT NULL,
    `comment`       varchar(500) NOT NULL,
    `enum_type`     varchar(500) NULL     DEFAULT NULL,
    `remark`        varchar(500) NOT NULL,
    `created_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_enum_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

COMMENT ON TABLE `gen_enum` IS '生成枚举';
COMMENT ON COLUMN `gen_enum`.`id` IS 'ID';
COMMENT ON COLUMN `gen_enum`.`model_id` IS '模型';
COMMENT ON COLUMN `gen_enum`.`sub_group_id` IS '子组';
COMMENT ON COLUMN `gen_enum`.`package_path` IS '包路径';
COMMENT ON COLUMN `gen_enum`.`name` IS '枚举名';
COMMENT ON COLUMN `gen_enum`.`comment` IS '枚举注释';
COMMENT ON COLUMN `gen_enum`.`enum_type` IS '枚举类型';
COMMENT ON COLUMN `gen_enum`.`remark` IS '备注';
COMMENT ON COLUMN `gen_enum`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_enum`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_enum_item
-- ----------------------------
CREATE TABLE `gen_enum_item`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `enum_id`       bigint       NOT NULL,
    `name`          varchar(500) NOT NULL,
    `mapped_value`  varchar(500) NOT NULL,
    `comment`       varchar(500) NOT NULL,
    `order_key`     bigint       NOT NULL,
    `default_item`  boolean      NOT NULL,
    `remark`        varchar(500) NOT NULL,
    `created_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_enum_item_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX `idx_enum_item_enum` ON `gen_enum_item` (`enum_id`);

COMMENT ON TABLE `gen_enum_item` IS '生成枚举元素';
COMMENT ON COLUMN `gen_enum_item`.`id` IS 'ID';
COMMENT ON COLUMN `gen_enum_item`.`enum_id` IS '对应枚举';
COMMENT ON COLUMN `gen_enum_item`.`name` IS '元素名';
COMMENT ON COLUMN `gen_enum_item`.`mapped_value` IS '映射值';
COMMENT ON COLUMN `gen_enum_item`.`comment` IS '元素注释';
COMMENT ON COLUMN `gen_enum_item`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_enum_item`.`default_item` IS '是否是默认值';
COMMENT ON COLUMN `gen_enum_item`.`remark` IS '备注';
COMMENT ON COLUMN `gen_enum_item`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_enum_item`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_data_source
-- ----------------------------
CREATE TABLE `gen_data_source`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `type`          varchar(500) NOT NULL,
    `name`          varchar(500) NOT NULL,
    `url`           varchar(500) NOT NULL,
    `username`      varchar(500) NOT NULL,
    `password`      varchar(500) NOT NULL,
    `remark`        varchar(500) NOT NULL,
    `created_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_data_source` IS '生成数据源';
COMMENT ON COLUMN `gen_data_source`.`id` IS 'ID';
COMMENT ON COLUMN `gen_data_source`.`type` IS '数据库类型';
COMMENT ON COLUMN `gen_data_source`.`name` IS '名称';
COMMENT ON COLUMN `gen_data_source`.`url` IS '链接';
COMMENT ON COLUMN `gen_data_source`.`username` IS '用户名';
COMMENT ON COLUMN `gen_data_source`.`password` IS '密码';
COMMENT ON COLUMN `gen_data_source`.`remark` IS '备注';
COMMENT ON COLUMN `gen_data_source`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_data_source`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_schema
-- ----------------------------
CREATE TABLE `gen_schema`
(
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `data_source_id` bigint       NOT NULL,
    `name`           varchar(500) NOT NULL,
    `remark`         varchar(500) NOT NULL,
    `created_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_schema_data_source` FOREIGN KEY (`data_source_id`) REFERENCES `gen_data_source` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX `idx_schema_data_source` ON `gen_schema` (`data_source_id`);

COMMENT ON TABLE `gen_schema` IS '生成数据架构';
COMMENT ON COLUMN `gen_schema`.`id` IS 'ID';
COMMENT ON COLUMN `gen_schema`.`data_source_id` IS '数据源';
COMMENT ON COLUMN `gen_schema`.`name` IS '名称';
COMMENT ON COLUMN `gen_schema`.`remark` IS '备注';
COMMENT ON COLUMN `gen_schema`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_schema`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
CREATE TABLE `gen_table`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `model_id`      bigint       NULL,
    `schema_id`     bigint       NULL,
    `sub_group_id`  BIGINT                DEFAULT NULL,
    `name`          varchar(500) NOT NULL,
    `comment`       varchar(500) NOT NULL,
    `type`          varchar(500) NOT NULL,
    `remark`        varchar(500) NOT NULL,
    `created_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_table_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_table_schema` FOREIGN KEY (`schema_id`) REFERENCES `gen_schema` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX `idx_table_model` ON `gen_table` (`model_id`);
CREATE INDEX `idx_table_schema` ON `gen_table` (`schema_id`);

COMMENT ON TABLE `gen_table` IS '生成表';
COMMENT ON COLUMN `gen_table`.`id` IS 'ID';
COMMENT ON COLUMN `gen_table`.`model_id` IS '模型';
COMMENT ON COLUMN `gen_table`.`schema_id` IS '数据架构';
COMMENT ON COLUMN `gen_table`.`sub_group_id` IS '子组';
COMMENT ON COLUMN `gen_table`.`name` IS '名称';
COMMENT ON COLUMN `gen_table`.`comment` IS '注释';
COMMENT ON COLUMN `gen_table`.`type` IS '种类';
COMMENT ON COLUMN `gen_table`.`remark` IS '备注';
COMMENT ON COLUMN `gen_table`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_table`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_super_table_mapping
-- ----------------------------
CREATE TABLE `gen_super_table_mapping`
(
    `super_table_id`   bigint NOT NULL,
    `inherit_table_id` bigint NOT NULL,
    PRIMARY KEY (`super_table_id`, `inherit_table_id`),
    CONSTRAINT `fk_super_table_mapping_super_table` FOREIGN KEY (`super_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_super_table_mapping_inherit_table` FOREIGN KEY (`inherit_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

COMMENT ON TABLE `gen_super_table_mapping` IS '上级表与继承表关联表';
COMMENT ON COLUMN `gen_super_table_mapping`.`super_table_id` IS '上级表';
COMMENT ON COLUMN `gen_super_table_mapping`.`inherit_table_id` IS '继承表';

-- ----------------------------
-- Table structure for gen_column
-- ----------------------------
CREATE TABLE `gen_column`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `table_id`          bigint       NOT NULL,
    `name`              varchar(500) NOT NULL,
    `type_code`         int          NOT NULL,
    `overwrite_by_raw`  boolean      NOT NULL,
    `raw_type`          varchar(500) NOT NULL,
    `type_not_null`     boolean      NOT NULL,
    `data_size`         bigint       NOT NULL,
    `numeric_precision` bigint       NOT NULL,
    `default_value`     varchar(500) NULL     DEFAULT NULL,
    `comment`           varchar(500) NOT NULL,
    `part_of_pk`        boolean      NOT NULL,
    `auto_increment`    boolean      NOT NULL,
    `business_key`      boolean      NOT NULL,
    `key_group`         varchar(500) NULL     DEFAULT NULL,
    `logical_delete`    boolean      NOT NULL,
    `enum_id`           bigint       NULL     DEFAULT NULL,
    `order_key`         bigint       NOT NULL,
    `remark`            varchar(500) NOT NULL,
    `created_time`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_column_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX `idx_column_table` ON `gen_column` (`table_id`);
CREATE INDEX `idx_column_enum` ON `gen_column` (`enum_id`);

COMMENT ON TABLE `gen_column` IS '生成列';
COMMENT ON COLUMN `gen_column`.`id` IS 'ID';
COMMENT ON COLUMN `gen_column`.`table_id` IS '归属表';
COMMENT ON COLUMN `gen_column`.`name` IS '名称';
COMMENT ON COLUMN `gen_column`.`type_code` IS 'JdbcType 码值';
COMMENT ON COLUMN `gen_column`.`overwrite_by_raw` IS '覆盖为字面类型';
COMMENT ON COLUMN `gen_column`.`raw_type` IS '字面类型';
COMMENT ON COLUMN `gen_column`.`type_not_null` IS '是否非空';
COMMENT ON COLUMN `gen_column`.`data_size` IS '长度';
COMMENT ON COLUMN `gen_column`.`numeric_precision` IS '精度';
COMMENT ON COLUMN `gen_column`.`default_value` IS '列默认值';
COMMENT ON COLUMN `gen_column`.`comment` IS '注释';
COMMENT ON COLUMN `gen_column`.`part_of_pk` IS '是否为主键的部分';
COMMENT ON COLUMN `gen_column`.`auto_increment` IS '是否自增';
COMMENT ON COLUMN `gen_column`.`business_key` IS '是否为业务键';
COMMENT ON COLUMN `gen_column`.`key_group` IS '业务键组';
COMMENT ON COLUMN `gen_column`.`logical_delete` IS '是否为逻辑删除';
COMMENT ON COLUMN `gen_column`.`enum_id` IS '枚举';
COMMENT ON COLUMN `gen_column`.`remark` IS '备注';
COMMENT ON COLUMN `gen_column`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_column`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_column`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_association
-- ----------------------------
CREATE TABLE `gen_association`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `model_id`          bigint       NULL,
    `name`              varchar(500) NOT NULL,
    `source_table_id`   bigint       NOT NULL,
    `target_table_id`   bigint       NOT NULL,
    `type`              varchar(500) NOT NULL,
    `dissociate_action` varchar(500) NULL     DEFAULT NULL,
    `update_action`     varchar(500) NOT NULL,
    `delete_action`     varchar(500) NOT NULL,
    `fake`              boolean      NOT NULL,
    `remark`            varchar(500) NOT NULL,
    `created_time`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_association_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_association_source_column` FOREIGN KEY (`source_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_association_target_column` FOREIGN KEY (`target_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX `idx_association_model` ON `gen_association` (`model_id`);
CREATE INDEX `idx_association_source_table` ON `gen_association` (`source_table_id`);
CREATE INDEX `idx_association_target_table` ON `gen_association` (`target_table_id`);

COMMENT ON TABLE `gen_association` IS '生成关联';
COMMENT ON COLUMN `gen_association`.`id` IS 'ID';
COMMENT ON COLUMN `gen_association`.`model_id` IS '模型';
COMMENT ON COLUMN `gen_association`.`name` IS '关联名称';
COMMENT ON COLUMN `gen_association`.`source_table_id` IS '主表';
COMMENT ON COLUMN `gen_association`.`target_table_id` IS '从表';
COMMENT ON COLUMN `gen_association`.`type` IS '关联类型';
COMMENT ON COLUMN `gen_association`.`dissociate_action` IS '脱钩行为';
COMMENT ON COLUMN `gen_association`.`update_action` IS '更新行为';
COMMENT ON COLUMN `gen_association`.`delete_action` IS '删除行为';
COMMENT ON COLUMN `gen_association`.`fake` IS '是否伪外键';
COMMENT ON COLUMN `gen_association`.`remark` IS '备注';
COMMENT ON COLUMN `gen_association`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_association`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_column_reference
-- ----------------------------
CREATE TABLE `gen_column_reference`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `association_id`   bigint       NOT NULL,
    `source_column_id` bigint       NOT NULL,
    `target_column_id` bigint       NOT NULL,
    `order_key`        bigint       NOT NULL,
    `created_time`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `remark`           varchar(500) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_column_reference_association` FOREIGN KEY (`association_id`) REFERENCES `gen_association` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_reference_source_column` FOREIGN KEY (`source_column_id`) REFERENCES `gen_column` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_column_reference_target_column` FOREIGN KEY (`target_column_id`) REFERENCES `gen_column` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX `idx_column_reference_association` ON `gen_column_reference` (`association_id`);
CREATE INDEX `idx_column_reference_source_column` ON `gen_column_reference` (`source_column_id`);
CREATE INDEX `idx_column_reference_target_column` ON `gen_column_reference` (`target_column_id`);

COMMENT ON TABLE `gen_column_reference` IS '列引用';
COMMENT ON COLUMN `gen_column_reference`.`id` IS 'ID';
COMMENT ON COLUMN `gen_column_reference`.`association_id` IS '关联';
COMMENT ON COLUMN `gen_column_reference`.`source_column_id` IS '主列';
COMMENT ON COLUMN `gen_column_reference`.`target_column_id` IS '从列';
COMMENT ON COLUMN `gen_column_reference`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_column_reference`.`remark` IS '备注';
COMMENT ON COLUMN `gen_column_reference`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_column_reference`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_table_index
-- ----------------------------
CREATE TABLE `gen_table_index`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `table_id`      bigint       NOT NULL,
    `name`          varchar(500) NOT NULL,
    `unique_index`  boolean      NOT NULL,
    `remark`        varchar(500) NOT NULL,
    `created_time`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_index_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX `idx_index_table` ON `gen_table_index` (`table_id`);

COMMENT ON TABLE `gen_table_index` IS '列引用';
COMMENT ON COLUMN `gen_table_index`.`id` IS 'ID';
COMMENT ON COLUMN `gen_table_index`.`table_id` IS '归属表';
COMMENT ON COLUMN `gen_table_index`.`name` IS '名称';
COMMENT ON COLUMN `gen_table_index`.`unique_index` IS '是否唯一索引';
COMMENT ON COLUMN `gen_table_index`.`remark` IS '备注';
COMMENT ON COLUMN `gen_table_index`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_table_index`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_index_column_mapping
-- ----------------------------
CREATE TABLE `gen_index_column_mapping`
(
    `index_id`  bigint NOT NULL,
    `column_id` bigint NOT NULL,
    PRIMARY KEY (`index_id`, `column_id`),
    CONSTRAINT `fk_columns_mapping_index` FOREIGN KEY (`index_id`) REFERENCES `gen_table_index` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_index_mapping_column` FOREIGN KEY (`column_id`) REFERENCES `gen_column` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

COMMENT ON TABLE `gen_index_column_mapping` IS '索引与列关联表';
COMMENT ON COLUMN `gen_index_column_mapping`.`index_id` IS '索引';
COMMENT ON COLUMN `gen_index_column_mapping`.`column_id` IS '列';

-- ----------------------------
-- Table structure for gen_entity
-- ----------------------------
CREATE TABLE `gen_entity`
(
    `id`                   bigint       NOT NULL AUTO_INCREMENT,
    `model_id`             bigint       NULL,
    `package_path`         varchar(500) NOT NULL,
    `table_id`             bigint       NOT NULL,
    `name`                 varchar(500) NOT NULL,
    `overwrite_name`       boolean      NOT NULL,
    `comment`              varchar(500) NOT NULL,
    `overwrite_comment`    boolean      NOT NULL,
    `author`               varchar(500) NOT NULL,
    `other_annotation`     varchar(500) NULL     DEFAULT NULL,
    `can_add`              boolean      NOT NULL,
    `can_edit`             boolean      NOT NULL,
    `can_delete`           boolean      NOT NULL,
    `can_query`            boolean      NOT NULL,
    `has_page`             boolean      NOT NULL,
    `page_can_query`       boolean      NOT NULL,
    `page_can_add`         boolean      NOT NULL,
    `page_can_edit`        boolean      NOT NULL,
    `page_can_view_detail` boolean      NOT NULL,
    `page_can_delete`      boolean      NOT NULL,
    `query_by_page`        boolean      NOT NULL,
    `remark`               varchar(500) NOT NULL,
    `created_time`         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_entity_model` FOREIGN KEY (`model_id`) REFERENCES `gen_model` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_entity_table` FOREIGN KEY (`table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE UNIQUE INDEX `uidx_entity_table` ON `gen_entity` (`table_id`);
CREATE INDEX `idx_entity_model` ON `gen_entity` (`model_id`);
CREATE INDEX `idx_entity_table` ON `gen_entity` (`table_id`);

COMMENT ON TABLE `gen_entity` IS '生成实体';
COMMENT ON COLUMN `gen_entity`.`id` IS 'ID';
COMMENT ON COLUMN `gen_entity`.`model_id` IS '模型';
COMMENT ON COLUMN `gen_entity`.`package_path` IS '包路径';
COMMENT ON COLUMN `gen_entity`.`table_id` IS '对应表';
COMMENT ON COLUMN `gen_entity`.`name` IS '类名称';
COMMENT ON COLUMN `gen_entity`.`overwrite_name` IS '覆盖自动生成类名称';
COMMENT ON COLUMN `gen_entity`.`comment` IS '类注释';
COMMENT ON COLUMN `gen_entity`.`overwrite_comment` IS '覆盖自动生成注释';
COMMENT ON COLUMN `gen_entity`.`author` IS '作者';
COMMENT ON COLUMN `gen_entity`.`other_annotation` IS '其他注解';
COMMENT ON COLUMN `gen_entity`.`can_add` IS '是否可以创建';
COMMENT ON COLUMN `gen_entity`.`can_edit` IS '是否可以修改';
COMMENT ON COLUMN `gen_entity`.`can_delete` IS '是否可以删除';
COMMENT ON COLUMN `gen_entity`.`can_query` IS '是否可以查询';
COMMENT ON COLUMN `gen_entity`.`has_page` IS '是否具有页面';
COMMENT ON COLUMN `gen_entity`.`page_can_query` IS '管理端页面中可查询';
COMMENT ON COLUMN `gen_entity`.`page_can_add` IS '管理端页面中可新增';
COMMENT ON COLUMN `gen_entity`.`page_can_edit` IS '管理端页面中可编辑';
COMMENT ON COLUMN `gen_entity`.`page_can_view_detail` IS '管理端页面中可查看详情';
COMMENT ON COLUMN `gen_entity`.`page_can_delete` IS '管理端页面中可删除';
COMMENT ON COLUMN `gen_entity`.`query_by_page` IS '应用分页查询';
COMMENT ON COLUMN `gen_entity`.`remark` IS '备注';
COMMENT ON COLUMN `gen_entity`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_entity`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_super_entity_mapping
-- ----------------------------
CREATE TABLE `gen_super_entity_mapping`
(
    `super_entity_id`   bigint NOT NULL,
    `inherit_entity_id` bigint NOT NULL,
    PRIMARY KEY (`super_entity_id`, `inherit_entity_id`),
    CONSTRAINT `fk_super_entity_mapping_super_entity` FOREIGN KEY (`super_entity_id`) REFERENCES `gen_entity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_super_entity_mapping_inherit_entity` FOREIGN KEY (`inherit_entity_id`) REFERENCES `gen_entity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

COMMENT ON TABLE `gen_super_entity_mapping` IS '上级实体与继承实体关联表';
COMMENT ON COLUMN `gen_super_entity_mapping`.`super_entity_id` IS '上级实体';
COMMENT ON COLUMN `gen_super_entity_mapping`.`inherit_entity_id` IS '继承实体';

-- ----------------------------
-- Table structure for gen_property
-- ----------------------------
CREATE TABLE `gen_property`
(
    `id`                         bigint       NOT NULL AUTO_INCREMENT,
    `entity_id`                  bigint       NOT NULL,
    `column_id`                  bigint       NULL     DEFAULT NULL,
    `name`                       varchar(500) NOT NULL,
    `overwrite_name`             boolean      NOT NULL,
    `comment`                    varchar(500) NOT NULL,
    `overwrite_comment`          boolean      NOT NULL,
    `type`                       varchar(500) NOT NULL,
    `type_table_id`              bigint       NULL     DEFAULT NULL,
    `list_type`                  boolean      NOT NULL,
    `type_not_null`              boolean      NOT NULL,
    `id_property`                boolean      NOT NULL,
    `generated_id`               boolean      NOT NULL,
    `generated_id_annotation`    varchar(500) NULL     DEFAULT NULL,
    `key_property`               boolean      NOT NULL,
    `key_group`                  varchar(500) NULL     DEFAULT NULL,
    `logical_delete`             boolean      NOT NULL,
    `logical_deleted_annotation` varchar(500) NULL     DEFAULT NULL,
    `id_view`                    boolean      NOT NULL,
    `id_view_target`             varchar(500) NULL     DEFAULT NULL,
    `association_type`           varchar(500) NULL     DEFAULT NULL,
    `long_association`           boolean      NOT NULL,
    `mapped_by`                  varchar(500) NULL     DEFAULT NULL,
    `input_not_null`             boolean      NULL     DEFAULT NULL,
    `join_column_metas`          varchar(500) NULL     DEFAULT NULL,
    `join_table_meta`            varchar(500) NULL     DEFAULT NULL,
    `dissociate_annotation`      varchar(500) NULL     DEFAULT NULL,
    `other_annotation`           varchar(500) NULL     DEFAULT NULL,
    `body`                       varchar(500) NULL     DEFAULT NULL,
    `enum_id`                    bigint       NULL     DEFAULT NULL,
    `order_key`                  bigint       NOT NULL,
    `special_form_type`          varchar(500) NULL     DEFAULT NULL,
    `in_list_view`               boolean      NOT NULL,
    `in_detail_view`             boolean      NOT NULL,
    `in_insert_input`            boolean      NOT NULL,
    `in_update_input`            boolean      NOT NULL,
    `in_specification`           boolean      NOT NULL,
    `in_option_view`             boolean      NOT NULL,
    `in_short_association_view`  boolean      NOT NULL,
    `in_long_association_view`   boolean      NOT NULL,
    `in_long_association_input`  boolean      NOT NULL,
    `remark`                     varchar(500) NOT NULL,
    `created_time`               TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`              TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_property_column` FOREIGN KEY (`column_id`) REFERENCES `gen_column` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_entity` FOREIGN KEY (`entity_id`) REFERENCES `gen_entity` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_enum` FOREIGN KEY (`enum_id`) REFERENCES `gen_enum` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_property_type_table` FOREIGN KEY (`type_table_id`) REFERENCES `gen_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX `idx_property_column` ON `gen_property` (`column_id`);
CREATE INDEX `idx_property_entity` ON `gen_property` (`entity_id`);
CREATE INDEX `idx_property_enum` ON `gen_property` (`enum_id`);
CREATE INDEX `idx_property_type_table` ON `gen_property` (`type_table_id`);

COMMENT ON TABLE `gen_property` IS '生成属性';
COMMENT ON COLUMN `gen_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_property`.`entity_id` IS '归属实体';
COMMENT ON COLUMN `gen_property`.`column_id` IS '对应列';
COMMENT ON COLUMN `gen_property`.`name` IS '属性名';
COMMENT ON COLUMN `gen_property`.`overwrite_name` IS '覆盖自动生成属性名';
COMMENT ON COLUMN `gen_property`.`comment` IS '属性注释';
COMMENT ON COLUMN `gen_property`.`overwrite_comment` IS '覆盖自动生成注释';
COMMENT ON COLUMN `gen_property`.`type` IS '属性类型';
COMMENT ON COLUMN `gen_property`.`type_table_id` IS '类型对应表';
COMMENT ON COLUMN `gen_property`.`list_type` IS '是否列表';
COMMENT ON COLUMN `gen_property`.`type_not_null` IS '是否非空';
COMMENT ON COLUMN `gen_property`.`id_property` IS '是否 ID 属性';
COMMENT ON COLUMN `gen_property`.`generated_id` IS '是否是生成式 ID';
COMMENT ON COLUMN `gen_property`.`generated_id_annotation` IS '生成 ID 注解';
COMMENT ON COLUMN `gen_property`.`key_property` IS '是否为业务键属性';
COMMENT ON COLUMN `gen_property`.`key_group` IS '业务键组';
COMMENT ON COLUMN `gen_property`.`logical_delete` IS '是否为逻辑删除属性';
COMMENT ON COLUMN `gen_property`.`logical_deleted_annotation` IS '逻辑删除注解';
COMMENT ON COLUMN `gen_property`.`id_view` IS '是否为 ID 视图属性';
COMMENT ON COLUMN `gen_property`.`id_view_target` IS 'ID 视图目标';
COMMENT ON COLUMN `gen_property`.`association_type` IS '关联类型';
COMMENT ON COLUMN `gen_property`.`long_association` IS '是否为长关联';
COMMENT ON COLUMN `gen_property`.`mapped_by` IS '映射镜像';
COMMENT ON COLUMN `gen_property`.`input_not_null` IS '输入非空';
COMMENT ON COLUMN `gen_property`.`join_column_metas` IS '关联列元数据';
COMMENT ON COLUMN `gen_property`.`join_table_meta` IS '关联表元数据';
COMMENT ON COLUMN `gen_property`.`dissociate_annotation` IS '脱钩注解';
COMMENT ON COLUMN `gen_property`.`other_annotation` IS '其他注解';
COMMENT ON COLUMN `gen_property`.`body` IS '属性方法体';
COMMENT ON COLUMN `gen_property`.`enum_id` IS '对应枚举';
COMMENT ON COLUMN `gen_property`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_property`.`special_form_type` IS '特殊表单类型';
COMMENT ON COLUMN `gen_property`.`in_list_view` IS '是否在列表视图DTO中';
COMMENT ON COLUMN `gen_property`.`in_detail_view` IS '是否在详情视图DTO中';
COMMENT ON COLUMN `gen_property`.`in_insert_input` IS '是否在新增入参DTO中';
COMMENT ON COLUMN `gen_property`.`in_update_input` IS '是否在修改入参DTO中';
COMMENT ON COLUMN `gen_property`.`in_specification` IS '是否在查询规格DTO中';
COMMENT ON COLUMN `gen_property`.`in_option_view` IS '是否在选项视图DTO中';
COMMENT ON COLUMN `gen_property`.`in_short_association_view` IS '是否在短关联视图DTO中';
COMMENT ON COLUMN `gen_property`.`in_long_association_view` IS '是否在长关联视图DTO中';
COMMENT ON COLUMN `gen_property`.`in_long_association_input` IS '是否在长关联入参DTO中';
COMMENT ON COLUMN `gen_property`.`remark` IS '备注';
COMMENT ON COLUMN `gen_property`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_property`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
CREATE TABLE `gen_type_mapping`
(
    `id`               bigint       NOT NULL AUTO_INCREMENT,
    `data_source_type` varchar(500) NOT NULL,
    `type_expression`  varchar(500) NOT NULL,
    `language`         varchar(500) NOT NULL,
    `property_type`    varchar(500) NOT NULL,
    `order_key`        bigint       NOT NULL,
    `remark`           varchar(500) NOT NULL,
    `created_time`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_type_mapping` IS '列到属性类型映射';
COMMENT ON COLUMN `gen_type_mapping`.`id` IS 'ID';
COMMENT ON COLUMN `gen_type_mapping`.`data_source_type` IS '数据源类型';
COMMENT ON COLUMN `gen_type_mapping`.`type_expression` IS '数据库类型表达式';
COMMENT ON COLUMN `gen_type_mapping`.`language` IS '语言';
COMMENT ON COLUMN `gen_type_mapping`.`property_type` IS '属性类型';
COMMENT ON COLUMN `gen_type_mapping`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_type_mapping`.`remark` IS '备注';
COMMENT ON COLUMN `gen_type_mapping`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_type_mapping`.`modified_time` IS '修改时间';

-- ----------------------------
-- Table structure for gen_type_mapping
-- ----------------------------
CREATE TABLE `gen_column_default`
(
    `id`                bigint       NOT NULL AUTO_INCREMENT,
    `data_source_type`  varchar(500) NULL,
    `type_code`         int          NOT NULL,
    `raw_type`          varchar(500) NOT NULL,
    `data_size`         bigint       NOT NULL,
    `numeric_precision` bigint       NOT NULL,
    `default_value`     varchar(500) NULL     DEFAULT NULL,
    `order_key`         bigint       NOT NULL,
    `remark`            varchar(500) NOT NULL,
    `created_time`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_column_default` IS '列默认';
COMMENT ON COLUMN `gen_column_default`.`id` IS 'ID';
COMMENT ON COLUMN `gen_column_default`.`data_source_type` IS '数据源类型';
COMMENT ON COLUMN `gen_column_default`.`type_code` IS 'JdbcType 码值';
COMMENT ON COLUMN `gen_column_default`.`raw_type` IS '字面类型';
COMMENT ON COLUMN `gen_column_default`.`data_size` IS '长度';
COMMENT ON COLUMN `gen_column_default`.`numeric_precision` IS '精度';
COMMENT ON COLUMN `gen_column_default`.`default_value` IS '默认值';
COMMENT ON COLUMN `gen_column_default`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_column_default`.`remark` IS '备注';
COMMENT ON COLUMN `gen_column_default`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_column_default`.`modified_time` IS '修改时间';

INSERT INTO `gen_column_default`
(`data_source_type`, `type_code`, `raw_type`, `data_size`, `numeric_precision`, `default_value`, `order_key`, `remark`)
VALUES (NULL, 12, 'VARCHAR', 255, 0, NULL, 1, ''),
       ('PostgreSQL', 12, 'TEXT', 0, 0, NULL, 2, '');

CREATE TABLE `gen_model_sub_group`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT,
    `model_id`         BIGINT       NOT NULL,
    `name`             varchar(500) NOT NULL,
    `comment`          varchar(500) NOT NULL,
    `sub_package_path` varchar(500) NOT NULL,
    `style`            longtext     NOT NULL,
    `created_time`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_time`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_model_sub_group` IS '生成模型子组';
COMMENT ON COLUMN `gen_model_sub_group`.`id` IS 'ID';
COMMENT ON COLUMN `gen_model_sub_group`.`model_id` IS '模型';
COMMENT ON COLUMN `gen_model_sub_group`.`name` IS '名称';
COMMENT ON COLUMN `gen_model_sub_group`.`comment` IS '注释';
COMMENT ON COLUMN `gen_model_sub_group`.`sub_package_path` IS '子包路径';
COMMENT ON COLUMN `gen_model_sub_group`.`style` IS '样式';

ALTER TABLE `gen_model_sub_group`
    ADD CONSTRAINT `fk_gen_model_sub_group_model_id`
        FOREIGN KEY (`model_id`)
            REFERENCES `gen_model` (`id`);

ALTER TABLE `gen_enum`
    ADD CONSTRAINT `fk_gen_enum_sub_group_id`
        FOREIGN KEY (`sub_group_id`)
            REFERENCES `gen_model_sub_group` (`id`);

ALTER TABLE `gen_table`
    ADD CONSTRAINT `fk_gen_table_sub_group_id`
        FOREIGN KEY (`sub_group_id`)
            REFERENCES `gen_model_sub_group` (`id`);

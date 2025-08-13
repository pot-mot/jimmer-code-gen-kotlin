DROP TABLE IF EXISTS `db_data_source` CASCADE;
DROP TABLE IF EXISTS `db_foreign_key` CASCADE;
DROP TABLE IF EXISTS `db_foreign_key_column_reference` CASCADE;
DROP TABLE IF EXISTS `db_primary_key` CASCADE;
DROP TABLE IF EXISTS `db_schema` CASCADE;
DROP TABLE IF EXISTS `db_table` CASCADE;
DROP TABLE IF EXISTS `db_table_column` CASCADE;
DROP TABLE IF EXISTS `db_table_index` CASCADE;
DROP TABLE IF EXISTS `gen_column_info` CASCADE;
DROP TABLE IF EXISTS `gen_embeddable_type` CASCADE;
DROP TABLE IF EXISTS `gen_embeddable_type_deep_property` CASCADE;
DROP TABLE IF EXISTS `gen_embeddable_type_property` CASCADE;
DROP TABLE IF EXISTS `gen_entity` CASCADE;
DROP TABLE IF EXISTS `gen_entity_index` CASCADE;
DROP TABLE IF EXISTS `gen_entity_inherit` CASCADE;
DROP TABLE IF EXISTS `gen_entity_key_group` CASCADE;
DROP TABLE IF EXISTS `gen_enum` CASCADE;
DROP TABLE IF EXISTS `gen_enum_item` CASCADE;
DROP TABLE IF EXISTS `gen_extra_property` CASCADE;
DROP TABLE IF EXISTS `gen_id_property` CASCADE;
DROP TABLE IF EXISTS `gen_join_table` CASCADE;
DROP TABLE IF EXISTS `gen_join_table_column` CASCADE;
DROP TABLE IF EXISTS `gen_join_table_filter` CASCADE;
DROP TABLE IF EXISTS `gen_join_table_logical_delete_filter` CASCADE;
DROP TABLE IF EXISTS `gen_logical_delete_property` CASCADE;
DROP TABLE IF EXISTS `gen_many_to_many_association` CASCADE;
DROP TABLE IF EXISTS `gen_many_to_many_mapped_property` CASCADE;
DROP TABLE IF EXISTS `gen_many_to_many_source_property` CASCADE;
DROP TABLE IF EXISTS `gen_many_to_one_association` CASCADE;
DROP TABLE IF EXISTS `gen_many_to_one_mapped_property` CASCADE;
DROP TABLE IF EXISTS `gen_many_to_one_source_property` CASCADE;
DROP TABLE IF EXISTS `gen_model` CASCADE;
DROP TABLE IF EXISTS `gen_model_config` CASCADE;
DROP TABLE IF EXISTS `gen_model_group` CASCADE;
DROP TABLE IF EXISTS `gen_one_to_one_association` CASCADE;
DROP TABLE IF EXISTS `gen_one_to_one_mapped_property` CASCADE;
DROP TABLE IF EXISTS `gen_one_to_one_source_property` CASCADE;
DROP TABLE IF EXISTS `gen_property` CASCADE;
DROP TABLE IF EXISTS `gen_sort_property` CASCADE;
DROP TABLE IF EXISTS `gen_type_pair` CASCADE;
DROP TABLE IF EXISTS `gen_version_property` CASCADE;

CREATE TABLE `db_data_source` (
                                  `id` INTEGER NOT NULL AUTO_INCREMENT,
                                  `type` CHARACTER VARYING(500) NOT NULL,
                                  `name` CHARACTER VARYING(500) NOT NULL,
                                  `url` CHARACTER VARYING(500) NOT NULL,
                                  `username` CHARACTER VARYING(500) NOT NULL,
                                  `password` CHARACTER VARYING(500) NOT NULL,
                                  `remark` CHARACTER VARYING(500) NOT NULL,
                                  PRIMARY KEY (`id`)
);

COMMENT ON TABLE `db_data_source` IS '数据源';
COMMENT ON COLUMN `db_data_source`.`id` IS 'ID';
COMMENT ON COLUMN `db_data_source`.`type` IS '数据库类型';
COMMENT ON COLUMN `db_data_source`.`name` IS '名称';
COMMENT ON COLUMN `db_data_source`.`url` IS '链接';
COMMENT ON COLUMN `db_data_source`.`username` IS '用户名';
COMMENT ON COLUMN `db_data_source`.`password` IS '密码';
COMMENT ON COLUMN `db_data_source`.`remark` IS '备注';

CREATE TABLE `db_foreign_key` (
                                  `id` INTEGER NOT NULL AUTO_INCREMENT,
                                  `name` CHARACTER VARYING(500) NOT NULL,
                                  `source_table_id` INTEGER NOT NULL,
                                  `target_table_id` INTEGER NOT NULL,
                                  `type` CHARACTER VARYING(500) NOT NULL,
                                  `update_action` CHARACTER VARYING(500) NOT NULL,
                                  `delete_action` CHARACTER VARYING(500) NOT NULL,
                                  `remark` CHARACTER VARYING(500) NOT NULL,
                                  PRIMARY KEY (`id`)
);

COMMENT ON TABLE `db_foreign_key` IS '外键';
COMMENT ON COLUMN `db_foreign_key`.`id` IS 'ID';
COMMENT ON COLUMN `db_foreign_key`.`name` IS '关联名称';
COMMENT ON COLUMN `db_foreign_key`.`source_table_id` IS '主表';
COMMENT ON COLUMN `db_foreign_key`.`target_table_id` IS '从表';
COMMENT ON COLUMN `db_foreign_key`.`type` IS '关联类型';
COMMENT ON COLUMN `db_foreign_key`.`update_action` IS '更新行为';
COMMENT ON COLUMN `db_foreign_key`.`delete_action` IS '删除行为';
COMMENT ON COLUMN `db_foreign_key`.`remark` IS '备注';

CREATE TABLE `db_foreign_key_column_reference` (
                                                   `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                   `association_id` INTEGER NOT NULL,
                                                   `source_column_id` INTEGER NOT NULL,
                                                   `target_column_id` INTEGER NOT NULL,
                                                   `remark` CHARACTER VARYING(500) NOT NULL,
                                                   PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_db_foreign_key_column_reference` ON `db_foreign_key_column_reference` (`association_id`, `source_column_id`, `target_column_id`);

COMMENT ON TABLE `db_foreign_key_column_reference` IS '外键列引用';
COMMENT ON COLUMN `db_foreign_key_column_reference`.`id` IS 'ID';
COMMENT ON COLUMN `db_foreign_key_column_reference`.`association_id` IS '关联';
COMMENT ON COLUMN `db_foreign_key_column_reference`.`source_column_id` IS '主列';
COMMENT ON COLUMN `db_foreign_key_column_reference`.`target_column_id` IS '从列';
COMMENT ON COLUMN `db_foreign_key_column_reference`.`remark` IS '备注';

CREATE TABLE `db_primary_key` (
                                  `id` INTEGER NOT NULL AUTO_INCREMENT,
                                  `table_id` INTEGER NOT NULL,
                                  `name` CHARACTER VARYING(500) NOT NULL,
                                  `serial_name` VARCHAR(255) DEFAULT NULL,
                                  `auto_increment` BOOLEAN NOT NULL,
                                  `remark` CHARACTER VARYING(500) NOT NULL,
                                  PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_db_primary_key` ON `db_primary_key` (`table_id`);

COMMENT ON TABLE `db_primary_key` IS '主键';
COMMENT ON COLUMN `db_primary_key`.`id` IS 'ID';
COMMENT ON COLUMN `db_primary_key`.`table_id` IS '表';
COMMENT ON COLUMN `db_primary_key`.`name` IS '名称';
COMMENT ON COLUMN `db_primary_key`.`serial_name` IS '对应序列';
COMMENT ON COLUMN `db_primary_key`.`auto_increment` IS '是否自增';
COMMENT ON COLUMN `db_primary_key`.`remark` IS '备注';

CREATE TABLE `db_schema` (
                             `id` INTEGER NOT NULL AUTO_INCREMENT,
                             `data_source_id` INTEGER NOT NULL,
                             `name` CHARACTER VARYING(500) NOT NULL,
                             `remark` CHARACTER VARYING(500) NOT NULL,
                             PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_db_schema` ON `db_schema` (`data_source_id`, `name`);

COMMENT ON TABLE `db_schema` IS '数据架构';
COMMENT ON COLUMN `db_schema`.`id` IS 'ID';
COMMENT ON COLUMN `db_schema`.`data_source_id` IS '数据源';
COMMENT ON COLUMN `db_schema`.`name` IS '名称';
COMMENT ON COLUMN `db_schema`.`remark` IS '备注';

CREATE TABLE `db_table` (
                            `id` INTEGER NOT NULL AUTO_INCREMENT,
                            `schema_id` INTEGER NOT NULL,
                            `name` CHARACTER VARYING(500) NOT NULL,
                            `comment` CHARACTER VARYING(500) NOT NULL,
                            `type` CHARACTER VARYING(500) NOT NULL,
                            `remark` CHARACTER VARYING(500) NOT NULL,
                            PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_db_table` ON `db_table` (`schema_id`, `name`);

COMMENT ON TABLE `db_table` IS '表';
COMMENT ON COLUMN `db_table`.`id` IS 'ID';
COMMENT ON COLUMN `db_table`.`schema_id` IS '数据架构';
COMMENT ON COLUMN `db_table`.`name` IS '名称';
COMMENT ON COLUMN `db_table`.`comment` IS '注释';
COMMENT ON COLUMN `db_table`.`type` IS '类型';
COMMENT ON COLUMN `db_table`.`remark` IS '备注';

CREATE TABLE `db_table_column` (
                                   `id` INTEGER NOT NULL AUTO_INCREMENT,
                                   `table_id` INTEGER NOT NULL,
                                   `name` CHARACTER VARYING(500) NOT NULL,
                                   `order_key` INTEGER NOT NULL,
                                   `jdbc_type_code` INTEGER NOT NULL,
                                   `raw_type` CHARACTER VARYING(500) NOT NULL,
                                   `type_is_not_null` BOOLEAN NOT NULL,
                                   `data_size` BIGINT DEFAULT NULL,
                                   `numeric_precision` BIGINT DEFAULT NULL,
                                   `default_value` CHARACTER VARYING(500) DEFAULT NULL,
                                   `comment` CHARACTER VARYING(500) NOT NULL,
                                   `remark` CHARACTER VARYING(500) NOT NULL,
                                   PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_db_table_column` ON `db_table_column` (`table_id`, `name`);

COMMENT ON TABLE `db_table_column` IS '列';
COMMENT ON COLUMN `db_table_column`.`id` IS 'ID';
COMMENT ON COLUMN `db_table_column`.`table_id` IS '归属表';
COMMENT ON COLUMN `db_table_column`.`name` IS '名称';
COMMENT ON COLUMN `db_table_column`.`order_key` IS '排序键';
COMMENT ON COLUMN `db_table_column`.`jdbc_type_code` IS 'JdbcType 码值';
COMMENT ON COLUMN `db_table_column`.`raw_type` IS '字面类型';
COMMENT ON COLUMN `db_table_column`.`type_is_not_null` IS '类型是否非空';
COMMENT ON COLUMN `db_table_column`.`data_size` IS '长度';
COMMENT ON COLUMN `db_table_column`.`numeric_precision` IS '精度';
COMMENT ON COLUMN `db_table_column`.`default_value` IS '列默认值';
COMMENT ON COLUMN `db_table_column`.`comment` IS '注释';
COMMENT ON COLUMN `db_table_column`.`remark` IS '备注';

CREATE TABLE `db_table_index` (
                                  `id` INTEGER NOT NULL AUTO_INCREMENT,
                                  `table_id` INTEGER NOT NULL,
                                  `name` CHARACTER VARYING(500) NOT NULL,
                                  `unique_index` BOOLEAN NOT NULL,
                                  `remark` CHARACTER VARYING(500) NOT NULL,
                                  PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_db_table_index` ON `db_table_index` (`table_id`, `name`);

COMMENT ON TABLE `db_table_index` IS '表索引';
COMMENT ON COLUMN `db_table_index`.`id` IS 'ID';
COMMENT ON COLUMN `db_table_index`.`table_id` IS '归属表';
COMMENT ON COLUMN `db_table_index`.`name` IS '名称';
COMMENT ON COLUMN `db_table_index`.`unique_index` IS '是否是唯一索引';
COMMENT ON COLUMN `db_table_index`.`remark` IS '备注';

CREATE TABLE `gen_column_info` (
                                   `id` INTEGER NOT NULL AUTO_INCREMENT,
                                   `column_name` VARCHAR(255) NOT NULL,
                                   `jdbc_type_code` INTEGER NOT NULL,
                                   `raw_type` CHARACTER VARYING(500) NOT NULL,
                                   `type_is_not_null` BOOLEAN NOT NULL,
                                   `data_size` INTEGER DEFAULT NULL,
                                   `numeric_precision` INTEGER DEFAULT NULL,
                                   `column_default_exp` CHARACTER VARYING(500) DEFAULT NULL,
                                   PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_column_info` IS '列信息';
COMMENT ON COLUMN `gen_column_info`.`id` IS 'ID';
COMMENT ON COLUMN `gen_column_info`.`column_name` IS '列名';
COMMENT ON COLUMN `gen_column_info`.`jdbc_type_code` IS 'JdbcType 码值';
COMMENT ON COLUMN `gen_column_info`.`raw_type` IS '字面类型';
COMMENT ON COLUMN `gen_column_info`.`type_is_not_null` IS '类型是否非空';
COMMENT ON COLUMN `gen_column_info`.`data_size` IS '长度';
COMMENT ON COLUMN `gen_column_info`.`numeric_precision` IS '精度';
COMMENT ON COLUMN `gen_column_info`.`column_default_exp` IS '列默认表达式';

CREATE TABLE `gen_embeddable_type` (
                                       `id` INTEGER NOT NULL AUTO_INCREMENT,
                                       `group_id` INTEGER NOT NULL,
                                       `name` VARCHAR(255) NOT NULL,
                                       `sub_package_path` CHARACTER VARYING(500) NOT NULL,
                                       `comment` VARCHAR(255) NOT NULL,
                                       PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_embeddable_type` ON `gen_embeddable_type` (`group_id`, `name`);

COMMENT ON TABLE `gen_embeddable_type` IS '复合类型';
COMMENT ON COLUMN `gen_embeddable_type`.`id` IS 'ID';
COMMENT ON COLUMN `gen_embeddable_type`.`group_id` IS '分组';
COMMENT ON COLUMN `gen_embeddable_type`.`name` IS '名称';
COMMENT ON COLUMN `gen_embeddable_type`.`sub_package_path` IS '子包路径';
COMMENT ON COLUMN `gen_embeddable_type`.`comment` IS '注释';

CREATE TABLE `gen_embeddable_type_deep_property` (
                                                     `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                     `embeddable_id` INTEGER NOT NULL,
                                                     `name` CHARACTER VARYING(500) NOT NULL,
                                                     `comment` CHARACTER VARYING(500) NOT NULL,
                                                     `type_embeddable_id` INTEGER NOT NULL,
                                                     `override_column_names` VARCHAR(255) NOT NULL,
                                                     `extra_annotations` CHARACTER VARYING(500) DEFAULT NULL,
                                                     `extra_imports` VARCHAR(255) DEFAULT NULL,
                                                     `extra_validations` VARCHAR(255) DEFAULT NULL,
                                                     `remark` CHARACTER VARYING(500) NOT NULL,
                                                     `order_key` INTEGER NOT NULL,
                                                     PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_embeddable_type_deep_property` ON `gen_embeddable_type_deep_property` (`embeddable_id`, `name`);

COMMENT ON TABLE `gen_embeddable_type_deep_property` IS '深层复合属性';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`embeddable_id` IS '嵌入类型';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`name` IS '名称';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`comment` IS '注释';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`type_embeddable_id` IS '类型对应嵌入类型';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`override_column_names` IS '覆盖列名';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`extra_annotations` IS '其他注解';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`extra_imports` IS '其他导入';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`extra_validations` IS '其他验证器';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`remark` IS '备注';
COMMENT ON COLUMN `gen_embeddable_type_deep_property`.`order_key` IS '排序键';

CREATE TABLE `gen_embeddable_type_property` (
                                                `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                `embeddable_id` INTEGER NOT NULL,
                                                `name` CHARACTER VARYING(500) NOT NULL,
                                                `comment` CHARACTER VARYING(500) NOT NULL,
                                                `extra_annotations` CHARACTER VARYING(500) DEFAULT NULL,
                                                `extra_imports` VARCHAR(255) DEFAULT NULL,
                                                `extra_validations` VARCHAR(255) DEFAULT NULL,
                                                `remark` CHARACTER VARYING(500) NOT NULL,
                                                `order_key` INTEGER NOT NULL,
                                                `raw_type` CHARACTER VARYING(500) DEFAULT NULL,
                                                `type_enum_id` INTEGER DEFAULT NULL,
                                                `type_embeddable_id` INTEGER DEFAULT NULL,
                                                `column_info_id` INTEGER DEFAULT NULL,
                                                PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_embeddable_type_property` ON `gen_embeddable_type_property` (`embeddable_id`, `name`);

COMMENT ON TABLE `gen_embeddable_type_property` IS '复合类型属性';
COMMENT ON COLUMN `gen_embeddable_type_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_embeddable_type_property`.`embeddable_id` IS '嵌入类型';
COMMENT ON COLUMN `gen_embeddable_type_property`.`name` IS '名称';
COMMENT ON COLUMN `gen_embeddable_type_property`.`comment` IS '注释';
COMMENT ON COLUMN `gen_embeddable_type_property`.`extra_annotations` IS '其他注解';
COMMENT ON COLUMN `gen_embeddable_type_property`.`extra_imports` IS '其他导入';
COMMENT ON COLUMN `gen_embeddable_type_property`.`extra_validations` IS '其他验证器';
COMMENT ON COLUMN `gen_embeddable_type_property`.`remark` IS '备注';
COMMENT ON COLUMN `gen_embeddable_type_property`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_embeddable_type_property`.`raw_type` IS '字面类型';
COMMENT ON COLUMN `gen_embeddable_type_property`.`type_enum_id` IS '类型对应枚举';
COMMENT ON COLUMN `gen_embeddable_type_property`.`type_embeddable_id` IS '类型对应嵌入';
COMMENT ON COLUMN `gen_embeddable_type_property`.`column_info_id` IS '列信息';

CREATE TABLE `gen_entity` (
                              `id` INTEGER NOT NULL AUTO_INCREMENT,
                              `group_id` INTEGER NOT NULL,
                              `interface_name` CHARACTER VARYING(500) NOT NULL,
                              `mapped_super` BOOLEAN NOT NULL,
                              `table_name` CHARACTER VARYING(500) NOT NULL,
                              `plural_name` CHARACTER VARYING(500) NOT NULL,
                              `author` CHARACTER VARYING(500) NOT NULL,
                              `comment` CHARACTER VARYING(500) NOT NULL,
                              `sub_package_path` CHARACTER VARYING(500) NOT NULL,
                              `other_annotations` CHARACTER VARYING(500) NOT NULL,
                              `other_imports` VARCHAR(255) NOT NULL,
                              `remark` CHARACTER VARYING(500) NOT NULL,
                              `id_property_id` INTEGER DEFAULT NULL,
                              `logical_delete_property_id` INTEGER DEFAULT NULL,
                              `version_property_id` INTEGER DEFAULT NULL,
                              `x` DOUBLE PRECISION NOT NULL,
                              `y` DOUBLE PRECISION NOT NULL,
                              PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_entity` ON `gen_entity` (`group_id`, `interface_name`);

COMMENT ON TABLE `gen_entity` IS '实体';
COMMENT ON COLUMN `gen_entity`.`id` IS 'ID';
COMMENT ON COLUMN `gen_entity`.`group_id` IS '分组';
COMMENT ON COLUMN `gen_entity`.`interface_name` IS '接口名称';
COMMENT ON COLUMN `gen_entity`.`mapped_super` IS '抽象超类型';
COMMENT ON COLUMN `gen_entity`.`table_name` IS '表名称';
COMMENT ON COLUMN `gen_entity`.`plural_name` IS '复数名称';
COMMENT ON COLUMN `gen_entity`.`author` IS '作者';
COMMENT ON COLUMN `gen_entity`.`comment` IS '注释';
COMMENT ON COLUMN `gen_entity`.`sub_package_path` IS '子包路径';
COMMENT ON COLUMN `gen_entity`.`other_annotations` IS '其他注解';
COMMENT ON COLUMN `gen_entity`.`other_imports` IS '其他导入';
COMMENT ON COLUMN `gen_entity`.`remark` IS '备注';
COMMENT ON COLUMN `gen_entity`.`id_property_id` IS 'ID属性';
COMMENT ON COLUMN `gen_entity`.`logical_delete_property_id` IS '逻辑删除属性';
COMMENT ON COLUMN `gen_entity`.`version_property_id` IS '乐观锁属性';
COMMENT ON COLUMN `gen_entity`.`x` IS 'X坐标';
COMMENT ON COLUMN `gen_entity`.`y` IS 'Y坐标';

CREATE TABLE `gen_entity_index` (
                                    `id` INTEGER NOT NULL AUTO_INCREMENT,
                                    `entity_id` INTEGER NOT NULL,
                                    `name` CHARACTER VARYING(500) NOT NULL,
                                    `unique_index` BOOLEAN NOT NULL,
                                    `remark` CHARACTER VARYING(500) NOT NULL,
                                    `order_key` INTEGER NOT NULL,
                                    PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_entity_index` ON `gen_entity_index` (`entity_id`, `name`);

COMMENT ON TABLE `gen_entity_index` IS '实体索引';
COMMENT ON COLUMN `gen_entity_index`.`id` IS 'ID';
COMMENT ON COLUMN `gen_entity_index`.`entity_id` IS '归属表';
COMMENT ON COLUMN `gen_entity_index`.`name` IS '名称';
COMMENT ON COLUMN `gen_entity_index`.`unique_index` IS '是否是唯一索引';
COMMENT ON COLUMN `gen_entity_index`.`remark` IS '备注';
COMMENT ON COLUMN `gen_entity_index`.`order_key` IS '排序键';

CREATE TABLE `gen_entity_inherit` (
                                      `id` INTEGER NOT NULL AUTO_INCREMENT,
                                      `parent_id` INTEGER NOT NULL,
                                      `child_id` INTEGER NOT NULL,
                                      PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_entity_inherit` ON `gen_entity_inherit` (`parent_id`, `child_id`);

COMMENT ON TABLE `gen_entity_inherit` IS '实体继承';
COMMENT ON COLUMN `gen_entity_inherit`.`id` IS 'ID';
COMMENT ON COLUMN `gen_entity_inherit`.`parent_id` IS '父级';
COMMENT ON COLUMN `gen_entity_inherit`.`child_id` IS '子级';

CREATE TABLE `gen_entity_key_group` (
                                        `id` INTEGER NOT NULL AUTO_INCREMENT,
                                        `index_id` INTEGER NOT NULL,
                                        `name` VARCHAR(255) NOT NULL,
                                        PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_entity_key_group` ON `gen_entity_key_group` (`index_id`);

COMMENT ON TABLE `gen_entity_key_group` IS '实体业务键组';
COMMENT ON COLUMN `gen_entity_key_group`.`id` IS 'ID';
COMMENT ON COLUMN `gen_entity_key_group`.`index_id` IS '对应索引';
COMMENT ON COLUMN `gen_entity_key_group`.`name` IS '名称';

CREATE TABLE `gen_enum` (
                            `id` INTEGER NOT NULL AUTO_INCREMENT,
                            `group_id` INTEGER NOT NULL,
                            `name` CHARACTER VARYING(500) NOT NULL,
                            `comment` CHARACTER VARYING(500) NOT NULL,
                            `enum_type` CHARACTER VARYING(500) NOT NULL,
                            `sub_package_path` CHARACTER VARYING(500) NOT NULL,
                            `remark` CHARACTER VARYING(500) NOT NULL,
                            PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_enum` ON `gen_enum` (`group_id`, `name`);

COMMENT ON TABLE `gen_enum` IS '枚举';
COMMENT ON COLUMN `gen_enum`.`id` IS 'ID';
COMMENT ON COLUMN `gen_enum`.`group_id` IS '分组';
COMMENT ON COLUMN `gen_enum`.`name` IS '枚举名';
COMMENT ON COLUMN `gen_enum`.`comment` IS '枚举注释';
COMMENT ON COLUMN `gen_enum`.`enum_type` IS '枚举类型';
COMMENT ON COLUMN `gen_enum`.`sub_package_path` IS '子包路径';
COMMENT ON COLUMN `gen_enum`.`remark` IS '备注';

CREATE TABLE `gen_enum_item` (
                                 `id` INTEGER NOT NULL AUTO_INCREMENT,
                                 `enum_id` INTEGER NOT NULL,
                                 `name` CHARACTER VARYING(500) NOT NULL,
                                 `mapped_value` CHARACTER VARYING(500) NOT NULL,
                                 `comment` CHARACTER VARYING(500) NOT NULL,
                                 `default_item` BOOLEAN NOT NULL,
                                 `remark` CHARACTER VARYING(500) NOT NULL,
                                 `order_key` INTEGER NOT NULL,
                                 PRIMARY KEY (`id`)
);

CREATE INDEX `fk_enum_item_enum_INDEX_A` ON `gen_enum_item` (`enum_id`);
CREATE INDEX `idx_enum_item_enum` ON `gen_enum_item` (`enum_id`);
CREATE UNIQUE INDEX `key_of_gen_enum_item` ON `gen_enum_item` (`enum_id`, `name`);

COMMENT ON TABLE `gen_enum_item` IS '枚举元素';
COMMENT ON COLUMN `gen_enum_item`.`id` IS 'ID';
COMMENT ON COLUMN `gen_enum_item`.`enum_id` IS '枚举';
COMMENT ON COLUMN `gen_enum_item`.`name` IS '名称';
COMMENT ON COLUMN `gen_enum_item`.`mapped_value` IS '值';
COMMENT ON COLUMN `gen_enum_item`.`comment` IS '注释';
COMMENT ON COLUMN `gen_enum_item`.`default_item` IS '是否默认';
COMMENT ON COLUMN `gen_enum_item`.`remark` IS '备注';
COMMENT ON COLUMN `gen_enum_item`.`order_key` IS '排序键';

CREATE TABLE `gen_extra_property` (
                                      `id` INTEGER NOT NULL AUTO_INCREMENT,
                                      `property_id` INTEGER NOT NULL,
                                      `type` VARCHAR(255) NOT NULL,
                                      `type_entity_id` INTEGER DEFAULT NULL,
                                      `type_enum_id` INTEGER DEFAULT NULL,
                                      `body` CHARACTER VARYING(500) DEFAULT NULL,
                                      PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_extra_property` ON `gen_extra_property` (`property_id`);

COMMENT ON TABLE `gen_extra_property` IS '额外属性';
COMMENT ON COLUMN `gen_extra_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_extra_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_extra_property`.`type` IS '类型';
COMMENT ON COLUMN `gen_extra_property`.`type_entity_id` IS '类型对应实体';
COMMENT ON COLUMN `gen_extra_property`.`type_enum_id` IS '类型对应枚举';
COMMENT ON COLUMN `gen_extra_property`.`body` IS '属性方法体';

CREATE TABLE `gen_id_property` (
                                   `id` INTEGER NOT NULL AUTO_INCREMENT,
                                   `property_id` INTEGER NOT NULL,
                                   `generated_id_annotation` CHARACTER VARYING(500) DEFAULT NULL,
                                   PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_id_property` ON `gen_id_property` (`property_id`);

COMMENT ON TABLE `gen_id_property` IS 'ID属性';
COMMENT ON COLUMN `gen_id_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_id_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_id_property`.`generated_id_annotation` IS '生成 ID 注解';

CREATE TABLE `gen_join_table` (
                                  `id` INTEGER NOT NULL AUTO_INCREMENT,
                                  `name` VARCHAR(255) NOT NULL,
                                  `comment` VARCHAR(255) NOT NULL,
                                  `readonly` BOOLEAN NOT NULL,
                                  `prevent_deletion_by_source` BOOLEAN NOT NULL,
                                  `prevent_deletion_by_target` BOOLEAN NOT NULL,
                                  `deleted_when_endpoint_is_logically_deleted` BOOLEAN NOT NULL,
                                  `logical_delete_filter_id` INTEGER DEFAULT NULL,
                                  PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_join_table` IS 'Join Table';
COMMENT ON COLUMN `gen_join_table`.`id` IS 'ID';
COMMENT ON COLUMN `gen_join_table`.`name` IS '名称';
COMMENT ON COLUMN `gen_join_table`.`comment` IS '注释';
COMMENT ON COLUMN `gen_join_table`.`readonly` IS '只读';
COMMENT ON COLUMN `gen_join_table`.`prevent_deletion_by_source` IS '阻止从源删除';
COMMENT ON COLUMN `gen_join_table`.`prevent_deletion_by_target` IS '阻止从目标删除';
COMMENT ON COLUMN `gen_join_table`.`deleted_when_endpoint_is_logically_deleted` IS '在终端逻辑删除时物理删除';
COMMENT ON COLUMN `gen_join_table`.`logical_delete_filter_id` IS '逻辑删除过滤器';

CREATE TABLE `gen_join_table_column` (
                                         `id` INTEGER NOT NULL AUTO_INCREMENT,
                                         `join_table_id` INTEGER NOT NULL,
                                         `column_name` VARCHAR(255) NOT NULL,
                                         `reference_column_name` VARCHAR(255) NOT NULL,
                                         `comment` VARCHAR(255) NOT NULL,
                                         `fake` BOOLEAN NOT NULL,
                                         PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_join_table_column` ON `gen_join_table_column` (`join_table_id`, `column_name`);

COMMENT ON TABLE `gen_join_table_column` IS 'Join Table Join Column';
COMMENT ON COLUMN `gen_join_table_column`.`id` IS 'ID';
COMMENT ON COLUMN `gen_join_table_column`.`join_table_id` IS 'Join Table';
COMMENT ON COLUMN `gen_join_table_column`.`column_name` IS '本地列名称';
COMMENT ON COLUMN `gen_join_table_column`.`reference_column_name` IS '引用列名称';
COMMENT ON COLUMN `gen_join_table_column`.`comment` IS '注释';
COMMENT ON COLUMN `gen_join_table_column`.`fake` IS '是否伪外键';

CREATE TABLE `gen_join_table_filter` (
                                         `id` INTEGER NOT NULL AUTO_INCREMENT,
                                         `join_table_id` INTEGER NOT NULL,
                                         `column_name` VARCHAR(255) NOT NULL,
                                         `type` VARCHAR(255) NOT NULL,
                                         `values` VARCHAR(255) NOT NULL,
                                         PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_join_table_filter` IS 'Join Table 过滤器';
COMMENT ON COLUMN `gen_join_table_filter`.`id` IS 'ID';
COMMENT ON COLUMN `gen_join_table_filter`.`join_table_id` IS 'Join Table';
COMMENT ON COLUMN `gen_join_table_filter`.`column_name` IS '列名称';
COMMENT ON COLUMN `gen_join_table_filter`.`type` IS '类型';
COMMENT ON COLUMN `gen_join_table_filter`.`values` IS '值';

CREATE TABLE `gen_join_table_logical_delete_filter` (
                                                        `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                        `column_name` VARCHAR(255) NOT NULL,
                                                        `type` VARCHAR(255) NOT NULL,
                                                        `values` VARCHAR(255) NOT NULL,
                                                        `logical_deleted_annotation` CHARACTER VARYING(500) NOT NULL,
                                                        `nullable` BOOLEAN NOT NULL,
                                                        PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_join_table_logical_delete_filter` IS 'Join Table 逻辑删除过滤器';
COMMENT ON COLUMN `gen_join_table_logical_delete_filter`.`id` IS 'ID';
COMMENT ON COLUMN `gen_join_table_logical_delete_filter`.`column_name` IS '列名称';
COMMENT ON COLUMN `gen_join_table_logical_delete_filter`.`type` IS '类型';
COMMENT ON COLUMN `gen_join_table_logical_delete_filter`.`values` IS '值';
COMMENT ON COLUMN `gen_join_table_logical_delete_filter`.`logical_deleted_annotation` IS '逻辑删除注解';
COMMENT ON COLUMN `gen_join_table_logical_delete_filter`.`nullable` IS '可空';

CREATE TABLE `gen_logical_delete_property` (
                                               `id` INTEGER NOT NULL AUTO_INCREMENT,
                                               `property_id` INTEGER NOT NULL,
                                               `logical_deleted_annotation` CHARACTER VARYING(500) NOT NULL,
                                               PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_logical_delete_property` ON `gen_logical_delete_property` (`property_id`);

COMMENT ON TABLE `gen_logical_delete_property` IS '逻辑删除属性';
COMMENT ON COLUMN `gen_logical_delete_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_logical_delete_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_logical_delete_property`.`logical_deleted_annotation` IS '逻辑删除注解';

CREATE TABLE `gen_many_to_many_association` (
                                                `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                `source_property_id` INTEGER NOT NULL,
                                                `mapped_property_id` INTEGER NOT NULL,
                                                `join_table_id` INTEGER NOT NULL,
                                                PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_many_to_many_association` IS '多对多关联';
COMMENT ON COLUMN `gen_many_to_many_association`.`id` IS 'ID';
COMMENT ON COLUMN `gen_many_to_many_association`.`source_property_id` IS '源属性';
COMMENT ON COLUMN `gen_many_to_many_association`.`mapped_property_id` IS '目标属性';
COMMENT ON COLUMN `gen_many_to_many_association`.`join_table_id` IS 'Join Table';

CREATE TABLE `gen_many_to_many_mapped_property` (
                                                    `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                    `property_id` INTEGER NOT NULL,
                                                    `mapped_by_id` INTEGER NOT NULL,
                                                    `id_view_name` VARCHAR(255) NOT NULL,
                                                    `type_entity_id` INTEGER NOT NULL,
                                                    PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_many_to_many_mapped_property` ON `gen_many_to_many_mapped_property` (`property_id`);
CREATE UNIQUE INDEX `key_of_gen_many_to_many_mapped_property_mapped_by` ON `gen_many_to_many_mapped_property` (`mapped_by_id`);

COMMENT ON TABLE `gen_many_to_many_mapped_property` IS '多对多映射属性';
COMMENT ON COLUMN `gen_many_to_many_mapped_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_many_to_many_mapped_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_many_to_many_mapped_property`.`mapped_by_id` IS '对应源属性';
COMMENT ON COLUMN `gen_many_to_many_mapped_property`.`id_view_name` IS 'ID视图名';
COMMENT ON COLUMN `gen_many_to_many_mapped_property`.`type_entity_id` IS '类型对应实体';

CREATE TABLE `gen_many_to_many_source_property` (
                                                    `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                    `property_id` INTEGER NOT NULL,
                                                    `id_view_name` VARCHAR(255) NOT NULL,
                                                    `type_entity_id` INTEGER NOT NULL,
                                                    PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_many_to_many_source_property` ON `gen_many_to_many_source_property` (`property_id`);

COMMENT ON TABLE `gen_many_to_many_source_property` IS '多对多源属性';
COMMENT ON COLUMN `gen_many_to_many_source_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_many_to_many_source_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_many_to_many_source_property`.`id_view_name` IS 'ID视图名';
COMMENT ON COLUMN `gen_many_to_many_source_property`.`type_entity_id` IS '类型对应实体';

CREATE TABLE `gen_many_to_one_association` (
                                               `id` INTEGER NOT NULL AUTO_INCREMENT,
                                               `source_property_id` INTEGER NOT NULL,
                                               `mapped_property_id` INTEGER NOT NULL,
                                               `join_table_id` INTEGER DEFAULT NULL,
                                               PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_many_to_one_association` IS '多对一关联';
COMMENT ON COLUMN `gen_many_to_one_association`.`id` IS 'ID';
COMMENT ON COLUMN `gen_many_to_one_association`.`source_property_id` IS '源属性';
COMMENT ON COLUMN `gen_many_to_one_association`.`mapped_property_id` IS '目标属性';
COMMENT ON COLUMN `gen_many_to_one_association`.`join_table_id` IS 'Join Table';

CREATE TABLE `gen_many_to_one_mapped_property` (
                                                   `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                   `property_id` INTEGER NOT NULL,
                                                   `mapped_by_id` INTEGER NOT NULL,
                                                   `id_view_name` VARCHAR(255) NOT NULL,
                                                   `type_entity_id` INTEGER NOT NULL,
                                                   PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_many_to_one_mapped_property` ON `gen_many_to_one_mapped_property` (`property_id`);
CREATE UNIQUE INDEX `key_of_gen_many_to_one_mapped_property_mapped_by` ON `gen_many_to_one_mapped_property` (`mapped_by_id`);

COMMENT ON TABLE `gen_many_to_one_mapped_property` IS '多对一映射属性（对多）';
COMMENT ON COLUMN `gen_many_to_one_mapped_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_many_to_one_mapped_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_many_to_one_mapped_property`.`mapped_by_id` IS '对应源属性';
COMMENT ON COLUMN `gen_many_to_one_mapped_property`.`id_view_name` IS 'ID视图名';
COMMENT ON COLUMN `gen_many_to_one_mapped_property`.`type_entity_id` IS '类型对应实体';

CREATE TABLE `gen_many_to_one_source_property` (
                                                   `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                   `property_id` INTEGER NOT NULL,
                                                   `id_view_name` VARCHAR(255) NOT NULL,
                                                   `type_is_not_null` BOOLEAN NOT NULL,
                                                   `type_entity_id` INTEGER NOT NULL,
                                                   PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_many_to_one_source_property` ON `gen_many_to_one_source_property` (`property_id`);

COMMENT ON TABLE `gen_many_to_one_source_property` IS '多对一源属性（对单）';
COMMENT ON COLUMN `gen_many_to_one_source_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_many_to_one_source_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_many_to_one_source_property`.`id_view_name` IS 'ID视图名';
COMMENT ON COLUMN `gen_many_to_one_source_property`.`type_is_not_null` IS '类型是否非空';
COMMENT ON COLUMN `gen_many_to_one_source_property`.`type_entity_id` IS '类型对应实体';

CREATE TABLE `gen_model` (
                             `id` INTEGER NOT NULL AUTO_INCREMENT,
                             `name` CHARACTER VARYING(500) NOT NULL,
                             `author` CHARACTER VARYING(500) NOT NULL,
                             `language` CHARACTER VARYING(500) NOT NULL,
                             `database_type` CHARACTER VARYING(500) NOT NULL,
                             `database_naming_strategy` CHARACTER VARYING(500) NOT NULL,
                             `transition` VARCHAR(255) NOT NULL,
                             `created_time` TIMESTAMP(0) NOT NULL,
                             `modified_time` TIMESTAMP(0) NOT NULL,
                             PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_model` IS '模型';
COMMENT ON COLUMN `gen_model`.`id` IS 'ID';
COMMENT ON COLUMN `gen_model`.`name` IS '名称';
COMMENT ON COLUMN `gen_model`.`author` IS '作者';
COMMENT ON COLUMN `gen_model`.`language` IS '语言';
COMMENT ON COLUMN `gen_model`.`database_type` IS '数据库类型';
COMMENT ON COLUMN `gen_model`.`database_naming_strategy` IS '数据库命名策略';
COMMENT ON COLUMN `gen_model`.`transition` IS '图偏移';
COMMENT ON COLUMN `gen_model`.`created_time` IS '创建时间';
COMMENT ON COLUMN `gen_model`.`modified_time` IS '修改时间';

CREATE TABLE `gen_model_config` (
                                    `id` INTEGER NOT NULL AUTO_INCREMENT,
                                    `model_id` INTEGER NOT NULL,
                                    `base_package_path` CHARACTER VARYING(500) NOT NULL,
                                    `base_table_path` CHARACTER VARYING(500) NOT NULL,
                                    `default_dissociate_action` CHARACTER VARYING(500) NOT NULL,
                                    `default_use_real_fk` BOOLEAN NOT NULL,
                                    `default_id_type` INTEGER NOT NULL,
                                    `default_generated_id_annotation` CHARACTER VARYING(500) NOT NULL,
                                    `default_logical_deleted_annotation` CHARACTER VARYING(500) NOT NULL,
                                    `generate_table_annotation` BOOLEAN NOT NULL,
                                    `generate_column_annotation` BOOLEAN NOT NULL,
                                    `table_to_entity_removed_name_prefixes` CHARACTER VARYING(500) NOT NULL,
                                    `table_to_entity_removed_name_suffixes` CHARACTER VARYING(500) NOT NULL,
                                    `table_to_entity_removed_comment_prefixes` CHARACTER VARYING(500) NOT NULL,
                                    `table_to_entity_removed_comment_suffixes` CHARACTER VARYING(500) NOT NULL,
                                    `column_to_property_removed_name_prefixes` CHARACTER VARYING(500) NOT NULL,
                                    `column_to_property_removed_name_suffixes` CHARACTER VARYING(500) NOT NULL,
                                    `column_to_property_removed_comment_prefixes` CHARACTER VARYING(500) NOT NULL,
                                    `column_to_property_removed_comment_suffixes` CHARACTER VARYING(500) NOT NULL,
                                    `remark` CHARACTER VARYING(500) NOT NULL,
                                    PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_model_config` ON `gen_model_config` (`model_id`);

COMMENT ON TABLE `gen_model_config` IS '模型配置';
COMMENT ON COLUMN `gen_model_config`.`id` IS 'ID';
COMMENT ON COLUMN `gen_model_config`.`model_id` IS '模型';
COMMENT ON COLUMN `gen_model_config`.`base_package_path` IS '基础包路径';
COMMENT ON COLUMN `gen_model_config`.`base_table_path` IS '基础表路径';
COMMENT ON COLUMN `gen_model_config`.`default_dissociate_action` IS '默认脱钩行为';
COMMENT ON COLUMN `gen_model_config`.`default_use_real_fk` IS '默认使用真实外键';
COMMENT ON COLUMN `gen_model_config`.`default_id_type` IS '默认ID类型';
COMMENT ON COLUMN `gen_model_config`.`default_generated_id_annotation` IS '默认ID生成注解';
COMMENT ON COLUMN `gen_model_config`.`default_logical_deleted_annotation` IS '默认逻辑删除注解';
COMMENT ON COLUMN `gen_model_config`.`generate_table_annotation` IS '生成 Table 注解';
COMMENT ON COLUMN `gen_model_config`.`generate_column_annotation` IS '生成 Column 注解';
COMMENT ON COLUMN `gen_model_config`.`table_to_entity_removed_name_prefixes` IS '表转换实体时移除的表名前缀';
COMMENT ON COLUMN `gen_model_config`.`table_to_entity_removed_name_suffixes` IS '表转换实体时移除的表名后缀';
COMMENT ON COLUMN `gen_model_config`.`table_to_entity_removed_comment_prefixes` IS '表转换实体时移除的表注释前缀';
COMMENT ON COLUMN `gen_model_config`.`table_to_entity_removed_comment_suffixes` IS '表转换实体时移除的表注释后缀';
COMMENT ON COLUMN `gen_model_config`.`column_to_property_removed_name_prefixes` IS '列转换属性时移除的列名前缀';
COMMENT ON COLUMN `gen_model_config`.`column_to_property_removed_name_suffixes` IS '转换属性时移除的列名后缀';
COMMENT ON COLUMN `gen_model_config`.`column_to_property_removed_comment_prefixes` IS '转换属性时移除的列注释前缀';
COMMENT ON COLUMN `gen_model_config`.`column_to_property_removed_comment_suffixes` IS '转换属性时移除的列注释后缀';
COMMENT ON COLUMN `gen_model_config`.`remark` IS '备注';

CREATE TABLE `gen_model_group` (
                                   `id` INTEGER NOT NULL AUTO_INCREMENT,
                                   `model_id` INTEGER NOT NULL,
                                   `name` CHARACTER VARYING(500) NOT NULL,
                                   `comment` CHARACTER VARYING(500) NOT NULL,
                                   `package_path` CHARACTER VARYING(500) NOT NULL,
                                   `table_path` CHARACTER VARYING(500) NOT NULL,
                                   `color` CHARACTER VARYING(1000000000) NOT NULL,
                                   `order_key` INTEGER NOT NULL,
                                   PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_model_group` ON `gen_model_group` (`model_id`, `name`);

COMMENT ON TABLE `gen_model_group` IS '模型分组';
COMMENT ON COLUMN `gen_model_group`.`id` IS 'ID';
COMMENT ON COLUMN `gen_model_group`.`model_id` IS '模型';
COMMENT ON COLUMN `gen_model_group`.`name` IS '名称';
COMMENT ON COLUMN `gen_model_group`.`comment` IS '注释';
COMMENT ON COLUMN `gen_model_group`.`package_path` IS '包路径';
COMMENT ON COLUMN `gen_model_group`.`table_path` IS '表路径';
COMMENT ON COLUMN `gen_model_group`.`color` IS '颜色';
COMMENT ON COLUMN `gen_model_group`.`order_key` IS '排序键';

CREATE TABLE `gen_one_to_one_association` (
                                              `id` INTEGER NOT NULL AUTO_INCREMENT,
                                              `source_property_id` INTEGER NOT NULL,
                                              `mapped_property_id` INTEGER NOT NULL,
                                              `join_table_id` INTEGER DEFAULT NULL,
                                              PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_one_to_one_association` IS '一对一关联';
COMMENT ON COLUMN `gen_one_to_one_association`.`id` IS 'ID';
COMMENT ON COLUMN `gen_one_to_one_association`.`source_property_id` IS '源属性';
COMMENT ON COLUMN `gen_one_to_one_association`.`mapped_property_id` IS '目标属性';
COMMENT ON COLUMN `gen_one_to_one_association`.`join_table_id` IS 'Join Table';

CREATE TABLE `gen_one_to_one_mapped_property` (
                                                  `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                  `property_id` INTEGER NOT NULL,
                                                  `mapped_by_id` INTEGER NOT NULL,
                                                  `id_view_name` VARCHAR(255) NOT NULL,
                                                  `type_entity_id` INTEGER NOT NULL,
                                                  PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_one_to_one_mapped_property` ON `gen_one_to_one_mapped_property` (`property_id`);
CREATE UNIQUE INDEX `key_of_gen_one_to_one_mapped_property_mapped_by` ON `gen_one_to_one_mapped_property` (`mapped_by_id`);

COMMENT ON TABLE `gen_one_to_one_mapped_property` IS '一对一映射属性';
COMMENT ON COLUMN `gen_one_to_one_mapped_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_one_to_one_mapped_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_one_to_one_mapped_property`.`mapped_by_id` IS '对应源属性';
COMMENT ON COLUMN `gen_one_to_one_mapped_property`.`id_view_name` IS 'ID视图名';
COMMENT ON COLUMN `gen_one_to_one_mapped_property`.`type_entity_id` IS '类型对应实体';

CREATE TABLE `gen_one_to_one_source_property` (
                                                  `id` INTEGER NOT NULL AUTO_INCREMENT,
                                                  `property_id` INTEGER NOT NULL,
                                                  `id_view_name` VARCHAR(255) NOT NULL,
                                                  `type_is_not_null` BOOLEAN NOT NULL,
                                                  `type_entity_id` INTEGER NOT NULL,
                                                  PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_one_to_one_source_property` ON `gen_one_to_one_source_property` (`property_id`);

COMMENT ON TABLE `gen_one_to_one_source_property` IS '一对一源属性';
COMMENT ON COLUMN `gen_one_to_one_source_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_one_to_one_source_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_one_to_one_source_property`.`id_view_name` IS 'ID视图名';
COMMENT ON COLUMN `gen_one_to_one_source_property`.`type_is_not_null` IS '类型是否非空';
COMMENT ON COLUMN `gen_one_to_one_source_property`.`type_entity_id` IS '类型对应实体';

CREATE TABLE `gen_property` (
                                `id` INTEGER NOT NULL AUTO_INCREMENT,
                                `entity_id` INTEGER NOT NULL,
                                `name` CHARACTER VARYING(500) NOT NULL,
                                `comment` CHARACTER VARYING(500) NOT NULL,
                                `extra_annotations` CHARACTER VARYING(500) DEFAULT NULL,
                                `extra_imports` VARCHAR(255) DEFAULT NULL,
                                `extra_validations` VARCHAR(255) DEFAULT NULL,
                                `remark` CHARACTER VARYING(500) NOT NULL,
                                `order_key` INTEGER NOT NULL,
                                `raw_type` CHARACTER VARYING(500) DEFAULT NULL,
                                `type_enum_id` INTEGER DEFAULT NULL,
                                `type_embeddable_id` INTEGER DEFAULT NULL,
                                `column_info_id` INTEGER DEFAULT NULL,
                                PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_property` ON `gen_property` (`entity_id`, `name`);

COMMENT ON TABLE `gen_property` IS '属性';
COMMENT ON COLUMN `gen_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_property`.`entity_id` IS '实体';
COMMENT ON COLUMN `gen_property`.`name` IS '名称';
COMMENT ON COLUMN `gen_property`.`comment` IS '注释';
COMMENT ON COLUMN `gen_property`.`extra_annotations` IS '其他注解';
COMMENT ON COLUMN `gen_property`.`extra_imports` IS '其他导入';
COMMENT ON COLUMN `gen_property`.`extra_validations` IS '其他验证器';
COMMENT ON COLUMN `gen_property`.`remark` IS '备注';
COMMENT ON COLUMN `gen_property`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_property`.`raw_type` IS '字面类型';
COMMENT ON COLUMN `gen_property`.`type_enum_id` IS '类型对应枚举';
COMMENT ON COLUMN `gen_property`.`type_embeddable_id` IS '类型对应嵌入';
COMMENT ON COLUMN `gen_property`.`column_info_id` IS '列信息';

CREATE TABLE `gen_sort_property` (
                                     `id` INTEGER NOT NULL AUTO_INCREMENT,
                                     `property_id` INTEGER NOT NULL,
                                     `entity_id` INTEGER NOT NULL,
                                     `order_direction` VARCHAR(255) NOT NULL,
                                     PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_sort_property` ON `gen_sort_property` (`property_id`);

COMMENT ON TABLE `gen_sort_property` IS '排序属性';
COMMENT ON COLUMN `gen_sort_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_sort_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_sort_property`.`entity_id` IS '实体';
COMMENT ON COLUMN `gen_sort_property`.`order_direction` IS '排序方向';

CREATE TABLE `gen_type_pair` (
                                 `id` BIGINT NOT NULL AUTO_INCREMENT,
                                 `database_type` CHARACTER VARYING(500) DEFAULT NULL,
                                 `jdbc_type_code` INTEGER NOT NULL,
                                 `raw_type` CHARACTER VARYING(500) NOT NULL,
                                 `default_data_size` INTEGER DEFAULT NULL,
                                 `default_numeric_precision` INTEGER DEFAULT NULL,
                                 `language` CHARACTER VARYING(500) DEFAULT NULL,
                                 `property_type` CHARACTER VARYING(500) NOT NULL,
                                 `order_key` INTEGER NOT NULL,
                                 `remark` CHARACTER VARYING(500) NOT NULL,
                                 PRIMARY KEY (`id`)
);

COMMENT ON TABLE `gen_type_pair` IS '类型对';
COMMENT ON COLUMN `gen_type_pair`.`id` IS 'ID';
COMMENT ON COLUMN `gen_type_pair`.`database_type` IS '数据源类型';
COMMENT ON COLUMN `gen_type_pair`.`jdbc_type_code` IS 'JdbcType 码值';
COMMENT ON COLUMN `gen_type_pair`.`raw_type` IS '字面类型';
COMMENT ON COLUMN `gen_type_pair`.`default_data_size` IS '默认长度';
COMMENT ON COLUMN `gen_type_pair`.`default_numeric_precision` IS '默认精度';
COMMENT ON COLUMN `gen_type_pair`.`language` IS '语言';
COMMENT ON COLUMN `gen_type_pair`.`property_type` IS '属性类型';
COMMENT ON COLUMN `gen_type_pair`.`order_key` IS '排序键';
COMMENT ON COLUMN `gen_type_pair`.`remark` IS '备注';

CREATE TABLE `gen_version_property` (
                                        `id` INTEGER NOT NULL AUTO_INCREMENT,
                                        `property_id` INTEGER NOT NULL,
                                        `column_name` VARCHAR(255) NOT NULL,
                                        PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `key_of_gen_version_property` ON `gen_version_property` (`property_id`);

COMMENT ON TABLE `gen_version_property` IS '乐观锁属性';
COMMENT ON COLUMN `gen_version_property`.`id` IS 'ID';
COMMENT ON COLUMN `gen_version_property`.`property_id` IS '属性';
COMMENT ON COLUMN `gen_version_property`.`column_name` IS '列名称';

ALTER TABLE `db_schema`
    ADD CONSTRAINT `fk_db_schema_data_source_id`
        FOREIGN KEY (`data_source_id`)
            REFERENCES `db_data_source` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `db_foreign_key`
    ADD CONSTRAINT `fk_db_foreign_key_source_table_id`
        FOREIGN KEY (`source_table_id`)
            REFERENCES `db_table` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `db_foreign_key`
    ADD CONSTRAINT `fk_db_foreign_key_target_table_id`
        FOREIGN KEY (`target_table_id`)
            REFERENCES `db_table` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `db_foreign_key_column_reference`
    ADD CONSTRAINT `fk_db_foreign_key_column_reference_association_id`
        FOREIGN KEY (`association_id`)
            REFERENCES `db_foreign_key` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `db_foreign_key_column_reference`
    ADD CONSTRAINT `fk_db_foreign_key_column_reference_source_column_id`
        FOREIGN KEY (`source_column_id`)
            REFERENCES `db_table_column` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `db_foreign_key_column_reference`
    ADD CONSTRAINT `fk_db_foreign_key_column_reference_target_column_id`
        FOREIGN KEY (`target_column_id`)
            REFERENCES `db_table_column` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `db_primary_key`
    ADD CONSTRAINT `fk_db_primary_key_table_id`
        FOREIGN KEY (`table_id`)
            REFERENCES `db_table` (`id`);

DROP TABLE IF EXISTS `db_table_column_db_primary_key_mapping` CASCADE;

CREATE TABLE `db_table_column_db_primary_key_mapping` (
                                                          `db_table_column_id` INTEGER NOT NULL,
                                                          `db_primary_key_id` INTEGER NOT NULL
);

ALTER TABLE `db_table_column_db_primary_key_mapping` ADD CONSTRAINT `pk_db_table_column_db_primary_key_mapping` PRIMARY KEY (`db_table_column_id`,`db_primary_key_id`);

ALTER TABLE `db_table_column_db_primary_key_mapping`
    ADD CONSTRAINT `db_table_column_db_primary_key_mapping_S`
        FOREIGN KEY (`db_table_column_id`)
            REFERENCES `db_table_column` (`id`);

ALTER TABLE `db_table_column_db_primary_key_mapping`
    ADD CONSTRAINT `db_table_column_db_primary_key_mapping_T`
        FOREIGN KEY (`db_primary_key_id`)
            REFERENCES `db_primary_key` (`id`);

COMMENT ON TABLE `db_table_column_db_primary_key_mapping` IS '列与主键的映射关系表';

ALTER TABLE `db_table`
    ADD CONSTRAINT `fk_db_table_schema_id`
        FOREIGN KEY (`schema_id`)
            REFERENCES `db_schema` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `db_table_column`
    ADD CONSTRAINT `fk_db_table_column_table_id`
        FOREIGN KEY (`table_id`)
            REFERENCES `db_table` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `db_table_index`
    ADD CONSTRAINT `fk_db_table_index_table_id`
        FOREIGN KEY (`table_id`)
            REFERENCES `db_table` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

DROP TABLE IF EXISTS `db_table_index_db_table_column_mapping` CASCADE;

CREATE TABLE `db_table_index_db_table_column_mapping` (
                                                          `db_table_index_id` INTEGER NOT NULL,
                                                          `db_table_column_id` INTEGER NOT NULL
);

ALTER TABLE `db_table_index_db_table_column_mapping` ADD CONSTRAINT `pk_db_table_index_db_table_column_mapping` PRIMARY KEY (`db_table_index_id`,`db_table_column_id`);

ALTER TABLE `db_table_index_db_table_column_mapping`
    ADD CONSTRAINT `db_table_index_db_table_column_mapping_S`
        FOREIGN KEY (`db_table_index_id`)
            REFERENCES `db_table_index` (`id`);

ALTER TABLE `db_table_index_db_table_column_mapping`
    ADD CONSTRAINT `db_table_index_db_table_column_mapping_T`
        FOREIGN KEY (`db_table_column_id`)
            REFERENCES `db_table_column` (`id`);

COMMENT ON TABLE `db_table_index_db_table_column_mapping` IS '表索引与列的映射关系表';

ALTER TABLE `gen_embeddable_type_property`
    ADD CONSTRAINT `fk_gen_embeddable_type_property_column_info_id`
        FOREIGN KEY (`column_info_id`)
            REFERENCES `gen_column_info` (`id`);

ALTER TABLE `gen_property`
    ADD CONSTRAINT `fk_gen_property_column_info_id`
        FOREIGN KEY (`column_info_id`)
            REFERENCES `gen_column_info` (`id`);

ALTER TABLE `gen_embeddable_type`
    ADD CONSTRAINT `fk_gen_embeddable_type_group_id`
        FOREIGN KEY (`group_id`)
            REFERENCES `gen_model_group` (`id`);

ALTER TABLE `gen_embeddable_type_deep_property`
    ADD CONSTRAINT `fk_gen_embeddable_type_deep_property_embeddable_id`
        FOREIGN KEY (`embeddable_id`)
            REFERENCES `gen_embeddable_type` (`id`);

ALTER TABLE `gen_embeddable_type_deep_property`
    ADD CONSTRAINT `fk_gen_embeddable_type_deep_property_type_embeddable_id`
        FOREIGN KEY (`type_embeddable_id`)
            REFERENCES `gen_embeddable_type` (`id`);

ALTER TABLE `gen_embeddable_type_property`
    ADD CONSTRAINT `fk_gen_embeddable_type_property_embeddable_type_id`
        FOREIGN KEY (`embeddable_id`)
            REFERENCES `gen_embeddable_type` (`id`);

ALTER TABLE `gen_embeddable_type_property`
    ADD CONSTRAINT `fk_gen_embeddable_type_property_type_embeddable_id`
        FOREIGN KEY (`type_embeddable_id`)
            REFERENCES `gen_embeddable_type` (`id`);

ALTER TABLE `gen_property`
    ADD CONSTRAINT `fk_gen_property_type_embeddable_id`
        FOREIGN KEY (`type_embeddable_id`)
            REFERENCES `gen_embeddable_type` (`id`);

ALTER TABLE `gen_embeddable_type_property`
    ADD CONSTRAINT `fk_gen_embeddable_type_property_type_enum_id`
        FOREIGN KEY (`type_enum_id`)
            REFERENCES `gen_enum` (`id`);

ALTER TABLE `gen_entity`
    ADD CONSTRAINT `fk_gen_entity_group_id`
        FOREIGN KEY (`group_id`)
            REFERENCES `gen_model_group` (`id`);

ALTER TABLE `gen_entity`
    ADD CONSTRAINT `fk_gen_entity_id_property_id`
        FOREIGN KEY (`id_property_id`)
            REFERENCES `gen_id_property` (`id`);

ALTER TABLE `gen_entity`
    ADD CONSTRAINT `fk_gen_entity_logical_delete_property_id`
        FOREIGN KEY (`logical_delete_property_id`)
            REFERENCES `gen_logical_delete_property` (`id`);

ALTER TABLE `gen_entity`
    ADD CONSTRAINT `fk_gen_entity_version_property_id`
        FOREIGN KEY (`version_property_id`)
            REFERENCES `gen_version_property` (`id`);

ALTER TABLE `gen_entity_index`
    ADD CONSTRAINT `fk_gen_entity_index_entity_id`
        FOREIGN KEY (`entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_entity_inherit`
    ADD CONSTRAINT `fk_gen_entity_inherit_child_id`
        FOREIGN KEY (`child_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_entity_inherit`
    ADD CONSTRAINT `fk_gen_entity_inherit_parent_id`
        FOREIGN KEY (`parent_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_extra_property`
    ADD CONSTRAINT `fk_gen_extra_property_type_entity_id`
        FOREIGN KEY (`type_entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_property`
    ADD CONSTRAINT `fk_gen_property_entity_id`
        FOREIGN KEY (`entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_sort_property`
    ADD CONSTRAINT `fk_gen_sort_property_entity_id`
        FOREIGN KEY (`entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_many_to_many_mapped_property`
    ADD CONSTRAINT `fk_gen_many_to_many_mapped_property_type_entity_id`
        FOREIGN KEY (`type_entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_many_to_many_source_property`
    ADD CONSTRAINT `fk_gen_many_to_many_source_property_type_entity_id`
        FOREIGN KEY (`type_entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_many_to_one_mapped_property`
    ADD CONSTRAINT `fk_gen_many_to_one_mapped_property_type_entity_id`
        FOREIGN KEY (`type_entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_many_to_one_source_property`
    ADD CONSTRAINT `fk_gen_many_to_one_source_property_type_entity_id`
        FOREIGN KEY (`type_entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_one_to_one_mapped_property`
    ADD CONSTRAINT `fk_gen_one_to_one_mapped_property_type_entity_id`
        FOREIGN KEY (`type_entity_id`)
            REFERENCES `gen_entity` (`id`);

ALTER TABLE `gen_one_to_one_source_property`
    ADD CONSTRAINT `fk_gen_one_to_one_source_property_type_entity_id`
        FOREIGN KEY (`type_entity_id`)
            REFERENCES `gen_entity` (`id`);

DROP TABLE IF EXISTS `gen_entity_index_gen_property_mapping` CASCADE;

CREATE TABLE `gen_entity_index_gen_property_mapping` (
                                                         `gen_entity_index_id` INTEGER NOT NULL,
                                                         `gen_property_id` INTEGER NOT NULL
);

ALTER TABLE `gen_entity_index_gen_property_mapping` ADD CONSTRAINT `pk_gen_entity_index_gen_property_mapping` PRIMARY KEY (`gen_entity_index_id`,`gen_property_id`);

ALTER TABLE `gen_entity_index_gen_property_mapping`
    ADD CONSTRAINT `gen_entity_index_gen_property_mapping_S`
        FOREIGN KEY (`gen_entity_index_id`)
            REFERENCES `gen_entity_index` (`id`);

ALTER TABLE `gen_entity_index_gen_property_mapping`
    ADD CONSTRAINT `gen_entity_index_gen_property_mapping_T`
        FOREIGN KEY (`gen_property_id`)
            REFERENCES `gen_property` (`id`);

COMMENT ON TABLE `gen_entity_index_gen_property_mapping` IS '实体索引与属性的映射关系表';

ALTER TABLE `gen_entity_key_group`
    ADD CONSTRAINT `fk_gen_entity_key_group_index_id`
        FOREIGN KEY (`index_id`)
            REFERENCES `gen_entity_index` (`id`);

ALTER TABLE `gen_enum`
    ADD CONSTRAINT `fk_gen_enum_group_id`
        FOREIGN KEY (`group_id`)
            REFERENCES `gen_model_group` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `gen_enum_item`
    ADD CONSTRAINT `fk_enum_item_enum`
        FOREIGN KEY (`enum_id`)
            REFERENCES `gen_enum` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `gen_extra_property`
    ADD CONSTRAINT `fk_gen_extra_property_type_enum_id`
        FOREIGN KEY (`type_enum_id`)
            REFERENCES `gen_enum` (`id`);

ALTER TABLE `gen_property`
    ADD CONSTRAINT `fk_gen_property_type_enum_id`
        FOREIGN KEY (`type_enum_id`)
            REFERENCES `gen_enum` (`id`);

ALTER TABLE `gen_extra_property`
    ADD CONSTRAINT `fk_gen_extra_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_id_property`
    ADD CONSTRAINT `fk_gen_id_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_join_table`
    ADD CONSTRAINT `fk_gen_join_table_logical_delete_filter_id`
        FOREIGN KEY (`logical_delete_filter_id`)
            REFERENCES `gen_join_table_logical_delete_filter` (`id`);

ALTER TABLE `gen_join_table_column`
    ADD CONSTRAINT `fk_gen_join_table_column_join_table_id`
        FOREIGN KEY (`join_table_id`)
            REFERENCES `gen_join_table` (`id`);

ALTER TABLE `gen_join_table_filter`
    ADD CONSTRAINT `fk_gen_join_table_filter_join_table_id`
        FOREIGN KEY (`join_table_id`)
            REFERENCES `gen_join_table` (`id`);

ALTER TABLE `gen_many_to_many_association`
    ADD CONSTRAINT `fk_gen_many_to_many_association_join_table_id`
        FOREIGN KEY (`join_table_id`)
            REFERENCES `gen_join_table` (`id`);

ALTER TABLE `gen_many_to_one_association`
    ADD CONSTRAINT `fk_gen_many_to_one_association_join_table_id`
        FOREIGN KEY (`join_table_id`)
            REFERENCES `gen_join_table` (`id`);

ALTER TABLE `gen_one_to_one_association`
    ADD CONSTRAINT `fk_gen_one_to_one_association_join_table_id`
        FOREIGN KEY (`join_table_id`)
            REFERENCES `gen_join_table` (`id`);

ALTER TABLE `gen_logical_delete_property`
    ADD CONSTRAINT `fk_gen_logical_delete_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_many_to_many_association`
    ADD CONSTRAINT `fk_gen_many_to_many_association_mapped_property_id`
        FOREIGN KEY (`mapped_property_id`)
            REFERENCES `gen_many_to_many_mapped_property` (`id`);

ALTER TABLE `gen_many_to_many_association`
    ADD CONSTRAINT `fk_gen_many_to_many_association_source_property_id`
        FOREIGN KEY (`source_property_id`)
            REFERENCES `gen_many_to_many_source_property` (`id`);

ALTER TABLE `gen_many_to_many_mapped_property`
    ADD CONSTRAINT `fk_gen_many_to_many_mapped_property_mapped_by_id`
        FOREIGN KEY (`mapped_by_id`)
            REFERENCES `gen_many_to_many_source_property` (`id`);

ALTER TABLE `gen_many_to_many_mapped_property`
    ADD CONSTRAINT `fk_gen_many_to_many_mapped_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_many_to_many_source_property`
    ADD CONSTRAINT `fk_gen_many_to_many_source_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_many_to_one_association`
    ADD CONSTRAINT `fk_gen_many_to_one_association_mapped_property_id`
        FOREIGN KEY (`mapped_property_id`)
            REFERENCES `gen_many_to_one_mapped_property` (`id`);

ALTER TABLE `gen_many_to_one_association`
    ADD CONSTRAINT `fk_gen_many_to_one_association_source_property_id`
        FOREIGN KEY (`source_property_id`)
            REFERENCES `gen_many_to_one_source_property` (`id`);

ALTER TABLE `gen_many_to_one_mapped_property`
    ADD CONSTRAINT `fk_gen_many_to_one_mapped_property_mapped_by_id`
        FOREIGN KEY (`mapped_by_id`)
            REFERENCES `gen_many_to_one_source_property` (`id`);

ALTER TABLE `gen_many_to_one_mapped_property`
    ADD CONSTRAINT `fk_gen_many_to_one_mapped_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_many_to_one_source_property`
    ADD CONSTRAINT `fk_gen_many_to_one_source_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_model_config`
    ADD CONSTRAINT `fk_gen_model_config_model_id`
        FOREIGN KEY (`model_id`)
            REFERENCES `gen_model` (`id`);

ALTER TABLE `gen_model_group`
    ADD CONSTRAINT `fk_gen_model_group_model_id`
        FOREIGN KEY (`model_id`)
            REFERENCES `gen_model` (`id`)
            ON UPDATE restrict
            ON DELETE restrict;

ALTER TABLE `gen_one_to_one_association`
    ADD CONSTRAINT `fk_gen_one_to_one_association_mapped_property_id`
        FOREIGN KEY (`mapped_property_id`)
            REFERENCES `gen_one_to_one_mapped_property` (`id`);

ALTER TABLE `gen_one_to_one_association`
    ADD CONSTRAINT `fk_gen_one_to_one_association_source_property_id`
        FOREIGN KEY (`source_property_id`)
            REFERENCES `gen_one_to_one_source_property` (`id`);

ALTER TABLE `gen_one_to_one_mapped_property`
    ADD CONSTRAINT `fk_gen_one_to_one_mapped_property_mapped_by_id`
        FOREIGN KEY (`mapped_by_id`)
            REFERENCES `gen_one_to_one_source_property` (`id`);

ALTER TABLE `gen_one_to_one_mapped_property`
    ADD CONSTRAINT `fk_gen_one_to_one_mapped_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_one_to_one_source_property`
    ADD CONSTRAINT `fk_gen_one_to_one_source_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_sort_property`
    ADD CONSTRAINT `fk_gen_sort_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);

ALTER TABLE `gen_version_property`
    ADD CONSTRAINT `fk_gen_version_property_property_id`
        FOREIGN KEY (`property_id`)
            REFERENCES `gen_property` (`id`);


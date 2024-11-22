ALTER TABLE `jimmer_code_gen`.`gen_column`
    ADD COLUMN `key_group` varchar(500) NULL DEFAULT NULL COMMENT '业务键组' AFTER `business_key`;


ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `key_group` varchar(500) NULL DEFAULT NULL COMMENT '业务键组' AFTER `key_property`;


ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `long_association` boolean NOT NULL DEFAULT FALSE COMMENT '是否为长关联' AFTER `association_type`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `long_association` boolean NOT NULL COMMENT '是否为长关联' AFTER `association_type`;



ALTER TABLE `jimmer_code_gen`.`gen_entity`
    ADD COLUMN `can_add` boolean NOT NULL DEFAULT TRUE COMMENT '是否可以创建' AFTER `author`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    MODIFY COLUMN `can_add` boolean NOT NULL COMMENT '是否可以创建' AFTER `author`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    ADD COLUMN `can_edit` boolean NOT NULL DEFAULT TRUE COMMENT '是否可以修改' AFTER `can_add`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    MODIFY COLUMN `can_edit` boolean NOT NULL COMMENT '是否可以修改' AFTER `can_add`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    ADD COLUMN `can_delete` boolean NOT NULL DEFAULT TRUE COMMENT '是否可以删除' AFTER `can_edit`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    MODIFY COLUMN `can_delete` boolean NOT NULL COMMENT '是否可以删除' AFTER `can_edit`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    ADD COLUMN `can_query` boolean NOT NULL DEFAULT TRUE COMMENT '是否可以查询' AFTER `can_delete`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    MODIFY COLUMN `can_query` boolean NOT NULL COMMENT '是否可以查询' AFTER `can_delete`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    ADD COLUMN `has_page` boolean NOT NULL DEFAULT TRUE COMMENT '是否具有页面' AFTER `can_query`;
ALTER TABLE `jimmer_code_gen`.`gen_entity`
    MODIFY COLUMN `has_page` boolean NOT NULL COMMENT '是否具有页面' AFTER `can_query`;



ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_list_view` boolean NOT NULL DEFAULT TRUE COMMENT '是否在列表视图DTO中' AFTER `order_key`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_list_view` boolean NOT NULL COMMENT '是否在列表视图DTO中' AFTER `order_key`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_detail_view` boolean NOT NULL DEFAULT TRUE COMMENT '是否在详情视图DTO中' AFTER `in_list_view`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_detail_view` boolean NOT NULL COMMENT '是否在详情视图DTO中' AFTER `in_list_view`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_insert_input` boolean NOT NULL DEFAULT TRUE COMMENT '是否在新增入参DTO中' AFTER `in_detail_view`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_insert_input` boolean NOT NULL COMMENT '是否在新增入参DTO中' AFTER `in_detail_view`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_update_input` boolean NOT NULL DEFAULT TRUE COMMENT '是否在修改入参DTO中' AFTER `in_insert_input`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_update_input` boolean NOT NULL COMMENT '是否在修改入参DTO中' AFTER `in_insert_input`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_specification` boolean NOT NULL DEFAULT TRUE COMMENT '是否在查询规格DTO中' AFTER `in_update_input`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_specification` boolean NOT NULL COMMENT '是否在查询规格DTO中' AFTER `in_update_input`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_long_association_view` boolean NOT NULL DEFAULT TRUE COMMENT '是否在长关联视图DTO中' AFTER `in_specification`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_long_association_view` boolean NOT NULL COMMENT '是否在长关联视图DTO中' AFTER `in_specification`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_long_association_input` boolean NOT NULL DEFAULT TRUE COMMENT '是否在长关联入参DTO中' AFTER `in_long_association_view`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_long_association_input` boolean NOT NULL COMMENT '是否在长关联入参DTO中' AFTER `in_long_association_view`;



ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_option_view` boolean NOT NULL DEFAULT FALSE COMMENT '是否在选项视图DTO中' AFTER `in_specification`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_option_view` boolean NOT NULL COMMENT '是否在选项视图DTO中' AFTER `in_specification`;

ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `in_short_association_view` boolean NOT NULL DEFAULT FALSE COMMENT '是否在短关联视图DTO中' AFTER `in_option_view`;
ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `in_short_association_view` boolean NOT NULL COMMENT '是否在短关联视图DTO中' AFTER `in_option_view`;


ALTER TABLE `jimmer_code_gen`.`gen_enum_item`
    ADD COLUMN `default_item` boolean NOT NULL DEFAULT FALSE COMMENT '是否是默认值' AFTER `comment`;
ALTER TABLE `jimmer_code_gen`.`gen_enum_item`
    MODIFY COLUMN `default_item` boolean NOT NULL COMMENT '是否是默认值' AFTER `comment`;
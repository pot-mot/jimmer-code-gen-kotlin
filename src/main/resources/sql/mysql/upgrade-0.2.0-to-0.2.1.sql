ALTER TABLE `gen_model`
    ADD COLUMN `view_type` varchar(500) NOT NULL DEFAULT 'VUE3_ELEMENT_PLUS' COMMENT '视图类型';

ALTER TABLE `gen_model`
    MODIFY COLUMN `view_type` varchar(500) NOT NULL COMMENT '视图类型';

ALTER TABLE `gen_model`
    ADD COLUMN `default_id_type` int NOT NULL DEFAULT 4 COMMENT '默认 Id 类型';

ALTER TABLE `gen_model`
    MODIFY COLUMN `default_id_type` int NOT NULL COMMENT '默认 Id 类型';

ALTER TABLE `gen_model`
    ADD COLUMN `generated_id_annotation` varchar(500) NOT NULL DEFAULT '{"imports": ["org.babyfish.jimmer.sql.GeneratedValue", "org.babyfish.jimmer.sql.GenerationType"], "annotations": ["@GeneratedValue(strategy = GenerationType.IDENTITY)"]}' COMMENT 'Id 注解';

ALTER TABLE `gen_model`
    MODIFY COLUMN `generated_id_annotation` varchar(500) NOT NULL COMMENT 'Id 注解';

ALTER TABLE `gen_model`
    DROP COLUMN `logical_deleted_annotation`;

ALTER TABLE `gen_model`
    ADD COLUMN `logical_deleted_annotation` varchar(500) NOT NULL DEFAULT '{"imports": ["org.babyfish.jimmer.sql.LogicalDeleted"], "annotations": ["@LogicalDeleted"]}' COMMENT '逻辑删除注解';

ALTER TABLE `gen_model`
    MODIFY COLUMN `logical_deleted_annotation` varchar(500) NOT NULL COMMENT '逻辑删除注解';

ALTER TABLE `gen_model`
    ADD COLUMN `date_time_format_in_view` boolean NOT NULL DEFAULT TRUE COMMENT '在前端视图中进行日期格式化';

ALTER TABLE `gen_model`
    MODIFY COLUMN `date_time_format_in_view` boolean NOT NULL COMMENT '在前端视图中进行日期格式化';



ALTER TABLE `gen_entity`
    ADD COLUMN `page_can_query` boolean NOT NULL DEFAULT FALSE COMMENT '管理端页面中可查询';

UPDATE `gen_entity` SET `page_can_query` = TRUE WHERE `can_query`;

ALTER TABLE `gen_entity`
    MODIFY COLUMN `page_can_query` boolean NOT NULL COMMENT '管理端页面中可查询';

ALTER TABLE `gen_entity`
    ADD COLUMN `page_can_add` boolean NOT NULL DEFAULT FALSE COMMENT '管理端页面中可新增';

UPDATE `gen_entity` SET `page_can_add` = TRUE WHERE `can_add`;

ALTER TABLE `gen_entity`
    MODIFY COLUMN `page_can_add` boolean NOT NULL COMMENT '管理端页面中可新增';

ALTER TABLE `gen_entity`
    ADD COLUMN `page_can_edit` boolean NOT NULL DEFAULT FALSE COMMENT '管理端页面中可编辑';

UPDATE `gen_entity` SET `page_can_edit` = TRUE WHERE `can_edit`;

ALTER TABLE `gen_entity`
    MODIFY COLUMN `page_can_edit` boolean NOT NULL COMMENT '管理端页面中可编辑';

ALTER TABLE `gen_entity`
    ADD COLUMN `page_can_view_detail` boolean NOT NULL DEFAULT FALSE COMMENT '管理端页面中可查看详情';

ALTER TABLE `gen_entity`
    MODIFY COLUMN `page_can_view_detail` boolean NOT NULL COMMENT '管理端页面中可查看详情';

ALTER TABLE `gen_entity`
    ADD COLUMN `page_can_delete` boolean NOT NULL DEFAULT FALSE COMMENT '管理端页面中可删除';

UPDATE `gen_entity` SET `page_can_delete` = TRUE WHERE `can_delete`;

ALTER TABLE `gen_entity`
    MODIFY COLUMN `page_can_delete` boolean NOT NULL COMMENT '管理端页面中可删除';

ALTER TABLE `gen_entity`
    ADD COLUMN `query_by_page` boolean NOT NULL DEFAULT TRUE COMMENT '应用分页查询';

ALTER TABLE `gen_entity`
    MODIFY COLUMN `query_by_page` boolean NOT NULL COMMENT '应用分页查询';



ALTER TABLE `gen_property`
    ADD COLUMN `generated_id` boolean NOT NULL DEFAULT FALSE COMMENT '是否是生成式 ID';

UPDATE `gen_property` SET `generated_id` = TRUE WHERE `id_generation_annotation` is not null;

ALTER TABLE `gen_property`
    MODIFY COLUMN `generated_id` boolean NOT NULL COMMENT '是否是生成式 ID';

ALTER TABLE `gen_property`
    DROP COLUMN `id_generation_annotation`;

ALTER TABLE `gen_property`
    ADD COLUMN `generated_id_annotation` varchar(500) NULL DEFAULT NULL COMMENT '生成 ID 注解';

ALTER TABLE `gen_property`
    ADD COLUMN `logical_deleted_annotation` varchar(500) NULL DEFAULT NULL COMMENT '逻辑删除注解';

ALTER TABLE `gen_property`
    ADD COLUMN `special_form_type` varchar(500) NULL DEFAULT NULL COMMENT '特殊表单类型';


UPDATE `gen_property` SET `other_annotation` = REPLACE(`other_annotation`, '\"importLines\":', '\"imports\":');

UPDATE `gen_property` SET `body` = REPLACE(`body`, '\"importLines\":', '\"imports\":');
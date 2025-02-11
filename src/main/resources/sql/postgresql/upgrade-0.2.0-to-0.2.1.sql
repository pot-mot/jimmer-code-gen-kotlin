ALTER TABLE gen_model
    ADD COLUMN view_type text NOT NULL DEFAULT 'VUE3_ELEMENT_PLUS';
COMMENT ON COLUMN gen_model.view_type IS '视图类型';
ALTER TABLE gen_model
    ALTER COLUMN view_type DROP DEFAULT;

ALTER TABLE gen_model
    ADD COLUMN default_id_type int NOT NULL DEFAULT 4;
COMMENT ON COLUMN gen_model.default_id_type IS '默认 Id 类型';
ALTER TABLE gen_model
    ALTER COLUMN default_id_type DROP DEFAULT;

ALTER TABLE gen_model
    ADD COLUMN generated_id_annotation jsonb NOT NULL DEFAULT '{"imports": ["org.babyfish.jimmer.sql.GeneratedValue", "org.babyfish.jimmer.sql.GenerationType"], "annotations": ["@GeneratedValue(strategy = GenerationType.IDENTITY)"]}';
COMMENT ON COLUMN gen_model.generated_id_annotation IS 'Id 注解';
ALTER TABLE gen_model
    ALTER COLUMN generated_id_annotation DROP DEFAULT;

ALTER TABLE gen_model
    DROP COLUMN logical_deleted_annotation;

ALTER TABLE gen_model
    ADD COLUMN logical_deleted_annotation jsonb NOT NULL DEFAULT '{"imports": ["org.babyfish.jimmer.sql.LogicalDeleted"], "annotations": ["@LogicalDeleted"]}';
COMMENT ON COLUMN gen_model.logical_deleted_annotation IS '逻辑删除注解';
ALTER TABLE gen_model
    ALTER COLUMN logical_deleted_annotation DROP DEFAULT;

ALTER TABLE gen_model
    ADD COLUMN date_time_format_in_view boolean NOT NULL DEFAULT TRUE;
COMMENT ON COLUMN gen_model.date_time_format_in_view IS '在前端视图中进行日期格式化';
ALTER TABLE gen_model
    ALTER COLUMN date_time_format_in_view DROP DEFAULT;


ALTER TABLE gen_entity
    ADD COLUMN page_can_query boolean NOT NULL DEFAULT FALSE;
COMMENT ON COLUMN gen_entity.page_can_query IS '管理端页面中可查询';
UPDATE gen_entity SET page_can_query = TRUE WHERE can_query;
ALTER TABLE gen_entity
    ALTER COLUMN page_can_query DROP DEFAULT;

ALTER TABLE gen_entity
    ADD COLUMN page_can_add boolean NOT NULL DEFAULT FALSE;
COMMENT ON COLUMN gen_entity.page_can_add IS '管理端页面中可新增';
UPDATE gen_entity SET page_can_add = TRUE WHERE can_add;
ALTER TABLE gen_entity
    ALTER COLUMN page_can_add DROP DEFAULT;

ALTER TABLE gen_entity
    ADD COLUMN page_can_edit boolean NOT NULL DEFAULT FALSE;
COMMENT ON COLUMN gen_entity.page_can_edit IS '管理端页面中可编辑';
UPDATE gen_entity SET page_can_edit = TRUE WHERE can_edit;
ALTER TABLE gen_entity
    ALTER COLUMN page_can_edit DROP DEFAULT;

ALTER TABLE gen_entity
    ADD COLUMN page_can_view_detail boolean NOT NULL DEFAULT FALSE;
COMMENT ON COLUMN gen_entity.page_can_view_detail IS '管理端页面中可查看详情';
ALTER TABLE gen_entity
    ALTER COLUMN page_can_view_detail DROP DEFAULT;

ALTER TABLE gen_entity
    ADD COLUMN page_can_delete boolean NOT NULL DEFAULT FALSE;
COMMENT ON COLUMN gen_entity.page_can_delete IS '管理端页面中可删除';
UPDATE gen_entity SET page_can_delete = TRUE WHERE can_delete;
ALTER TABLE gen_entity
    ALTER COLUMN page_can_delete DROP DEFAULT;

ALTER TABLE gen_entity
    ADD COLUMN query_by_page boolean NOT NULL DEFAULT TRUE;
COMMENT ON COLUMN gen_entity.query_by_page IS '应用分页查询';
ALTER TABLE gen_entity
    ALTER COLUMN query_by_page DROP DEFAULT;


ALTER TABLE gen_property
    ADD COLUMN generated_id boolean NOT NULL DEFAULT FALSE;
COMMENT ON COLUMN gen_property.generated_id IS '是否是生成式 ID';
UPDATE gen_property SET generated_id = TRUE WHERE id_generation_annotation IS NOT NULL;
ALTER TABLE gen_property
    ALTER COLUMN generated_id DROP DEFAULT;

ALTER TABLE gen_property
    DROP COLUMN id_generation_annotation;

ALTER TABLE gen_property
    ADD COLUMN generated_id_annotation jsonb NULL DEFAULT NULL;
COMMENT ON COLUMN gen_property.generated_id_annotation IS '生成 ID 注解';

ALTER TABLE gen_property
    ADD COLUMN logical_deleted_annotation jsonb NULL DEFAULT NULL;
COMMENT ON COLUMN gen_property.logical_deleted_annotation IS '逻辑删除注解';

ALTER TABLE gen_property
    ADD COLUMN special_form_type text NULL DEFAULT NULL;
COMMENT ON COLUMN gen_property.special_form_type IS '特殊表单类型';

UPDATE gen_property SET other_annotation = REPLACE(other_annotation, '"importLines":', '"imports":');

UPDATE gen_property SET body = REPLACE(body, '"importLines":', '"imports":');

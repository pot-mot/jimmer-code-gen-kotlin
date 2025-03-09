ALTER TABLE gen_enum
    ADD COLUMN sub_group_id BIGINT DEFAULT NULL COMMENT '子组';

ALTER TABLE gen_table
    ADD COLUMN sub_group_id BIGINT DEFAULT NULL COMMENT '子组';

CREATE TABLE `gen_model_sub_group`
(
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `model_id`         BIGINT       NOT NULL COMMENT '模型',
    `name`             varchar(500) NOT NULL COMMENT '名称',
    `comment`          varchar(500) NOT NULL COMMENT '注释',
    `sub_package_path` varchar(500) NOT NULL COMMENT '子包路径',
    `style`            longtext     NOT NULL COMMENT '样式',
    `created_time`     datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modified_time`    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    CHARACTER SET = utf8mb4
    COMMENT = '生成模型子组'
    ROW_FORMAT = Dynamic;

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

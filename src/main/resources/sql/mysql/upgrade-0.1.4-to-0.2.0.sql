ALTER TABLE `jimmer_code_gen`.`gen_column`
    ADD COLUMN `key_group` varchar(500) NULL DEFAULT NULL COMMENT '业务键组' AFTER `business_key`;


ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `key_group` varchar(500) NULL DEFAULT NULL COMMENT '业务键组' AFTER `key_property`;


ALTER TABLE `jimmer_code_gen`.`gen_property`
    ADD COLUMN `long_association` boolean NOT NULL DEFAULT FALSE COMMENT '是否为长关联' AFTER `association_type`;

ALTER TABLE `jimmer_code_gen`.`gen_property`
    MODIFY COLUMN `long_association` boolean NOT NULL COMMENT '是否为长关联' AFTER `association_type`;
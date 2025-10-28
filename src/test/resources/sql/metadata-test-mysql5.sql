CREATE TABLE test_user
(
    id INT COMMENT '非自增主键',

    -- 单列主键
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='测试用户表';

CREATE TABLE test_group_categories
(
    group_id    INT NOT NULL,
    category_id INT NOT NULL,

    -- 多列主键
    primary key (group_id, category_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组分类表';

CREATE TABLE test_table
(
    id               INT AUTO_INCREMENT COMMENT '自增主键',
    user_id          INT NOT NULL COMMENT '用户ID',
    group_id         INT NOT NULL COMMENT '组ID',
    category_id      INT NOT NULL COMMENT '分类ID',
    nullable_user_id INT COMMENT '可空用户ID',
    name             VARCHAR(50) COMMENT '名称',
    email            VARCHAR(100) COMMENT '邮箱',
    status           TINYINT   DEFAULT 1 COMMENT '状态',
    type_int         INT COMMENT '整数类型',
    type_bigint      BIGINT COMMENT '大整数类型',
    type_smallint    SMALLINT COMMENT '小整数类型',
    type_decimal     DECIMAL(10, 2) COMMENT '精确小数类型',
    type_float       FLOAT COMMENT '单精度浮点数',
    type_double      DOUBLE COMMENT '双精度浮点数',
    type_boolean     BOOLEAN COMMENT '布尔类型',
    type_date        DATE COMMENT '日期类型',
    type_datetime    DATETIME COMMENT '日期时间类型',
    type_timestamp   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '时间戳类型',
    type_text        TEXT COMMENT '文本类型',
    type_longtext    LONGTEXT COMMENT '长文本类型',
    type_enum        ENUM ('value1', 'value2', 'value3') COMMENT '枚举类型',
    type_set         SET ('option1', 'option2', 'option3') COMMENT '集合类型',
    type_json        JSON COMMENT 'JSON数据类型',
    type_blob        BLOB COMMENT '二进制大对象类型',
    type_bit         BIT(8) COMMENT '位类型',

    PRIMARY KEY (id),

    -- 单列外键，且修改删除时级联
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES test_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    -- 单列外键，且修改删除时置空
    CONSTRAINT fk_nullable_user FOREIGN KEY (nullable_user_id) REFERENCES test_user (id) ON DELETE SET NULL ON UPDATE SET NULL,

    -- 多列外键，且修改删除时严格
    CONSTRAINT fk_group_category FOREIGN KEY (group_id, category_id) REFERENCES test_group_categories (group_id, category_id) ON DELETE RESTRICT ON UPDATE RESTRICT,

    -- 唯一索引
    UNIQUE INDEX uk_email (email),

    -- 非唯一索引
    INDEX idx_name_status (name, status)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='测试表';

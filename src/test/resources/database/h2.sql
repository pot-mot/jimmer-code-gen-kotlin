-- 创建 test_user 表
CREATE TABLE test_user
(
    id INT PRIMARY KEY
);

COMMENT ON TABLE test_user IS '测试用户表';
COMMENT ON COLUMN test_user.id IS '非自增主键';

-- 创建 test_group_categories 表
CREATE TABLE test_group_categories
(
    group_id    INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (group_id, category_id)
);

COMMENT ON TABLE test_group_categories IS '组分类表';

-- 创建 test_table 表
CREATE TABLE test_table
(
    id                        INT AUTO_INCREMENT,
    user_id                   INT NOT NULL,
    group_id                  INT NOT NULL,
    category_id               INT NOT NULL,
    nullable_user_id          INT,
    name                      VARCHAR(50),
    email                     VARCHAR(100),
    status                    SMALLINT  DEFAULT 1,
    type_int                  INT,
    type_bigint               BIGINT,
    type_smallint             SMALLINT,
    type_decimal              NUMERIC(10, 2),
    type_float                REAL,
    type_double               DOUBLE PRECISION,
    type_boolean              BOOLEAN,
    type_date                 DATE,
    type_datetime             TIMESTAMP,
    type_timestamp            TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type_timestamp_tz         TIMESTAMP WITH TIME ZONE,
    type_text                 TEXT,
    type_check_enum           VARCHAR(20),
    type_json                 JSON,
    type_blob                 BYTEA,
    type_bit                  BINARY(8),

    PRIMARY KEY (id),

    -- 外键约束
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES test_user (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

    CONSTRAINT fk_nullable_user
        FOREIGN KEY (nullable_user_id)
            REFERENCES test_user (id)
            ON DELETE SET NULL
            ON UPDATE SET NULL,

    CONSTRAINT fk_group_category
        FOREIGN KEY (group_id, category_id)
            REFERENCES test_group_categories (group_id, category_id)
            ON DELETE RESTRICT
            ON UPDATE RESTRICT,

    -- 唯一约束
    CONSTRAINT uk_email UNIQUE (email),

    -- 枚举约束
    CHECK (type_check_enum IN ('value1', 'value2', 'value3'))
);

-- 创建索引
CREATE INDEX idx_name_status ON test_table (name, status);

COMMENT ON TABLE test_table IS '测试表';
COMMENT ON COLUMN test_table.id IS '自增主键';
COMMENT ON COLUMN test_table.user_id IS '用户ID';
COMMENT ON COLUMN test_table.group_id IS '组ID';
COMMENT ON COLUMN test_table.category_id IS '分类ID';
COMMENT ON COLUMN test_table.nullable_user_id IS '可空用户ID';
COMMENT ON COLUMN test_table.name IS '名称';
COMMENT ON COLUMN test_table.email IS '邮箱';
COMMENT ON COLUMN test_table.status IS '状态';
COMMENT ON COLUMN test_table.type_int IS '整数类型';
COMMENT ON COLUMN test_table.type_bigint IS '大整数类型';
COMMENT ON COLUMN test_table.type_smallint IS '小整数类型';
COMMENT ON COLUMN test_table.type_decimal IS '精确小数类型';
COMMENT ON COLUMN test_table.type_float IS '单精度浮点数';
COMMENT ON COLUMN test_table.type_double IS '双精度浮点数';
COMMENT ON COLUMN test_table.type_boolean IS '布尔类型';
COMMENT ON COLUMN test_table.type_date IS '日期类型';
COMMENT ON COLUMN test_table.type_datetime IS '日期时间类型';
COMMENT ON COLUMN test_table.type_timestamp IS '时间戳类型';
COMMENT ON COLUMN test_table.type_timestamp_tz IS '时区时间戳类型';
COMMENT ON COLUMN test_table.type_text IS '文本类型';
COMMENT ON COLUMN test_table.type_check_enum IS '枚举类型检查';
COMMENT ON COLUMN test_table.type_json IS 'JSON数据类型';
COMMENT ON COLUMN test_table.type_blob IS '二进制大对象类型';
COMMENT ON COLUMN test_table.type_bit IS '位类型';

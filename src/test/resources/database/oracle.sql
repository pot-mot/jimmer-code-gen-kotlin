-- 创建用户test，密码为test，允许远程连接
CREATE USER test IDENTIFIED BY test;

-- 授予test用户连接数据库的权限
GRANT CONNECT TO test;

-- 授予test用户创建会话的权限
GRANT CREATE SESSION TO test;

-- 授予test用户基本的数据库操作权限
GRANT RESOURCE TO test;

-- 设置当前模式为test用户
ALTER SESSION SET CURRENT_SCHEMA = test;

-- 创建 test_user 表
CREATE TABLE test_user
(
    id NUMBER(10) NOT NULL,
    PRIMARY KEY (id)
);

COMMENT ON TABLE test_user IS '测试用户表';
COMMENT ON COLUMN test_user.id IS '非自增主键';

-- 创建 test_group_categories 表
CREATE TABLE test_group_categories
(
    group_id    NUMBER(10) NOT NULL,
    category_id NUMBER(10) NOT NULL,
    PRIMARY KEY (group_id, category_id)
);

COMMENT ON TABLE test_group_categories IS '组分类表';

-- 创建 test_table 表
CREATE SEQUENCE seq_test_table_id
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE test_table
(
    id                     NUMBER(19) DEFAULT seq_test_table_id.nextval,
    user_id                NUMBER(10) NOT NULL,
    group_id               NUMBER(10) NOT NULL,
    category_id            NUMBER(10) NOT NULL,
    nullable_user_id       NUMBER(10),
    name                   VARCHAR2(50),
    email                  VARCHAR2(100),
    status                 NUMBER(3)  DEFAULT 1,
    type_int               INT,
    type_integer           INTEGER,
    type_smallint          SMALLINT,
    type_int_number        NUMBER(10),
    type_smallint_number   NUMBER(5),
    type_bigint            NUMBER(19),
    type_numeric           NUMERIC,
    type_numeric_10        NUMERIC(10),
    type_numeric_10_2      NUMERIC(10, 2),
    type_decimal           DECIMAL,
    type_decimal_10        DECIMAL(10),
    type_decimal_10_2      DECIMAL(10, 2),
    type_dec               DEC,
    type_real              REAL,
    type_float             FLOAT,
    type_binary_float      BINARY_FLOAT,
    type_double_precision  DOUBLE PRECISION,
    type_binary_double     BINARY_DOUBLE,
    type_date              DATE,
    type_timestamp         TIMESTAMP,
    type_timestamp_default TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type_timestamp_3       TIMESTAMP(3),
    type_timestamp_tz      TIMESTAMP WITH TIME ZONE,
    type_timestamp_3_tz    TIMESTAMP(3) WITH TIME ZONE,
    type_check_enum        VARCHAR2(20),
    type_char              CHAR(20),
    type_nchar             NCHAR(20),
    type_varchar           VARCHAR(20),
    type_varchar2          VARCHAR2(20),
    type_raw_8             RAW(8),
    type_blob              BLOB,
    type_clob              CLOB,
    type_nclob             NCLOB,
    PRIMARY KEY (id)
);

ALTER TABLE test_table
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES test_user (id) ON DELETE CASCADE;
ALTER TABLE test_table
    ADD CONSTRAINT fk_nullable_user FOREIGN KEY (nullable_user_id) REFERENCES test_user (id) ON DELETE SET NULL;
ALTER TABLE test_table
    ADD CONSTRAINT fk_group_category FOREIGN KEY (group_id, category_id) REFERENCES test_group_categories (group_id, category_id);
ALTER TABLE test_table
    ADD CONSTRAINT uk_email UNIQUE (email);
ALTER TABLE test_table
    ADD CONSTRAINT chk_type_check_enum CHECK (type_check_enum IN ('value1', 'value2', 'value3'));

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
COMMENT ON COLUMN test_table.type_blob IS '二进制大对象类型';
COMMENT ON COLUMN test_table.type_bit IS '位类型';

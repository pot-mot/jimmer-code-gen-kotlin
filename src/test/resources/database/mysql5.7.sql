SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;

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
    id                     INT AUTO_INCREMENT COMMENT '自增主键',
    user_id                INT NOT NULL COMMENT '用户ID',
    group_id               INT NOT NULL COMMENT '组ID',
    category_id            INT NOT NULL COMMENT '分类ID',
    nullable_user_id       INT COMMENT '可空用户ID',
    name                   VARCHAR(50) COMMENT '名称',
    email                  VARCHAR(100) COMMENT '邮箱',
    status                 TINYINT DEFAULT 1 COMMENT '状态',
    type_int               INT COMMENT '整数类型',
    type_int1              INT1 COMMENT '1位整数类型',
    type_int2              INT2 COMMENT '2位整数类型',
    type_int4              INT4 COMMENT '4位整数类型',
    type_int8              INT8 COMMENT '8位整数类型',
    type_tinyint           TINYINT COMMENT '微整数类型',
    type_smallint          SMALLINT COMMENT '小整数类型',
    type_mediumint         MEDIUMINT COMMENT '中整数类型',
    type_bigint            BIGINT COMMENT '大整数类型',
    type_numeric_10        NUMERIC(10) COMMENT '精确数值类型(10)',
    type_numeric_10_2      NUMERIC(10, 2) COMMENT '精确数值类型(10, 2)',
    type_decimal_10        DECIMAL(10) COMMENT '精确数值类型(10)',
    type_decimal_10_2      DECIMAL(10, 2) COMMENT '精确数值类型(10, 2)',
    type_dec               DEC,
    type_fixed             FIXED,
    type_real              REAL COMMENT '单精度浮点数',
    type_float             FLOAT COMMENT '单精度浮点数',
    type_double            DOUBLE COMMENT '双精度浮点数',
    type_double_precision  DOUBLE PRECISION COMMENT '双精度浮点数',
    type_float4            FLOAT4 COMMENT '单精度浮点数',
    type_float8            FLOAT8 COMMENT '单精度浮点数',
    type_boolean           BOOLEAN COMMENT '布尔类型',
    type_year              YEAR COMMENT '年类型',
    type_date              DATE COMMENT '日期类型',
    type_time              TIME COMMENT '时间类型',
    type_time_30           TIME(3) COMMENT '时间类型(3)',
    type_datetime          DATETIME COMMENT '日期时间类型',
    type_datetime_3        DATETIME(3) COMMENT '日期时间类型(3)',
    type_timestamp         TIMESTAMP COMMENT '时间戳类型',
    type_timestamp_default TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    type_text              TEXT COMMENT '文本类型',
    type_tinytext          TINYTEXT COMMENT '短文本类型',
    type_mediumtext        MEDIUMTEXT COMMENT '中长文本类型',
    type_longtext          LONGTEXT COMMENT '长文本类型',
    type_enum              ENUM ('value1', 'value2', 'value3') COMMENT '枚举类型',
    type_set               SET ('option1', 'option2', 'option3') COMMENT '集合类型',
    type_json              JSON COMMENT 'JSON数据类型',
    type_bit               BIT,
    type_bit_8             BIT(8),
    type_binary            BINARY,
    type_binary_8          BINARY(8),
    type_varbinary_8       VARBINARY(8),
    type_blob              BLOB,
    type_tinyblob          TINYBLOB,
    type_mediumblob        MEDIUMBLOB,
    type_long_blob         LONGBLOB,
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

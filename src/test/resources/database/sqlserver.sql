-- 创建登录账户
USE master;
GO
CREATE LOGIN test_login WITH PASSWORD = 'Test1234!';
GO

-- 创建数据库
CREATE DATABASE test;
GO

-- 使用新数据库创建用户
USE test;
GO
CREATE USER test FOR LOGIN test_login;
GO

-- 授权
GRANT CONNECT TO test;
GO
ALTER ROLE db_datareader ADD MEMBER test;
GO
ALTER ROLE db_datawriter ADD MEMBER test;
GO
ALTER ROLE db_ddladmin ADD MEMBER test;
GO
GRANT VIEW DATABASE STATE ON DATABASE::test TO test;
GO

SET QUOTED_IDENTIFIER ON;
GO

-- 创建 test_user 表
CREATE TABLE test_user
(
    id INT PRIMARY KEY
);

EXEC sp_addextendedproperty 'MS_Description', N'测试用户表', 'SCHEMA', 'dbo', 'TABLE', 'test_user';
EXEC sp_addextendedproperty 'MS_Description', N'非自增主键', 'SCHEMA', 'dbo', 'TABLE', 'test_user', 'COLUMN', 'id';

-- 创建 test_group_categories 表
CREATE TABLE test_group_categories
(
    group_id    INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (group_id, category_id)
);

EXEC sp_addextendedproperty 'MS_Description', N'组分类表', 'SCHEMA', 'dbo', 'TABLE', 'test_group_categories';

-- 创建 test_table 表
CREATE TABLE test_table
(
    id                        INT IDENTITY(1,1),
    user_id                   INT NOT NULL,
    group_id                  INT NOT NULL,
    category_id               INT NOT NULL,
    nullable_user_id          INT,
    name                      NVARCHAR(50),
    email                     NVARCHAR(100),
    status                    SMALLINT DEFAULT 1,
    type_int                  INT,
    type_bigint               BIGINT,
    type_smallint             SMALLINT,
    type_decimal              DECIMAL(10, 2),
    type_float                FLOAT(24),
    type_double               FLOAT(53),
    type_boolean              BIT,
    type_date                 DATE,
    type_datetime             DATETIME2,
    type_timestamp            DATETIME2 DEFAULT GETDATE(),
    type_timestamp_tz         DATETIMEOFFSET,
    type_text                 NVARCHAR(MAX),
    type_check_enum           NVARCHAR(20),
    type_blob                 VARBINARY(MAX),
    type_bit                  BINARY(8),

    CONSTRAINT pk_test_table
        PRIMARY KEY (id),

    -- 外键约束
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES test_user (id),

    CONSTRAINT fk_nullable_user
        FOREIGN KEY (nullable_user_id)
            REFERENCES test_user (id)
            ON DELETE SET NULL
            ON UPDATE CASCADE,

    CONSTRAINT fk_group_category
        FOREIGN KEY (group_id, category_id)
            REFERENCES test_group_categories (group_id, category_id),

    -- 唯一约束
    CONSTRAINT uk_email UNIQUE (email),

    -- 枚举约束
    CONSTRAINT chk_type_check_enum
        CHECK (type_check_enum IN ('value1', 'value2', 'value3'))
);

-- 创建索引
CREATE INDEX idx_name_status ON test_table (name, status) WHERE status = 1;

-- 添加表和列注释
EXEC sp_addextendedproperty 'MS_Description', N'测试表', 'SCHEMA', 'dbo', 'TABLE', 'test_table';
EXEC sp_addextendedproperty 'MS_Description', N'自增主键', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'id';
EXEC sp_addextendedproperty 'MS_Description', N'用户ID', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'user_id';
EXEC sp_addextendedproperty 'MS_Description', N'组ID', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'group_id';
EXEC sp_addextendedproperty 'MS_Description', N'分类ID', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'category_id';
EXEC sp_addextendedproperty 'MS_Description', N'可空用户ID', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'nullable_user_id';
EXEC sp_addextendedproperty 'MS_Description', N'名称', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'name';
EXEC sp_addextendedproperty 'MS_Description', N'邮箱', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'email';
EXEC sp_addextendedproperty 'MS_Description', N'状态', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'status';
EXEC sp_addextendedproperty 'MS_Description', N'整数类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_int';
EXEC sp_addextendedproperty 'MS_Description', N'大整数类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_bigint';
EXEC sp_addextendedproperty 'MS_Description', N'小整数类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_smallint';
EXEC sp_addextendedproperty 'MS_Description', N'精确小数类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_decimal';
EXEC sp_addextendedproperty 'MS_Description', N'单精度浮点数', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_float';
EXEC sp_addextendedproperty 'MS_Description', N'双精度浮点数', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_double';
EXEC sp_addextendedproperty 'MS_Description', N'布尔类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_boolean';
EXEC sp_addextendedproperty 'MS_Description', N'日期类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_date';
EXEC sp_addextendedproperty 'MS_Description', N'日期时间类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_datetime';
EXEC sp_addextendedproperty 'MS_Description', N'时间戳类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_timestamp';
EXEC sp_addextendedproperty 'MS_Description', N'时区时间戳类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_timestamp_tz';
EXEC sp_addextendedproperty 'MS_Description', N'文本类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_text';
EXEC sp_addextendedproperty 'MS_Description', N'枚举类型检查', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_check_enum';
EXEC sp_addextendedproperty 'MS_Description', N'二进制大对象类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_blob';
EXEC sp_addextendedproperty 'MS_Description', N'位类型', 'SCHEMA', 'dbo', 'TABLE', 'test_table', 'COLUMN', 'type_bit';

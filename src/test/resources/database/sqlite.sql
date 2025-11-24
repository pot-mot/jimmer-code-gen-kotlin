-- 创建 test_user 表
CREATE TABLE test_user
(
    id INTEGER PRIMARY KEY
);

-- 创建 test_group_categories 表
CREATE TABLE test_group_categories
(
    group_id    INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    PRIMARY KEY (group_id, category_id)
);

-- 创建 test_table 表
CREATE TABLE test_table
(
    id                        INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id                   INTEGER NOT NULL,
    group_id                  INTEGER NOT NULL,
    category_id               INTEGER NOT NULL,
    nullable_user_id          INTEGER,
    name                      TEXT,
    email                     TEXT,
    status                    INTEGER DEFAULT 1,
    type_int                  INTEGER,
    type_bigint               INTEGER,
    type_smallint             INTEGER,
    type_real                 REAL,
    type_timestamp            TEXT DEFAULT (datetime('now')),  -- SQLite 时间函数
    type_text                 TEXT,
    type_check_enum           TEXT,
    type_blob                 BLOB,

    -- 命名外键约束
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES test_user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_nullable_user
        FOREIGN KEY (nullable_user_id) REFERENCES test_user (id) ON DELETE SET NULL ON UPDATE SET NULL,
    CONSTRAINT fk_group_category
        FOREIGN KEY (group_id, category_id) REFERENCES test_group_categories (group_id, category_id) ON DELETE RESTRICT ON UPDATE RESTRICT,

    -- 命名唯一约束
    CONSTRAINT uk_test_table_email
        UNIQUE (email),

    -- 枚举约束（也可以命名）
    CONSTRAINT ck_test_table_enum
        CHECK (type_check_enum IN ('value1', 'value2', 'value3'))
);

-- 创建索引
CREATE INDEX idx_name_status ON test_table (name, status);

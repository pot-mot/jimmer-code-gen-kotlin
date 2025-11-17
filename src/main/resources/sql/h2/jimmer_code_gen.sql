create table if not exists db_check
(
    id uuid not null,
    table_id uuid not null,
    name text not null,
    expression text not null
);


create table if not exists db_column
(
    id uuid not null,
    table_id uuid not null,
    name text not null,
    comment text not null,
    type text not null,
    data_size integer,
    numeric_precision integer,
    nullable bool not null,
    default_value text,
    part_of_primary_key bool,
    auto_increment bool
);


create table if not exists db_database
(
    id uuid not null,
    type text check (type in ('MYSQL', 'POSTGRESQL', 'ORACLE', 'SQLSERVER', 'H2', 'SQLITE')) not null,
    name text not null,
    url text not null,
    username text not null,
    password text not null
);



create table if not exists db_foreign_key
(
    id uuid not null,
    table_id uuid not null,
    name text not null,
    comment text not null,
    referenced_table_name text not null,
    referenced_table_schema text not null,
    on_update text,
    on_delete text,
    column_refs text not null
);


create table if not exists db_index
(
    id uuid not null,
    table_id uuid not null,
    name text not null,
    column_names text not null,
    unique_index bool not null,
    where_predicates text
);


create table if not exists db_table
(
    id uuid not null,
    database_id uuid not null,
    schema text not null,
    name text not null,
    comment text not null
);


create table if not exists model
(
    id uuid not null,
    name text not null,
    description text not null,
    created_time timestamp not null,
    modified_time timestamp not null,
    database_type text check (database_type in ('MYSQL', 'POSTGRESQL', 'ORACLE', 'SQLSERVER', 'H2', 'SQLITE')) not null,
    database_name_strategy text check (database_name_strategy in ('UPPER_SNAKE', 'LOWER_SNAKE')) not null,
    default_foreign_key_type text check (default_foreign_key_type in ('REAL', 'FAKE')) not null,
    jvm_language text check (jvm_language in ('JAVA', 'KOTLIN')) not null,
    default_enumeration_strategy text check (default_enumeration_strategy in ('NAME', 'ORDINAL')) not null,
    viewport text not null,
    json_data text not null
);



create table if not exists model_history
(
    id uuid not null,
    model_id uuid not null,
    name text not null,
    description text not null,
    modified_time timestamp not null,
    database_type text check (database_type in ('MYSQL', 'POSTGRESQL', 'ORACLE', 'SQLSERVER', 'H2', 'SQLITE')) not null,
    database_name_strategy text check (database_name_strategy in ('UPPER_SNAKE', 'LOWER_SNAKE')) not null,
    default_foreign_key_type text check (default_foreign_key_type in ('REAL', 'FAKE')) not null,
    jvm_language text check (jvm_language in ('JAVA', 'KOTLIN')) not null,
    default_enumeration_strategy text check (default_enumeration_strategy in ('NAME', 'ORDINAL')) not null,
    viewport text not null,
    json_data text not null
);


create table if not exists generate_script
(
    id uuid not null,
    name text not null,
    type text check (type in ('AssociationGenerator', 'EmbeddableTypeGenerator', 'EntityGenerator', 'EnumerationGenerator', 'GroupGenerator', 'MappedSuperClassGenerator', 'ModelGenerator', 'TableGenerator')) not null,
    enabled bool not null,
    database_type text check (database_type in ('MYSQL', 'POSTGRESQL', 'ORACLE', 'SQLSERVER', 'H2', 'SQLITE', 'ANY')) not null,
    jvm_language text check (jvm_language in ('JAVA', 'KOTLIN', 'ANY')) not null,
    script_content text not null
);



create table if not exists cross_type
(
    id uuid not null,
    order_key integer not null,
    nullable bool,
    sql_type_id uuid not null,
    jvm_type_id uuid not null,
    ts_type_id uuid not null
);


create table if not exists jvm_to_sql_match_rule
(
    id uuid not null,
    order_key integer not null,
    jvm_source text check (jvm_source in ('JAVA', 'KOTLIN', 'ANY')) not null,
    match_reg_exp text not null,
    result_id uuid not null
);


create table if not exists jvm_to_ts_match_rule
(
    id uuid not null,
    order_key integer not null,
    jvm_source text check (jvm_source in ('JAVA', 'KOTLIN', 'ANY')) not null,
    match_reg_exp text not null,
    result_id uuid not null
);


create table if not exists jvm_type
(
    id uuid not null,
    order_key integer not null,
    jvm_source text check (jvm_source in ('JAVA', 'KOTLIN', 'ANY')) not null,
    type_expression text not null,
    serialized bool not null,
    extra_imports text not null,
    extra_annotations text not null
);



create table if not exists sql_to_jvm_match_rule
(
    id uuid not null,
    order_key integer not null,
    database_source text check (database_source in ('MYSQL', 'POSTGRESQL', 'ORACLE', 'SQLSERVER', 'H2', 'SQLITE', 'ANY')) not null,
    nullable_limit bool,
    match_reg_exp text not null,
    result_id uuid not null
);


create table if not exists sql_to_ts_match_rule
(
    id uuid not null,
    order_key integer not null,
    database_source text check (database_source in ('MYSQL', 'POSTGRESQL', 'ORACLE', 'SQLSERVER', 'H2', 'SQLITE', 'ANY')) not null,
    match_reg_exp text not null,
    result_id uuid not null
);


create table if not exists sql_type
(
    id uuid not null,
    order_key integer not null,
    database_source text check (database_source in ('MYSQL', 'POSTGRESQL', 'ORACLE', 'SQLSERVER', 'H2', 'SQLITE', 'ANY')) not null,
    type text not null,
    data_size integer,
    numeric_precision integer,
    default_value text
);



create table if not exists ts_to_jvm_match_rule
(
    id uuid not null,
    order_key integer not null,
    match_reg_exp text not null,
    result_id uuid not null
);


create table if not exists ts_to_sql_match_rule
(
    id uuid not null,
    order_key integer not null,
    match_reg_exp text not null,
    result_id uuid not null
);


create table if not exists ts_type
(
    id uuid not null,
    order_key integer not null,
    type_expression text not null,
    extra_imports text not null
);


alter table db_check add constraint pk_db_check_id primary key (id);
alter table db_column add constraint pk_db_column_id primary key (id);
alter table db_database add constraint pk_db_database_id primary key (id);
alter table db_foreign_key add constraint pk_db_foreign_key_id primary key (id);
alter table db_index add constraint pk_db_index_id primary key (id);
alter table db_table add constraint pk_db_table_id primary key (id);
alter table model add constraint pk_model_id primary key (id);
alter table model_history add constraint pk_model_history_id primary key (id);
alter table generate_script add constraint pk_generate_script_id primary key (id);
alter table cross_type add constraint pk_cross_type_id primary key (id);
alter table jvm_to_sql_match_rule add constraint pk_jvm_to_sql_match_rule_id primary key (id);
alter table jvm_to_ts_match_rule add constraint pk_jvm_to_ts_match_rule_id primary key (id);
alter table jvm_type add constraint pk_jvm_type_id primary key (id);
alter table sql_to_jvm_match_rule add constraint pk_sql_to_jvm_match_rule_id primary key (id);
alter table sql_to_ts_match_rule add constraint pk_sql_to_ts_match_rule_id primary key (id);
alter table sql_type add constraint pk_sql_type_id primary key (id);
alter table ts_to_jvm_match_rule add constraint pk_ts_to_jvm_match_rule_id primary key (id);
alter table ts_to_sql_match_rule add constraint pk_ts_to_sql_match_rule_id primary key (id);
alter table ts_type add constraint pk_ts_type_id primary key (id);
alter table db_check add constraint uk_db_check_default unique (table_id);
alter table db_check add constraint fk_db_check_table foreign key (table_id) references db_table (id);
alter table db_column add constraint uk_db_column_default unique (table_id, name);
alter table db_column add constraint fk_db_column_table foreign key (table_id) references db_table (id);
alter table db_foreign_key add constraint uk_db_foreign_key_default unique (table_id, name);
alter table db_foreign_key add constraint fk_db_foreign_key_table foreign key (table_id) references db_table (id);
alter table db_index add constraint uk_db_index_default unique (table_id, name);
alter table db_index add constraint fk_db_index_table foreign key (table_id) references db_table (id);
alter table db_table add constraint uk_db_table_default unique (database_id, schema, name);
alter table db_table add constraint fk_db_table_database foreign key (database_id) references db_database (id);
alter table model_history add constraint fk_model_history_model foreign key (model_id) references model (id);
alter table cross_type add constraint fk_cross_type_sql_type foreign key (sql_type_id) references sql_type (id);
alter table cross_type add constraint fk_cross_type_jvm_type foreign key (jvm_type_id) references jvm_type (id);
alter table cross_type add constraint fk_cross_type_ts_type foreign key (ts_type_id) references ts_type (id);
alter table jvm_to_sql_match_rule add constraint fk_jvm_to_sql_match_rule_result foreign key (result_id) references sql_type (id);
alter table jvm_to_ts_match_rule add constraint fk_jvm_to_ts_match_rule_result foreign key (result_id) references ts_type (id);
alter table sql_to_jvm_match_rule add constraint fk_sql_to_jvm_match_rule_result foreign key (result_id) references jvm_type (id);
alter table sql_to_ts_match_rule add constraint fk_sql_to_ts_match_rule_result foreign key (result_id) references ts_type (id);
alter table ts_to_jvm_match_rule add constraint fk_ts_to_jvm_match_rule_result foreign key (result_id) references jvm_type (id);
alter table ts_to_sql_match_rule add constraint fk_ts_to_sql_match_rule_result foreign key (result_id) references sql_type (id);
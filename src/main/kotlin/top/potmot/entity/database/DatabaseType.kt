package top.potmot.entity.database

enum class DatabaseType {
    MYSQL, POSTGRESQL, ORACLE, SQLSERVER, H2, SQLITE
}

enum class DatabaseSource {
    MYSQL, POSTGRESQL, ORACLE, SQLSERVER, H2, SQLITE, ANY
}

enum class ScriptDatabaseType {
    MYSQL, POSTGRESQL, ORACLE, SQLSERVER, H2, SQLITE, ANY
}

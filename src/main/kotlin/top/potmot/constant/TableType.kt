package top.potmot.constant

enum class TableType(val value: String) {
    TABLE("TABLE"),
    VIEW("VIEW"),
    SYSTEM_TABLE("SYSTEM TABLE"),
    GLOBAL_TEMPORARY("GLOBAL TEMPORARY"),
    LOCAL_TEMPORARY("LOCAL TEMPORARY"),
    ALIAS("ALIAS"),
}

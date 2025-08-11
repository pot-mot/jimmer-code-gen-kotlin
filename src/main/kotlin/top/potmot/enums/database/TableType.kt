package top.potmot.enums.database


/**
 * 表类型
 */
enum class TableType {
    /**
     * TABLE
     */
    TABLE,

    /**
     * VIEW
     */
    VIEW,

    /**
     * SYSTEM_TABLE
     */
    SYSTEM_TABLE,

    /**
     * GLOBAL_TEMPORARY
     */
    GLOBAL_TEMPORARY,

    /**
     * LOCAL_TEMPORARY
     */
    LOCAL_TEMPORARY,

    /**
     * ALIAS
     */
    ALIAS,

    /**
     * UNKNOWN
     */
    UNKNOWN,
}

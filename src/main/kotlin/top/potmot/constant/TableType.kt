package top.potmot.constant

enum class TableType(val value: String) {
    TABLE("TABLE"),
    VIEW("VIEW"),
    SYSTEM_TABLE("SYSTEM TABLE"),
    GLOBAL_TEMPORARY("GLOBAL TEMPORARY"),
    LOCAL_TEMPORARY("LOCAL TEMPORARY"),
    ALIAS("ALIAS"),
    UNKNOWN("UNKNOWN");

    companion object {
        fun fromValue(value: String?): TableType {
            if (value == null) return UNKNOWN
            return try {
                valueOf(value.uppercase())
            } catch (e: Exception) {
                UNKNOWN
            }
        }
    }
}

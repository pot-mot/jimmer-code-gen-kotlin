package top.potmot.enumeration

enum class TableType {
    TABLE,
    SUPER_TABLE,
    VIEW,
    SYSTEM_TABLE,
    GLOBAL_TEMPORARY,
    LOCAL_TEMPORARY,
    ALIAS,
    UNKNOWN;

    companion object {
        fun fromValue(value: String?): TableType =
            value?.let {
                try {
                    valueOf(value.uppercase().replace(" ", "_"))
                } catch (e: Exception) {
                    UNKNOWN
                }
            } ?: UNKNOWN
    }
}

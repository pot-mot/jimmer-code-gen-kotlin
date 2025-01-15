package top.potmot.entity.dto.share

interface ColumnTypeMeta {
    val typeCode: Int
    val overwriteByRaw: Boolean
    val rawType: String
    val typeNotNull: Boolean
    val dataSize: Int
    val numericPrecision: Int
    val autoIncrement: Boolean
}
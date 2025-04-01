package top.potmot.core.entity.reconvert.type

import java.sql.JDBCType
import top.potmot.core.common.booleanType
import top.potmot.core.common.dateTimeType
import top.potmot.core.common.dateType
import top.potmot.core.common.intType
import top.potmot.core.common.numericType
import top.potmot.core.common.stringType
import top.potmot.core.common.timeType
import top.potmot.entity.dto.GenEntityReconvertView
import top.potmot.enumeration.EnumType

private fun typeStrToJdbcType(typeStr: String): JDBCType? =
    when (typeStr) {
        in intType -> JDBCType.INTEGER
        in numericType -> JDBCType.NUMERIC
        in booleanType -> JDBCType.BOOLEAN
        in stringType -> JDBCType.VARCHAR
        in timeType -> JDBCType.TIME
        in dateType -> JDBCType.DATE
        in dateTimeType -> JDBCType.TIMESTAMP
        else -> null
    }

fun toColumnType(
    property: GenEntityReconvertView.TargetOf_properties,
): JDBCType =
    property.enum?.let {
        when (it.enumType) {
            EnumType.ORDINAL -> JDBCType.INTEGER
            else -> JDBCType.VARCHAR
        }
    }
        ?: typeStrToJdbcType(property.type)
        ?: throw IllegalArgumentException("unknown type: ${property.type}")
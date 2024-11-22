package top.potmot.entity.convert

import top.potmot.entity.dto.GenTableInput
import top.potmot.enumeration.TableType
import java.sql.Types

val baseColumn = GenTableInput.TargetOf_columns(
    name = "column",
    comment = "column comment",
    remark = "column remark",
    typeCode = Types.INTEGER,
    overwriteByRaw = false,
    rawType = "int",
    typeNotNull = true,
    dataSize = 10,
    numericPrecision = 0,
    partOfPk = false,
    autoIncrement = false,
    businessKey = false,
    keyGroup = null,
    logicalDelete = false,
    orderKey = 0,
    enumId = null,
)

val primaryColumn = baseColumn.copy(
    name = "id",
    comment = "id comment",
    remark = "id remark",
    partOfPk = true,
    autoIncrement = true,
)

val baseTable = GenTableInput(
    name = "table",
    comment = "table comment",
    remark = "table remark",
    type = TableType.TABLE,
    superTableIds = emptyList(),
    columns = listOf(
        baseColumn,
        primaryColumn,
    )
)

val createdTime = baseColumn.copy(
    name = "created_time",
    comment = "created_time comment",
    remark = "created_time remark",
    typeCode = Types.TIME,
    rawType = "datetime"
)

val modifiedTime = baseColumn.copy(
    name = "modified_time",
    comment = "modified_time comment",
    remark = "modified_time remark",
    typeCode = Types.TIME,
    rawType = "datetime"
)

val superTable = baseTable.copy(
    name = "super_table",
    comment = "super_table comment",
    columns = listOf(
        createdTime,
        modifiedTime,
    )
)
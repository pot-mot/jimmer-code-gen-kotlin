//package top.potmot.utils.liquibase
//
//import liquibase.Contexts
//import liquibase.Liquibase
//import liquibase.change.Change
//import liquibase.change.ColumnConfig
//import liquibase.change.ConstraintsConfig
//import liquibase.change.core.AddForeignKeyConstraintChange
//import liquibase.change.core.AddUniqueConstraintChange
//import liquibase.change.core.CreateTableChange
//import liquibase.changelog.ChangeSet
//import liquibase.changelog.DatabaseChangeLog
//import liquibase.database.Database
//import liquibase.database.DatabaseFactory
//import liquibase.database.jvm.JdbcConnection
//import liquibase.resource.AbstractResourceAccessor
//import liquibase.resource.OpenOptions
//import liquibase.resource.Resource
//import liquibase.serializer.core.xml.XMLChangeLogSerializer
//import top.potmot.config.GenConfig
//import top.potmot.core.entity.convert.clearColumnName
//import top.potmot.core.entity.convert.clearTableName
//import top.potmot.enumeration.AssociationType
//import top.potmot.model.extension.toSource
//import top.potmot.core.meta.getFullType
//import top.potmot.model.GenDataSource
//import top.potmot.model.dto.GenAssociationModelInput
//import top.potmot.model.dto.GenTableColumnsInput
//import java.io.ByteArrayInputStream
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import java.io.InputStream
//import java.io.OutputStream
//import java.io.StringWriter
//import java.net.URI
//import java.sql.Types
//import java.util.*
//
///**
// * 转换 GenColumn 为 ColumnConfig
// */
//private fun GenTableColumnsInput.TargetOf_columns.toColumnConfig(): ColumnConfig {
//    val columnConfig = ColumnConfig()
//
//    // 基本信息
//    columnConfig.name = name
//    columnConfig.remarks = comment
//    columnConfig.type = getFullType(typeCode, type, displaySize, numericPrecision)
//    columnConfig.isAutoIncrement = autoIncrement
//
//    defaultValue.let {
//        try {
//            // TODO 默认值转换存在极大兼容问题
//            when (typeCode) {
//                Types.NULL -> columnConfig.defaultOnNull = true
//
//                Types.BIT, Types.BOOLEAN -> columnConfig.defaultValueBoolean = defaultValue.toBoolean()
//
//                Types.TINYINT,
//                Types.SMALLINT,
//                Types.INTEGER,
//                Types.BIGINT -> columnConfig.defaultValueNumeric = defaultValue?.toLong()
//
//                Types.REAL,
//                Types.FLOAT, Types.DOUBLE,
//                Types.DECIMAL, Types.NUMERIC -> columnConfig.defaultValueNumeric = defaultValue?.toDouble()
//
//                Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR, Types.CLOB, Types.NCLOB, Types.SQLXML, Types.DATALINK -> columnConfig.defaultValue =
//                    defaultValue
//
//                Types.DATE, Types.TIME, Types.TIME_WITH_TIMEZONE, Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE -> columnConfig.defaultOnNull =
//                    true
//
//                Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY -> columnConfig.defaultOnNull = true
//
//                else -> columnConfig.defaultValue = defaultValue
//            }
//        } catch (e: Exception) {
//            columnConfig.defaultOnNull = true
//        }
//    }
//
//
//    //处理约束
//    val constraints = ConstraintsConfig()
//    constraints.setNullable(!typeNotNull)
//    constraints.setPrimaryKey(partOfPk)
//    columnConfig.setConstraints(constraints)
//
//    return columnConfig
//}
//
///**
// * 转换 GenTableColumnsInput 为 CreateTableChange
// */
//private fun GenTableColumnsInput.toCreateTableChange(): CreateTableChange {
//    val createTableChange = CreateTableChange()
//
//    // 基本信息
//    createTableChange.tableName = name
//    createTableChange.remarks = comment
//
//    schema?.let {
//        createTableChange.schemaName = it.name
//    }
//
//    createTableChange.columns = columns.map { it.toColumnConfig() }
//
//    return createTableChange
//}
//
///**
// * 从 GenTableColumnsInput 中获取 AddUniqueConstraintChange
// */
//private fun GenTableColumnsInput.getAddUniqueConstraintChange(): List<AddUniqueConstraintChange> =
//    columns.filter { it.partOfUniqueIdx && !it.partOfPk }.map {
//        val addUniqueConstraintChange = AddUniqueConstraintChange()
//
//        addUniqueConstraintChange.constraintName = "u_idx_${it.name}"
//        addUniqueConstraintChange.tableName = name
//        addUniqueConstraintChange.columnNames = it.name
//
//        addUniqueConstraintChange
//    }
//
//private fun fkName(
//    sourceColumn: GenAssociationModelInput.TargetOf_sourceColumn,
//    targetColumn: GenAssociationModelInput.TargetOf_targetColumn
//): String =
//    "fk_" + "${sourceColumn.table.name.clearTableName()}_${sourceColumn.name.clearColumnName()}" +
//            "__${targetColumn.table.name.clearTableName()}_${targetColumn.name.clearColumnName()}"
//
///**
// * 从 GenAssociation 转化为 普通外键变更记录
// * 即生成外键与可能存在的唯一约束
// */
//private fun GenAssociationModelInput.toFkChange(): AddForeignKeyConstraintChange {
//    val fkChange = AddForeignKeyConstraintChange()
//
//    fkChange.constraintName = fkName(sourceColumn, targetColumn)
//
//    fkChange.baseTableName = sourceColumn.table.name
//    fkChange.baseColumnNames = sourceColumn.name
//
//    fkChange.referencedTableName = targetColumn.table.name
//    fkChange.referencedColumnNames = targetColumn.name
//
//    return fkChange
//}
//
///**
// * 从 GenAssociation 转化为 多对多变更记录
// * 即生成中间表与两个关联外键
// */
//private fun GenAssociationModelInput.toManyToManyChanges(): List<Change> {
//    val sourceTableName = sourceColumn.table.name.clearTableName()
//    val targetTableName = targetColumn.table.name.clearTableName()
//
//    val relationTable = CreateTableChange()
//
//    relationTable.tableName = "${sourceTableName}_${targetTableName}_mapping"
//    relationTable.remarks = "${sourceColumn.table.comment}与${targetColumn.table.comment}关联表"
//
//    // 关联表源列
//    val relationSourceColumn = ColumnConfig()
//    relationSourceColumn.setName("${sourceTableName}_id")
//    relationSourceColumn.setType(sourceColumn.type)
//    relationSourceColumn.setConstraints(ConstraintsConfig().setPrimaryKey(true))
//    relationTable.addColumn(relationSourceColumn)
//    // 关联表目标列
//    val relationTargetColumn = ColumnConfig()
//    relationTargetColumn.setName("${targetTableName}_id")
//    relationTargetColumn.setType(targetColumn.type)
//    relationTargetColumn.setConstraints(ConstraintsConfig().setPrimaryKey(true))
//    relationTable.addColumn(relationTargetColumn)
//
//    // 关联表与源表外键
//    val sourceFk = AddForeignKeyConstraintChange()
//
//    sourceFk.constraintName = "fk_${relationTable.tableName}_${relationSourceColumn.name}"
//    sourceFk.baseTableName = relationTable.tableName
//    sourceFk.baseColumnNames = relationSourceColumn.name
//    sourceFk.referencedTableName = sourceTableName
//    sourceFk.referencedColumnNames = sourceColumn.name
//
//    // 关联表与目标表外键
//    val targetFk = AddForeignKeyConstraintChange()
//
//    targetFk.constraintName = "fk_${relationTable.tableName}_${relationTargetColumn.name}"
//    targetFk.baseTableName = relationTable.tableName
//    targetFk.baseColumnNames = relationTargetColumn.name
//    targetFk.referencedTableName = targetTableName
//    targetFk.referencedColumnNames = targetColumn.name
//
//
//    return listOf(relationTable, sourceFk, targetFk)
//}
//
//
//fun createDatabaseChangeLog(
//    tables: List<GenTableColumnsInput>,
//    associations: List<GenAssociationModelInput>
//): DatabaseChangeLog {
//    val changeLog = DatabaseChangeLog()
//    val changeSet = ChangeSet("1", GenConfig.author, false, false, null, null, null, null)
//    changeLog.addChangeSet(changeSet)
//
//    tables.forEach {
//        it.toCreateTableChange()
//            .apply { changeSet.addChange(this) }
//        it.getAddUniqueConstraintChange().forEach { changeSet.addChange(it) }
//    }
//
//    associations.forEach {
//        if (it.associationType == AssociationType.MANY_TO_MANY) {
//            it.toManyToManyChanges()
//                .forEach { changeSet.addChange(it) }
//        } else {
//            it.toFkChange()
//                .apply { changeSet.addChange(this) }
//        }
//    }
//
//    return changeLog
//}
//
///**
// * 将 changeLog 文件转成 创建用 SQL
// */
//private fun changeLogToCreateSql(
//    changeLog: DatabaseChangeLog,
//    database: Database
//): String {
//    val serializer = XMLChangeLogSerializer()
//    val changeSetBytes = ByteArrayOutputStream()
//    serializer.write(changeLog.changeSets, changeSetBytes)
//
//    val liquibase = Liquibase(
//        "classpath:db/createTable.xml",
//        object : AbstractResourceAccessor() {
//            override fun close() {}
//
//            override fun search(path: String?, recursive: Boolean): MutableList<Resource> {
//                return emptyList<Resource>().toMutableList()
//            }
//
//            override fun getAll(path: String?): MutableList<Resource> {
//                return listOf(object : Resource {
//                    override fun getPath(): String? {
//                        return null
//                    }
//
//                    @Throws(IOException::class)
//                    override fun openInputStream(): InputStream {
//                        return ByteArrayInputStream(changeSetBytes.toByteArray())
//                    }
//
//                    override fun isWritable(): Boolean {
//                        return false
//                    }
//
//                    override fun exists(): Boolean {
//                        return true
//                    }
//
//                    override fun resolve(other: String): Resource? {
//                        return null
//                    }
//
//                    override fun resolveSibling(other: String): Resource? {
//                        return null
//                    }
//
//                    @Throws(IOException::class)
//                    override fun openOutputStream(openOptions: OpenOptions?): OutputStream? {
//                        return null
//                    }
//
//                    override fun getUri(): URI? {
//                        return null
//                    }
//                }).toMutableList()
//            }
//
//            override fun describeLocations(): MutableList<String> {
//                return emptyList<String>().toMutableList()
//            }
//        },
//        database
//    )
//
//    val output = StringWriter()
//    liquibase.update(Contexts(), output)
//    var sql = output.toString()
//    val sb = StringBuilder()
//    val scanner = Scanner(sql)
//    while (scanner.hasNextLine()) {
//        val line = scanner.nextLine()
//        // 过滤非必要的行
//        if (line.contains("DATABASECHANGELOGLOCK", ignoreCase = true) ||
//            line.contains("DATABASECHANGELOG", ignoreCase = true) ||
//            line.contains("SET SEARCH_PATH", ignoreCase = true) ||
//            line.startsWith("--") ||
//            line.isBlank()
//        ) {
//            continue
//        }
//
//        // 格式化一下建表语句
//        if (line.startsWith("CREATE TABLE")) {
//            val indent = "    "
//            val startIndex = line.indexOf("(")
//            val endIndex = line.lastIndexOf(")")
//
//            sb.appendLine(line.substring(0, startIndex + 1))
//
//            val columns = line
//                .substring(startIndex + 1, endIndex)
//                .split(",")
//                .joinToString(",\n") {"$indent${it.trim()}"}
//
//            sb.appendLine(columns)
//
//            sb.appendLine(line.substring(endIndex))
//
//        } else {
//            sb.appendLine(line)
//        }
//    }
//    sql = sb.toString()
//
//    return sql
//}
//
//fun GenDataSource.createSql(
//    tables: List<GenTableColumnsInput>,
//    associations: List<GenAssociationModelInput>
//): String {
//    val databaseChangeLog = createDatabaseChangeLog(tables, associations)
//
//    val database = DatabaseFactory.getInstance()
//        .findCorrectDatabaseImplementation(JdbcConnection(this.toSource().get()))
//
//    database.outputDefaultCatalog = false
//    database.outputDefaultSchema = false
//
//    return changeLogToCreateSql(databaseChangeLog, database)
//}

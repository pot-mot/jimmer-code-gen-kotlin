package top.potmot.utils.liquibase

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
//import top.potmot.core.config.getContextOrGlobal
//import top.potmot.core.database.generate.columnType.ColumnTypeDefiner
//import top.potmot.core.database.generate.columnType.getColumnTypeDefiner
//import top.potmot.enumeration.AssociationType
//import top.potmot.entity.GenDataSource
//import top.potmot.entity.dto.GenTableLiquibaseView
//import top.potmot.core.database.dataSource.toSource
//import java.io.ByteArrayInputStream
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import java.io.InputStream
//import java.io.OutputStream
//import java.io.StringWriter
//import java.net.URI
//import java.sql.Types
//import java.util.Scanner
//import top.potmot.core.database.meta.MappingTableMeta
//import top.potmot.entity.dto.GenAssociationLiquibaseView
//
//// https://blog.csdn.net/qq_42629988/article/details/122883976
//
///**
// * 转换 GenColumn 为 ColumnConfig
// */
//private fun GenTableLiquibaseView.TargetOf_columns.toColumnConfig(typeDefiner: ColumnTypeDefiner): ColumnConfig {
//    val columnConfig = ColumnConfig()
//
//    // 基本信息
//    columnConfig.name = name
//    columnConfig.remarks = comment
//    columnConfig.type = typeDefiner.getTypeDefine(this)
//    columnConfig.isAutoIncrement = autoIncrement
//
//    defaultValue.let {
//        try {
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
// * 转换 GenTableLiquibaseView 为 CreateTableChange
// */
//private fun GenTableLiquibaseView.toCreateTableChange(typeDefiner: ColumnTypeDefiner): CreateTableChange {
//    val createTableChange = CreateTableChange()
//
//    // 基本信息
//    createTableChange.tableName = name
//    createTableChange.remarks = comment
//
//    createTableChange.columns = columns.map { it.toColumnConfig(typeDefiner) }
//
//    return createTableChange
//}
//
///**
// * 从 GenTableLiquibaseView 中获取 AddUniqueConstraintChange
// */
//private fun GenTableLiquibaseView.getAddUniqueConstraintChange(): List<AddUniqueConstraintChange> =
//    indexes.filter { it.uniqueIndex }.map { index ->
//        val addUniqueConstraintChange = AddUniqueConstraintChange()
//
//        addUniqueConstraintChange.constraintName = index.name
//        addUniqueConstraintChange.tableName = name
//        addUniqueConstraintChange.columnNames = index.columns.joinToString(",") { it.name }
//
//        addUniqueConstraintChange
//    }
//
///**
// * 从 GenAssociation 转化为 普通外键变更记录
// * 即生成外键与可能存在的唯一约束
// */
//private fun GenAssociationLiquibaseView.toFkChange(): AddForeignKeyConstraintChange {
//    val fkChange = AddForeignKeyConstraintChange()
//
//    val sourceColumns = columnReferences.map { it.sourceColumn.name }
//    val targetColumns = columnReferences.map { it.targetColumn.name }
//
//    fkChange.constraintName = name
//
//    fkChange.baseTableName = sourceTable.name
//    fkChange.baseColumnNames = sourceColumns.joinToString(",")
//
//    fkChange.referencedTableName = targetTable.name
//    fkChange.referencedColumnNames = targetColumns.joinToString(",")
//
//    return fkChange
//}
//
//private fun GenAssociationLiquibaseView.toMappingTableMeta() =
//    MappingTableMeta(
//        name,
//        sourceTable.name,
//        sourceTable.comment,
//        columnReferences.map { it.sourceColumn.name },
//
//        targetTable.name,
//        targetTable.comment,
//        columnReferences.map { it.targetColumn.name },
//
//        columnReferences.map { it.sourceColumn },
//    )
//
///**
// * 从 GenAssociation 转化为 多对多变更记录
// * 即生成中间表与两个关联外键
// */
//private fun GenAssociationLiquibaseView.toManyToManyChanges(typeDefiner: ColumnTypeDefiner): List<Change> {
//    val mappingTable = CreateTableChange()
//
//    val meta = toMappingTableMeta()
//
//    mappingTable.tableName = meta.name
//    mappingTable.remarks = meta.comment
//
//    // 关联表源列
//    meta.mappingSourceColumnNames.mapIndexed { index, it ->
//        val mappingSourceColumn = ColumnConfig()
//
//        mappingSourceColumn.setName(it)
//        mappingSourceColumn.setType(typeDefiner.getTypeDefine(meta.columnTypes[index]))
//        mappingSourceColumn.setConstraints(ConstraintsConfig().setPrimaryKey(true))
//        mappingTable.addColumn(mappingSourceColumn)
//    }
//
//    // 关联表目标列
//    meta.mappingTargetColumnNames.mapIndexed { index, it ->
//        val mappingSourceColumn = ColumnConfig()
//
//        mappingSourceColumn.setName(it)
//        mappingSourceColumn.setType(typeDefiner.getTypeDefine(meta.columnTypes[index]))
//        mappingSourceColumn.setConstraints(ConstraintsConfig().setPrimaryKey(true))
//        mappingTable.addColumn(mappingSourceColumn)
//    }
//
//    // 关联表与源表外键
//    val sourceFk = AddForeignKeyConstraintChange()
//
//    sourceFk.constraintName = meta.sourceFk.name
//    sourceFk.baseTableName = meta.name
//    sourceFk.baseColumnNames = meta.mappingSourceColumnNames.joinToString(",")
//    sourceFk.referencedTableName = meta.sourceTableName
//    sourceFk.referencedColumnNames = meta.sourceColumnNames.joinToString(",")
//
//    // 关联表与目标表外键
//    val targetFk = AddForeignKeyConstraintChange()
//
//    targetFk.constraintName = meta.targetFk.name
//    targetFk.baseTableName = meta.name
//    targetFk.baseColumnNames = meta.mappingTargetColumnNames.joinToString(",")
//    targetFk.referencedTableName = meta.targetTableName
//    targetFk.referencedColumnNames = meta.targetColumnNames.joinToString(",")
//
//
//    return listOf(mappingTable, sourceFk, targetFk)
//}
//
//
///**
// * 创建数据源变更记录
// */
//fun createDatabaseChangeLog(
//    dataSource: GenDataSource,
//    tables: List<GenTableLiquibaseView>,
//    associations: List<GenAssociationLiquibaseView>
//): DatabaseChangeLog {
//    val changeLog = DatabaseChangeLog()
//    val changeSet = ChangeSet("1", getContextOrGlobal().author, false, false, null, null, null, null)
//    changeLog.addChangeSet(changeSet)
//
//    val typeDefiner = dataSource.type.getColumnTypeDefiner()
//
//    tables.forEach { column ->
//        column.toCreateTableChange(typeDefiner)
//            .apply { changeSet.addChange(this) }
//        column.getAddUniqueConstraintChange().forEach { changeSet.addChange(it) }
//    }
//
//    associations.forEach { association ->
//        if (association.type == AssociationType.MANY_TO_MANY) {
//            association.toManyToManyChanges(typeDefiner)
//                .forEach { changeSet.addChange(it) }
//        } else {
//            association.toFkChange()
//                .apply { changeSet.addChange(this) }
//        }
//    }
//
//    return changeLog
//}
//
///**
// * 此处给出了一个虚假的 createTable.xml 并通过实现 AbstractResourceAccessor 将真正的源覆盖到了 ByteArray 中
// */
//private fun createLiquibase(database: Database, byteArray: ByteArray): Liquibase =
//    Liquibase(
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
//                        return ByteArrayInputStream(byteArray)
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
///**
// * 将 changeLog 文件转成创建用 SQL
// */
//private fun changeLogToCreateSql(
//    database: Database,
//    changeLog: DatabaseChangeLog,
//): String {
//    val serializer = XMLChangeLogSerializer()
//    val changeSetBytes = ByteArrayOutputStream()
//    serializer.write(changeLog.changeSets, changeSetBytes)
//
//    val liquibase = createLiquibase(database, changeSetBytes.toByteArray())
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
//                .joinToString(",\n") { "$indent${it.trim()}" }
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
//    tables: List<GenTableLiquibaseView>,
//    associations: List<GenAssociationLiquibaseView>
//): String {
//    val databaseChangeLog = createDatabaseChangeLog(this, tables, associations)
//
//    val connection = toSource().get()
//
//    val database = DatabaseFactory.getInstance()
//        .findCorrectDatabaseImplementation(JdbcConnection(connection))
//
//    database.outputDefaultCatalog = false
//    database.outputDefaultSchema = false
//
//    val sql = changeLogToCreateSql(database, databaseChangeLog)
//
//    connection.close()
//
//    return sql
//}

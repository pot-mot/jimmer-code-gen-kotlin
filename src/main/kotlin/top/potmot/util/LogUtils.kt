package top.potmot.util

import org.babyfish.jimmer.kt.hide
import org.babyfish.jimmer.kt.new
import org.slf4j.Logger
import top.potmot.constant.ConsoleStyle
import top.potmot.model.GenColumn
import top.potmot.model.GenEntity
import top.potmot.model.GenProperty
import top.potmot.model.GenTable
import top.potmot.model.by

object LogUtils {
    private val LOGGER: Logger = org.slf4j.LoggerFactory.getLogger(LogUtils::class.java)

    fun logSql(sql: String, variables: List<Any?>, cost: Long, result: Any?, logger: Logger = LOGGER) {
        val stringBuilder = StringBuilder()
        val args = ArrayList<String>()
        stringBuilder.append("sql: {}\n")
        args.add(sql.replace("\\([?, ]*?\\)".toRegex(), "VARIABLES"))
        if (variables.size > 20) {
            stringBuilder.append(ConsoleStyle.RED).append("variable size: {}").append(ConsoleStyle.RESET)
            args.add(variables.size.toString())
        } else {
            stringBuilder.append("variable: ").append(variables)
        }
        stringBuilder.append("\n")
        if (cost > 100) {
            stringBuilder.append(ConsoleStyle.RED_BACKGROUND).append("time: {}").append(ConsoleStyle.RESET)
            args.add(cost.toString())
        } else if (cost > 20) {
            stringBuilder.append(ConsoleStyle.YELLOW_BACKGROUND).append("time: {}").append(ConsoleStyle.RESET)
            args.add(cost.toString())
        } else {
            stringBuilder.append("time: ").append(cost)
        }
        stringBuilder.append("\n").append(result)
        stringBuilder.append("\n")
        logger.info(stringBuilder.toString(), *args.toTypedArray())
    }

    fun logTable(table: GenTable, logger: Logger = LOGGER) {
        val stringBuilder = StringBuilder()
        val tableWithoutColumns = new(GenTable::class).by(table) {
            hide(this, GenTable::columns)
            hide(this, GenTable::entity)
            columns = columns.map {
                hide(it, GenColumn::property)
                it
            }
        }
        stringBuilder
            .append("----------------------------\n")
            .append(tableWithoutColumns).append('\n')
        table.columns.forEach {
            stringBuilder.append(it).append('\n')
        }
        logger.info(stringBuilder.toString())
    }

    fun logEntity(entity: GenEntity, logger: Logger = LOGGER) {
        val stringBuilder = StringBuilder()
        val entityWithoutProperties = new(GenEntity::class).by(entity) {
            hide(this, GenEntity::properties)
            hide(this, GenEntity::table)
            properties = properties.map {
                hide(it, GenProperty::column)
                it
            }
        }
        stringBuilder
            .append("----------------------------\n")
            .append(entityWithoutProperties).append('\n')
        entity.properties.forEach {
            stringBuilder.append(it).append('\n')
        }
        logger.info(stringBuilder.toString())
    }
}

package top.potmot.util

import org.slf4j.Logger
import top.potmot.constant.ConsoleStyle

object LogUtils {
    fun infoSqlLog(sql: String, logger: Logger) {
        logger.info(sql)
    }

    fun infoSqlLog(sql: String, variables: List<Any?>, cost: Long, result: Any?, logger: Logger) {
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
        stringBuilder.append("\n").append(result);
        stringBuilder.append("\n")
        logger.info(stringBuilder.toString(), *args.toTypedArray())
    }
}

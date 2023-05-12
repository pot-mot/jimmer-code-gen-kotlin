package top.potmot.external.utils;

import org.slf4j.Logger;
import top.potmot.external.constant.ConsoleStyle;

import java.util.ArrayList;
import java.util.List;

public class LogOutputUtil {
    public static void infoSqlLog(String sql, List<Object> variables, Long cost, Object result, Logger logger) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> args = new ArrayList<>();
        stringBuilder.append("sql: {}\n");
        args.add(sql.replaceAll("\\([?, ]*?\\)", "VARIABLES"));

        if (variables.size() > 20) {
            stringBuilder.append(ConsoleStyle.RED).append("variable size: {}").append(ConsoleStyle.RESET);
            args.add(String.valueOf(variables.size()));
        } else {
            stringBuilder.append("variable: ").append(variables);
        }

        stringBuilder.append("\n");

        if (cost > 100) {
            stringBuilder.append(ConsoleStyle.RED_BACKGROUND).append("time: {}").append(ConsoleStyle.RESET);
            args.add(String.valueOf(cost));
        } else if (cost > 20) {
            stringBuilder.append(ConsoleStyle.YELLOW_BACKGROUND).append("time: {}").append(ConsoleStyle.RESET);
            args.add(String.valueOf(cost));
        } else {
            stringBuilder.append("time: ").append(cost);
        }
//        stringBuilder.append(result);
        stringBuilder.append("\n");

        logger.info(stringBuilder.toString(), args.toArray());
    }
}

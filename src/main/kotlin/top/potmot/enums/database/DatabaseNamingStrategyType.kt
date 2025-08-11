package top.potmot.enums.database


/**
 * 数据库命名策略
 */
enum class DatabaseNamingStrategyType {
    /**
     * 字面
     */
    RAW,

    /**
     * 全小写
     */
    LOWER_CASE,

    /**
     * 全大写
     */
    UPPER_CASE,
}

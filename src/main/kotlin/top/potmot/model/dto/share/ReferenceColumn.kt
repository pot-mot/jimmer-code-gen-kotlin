package top.potmot.model.dto.share

interface ReferenceColumn {
    /**
     * ID
     */
    val id: Long

    /**
     * 名称
     */
    val name: String

    /**
     * 注释
     */
    val comment: String

    /**
     * 是否为主键的部分
     */
    val partOfPk: Boolean
}

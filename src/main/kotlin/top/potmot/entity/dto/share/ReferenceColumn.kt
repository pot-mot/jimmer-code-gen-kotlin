package top.potmot.entity.dto.share

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

    /**
     * 是否为逻辑删除
     */
    val logicalDelete: Boolean
}

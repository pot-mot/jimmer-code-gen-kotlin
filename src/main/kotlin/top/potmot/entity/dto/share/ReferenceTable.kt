package top.potmot.entity.dto.share

import top.potmot.enumeration.TableType

interface ReferenceTable {
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
     * 种类
     */
    val type: TableType

    /**
     * 是否为逻辑删除
     */
    val logicalDelete: Boolean

    /**
     * 继承表
     */
    val inheritTables: List<ReferenceTable>?
}

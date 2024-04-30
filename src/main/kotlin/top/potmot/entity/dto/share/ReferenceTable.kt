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
     * 继承表
     */
    val inheritTables: List<ReferenceTable>?
}

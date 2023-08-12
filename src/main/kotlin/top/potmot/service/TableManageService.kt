package top.potmot.service

import top.potmot.model.GenTableGroup
import java.util.*

interface TableManageService {
    fun createTableGroup(name: String, parentId: Long? = null): Optional<GenTableGroup>

    fun updateTableGroup(id: Long, name: String, parentId: Long? = null): GenTableGroup

    fun moveTables(ids: List<Long>, groupId: Long): Int

    fun deleteTableGroupsI(ids: List<Long>): Int
}

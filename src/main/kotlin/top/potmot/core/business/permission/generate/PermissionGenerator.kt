package top.potmot.core.business.permission.generate

import top.potmot.core.business.property.EntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.trimBlankLine

private const val allPermissionFile = "all-permissions"

object PermissionGenerator {
    fun generate(entities: Iterable<EntityBusiness>): List<GenerateFile> {
        val items = entities.map {
            GenerateFile(
                it,
                "sql/permission/${it.lowerName}.sql",
                buildString {
                    val permissions = it.permissionStrList

                    for (permission in permissions) {
                        appendLine("INSERT INTO sys_permission (name, created_by, created_time, modified_by, modified_time)")
                        appendLine("VALUES ('${permission}', 1, now(), 1, now());")
                    }

                    appendLines(
                        "INSERT INTO sys_role_sys_permission_mapping (role_id, permission_id)",
                        "SELECT 1, id FROM sys_permission ",
                        "WHERE sys_permission.name IN ",
                        "(${permissions.joinToString(", ") { permission -> "'$permission'" }});"
                    )
                }.trimBlankLine(),
                listOf(GenerateTag.BackEnd, GenerateTag.Permission)
            )
        }.sortedBy { it.path }

        val allPermissions = createGenerateFileByEntities(
            entities,
            "sql/permission/${allPermissionFile}.sql",
            items.joinToString("\n\n") { it.content },
            listOf(GenerateTag.BackEnd, GenerateTag.Permission)
        )

        return listOf(allPermissions) + items
    }
}
package top.potmot.core.business.permission.generate

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.trimBlankLine

private const val allPermissionFile = "all-permissions"

object PermissionGenerator {
    fun generate(entities: Iterable<EntityBusiness>): List<GenerateFile> {
        val items = entities.map {
            val permissions = it.permissionStrList
            val allPermissions = it.allPermissionStrList

            GenerateFile(
                it,
                "sql/permission/${it.lowerName}.sql",
                buildString {
                    if (allPermissions.isNotEmpty()) {
                        appendLines(
                            "DELETE FROM sys_role_sys_permission_mapping WHERE sys_permission_id IN (",
                            "    SELECT id FROM sys_permission",
                            "    WHERE name IN (${allPermissions.joinToString(", ") { permission -> "'$permission'" }})",
                            ");",
                            "",
                        )

                        appendLines(
                            "DELETE FROM sys_menu_sys_permission_mapping WHERE sys_permission_id IN (",
                            "    SELECT id FROM sys_permission",
                            "    WHERE name IN (${allPermissions.joinToString(", ") { permission -> "'$permission'" }})",
                            ");",
                            ""
                        )

                        appendLines(
                            "DELETE FROM sys_permission",
                            "WHERE name IN (${allPermissions.joinToString(", ") { permission -> "'$permission'" }});",
                            "",
                        )
                    }

                    if (permissions.isNotEmpty()) {
                        for (permission in permissions) {
                            appendLines(
                                "INSERT INTO sys_permission (name, created_by, created_time, modified_by, modified_time)",
                                "VALUES ('${permission}', 1, now(), 1, now());"
                            )
                        }

                        appendLines(
                            "",
                            "INSERT INTO sys_role_sys_permission_mapping (sys_role_id, sys_permission_id)",
                            "SELECT 1, id FROM sys_permission ",
                            "WHERE sys_permission.name IN (${permissions.joinToString(", ") { permission -> "'$permission'" }});"
                        )
                    }
                }.trimBlankLine(),
                listOf(GenerateTag.BackEnd, GenerateTag.Permission)
            )
        }.sortedBy { it.path }

        val allPermissions = createGenerateFileByEntities(
            entities,
            "sql/permission/${allPermissionFile}.sql",
            items.joinToString("\n\n\n") { it.content },
            listOf(GenerateTag.BackEnd, GenerateTag.Permission)
        )

        return listOf(allPermissions) + items
    }
}
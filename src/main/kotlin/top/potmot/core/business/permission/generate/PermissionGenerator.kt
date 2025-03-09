package top.potmot.core.business.permission.generate

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.trimBlankLine

private const val allPermissionFile = "all-permissions.sql"

object PermissionGenerator {
    private fun formatFilePath(entity: EntityBusiness): String =buildString {
        append("sql/permission/")
        if (!entity.subPackagePath.isNullOrBlank()) {
            append("${entity.subPackagePath.replace(".", "/")}/")
        }
        append("${entity.lowerName}.sql")
    }

    fun generate(entities: Iterable<EntityBusiness>): List<GenerateFile> {
        val items = entities.map {
            val permissions = it.permissionStrList
            val allPermissions = it.allPermissionStrList

            GenerateFile(
                it,
                formatFilePath(it),
                buildString {
                    if (allPermissions.isNotEmpty()) {
                        appendBlock(
                            """
DELETE FROM sys_role_sys_permission_mapping WHERE sys_permission_id IN (
    SELECT id FROM sys_permission
    WHERE name IN (${allPermissions.joinToString(", ") { permission -> "'$permission'" }})
);

DELETE FROM sys_menu_sys_permission_mapping WHERE sys_permission_id IN (
    SELECT id FROM sys_permission
    WHERE name IN (${allPermissions.joinToString(", ") { permission -> "'$permission'" }})
);

DELETE FROM sys_permission
WHERE name IN (${allPermissions.joinToString(", ") { permission -> "'$permission'" }});
"""
                        )
                    }

                    if (permissions.isNotEmpty()) {
                        for (permission in permissions) {
                            appendLines(
                                "INSERT INTO sys_permission (name, created_by, created_time, modified_by, modified_time)",
                                "VALUES ('${permission}', 1, now(), 1, now());"
                            )
                        }

                        appendBlock(
                            """
INSERT INTO sys_role_sys_permission_mapping (sys_role_id, sys_permission_id)
SELECT 1, id FROM sys_permission 
WHERE sys_permission.name IN (${permissions.joinToString(", ") { permission -> "'$permission'" }});
"""
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
package top.potmot.core.business.permission.generate

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.collection.forEachJoinDo
import top.potmot.utils.string.appendBlock
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.trimBlankLine

private const val allPermissionFile = "all-permissions.sql"

object PermissionGenerator {
    private fun formatFilePath(entity: EntityBusiness): String = buildString {
        append("sql/permission/")
        if (!entity.subPackagePath.isNullOrBlank()) {
            append("${entity.subPackagePath.replace(".", "/")}/")
        }
        append("${entity.lowerName}.sql")
    }

    fun generate(
        entities: Iterable<EntityBusiness>,
    ): List<GenerateFile> {
        val subGroups = entities.mapNotNull { it.subGroup }.distinctBy { it.id }

        val subGroupFiles = subGroups.map {
            val role = "group__${it.name}_manager"
            val menuPermission = "${it.name}:menu"

            GenerateFile(
                path = "sql/permission/${it.subPackagePath.replace(".", "/")}/group-${it.name}.sql",
                content = buildString {
                    appendBlock(
                        """
DELETE FROM sys_user_sys_role_mapping WHERE sys_role_id IN (
    SELECT id FROM sys_role
    WHERE name = '${role}'
);

DELETE FROM sys_role_sys_permission_mapping WHERE sys_role_id IN (
    SELECT id FROM sys_role
    WHERE name = '${role}'
);

DELETE FROM sys_menu_sys_permission_mapping WHERE sys_permission_id IN (
    SELECT id FROM sys_permission
    WHERE name IN ('${menuPermission}')
);

DELETE FROM sys_permission
WHERE name IN ('${menuPermission}');

DELETE FROM sys_role
WHERE name = '${role}';

INSERT INTO sys_role (name, created_by, created_time, modified_by, modified_time)
VALUES ('${role}', 1, now(), 1, now());

INSERT INTO sys_user_sys_role_mapping (sys_role_id, sys_user_id)
VALUES ((SELECT id FROM sys_role WHERE name = '${role}' LIMIT 1), 1);
"""
                    )

                    appendLines(
                        "INSERT INTO sys_permission (name, created_by, created_time, modified_by, modified_time)",
                        "VALUES ('${menuPermission}', 1, now(), 1, now());",
                    )

                    appendBlock(
                        """
INSERT INTO sys_role_sys_permission_mapping (sys_role_id, sys_permission_id)
SELECT (SELECT id FROM sys_role WHERE name = '${role}' LIMIT 1), id FROM sys_permission 
WHERE sys_permission.name IN ('${menuPermission}');
"""
                    )
                },
                listOf(GenerateTag.BackEnd, GenerateTag.Permission)
            )
        }

        val entityFiles = entities.map {
            val role = it.permissions.role
            val subGroupRole by lazy {
                "group__${it.subGroup!!.name}_manager"
            }
            val permissions = it.permissionStrList
            val allPermissions = it.allPermissionStrList

            GenerateFile(
                it,
                formatFilePath(it),
                buildString {
                    appendBlock(
                        """
DELETE FROM sys_user_sys_role_mapping WHERE sys_role_id IN (
    SELECT id FROM sys_role
    WHERE name = '${role}'
);

DELETE FROM sys_role_sys_permission_mapping WHERE sys_role_id IN (
    SELECT id FROM sys_role
    WHERE name = '${role}'
);

DELETE FROM sys_role
WHERE name = '${role}';
"""
                    )

                    if (allPermissions.isNotEmpty()) {
                        appendBlock(
                            """
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
                        appendLines(
                            "INSERT INTO sys_role (name, created_by, created_time, modified_by, modified_time)",
                            "VALUES ('${role}', 1, now(), 1, now());",
                            "",
                            "INSERT INTO sys_user_sys_role_mapping (sys_role_id, sys_user_id)",
                            "VALUES ((SELECT id FROM sys_role WHERE name = '${role}' LIMIT 1), 1);",
                            ""
                        )

                        for (permission in permissions) {
                            appendLines(
                                "INSERT INTO sys_permission (name, created_by, created_time, modified_by, modified_time)",
                                "VALUES ('${permission}', 1, now(), 1, now());"
                            )
                        }

                        appendBlock(
                            """
INSERT INTO sys_role_sys_permission_mapping (sys_role_id, sys_permission_id)
SELECT (SELECT id FROM sys_role WHERE name = '${role}' LIMIT 1), id FROM sys_permission 
WHERE sys_permission.name IN (${permissions.joinToString(", ") { permission -> "'$permission'" }});
"""
                        )

                        if (it.subGroup != null) {
                            appendBlock(
                                """
INSERT INTO sys_role_sys_permission_mapping (sys_role_id, sys_permission_id)
SELECT (SELECT id FROM sys_role WHERE name = '${subGroupRole}' LIMIT 1), id FROM sys_permission 
WHERE sys_permission.name IN (${permissions.joinToString(", ") { permission -> "'$permission'" }});
"""
                            )
                        }
                    }
                }.trimBlankLine(),
                listOf(GenerateTag.BackEnd, GenerateTag.Permission)
            )
        }.sortedBy { it.path }

        val allPermissions = createGenerateFileByEntities(
            entities,
            "sql/permission/${allPermissionFile}.sql",
            buildString {
                subGroupFiles.forEachJoinDo({
                    appendLine()
                }) {
                    append(it.content)
                }

                appendLine()

                entityFiles.forEachJoinDo({
                    appendLine()
                }) {
                    append(it.content)
                }
            },
            listOf(GenerateTag.BackEnd, GenerateTag.Permission)
        )

        return listOf(allPermissions) + subGroupFiles + entityFiles
    }
}
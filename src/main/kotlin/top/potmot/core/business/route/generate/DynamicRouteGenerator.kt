package top.potmot.core.business.route.generate

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.collection.forEachJoinDo
import top.potmot.utils.string.appendBlock

object DynamicRouteGenerator {
    private fun formatFilePath(entity: EntityBusiness): String = buildString {
        append("sql/menu/")
        if (!entity.subPackagePath.isNullOrBlank()) {
            append("${entity.subPackagePath.replace(".", "/")}/")
        }
        append("${entity.lowerName}.sql")
    }


    fun generate(
        entities: Iterable<RootEntityBusiness>
    ): List<GenerateFile> {
        val subGroups = entities.mapNotNull { it.subGroup }.distinctBy { it.id }

        val subGroupFiles = subGroups.map {
            GenerateFile(
                path = "sql/menu/${it.subPackagePath.replace(".", "/")}/group-${it.name}.sql",
                content = buildString {
                    appendBlock(
                        """
DELETE FROM sys_menu_sys_permission_mapping WHERE sys_menu_id IN (
    SELECT id FROM sys_menu WHERE name = '${it.name}'
);

DELETE FROM sys_menu WHERE name = '${it.name}';

INSERT INTO sys_menu 
(parent_id, name, path, icon, label, component, order_key, created_by, created_time, modified_by, modified_time)
VALUES (NULL, '${it.name}', '/${it.name}', 'List', '${it.comment}', NULL, 1, 1, now(), 1, now());
""".trim()
                    )
                },
                listOf(GenerateTag.BackEnd, GenerateTag.Route)
            )
        }

        val entityFiles = entities.map {
            val page = it.components.page

            GenerateFile(
                it,
                formatFilePath(it),
                buildString {
                    appendBlock(
                        """
DELETE FROM sys_menu_sys_permission_mapping WHERE sys_menu_id IN (
    SELECT id FROM sys_menu WHERE name = '${page.name}'
);

DELETE FROM sys_menu WHERE name = '${page.name}';
""".trim()
                    )

                    if (it.hasPage) {
                        val parentIdValue = if (it.subGroup != null) {
                            "(SELECT id FROM sys_menu WHERE name = '${it.subGroup.name}' LIMIT 1)"
                        } else {
                            "NULL"
                        }

                        appendBlock(
                            """
INSERT INTO sys_menu 
(parent_id, name, path, icon, label, component, order_key, created_by, created_time, modified_by, modified_time)
VALUES ($parentIdValue, '${page.name}', '/${page.name}', 'List', '${it.comment}', '${page.fullPath}', 1, 1, now(), 1, now());

INSERT INTO sys_menu_sys_permission_mapping (sys_permission_id, sys_menu_id)
SELECT sys_permission.id, sys_menu.id FROM sys_permission, sys_menu 
WHERE sys_permission.name = '${it.permissions.menu}' AND sys_menu.name = '${page.name}';
""".trimEnd()
                        )
                    }
                },
                listOf(GenerateTag.BackEnd, GenerateTag.Route)
            )
        }.sortedBy { it.path }

        val allDynamicRoutes = createGenerateFileByEntities(
            entities,
            "sql/menu/all-menus.sql",
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
            listOf(GenerateTag.BackEnd, GenerateTag.Route)
        )

        return listOf(allDynamicRoutes) + subGroupFiles + entityFiles
    }
}
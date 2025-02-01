package top.potmot.core.business.route.generate

import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.trimBlankLine

object DynamicRouteGenerator {
    fun generate(entities: Iterable<RootEntityBusiness>): List<GenerateFile> {
        val items = entities.filter { it.hasPage }.map {
            val page = it.components.page

            GenerateFile(
                it,
                "sql/menu/${it.lowerName}.sql",
                """
DELETE FROM sys_menu_sys_permission_mapping WHERE sys_menu_id IN (
    SELECT id FROM sys_menu WHERE name = '${page.name}'
);

DELETE FROM sys_menu WHERE name = '${page.name}';

INSERT INTO sys_menu 
(parent_id, name, path, icon, label, component, order_key, created_by, created_time, modified_by, modified_time)
VALUES (NULL, '${page.name}', '/${page.name}', 'List', '${it.comment}', '${page.fullPath}', 1, 1, now(), 1, now());

INSERT INTO sys_menu_sys_permission_mapping (sys_permission_id, sys_menu_id)
SELECT sys_permission.id, sys_menu.id FROM sys_permission, sys_menu 
WHERE sys_permission.name = '${it.permissions.menu}' AND sys_menu.name = '${page.name}';
                """.trimBlankLine(),
                listOf(GenerateTag.BackEnd, GenerateTag.Route)
            )
        }.sortedBy { it.path }

        val allDynamicRoutes = createGenerateFileByEntities(
            entities,
            "sql/menu/all-menus.sql",
            items.joinToString("\n\n") { it.content },
            listOf(GenerateTag.BackEnd, GenerateTag.Route)
        )

        return listOf(allDynamicRoutes) + items
    }
}
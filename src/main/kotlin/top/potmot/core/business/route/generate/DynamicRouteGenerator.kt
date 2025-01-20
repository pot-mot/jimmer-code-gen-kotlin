package top.potmot.core.business.route.generate

import top.potmot.core.business.meta.EntityBusiness
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.trimBlankLine

object DynamicRouteGenerator {
    fun generate(entities: Iterable<EntityBusiness>): List<GenerateFile> {
        val items = entities.filter { it.hasPage }.map {
            val lowerName = it.name.lowercase()
            val dir = it.dir
            val page = it.components.page

            GenerateFile(
                it,
                "sql/menu/${lowerName}.sql",
                """
INSERT INTO sys_menu 
(parent_id, name, path, icon, label, component, order_key, created_by, created_time, modified_by, modified_time)
VALUES (NULL, '${page}', '/${page}', 'List', '${it.comment}管理', 'pages/${dir}/${page}.vue', 1, 1, now(), 1, now());

INSERT INTO sys_permission_sys_menu_mapping (permission_id, menu_id)
SELECT sys_permission.id, sys_menu.id FROM sys_permission, sys_menu 
WHERE sys_permission.name = '${it.permissions.menu}' AND sys_menu.name = '${page}';
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
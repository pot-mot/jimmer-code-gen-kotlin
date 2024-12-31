package top.potmot.core.business.route.generate

import top.potmot.core.business.utils.mark.components
import top.potmot.core.business.utils.mark.dir
import top.potmot.core.business.utils.mark.permissions
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.trimBlankLine

object DynamicRouteGenerator {
    fun generate(entities: Iterable<GenEntityBusinessView>): List<GenerateFile> {
        val items = entities.filter { it.hasPage }.map {
            val lowerName = it.name.lowercase()
            val dir = it.dir
            val page = it.components.page

            GenerateFile(
                it,
                "sql/menu/${lowerName}.sql",
                """
INSERT INTO SYS_MENU 
(PARENT_ID, NAME, PATH, ICON, LABEL, COMPONENT, ORDER_KEY, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME) 
VALUES (NULL, '${page}', '/${page}', 'List', '${it.comment}管理', 'pages/${dir}/${page}.vue', 1, 1, now(), 1, now());

INSERT INTO SYS_PERMISSION_SYS_MENU_MAPPING
SELECT SYS_PERMISSION.ID, SYS_MENU.ID FROM SYS_PERMISSION, SYS_MENU
WHERE SYS_PERMISSION.NAME = '${it.permissions.menu}' AND SYS_MENU.NAME = '${page}';
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
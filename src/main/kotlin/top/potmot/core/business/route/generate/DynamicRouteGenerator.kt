package top.potmot.core.business.route.generate

import top.potmot.core.business.utils.componentNames
import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.utils.string.trimBlankLine

object DynamicRouteGenerator {
    fun generate(entities: List<GenerateEntity>): List<Pair<String, String>> {
        val items = entities.map {
            val lowerName = it.name.replaceFirstChar { c -> c.lowercase() }
            val page = it.componentNames.page

            "menu/${lowerName}.sql" to
                    """
INSERT INTO SYS_MENU 
(PARENT_ID, NAME, PATH, ICON, LABEL, COMPONENT, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME) 
VALUES (NULL, '${page}', '/${page}', 'List', '${it.comment}管理', '${page}.vue', 1, now(), 1, now());

INSERT INTO SYS_PERMISSION_SYS_MENU_MAPPING
SELECT SYS_PERMISSION.ID, SYS_MENU.ID FROM SYS_PERMISSION, SYS_MENU
WHERE SYS_PERMISSION.NAME = '${lowerName}:menu' AND SYS_MENU.NAME = '${page}';
                    """.trimBlankLine()
        }.distinct().sortedBy { it.first }

        val all = "menu/all-menus.sql" to items.map { it.second }.joinToString("\n\n")

        return listOf(all) + items
    }
}
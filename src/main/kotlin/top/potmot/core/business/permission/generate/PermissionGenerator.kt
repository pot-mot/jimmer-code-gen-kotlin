package top.potmot.core.business.permission.generate

import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.utils.string.trimBlankLine

object PermissionGenerator {
    fun generate(entities: List<GenerateEntity>): List<Pair<String, String>> {
        val items = entities.map {
            val lowerName = it.name.replaceFirstChar { c -> c.lowercase() }

            "permission/${lowerName}.sql" to
                    """
INSERT INTO SYS_PERMISSION (NAME, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)
VALUES ('${lowerName}:get', 1, now(), 1, now());
INSERT INTO SYS_PERMISSION (NAME, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)
VALUES ('${lowerName}:list', 1, now(), 1, now());
INSERT INTO SYS_PERMISSION (NAME, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)
VALUES ('${lowerName}:insert', 1, now(), 1, now());
INSERT INTO SYS_PERMISSION (NAME, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)
VALUES ('${lowerName}:update', 1, now(), 1, now());
INSERT INTO SYS_PERMISSION (NAME, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)
VALUES ('${lowerName}:delete', 1, now(), 1, now());
INSERT INTO SYS_PERMISSION (NAME, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)
VALUES ('${lowerName}:menu', 1, now(), 1, now());

INSERT INTO SYS_ROLE_SYS_PERMISSION_MAPPING 
SELECT 1, ID FROM SYS_PERMISSION 
WHERE SYS_PERMISSION.NAME IN 
    ('${lowerName}:get', '${lowerName}:list', '${lowerName}:insert', '${lowerName}:update', '${lowerName}:delete', '${lowerName}:menu');
                    """.trimBlankLine()
        }.distinct().sortedBy { it.first }

        val all = "permission/all-permissions.sql" to items.map { it.second }.joinToString("\n\n")

        return listOf(all) + items
    }
}
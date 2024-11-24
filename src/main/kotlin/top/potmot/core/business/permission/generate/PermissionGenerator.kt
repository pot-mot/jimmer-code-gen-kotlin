package top.potmot.core.business.permission.generate

import top.potmot.core.business.utils.lowerName
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.entity.dto.share.GenerateEntity
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.trimBlankLine

private const val allPermissionFile = "all-permissions"

object PermissionGenerator {
    fun generate(entities: Iterable<GenerateEntity>): List<GenerateFile> {
        val items = entities.map {
            val lowerName = it.lowerName

            GenerateFile(
                it,
                "sql/permission/${lowerName}.sql",
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
INSERT INTO SYS_PERMISSION (NAME, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)
VALUES ('${lowerName}:select', 1, now(), 1, now());

INSERT INTO SYS_ROLE_SYS_PERMISSION_MAPPING 
SELECT 1, ID FROM SYS_PERMISSION 
WHERE SYS_PERMISSION.NAME IN 
    ('${lowerName}:get', '${lowerName}:list', '${lowerName}:insert', '${lowerName}:update', '${lowerName}:delete', '${lowerName}:menu');
                """.trimBlankLine(),
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
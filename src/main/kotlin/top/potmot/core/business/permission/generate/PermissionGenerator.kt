package top.potmot.core.business.permission.generate

import top.potmot.core.business.utils.mark.lowerName
import top.potmot.core.business.utils.mark.permissionStrList
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenerateFile
import top.potmot.entity.dto.createGenerateFileByEntities
import top.potmot.enumeration.GenerateTag
import top.potmot.utils.string.appendLines
import top.potmot.utils.string.trimBlankLine

private const val allPermissionFile = "all-permissions"

object PermissionGenerator {
    fun generate(entities: Iterable<GenEntityBusinessView>): List<GenerateFile> {
        val items = entities.map {
            GenerateFile(
                it,
                "sql/permission/${it.lowerName}.sql",
                buildString {
                    val permissions = it.permissionStrList

                    for (permission in permissions) {
                        appendLine("INSERT INTO SYS_PERMISSION (NAME, CREATED_BY, CREATED_TIME, MODIFIED_BY, MODIFIED_TIME)")
                        appendLine("VALUES ('${permission}', 1, now(), 1, now());")
                    }

                    appendLines(
                        "INSERT INTO SYS_ROLE_SYS_PERMISSION_MAPPING ",
                        "SELECT 1, ID FROM SYS_PERMISSION ",
                        "WHERE SYS_PERMISSION.NAME IN ",
                        "(${permissions.joinToString(", ") { permission -> "'$permission'" }});"
                    )
                }.trimBlankLine(),
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
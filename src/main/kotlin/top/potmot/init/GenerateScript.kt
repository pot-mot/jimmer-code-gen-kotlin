package top.potmot.init

import top.potmot.entity.database.DatabaseTypeOrAny
import top.potmot.entity.model.JvmLanguageOrAny
import top.potmot.entity.script.ScriptType
import top.potmot.entity.script.dto.GenerateScriptInsertInput

val initGenerateScripts = listOf(
    GenerateScriptInsertInput(
        name = "javaEmbeddableTypeGenerator",
        type = ScriptType.EmbeddableTypeGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.ANY,
        jvmLanguage = JvmLanguageOrAny.JAVA,
        scriptContent = $$"""
(
    embeddableType: DeepReadonly<EmbeddableTypeWithOverrideProperties>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const builder = context.createJvmFileBuilder({
        groupId: embeddableType.groupId,
        subPackagePath: embeddableType.subPackagePath,
    })

    builder.addImports("org.babyfish.jimmer.sql.Embeddable")

    for (const property of embeddableType.properties) {
        builder.pushProperty(property, {type: "EmbeddableType", embeddableType})
    }

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: " {", end: "}"}
    })

    const packagePath = builder.getPackagePath()
    let packageDirPath = ""
    if (packagePath.length > 0) {
        packageDirPath = packagePath.replace(/\./g, "/") + "/"
        template.appendLine(`package ${packagePath};`)
        template.appendLine()
    }

    const imports = [...builder.getImportSet()].sort()
    if (imports.length > 0) {
        for (const importItem of imports) {
            template.appendLine(`import ${importItem};`)
        }
        template.appendLine()
    }

    if (embeddableType.comment.length > 0) {
        template.appendLine(`/**`)
        for (const line of embeddableType.comment.split("\n")) {
            template.appendLine(` * ${line}`)
        }
        template.appendLine(` */`)
    }

    template.appendLine(`@Embeddable`)
    template.append(`public interface ${embeddableType.name}`)

    template.startScope()
    for (const property of builder.getProperties()) {
        template.appendLine()

        if (property.comment.length > 0) {
            template.appendLine(`/**`)
            for (const line of property.comment.split("\n")) {
                template.appendLine(` * ${line}`)
            }
            template.appendLine(` */`)
        }

        if (property.annotations.length > 0) {
            for (const annotation of property.annotations) {
                template.appendBlock(annotation)
            }
        }

        if (property.body) {
            template.append(`default ${property.type} ${property.name}()`)
            template.startScope()
            template.appendBlock(property.body)
            template.endScope()
        } else {
            template.appendLine(`${property.type} ${property.name}();`)
        }
    }
    template.endScope()

    result[`/java/${packageDirPath}${embeddableType.name}.java`] = template.build({cleanEmptyLineIndent: true})

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "javaEntityGenerator",
        type = ScriptType.EntityGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.ANY,
        jvmLanguage = JvmLanguageOrAny.JAVA,
        scriptContent = $$"""
(
    entity: DeepReadonly<EntityWithInheritInfo>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const builder = context.createJvmFileBuilder({
        groupId: entity.groupId,
        subPackagePath: entity.subPackagePath,
    })

    builder.addImports("org.babyfish.jimmer.sql.Entity")
    builder.addImports("org.babyfish.jimmer.sql.Table")
    builder.addImports(entity.extraImports)

    for (const mappedSuperClassId of entity.extendsIds) {
        builder.requireMappedSuperClass(mappedSuperClassId)
    }

    for (const property of entity.properties) {
        builder.pushProperty(property, {type: "Entity", entity})
    }

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: " {", end: "}"}
    })

    const packagePath = builder.getPackagePath()
    let packageDirPath = ""
    if (packagePath.length > 0) {
        packageDirPath = packagePath.replace(/\./g, "/") + "/"

        template.appendLine(`package ${builder.getPackagePath()};`)
        template.appendLine()
    }

    const imports = [...builder.getImportSet()].sort()
    if (imports.length > 0) {
        for (const importItem of imports) {
            template.appendLine(`import ${importItem};`)
        }
        template.appendLine()
    }

    if (entity.comment.length > 0) {
        template.appendLine(`/**`)
        for (const line of entity.comment.split("\n")) {
            template.appendLine(` * ${line}`)
        }
        template.appendLine(` */`)
    }
    template.appendLine(`@Entity`)
    template.appendLine(`@Table(name = "${entity.tableName}")`)
    for (const annotation of entity.extraAnnotations) {
        template.appendBlock(annotation)
    }
    template.append(`public interface ${entity.name}`)
    if (entity.directExtends.size > 1) {
        template.append(` extends`)
        for (const extend of entity.directExtends) {
            template.append(`\n        ${extend.name}`)
        }
    } else if (entity.directExtends.size === 1) {
        template.append(` extends ${[...entity.directExtends].map(it => it.name).join(", ")}`)
    }

    template.startScope()
    for (const property of builder.getProperties()) {
        template.appendLine()

        if (property.comment.length > 0) {
            template.appendLine(`/**`)
            for (const line of property.comment.split("\n")) {
                template.appendLine(` * ${line}`)
            }
            template.appendLine(` */`)
        }
        if (property.annotations.length > 0) {
            for (const annotation of property.annotations) {
                template.appendBlock(annotation)
            }
        }
        if (property.body) {
            template.append(`default ${property.type} ${property.name}()`)
            template.startScope()
            template.appendBlock(property.body)
            template.endScope()
        } else {
            template.appendLine(`${property.type} ${property.name}();`)
        }
    }
    template.endScope()

    result[`/java/${packageDirPath}${entity.name}.java`] = template.build({cleanEmptyLineIndent: true})

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "javaEnumerationGenerator",
        type = ScriptType.EnumerationGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.ANY,
        jvmLanguage = JvmLanguageOrAny.JAVA,
        scriptContent = $$"""
(
    enumeration: DeepReadonly<Enumeration>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const builder = context.createJvmFileBuilder({
        groupId: enumeration.groupId,
        subPackagePath: enumeration.subPackagePath,
    })

    builder.addImports("org.babyfish.jimmer.sql.EnumType")
    builder.addImports("org.babyfish.jimmer.sql.EnumItem")

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: " {", end: "}"}
    })

    const packagePath = builder.getPackagePath()
    let packageDirPath = ""
    if (packagePath.length > 0) {
        packageDirPath = packagePath.replace(/\./g, "/") + "/"
        template.appendLine(`package ${packagePath};`)
        template.appendLine()
    }

    const imports = [...builder.getImportSet()].sort()
    if (imports.length > 0) {
        for (const importItem of imports) {
            template.appendLine(`import ${importItem};`)
        }
        template.appendLine()
    }

    if (enumeration.comment.length > 0) {
        template.appendLine(`/**`)
        for (const line of enumeration.comment.split("\n")) {
            template.appendLine(` * ${line}`)
        }
        template.appendLine(` */`)
    }

    const isName = (enumeration.strategy === 'NAME')
    if (isName) {
        template.appendLine("@EnumType(EnumType.Strategy.NAME)")
    } else {
        template.appendLine("@EnumType(EnumType.Strategy.ORDINAL)")
    }
    if (enumeration.extraAnnotations.length > 0) {
        for (const annotation of enumeration.extraAnnotations) {
            template.appendBlock(annotation)
        }
    }
    template.append(`public enum ${enumeration.name}`)

    template.startScope()
    for (const item of enumeration.items) {
        template.appendLine()

        if (item.comment.length > 0) {
            template.appendLine(`/**`)
            for (const line of item.comment.split("\n")) {
                template.appendLine(` * ${line}`)
            }
            template.appendLine(` */`)
        }
        if (isName) {
            template.appendLine(`@EnumItem(name = "${item.name}")`)
        } else {
            template.appendLine(`@EnumItem(ordinal = ${item.ordinal})`)
        }
        if (item.extraAnnotations.length > 0) {
            for (const annotation of item.extraAnnotations) {
                template.appendBlock(annotation)
            }
        }
        template.appendLine(`${item.name},`)
    }
    template.endScope()

    result[`/java/${packageDirPath}${enumeration.name}.java`] = template.build({cleanEmptyLineIndent: true})

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "javaMappedSuperClassGenerator",
        type = ScriptType.MappedSuperClassGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.ANY,
        jvmLanguage = JvmLanguageOrAny.JAVA,
        scriptContent = $$"""
(
    mappedSuperClass: DeepReadonly<MappedSuperClassWithInheritInfo>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const builder = context.createJvmFileBuilder({
        groupId: mappedSuperClass.groupId,
        subPackagePath: mappedSuperClass.subPackagePath,
    })

    builder.addImports("org.babyfish.jimmer.sql.MappedSuperclass")

    for (const mappedSuperClassId of mappedSuperClass.extendsIds) {
        builder.requireMappedSuperClass(mappedSuperClassId)
    }

    for (const property of mappedSuperClass.properties) {
        builder.pushProperty(property, {type: "MappedSuperClass", mappedSuperClass})
    }

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: " {", end: "}"}
    })

    const packagePath = builder.getPackagePath()
    let packageDirPath = ""
    if (packagePath.length > 0) {
        packageDirPath = packagePath.replace(/\./g, "/") + "/"
        template.appendLine(`package ${packagePath};`)
        template.appendLine()
    }

    const imports = [...builder.getImportSet()].sort()
    if (imports.length > 0) {
        for (const importItem of imports) {
            template.appendLine(`import ${importItem};`)
        }
        template.appendLine()
    }

    if (mappedSuperClass.comment.length > 0) {
        template.appendLine(`/**`)
        for (const line of mappedSuperClass.comment.split("\n")) {
            template.appendLine(` * ${line}`)
        }
        template.appendLine(` */`)
    }

    template.appendLine(`@MappedSuperclass`)
    template.append(`public interface ${mappedSuperClass.name}`)

    if (mappedSuperClass.directExtends.size > 1) {
        template.append(` extends`)
        for (const extend of mappedSuperClass.directExtends) {
            template.append(`\n        ${extend.name}`)
        }
    } else if (mappedSuperClass.directExtends.size === 1) {
        template.append(` extends ${[...mappedSuperClass.directExtends].map(it => it.name).join(", ")}`)
    }

    template.startScope()
    for (const property of builder.getProperties()) {
        template.appendLine()

        if (property.comment.length > 0) {
            template.appendLine(`/**`)
            for (const line of property.comment.split("\n")) {
                template.appendLine(` * ${line}`)
            }
            template.appendLine(` */`)
        }

        if (property.annotations.length > 0) {
            for (const annotation of property.annotations) {
                template.appendBlock(annotation)
            }
        }

        if (property.body) {
            template.append(`default ${property.type} ${property.name}()`)
            template.startScope()
            template.appendBlock(property.body)
            template.endScope()
        } else {
            template.appendLine(`${property.type} ${property.name}();`)
        }
    }
    template.endScope()

    result[`/java/${packageDirPath}${mappedSuperClass.name}.java`] = template.build({cleanEmptyLineIndent: true})

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "kotlinEmbeddableTypeGenerator",
        type = ScriptType.EmbeddableTypeGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.ANY,
        jvmLanguage = JvmLanguageOrAny.KOTLIN,
        scriptContent = $$"""
(
    embeddableType: DeepReadonly<EmbeddableTypeWithOverrideProperties>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const builder = context.createJvmFileBuilder({
        groupId: embeddableType.groupId,
        subPackagePath: embeddableType.subPackagePath,
    })

    builder.addImports("org.babyfish.jimmer.sql.Embeddable")

    for (const property of embeddableType.properties) {
        builder.pushProperty(property, {type: "EmbeddableType", embeddableType})
    }

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: " {", end: "}"}
    })

    const packagePath = builder.getPackagePath()
    let packageDirPath = ""
    if (packagePath.length > 0) {
        packageDirPath = packagePath.replace(/\./g, "/") + "/"

        template.appendLine(`package ${builder.getPackagePath()}`)
        template.appendLine()
    }

    const imports = [...builder.getImportSet()].sort()
    if (imports.length > 0) {
        for (const importItem of imports) {
            template.appendLine(`import ${importItem}`)
        }
        template.appendLine()
    }

    if (embeddableType.comment?.length > 0) {
        template.appendLine(`/**`)
        for (const line of embeddableType.comment.split("\n")) {
            template.appendLine(` * ${line}`)
        }
        template.appendLine(` */`)
    }

    template.appendLine("@Embeddable")
    template.append(`interface ${embeddableType.name}`)
    template.startScope()

    for (const property of builder.getProperties()) {
        template.appendLine()

        if (property.comment.length > 0) {
            template.appendLine(`/**`)
            for (const line of property.comment.split("\n")) {
                template.appendLine(` * ${line}`)
            }
            template.appendLine(` */`)
        }

        if (property.annotations.length > 0) {
            for (const annotation of property.annotations) {
                template.appendBlock(annotation)
            }
        }

        template.appendLine(`val ${property.name}: ${property.type}${property.nullable ? "?" : ""}`)
    }

    template.endScope()

    result[`/kotlin/${packageDirPath}${embeddableType.name}.kt`] = template.build({cleanEmptyLineIndent: true})

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "kotlinEntityGenerator",
        type = ScriptType.EntityGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.ANY,
        jvmLanguage = JvmLanguageOrAny.KOTLIN,
        scriptContent = $$"""
(
    entity: DeepReadonly<EntityWithInheritInfo>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const builder = context.createJvmFileBuilder({
        groupId: entity.groupId,
        subPackagePath: entity.subPackagePath,
    })

    builder.addImports("org.babyfish.jimmer.sql.Entity")
    builder.addImports("org.babyfish.jimmer.sql.Table")
    builder.addImports(entity.extraImports)

    for (const mappedSuperClassId of entity.extendsIds) {
        builder.requireMappedSuperClass(mappedSuperClassId)
    }

    for (const property of entity.properties) {
        builder.pushProperty(property, {type: "Entity", entity})
    }

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: " {", end: "}"}
    })

    const packagePath = builder.getPackagePath()
    let packageDirPath = ""
    if (packagePath.length > 0) {
        packageDirPath = packagePath.replace(/\./g, "/") + "/"

        template.appendLine(`package ${builder.getPackagePath()}`)
        template.appendLine()
    }

    const imports = [...builder.getImportSet()].sort()
    if (imports.length > 0) {
        for (const importItem of imports) {
            template.appendLine(`import ${importItem}`)
        }
        template.appendLine()
    }

    if (entity.comment.length > 0) {
        template.appendLine(`/**`)
        for (const line of entity.comment.split("\n")) {
            template.appendLine(` * ${line}`)
        }
        template.appendLine(` */`)
    }

    template.appendLine("@Entity")
    template.appendLine(`@Table(name = "${entity.tableName}")`)

    for (const annotation of entity.extraAnnotations) {
        template.appendBlock(annotation)
    }

    template.append(`interface ${entity.name}`)

    if (entity.directExtends.size > 1) {
        template.append(` :`)
        for (const extend of entity.directExtends) {
            template.append(`\n    ${extend.name}`)
        }
    } else if (entity.directExtends.size === 1) {
        template.append(` : ${[...entity.directExtends].map(it => it.name).join(", ")}`)
    }

    template.startScope()
    for (const property of builder.getProperties()) {
        template.appendLine()

        if (property.comment.length > 0) {
            template.appendLine(`/**`)
            for (const line of property.comment.split("\n")) {
                template.appendLine(` * ${line}`)
            }
            template.appendLine(` */`)
        }

        if (property.annotations.length > 0) {
            for (const annotation of property.annotations) {
                template.appendBlock(annotation)
            }
        }

        template.appendLine(`val ${property.name}: ${property.type}${property.nullable ? "?" : ""}`)
        if (property.body) {
            template.startScope("get() {")
            template.appendBlock(property.body)
            template.endScope()
        }
    }
    template.endScope()

    result[`/kotlin/${packageDirPath}${entity.name}.kt`] = template.build({cleanEmptyLineIndent: true})

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "kotlinEnumerationGenerator",
        type = ScriptType.EnumerationGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.ANY,
        jvmLanguage = JvmLanguageOrAny.KOTLIN,
        scriptContent = $$"""
(
    enumeration: DeepReadonly<Enumeration>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const builder = context.createJvmFileBuilder({
        groupId: enumeration.groupId,
        subPackagePath: enumeration.subPackagePath,
    })

    builder.addImports("org.babyfish.jimmer.sql.EnumType")
    builder.addImports("org.babyfish.jimmer.sql.EnumItem")

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: " {", end: "}"}
    })

    const packagePath = builder.getPackagePath()
    let packageDirPath = ""
    if (packagePath.length > 0) {
        packageDirPath = packagePath.replace(/\./g, "/") + "/"

        template.appendLine(`package ${builder.getPackagePath()}`)
        template.appendLine()
    }

    const imports = [...builder.getImportSet()].sort()
    if (imports.length > 0) {
        for (const importItem of imports) {
            template.appendLine(`import ${importItem}`)
        }
        template.appendLine()
    }

    if (enumeration.comment?.length > 0) {
        template.appendLine(`/**`)
        for (const line of enumeration.comment.split("\n")) {
            template.appendLine(` * ${line}`)
        }
        template.appendLine(` */`)
    }

    const isName = (enumeration.strategy === 'NAME')
    if (isName) {
        template.appendLine("@EnumType(EnumType.Strategy.NAME)")
    } else {
        template.appendLine("@EnumType(EnumType.Strategy.ORDINAL)")
    }
    if (enumeration.extraAnnotations.length > 0) {
        for (const annotation of enumeration.extraAnnotations) {
            template.appendBlock(annotation)
        }
    }
    template.append(`enum class ${enumeration.name}`)

    template.startScope()
    for (const item of enumeration.items) {
        template.appendLine()

        if (item.comment.length > 0) {
            template.appendLine(`/**`)
            for (const line of item.comment.split("\n")) {
                template.appendLine(` * ${line}`)
            }
            template.appendLine(` */`)
        }
        if (isName) {
            template.appendLine(`@EnumItem(name = "${item.name}")`)
        } else {
            template.appendLine(`@EnumItem(ordinal = ${item.ordinal})`)
        }
        if (item.extraAnnotations.length > 0) {
            for (const annotation of item.extraAnnotations) {
                template.appendBlock(annotation)
            }
        }
        template.appendLine(`${item.name},`)
    }
    template.endScope()

    result[`/kotlin/${packageDirPath}${enumeration.name}.kt`] = template.build({cleanEmptyLineIndent: true})

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "kotlinMappedSuperclassGenerator",
        type = ScriptType.MappedSuperClassGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.ANY,
        jvmLanguage = JvmLanguageOrAny.KOTLIN,
        scriptContent = $$"""
(
    mappedSuperClass: DeepReadonly<MappedSuperClassWithInheritInfo>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const builder = context.createJvmFileBuilder({
        groupId: mappedSuperClass.groupId,
        subPackagePath: mappedSuperClass.subPackagePath,
    })

    builder.addImports("org.babyfish.jimmer.sql.MappedSuperclass")

    for (const mappedSuperClassId of mappedSuperClass.extendsIds) {
        builder.requireMappedSuperClass(mappedSuperClassId)
    }

    for (const property of mappedSuperClass.properties) {
        builder.pushProperty(property, {type: "MappedSuperClass", mappedSuperClass})
    }

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: " {", end: "}"}
    })

    const packagePath = builder.getPackagePath()
    let packageDirPath = ""
    if (packagePath.length > 0) {
        packageDirPath = packagePath.replace(/\./g, "/") + "/"

        template.appendLine(`package ${builder.getPackagePath()}`)
        template.appendLine()
    }

    const imports = [...builder.getImportSet()].sort()
    if (imports.length > 0) {
        for (const importItem of imports) {
            template.appendLine(`import ${importItem}`)
        }
        template.appendLine()
    }

    if (mappedSuperClass.comment?.length > 0) {
        template.appendLine(`/**`)
        for (const line of mappedSuperClass.comment.split("\n")) {
            template.appendLine(` * ${line}`)
        }
        template.appendLine(` */`)
    }

    template.appendLine("@MappedSuperclass")
    template.append(`interface ${mappedSuperClass.name}`)

    if (mappedSuperClass.directExtends.size > 0) {
        template.append(" :")
        const extendsList = [...mappedSuperClass.directExtends].map(cls => cls.name);
        for (let i = 0; i < extendsList.length; i++) {
            template.append(`${i === 0 ? '\n    ' : ',\n    '}${extendsList[i]}`);
        }
    }

    template.startScope()

    for (const property of builder.getProperties()) {
        template.appendLine()

        if (property.comment.length > 0) {
            template.appendLine(`/**`)
            for (const line of property.comment.split("\n")) {
                template.appendLine(` * ${line}`)
            }
            template.appendLine(` */`)
        }

        if (property.annotations.length > 0) {
            for (const annotation of property.annotations) {
                template.appendBlock(annotation)
            }
        }

        template.appendLine(`val ${property.name}: ${property.type}${property.nullable ? "?" : ""}`)
    }

    template.endScope()

    result[`/kotlin/${packageDirPath}${mappedSuperClass.name}.kt`] = template.build({cleanEmptyLineIndent: true})

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "mysqlTableGenerator",
        type = ScriptType.TableGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.MYSQL,
        jvmLanguage = JvmLanguageOrAny.ANY,
        scriptContent = $$"""
(
    tables: DeepReadonly<Table[]>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const statementMap = new Map<string, {
        createTable: string,
        createIndexes: string[],
        createForeignKeys: string[],
        createChecks: string[],
    }>()

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: "", end: ""}
    })

    for (const table of tables) {
        template.appendLine(`CREATE TABLE ${table.name}`)
        template.startScope("(")
        const pkColumns: DeepReadonly<Column>[] = []
        for (const column of table.columns) {
            template.append(column.name)
            template.append(" ")
            template.append(column.type)
            if (!column.nullable) {
                template.append(" NOT NULL")
            }
            if (column.defaultValue) {
                template.append(" DEFAULT ")
                template.append(column.defaultValue)
            }
            if (column.autoIncrement) {
                template.append(" AUTO_INCREMENT")
            }
            if (column.comment) {
                template.append(` COMMENT '${column.comment}'`)
            }
            template.appendLine(",")
            if (column.partOfPrimaryKey) {
                pkColumns.push(column)
            }
        }
        template.appendLine(`PRIMARY KEY (${pkColumns.map(it => it.name).join(", ")})`)
        template.endScope(") ")
        template.append(" ENGINE=InnoDB")
        template.append(" DEFAULT CHARSET = utf8mb4")
        template.append(` COMMENT='${table.comment}'`)
        template.appendLine(";")
        const createTable = template.build({cleanEmptyLineIndent: true})
        template.clean()

        const createForeignKeys: string[] = []
        for (const foreignKey of table.foreignKeys) {
            template.append("ALTER TABLE ")
            template.append(table.name)
            template.append(` ADD CONSTRAINT ${foreignKey.name}`)
            template.append(` FOREIGN KEY (${foreignKey.columnRefs.map(it => it.columnName).join(", ")})`)
            template.append(` REFERENCES ${foreignKey.referencedTableName}`)
            template.append(` (${foreignKey.columnRefs.map(it => it.referencedColumnName).join(", ")})`)
            if (foreignKey.onUpdate) {
                template.append(` ON UPDATE ${foreignKey.onUpdate}`)
            }
            if (foreignKey.onDelete) {
                template.append(` ON DELETE ${foreignKey.onDelete}`)
            }
            template.appendLine(";")

            createForeignKeys.push(template.build({cleanEmptyLineIndent: true}))
            template.clean()
        }

        const createIndexes: string[] = []
        for (const index of table.indexes) {
            template.append("CREATE")
            if (index.uniqueIndex) {
                template.append(" UNIQUE")
            }
            template.append(" INDEX ")
            template.append(index.name)
            template.append(" ON ")
            template.append(table.name)
            template.append(` (${index.columnNames.join(", ")})`)
            if (index.wherePredicates) {
                template.append(' WHERE')
                template.append(` ${index.wherePredicates}`)
            }
            template.appendLine(";")
            createIndexes.push(template.build({cleanEmptyLineIndent: true}))
            template.clean()
        }

        const createChecks: string[] = []
        for (const check of table.checks) {
            template.append("ALTER TABLE ")
            template.append(table.name)
            template.append(" ADD CONSTRAINT ")
            template.append(check.name)
            template.append(" CHECK ")
            template.append(check.expression)
            template.appendLine(";")
            createIndexes.push(template.build({cleanEmptyLineIndent: true}))
            template.clean()
        }

        statementMap.set(
            table.name,
            {
                createTable,
                createForeignKeys,
                createIndexes,
                createChecks,
            },
        )
    }

    for (const [tableName, {
        createTable,
        createIndexes,
        createForeignKeys,
        createChecks,
    }] of statementMap) {
        template.appendBlock(createTable)
        if (createIndexes.length > 0) {
            template.appendLine()
            for (const createIndex of createIndexes) {
                template.appendBlock(createIndex)
            }
        }
        if (createForeignKeys.length > 0) {
            template.appendLine()
            for (const createForeignKey of createForeignKeys) {
                template.appendBlock(createForeignKey)
            }
        }
        if (createChecks.length > 0) {
            template.appendLine()
            for (const createCheck of createChecks) {
                template.appendBlock(createCheck)
            }
        }

        result[`/sql/tables/${tableName}.sql`] = template.build({cleanEmptyLineIndent: true})
        template.clean()
    }

    for (const {createTable} of statementMap.values()) {
        template.appendBlock(createTable)
        template.appendLine()
    }
    for (const {createIndexes} of statementMap.values()) {
        if (createIndexes.length > 0) {
            for (const createIndex of createIndexes) {
                template.appendBlock(createIndex)
            }
            template.appendLine()
        }
    }
    for (const {createForeignKeys} of statementMap.values()) {
        if (createForeignKeys.length > 0) {
            for (const createForeignKey of createForeignKeys) {
                template.appendBlock(createForeignKey)
            }
            template.appendLine()
        }
    }
    for (const {createChecks} of statementMap.values()) {
        if (createChecks.length > 0) {
            for (const createCheck of createChecks) {
                template.appendBlock(createCheck)
            }
            template.appendLine()
        }
    }

    result[`/sql/all-tables.sql`] = template.build({cleanEmptyLineIndent: true})
    template.clean()

    return result
}
""".trim(),
    )
,    GenerateScriptInsertInput(
        name = "pgTableGenerator",
        type = ScriptType.TableGenerator,
        enabled = true,
        databaseType = DatabaseTypeOrAny.POSTGRESQL,
        jvmLanguage = JvmLanguageOrAny.ANY,
        scriptContent = $$"""
(
    tables: DeepReadonly<Table[]>,
    context: DeepReadonly<ModelContext>
): Record<string, string> => {
    const result: Record<string, string> = {}

    const statementMap = new Map<string, {
        createTable: string,
        createIndexes: string[],
        createForeignKeys: string[],
        createChecks: string[],
        addComments: string[],
    }>()

    const template = context.createTemplateBuilder({
        indent: "    ",
        scope: {start: "", end: ""}
    })

    for (const table of tables) {
        template.appendLine(`CREATE TABLE ${table.name}`)
        template.startScope("(")
        const pkColumns: DeepReadonly<Column>[] = []
        for (const column of table.columns) {
            template.append(column.name)
            template.append(" ")
            template.append(column.type)
            if (!column.nullable) {
                template.append(" NOT NULL")
            }
            if (column.defaultValue) {
                template.append(" DEFAULT ")
                template.append(column.defaultValue)
            }
            template.appendLine(",")
            if (column.partOfPrimaryKey) {
                pkColumns.push(column)
            }
        }
        template.appendLine(`PRIMARY KEY (${pkColumns.map(it => it.name).join(", ")})`)
        template.endScope(");")
        const createTable = template.build({cleanEmptyLineIndent: true})
        template.clean()

        const createForeignKeys: string[] =[]
        for (const foreignKey of table.foreignKeys) {
            template.append(`ALTER TABLE ${table.name}`)
            template.append(` ADD CONSTRAINT ${foreignKey.name}`)
            template.append(` FOREIGN KEY (${foreignKey.columnRefs.map(it => it.columnName).join(", ")})`)
            template.append(` REFERENCES ${foreignKey.referencedTableName}`)
            template.append(` (${foreignKey.columnRefs.map(it => it.referencedColumnName).join(", ")})`)
            if (foreignKey.onUpdate) {
                template.append(` ON UPDATE ${foreignKey.onUpdate}`)
            }
            if (foreignKey.onDelete) {
                template.append(` ON DELETE ${foreignKey.onDelete}`)
            }
            template.appendLine(";")

            createForeignKeys.push( template.build({cleanEmptyLineIndent: true}))
            template.clean()
        }

        const createIndexes: string[] = []
        for (const index of table.indexes) {
            template.append("CREATE")
            if (index.uniqueIndex) {
                template.append(" UNIQUE")
            }
            template.append(" INDEX ")
            template.append(index.name)
            template.append(" ON ")
            template.append(table.name)
            template.append(` (${index.columnNames.join(", ")})`)
            if (index.wherePredicates) {
                template.append(' WHERE')
                template.append(` ${index.wherePredicates}`)
            }
            template.appendLine(";")
            createIndexes.push( template.build({cleanEmptyLineIndent: true}))
            template.clean()
        }

        const createChecks: string[] = []
        for (const check of table.checks) {
            template.append("ALTER TABLE ")
            template.append(table.name)
            template.append(" ADD CONSTRAINT ")
            template.append(check.name)
            template.append(" CHECK ")
            template.append(check.expression)
            template.appendLine(";")
            createIndexes.push(template.build({cleanEmptyLineIndent: true}))
            template.clean()
        }

        const addComments: string[] = []
        template.append("COMMENT ON TABLE ")
        template.append(table.name)
        template.appendLine(` IS '${table.comment}';`)
        addComments.push(template.build({cleanEmptyLineIndent: true}))
        template.clean()
        for (const column of table.columns) {
            template.append("COMMENT ON COLUMN ")
            template.append(table.name)
            template.append(".")
            template.append(column.name)
            template.append(" IS '")
            template.append(column.comment)
            template.append("';")
            addComments.push(template.build({cleanEmptyLineIndent: true}))
            template.clean()
        }

        // TODO add comment
        statementMap.set(
            table.name,
            {
                createTable,
                createForeignKeys,
                createIndexes,
                createChecks,
                addComments,
            },
        )
    }

    for (const [tableName, {
        createTable,
        createIndexes,
        createForeignKeys,
        createChecks,
        addComments,
    }] of statementMap) {
        template.appendBlock(createTable)
        if (createIndexes.length > 0) {
            template.appendLine()
            for (const createIndex of createIndexes) {
                template.appendBlock(createIndex)
            }
        }
        if (createForeignKeys.length > 0) {
            template.appendLine()
            for (const createForeignKey of createForeignKeys) {
                template.appendBlock(createForeignKey)
            }
        }
        if (createChecks.length > 0) {
            template.appendLine()
            for (const createCheck of createChecks) {
                template.appendBlock(createCheck)
            }
        }
        if (addComments.length > 0) {
            template.appendLine()
            for (const addComment of addComments) {
                template.appendBlock(addComment)
            }
        }

        result[`/sql/tables/${tableName}.sql`] = template.build({cleanEmptyLineIndent: true})
        template.clean()
    }

    for (const {createTable} of statementMap.values()) {
        template.appendBlock(createTable)
        template.appendLine()
    }
    for (const {createIndexes} of statementMap.values()) {
        if (createIndexes.length > 0) {
            for (const createIndex of createIndexes) {
                template.appendBlock(createIndex)
            }
            template.appendLine()
        }
    }
    for (const {createForeignKeys} of statementMap.values()) {
        if (createForeignKeys.length > 0) {
            for (const createForeignKey of createForeignKeys) {
                template.appendBlock(createForeignKey)
            }
            template.appendLine()
        }
    }
    for (const {createChecks} of statementMap.values()) {
        if (createChecks.length > 0) {
            for (const createCheck of createChecks) {
                template.appendBlock(createCheck)
            }
            template.appendLine()
        }
    }
    for (const {addComments} of statementMap.values()) {
        if (addComments.length > 0) {
            for (const addComment of addComments) {
                template.appendBlock(addComment)
            }
            template.appendLine()
        }
    }

    result[`/sql/all-tables.sql`] = template.build({cleanEmptyLineIndent: true})
    template.clean()

    return result
}
""".trim(),
    )

)

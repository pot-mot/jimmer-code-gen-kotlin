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
        const propertyInfo = builder.pushProperty(property)
        if (property.nullable) {
            builder.addImports("org.jetbrains.annotations.Nullable")
            propertyInfo.annotations.push("@Nullable")
        }
    }

    result[`/entity/${embeddableType.name}.java`] = `package ${builder.getPackagePath()};

${[...builder.getImportSet()]
        .sort((a, b) => a.localeCompare(b))
        .map(importItem => `import ${importItem};`).join("\n")}

@Entity
public interface ${embeddableType.name} {
${builder.getProperties()
        .map(property =>
            `    ${property.annotations
                .flatMap(it => it.split("\n"))
                .filter(it => it.trim().length > 0)
                .join("\n    ")}
    ${property.type} ${property.name}();`
        )
        .join("\n\n")}
}
`

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

    for (const mappedSuperClassId of entity.extendsIds) {
        builder.requireMappedSuperClass(mappedSuperClassId)
    }

    for (const property of entity.properties) {
        const propertyInfo = builder.pushProperty(property)
        if (property.nullable) {
            builder.addImports("org.jetbrains.annotations.Nullable")
            propertyInfo.annotations.push("@Nullable")
        }
    }

    const entityExtends = entity.directExtends.size > 0 ?
        " extends\n    " + [...entity.directExtends].map(mappedSuperClass => mappedSuperClass.name).join(",\n    ") + "\n" : " "

    result[`/entity/${entity.name}.java`] = `package ${builder.getPackagePath()};

${[...builder.getImportSet()]
        .sort((a, b) => a.localeCompare(b))
        .map(importItem => `import ${importItem};`).join("\n")}

@Entity
@Table(name = "${entity.tableName}")
public interface ${entity.name}${entityExtends}{
${builder.getProperties()
        .map(property =>
            `    ${property.annotations
                .flatMap(it => it.split("\n"))
                .filter(it => it.trim().length > 0)
                .join("\n    ")}
    ${property.type} ${property.name}();`
        )
        .join("\n\n")}
}
`

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

    result[`/entity/${enumeration.name}.java`] = `package ${builder.getPackagePath()};

${[...builder.getImportSet()]
        .sort((a, b) => a.localeCompare(b))
        .map(importItem => `import ${importItem};`).join("\n")}

${enumeration.strategy === 'NAME' ?
        "@EnumType(EnumType.Strategy.NAME)" :
        "@EnumType(EnumType.Strategy.ORDINAL)"
    }
public enum ${enumeration.name} {
${enumeration.items
        .map(item =>
            `    ${enumeration.strategy === 'NAME' ? 
                `@EnumItem(name = "${item.name}")` :
                `@EnumItem(ordinal = ${item.ordinal})`
        }
    ${item.name},`
        )
        .join("\n\n")}
}
`

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
        const propertyInfo = builder.pushProperty(property)
        if (property.nullable) {
            builder.addImports("org.jetbrains.annotations.Nullable")
            propertyInfo.annotations.push("@Nullable")
        }
    }

    const entityExtends = mappedSuperClass.directExtends.size > 0 ?
        " extends\n    " + [...mappedSuperClass.directExtends].map(mappedSuperClass => mappedSuperClass.name).join(",\n    ") + "\n" : " "

    result[`/entity/${mappedSuperClass.name}.java`] = `package ${builder.getPackagePath()};

${[...builder.getImportSet()]
        .sort((a, b) => a.localeCompare(b))
        .map(importItem => `import ${importItem};`).join("\n")}

@MappedSuperclass
public interface ${mappedSuperClass.name}${entityExtends}{
${builder.getProperties()
        .map(property =>
            `    ${property.annotations
                .flatMap(it => it.split("\n"))
                .filter(it => it.trim().length > 0)
                .join("\n    ")}
    ${property.type} ${property.name}();`
        )
        .join("\n\n")}
}
`

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
        builder.pushProperty(property)
    }

    result[`/entity/${embeddableType.name}.kt`] = `package ${builder.getPackagePath()}

${[...builder.getImportSet()]
        .sort((a, b) => a.localeCompare(b))
        .map(importItem => `import ${importItem}`).join("\n")}

@Embeddable
interface ${embeddableType.name} {
${builder.getProperties()
        .map(property =>
            `    ${property.annotations
                .flatMap(it => it.split("\n"))
                .filter(it => it.trim().length > 0)
                .join("\n    ")}
    val ${property.name}: ${property.type}${property.nullable ? "?" : ""}`
        )
        .join("\n\n")}
}
`

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

    for (const mappedSuperClassId of entity.extendsIds) {
        builder.requireMappedSuperClass(mappedSuperClassId)
    }

    for (const property of entity.properties) {
        builder.pushProperty(property)
    }

    const entityExtends = entity.directExtends.size > 0 ?
        " :\n    " + [...entity.directExtends].map(mappedSuperClass => mappedSuperClass.name).join(",\n    ") + "\n" : " "

    result[`/entity/${entity.name}.kt`] = `package ${builder.getPackagePath()}

${[...builder.getImportSet()]
        .sort((a, b) => a.localeCompare(b))
        .map(importItem => `import ${importItem}`).join("\n")}

@Entity
@Table(name = "${entity.tableName}")
interface ${entity.name}${entityExtends}{
${builder.getProperties()
        .map(property =>
            `    ${property.annotations
                .flatMap(it => it.split("\n"))
                .filter(it => it.trim().length > 0)
                .join("\n    ")}
    val ${property.name}: ${property.type}${property.nullable ? "?" : ""}`
        )
        .join("\n\n")}
}
`

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

    result[`/entity/${enumeration.name}.kt`] = `package ${builder.getPackagePath()}

${[...builder.getImportSet()]
        .sort((a, b) => a.localeCompare(b))
        .map(importItem => `import ${importItem}`).join("\n")}

${enumeration.strategy === 'NAME' ?
        "@EnumType(EnumType.Strategy.NAME)" :
        "@EnumType(EnumType.Strategy.ORDINAL)"
    }
enum class ${enumeration.name} {
${enumeration.items
        .map(item =>
            `    ${enumeration.strategy === 'NAME' ?
                `@EnumItem(name = "${item.name}")` :
                `@EnumItem(ordinal = ${item.ordinal})`
            }
    ${item.name},`
        )
        .join("\n\n")}
}
`

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
        builder.pushProperty(property)
    }

    const entityExtends = mappedSuperClass.directExtends.size > 0 ?
        " :\n    " + [...mappedSuperClass.directExtends].map(mappedSuperClass => mappedSuperClass.name).join(",\n    ") + "\n" : " "

    result[`/entity/${mappedSuperClass.name}.kt`] = `package ${builder.getPackagePath()}

${[...builder.getImportSet()]
        .sort((a, b) => a.localeCompare(b))
        .map(importItem => `import ${importItem}`).join("\n")}

@MappedSuperclass
interface ${mappedSuperClass.name}${entityExtends}{
${builder.getProperties()
        .map(property =>
            `    ${property.annotations
                .flatMap(it => it.split("\n"))
                .filter(it => it.trim().length > 0)
                .join("\n    ")}
    val ${property.name}: ${property.type}${property.nullable ? "?" : ""}`
        )
        .join("\n\n")}
}
`

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
    const baseDir = "/sql"
    const result: Record<string, string> = {}

    const statementMap = new Map<string, {
        createTable: string,
        createIndexes: string[],
        createForeignKeys: string[],
    }>()

    for (const table of tables) {
        // TODO add table checks
        statementMap.set(
            table.name,
            {
                createTable: `CREATE TABLE ${table.name}
(
${table.columns.map(column => `    ${column.name} ${column.type} ${
    column.nullable ? '' : 'NOT NULL'
} ${
    column.defaultValue ? `DEFAULT ${column.defaultValue}` : ''
} ${
    column.autoIncrement ? 'AUTO INCREMENT' : ''
} ${
    column.comment ? `COMMENT '${column.comment}'` : ''
}`.trimEnd()).join(",\n")},
    PRIMARY KEY (${table.columns.filter(it => it.partOfPrimaryKey).map(it => it.name).join(", ")})
);
`,
                createForeignKeys: table.foreignKeys.map(foreignKey => `ALTER TABLE ${table.name}
    ADD CONSTRAINT ${foreignKey.name}
        FOREIGN KEY (${foreignKey.columnRefs.map(it => it.columnName).join(", ")}) 
            REFERENCES ${foreignKey.referencedTableName} (${foreignKey.columnRefs.map(it => it.referencedColumnName).join(", ")});
`),
                createIndexes: table.indexes.map(index => `CREATE ${index.uniqueIndex ? "UNIQUE" : ""} INDEX ${index.name} ON ${table.name} (${index.columnNames.join(", ")});`)
            },
        )
    }

    for (const [tableName, {createTable, createIndexes, createForeignKeys}] of statementMap) {
        result[`${baseDir}/tables/${tableName}.sql`] = `${createTable}\n${createIndexes.join("\n")}\n${createForeignKeys.join("\n")}`
    }

    const allTableStatements: string[] = []
    for (const {createTable, createIndexes} of statementMap.values()) {
        allTableStatements.push(createTable)
        allTableStatements.push(...createIndexes)
    }
    for (const {createForeignKeys} of statementMap.values()) {
        allTableStatements.push(...createForeignKeys)
    }
    result[`${baseDir}/all-tables.sql`] = allTableStatements.join("\n")

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
    const baseDir = "/sql"
    const result: Record<string, string> = {}

    const statementMap = new Map<string, {
        createTable: string,
        createIndexes: string[],
        createForeignKeys: string[],
    }>()

    for (const table of tables) {
        // TODO add table checks
        statementMap.set(
            table.name,
            {
                createTable: `CREATE TABLE ${table.name}
(
${table.columns.map(column => `    ${column.name} ${column.type} ${
    column.nullable ? '' : 'NOT NULL'
} ${
    column.defaultValue ? `DEFAULT ${column.defaultValue}` : ''
}`.trimEnd()).join(",\n")},
    PRIMARY KEY (${table.columns.filter(it => it.partOfPrimaryKey).map(it => it.name).join(", ")})
);
`,
                createForeignKeys: table.foreignKeys.map(foreignKey => `ALTER TABLE ${table.name}
    ADD CONSTRAINT ${foreignKey.name}
        FOREIGN KEY (${foreignKey.columnRefs.map(it => it.columnName).join(", ")}) 
            REFERENCES ${foreignKey.referencedTableName} (${foreignKey.columnRefs.map(it => it.referencedColumnName).join(", ")});
`),
                createIndexes: table.indexes.map(index => `CREATE ${index.uniqueIndex ? "UNIQUE" : ""} INDEX ${index.name} ON ${table.name} (${index.columnNames.join(", ")});`)
            },
        )
    }

    for (const [tableName, {createTable, createIndexes, createForeignKeys}] of statementMap) {
        result[`${baseDir}/tables/${tableName}.sql`] = `${createTable}\n${createIndexes.join("\n")}\n${createForeignKeys.join("\n")}`
    }

    const allTableStatements: string[] = []
    for (const {createTable, createIndexes} of statementMap.values()) {
        allTableStatements.push(createTable)
        allTableStatements.push(...createIndexes)
    }
    for (const {createForeignKeys} of statementMap.values()) {
        allTableStatements.push(...createForeignKeys)
    }
    result[`${baseDir}/all-tables.sql`] = allTableStatements.join("\n")

    return result
}
""".trim(),
    )

)

package top.potmot.entity.typeMapping.dto

fun List<CrossTypeInput>.crossTypeWithOrderKey() = mapIndexed { index, input ->
    input.toEntity { orderKey = index }
}

fun List<JvmTypeInput>.jvmTypeWithOrderKey() = mapIndexed { index, input ->
    input.toEntity {
        orderKey = index
        sqlMatchRules = input.sqlMatchRules.mapIndexed { subIndex, rule ->
            rule.toEntity { orderKey = subIndex }
        }
        tsMatchRules = input.tsMatchRules.mapIndexed { subIndex, rule ->
            rule.toEntity { orderKey = subIndex }
        }
    }
}

fun List<SqlTypeInput>.sqlTypeWithOrderKey() = mapIndexed { index, input ->
    input.toEntity {
        orderKey = index
        jvmMatchRules = input.jvmMatchRules.mapIndexed { subIndex, rule ->
            rule.toEntity { orderKey = subIndex }
        }
        tsMatchRules = input.tsMatchRules.mapIndexed { subIndex, rule ->
            rule.toEntity { orderKey = subIndex }
        }
    }
}

fun List<TsTypeInput>.tsTypeWithOrderKey() = mapIndexed { index, input ->
    input.toEntity {
        orderKey = index
        jvmMatchRules = input.jvmMatchRules.mapIndexed { subIndex, rule ->
            rule.toEntity { orderKey = subIndex }
        }
        sqlMatchRules = input.sqlMatchRules.mapIndexed { subIndex, rule ->
            rule.toEntity { orderKey = subIndex }
        }
    }
}

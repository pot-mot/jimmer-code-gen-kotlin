package top.potmot.entity.typeMapping.dto

fun List<CrossTypeInput>.withOrderKey() = mapIndexed { index, input ->
    input.toEntity { orderKey = index }
}

fun List<JvmTypeInput>.withOrderKey() = mapIndexed { index, input ->
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

fun List<SqlTypeInput>.withOrderKey() = mapIndexed { index, input ->
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

fun List<TsTypeInput>.withOrderKey() = mapIndexed { index, input ->
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

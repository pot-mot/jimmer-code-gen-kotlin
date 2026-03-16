package top.potmot.entity.typeMapping.dto

import org.babyfish.jimmer.DraftObjects.unload
import top.potmot.entity.typeMapping.CrossType
import top.potmot.entity.typeMapping.JvmType
import top.potmot.entity.typeMapping.SqlType
import top.potmot.entity.typeMapping.TsType
import top.potmot.entity.typeMapping.copy

fun List<CrossTypeInput>.crossTypeWithOrderKey() = mapIndexed { index, input ->
    input.toEntity { orderKey = index }
}

fun CrossTypeUpdateInput.prepareOrderKey() = toEntity {
    unload(this, CrossType::orderKey.name)
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
fun JvmTypeUpdateInput.prepareOrderKey() = toEntity {
    unload(this, JvmType::orderKey.name)
    sqlMatchRules = sqlMatchRules.mapIndexed { subIndex, rule ->
        rule.copy { orderKey = subIndex }
    }
    tsMatchRules = tsMatchRules.mapIndexed { subIndex, rule ->
        rule.copy { orderKey = subIndex }
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

fun SqlTypeUpdateInput.prepareOrderKey() = toEntity {
    unload(this, SqlType::orderKey.name)
    jvmMatchRules = jvmMatchRules.mapIndexed { subIndex, rule ->
        rule.copy { orderKey = subIndex }
    }
    tsMatchRules = tsMatchRules.mapIndexed { subIndex, rule ->
        rule.copy { orderKey = subIndex }
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

fun TsTypeUpdateInput.prepareOrderKey() = toEntity {
    unload(this, TsType::orderKey.name)
    jvmMatchRules = jvmMatchRules.mapIndexed { subIndex, rule ->
        rule.copy { orderKey = subIndex }
    }
    sqlMatchRules = sqlMatchRules.mapIndexed { subIndex, rule ->
        rule.copy { orderKey = subIndex }
    }
}

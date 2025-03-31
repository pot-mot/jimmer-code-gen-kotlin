package top.potmot.core.common

import org.babyfish.jimmer.View
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ne
import org.babyfish.jimmer.sql.kt.ast.query.KMutableRootQuery
import top.potmot.core.business.meta.EnumBusiness
import top.potmot.core.business.meta.RootEntityBusiness
import top.potmot.core.business.meta.toFlat
import top.potmot.entity.GenEntity
import top.potmot.entity.GenEnum
import top.potmot.entity.GenTable
import top.potmot.entity.dto.GenEntityBusinessView
import top.potmot.entity.dto.GenEntityGenerateFileFillView
import top.potmot.entity.dto.GenEntityGenerateView
import top.potmot.entity.dto.GenEnumGenerateFileFillView
import top.potmot.entity.dto.GenEnumGenerateView
import top.potmot.entity.dto.GenTableGenerateView
import top.potmot.entity.dto.IdName
import top.potmot.entity.dto.IdNamePackagePath
import top.potmot.entity.dto.TableEntityNotNullPair
import top.potmot.entity.modelId
import top.potmot.entity.table
import top.potmot.entity.type
import top.potmot.enumeration.GenerateType
import top.potmot.enumeration.TableType

data class GenerateContext(
    val typeSet: Set<GenerateType>,
    val sqlClient: KSqlClient,
    val modelId: Long
) {
    private inline fun <reified V : View<GenTable>> KSqlClient.listTable(
        modelId: Long
    ) =
        executeQuery(GenTable::class) {
            where(table.modelId eq modelId)
            select(table.fetch(V::class))
        }

    private inline fun <reified V : View<GenEntity>> KSqlClient.listEntity(
        modelId: Long,
        crossinline block: KMutableRootQuery<GenEntity>.() -> Unit = {},
    ) =
        executeQuery(GenEntity::class) {
            where(table.modelId eq modelId)
            block()
            select(table.fetch(V::class))
        }

    private inline fun <reified V : View<GenEnum>> KSqlClient.listEnum(
        modelId: Long
    ) =
        executeQuery(GenEnum::class) {
            where(table.modelId eq modelId)
            select(table.fetch(V::class))
        }

    val lazyTable = lazy {
        sqlClient.listTable<GenTableGenerateView>(modelId)
    }
    val tables by lazyTable

    val lazyEntities = lazy {
        sqlClient.listEntity<GenEntityGenerateView>(modelId)
    }
    val entities by lazyEntities

    val lazyEnums = lazy {
        sqlClient.listEnum<GenEnumGenerateView>(modelId)
    }
    val enums by lazyEnums

    val enumBusinesses by lazy {
        enums.map { EnumBusiness(it) }
    }
    val enumBusinessIdMap by lazy {
        enumBusinesses.associateBy { it.id }
    }

    val lazyEntityBusinesses = lazy {
        val entityBusinessViews = sqlClient.listEntity<GenEntityBusinessView>(modelId) {
            where(table.table.type ne TableType.SUPER_TABLE)
        }.map { it.toFlat() }
        val entityBusinessIdMap = entityBusinessViews.associateBy { it.id }
        entityBusinessViews.map { RootEntityBusiness(it, entityBusinessIdMap, enumBusinessIdMap) }
    }
    val entityBusinesses by lazyEntityBusinesses

    val tableEntityPairsForInfo by lazy {
        if (lazyEntities.isInitialized()) {
            entities.mapNotNull {
                if (it.table == null) null
                else TableEntityNotNullPair(
                    table = IdName(it.table.id, it.table.name),
                    entity = IdNamePackagePath(it.id, it.name, it.packagePath)
                )
            }
        } else {
            sqlClient.listEntity<GenEntityGenerateFileFillView>(modelId = modelId).mapNotNull {
                if (it.table == null) null
                else TableEntityNotNullPair(
                    table = IdName(it.table.id, it.table.name),
                    entity = IdNamePackagePath(it.id, it.name, it.packagePath)
                )
            }
        }
    }

    val enumsForInfo by lazy {
        if (lazyEnums.isInitialized()) {
            enums.map {
                IdNamePackagePath(it.id, it.name, it.packagePath)
            }
        } else {
            sqlClient.listEnum<GenEnumGenerateFileFillView>(modelId = modelId).map {
                IdNamePackagePath(it.id, it.name, it.packagePath)
            }
        }
    }
}
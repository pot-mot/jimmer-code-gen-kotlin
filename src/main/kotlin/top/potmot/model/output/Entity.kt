package top.potmot.model.output

import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import top.potmot.model.GenEntity
import top.potmot.model.by

val COMMON = newFetcher(GenEntity::class).by {
    allScalarFields()
}
